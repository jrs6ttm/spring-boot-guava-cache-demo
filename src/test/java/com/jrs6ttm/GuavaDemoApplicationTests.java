package com.jrs6ttm;

import com.google.common.base.Ticker;
import com.google.common.cache.CacheBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuavaDemoApplicationTests {

    @Resource
    private CacheBuilder<Object, Object> cacheBuilder;

    @Test
    public void testCacheExpiredUseCM() {
        // 自定义ticker
        TestTicker testTicker = new TestTicker();

        // 模拟时间流逝
        com.google.common.cache.Cache<Object, Optional<String>> cache = cacheBuilder.ticker(testTicker).build();
        cache.put(1, Optional.empty());
        cache.put("id", Optional.ofNullable(UUID.randomUUID().toString()));
        System.out.println("GuavaDemoApplicationTests.testCacheExpired id : " + cache.getIfPresent("id"));

        // 模拟时间流逝
        testTicker.addElapsedTime(TimeUnit.NANOSECONDS.convert(10, TimeUnit.MINUTES));

        Assert.assertEquals(null, cache.getIfPresent("id"));
        System.out.println("GuavaDemoApplicationTests.testCacheExpired id : " + cache.getIfPresent("id"));
        System.out.println("GuavaDemoApplicationTests.testCacheExpiredUseCM size: " + cache.size());
    }


    /**
     * http://cd826.iteye.com/blog/2036659
     */
    @Test
    public void testCacheExpired() {
        // 自定义ticker
        TestTicker testTicker = new TestTicker();

        // 模拟时间流逝
        com.google.common.cache.Cache<String, Optional<String>> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .ticker(testTicker)
                .build();

        cache.put("id", Optional.ofNullable(UUID.randomUUID().toString()));

        // 模拟时间流逝
        testTicker.addElapsedTime(TimeUnit.NANOSECONDS.convert(10, TimeUnit.MINUTES));

        Assert.assertEquals(null, cache.getIfPresent("id"));
        System.out.println("GuavaDemoApplicationTests.testCacheExpired id : " + cache.getIfPresent("id"));
    }

    @Test
    public void testInvalidateCache() {

        com.google.common.cache.Cache<String, Optional<String>> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();

        cache.put("id", Optional.ofNullable(UUID.randomUUID().toString()));
        System.out.println("GuavaDemoApplicationTests.testCacheExpired id : " + cache.getIfPresent("id"));
        // 清除缓存
        cache.invalidate("id");
        Assert.assertEquals(null, cache.getIfPresent("id"));
        System.out.println("GuavaDemoApplicationTests.testCacheExpired id : " + cache.getIfPresent("id"));

    }

    private class TestTicker extends Ticker {
        private long start = Ticker.systemTicker().read();
        private long elapsedNano = 0;

        @Override
        public long read() {
            return start + elapsedNano;
        }

        public void addElapsedTime(long elapsedNano) {
            this.elapsedNano = elapsedNano;
        }
    }
}
