package com.example.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.entity.Submit;
import com.example.mapper.SubmitMapper;
import com.github.pagehelper.PageInfo;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SubmitService.class})
@ExtendWith(SpringExtension.class)
class SubmitServiceDiffblueTest {
    @MockBean
    private SubmitMapper submitMapper;

    @Autowired
    private SubmitService submitService;

    /**
     * Method under test: {@link SubmitService#add(Submit)}
     */
    @Test
    void testAdd() {
        // Arrange
        when(submitMapper.insert(Mockito.<Submit>any())).thenReturn(1);

        Submit submit = new Submit();
        submit.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setId(1);
        submit.setImg("Img");
        submit.setName("Name");
        submit.setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setStatus("Status");
        submit.setTime("Time");

        // Act
        submitService.add(submit);

        // Assert
        verify(submitMapper).insert(isA(Submit.class));
    }

    /**
     * Method under test: {@link SubmitService#deleteById(Integer)}
     */
    @Test
    void testDeleteById() {
        // Arrange
        when(submitMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        // Act
        submitService.deleteById(1);

        // Assert that nothing has changed
        verify(submitMapper).deleteById(eq(1));
    }

    /**
     * Method under test: {@link SubmitService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch() {
        // Arrange
        when(submitMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(2);

        // Act
        submitService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(submitMapper).deleteById(eq(2));
    }

    /**
     * Method under test: {@link SubmitService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch2() {
        // Arrange
        when(submitMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        // Act
        submitService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(submitMapper, atLeast(1)).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link SubmitService#updateById(Submit)}
     */
    @Test
    void testUpdateById() {
        // Arrange
        when(submitMapper.updateById(Mockito.<Submit>any())).thenReturn(1);

        Submit submit = new Submit();
        submit.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setId(1);
        submit.setImg("Img");
        submit.setName("Name");
        submit.setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setStatus("Status");
        submit.setTime("Time");

        // Act
        submitService.updateById(submit);

        // Assert that nothing has changed
        verify(submitMapper).updateById(isA(Submit.class));
    }

    /**
     * Method under test: {@link SubmitService#selectById(Integer)}
     */
    @Test
    void testSelectById() {
        // Arrange
        Submit submit = new Submit();
        submit.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setId(1);
        submit.setImg("Img");
        submit.setName("Name");
        submit.setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setStatus("Status");
        submit.setTime("Time");
        when(submitMapper.selectById(Mockito.<Integer>any())).thenReturn(submit);

        // Act
        Submit actualSelectByIdResult = submitService.selectById(1);

        // Assert
        verify(submitMapper).selectById(eq(1));
        assertSame(submit, actualSelectByIdResult);
    }

    /**
     * Method under test: {@link SubmitService#selectAll(Submit)}
     */
    @Test
    void testSelectAll() {
        // Arrange
        ArrayList<Submit> submitList = new ArrayList<>();
        when(submitMapper.selectAll(Mockito.<Submit>any())).thenReturn(submitList);

        Submit submit = new Submit();
        submit.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setId(1);
        submit.setImg("Img");
        submit.setName("Name");
        submit.setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setStatus("Status");
        submit.setTime("Time");

        // Act
        List<Submit> actualSelectAllResult = submitService.selectAll(submit);

        // Assert
        verify(submitMapper).selectAll(isA(Submit.class));
        assertTrue(actualSelectAllResult.isEmpty());
        assertSame(submitList, actualSelectAllResult);
    }

    /**
     * Method under test: {@link SubmitService#selectPage(Submit, Integer, Integer)}
     */
    @Test
    void testSelectPage() {
        // Arrange
        ArrayList<Submit> submitList = new ArrayList<>();
        when(submitMapper.selectAll(Mockito.<Submit>any())).thenReturn(submitList);

        Submit submit = new Submit();
        submit.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setId(1);
        submit.setImg("Img");
        submit.setName("Name");
        submit.setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setStatus("Status");
        submit.setTime("Time");

        // Act
        PageInfo<Submit> actualSelectPageResult = submitService.selectPage(submit, 10, 3);

        // Assert
        verify(submitMapper).selectAll(isA(Submit.class));
        assertEquals(0, actualSelectPageResult.getNavigateFirstPage());
        assertEquals(0, actualSelectPageResult.getNavigateLastPage());
        assertEquals(0, actualSelectPageResult.getNextPage());
        assertEquals(0, actualSelectPageResult.getPageSize());
        assertEquals(0, actualSelectPageResult.getPages());
        assertEquals(0, actualSelectPageResult.getPrePage());
        assertEquals(0, actualSelectPageResult.getSize());
        assertEquals(0, actualSelectPageResult.getNavigatepageNums().length);
        assertEquals(0L, actualSelectPageResult.getEndRow());
        assertEquals(0L, actualSelectPageResult.getStartRow());
        assertEquals(0L, actualSelectPageResult.getTotal());
        assertEquals(1, actualSelectPageResult.getPageNum());
        assertEquals(8, actualSelectPageResult.getNavigatePages());
        assertFalse(actualSelectPageResult.hasContent());
        assertFalse(actualSelectPageResult.isHasNextPage());
        assertFalse(actualSelectPageResult.isHasPreviousPage());
        assertTrue(actualSelectPageResult.isIsFirstPage());
        assertTrue(actualSelectPageResult.isIsLastPage());
        List<Submit> list = actualSelectPageResult.getList();
        assertTrue(list.isEmpty());
        assertSame(submitList, list);
    }

    /**
     * Method under test: {@link SubmitService#selectPage(Submit, Integer, Integer)}
     */
    @Test
    void testSelectPage2() {
        // Arrange
        Submit submit = new Submit();
        submit.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setId(1);
        submit.setImg("Img");
        submit.setName("Name");
        submit.setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit.setStatus("Status");
        submit.setTime("Time");

        ArrayList<Submit> submitList = new ArrayList<>();
        submitList.add(submit);
        when(submitMapper.selectAll(Mockito.<Submit>any())).thenReturn(submitList);

        Submit submit2 = new Submit();
        submit2.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit2.setId(1);
        submit2.setImg("Img");
        submit2.setName("Name");
        submit2.setStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        submit2.setStatus("Status");
        submit2.setTime("Time");

        // Act
        PageInfo<Submit> actualSelectPageResult = submitService.selectPage(submit2, 10, 3);

        // Assert
        verify(submitMapper).selectAll(isA(Submit.class));
        assertEquals(0, actualSelectPageResult.getNextPage());
        assertEquals(0, actualSelectPageResult.getPrePage());
        assertEquals(0L, actualSelectPageResult.getEndRow());
        assertEquals(0L, actualSelectPageResult.getStartRow());
        assertEquals(1, actualSelectPageResult.getNavigateFirstPage());
        assertEquals(1, actualSelectPageResult.getNavigateLastPage());
        assertEquals(1, actualSelectPageResult.getPageNum());
        assertEquals(1, actualSelectPageResult.getPageSize());
        assertEquals(1, actualSelectPageResult.getPages());
        assertEquals(1, actualSelectPageResult.getSize());
        assertEquals(1L, actualSelectPageResult.getTotal());
        assertEquals(8, actualSelectPageResult.getNavigatePages());
        assertFalse(actualSelectPageResult.isHasNextPage());
        assertFalse(actualSelectPageResult.isHasPreviousPage());
        assertTrue(actualSelectPageResult.hasContent());
        assertTrue(actualSelectPageResult.isIsFirstPage());
        assertTrue(actualSelectPageResult.isIsLastPage());
        assertSame(submitList, actualSelectPageResult.getList());
        assertArrayEquals(new int[]{1}, actualSelectPageResult.getNavigatepageNums());
    }
}
