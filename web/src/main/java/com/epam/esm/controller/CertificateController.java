package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Parameters;
import com.epam.esm.exception.CertificateFieldCanNotNullException;
import com.epam.esm.service.CertificateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<GiftCertificate> findByParameters(@RequestParam(required = false) String name, @RequestParam(required = false) String description,
                                       @RequestParam(required = false) String tagName, @RequestParam(required = false) String sort,
                                       @RequestParam(required = false) String typeSort) {
        Parameters parameters = new Parameters(name, description, tagName, sort, typeSort);
        return service.findByParameters(parameters);
    }

    @PutMapping
    public void update(@RequestBody GiftCertificate giftCertificate) throws CertificateFieldCanNotNullException {
        service.update(giftCertificate);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody GiftCertificate giftCertificate) throws CertificateFieldCanNotNullException {
        service.save(giftCertificate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}