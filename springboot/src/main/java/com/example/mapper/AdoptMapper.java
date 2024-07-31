package com.example.mapper;

import com.example.entity.Adopt;
import com.example.entity.Animal;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Interface for operations related to adopt data
 */
public interface AdoptMapper {

    /**
     * Insert new adopt
     */
    int insert(Adopt adopt);

    /**
     * Delete adopt by ID
     */
    int deleteById(Integer id);

    /**
     * Update adopt by ID
     */
    int updateById(Adopt adopt);

    /**
     * Select adopt by ID
     */
    Adopt selectById(Integer id);

    /**
     * Select all adopts
     */
    List<Adopt> selectAll(Adopt adopt);

    @Select("select distinct animal_id from adopt group by animal_id")
    List<Adopt> selectAnimal();

    @Select ("select distinct user_id from adopt group by user_id")
    List<Adopt> selectAdopter();
}
