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

import com.example.entity.Animal;
import com.example.mapper.AnimalMapper;
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

@ContextConfiguration(classes = {AnimalService.class})
@ExtendWith(SpringExtension.class)
class AnimalServiceDiffblueTest {
    @MockBean
    private AnimalMapper animalMapper;

    @Autowired
    private AnimalService animalService;

    /**
     * Method under test: {@link AnimalService#add(Animal)}
     */
    @Test
    void testAdd() {
        // Arrange
        when(animalMapper.insert(Mockito.<Animal>any())).thenReturn(1);

        Animal animal = new Animal();
        animal.setAge(1);
        animal.setDescr("Descr");
        animal.setId(1);
        animal.setImg("Img");
        animal.setName("Name");
        animal.setSex("Sex");
        animal.setSpecies("Species");
        animal.setStatus("Status");

        // Act
        animalService.add(animal);

        // Assert that nothing has changed
        verify(animalMapper).insert(isA(Animal.class));
    }

    /**
     * Method under test: {@link AnimalService#deleteById(Integer)}
     */
    @Test
    void testDeleteById() {
        // Arrange
        when(animalMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        // Act
        animalService.deleteById(1);

        // Assert that nothing has changed
        verify(animalMapper).deleteById(eq(1));
    }

    /**
     * Method under test: {@link AnimalService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch() {
        // Arrange
        when(animalMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(2);

        // Act
        animalService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(animalMapper).deleteById(eq(2));
    }

    /**
     * Method under test: {@link AnimalService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch2() {
        // Arrange
        when(animalMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        // Act
        animalService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(animalMapper, atLeast(1)).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link AnimalService#updateById(Animal)}
     */
    @Test
    void testUpdateById() {
        // Arrange
        when(animalMapper.updateById(Mockito.<Animal>any())).thenReturn(1);

        Animal animal = new Animal();
        animal.setAge(1);
        animal.setDescr("Descr");
        animal.setId(1);
        animal.setImg("Img");
        animal.setName("Name");
        animal.setSex("Sex");
        animal.setSpecies("Species");
        animal.setStatus("Status");

        // Act
        animalService.updateById(animal);

        // Assert that nothing has changed
        verify(animalMapper).updateById(isA(Animal.class));
    }

    /**
     * Method under test: {@link AnimalService#selectById(Integer)}
     */
    @Test
    void testSelectById() {
        // Arrange
        Animal animal = new Animal();
        animal.setAge(1);
        animal.setDescr("Descr");
        animal.setId(1);
        animal.setImg("Img");
        animal.setName("Name");
        animal.setSex("Sex");
        animal.setSpecies("Species");
        animal.setStatus("Status");
        when(animalMapper.selectById(Mockito.<Integer>any())).thenReturn(animal);

        // Act
        Animal actualSelectByIdResult = animalService.selectById(1);

        // Assert
        verify(animalMapper).selectById(eq(1));
        assertSame(animal, actualSelectByIdResult);
    }

    /**
     * Method under test: {@link AnimalService#selectAll(Animal)}
     */
    @Test
    void testSelectAll() {
        // Arrange
        ArrayList<Animal> animalList = new ArrayList<>();
        when(animalMapper.selectAll(Mockito.<Animal>any())).thenReturn(animalList);

        Animal animal = new Animal();
        animal.setAge(1);
        animal.setDescr("Descr");
        animal.setId(1);
        animal.setImg("Img");
        animal.setName("Name");
        animal.setSex("Sex");
        animal.setSpecies("Species");
        animal.setStatus("Status");

        // Act
        List<Animal> actualSelectAllResult = animalService.selectAll(animal);

        // Assert
        verify(animalMapper).selectAll(isA(Animal.class));
        assertTrue(actualSelectAllResult.isEmpty());
        assertSame(animalList, actualSelectAllResult);
    }

    /**
     * Method under test: {@link AnimalService#selectPage(Animal, Integer, Integer)}
     */
    @Test
    void testSelectPage() {
        // Arrange
        ArrayList<Animal> animalList = new ArrayList<>();
        when(animalMapper.selectAll(Mockito.<Animal>any())).thenReturn(animalList);

        Animal animal = new Animal();
        animal.setAge(1);
        animal.setDescr("Descr");
        animal.setId(1);
        animal.setImg("Img");
        animal.setName("Name");
        animal.setSex("Sex");
        animal.setSpecies("Species");
        animal.setStatus("Status");

        // Act
        PageInfo<Animal> actualSelectPageResult = animalService.selectPage(animal, 10, 3);

        // Assert
        verify(animalMapper).selectAll(isA(Animal.class));
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
        List<Animal> list = actualSelectPageResult.getList();
        assertTrue(list.isEmpty());
        assertSame(animalList, list);
    }

    /**
     * Method under test: {@link AnimalService#selectPage(Animal, Integer, Integer)}
     */
    @Test
    void testSelectPage2() {
        // Arrange
        Animal animal = new Animal();
        animal.setAge(1);
        animal.setDescr("Descr");
        animal.setId(1);
        animal.setImg("Img");
        animal.setName("Name");
        animal.setSex("Sex");
        animal.setSpecies("Species");
        animal.setStatus("Status");

        ArrayList<Animal> animalList = new ArrayList<>();
        animalList.add(animal);
        when(animalMapper.selectAll(Mockito.<Animal>any())).thenReturn(animalList);

        Animal animal2 = new Animal();
        animal2.setAge(1);
        animal2.setDescr("Descr");
        animal2.setId(1);
        animal2.setImg("Img");
        animal2.setName("Name");
        animal2.setSex("Sex");
        animal2.setSpecies("Species");
        animal2.setStatus("Status");

        // Act
        PageInfo<Animal> actualSelectPageResult = animalService.selectPage(animal2, 10, 3);

        // Assert
        verify(animalMapper).selectAll(isA(Animal.class));
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
        assertSame(animalList, actualSelectPageResult.getList());
        assertArrayEquals(new int[]{1}, actualSelectPageResult.getNavigatepageNums());
    }
}
