package com.example.controller;

import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.common.enums.AdoptStatusEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Adopt;
import com.example.entity.Animal;
import com.example.entity.User;
import com.example.service.AdoptService;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Adoption information front-end operation interface
 **/
@RestController
@RequestMapping("/adopt")
public class AdoptController {

    @Resource
    private AdoptService adoptService;

    /**
     * Add new adopt will no longer be used bc user click Adopt button and relevant information about that pet will be at Manager
     */
    @PostMapping("/add")
    public Result add(@RequestBody Adopt adopt) {
        adopt.setTime(DateUtil.now());
        adopt.setStatus(AdoptStatusEnum.ADOPT_YES.name());
        adoptService.add(adopt);
        return Result.success();
    }


    /**
     * Delete adopt by ID
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        adoptService.deleteById(id);
        return Result.success();
    }

    /**
     * Batch delete adopts
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        adoptService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * Update adopt by ID
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Adopt adopt) {
        adoptService.updateById(adopt);
        return Result.success();
    }

    /**
     * Get adopt by ID
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Adopt adopt = adoptService.selectById(id);
        return Result.success(adopt);
    }

//    get animal data by animal name
    @GetMapping("/selectAnimal")
    public Result selectAnimal() {
        List<Animal> list = adoptService.selectAnimal();
        return Result.success(list);
    }

    @GetMapping("/selectAdopter")
    public Result selectAdopter() {
        List<User> list = adoptService.selectAdopter();
        return Result.success(list);
    }
    /**
     * Get all adopts
     */
    @GetMapping("/selectAll")
    public Result selectAll(Adopt adopt) {
        List<Adopt> list = adoptService.selectAll(adopt);
        return Result.success(list);
    }

    /**
     * Get adopts by page
     */
    @GetMapping("/selectPage")
    public Result selectPage(Adopt adopt,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){
            adopt.setUserId(currentUser.getId());
        }

        PageInfo<Adopt> page = adoptService.selectPage(adopt, pageNum, pageSize);
        return Result.success(page);
    }

}