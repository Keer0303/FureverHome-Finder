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

import com.example.entity.Adopt;
import com.example.entity.Animal;
import com.example.entity.User;
import com.example.mapper.AdoptMapper;
import com.example.mapper.AnimalMapper;
import com.example.mapper.UserMapper;
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

@ContextConfiguration(classes = {AdoptService.class})
@ExtendWith(SpringExtension.class)
class AdoptServiceDiffblueTest {
    @MockBean
    private AdoptMapper adoptMapper;

    @Autowired
    private AdoptService adoptService;

    @MockBean
    private AnimalMapper animalMapper;

    @MockBean
    private UserMapper userMapper;

    /**
     * Method under test: {@link AdoptService#add(Adopt)}
     */
    @Test
    void testAdd() {
        // Arrange
        when(adoptMapper.insert(Mockito.<Adopt>any())).thenReturn(1);

        Adopt adopt = new Adopt();
        adopt.setAnimalId(1);
        adopt.setAnimalImg("Animal Img");
        adopt.setAnimalName("Animal Name");
        adopt.setId(1);
        adopt.setStatus("Status");
        adopt.setTime("Time");
        adopt.setUserId(1);
        adopt.setUserName("janedoe");

        // Act
        adoptService.add(adopt);

        // Assert
        verify(adoptMapper).insert(isA(Adopt.class));
        assertEquals("Adopted", adopt.getStatus());
    }

