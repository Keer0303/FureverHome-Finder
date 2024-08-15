package com.example.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NoticeService.class})
@ExtendWith(SpringExtension.class)
class NoticeServiceDiffblueTest {
    @MockBean
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeService noticeService;

    /**
     * Method under test: {@link NoticeService#add(Notice)}
     */
    @Test
    void testAdd() {
        // Arrange
        when(noticeMapper.insert(Mockito.<Notice>any())).thenReturn(1);

        Notice notice = new Notice();
        notice.setContent("Not all who wander are lost");
        notice.setId(1);
        notice.setTime("Time");
        notice.setTitle("Dr");
        notice.setUser("User");

        // Act
        noticeService.add(notice);

        // Assert
        verify(noticeMapper).insert(isA(Notice.class));
        assertNull(notice.getUser());
    }

    /**
     * Method under test: {@link NoticeService#deleteById(Integer)}
     */
    @Test
    void testDeleteById() {
        // Arrange
        when(noticeMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        // Act
        noticeService.deleteById(1);

        // Assert that nothing has changed
        verify(noticeMapper).deleteById(eq(1));
    }

    /**
     * Method under test: {@link NoticeService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch() {
        // Arrange
        when(noticeMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(2);

        // Act
        noticeService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(noticeMapper).deleteById(eq(2));
    }

    /**
     * Method under test: {@link NoticeService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch2() {
        // Arrange
        when(noticeMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        // Act
        noticeService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(noticeMapper, atLeast(1)).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link NoticeService#updateById(Notice)}
     */
    @Test
    void testUpdateById() {
        // Arrange
        when(noticeMapper.updateById(Mockito.<Notice>any())).thenReturn(1);

        Notice notice = new Notice();
        notice.setContent("Not all who wander are lost");
        notice.setId(1);
        notice.setTime("Time");
        notice.setTitle("Dr");
        notice.setUser("User");

        // Act
        noticeService.updateById(notice);

        // Assert that nothing has changed
        verify(noticeMapper).updateById(isA(Notice.class));
    }

    /**
     * Method under test: {@link NoticeService#selectById(Integer)}
     */
    @Test
    void testSelectById() {
        // Arrange
        Notice notice = new Notice();
        notice.setContent("Not all who wander are lost");
        notice.setId(1);
        notice.setTime("Time");
        notice.setTitle("Dr");
        notice.setUser("User");
        when(noticeMapper.selectById(Mockito.<Integer>any())).thenReturn(notice);

        // Act
        Notice actualSelectByIdResult = noticeService.selectById(1);

        // Assert
        verify(noticeMapper).selectById(eq(1));
        assertSame(notice, actualSelectByIdResult);
    }

    /**
     * Method under test: {@link NoticeService#selectAll(Notice)}
     */
    @Test
    void testSelectAll() {
        // Arrange
        ArrayList<Notice> noticeList = new ArrayList<>();
        when(noticeMapper.selectAll(Mockito.<Notice>any())).thenReturn(noticeList);

        Notice notice = new Notice();
        notice.setContent("Not all who wander are lost");
        notice.setId(1);
        notice.setTime("Time");
        notice.setTitle("Dr");
        notice.setUser("User");

        // Act
        List<Notice> actualSelectAllResult = noticeService.selectAll(notice);

        // Assert
        verify(noticeMapper).selectAll(isA(Notice.class));
        assertTrue(actualSelectAllResult.isEmpty());
        assertSame(noticeList, actualSelectAllResult);
    }

    /**
     * Method under test: {@link NoticeService#selectPage(Notice, Integer, Integer)}
     */
    @Test
    void testSelectPage() {
        // Arrange
        ArrayList<Notice> noticeList = new ArrayList<>();
        when(noticeMapper.selectAll(Mockito.<Notice>any())).thenReturn(noticeList);

        Notice notice = new Notice();
        notice.setContent("Not all who wander are lost");
        notice.setId(1);
        notice.setTime("Time");
        notice.setTitle("Dr");
        notice.setUser("User");

        // Act
        PageInfo<Notice> actualSelectPageResult = noticeService.selectPage(notice, 10, 3);

        // Assert
        verify(noticeMapper).selectAll(isA(Notice.class));
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
        List<Notice> list = actualSelectPageResult.getList();
        assertTrue(list.isEmpty());
        assertSame(noticeList, list);
    }

    /**
     * Method under test: {@link NoticeService#selectPage(Notice, Integer, Integer)}
     */
    @Test
    void testSelectPage2() {
        // Arrange
        Notice notice = new Notice();
        notice.setContent("Not all who wander are lost");
        notice.setId(1);
        notice.setTime("Time");
        notice.setTitle("Dr");
        notice.setUser("User");

        ArrayList<Notice> noticeList = new ArrayList<>();
        noticeList.add(notice);
        when(noticeMapper.selectAll(Mockito.<Notice>any())).thenReturn(noticeList);

        Notice notice2 = new Notice();
        notice2.setContent("Not all who wander are lost");
        notice2.setId(1);
        notice2.setTime("Time");
        notice2.setTitle("Dr");
        notice2.setUser("User");

        // Act
        PageInfo<Notice> actualSelectPageResult = noticeService.selectPage(notice2, 10, 3);

        // Assert
        verify(noticeMapper).selectAll(isA(Notice.class));
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
        assertSame(noticeList, actualSelectPageResult.getList());
        assertArrayEquals(new int[]{1}, actualSelectPageResult.getNavigatepageNums());
    }
}
