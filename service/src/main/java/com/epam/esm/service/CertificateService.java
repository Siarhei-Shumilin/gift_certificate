package com.epam.esm.service;

import com.epam.esm.entity.CertificateTagConnecting;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.CertificateTagConnectingMapper;
import com.epam.esm.util.CertificateValidator;
import com.epam.esm.util.TagVerifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CertificateService extends GeneralService {
    private final TagService tagService;
    private final CertificateValidator validator;
    private final TagVerifier tagVerifier;
    private final CertificateMapper certificateMapper;
    private final CertificateTagConnectingMapper certificateTagConnectingMapper;

    public CertificateService(TagService tagService, CertificateValidator validator, TagVerifier tagVerifier,
                              CertificateMapper certificateMapper, CertificateTagConnectingMapper certificateTagConnectingMapperBatis) {
        this.tagService = tagService;
        this.validator = validator;
        this.tagVerifier = tagVerifier;
        this.certificateMapper = certificateMapper;
        this.certificateTagConnectingMapper = certificateTagConnectingMapperBatis;
    }

    public List<GiftCertificate> findByParameters(Map<String, Object> parameters, List<String> tagList, Locale locale) {
        List<GiftCertificate> certificateList = certificateMapper.findByParameters(parameters, tagList, getRowBounds(parameters, locale));
        for (GiftCertificate giftCertificate : certificateList) {
            CertificateTagConnecting certificateTagConnecting = new CertificateTagConnecting();
            certificateTagConnecting.setCertificateId(giftCertificate.getId());
            List<Tag> certificateTags = certificateMapper.findCertificateTags(certificateTagConnecting);
            giftCertificate.setTagList(certificateTags);
        }
        return certificateList;
    }

    @Transactional
    public long save(GiftCertificate giftCertificate, Locale locale) {
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        if (validator.validate(giftCertificate)) {
            tagVerifier.checkAndSaveTagIfNotExist(giftCertificate, locale);
            certificateMapper.save(giftCertificate);
            saveConnect(giftCertificate);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_DATA_INCORRECT, locale);
        }
        return giftCertificate.getId();
    }

    public int delete(int id) {
        return certificateMapper.delete(id);
    }

    @Transactional
    public int update(long id, GiftCertificate giftCertificate, Locale locale) {
        int update;
        if (validator.validate(giftCertificate)) {
            CertificateTagConnecting certificateTagConnecting = new CertificateTagConnecting();
            tagVerifier.checkAndSaveTagIfNotExist(giftCertificate, locale);
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            giftCertificate.setId(id);
            update = certificateMapper.update(giftCertificate);
            certificateTagConnecting.setCertificateId(id);
            certificateTagConnectingMapper.deleteConnect(certificateTagConnecting);
            saveConnect(giftCertificate);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_DATA_INCORRECT, locale);
        }
        return update;
    }

    @Transactional
    public int updatePrice(long id, GiftCertificate giftCertificate, Locale locale) {
        int updatedRow;
        if (giftCertificate.getPrice() != null && giftCertificate.getPrice().intValue() > 0) {
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            giftCertificate.setId(id);
            updatedRow = certificateMapper.updatePrice(giftCertificate);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_DATA_INCORRECT, locale);
        }

        return updatedRow;
    }

    private void saveConnect(GiftCertificate giftCertificate) {
        List<Tag> tagList = giftCertificate.getTagList();
        for (Tag tag : tagList) {
            long tagId = tagService.findIdTag(tag.getName());
            CertificateTagConnecting certificateTagConnecting = new CertificateTagConnecting(giftCertificate.getId(), tagId);
            certificateTagConnectingMapper.save(certificateTagConnecting);
        }
    }
}