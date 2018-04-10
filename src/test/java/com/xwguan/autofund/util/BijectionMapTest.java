package com.xwguan.autofund.util;

import org.junit.Test;

public class BijectionMapTest {

    @Test
    public void testBijectionMap() {
        BijectionMap<String, Integer> map = new BijectionMap<String, Integer>();
        try {
            System.out.println(map.put("a", 1));
            System.out.println(map.put("1", 2));
            System.out.println(map.put("a", 3));
            System.out.println(map.put("", 3));
            System.out.println(map.getKey(1));
            System.out.println(map.getValue("a"));
            System.out.println(map.containsKey("a"));
            System.out.println(map.containsKey("3"));
            System.out.println(map.containsBijection("a", 1));
            System.out.println(map.containsBijection("a", 2));
            System.out.println(map.keySet());
            System.out.println(map.valueSet());
            System.out.println(map);
            map.remove("a", 1);
            System.out.println(map.keySet());
            System.out.println(map.valueSet());
            System.out.println(map);
            System.out.println(map.getHashMap());
            System.out.println(map.getReversedHashMap());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
