package com.xwguan.autofund.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A bijection map. The key and value satisfy a one to one correspondence, thus we can get unique value by key and
 * unique key by value. The values of key or value are <b>NOT ALLOWED</b> to be null. The method {@code int hashCode()}
 * and {@code boolean equals(Object obj)} <b>MUST</b> be overrided in the class of the key and value, or unpredictable
 * mapping errors may occur in this class.
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-02
 */
public class BijectionMap<K, V> {

    private Map<K, V> k2vMap;

    private Map<V, K> v2kMap;

    public BijectionMap() {
        this.k2vMap = new HashMap<>();
        this.v2kMap = new HashMap<>();
    }

    public BijectionMap(int initialCapacity) {
        this.k2vMap = new HashMap<>(initialCapacity);
        this.v2kMap = new HashMap<>(initialCapacity);
    }

    public Set<K> keySet() {
        return k2vMap.keySet();
    }

    public Set<V> valueSet() {
        return v2kMap.keySet();
    }

    public boolean containsKey(K key) {
        return key != null && k2vMap.containsKey(key);
    }

    public boolean containsValue(V value) {
        return value != null && v2kMap.containsKey(value);
    }

    public boolean containsBijection(K key, V value) {
        // logically, containsX(x) && getY(x).equals(y) and containsY(y) && getX(y).equals(x) are equivalent
        return containsKey(key) && getValue(key).equals(value);
        // TODO
    }

    /**
     * Put the bijection of key and value into this map. If the mapping of key or value has already existed, nothing
     * will be put in, and false is to be returned.
     * 
     * @param key
     * @param value
     * @return true if successfully put, false if key or value has already existed
     */
    public boolean put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException("null is not an accepted value in " + this.getClass().getName());
        }
        if (containsKey(key) || containsValue(value)) {
            return false;
        }
        try {
            k2vMap.put(key, value);
            v2kMap.put(value, key);
            return true;
        } catch (Exception e) {
            // roll back to avoid inconformity of the two map
            k2vMap.remove(key);
            v2kMap.remove(value);
            throw e;
        }
    }

    /**
     * Remeve bijection if the bijection exists
     * 
     * @param key
     * @param value
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    public V remove(K key, V value) {
        if (!containsBijection(key, value)) {
            return null;
        }
        try {
            k2vMap.remove(key);
            v2kMap.remove(value);
            return value;
        } catch (Exception e) {
            k2vMap.put(key, value);
            v2kMap.put(value, key);
            throw e;
        }
    }

    /**
     * Remove bijection if key exist
     * 
     * @param key
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    public V remove(K key) {
        if (containsKey(key)) {
            return remove(key, getValue(key));
        } else {
            return null;
        }
    }

    /**
     * Get value associated with key
     * 
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    public V getValue(K key) {
        return k2vMap.get(key);
    }

    /**
     * Get key associated with value
     * 
     * @param value the value whose associated key is to be returned
     * @return the key to which the specified value is mapped, or null if this map contains no mapping for the value
     */
    public K getKey(V value) {
        return v2kMap.get(value);
    }

    /**
     * Get a new HashMap with the same key and associated value of this object
     * 
     * @return a new HashMap with the same key and associated value of this object
     */
    public Map<K, V> getHashMap() {
        return new HashMap<K, V>(k2vMap);
    }

    /**
     * Get a new HashMap whose key is the value and value is the associated key of this object
     * 
     * @return a new HashMap whose key is the value and value is the associated key of this object
     */
    public Map<V, K> getReversedHashMap() {
        return new HashMap<V, K>(v2kMap);
    }

    public int size() {
        return k2vMap.size();
    }

    public boolean isEmpty() {
        return k2vMap.isEmpty();
    }

    public void clear() {
        k2vMap.clear();
        v2kMap.clear();
    }

    @Override
    public String toString() {
        return "BijectionMap " + k2vMap.toString();
    }

}
