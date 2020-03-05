package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GeneralException;
import com.epam.esm.mapper.TagMapper;
import org.apache.ibatis.session.RowBounds;
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
        tagService.save(tag);
        Mockito.verify(tagMapper, Mockito.times(1)).save(tag);
    }

    @Test(expected = GeneralException.class)
    public void testSaveThrowException() {
        Tag tag = new Tag();
        tagService.save(tag);
    }

    @Test
    public void testDeleteShouldTagMapperCallDeleteMethod() {
        tagService.delete("1");
        Mockito.verify(tagMapper, Mockito.times(1)).delete(1);
    }

    @Test
    public void testFindByParameterShouldReturnTrue() {
        Tag tag = new Tag();
        tag.setName("name");
        tag.setId(1L);
        List<Tag> tags = Arrays.asList(tag);
        tagService.findTagByName(tags);
        Mockito.verify(tagMapper, Mockito.times(1)).findTagByName(tags);
    }

    @Test
    public void testFindMostPopularTagShouldMapperCallMethodFindMostPopularTag() {
        tagService.findMostPopularTag();
        Mockito.verify(tagMapper, Mockito.times(1)).findMostPopularTag();
    }

    @Test
    public void testFindByParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tagName", "name");
        RowBounds mock = Mockito.mock(RowBounds.class);
        Mockito.when(tagService.getRowBounds(parameters)).thenReturn(mock);
        tagService.findByParameters(parameters);
        Mockito.verify(tagMapper, Mockito.times(1)).findByParameters((String) parameters.get("tagName"), mock);
    }

    @Test
    public void testFindById() {
        tagService.findById("1");
        Mockito.verify(tagMapper, Mockito.times(1)).findById(1);
    }
}