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
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
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
    private final TagVerifier tagVerifier;
    private final CertificateMapper certificateMapper;
    private final CertificateTagConnectingMapper certificateTagConnectingMapper;
    private final SqlSessionFactory sqlSessionFactory;

    public CertificateService(TagService tagService, CertificateValidator validator, TagVerifier tagVerifier,
                              CertificateMapper certificateMapper, CertificateTagConnectingMapper certificateTagConnectingMapperBatis, SqlSessionFactory sqlSessionFactory) {
        this.tagService = tagService;
        this.validator = validator;
        this.tagVerifier = tagVerifier;
        this.certificateMapper = certificateMapper;
        this.certificateTagConnectingMapper = certificateTagConnectingMapperBatis;
        this.sqlSessionFactory = sqlSessionFactory;
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

    public int delete(String id, Locale locale) {
        long certificateId;
        try {
            certificateId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new GeneralException(ExceptionType.INCORRECT_DATA_FORMAT, locale);
        }
        return certificateMapper.delete(certificateId);
    }

    @Transactional
    public int update(String id, GiftCertificate giftCertificate, Locale locale) {
        long certificateId;
        try {
            certificateId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new GeneralException(ExceptionType.INCORRECT_DATA_FORMAT, locale);
        }
        int update;
        if (validator.validate(giftCertificate)) {
            CertificateTagConnecting certificateTagConnecting = new CertificateTagConnecting();
            tagVerifier.checkAndSaveTagIfNotExist(giftCertificate, locale);
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            giftCertificate.setId(certificateId);
            update = certificateMapper.update(giftCertificate);
            certificateTagConnecting.setCertificateId(certificateId);
            certificateTagConnectingMapper.deleteConnect(certificateTagConnecting);
            saveConnect(giftCertificate);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_DATA_INCORRECT, locale);
        }
        return update;
    }

    @Transactional
    public int updatePrice(String id, GiftCertificate giftCertificate, Locale locale) {
        long certificateId;
        try {
            certificateId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new GeneralException(ExceptionType.INCORRECT_DATA_FORMAT, locale);
        }
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
        List<Tag> tagList = giftCertificate.getTagList();
        List<CertificateTagConnecting> list = new ArrayList<>();
        for (Tag tag : tagList) {
            long tagId = tagService.findIdTag(tag.getName());
            list.add(new CertificateTagConnecting(giftCertificate.getId(), tagId));
        }
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        for (CertificateTagConnecting connecting : list) {
            sqlSessionTemplate.insert("com.epam.esm.mapper.CertificateTagConnectingMapper.save", connecting);
        }
        sqlSessionTemplate.flushStatements();
    }
}