    /**
     * Method under test: {@link AdoptService#deleteById(Integer)}
     */
    @Test
    void testDeleteById() {
        // Arrange
        when(adoptMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        // Act
        adoptService.deleteById(1);

        // Assert that nothing has changed
        verify(adoptMapper).deleteById(eq(1));
    }

    /**
     * Method under test: {@link AdoptService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch() {
        // Arrange
        when(adoptMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(2);

        // Act
        adoptService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(adoptMapper).deleteById(eq(2));
    }

    /**
     * Method under test: {@link AdoptService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch2() {
        // Arrange
        when(adoptMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        // Act
        adoptService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(adoptMapper, atLeast(1)).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link AdoptService#updateById(Adopt)}
     */
    @Test
    void testUpdateById() {
        // Arrange
        when(adoptMapper.updateById(Mockito.<Adopt>any())).thenReturn(1);

        Adopt adopt = new Adopt();
        adopt.setAnimalId(1);
        adopt.setAnimalImg("Animal Img");
        adopt.setAnimalName("Animal Name");
        adopt.setId(1);
        adopt.setStatus("Status");
        adopt.setTime("Time");
        adopt.setUserId(1);
        adopt.setUserName("janedoe");

        // Act
        adoptService.updateById(adopt);

        // Assert that nothing has changed
        verify(adoptMapper).updateById(isA(Adopt.class));
    }

    /**
     * Method under test: {@link AdoptService#updateById(Adopt)}
     */
    @Test
    void testUpdateById2() {
        // Arrange
        when(adoptMapper.updateById(Mockito.<Adopt>any())).thenReturn(1);

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

        Adopt adopt = new Adopt();
        adopt.setAnimalId(1);
        adopt.setAnimalImg("Animal Img");
        adopt.setAnimalName("Animal Name");
        adopt.setId(1);
        adopt.setStatus("GiveUp Adoption");
        adopt.setTime("Time");
        adopt.setUserId(1);
        adopt.setUserName("janedoe");

        // Act
        adoptService.updateById(adopt);

        // Assert
        verify(adoptMapper).updateById(isA(Adopt.class));
        verify(animalMapper).selectById(eq(1));
        verify(animalMapper).updateById(isA(Animal.class));
    }

    /**
     * Method under test: {@link AdoptService#selectById(Integer)}
     */
    @Test
    void testSelectById() {
        // Arrange
        Adopt adopt = new Adopt();
        adopt.setAnimalId(1);
        adopt.setAnimalImg("Animal Img");
        adopt.setAnimalName("Animal Name");
        adopt.setId(1);
        adopt.setStatus("Status");
        adopt.setTime("Time");
        adopt.setUserId(1);
        adopt.setUserName("janedoe");
        when(adoptMapper.selectById(Mockito.<Integer>any())).thenReturn(adopt);

        // Act
        Adopt actualSelectByIdResult = adoptService.selectById(1);

        // Assert
        verify(adoptMapper).selectById(eq(1));
        assertSame(adopt, actualSelectByIdResult);
    }

    /**
     * Method under test: {@link AdoptService#selectAll(Adopt)}
     */
    @Test
    void testSelectAll() {
        // Arrange
        ArrayList<Adopt> adoptList = new ArrayList<>();
        when(adoptMapper.selectAll(Mockito.<Adopt>any())).thenReturn(adoptList);

        Adopt adopt = new Adopt();
        adopt.setAnimalId(1);
        adopt.setAnimalImg("Animal Img");
        adopt.setAnimalName("Animal Name");
        adopt.setId(1);
        adopt.setStatus("Status");
        adopt.setTime("Time");
        adopt.setUserId(1);
        adopt.setUserName("janedoe");

        // Act
        List<Adopt> actualSelectAllResult = adoptService.selectAll(adopt);

        // Assert
        verify(adoptMapper).selectAll(isA(Adopt.class));
        assertTrue(actualSelectAllResult.isEmpty());
        assertSame(adoptList, actualSelectAllResult);
    }

    /**
     * Method under test: {@link AdoptService#selectPage(Adopt, Integer, Integer)}
     */
    @Test
    void testSelectPage() {
        // Arrange
        ArrayList<Adopt> adoptList = new ArrayList<>();
        when(adoptMapper.selectAll(Mockito.<Adopt>any())).thenReturn(adoptList);

        Adopt adopt = new Adopt();
        adopt.setAnimalId(1);
        adopt.setAnimalImg("Animal Img");
        adopt.setAnimalName("Animal Name");
        adopt.setId(1);
        adopt.setStatus("Status");
        adopt.setTime("Time");
        adopt.setUserId(1);
        adopt.setUserName("janedoe");

        // Act
        PageInfo<Adopt> actualSelectPageResult = adoptService.selectPage(adopt, 10, 3);

        // Assert
        verify(adoptMapper).selectAll(isA(Adopt.class));
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
        List<Adopt> list = actualSelectPageResult.getList();
        assertTrue(list.isEmpty());
        assertSame(adoptList, list);
    }

    /**
     * Method under test: {@link AdoptService#selectPage(Adopt, Integer, Integer)}
     */
    @Test
    void testSelectPage2() {
        // Arrange
        Adopt adopt = new Adopt();
        adopt.setAnimalId(1);
        adopt.setAnimalImg("Error getting current user information");
        adopt.setAnimalName("Error getting current user information");
        adopt.setId(1);
        adopt.setStatus("Error getting current user information");
        adopt.setTime("Error getting current user information");
        adopt.setUserId(1);
        adopt.setUserName("janedoe");

        ArrayList<Adopt> adoptList = new ArrayList<>();
        adoptList.add(adopt);
        when(adoptMapper.selectAll(Mockito.<Adopt>any())).thenReturn(adoptList);

        Adopt adopt2 = new Adopt();
        adopt2.setAnimalId(1);
        adopt2.setAnimalImg("Animal Img");
        adopt2.setAnimalName("Animal Name");
        adopt2.setId(1);
        adopt2.setStatus("Status");
        adopt2.setTime("Time");
        adopt2.setUserId(1);
        adopt2.setUserName("janedoe");

        // Act
        PageInfo<Adopt> actualSelectPageResult = adoptService.selectPage(adopt2, 10, 3);

        // Assert
        verify(adoptMapper).selectAll(isA(Adopt.class));
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
        assertSame(adoptList, actualSelectPageResult.getList());
        assertArrayEquals(new int[]{1}, actualSelectPageResult.getNavigatepageNums());
    }

    /**
     * Method under test: {@link AdoptService#selectAnimal()}
     */
    @Test
    void testSelectAnimal() {
        // Arrange
        when(adoptMapper.selectAnimal()).thenReturn(new ArrayList<>());

        // Act
        List<Animal> actualSelectAnimalResult = adoptService.selectAnimal();

        // Assert
        verify(adoptMapper).selectAnimal();
        assertTrue(actualSelectAnimalResult.isEmpty());
    }

    /**
     * Method under test: {@link AdoptService#selectAnimal()}
     */
    @Test
    void testSelectAnimal2() {
        // Arrange
        Adopt adopt = new Adopt();
        adopt.setAnimalId(1);
        adopt.setAnimalImg("Animal Img");
        adopt.setAnimalName("Animal Name");
        adopt.setId(1);
        adopt.setStatus("Status");
        adopt.setTime("Time");
        adopt.setUserId(1);
        adopt.setUserName("janedoe");

        ArrayList<Adopt> adoptList = new ArrayList<>();
        adoptList.add(adopt);
        when(adoptMapper.selectAnimal()).thenReturn(adoptList);

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
        List<Animal> actualSelectAnimalResult = adoptService.selectAnimal();

        // Assert
        verify(adoptMapper).selectAnimal();
        verify(animalMapper).selectById(eq(1));
        assertEquals(1, actualSelectAnimalResult.size());
        assertSame(animal, actualSelectAnimalResult.get(0));
    }

    /**
     * Method under test: {@link AdoptService#selectAdopter()}
     */
    @Test
    void testSelectAdopter() {
        // Arrange
        when(adoptMapper.selectAdopter()).thenReturn(new ArrayList<>());

        // Act
        List<User> actualSelectAdopterResult = adoptService.selectAdopter();

        // Assert
        verify(adoptMapper).selectAdopter();
        assertTrue(actualSelectAdopterResult.isEmpty());
    }

    /**
     * Method under test: {@link AdoptService#selectAdopter()}
     */
    @Test
    void testSelectAdopter2() {
        // Arrange
        Adopt adopt = new Adopt();
        adopt.setAnimalId(1);
        adopt.setAnimalImg("Animal Img");
        adopt.setAnimalName("Animal Name");
        adopt.setId(1);
        adopt.setStatus("Status");
        adopt.setTime("Time");
        adopt.setUserId(1);
        adopt.setUserName("janedoe");

        ArrayList<Adopt> adoptList = new ArrayList<>();
        adoptList.add(adopt);
        when(adoptMapper.selectAdopter()).thenReturn(adoptList);

        User user = new User();
        user.setAccount(10.0d);
        user.setAvatar("Avatar");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setName("Name");
        user.setNewPassword("iloveyou");
        user.setPassword("iloveyou");
        user.setPhone("6625550144");
        user.setRole("Role");
        user.setToken("ABC123");
        user.setUsername("janedoe");
        when(userMapper.selectById(Mockito.<Integer>any())).thenReturn(user);

        // Act
        List<User> actualSelectAdopterResult = adoptService.selectAdopter();

        // Assert
        verify(adoptMapper).selectAdopter();
        verify(userMapper).selectById(eq(1));
        assertEquals(1, actualSelectAdopterResult.size());
        assertSame(user, actualSelectAdopterResult.get(0));
    }
}
