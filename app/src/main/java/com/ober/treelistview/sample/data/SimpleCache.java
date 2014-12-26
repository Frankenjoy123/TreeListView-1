package com.ober.treelistview.sample.data;

import java.util.HashMap;

/**
 * Created by zlo on 2014/12/26.
 */
public class SimpleCache {

    private static SimpleCache instance;

    private HashMap<String, SimpleData> mCache;

    private SimpleCache() {mCache = new HashMap<>();}

    public static synchronized SimpleCache getInstance() {
        if(instance == null) {
            instance = new SimpleCache();
        }

        return instance;
    }

    public boolean isExist(String s) {
        return mCache.containsKey(s);
    }

    public boolean isExist(SimpleData simpleData) {
        return mCache.containsValue(simpleData);
    }

    public SimpleData put(SimpleData simpleData) {

        if(simpleData.s == null) {
            throw new NullPointerException("simpleData.s = null!");
        }

        return mCache.put(simpleData.s, simpleData);
    }

    public SimpleData remove(SimpleData simpleData) {
        return mCache.remove(simpleData.s);
    }

    public SimpleData remove(String s) {
        return mCache.remove(s);
    }

}
