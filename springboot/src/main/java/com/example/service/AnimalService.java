package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.Animal;
import com.example.mapper.AnimalMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Animal information business processing
 **/
@Service
public class AnimalService {

    @Resource
    private AnimalMapper animalMapper;

    /**
     * Add new animal
     */
    public void add(Animal animal) {
        animalMapper.insert(animal);
    }

    /**
     * Delete animal by ID
     */
    public void deleteById(Integer id) {
        animalMapper.deleteById(id);
    }

    /**
     * Batch delete animals
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            animalMapper.deleteById(id);
        }
    }

    /**
     * Update animal by ID
     */
    public void updateById(Animal animal) {
        animalMapper.updateById(animal);
    }


    /**
     * Select animal by ID
     */
    public Animal selectById(Integer id) {
        return animalMapper.selectById(id);
    }

    /**
     * Select all animals
     */
    public List<Animal> selectAll(Animal animal) {
        return animalMapper.selectAll(animal);
    }

    /**
     * Select animals by page
     */
    public PageInfo<Animal> selectPage(Animal animal, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Animal> list = animalMapper.selectAll(animal);
        return PageInfo.of(list);
    }


}
