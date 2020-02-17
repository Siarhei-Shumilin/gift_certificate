package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.CertificateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
    public Set<GiftCertificate> findByParameters(@RequestParam(required = false) Map<String, Object> parameters,
                                                 @RequestParam(required = false) List<String> tagName, Locale locale) {
        return service.findByParameters(parameters, tagName, locale);
    }

    @PutMapping(value = "/{id}")
    public int update(@PathVariable long id, @RequestBody GiftCertificate giftCertificate, Locale locale) {
        return service.update(id, giftCertificate, locale);
    }

    @PutMapping(value = "/price/{id}")
    public int updatePrice(@PathVariable long id, @RequestBody GiftCertificate giftCertificate, Locale locale) {
        return service.updatePrice(id, giftCertificate, locale);
    }

    @PostMapping
    public long save(@RequestBody GiftCertificate giftCertificate, Locale locale) {
        return service.save(giftCertificate, locale);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        return service.delete(id);
    }
}