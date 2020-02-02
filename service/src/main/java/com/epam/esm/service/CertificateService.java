package com.epam.esm.service;

import com.epam.esm.entity.*;
import com.epam.esm.exception.CertificateFieldCanNotNullException;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.CertificateTagConnectingMapper;
import com.epam.esm.util.CertificateValidator;
import com.epam.esm.util.TagVerifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {
    private final TagService tagService;
    private final CertificateValidator validator;
    private final TagVerifier tagVerifier;
    private final CertificateMapper certificateMapper;
    private final CertificateTagConnectingMapper certificateTagConnectingMapperBatis;

    public CertificateService(TagService tagService, CertificateValidator validator, TagVerifier tagVerifier,
                              CertificateMapper certificateMapper, CertificateTagConnectingMapper certificateTagConnectingMapperBatis) {
        this.tagService = tagService;
        this.validator = validator;
        this.tagVerifier = tagVerifier;
        this.certificateMapper = certificateMapper;
        this.certificateTagConnectingMapperBatis = certificateTagConnectingMapperBatis;
    }

    public List<GiftCertificate> findByParameters(Parameters parameters) {
        List<GiftCertificate> certificateList = certificateMapper.findByParameters(parameters);
        if (certificateList.size() < 1) {
            throw new CertificateNotFoundException("There is no such certificate");
        }
        setTagsToCertificates(certificateList);
        return certificateList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Transactional
    public long save(GiftCertificate giftCertificate) throws CertificateFieldCanNotNullException {
        Timestamp date = Timestamp.from(Instant.now());
        LocalDateTime localDateTime = date.toLocalDateTime();
        giftCertificate.setCreateDate(localDateTime);
        giftCertificate.setLastUpdateDate(localDateTime);
        long id;
        if (validator.validate(giftCertificate)) {
            if (giftCertificate.getTagList() != null) {
                tagVerifier.checkAndSaveTagIfNotExist(giftCertificate);
            }
            certificateMapper.save(giftCertificate);
            id = giftCertificate.getId();
            giftCertificate.setId(id);
            saveConnect(giftCertificate);
        } else {
            throw new CertificateFieldCanNotNullException("The certificate fields can't be null");
        }
        return id;
    }

    private void saveConnect(GiftCertificate giftCertificate) {
        List<String> tagName = giftCertificate.getTagList().stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        for (String name : tagName) {
            List<Tag> tags = tagService.findByParameters(name);
            CertificateTagConnecting certificateTagConnecting = new CertificateTagConnecting(giftCertificate.getId(), tags.get(0).getId());
            certificateTagConnectingMapperBatis.save(certificateTagConnecting);
        }
    }

    public String delete(int id) {
        int delete = certificateMapper.delete(id);
        String result;
        if(delete>0){
            result = "Deleted "+ delete +" certificates";
        }else{
            result = "Delete failed";
        }
        return result;
    }

    @Transactional
    public boolean update(GiftCertificate giftCertificate) throws CertificateFieldCanNotNullException {
        Timestamp date = Timestamp.from(Instant.now());
        int update = 0;
        if (validator.validate(giftCertificate)) {
            tagVerifier.checkAndSaveTagIfNotExist(giftCertificate);
            giftCertificate.setLastUpdateDate(date.toLocalDateTime());
            update = certificateMapper.update(giftCertificate);
            CertificateTagConnecting certificateTagConnecting = new CertificateTagConnecting();
            certificateTagConnecting.setCertificateId(giftCertificate.getId());
            certificateTagConnectingMapperBatis.deleteConnect(certificateTagConnecting);
            saveConnect(giftCertificate);
        } else if(giftCertificate.getId()!=0 && giftCertificate.getName()==null && giftCertificate.getPrice()!=null){
            giftCertificate.setLastUpdateDate(date.toLocalDateTime());
           update = certificateMapper.update(giftCertificate);
        }
        else {
            throw new CertificateFieldCanNotNullException("The certificate fields can't be null");
        }
        return update > 0;
    }

    private void setTagsToCertificates(List<GiftCertificate> certificates) {
        for (GiftCertificate giftCertificate : certificates) {
            CertificateTagConnecting certificateTagConnecting = new CertificateTagConnecting();
            certificateTagConnecting.setCertificateId(giftCertificate.getId());
            List<Tag> certificateTags = certificateMapper.findCertificateTags(certificateTagConnecting);
            giftCertificate.setTagList(certificateTags);
        }
    }
}