package com.epam.esm.service;

import com.epam.esm.entity.*;
import com.epam.esm.exception.CertificateFieldCanNotNullException;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.CertificateTagConnectingMapper;
import com.epam.esm.util.CertificateValidator;
import com.epam.esm.util.TagVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {
    @Autowired
    private TagService tagService;
    @Autowired
    private CertificateValidator validator;
    @Autowired
    private TagVerifier tagVerifier;
    @Autowired
    private CertificateMapper mapperMyBatis;
    @Autowired
    private CertificateTagConnectingMapper certificateTagConnectingMapperBatis;

    public List<GiftCertificate> findByParameters(Parameters parameters) {
        List<GiftCertificate> certificateList = mapperMyBatis.findByParameters(parameters);
        if (certificateList.size() < 1) {
            throw new CertificateNotFoundException();
        }
        setTagsToCertificates(certificateList);
        return certificateList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(GiftCertificate giftCertificate) throws CertificateFieldCanNotNullException {
        Timestamp date = Timestamp.from(Instant.now());
        LocalDateTime localDateTime = date.toLocalDateTime();
        giftCertificate.setCreateDate(localDateTime);
        giftCertificate.setLastUpdateDate(localDateTime);
        if (validator.validate(giftCertificate)) {
            if (giftCertificate.getTagList() != null) {
                tagVerifier.checkAndSaveTagIfNotExist(giftCertificate);
            }
            mapperMyBatis.save(giftCertificate);
            int id = giftCertificate.getId();
            giftCertificate.setId(id);
            saveConnect(giftCertificate);
        } else {
            throw new CertificateFieldCanNotNullException();
        }
    }

    private void saveConnect(GiftCertificate giftCertificate) {
        List<String> tagName = giftCertificate.getTagList().stream()
                .map(AbstractEntity::getName)
                .collect(Collectors.toList());
        for (String name : tagName) {
            List<Tag> tags = tagService.findByParameters(name);
            CertificateTagConnecting certificateTagConnecting = new CertificateTagConnecting(giftCertificate.getId(), tags.get(0).getId());
            certificateTagConnectingMapperBatis.save(certificateTagConnecting);
        }
    }

    public boolean delete(int id) {
        return mapperMyBatis.delete(id);
    }

    @Transactional
    public boolean update(GiftCertificate giftCertificate) throws CertificateFieldCanNotNullException {
        boolean update = false;
        if (validator.validate(giftCertificate)) {
            tagVerifier.checkAndSaveTagIfNotExist(giftCertificate);
            mapperMyBatis.update(giftCertificate);
            List<String> tagName = giftCertificate.getTagList().stream()
                    .map(AbstractEntity::getName)
                    .collect(Collectors.toList());
            CertificateTagConnecting certificateTagConnecting = new CertificateTagConnecting();
            certificateTagConnecting.setCertificateId(giftCertificate.getId());
            certificateTagConnectingMapperBatis.deleteConnect(certificateTagConnecting);
            for (String name : tagName) {
                List<Tag> tags = tagService.findByParameters(name);
                certificateTagConnecting.setTagId(tags.get(0).getId());
                certificateTagConnectingMapperBatis.updateConnect(certificateTagConnecting);
            }
        } else {
            throw new CertificateFieldCanNotNullException();
        }
        return update;
    }

    private void setTagsToCertificates(List<GiftCertificate> certificates) {
        for (GiftCertificate giftCertificate : certificates) {
            CertificateTagConnecting certificateTagConnecting = new CertificateTagConnecting();
            certificateTagConnecting.setCertificateId(giftCertificate.getId());
            List<Tag> certificateTags = mapperMyBatis.findCertificateTags(certificateTagConnecting);
            giftCertificate.setTagList(certificateTags);
        }
    }
}