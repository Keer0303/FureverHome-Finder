package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.AdoptStatusEnum;
import com.example.common.enums.AnimalStatusEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Adopt;
import com.example.entity.Animal;
import com.example.entity.User;
import com.example.mapper.AdoptMapper;
import com.example.mapper.AnimalMapper;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Adopt information business processing
 **/
@Service
public class AdoptService {

    @Resource
    private AdoptMapper adoptMapper;
    @Resource
    private AnimalMapper animalMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * Add new adopt
     */
    public void add(Adopt adopt) {
        adopt.setTime(DateUtil.now());
        adopt.setStatus(AdoptStatusEnum.ADOPT_YES.status);
        adoptMapper.insert(adopt);
    }

    /**
     * Delete adopt by ID
     */
    public void deleteById(Integer id) {
        adoptMapper.deleteById(id);
    }

    /**
     * Batch delete adopts
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            adoptMapper.deleteById(id);
        }
    }

    /**
     * Update adopt by ID
     */
    public void updateById(Adopt adopt) {
        if (AnimalStatusEnum.ADOPT_GIVEUP.status.equals(adopt.getStatus())) {
            // 已归还我们需要把宠物信息的状态同步成 待领养
            Animal animal = animalMapper.selectById(adopt.getAnimalId());
            animal.setStatus(AnimalStatusEnum.ADOPT_NO.status);
            animalMapper.updateById(animal);
        }
        adoptMapper.updateById(adopt);
    }


    /**
     * Select adopt by ID
     */
    public Adopt selectById(Integer id) {
        return adoptMapper.selectById(id);
    }

    /**
     * Select all adopts
     */
    public List<Adopt> selectAll(Adopt adopt) {
        return adoptMapper.selectAll(adopt);
    }

    /**
     * Select adopts by page
     */
    public PageInfo<Adopt> selectPage(Adopt adopt, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            adopt.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Adopt> list = adoptMapper.selectAll(adopt);
        return PageInfo.of(list);
    }

    public List<Animal> selectAnimal() {
        List<Adopt> adopts = adoptMapper.selectAnimal();
        List<Animal> animals = new ArrayList<>();
        for (Adopt adopt : adopts) {
            Animal animal = animalMapper.selectById(adopt.getAnimalId());
            if (ObjectUtil.isNotEmpty(animal)) {
                animals.add(animal);
            }
        }
        return animals;
    }

    public List<User> selectAdopter() {
        List<Adopt> adopts = adoptMapper.selectAdopter();
        List<User> adopters = new ArrayList<>();
        for (Adopt adopt : adopts) {
            User user = userMapper.selectById(adopt.getUserId());
            if (ObjectUtil.isNotEmpty(user)) {
                adopters.add(user);
            }
        }
        return adopters;
    }


}
