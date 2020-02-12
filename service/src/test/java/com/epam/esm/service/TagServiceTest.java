package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagDataIncorrectException;
import com.epam.esm.mapper.TagMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {
@Mock
private TagMapper tagMapper;
@Mock
private MessageSource messageSource;
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

    @Test(expected = TagDataIncorrectException.class)
    public void testSaveThrowException() {
        Tag tag = new Tag();
        tagService.save(tag, new Locale("en"));
    }

    @Test
    public void testDeleteShouldTagMapperCallDeleteMethod() {
        tagService.delete(1);
        Mockito.verify(tagMapper, Mockito.times(1)).delete(1);
    }

    @Test
    public void testFindByParameterShouldReturnTrue() {
        Tag tag = new Tag();
        tag.setName("name");
        tag.setId(1);
        List<Tag> tags = Arrays.asList(tag);
        Locale locale = new Locale("en");
        Mockito.when(tagMapper.findByParameters(tag.getName())).thenReturn(tags);
        List<Tag> byParameters = tagService.findByParameters(tag.getName());
        Assert.assertEquals(tags, byParameters);
    }
}