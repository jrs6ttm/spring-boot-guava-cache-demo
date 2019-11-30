package com.jrs6ttm.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jrs6ttm.entity.City;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CacheUtil {
    private static final String CACHE_KEY = "LocalCache";

    private LoadingCache<String, Map<Integer, City>> cache =
            CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Map<Integer, City>>() {
                @Override
                public Map<Integer, City> load(String key) throws Exception {
                    System.out.println("load city from service:");
                    List<City> allCity = new ArrayList<>();
                    City citytmp = new City();
                    citytmp.setId(1);
                    citytmp.setName("beijing");
                    allCity.add(citytmp);
                    citytmp = new City();
                    citytmp.setId(2);
                    citytmp.setName("qingdao");

                    Map<Integer, City> cityMap = new HashMap<>();
                    for (City city : allCity) {
                        cityMap.put(city.getId(), city);
                    }
                    return cityMap;
                }
            });

    public void refresh() {
        cache.refresh(CACHE_KEY);
    }


    private Map<Integer, City> getCityMap() {
        try {
            return cache.get(CACHE_KEY);
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Collection<City> getAllCity() {
        return getCityMap().values();
    }

    public City getCityById(int cityId) {
        return getCityMap().get(cityId);
    }

    public String getCityName(int cityId) {
        City city = getCityById(cityId);
        return null == city ? "" : city.getName();
    }
}
