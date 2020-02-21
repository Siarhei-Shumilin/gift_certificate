package com.epam.esm.service;

import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import org.apache.ibatis.session.RowBounds;

import java.util.Locale;
import java.util.Map;

public class GeneralService {

    protected RowBounds getRowBounds(Map<String, Object> parameters, Locale locale) {
        int page = 1;
        int limit = 5;
        String numberPage = (String) parameters.get("page");
        String numberCertificates = (String) parameters.get("limit");
        try {
            if (numberPage != null) {
                page = Integer.parseInt((String) parameters.get("page"));
            }
            if (numberCertificates != null) {
                limit = Integer.parseInt((String) parameters.get("limit"));
            }
        } catch (NumberFormatException e) {
            throw new GeneralException(ExceptionType.INCORRECT_DATA_FORMAT, locale);
        }
        int offset = (page - 1) * limit;
        return new RowBounds(offset, limit);
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
}
