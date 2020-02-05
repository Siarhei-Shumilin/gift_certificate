package com.epam.esm.service;

import com.epam.esm.entity.CertificateTagConnecting;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Parameters;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateFieldCanNotNullException;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.CertificateTagConnectingMapper;
import com.epam.esm.util.CertificateValidator;
import com.epam.esm.util.TagVerifier;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
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
    private final MessageSource messageSource;

    public CertificateService(TagService tagService, CertificateValidator validator, TagVerifier tagVerifier,
                              CertificateMapper certificateMapper, CertificateTagConnecting certificateTagConnecting,
                              CertificateTagConnectingMapper certificateTagConnectingMapperBatis, MessageSource messageSource) {
        this.tagService = tagService;
        this.validator = validator;
        this.tagVerifier = tagVerifier;
        this.certificateMapper = certificateMapper;
        this.certificateTagConnecting = certificateTagConnecting;
        this.certificateTagConnectingMapperBatis = certificateTagConnectingMapperBatis;
        this.messageSource = messageSource;
    }

    @Transactional
    public List<GiftCertificate> findByParameters(Parameters parameters, Locale locale) {
        List<GiftCertificate> certificateList = new ArrayList<>();
        if (parameters.getListTagName() == null) {
            certificateList = certificateMapper.findByParameters(parameters, getRowBounds(parameters));
        } else {
            certificateList.addAll(searchCertificatesByTags(parameters));
        }
        if (certificateList.isEmpty()) {
            throw new CertificateNotFoundException(messageSource.getMessage("certificate.not.exists", null, locale));
        }
        setTagsToCertificates(certificateList);
        return certificateList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Transactional
    public long save(GiftCertificate giftCertificate, Locale locale) throws CertificateFieldCanNotNullException {
        giftCertificate.setCreateDate(getDate());
        giftCertificate.setLastUpdateDate(getDate());
        if (validator.validate(giftCertificate)) {
            tagVerifier.checkAndSaveTagIfNotExist(giftCertificate);
            certificateMapper.save(giftCertificate);
            saveConnect(giftCertificate);
        } else {
            throw new CertificateFieldCanNotNullException(messageSource.getMessage("certificate.field.null", null, locale));
        }
        return giftCertificate.getId();
    }

    public String delete(int id) {
        return "Deleted " + certificateMapper.delete(id);
    }

    @Transactional
    public boolean update(GiftCertificate giftCertificate, Locale locale) throws CertificateFieldCanNotNullException {
        int updatedRow;
        if (validator.validate(giftCertificate)) {
            updatedRow = updateWholeObject(giftCertificate);
        } else if (giftCertificate.getId() != 0 && giftCertificate.getPrice() != null) {
            giftCertificate.setLastUpdateDate(getDate());
            updatedRow = certificateMapper.update(giftCertificate);
        } else {
            throw new CertificateFieldCanNotNullException(messageSource.getMessage("certificate.field.null", null, locale));
        }
        return updatedRow > 0;
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

    private int updateWholeObject(GiftCertificate giftCertificate) {
        tagVerifier.checkAndSaveTagIfNotExist(giftCertificate);
        giftCertificate.setLastUpdateDate(getDate());
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

    private List<GiftCertificate> searchCertificatesByTags(Parameters parameters) {
        RowBounds rowBounds = getRowBounds(parameters);
        List<GiftCertificate> certificateList = new ArrayList<>();
        if (parameters.getListTagName().size() == 1) {
            parameters.setTagName(parameters.getListTagName().get(0));
            certificateList = certificateMapper.findByParameters(parameters, rowBounds);
        } else {
            for (String tagName : parameters.getListTagName()) {
                parameters.setTagName(tagName);
                certificateList.addAll(certificateMapper.findByParameters(parameters, rowBounds));
            }
        }
        return certificateList;
    }

    private LocalDateTime getDate() {
        Timestamp date = Timestamp.from(Instant.now());
        return date.toLocalDateTime();
    }

    private RowBounds getRowBounds(Parameters parameters) {
        Integer page = parameters.getPage();
        if (parameters.getPage() == null) {
            page = 1;
        }
        return new RowBounds(((page - 1) * 5), 5);
    }
}