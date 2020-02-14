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
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CertificateService {
    private final TagService tagService;
    private final CertificateValidator validator;
    private final TagVerifier tagVerifier;
    private final CertificateMapper certificateMapper;
    private final CertificateTagConnecting certificateTagConnecting;
    private final CertificateTagConnectingMapper certificateTagConnectingMapperBatis;

    public CertificateService(TagService tagService, CertificateValidator validator, TagVerifier tagVerifier,
                              CertificateMapper certificateMapper, CertificateTagConnecting certificateTagConnecting,
                              CertificateTagConnectingMapper certificateTagConnectingMapperBatis) {
        this.tagService = tagService;
        this.validator = validator;
        this.tagVerifier = tagVerifier;
        this.certificateMapper = certificateMapper;
        this.certificateTagConnecting = certificateTagConnecting;
        this.certificateTagConnectingMapperBatis = certificateTagConnectingMapperBatis;
    }

    public List<GiftCertificate> findByParameters(Map<String, Object> parameters, List<String> tagList) {
        List<GiftCertificate> certificateList = new ArrayList<>();
        if (tagList == null) {
            certificateList = certificateMapper.findByParameters(parameters, getRowBounds(parameters));
        } else {
            for (String tagName : tagList) {
                parameters.put("tag", tagName);
                certificateList.addAll(certificateMapper.findByParameters(parameters, getRowBounds(parameters)));
            }
        }
        setTagsToCertificates(certificateList);
        return certificateList.stream()
                .distinct()
                .collect(Collectors.toList());
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
    public int update(GiftCertificate giftCertificate, Locale locale) {
        int updatedRow;
        if (validator.validate(giftCertificate)) {
            updatedRow = updateWholeObject(giftCertificate, locale);
        } else if (giftCertificate.getId() != 0 && giftCertificate.getPrice() != null) {
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            updatedRow = certificateMapper.update(giftCertificate);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_DATA_INCORRECT, locale);
        }
        return updatedRow;
    }

    private void saveConnect(GiftCertificate giftCertificate) {
        List<String> tagName = giftCertificate.getTagList().stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        for (String name : tagName) {
            List<Tag> tags = tagService.findByParameters(name);
            certificateTagConnecting.setCertificateId(giftCertificate.getId());
            certificateTagConnecting.setTagId(tags.get(0).getId());
            certificateTagConnectingMapperBatis.save(certificateTagConnecting);
        }
    }

    private int updateWholeObject(GiftCertificate giftCertificate, Locale locale) {
        tagVerifier.checkAndSaveTagIfNotExist(giftCertificate, locale);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        int update = certificateMapper.update(giftCertificate);
        certificateTagConnecting.setCertificateId(giftCertificate.getId());
        certificateTagConnectingMapperBatis.deleteConnect(certificateTagConnecting);
        saveConnect(giftCertificate);
        return update;
    }

    private void setTagsToCertificates(List<GiftCertificate> certificates) {
        for (GiftCertificate giftCertificate : certificates) {
            certificateTagConnecting.setCertificateId(giftCertificate.getId());
            List<Tag> certificateTags = certificateMapper.findCertificateTags(certificateTagConnecting);
            giftCertificate.setTagList(certificateTags);
        }
    }

    private RowBounds getRowBounds(Map<String, Object> parameters) {
        int page;
        if (parameters.get("page") == null) {
            page = 1;
        } else {
            page = Integer.parseInt((String)parameters.get("page"));
        }
        return new RowBounds(((page - 1) * 5), 5);
    }
}