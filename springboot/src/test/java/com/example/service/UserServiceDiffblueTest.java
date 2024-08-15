package com.example.service;

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
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceDiffblueTest {
    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#add(User)}
     */
    @Test
    void testAdd() {
        // Arrange
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
        when(userMapper.selectByUsername(Mockito.<String>any())).thenReturn(user);

        User user2 = new User();
        user2.setAccount(10.0d);
        user2.setAvatar("Avatar");
        user2.setEmail("jane.doe@example.org");
        user2.setId(1);
        user2.setName("Name");
        user2.setNewPassword("iloveyou");
        user2.setPassword("iloveyou");
        user2.setPhone("6625550144");
        user2.setRole("Role");
        user2.setToken("ABC123");
        user2.setUsername("janedoe");

        // Act and Assert
        assertThrows(CustomException.class, () -> userService.add(user2));
        verify(userMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link UserService#add(User)}
     */
    @Test
    void testAdd2() {
        // Arrange
        when(userMapper.selectByUsername(Mockito.<String>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

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

        // Act and Assert
        assertThrows(CustomException.class, () -> userService.add(user));
        verify(userMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link UserService#deleteById(Integer)}
     */
    @Test
    void testDeleteById() {
        // Arrange
        when(userMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        // Act
        userService.deleteById(1);

        // Assert that nothing has changed
        verify(userMapper).deleteById(eq(1));
    }

    /**
     * Method under test: {@link UserService#deleteById(Integer)}
     */
    @Test
    void testDeleteById2() {
        // Arrange
        when(userMapper.deleteById(Mockito.<Integer>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

        // Act and Assert
        assertThrows(CustomException.class, () -> userService.deleteById(1));
        verify(userMapper).deleteById(eq(1));
    }

    /**
     * Method under test: {@link UserService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch() {
        // Arrange
        when(userMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(2);

        // Act
        userService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(userMapper).deleteById(eq(2));
    }

    /**
     * Method under test: {@link UserService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch2() {
        // Arrange
        when(userMapper.deleteById(Mockito.<Integer>any())).thenReturn(1);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        // Act
        userService.deleteBatch(ids);

        // Assert that nothing has changed
        verify(userMapper, atLeast(1)).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link UserService#deleteBatch(List)}
     */
    @Test
    void testDeleteBatch3() {
        // Arrange
        when(userMapper.deleteById(Mockito.<Integer>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(2);

        // Act and Assert
        assertThrows(CustomException.class, () -> userService.deleteBatch(ids));
        verify(userMapper).deleteById(eq(2));
    }

    /**
     * Method under test: {@link UserService#selectAll(User)}
     */
    @Test
    void testSelectAll() {
        // Arrange
        ArrayList<User> userList = new ArrayList<>();
        when(userMapper.selectAll(Mockito.<User>any())).thenReturn(userList);

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

        // Act
        List<User> actualSelectAllResult = userService.selectAll(user);

        // Assert
        verify(userMapper).selectAll(isA(User.class));
        assertTrue(actualSelectAllResult.isEmpty());
        assertSame(userList, actualSelectAllResult);
    }

    /**
     * Method under test: {@link UserService#selectAll(User)}
     */
    @Test
    void testSelectAll2() {
        // Arrange
        when(userMapper.selectAll(Mockito.<User>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

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

        // Act and Assert
        assertThrows(CustomException.class, () -> userService.selectAll(user));
        verify(userMapper).selectAll(isA(User.class));
    }

    /**
     * Method under test: {@link UserService#login(Account)}
     */
    @Test
    void testLogin() {
        // Arrange
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
        when(userMapper.selectByUsername(Mockito.<String>any())).thenReturn(user);

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
        Account actualLoginResult = userService.login(account);

        // Assert
        verify(userMapper).selectByUsername(eq("janedoe"));
        assertSame(user, actualLoginResult);
    }

    /**
     * Method under test: {@link UserService#login(Account)}
     */
    @Test
    void testLogin2() {
        // Arrange
        User user = mock(User.class);
        when(user.getId()).thenReturn(1);
        when(user.getPassword()).thenReturn("iloveyou");
        doNothing().when(user).setNewPassword(Mockito.<String>any());
        doNothing().when(user).setToken(Mockito.<String>any());
        doNothing().when(user).setAccount(Mockito.<Double>any());
        doNothing().when(user).setAvatar(Mockito.<String>any());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<Integer>any());
        doNothing().when(user).setName(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setPhone(Mockito.<String>any());
        doNothing().when(user).setRole(Mockito.<String>any());
        doNothing().when(user).setUsername(Mockito.<String>any());
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
        when(userMapper.selectByUsername(Mockito.<String>any())).thenReturn(user);

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
        userService.login(account);

        // Assert
        verify(user).setNewPassword(eq("iloveyou"));
        verify(user, atLeast(1)).setToken(Mockito.<String>any());
        verify(user).getId();
        verify(user, atLeast(1)).getPassword();
        verify(user).setAccount(eq(10.0d));
        verify(user).setAvatar(eq("Avatar"));
        verify(user).setEmail(eq("jane.doe@example.org"));
        verify(user).setId(eq(1));
        verify(user).setName(eq("Name"));
        verify(user).setPassword(eq("iloveyou"));
        verify(user).setPhone(eq("6625550144"));
        verify(user).setRole(eq("Role"));
        verify(user).setUsername(eq("janedoe"));
        verify(userMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link UserService#login(Account)}
     */
    @Test
    void testLogin3() {
        // Arrange
        User user = mock(User.class);
        when(user.getId()).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));
        when(user.getPassword()).thenReturn("iloveyou");
        doNothing().when(user).setNewPassword(Mockito.<String>any());
        doNothing().when(user).setToken(Mockito.<String>any());
        doNothing().when(user).setAccount(Mockito.<Double>any());
        doNothing().when(user).setAvatar(Mockito.<String>any());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<Integer>any());
        doNothing().when(user).setName(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setPhone(Mockito.<String>any());
        doNothing().when(user).setRole(Mockito.<String>any());
        doNothing().when(user).setUsername(Mockito.<String>any());
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
        when(userMapper.selectByUsername(Mockito.<String>any())).thenReturn(user);

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
        assertThrows(CustomException.class, () -> userService.login(account));
        verify(user).setNewPassword(eq("iloveyou"));
        verify(user).setToken(eq("ABC123"));
        verify(user).getId();
        verify(user).getPassword();
        verify(user).setAccount(eq(10.0d));
        verify(user).setAvatar(eq("Avatar"));
        verify(user).setEmail(eq("jane.doe@example.org"));
        verify(user).setId(eq(1));
        verify(user).setName(eq("Name"));
        verify(user).setPassword(eq("iloveyou"));
        verify(user).setPhone(eq("6625550144"));
        verify(user).setRole(eq("Role"));
        verify(user).setUsername(eq("janedoe"));
        verify(userMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link UserService#login(Account)}
     */
    @Test
    void testLogin4() {
        // Arrange
        User user = mock(User.class);
        when(user.getPassword()).thenReturn("-");
        doNothing().when(user).setNewPassword(Mockito.<String>any());
        doNothing().when(user).setToken(Mockito.<String>any());
        doNothing().when(user).setAccount(Mockito.<Double>any());
        doNothing().when(user).setAvatar(Mockito.<String>any());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<Integer>any());
        doNothing().when(user).setName(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setPhone(Mockito.<String>any());
        doNothing().when(user).setRole(Mockito.<String>any());
        doNothing().when(user).setUsername(Mockito.<String>any());
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
        when(userMapper.selectByUsername(Mockito.<String>any())).thenReturn(user);

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
        assertThrows(CustomException.class, () -> userService.login(account));
        verify(user).setNewPassword(eq("iloveyou"));
        verify(user).setToken(eq("ABC123"));
        verify(user).getPassword();
        verify(user).setAccount(eq(10.0d));
        verify(user).setAvatar(eq("Avatar"));
        verify(user).setEmail(eq("jane.doe@example.org"));
        verify(user).setId(eq(1));
        verify(user).setName(eq("Name"));
        verify(user).setPassword(eq("iloveyou"));
        verify(user).setPhone(eq("6625550144"));
        verify(user).setRole(eq("Role"));
        verify(user).setUsername(eq("janedoe"));
        verify(userMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link UserService#register(Account)}
     */
    @Test
    void testRegister() {
        // Arrange
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
        when(userMapper.selectByUsername(Mockito.<String>any())).thenReturn(user);

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
        assertThrows(CustomException.class, () -> userService.register(account));
        verify(userMapper).selectByUsername(eq("janedoe"));
    }

    /**
     * Method under test: {@link UserService#register(Account)}
     */
    @Test
    void testRegister2() {
        // Arrange
        when(userMapper.selectByUsername(Mockito.<String>any())).thenThrow(new CustomException(ResultCodeEnum.SUCCESS));

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
        assertThrows(CustomException.class, () -> userService.register(account));
        verify(userMapper).selectByUsername(eq("janedoe"));
    }
}
