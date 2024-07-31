package com.example.mapper;

import com.example.entity.Animal;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Interface for operations related to animal data
 */
public interface AnimalMapper {

    /**
     * Insert new animal
     */
    int insert(Animal animal);

    /**
     * Delete animal by ID
     */
    int deleteById(Integer id);

    /**
     * Update animal by ID
     */
    int updateById(Animal animal);

    /**
     * Select animal by ID
     */
    Animal selectById(Integer id);
//
//    @Update("UPDATE animal SET status = #{status} WHERE id = #{id}")
//    void updateStatus(@Param("id") Integer id, @Param("status") String status);


    /**
     * Select all animals
     */
    List<Animal> selectAll(Animal animal);

//    Animal selectByName(String name);
//
//    Animal selectBySpecies(String species);
//
//    Animal selectByGender(String gender);
//
//    Animal selectByAge(String age);
}
