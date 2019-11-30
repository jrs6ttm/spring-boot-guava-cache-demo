package com.jrs6ttm.web;

import com.jrs6ttm.service.CacheService;
import com.jrs6ttm.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {
    @Autowired
    private CacheService cacheService;

    /**
     * 查询方法
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void getByCache() {
        this.cacheService.getByCache();
    }

    /**
     * 保存方法
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save() {
        this.cacheService.save();
    }

    /**
     * 删除方法
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete() {
        this.cacheService.delete();
    }
}
