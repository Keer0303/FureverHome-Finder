package com.example.mapper;


import cn.hutool.core.date.DateTime;
import com.example.entity.Submit;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Interface for operations related to notice data
 */
public interface SubmitMapper {

    /**
     * 新增
     */
    int insert(Submit submit);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Submit submit);

    /**
     * 根据ID查询
     */
    Submit selectById(Integer id);

    /**
     * 查询所有
     */
    List<Submit> selectAll(Submit submit);

    /**
     * 根据日期范围和状态查询
     */
    List<Submit> selectByCondition(@Param("submit") Submit submit,
                                   @Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);

}
