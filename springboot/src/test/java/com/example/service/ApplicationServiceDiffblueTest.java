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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.entity.Animal;
import com.example.entity.Application;
import com.example.mapper.AnimalMapper;
import com.example.mapper.ApplicationMapper;
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

@ContextConfiguration(classes = {ApplicationService.class})
@ExtendWith(SpringExtension.class)
class ApplicationServiceDiffblueTest {
    @MockBean
    private AnimalMapper animalMapper;

    @MockBean
    private ApplicationMapper applicationMapper;

    @Autowired
    private ApplicationService applicationService;

    /**
     * Method under test: {@link ApplicationService#add(Application)}
     */
    @Test
    void testAdd() {
        // Arrange
        doNothing().when(applicationMapper).insert(Mockito.<Application>any());

        Application application = new Application();
        application.setAddress("42 Main St");
        application.setAnimalId(1);
        application.setEmail("jane.doe@example.org");
        application.setId(1);
        application.setName("Name");
        application.setPhone("6625550144");
        application.setReason("Just cause");
        application.setStatus("Status");
        application.setUserId(1);

        // Act
        applicationService.add(application);

        // Assert
        verify(applicationMapper).insert(isA(Application.class));
        assertEquals("Pending", application.getStatus());
        assertNull(application.getEmail());
        assertNull(application.getName());
        assertNull(application.getPhone());
    }

    /**
     * Method under test: {@link ApplicationService#deleteById(Integer)}
     */
    @Test
    void testDeleteById() {
        // Arrange
        doNothing().when(applicationMapper).deleteByPrimaryKey(Mockito.<Integer>any());

        // Act
        applicationService.deleteById(1);

        // Assert that nothing has changed
        verify(applicationMapper).deleteByPrimaryKey(eq(1));
    }

    /**
     * Method under test: {@link ApplicationService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch() {
        // Arrange
        doNothing().when(applicationMapper).deleteByPrimaryKey(Mockito.<Integer>any());

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(2);

        // Act
        applicationService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(applicationMapper).deleteByPrimaryKey(eq(2));
    }

    /**
     * Method under test: {@link ApplicationService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch2() {
        // Arrange
        doNothing().when(applicationMapper).deleteByPrimaryKey(Mockito.<Integer>any());

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        // Act
        applicationService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(applicationMapper, atLeast(1)).deleteByPrimaryKey(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ApplicationService#updateById(Application)}
     */
    @Test
    void testUpdateById() {
        // Arrange
        doNothing().when(applicationMapper).updateByPrimaryKey(Mockito.<Application>any());

        Application application = new Application();
        application.setAddress("42 Main St");
        application.setAnimalId(1);
        application.setEmail("jane.doe@example.org");
        application.setId(1);
        application.setName("Name");
        application.setPhone("6625550144");
        application.setReason("Just cause");
        application.setStatus("Status");
        application.setUserId(1);

        // Act
        applicationService.updateById(application);

        // Assert
        verify(applicationMapper).updateByPrimaryKey(isA(Application.class));
    }

    /**
     * Method under test: {@link ApplicationService#updateById(Application)}
     */
    @Test
    void testUpdateById2() {
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
        when(animalMapper.updateById(Mockito.<Animal>any())).thenReturn(1);
        when(animalMapper.selectById(Mockito.<Integer>any())).thenReturn(animal);
        doNothing().when(applicationMapper).updateByPrimaryKey(Mockito.<Application>any());

        Application application = new Application();
        application.setAddress("42 Main St");
        application.setAnimalId(1);
        application.setEmail("jane.doe@example.org");
        application.setId(1);
        application.setName("Name");
        application.setPhone("6625550144");
        application.setReason("Just cause");
        application.setStatus("Approved");
        application.setUserId(1);
        // Act
        applicationService.updateById(application);
        // Assert
        verify(animalMapper).selectById(eq(1));
        verify(animalMapper).updateById(isA(Animal.class));
        verify(applicationMapper).updateByPrimaryKey(isA(Application.class));
    }
     /* Method under test: {@link ApplicationService#selectById(Integer)}*/
    @Test
    void testSelectById() {
        // Arrange
        Application application = new Application();
        application.setAddress("42 Main St");
        application.setAnimalId(1);
        application.setEmail("jane.doe@example.org");
        application.setId(1);
        application.setName("Name");
        application.setPhone("6625550144");
        application.setReason("Just cause");
        application.setStatus("Status");
        application.setUserId(1);
        when(applicationMapper.selectByPrimaryKey(Mockito.<Integer>any())).thenReturn(application);

        // Act
        Application actualSelectByIdResult = applicationService.selectById(1);

        // Assert
        verify(applicationMapper).selectByPrimaryKey(eq(1));
        assertSame(application, actualSelectByIdResult);
    }

