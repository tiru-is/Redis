package com.example.demo.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.model.Site;
import com.example.demo.util.HostPort;
import com.example.demo.util.RedisSchema;
import com.example.demo.util.TestKeyManager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Service
public class SiteDaoRedisImpl implements SiteDao {
	
	
    private  JedisPool jedisPool;
	 private static Jedis jedis;
	 private static TestKeyManager keyManager;

   public SiteDaoRedisImpl() {
	   String password = HostPort.getRedisPassword();

       if (password.length() > 0) {
           jedisPool = new JedisPool(new JedisPoolConfig(), HostPort.getRedisHost(), HostPort.getRedisPort(), 2000, password);
       } else {
           jedisPool = new JedisPool(HostPort.getRedisHost(), HostPort.getRedisPort());
       }

       jedis = new Jedis(HostPort.getRedisHost(), HostPort.getRedisPort());

       if (password.length() > 0) {
           jedis.auth(password);
       }

       keyManager = new TestKeyManager("test");
        this.jedisPool = jedisPool;
    }

    // When we insert a site, we set all of its values into a single hash.
    // We then store the site's id in a set for easy access.
    @Override
    public void insert(Site site) {
        try (Jedis jedis = jedisPool.getResource()) {
            String hashKey = RedisSchema.getSiteHashKey(site.getId());
            String siteIdKey = RedisSchema.getSiteIDsKey();
            jedis.hmset(hashKey, site.toMap());
            jedis.sadd(siteIdKey, hashKey);
        }
    }

    @Override
    public Site findById(long id) {
        try(Jedis jedis = jedisPool.getResource()) {
            String key = RedisSchema.getSiteHashKey(id);
            Map<String, String> fields = jedis.hgetAll(key);
                        
            if (fields == null || fields.isEmpty()) {
                return null;
            } else {
                return new Site(fields);
            }
        }
    }

    // Challenge #1
    @Override
    public Set<Site> findAll() {
    	try (Jedis jedis = jedisPool.getResource()) {
    	    Set<String> keys = jedis.keys("test:sites:info:*");
            Set<Site> sites = new HashSet<>(keys.size());
            for (String key : keys) {
                Map<String, String> site = jedis.hgetAll(key);
                if (!site.isEmpty()) {
                    sites.add(new Site(site));
                }
            }
            System.out.println(sites);
            return sites;
        }
    }
    
    public  boolean delete(long id) {
    	
    	try(Jedis jedis = jedisPool.getResource()) {
            String key = RedisSchema.getSiteHashKey(id);
            Map<String, String> fields = jedis.hgetAll(key);
                        
            if (fields == null || fields.isEmpty()) {
                return false;
            } else {
            	jedis.hdel(key, "capacity","panels","address","city","state","postalCode");
                return true;
            }
        }
    	
    }
    
    public Site update(Site site) {
    	
    	try(Jedis jedis = jedisPool.getResource()) {
            String key = RedisSchema.getSiteHashKey(site.getId());
            Map<String, String> fields = jedis.hgetAll(key);
                        
            if (fields == null || fields.isEmpty()) {
                return null;
            } else {
            	Map<String,String> map = new HashMap<String,String>();
            	map.put("capacity",site.getCapacity().toString());
            	map.put("panels",site.getPanels().toString());
            	map.put("address",site.getAddress());
            	map.put("city",site.getCity());
            	map.put("state",site.getState());
            	map.put("postalCode",site.getPostalCode());
            	jedis.hmset(key, map);
                return site;
            }
        }
    	
    }
}
