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

import com.example.entity.Room;
import com.example.mapper.RoomMapper;
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

@ContextConfiguration(classes = {RoomService.class})
@ExtendWith(SpringExtension.class)
class RoomServiceDiffblueTest {
    @MockBean
    private RoomMapper roomMapper;

    @Autowired
    private RoomService roomService;

    /**
     * Method under test: {@link RoomService#add(Room)}
     */
    @Test
    void testAdd() {
        // Arrange
        when(roomMapper.insert(Mockito.<Room>any())).thenReturn(1);

        Room room = new Room();
        room.setAnimal("Animal");
        room.setId(1);
        room.setName("Name");
        room.setStatus("Status");

        // Act
        roomService.add(room);

        // Assert
        verify(roomMapper).insert(isA(Room.class));
        assertEquals("Vacant", room.getStatus());
    }

    /**
     * Method under test: {@link RoomService#deleteById(Integer)}
     */
    @Test
    void testDeleteById() {
        // Arrange
        when(roomMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        // Act
        roomService.deleteById(1);

        // Assert that nothing has changed
        verify(roomMapper).deleteById(eq(1));
    }

    /**
     * Method under test: {@link RoomService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch() {
        // Arrange
        when(roomMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(2);

        // Act
        roomService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(roomMapper).deleteById(eq(2));
    }

    /**
     * Method under test: {@link RoomService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch2() {
        // Arrange
        when(roomMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        // Act
        roomService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(roomMapper, atLeast(1)).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link RoomService#updateById(Room)}
     */
    @Test
    void testUpdateById() {
        // Arrange
        when(roomMapper.updateById(Mockito.<Room>any())).thenReturn(1);

        Room room = new Room();
        room.setAnimal("Animal");
        room.setId(1);
        room.setName("Name");
        room.setStatus("Status");

        // Act
        roomService.updateById(room);

        // Assert that nothing has changed
        verify(roomMapper).updateById(isA(Room.class));
    }

    /**
     * Method under test: {@link RoomService#selectById(Integer)}
     */
    @Test
    void testSelectById() {
        // Arrange
        Room room = new Room();
        room.setAnimal("Animal");
        room.setId(1);
        room.setName("Name");
        room.setStatus("Status");
        when(roomMapper.selectById(Mockito.<Integer>any())).thenReturn(room);

        // Act
        Room actualSelectByIdResult = roomService.selectById(1);

        // Assert
        verify(roomMapper).selectById(eq(1));
        assertSame(room, actualSelectByIdResult);
    }

    /**
     * Method under test: {@link RoomService#selectAll(Room)}
     */
    @Test
    void testSelectAll() {
        // Arrange
        ArrayList<Room> roomList = new ArrayList<>();
        when(roomMapper.selectAll(Mockito.<Room>any())).thenReturn(roomList);

        Room room = new Room();
        room.setAnimal("Animal");
        room.setId(1);
        room.setName("Name");
        room.setStatus("Status");

        // Act
        List<Room> actualSelectAllResult = roomService.selectAll(room);

        // Assert
        verify(roomMapper).selectAll(isA(Room.class));
        assertTrue(actualSelectAllResult.isEmpty());
        assertSame(roomList, actualSelectAllResult);
    }

    /**
     * Method under test: {@link RoomService#selectPage(Room, Integer, Integer)}
     */
    @Test
    void testSelectPage() {
        // Arrange
        ArrayList<Room> roomList = new ArrayList<>();
        when(roomMapper.selectAll(Mockito.<Room>any())).thenReturn(roomList);

        Room room = new Room();
        room.setAnimal("Animal");
        room.setId(1);
        room.setName("Name");
        room.setStatus("Status");

        // Act
        PageInfo<Room> actualSelectPageResult = roomService.selectPage(room, 10, 3);

        // Assert
        verify(roomMapper).selectAll(isA(Room.class));
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
        List<Room> list = actualSelectPageResult.getList();
        assertTrue(list.isEmpty());
        assertSame(roomList, list);
    }

    /**
     * Method under test: {@link RoomService#selectPage(Room, Integer, Integer)}
     */
    @Test
    void testSelectPage2() {
        // Arrange
        Room room = new Room();
        room.setAnimal("Animal");
        room.setId(1);
        room.setName("Name");
        room.setStatus("Status");

        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room);
        when(roomMapper.selectAll(Mockito.<Room>any())).thenReturn(roomList);

        Room room2 = new Room();
        room2.setAnimal("Animal");
        room2.setId(1);
        room2.setName("Name");
        room2.setStatus("Status");

        // Act
        PageInfo<Room> actualSelectPageResult = roomService.selectPage(room2, 10, 3);

        // Assert
        verify(roomMapper).selectAll(isA(Room.class));
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
        assertSame(roomList, actualSelectPageResult.getList());
        assertArrayEquals(new int[]{1}, actualSelectPageResult.getNavigatepageNums());
    }
}
