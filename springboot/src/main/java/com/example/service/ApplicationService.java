package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.Animal;
import com.example.entity.Application;
import com.example.mapper.AnimalMapper;
import com.example.mapper.ApplicationMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Application information business processing
 **/
@Service
public class ApplicationService {

    @Resource
    private ApplicationMapper applicationMapper;
    @Resource
    private AnimalMapper animalMapper;

    /**
     * Add new application
     */
    public void add(Application application) {
        application.setStatus("Pending");
        Account currentUser = TokenUtils.getCurrentUser();
        application.setName(currentUser.getUsername());
        application.setEmail(currentUser.getEmail());
        application.setPhone(currentUser.getPhone());

        applicationMapper.insert(application);
    }

    /**
     * Delete application by ID
     */
    public void deleteById(Integer id) {
        applicationMapper.deleteByPrimaryKey(id);
    }

    /**
     * Batch delete applications
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            applicationMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * Update application by ID
     */
    public void updateById(Application application) {
        applicationMapper.updateByPrimaryKey(application);

        if ("Approved".equals(application.getStatus())) {
            Animal animal = animalMapper.selectById(application.getAnimalId());
            if (animal != null) {
                animal.setStatus("Pending");
                animalMapper.updateById(animal);
                System.out.println("Animal status updated to Pending for ID: " + animal.getId());
            }
        } else if ("Finished".equals(application.getStatus())) {
            Animal animal = animalMapper.selectById(application.getAnimalId());
            if (animal != null) {
                animal.setStatus("Adopted");
                animalMapper.updateById(animal);
                System.out.println("Animal status updated to Adopted for ID: " + animal.getId());
            }
        } else {
            System.err.println("Unexpected status: " + application.getStatus());
        }
    }

    /**
     * Select application by ID
     */
    public Application selectById(Integer id) {
        return applicationMapper.selectByPrimaryKey(id);
    }

    /**
     * Select all applications
     */
    public List<Application> selectAll(Application application) {
        return applicationMapper.selectAll(application);
    }

    /**
     * Select applications by page
     */
    public PageInfo<Application> selectPage(Application application, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Application> list = applicationMapper.selectAll(application);
        return PageInfo.of(list);
    }
}
