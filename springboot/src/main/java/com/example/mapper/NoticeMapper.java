package com.example.mapper;

import com.example.entity.Notice;
import java.util.List;

/**
 * Interface for operations related to notice data
 */
public interface NoticeMapper {

    /**
     * Insert new notice
     */
    int insert(Notice notice);

    /**
     * Delete notice by ID
     */
    int deleteById(Integer id);

    /**
     * Update notice by ID
     */
    int updateById(Notice notice);

    /**
     * Select notice by ID
     */
    Notice selectById(Integer id);

    /**
     * Select all notices
     */
    List<Notice> selectAll(Notice notice);

}
