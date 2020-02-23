package com.epam.esm.service;

import com.epam.esm.exception.GeneralException;
import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class GeneralServiceTest {

    @InjectMocks
    private GeneralService generalService;

    @Test
    public void testGetRowBoundsShouldReturnTrueWhenLimitThree() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", "1");
        map.put("limit", "3");
        RowBounds rowBounds = generalService.getRowBounds(map, new Locale("en"));
        int actual = rowBounds.getLimit();
        Assert.assertEquals(3, actual);
    }

    @Test(expected = GeneralException.class)
    public void testGetRowBoundsShouldThrowException() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", "1sd");
        map.put("limit", "3ds");
        generalService.getRowBounds(map, new Locale("en"));
    }

    @Test
    public void testParseIdShouldTrueWhenIdIsValid() {
        long id = generalService.parseId("1", new Locale("en"));
        Assert.assertEquals(1, id);
    }

    @Test(expected = GeneralException.class)
    public void testParseIdShouldThrowException() {
        generalService.parseId("1fef", new Locale("en"));
    }
}