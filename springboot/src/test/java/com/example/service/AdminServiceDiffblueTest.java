package com.example.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
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

@ContextConfiguration(classes = {AdminService.class})
@ExtendWith(SpringExtension.class)
class AdminServiceDiffblueTest {
    @MockBean
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    /**
     * Method under test: {@link AdminService#add(Admin)}
     */
    @Test
    void testAdd() {
        // Arrange
        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");
        when(adminMapper.selectByUsername(Mockito.<String>any())).thenReturn(admin);

        Admin admin2 = new Admin();
        admin2.setAvatar("Avatar");
        admin2.setEmail("jane.doe@example.org");
        admin2.setId(1);
        admin2.setName("Name");
        admin2.setNewPassword("iloveyou");
        admin2.setPassword("iloveyou");
        admin2.setPhone("6625550144");
        admin2.setRole("Role");
        admin2.setToken("ABC123");
        admin2.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.add(admin2));
        verify(adminMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link AdminService#add(Admin)}
     */
    @Test
    void testAdd2() {
        // Arrange
        when(adminMapper.selectByUsername(Mockito.<String>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.add(admin));
        verify(adminMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link AdminService#deleteById(Integer)}
     */
    @Test
    void testDeleteById() {
        // Arrange
        when(adminMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        // Act
        adminService.deleteById(1);

        // Assert that nothing has changed
        verify(adminMapper).deleteById(eq(1));
    }

    /**
     * Method under test: {@link AdminService#deleteById(Integer)}
     */
    @Test
    void testDeleteById2() {
        // Arrange
        when(adminMapper.deleteById(Mockito.<Integer>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.deleteById(1));
        verify(adminMapper).deleteById(eq(1));
    }

    /**
     * Method under test: {@link AdminService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch() {
        // Arrange
        when(adminMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(2);

        // Act
        adminService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(adminMapper).deleteById(eq(2));
    }

    /**
     * Method under test: {@link AdminService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch2() {
        // Arrange
        when(adminMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        // Act
        adminService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(adminMapper, atLeast(1)).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link AdminService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch3() {
        // Arrange
        when(adminMapper.deleteById(Mockito.<Integer>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(2);

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.deleteBatch(ids));
        verify(adminMapper).deleteById(eq(2));
    }

    /**
     * Method under test: {@link AdminService#updateById(Admin)}
     */
    @Test
    void testUpdateById() {
        // Arrange
        when(adminMapper.updateById(Mockito.<Admin>any())).thenReturn(1);

        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");

        // Act
        adminService.updateById(admin);

        // Assert that nothing has changed
        verify(adminMapper).updateById(isA(Admin.class));
    }

    /**
     * Method under test: {@link AdminService#updateById(Admin)}
     */
    @Test
    void testUpdateById2() {
        // Arrange
        when(adminMapper.updateById(Mockito.<Admin>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.updateById(admin));
        verify(adminMapper).updateById(isA(Admin.class));
    }

    /**
     * Method under test: {@link AdminService#selectById(Integer)}
     */
    @Test
    void testSelectById() {
        // Arrange
        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");
        when(adminMapper.selectById(Mockito.<Integer>any())).thenReturn(admin);

        // Act
        Admin actualSelectByIdResult = adminService.selectById(1);

        // Assert
        verify(adminMapper).selectById(eq(1));
        assertSame(admin, actualSelectByIdResult);
    }

    /**
     * Method under test: {@link AdminService#selectById(Integer)}
     */
    @Test
    void testSelectById2() {
        // Arrange
        when(adminMapper.selectById(Mockito.<Integer>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.selectById(1));
        verify(adminMapper).selectById(eq(1));
    }

    /**
     * Method under test: {@link AdminService#selectAll(Admin)}
     */
    @Test
    void testSelectAll() {
        // Arrange
        ArrayList<Admin> adminList = new ArrayList<>();
        when(adminMapper.selectAll(Mockito.<Admin>any())).thenReturn(adminList);

        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");

        // Act
        List<Admin> actualSelectAllResult = adminService.selectAll(admin);

        // Assert
        verify(adminMapper).selectAll(isA(Admin.class));
        assertTrue(actualSelectAllResult.isEmpty());
        assertSame(adminList, actualSelectAllResult);
    }

    /**
     * Method under test: {@link AdminService#selectAll(Admin)}
     */
    @Test
    void testSelectAll2() {
        // Arrange
        when(adminMapper.selectAll(Mockito.<Admin>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.selectAll(admin));
        verify(adminMapper).selectAll(isA(Admin.class));
    }

    /**
     * Method under test: {@link AdminService#selectPage(Admin, Integer, Integer)}
     */
    @Test
    void testSelectPage() {
        // Arrange
        ArrayList<Admin> adminList = new ArrayList<>();
        when(adminMapper.selectAll(Mockito.<Admin>any())).thenReturn(adminList);

        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");

        // Act
        PageInfo<Admin> actualSelectPageResult = adminService.selectPage(admin, 10, 3);

        // Assert
        verify(adminMapper).selectAll(isA(Admin.class));
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
        List<Admin> list = actualSelectPageResult.getList();
        assertTrue(list.isEmpty());
        assertSame(adminList, list);
    }

    /**
     * Method under test: {@link AdminService#selectPage(Admin, Integer, Integer)}
     */
    @Test
    void testSelectPage2() {
        // Arrange
        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");

        ArrayList<Admin> adminList = new ArrayList<>();
        adminList.add(admin);
        when(adminMapper.selectAll(Mockito.<Admin>any())).thenReturn(adminList);

        Admin admin2 = new Admin();
        admin2.setAvatar("Avatar");
        admin2.setEmail("jane.doe@example.org");
        admin2.setId(1);
        admin2.setName("Name");
        admin2.setNewPassword("iloveyou");
        admin2.setPassword("iloveyou");
        admin2.setPhone("6625550144");
        admin2.setRole("Role");
        admin2.setToken("ABC123");
        admin2.setUsername("janedoe");

        // Act
        PageInfo<Admin> actualSelectPageResult = adminService.selectPage(admin2, 10, 3);

        // Assert
        verify(adminMapper).selectAll(isA(Admin.class));
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
        assertSame(adminList, actualSelectPageResult.getList());
        assertArrayEquals(new int[]{1}, actualSelectPageResult.getNavigatepageNums());
    }

    /**
     * Method under test: {@link AdminService#selectPage(Admin, Integer, Integer)}
     */
    @Test
    void testSelectPage3() {
        // Arrange
        when(adminMapper.selectAll(Mockito.<Admin>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.selectPage(admin, 10, 3));
        verify(adminMapper).selectAll(isA(Admin.class));
    }

    /**
     * Method under test: {@link AdminService#login(Account)}
     */
    @Test
    void testLogin() {
        // Arrange
        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");
        when(adminMapper.selectByUsername(Mockito.<String>any())).thenReturn(admin);

        Account account = new Account();
        account.setAvatar("Avatar");
        account.setEmail("jane.doe@example.org");
        account.setId(1);
        account.setName("Name");
        account.setNewPassword("iloveyou");
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRole("Role");
        account.setToken("ABC123");
        account.setUsername("janedoe");

        // Act
        Account actualLoginResult = adminService.login(account);

        // Assert
        verify(adminMapper).selectByUsername(eq("janedoe"));
        assertSame(admin, actualLoginResult);
    }

    /**
     * Method under test: {@link AdminService#login(Account)}
     */
    @Test
    void testLogin2() {
        // Arrange
        Admin admin = mock(Admin.class);
        when(admin.getId()).thenReturn(1);
        when(admin.getPassword()).thenReturn("iloveyou");
        doNothing().when(admin).setNewPassword(Mockito.<String>any());
        doNothing().when(admin).setToken(Mockito.<String>any());
        doNothing().when(admin).setAvatar(Mockito.<String>any());
        doNothing().when(admin).setEmail(Mockito.<String>any());
        doNothing().when(admin).setId(Mockito.<Integer>any());
        doNothing().when(admin).setName(Mockito.<String>any());
        doNothing().when(admin).setPassword(Mockito.<String>any());
        doNothing().when(admin).setPhone(Mockito.<String>any());
        doNothing().when(admin).setRole(Mockito.<String>any());
        doNothing().when(admin).setUsername(Mockito.<String>any());
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");
        when(adminMapper.selectByUsername(Mockito.<String>any())).thenReturn(admin);

        Account account = new Account();
        account.setAvatar("Avatar");
        account.setEmail("jane.doe@example.org");
        account.setId(1);
        account.setName("Name");
        account.setNewPassword("iloveyou");
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRole("Role");
        account.setToken("ABC123");
        account.setUsername("janedoe");

        // Act
        adminService.login(account);

        // Assert
        verify(admin).setNewPassword(eq("iloveyou"));
        verify(admin, atLeast(1)).setToken(Mockito.<String>any());
        verify(admin).getId();
        verify(admin, atLeast(1)).getPassword();
        verify(admin).setAvatar(eq("Avatar"));
        verify(admin).setEmail(eq("jane.doe@example.org"));
        verify(admin).setId(eq(1));
        verify(admin).setName(eq("Name"));
        verify(admin).setPassword(eq("iloveyou"));
        verify(admin).setPhone(eq("6625550144"));
        verify(admin).setRole(eq("Role"));
        verify(admin).setUsername(eq("janedoe"));
        verify(adminMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link AdminService#login(Account)}
     */
    @Test
    void testLogin3() {
        // Arrange
        Admin admin = mock(Admin.class);
        when(admin.getId()).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));
        when(admin.getPassword()).thenReturn("iloveyou");
        doNothing().when(admin).setNewPassword(Mockito.<String>any());
        doNothing().when(admin).setToken(Mockito.<String>any());
        doNothing().when(admin).setAvatar(Mockito.<String>any());
        doNothing().when(admin).setEmail(Mockito.<String>any());
        doNothing().when(admin).setId(Mockito.<Integer>any());
        doNothing().when(admin).setName(Mockito.<String>any());
        doNothing().when(admin).setPassword(Mockito.<String>any());
        doNothing().when(admin).setPhone(Mockito.<String>any());
        doNothing().when(admin).setRole(Mockito.<String>any());
        doNothing().when(admin).setUsername(Mockito.<String>any());
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");
        when(adminMapper.selectByUsername(Mockito.<String>any())).thenReturn(admin);

        Account account = new Account();
        account.setAvatar("Avatar");
        account.setEmail("jane.doe@example.org");
        account.setId(1);
        account.setName("Name");
        account.setNewPassword("iloveyou");
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRole("Role");
        account.setToken("ABC123");
        account.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.login(account));
        verify(admin).setNewPassword(eq("iloveyou"));
        verify(admin).setToken(eq("ABC123"));
        verify(admin).getId();
        verify(admin).getPassword();
        verify(admin).setAvatar(eq("Avatar"));
        verify(admin).setEmail(eq("jane.doe@example.org"));
        verify(admin).setId(eq(1));
        verify(admin).setName(eq("Name"));
        verify(admin).setPassword(eq("iloveyou"));
        verify(admin).setPhone(eq("6625550144"));
        verify(admin).setRole(eq("Role"));
        verify(admin).setUsername(eq("janedoe"));
        verify(adminMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link AdminService#login(Account)}
     */
    @Test
    void testLogin4() {
        // Arrange
        Admin admin = mock(Admin.class);
        when(admin.getPassword()).thenReturn("-");
        doNothing().when(admin).setNewPassword(Mockito.<String>any());
        doNothing().when(admin).setToken(Mockito.<String>any());
        doNothing().when(admin).setAvatar(Mockito.<String>any());
        doNothing().when(admin).setEmail(Mockito.<String>any());
        doNothing().when(admin).setId(Mockito.<Integer>any());
        doNothing().when(admin).setName(Mockito.<String>any());
        doNothing().when(admin).setPassword(Mockito.<String>any());
        doNothing().when(admin).setPhone(Mockito.<String>any());
        doNothing().when(admin).setRole(Mockito.<String>any());
        doNothing().when(admin).setUsername(Mockito.<String>any());
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");
        when(adminMapper.selectByUsername(Mockito.<String>any())).thenReturn(admin);

        Account account = new Account();
        account.setAvatar("Avatar");
        account.setEmail("jane.doe@example.org");
        account.setId(1);
        account.setName("Name");
        account.setNewPassword("iloveyou");
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRole("Role");
        account.setToken("ABC123");
        account.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.login(account));
        verify(admin).setNewPassword(eq("iloveyou"));
        verify(admin).setToken(eq("ABC123"));
        verify(admin).getPassword();
        verify(admin).setAvatar(eq("Avatar"));
        verify(admin).setEmail(eq("jane.doe@example.org"));
        verify(admin).setId(eq(1));
        verify(admin).setName(eq("Name"));
        verify(admin).setPassword(eq("iloveyou"));
        verify(admin).setPhone(eq("6625550144"));
        verify(admin).setRole(eq("Role"));
        verify(admin).setUsername(eq("janedoe"));
        verify(adminMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link AdminService#register(Account)}
     */
    @Test
    void testRegister() {
        // Arrange
        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");
        when(adminMapper.selectByUsername(Mockito.<String>any())).thenReturn(admin);

        Account account = new Account();
        account.setAvatar("Avatar");
        account.setEmail("jane.doe@example.org");
        account.setId(1);
        account.setName("Name");
        account.setNewPassword("iloveyou");
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRole("Role");
        account.setToken("ABC123");
        account.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.register(account));
        verify(adminMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link AdminService#register(Account)}
     */
    @Test
    void testRegister2() {
        // Arrange
        when(adminMapper.selectByUsername(Mockito.<String>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

        Account account = new Account();
        account.setAvatar("Avatar");
        account.setEmail("jane.doe@example.org");
        account.setId(1);
        account.setName("Name");
        account.setNewPassword("iloveyou");
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRole("Role");
        account.setToken("ABC123");
        account.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.register(account));
        verify(adminMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link AdminService#updatePassword(Account)}
     */
    @Test
    void testUpdatePassword() {
        // Arrange
        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");
        when(adminMapper.updateById(Mockito.<Admin>any())).thenReturn(1);
        when(adminMapper.selectByUsername(Mockito.<String>any())).thenReturn(admin);

        Account account = new Account();
        account.setAvatar("Avatar");
        account.setEmail("jane.doe@example.org");
        account.setId(1);
        account.setName("Name");
        account.setNewPassword("iloveyou");
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRole("Role");
        account.setToken("ABC123");
        account.setUsername("janedoe");

        // Act
        adminService.updatePassword(account);

        // Assert
        verify(adminMapper).selectByUsername(eq("janedoe"));
        verify(adminMapper).updateById(isA(Admin.class));
    }

    /**
     * Method under test: {@link AdminService#updatePassword(Account)}
     */
    @Test
    void testUpdatePassword2() {
        // Arrange
        Admin admin = new Admin();
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");
        when(adminMapper.updateById(Mockito.<Admin>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));
        when(adminMapper.selectByUsername(Mockito.<String>any())).thenReturn(admin);

        Account account = new Account();
        account.setAvatar("Avatar");
        account.setEmail("jane.doe@example.org");
        account.setId(1);
        account.setName("Name");
        account.setNewPassword("iloveyou");
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRole("Role");
        account.setToken("ABC123");
        account.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.updatePassword(account));
        verify(adminMapper).selectByUsername(eq("janedoe"));
        verify(adminMapper).updateById(isA(Admin.class));
    }

    /**
     * Method under test: {@link AdminService#updatePassword(Account)}
     */
    @Test
    void testUpdatePassword3() {
        // Arrange
        Admin admin = mock(Admin.class);
        when(admin.getPassword()).thenReturn("foo");
        doNothing().when(admin).setNewPassword(Mockito.<String>any());
        doNothing().when(admin).setToken(Mockito.<String>any());
        doNothing().when(admin).setAvatar(Mockito.<String>any());
        doNothing().when(admin).setEmail(Mockito.<String>any());
        doNothing().when(admin).setId(Mockito.<Integer>any());
        doNothing().when(admin).setName(Mockito.<String>any());
        doNothing().when(admin).setPassword(Mockito.<String>any());
        doNothing().when(admin).setPhone(Mockito.<String>any());
        doNothing().when(admin).setRole(Mockito.<String>any());
        doNothing().when(admin).setUsername(Mockito.<String>any());
        admin.setAvatar("Avatar");
        admin.setEmail("jane.doe@example.org");
        admin.setId(1);
        admin.setName("Name");
        admin.setNewPassword("iloveyou");
        admin.setPassword("iloveyou");
        admin.setPhone("6625550144");
        admin.setRole("Role");
        admin.setToken("ABC123");
        admin.setUsername("janedoe");
        when(adminMapper.selectByUsername(Mockito.<String>any())).thenReturn(admin);

        Account account = new Account();
        account.setAvatar("Avatar");
        account.setEmail("jane.doe@example.org");
        account.setId(1);
        account.setName("Name");
        account.setNewPassword("iloveyou");
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRole("Role");
        account.setToken("ABC123");
        account.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> adminService.updatePassword(account));
        verify(admin).setNewPassword(eq("iloveyou"));
        verify(admin).setToken(eq("ABC123"));
        verify(admin).getPassword();
        verify(admin).setAvatar(eq("Avatar"));
        verify(admin).setEmail(eq("jane.doe@example.org"));
        verify(admin).setId(eq(1));
        verify(admin).setName(eq("Name"));
        verify(admin).setPassword(eq("iloveyou"));
        verify(admin).setPhone(eq("6625550144"));
        verify(admin).setRole(eq("Role"));
        verify(admin).setUsername(eq("janedoe"));
        verify(adminMapper).selectByUsername(eq("janedoe"));
    }

}
