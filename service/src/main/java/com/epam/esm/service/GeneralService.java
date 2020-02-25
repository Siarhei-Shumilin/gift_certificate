package com.epam.esm.service;

import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

@Service
public class GeneralService {

    protected RowBounds getRowBounds(Map<String, Object> parameters, Locale locale) {
        int page = 1;
        int pageSize = 5;
        String numberPage = (String) parameters.get("page");
        String numberCertificates = (String) parameters.get("pageSize");
        try {
            if (numberPage != null) {
                int parsePage = Integer.parseInt(numberPage);
                page = validatePage(parsePage);
            }
            if (numberCertificates != null) {
                int parseNumberCertificates = Integer.parseInt(numberCertificates);
                pageSize = validatePageSize(parseNumberCertificates);
            }
        } catch (NumberFormatException e) {
            throw new GeneralException(ExceptionType.INCORRECT_DATA_FORMAT, locale);
        }
        int offset = (page - 1) * pageSize;
        return new RowBounds(offset, pageSize);
    }

    protected long parseId(String entityId, Locale locale) {
        long id;
        try {
            id = Long.parseLong(entityId);
        } catch (NumberFormatException e) {
            throw new GeneralException(ExceptionType.INCORRECT_DATA_FORMAT, locale);
        }
        return id;
    }

    private int validatePageSize(int parseNumberCertificates) {
        int pageSize = 5;
        if (parseNumberCertificates > 0 && parseNumberCertificates < 100) {
            pageSize = parseNumberCertificates;
        } else if (parseNumberCertificates > 100) {
            pageSize = 100;
        }
        return pageSize;
    }

    private int validatePage(int parsePage) {
        int page = 1;
        if (parsePage > 0) {
            page = parsePage;
        }
        return page;
    }
}