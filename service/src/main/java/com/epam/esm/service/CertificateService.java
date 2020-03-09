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

    /**
     * @param parameters for searching giftCertificate
     * @param tagList is tag name for searching giftCertificate
     * @return a list of certificates based on the passed parameters.
     */
    public List<GiftCertificate> findByParameters(Map<String, Object> parameters, List<String> tagList) {
        return certificateMapper.findByParameters(parameters, tagList, getRowBounds(parameters));
    }

    /**
     * Parsing a string to get Id.
     *
     * @param id of giftCertificate that you want to find
     * @return a list of certificates by Id.
     * @throws GeneralException if the string has incorrect value format.
     */
    public GiftCertificate findById(String id) {
        long certificateId = parseId(id);
        return certificateMapper.findById(certificateId);
    }

    /**
     * Set the date when the certificate was created. The creation date is equal to the last update date.
     * If new tags are passed during creation/modification – they are created in database.
     * When the certificate fields and tags are valid, we save the certificate.
     * Finally, we save a connection between the certificate and the tags.
     *
     * @param giftCertificate that you want to save
     * @return {@code true} the certificate ID.
     * @throws GeneralException if the certificate fields are not correct.
     */
    @Transactional
    public long save(GiftCertificate giftCertificate) {
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        if (validator.validate(giftCertificate)) {
            tagValidator.checkAndSaveTagIfNotExist(giftCertificate);
            certificateMapper.save(giftCertificate);
            saveConnect(giftCertificate);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_DATA_INCORRECT);
        }
        return giftCertificate.getId();
    }

    /**
     * Parse a string to get Id. Then delete  the certificate.
     *
     * @param id of giftCertificate that you want to delete
     * @throws GeneralException if the string id has incorrect value format.
     */
    public int delete(String id) {
        long certificateId = parseId(id);
        return certificateMapper.delete(certificateId);
    }

    /**
     * Parse a string to get Id.
     * Validate the certificate fields and tags.
     * We set the date of the last update and update the certificate.
     * Finally, We delete old and save new connection between the certificate and tags.
     *
     * @param id              of giftCertificate that you want to update
     * @param giftCertificate that you want to update
     * @return {@code true} the  the number of updated rows.
     * @throws GeneralException if the string id has incorrect value format.
     */
    @Transactional
    public int update(String id, GiftCertificate giftCertificate) {
        long certificateId = parseId(id);
        int update;
        if (validator.validate(giftCertificate)) {
            tagValidator.checkAndSaveTagIfNotExist(giftCertificate);
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            giftCertificate.setId(certificateId);
            update = certificateMapper.update(giftCertificate);
            certificateTagConnectingMapper.deleteConnect(certificateId);
            saveConnect(giftCertificate);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_DATA_INCORRECT);
        }
        return update;
    }

    /**
     * Parse a string to get Id.
     * Validate the certificate price.
     * We set the date of the last update and update the certificate.
     *
     * @param id              of giftCertificate to set a new price for
     * @param giftCertificate to set a new price for
     * @return {@code true} the  the number of updated rows
     * @throws GeneralException if the string id has incorrect value format
     */
    @Transactional
    public int updatePrice(String id, GiftCertificate giftCertificate) {
        long certificateId = parseId(id);
        int updatedRow;
        if (validator.validatePrice(giftCertificate)) {
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            giftCertificate.setId(certificateId);
            updatedRow = certificateMapper.updatePrice(giftCertificate);
        } else {
            throw new GeneralException(ExceptionType.CERTIFICATE_DATA_INCORRECT);
        }

        return updatedRow;
    }

    /**
     * @param giftCertificate at which to communicate with the tags
     * @return the  the number of updated rows
     */
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