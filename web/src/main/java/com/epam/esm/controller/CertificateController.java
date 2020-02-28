package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.CertificateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/certificates")
public class CertificateController {
    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<GiftCertificate> findByParameters(@RequestParam(required = false) Map<String, Object> parameters,
                                                  @RequestParam(required = false) List<String> tagName, Locale locale) {
        return service.findByParameters(parameters, tagName, locale);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public GiftCertificate findById(@PathVariable String id, Locale locale) {
        return service.findById(id, locale);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Map<String, Integer> update(@PathVariable String id, @RequestBody GiftCertificate giftCertificate, Locale locale) {
        return Map.of("Number of updated certificates", service.update(id, giftCertificate, locale));
    }

    @PutMapping(value = "/price/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Map<String, Integer> updatePrice(@PathVariable String id, @RequestBody GiftCertificate giftCertificate, Locale locale) {
        return Map.of("Number of updated certificates", service.updatePrice(id, giftCertificate, locale));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Map<String, Long> save(@RequestBody GiftCertificate giftCertificate, Locale locale) {
        return Map.of("id", service.save(giftCertificate, locale));
    }

    @DeleteMapping("/{id}")
    public Map<String, Integer> delete(@PathVariable String id, Locale locale) {
        return Map.of("Number of deleted certificates", service.delete(id, locale));
    }
}