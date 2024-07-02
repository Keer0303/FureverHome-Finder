package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Interface for operations related to user data
 */
public interface UserMapper {

    /**
     * Insert new user
     */
    int insert(User user);

    /**
     * Delete user by ID
     */
    int deleteById(Integer id);

    /**
     * Update user by ID
     */
    int updateById(User user);

    /**
     * Select user by ID
     */
    User selectById(Integer id);

    /**
     * Select all users
     */
    List<User> selectAll(User user);

    /**
     * Select user by username
     */
    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);
}
