package com.example.controller;

import com.example.common.Result;
import com.example.entity.Adopt;
import com.example.entity.Animal;
import com.example.entity.Application;
import com.example.service.AnimalService;
import com.example.service.ApplicationService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * Application information front-end operation interface
 **/
@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Resource
    private ApplicationService applicationService;

    /**
     * Add new application
     */
    @PostMapping("/add")
    public Result add(@RequestBody Application application) {
        applicationService.add(application);
        return Result.success();
    }

    /**
     * Delete application by ID
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        applicationService.deleteById(id);
        return Result.success();
    }

    /**
     * Batch delete applications
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        applicationService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * Update application by ID
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Application application) {
        applicationService.updateById(application);
        return Result.success();
    }


    /**
     * Get application by ID
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Application application = applicationService.selectById(id);
        return Result.success(application);
    }

    /**
     * Get all applications
     */
    @GetMapping("/selectAll")
    public Result selectAll(Application application) {
        List<Application> list = applicationService.selectAll(application);
        return Result.success(list);
    }

    /**
     * Get applications by page
     */
    @GetMapping("/selectPage")
    public Result selectPage(Application application,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Application> page = applicationService.selectPage(application, pageNum, pageSize);
        return Result.success(page);
    }

}
