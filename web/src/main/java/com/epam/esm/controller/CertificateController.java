package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.CertificateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
                                                 @RequestParam(required = false) List<String> tagName, Locale locale) {
        return  service.findByParameters(parameters, tagName, locale);

    }

    @PutMapping(value = "/{id}")
    public Map<String, String> update(@PathVariable long id, @RequestBody GiftCertificate giftCertificate, Locale locale) {
        Map<String, String> map = new HashMap<>();
        map.put("Message" , "Updated " + service.update(id, giftCertificate, locale) + " certificate");
        return map;
    }

    @PutMapping(value = "/price/{id}")
    public Map<String, String> updatePrice(@PathVariable long id, @RequestBody GiftCertificate giftCertificate, Locale locale) {
       Map<String, String> map = new HashMap<>();
       map.put("Message" , "Updated " + service.updatePrice(id, giftCertificate, locale) + " certificate");
       return map;
    }

    @PostMapping
    public Map<String, Long> save(@RequestBody GiftCertificate giftCertificate, Locale locale) {
        Map<String, Long> map = new HashMap<>();
        map.put("id" , service.save(giftCertificate, locale));
        return map;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable int id) {
        Map<String, String> map = new HashMap<>();
        map.put("Message" , "Deleted " + service.delete(id) + " certificate");
        return map;
    }
}