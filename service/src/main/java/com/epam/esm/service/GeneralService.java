package com.epam.esm.service;

import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GeneralService {

    /**
     * @return {@code true} RowBounds.
     * @throws GeneralException if the page or the page size have incorrect value format.
     */
    protected RowBounds getRowBounds(Map<String, Object> parameters) {
        RowBounds rowBounds;
        try {
            int page = getNumberPage(parameters);
            int pageSize = getPageSize(parameters);
            int offset = (page - 1) * pageSize;
            rowBounds = new RowBounds(offset, pageSize);
        } catch (NumberFormatException e) {
            throw new GeneralException(ExceptionType.INCORRECT_DATA_FORMAT);
        }
        return rowBounds;
    }

    /**
     * Parse a string to get Id.
     *
     * @throws GeneralException if the string has incorrect value format.
     */
    protected long parseId(String entityId) {
        long id;
        try {
            id = Long.parseLong(entityId);
        } catch (NumberFormatException e) {
            throw new GeneralException(ExceptionType.INCORRECT_DATA_FORMAT);
        }
        return id;
    }

    /**
     * Get the page number. By default, the page number is 1.
     * If the page parameter is not null or empty, parse the string to get the value.
     * When the resulting value is less than 1, set the default value.
     *
     * @throws GeneralException if the string has incorrect value format.
     */
    private int getNumberPage(Map<String, Object> parameters){
        int page = 1;
        String numberPage = (String) parameters.get("page");
        if (numberPage != null && !numberPage.isEmpty()) {
            int parsePage = Integer.parseInt(numberPage);
            if (parsePage > 0) {
                page = parsePage;
            }
        }
        return page;
    }

    /**
     * Getting the page size. By default, the page number is 5.
     * If the page parameter is not null or empty, parse the string to get the value.
     * When the resulting value is less than 1, set the default value.
     * If the resulting value is more than 100, set page size to 100.
     *
     * @throws GeneralException if the string has incorrect value format.
     */
    private int getPageSize(Map<String, Object> parameters){
        int pageSize = 5;
        String numberCertificates = (String) parameters.get("pageSize");
        if (numberCertificates != null && !numberCertificates.isEmpty()) {
            int parseNumberCertificates = Integer.parseInt(numberCertificates);
            if (parseNumberCertificates > 0 && parseNumberCertificates < 100) {
                pageSize = parseNumberCertificates;
            } else if (parseNumberCertificates > 100) {
                pageSize = 100;
            }
        }
        return pageSize;
    }
}