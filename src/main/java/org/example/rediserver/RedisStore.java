package org.example.rediserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisStore {

    private final Map<String, String> store =  new ConcurrentHashMap<>();

    public String set(String key, String value){
        return store.put(key, value);
    }

    public String get(String key){
        return store.get(key);
    }

    public String delete(String key){
        return store.remove(key);
    }

}
