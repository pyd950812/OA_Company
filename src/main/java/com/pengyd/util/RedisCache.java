package com.pengyd.util;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:08
 * @function:
 */
@Component("redisCache")
public class RedisCache {
    private int port = 6379;

    private String host = "127.0.0.1";

    private Jedis jedis = new Jedis(this.host, this.port);

    private int expiretime = 1800;

    public String cache(String key, String value) {
        String result = this.jedis.set(key, value);
        this.jedis.expire(key, this.expiretime);
        return result;
    }

    public String get(String key) {
        this.jedis.expire(key, this.expiretime);
        return this.jedis.get(key);
    }

    public static void main(String[] args) {
        RedisCache r = new RedisCache();
        r.cache("1","1");
        System.out.println(r.get("1"));
    }
}
