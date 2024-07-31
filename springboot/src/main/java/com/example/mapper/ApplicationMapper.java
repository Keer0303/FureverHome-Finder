package com.example.mapper;

import com.example.entity.Application;
import java.util.List;

public interface ApplicationMapper {
    void insert(Application application);
    void deleteByPrimaryKey(Integer id);
    void deleteBatch(List<Integer> ids);
    void updateByPrimaryKey(Application application);
    Application selectByPrimaryKey(Integer id);
    List<Application> selectAll(Application application);
}
