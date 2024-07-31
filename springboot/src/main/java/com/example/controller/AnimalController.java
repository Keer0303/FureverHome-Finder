package com.example.controller;

import com.example.common.Result;
import com.example.entity.Animal;
import com.example.service.AnimalService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Animal information front-end operation interface
 **/
@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Resource
    private AnimalService animalService;

    /**
     * Add new animal
     */
    @PostMapping("/add")
    public Result add(@RequestBody Animal animal) {
        animalService.add(animal);
        return Result.success();
    }

    /**
     * Delete animal by ID
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        animalService.deleteById(id);
        return Result.success();
    }

    /**
     * Batch delete animals
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        animalService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * Update animal by ID
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Animal animal) {
        animalService.updateById(animal);
        return Result.success();
    }

    /**
     * Get animal by ID
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Animal animal = animalService.selectById(id);
        return Result.success(animal);
    }

    /**
     * Get all animals
     */
    @GetMapping("/selectAll")
    public Result selectAll(Animal animal) {
        List<Animal> list = animalService.selectAll(animal);
        return Result.success(list);
    }

    /**
     * Get animals by page
     */
    @GetMapping("/selectPage")
    public Result selectPage(Animal animal,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Animal> page = animalService.selectPage(animal, pageNum, pageSize);
        return Result.success(page);
    }

}