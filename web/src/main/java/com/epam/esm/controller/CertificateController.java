package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Parameters;
import com.epam.esm.exception.CertificateFieldCanNotNullException;
import com.epam.esm.service.CertificateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<GiftCertificate> findByParameters(@RequestParam(required = false) String name, @RequestParam(required = false) String description,
                                                 @RequestParam(required = false) List<String> tagName, @RequestParam(required = false) String sort,
                                                 @RequestParam(required = false) String typeSort, @RequestParam(required = false) Integer page, Locale locale) {
        Parameters parameters = new Parameters(name, description, tagName, sort, typeSort, page);
        return service.findByParameters(parameters, locale);
    }

    @PutMapping
    public boolean update(@RequestBody GiftCertificate giftCertificate, Locale locale) throws CertificateFieldCanNotNullException {
        return service.update(giftCertificate, locale);
    }

    @PostMapping
    public long save(@RequestBody GiftCertificate giftCertificate, Locale locale) throws CertificateFieldCanNotNullException {
        return service.save(giftCertificate, locale);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        return service.delete(id);
    }
}