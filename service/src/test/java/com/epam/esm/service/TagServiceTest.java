package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.TagMapper;
import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {

    @Mock
    private TagMapper tagMapper;
    @Spy
    @InjectMocks
    private TagService tagService;

    @Test
    public void testSaveShouldTagMapperCallSave() {
        Tag tag = new Tag();
        tag.setName("name");
        tag.setId(1);
        tagService.save(tag, new Locale("en"));
        Mockito.verify(tagMapper, Mockito.times(1)).save(tag);
    }

    @Test(expected = GeneralException.class)
    public void testSaveThrowException() {
        Tag tag = new Tag();
        tagService.save(tag, new Locale("en"));
    }

    @Test
    public void testDeleteShouldTagMapperCallDeleteMethod() {
        tagService.delete("1", new Locale("en"));
        Mockito.verify(tagMapper, Mockito.times(1)).delete(1);
    }

    @Test
    public void testFindByParameterShouldReturnTrue() {
        Tag tag = new Tag();
        tag.setName("name");
        tag.setId(1);
        List<Tag> tags = Arrays.asList(tag);
        Mockito.when(tagMapper.findIdTag(tag.getName())).thenReturn(1L);
        long actualId = tagService.findIdTag(tag.getName());
        Assert.assertEquals(tag.getId(), actualId);
    }

    @Test
    public void testFindMostPopularTagShouldMapperCallMethodFindMostPopularTag() {
        tagService.findMostPopularTag();
        Mockito.verify(tagMapper, Mockito.times(1)).findMostPopularTag();
    }

    @Test
    public void findByParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tagName", "name");
        Locale locale = new Locale("en");
        RowBounds mock = Mockito.mock(RowBounds.class);
        Mockito.when(tagService.getRowBounds(parameters, locale)).thenReturn(mock);
        tagService.findByParameters(parameters, locale);
        Mockito.verify(tagMapper, Mockito.times(1)).findByParameters((String) parameters.get("tagName"), mock);
    }
}