    /**
     * Method under test: {@link ApplicationService#selectAll(Application)}
     */
    @Test
    void testSelectAll() {
        // Arrange
        ArrayList<Application> applicationList = new ArrayList<>();
        when(applicationMapper.selectAll(Mockito.<Application>any())).thenReturn(applicationList);

        Application application = new Application();
        application.setAddress("42 Main St");
        application.setAnimalId(1);
        application.setEmail("jane.doe@example.org");
        application.setId(1);
        application.setName("Name");
        application.setPhone("6625550144");
        application.setReason("Just cause");
        application.setStatus("Status");
        application.setUserId(1);

        // Act
        List<Application> actualSelectAllResult = applicationService.selectAll(application);

        // Assert
        verify(applicationMapper).selectAll(isA(Application.class));
        assertTrue(actualSelectAllResult.isEmpty());
        assertSame(applicationList, actualSelectAllResult);
    }

    /**
     * Method under test:
     * {@link ApplicationService#selectPage(Application, Integer, Integer)}
     */
    @Test
    void testSelectPage() {
        // Arrange
        ArrayList<Application> applicationList = new ArrayList<>();
        when(applicationMapper.selectAll(Mockito.<Application>any())).thenReturn(applicationList);

        Application application = new Application();
        application.setAddress("42 Main St");
        application.setAnimalId(1);
        application.setEmail("jane.doe@example.org");
        application.setId(1);
        application.setName("Name");
        application.setPhone("6625550144");
        application.setReason("Just cause");
        application.setStatus("Status");
        application.setUserId(1);

        // Act
        PageInfo<Application> actualSelectPageResult = applicationService.selectPage(application, 10, 3);

        // Assert
        verify(applicationMapper).selectAll(isA(Application.class));
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
        List<Application> list = actualSelectPageResult.getList();
        assertTrue(list.isEmpty());
        assertSame(applicationList, list);
    }

    /**
     * Method under test:
     * {@link ApplicationService#selectPage(Application, Integer, Integer)}
     */
    @Test
    void testSelectPage2() {
        // Arrange
        Application application = new Application();
        application.setAddress("42 Main St");
        application.setAnimalId(1);
        application.setEmail("jane.doe@example.org");
        application.setId(1);
        application.setName("Name");
        application.setPhone("6625550144");
        application.setReason("Just cause");
        application.setStatus("Status");
        application.setUserId(1);

        ArrayList<Application> applicationList = new ArrayList<>();
        applicationList.add(application);
        when(applicationMapper.selectAll(Mockito.<Application>any())).thenReturn(applicationList);

        Application application2 = new Application();
        application2.setAddress("42 Main St");
        application2.setAnimalId(1);
        application2.setEmail("jane.doe@example.org");
        application2.setId(1);
        application2.setName("Name");
        application2.setPhone("6625550144");
        application2.setReason("Just cause");
        application2.setStatus("Status");
        application2.setUserId(1);

        // Act
        PageInfo<Application> actualSelectPageResult = applicationService.selectPage(application2, 10, 3);

        // Assert
        verify(applicationMapper).selectAll(isA(Application.class));
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
        assertSame(applicationList, actualSelectPageResult.getList());
        assertArrayEquals(new int[]{1}, actualSelectPageResult.getNavigatepageNums());
    }
}
