package com.jrs6ttm.service;

import com.jrs6ttm.util.CacheUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service("cacheService")
public class CacheService {

    public long save() {
        long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        System.out.println("进行缓存：" + timestamp);
        return timestamp;
    }

    public void delete() {
        System.out.println("删除缓存");
    }

    public void getByCache() {
        CacheUtil cacheUtil = new CacheUtil();

        for (int i = 0; i < 3; i++) {
            System.out.println("vist num--- " + i + " ---");
            String cname = cacheUtil.getCityName(1);
            System.out.println("name:" + cname);
        }
    }
}
