package com.epam.esm.service;

import com.epam.esm.entity.CertificateTagConnecting;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.CertificateTagConnectingMapper;
import com.epam.esm.util.CertificateValidator;
import com.epam.esm.util.TagValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class CertificateService extends GeneralService {
    private final TagService tagService;
    private final CertificateValidator validator;
    private final TagValidator tagValidator;
    private final CertificateMapper certificateMapper;
    private final CertificateTagConnectingMapper certificateTagConnectingMapper;

    public CertificateService(TagService tagService, CertificateValidator validator, TagValidator tagValidator,
                              CertificateMapper certificateMapper, CertificateTagConnectingMapper certificateTagConnectingMapperBatis) {
        this.tagService = tagService;
        this.validator = validator;
        this.tagValidator = tagValidator;
        this.certificateMapper = certificateMapper;
        this.certificateTagConnectingMapper = certificateTagConnectingMapperBatis;
    }

    public List<GiftCertificate> findByParameters(Map<String, Object> parameters, List<String> tagList, Locale locale) {
        List<GiftCertificate> certificateList = certificateMapper.findByParameters(parameters, tagList, getRowBounds(parameters, locale));
        for (GiftCertificate giftCertificate : certificateList) {
            List<Tag> certificateTags = certificateMapper.findCertificateTags(giftCertificate.getId());
            giftCertificate.setTagList(certificateTags);
        }
        return certificateList;
    }

    public GiftCertificate findById(String id, Locale locale) {
        long certificateId = parseId(id, locale);
        GiftCertificate giftCertificate = certificateMapper.findById(certificateId);
        List<Tag> certificateTags = certificateMapper.findCertificateTags(certificateId);
        giftCertificate.setTagList(certificateTags);
        return giftCertificate;
    }

    @Transactional
    public long save(GiftCertificate giftCertificate, Locale locale) {
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        if (validator.validate(giftCertificate)) {
            tagValidator.checkAndSaveTagIfNotExist(giftCertificate, locale);
            certificateMapper.save(giftCertificate);
            saveConnect(giftCertificate);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_DATA_INCORRECT, locale);
        }
        return giftCertificate.getId();
    }

    public int delete(String id, Locale locale) {
        long certificateId = parseId(id, locale);
        return certificateMapper.delete(certificateId);
    }

    @Transactional
    public int update(String id, GiftCertificate giftCertificate, Locale locale) {
        long certificateId = parseId(id, locale);
        int update;
        if (validator.validate(giftCertificate)) {
            tagValidator.checkAndSaveTagIfNotExist(giftCertificate, locale);
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            giftCertificate.setId(certificateId);
            update = certificateMapper.update(giftCertificate);
            certificateTagConnectingMapper.deleteConnect(certificateId);
            saveConnect(giftCertificate);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_DATA_INCORRECT, locale);
        }
        return update;
    }

    @Transactional
    public int updatePrice(String id, GiftCertificate giftCertificate, Locale locale) {
        long certificateId = parseId(id, locale);
        int updatedRow;
        if (giftCertificate.getPrice() != null && giftCertificate.getPrice().intValue() > 0) {
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            giftCertificate.setId(certificateId);
            updatedRow = certificateMapper.updatePrice(giftCertificate);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_DATA_INCORRECT, locale);
        }

        return updatedRow;
    }

    private void saveConnect(GiftCertificate giftCertificate) {
        List<Tag> listTagWithoutId = giftCertificate.getTagList();
        List<Tag> tagList = tagService.findTagByName(listTagWithoutId);
        List<CertificateTagConnecting> list = new ArrayList<>();
        for (Tag tag : tagList) {
            list.add(new CertificateTagConnecting(giftCertificate.getId(), tag.getId()));
        }
        certificateTagConnectingMapper.save(list);
    }
}