package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.CertificateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping(value = "/certificates",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CertificateController {
    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @GetMapping
    public List<GiftCertificate> findByParameters(@RequestParam(required = false) Map<String, Object> parameters,
                                                  @RequestParam(required = false) List<String> tagName) {
        return service.findByParameters(parameters, tagName);
    }

    @PutMapping
    public int update(@RequestBody GiftCertificate giftCertificate, Locale locale) throws CertificateDataIncorrectException {
        return service.update(giftCertificate, locale);
    }

    @PostMapping
    public long save(@RequestBody GiftCertificate giftCertificate, Locale locale) throws CertificateDataIncorrectException {
        return service.save(giftCertificate, locale);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        return service.delete(id);
    }
}