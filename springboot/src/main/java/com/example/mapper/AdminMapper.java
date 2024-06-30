package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Interface for operations related to admin data
 */
public interface AdminMapper {

    /**
     * Insert new admin
     */
    int insert(Admin admin);

    /**
     * Delete admin by ID
     */
    int deleteById(Integer id);

    /**
     * Update admin by ID
     */
    int updateById(Admin admin);

    /**
     * Select admin by ID
     */
    Admin selectById(Integer id);

    /**
     * Select all admins
     */
    List<Admin> selectAll(Admin admin);

    /**
     * Select admin by username
     */
    @Select("select * from admin where username = #{username}")
    Admin selectByUsername(String username);
}
