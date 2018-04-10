package com.xwguan.autofund.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

public class ToStringUtils {

    /**
     * 按需每个元素一行的toString方法, 不递归展开列表
     * input:
     * [a, b], true
     * output:
     * [
     * a,
     * b]
     * 
     * @param collection 待展开的集合
     * @param needUnfold 是否展开
     * @return
     */
    public static StringBuilder unfoldCollectionIfNeeded(Collection<?> collection, boolean needUnfold) {
        return unfoldCollectionIfNeeded(null, collection, needUnfold);
    }

    /**
     * 按需每个元素一行的toString方法, 不递归展开列表
     * input:
     * MyStr, [a, b], true
     * output:
     * MyStr=[
     * a,
     * b]
     * 
     * @param collection 待展开的集合
     * @param needUnfold 是否展开
     * @return
     */
    public static StringBuilder unfoldCollectionIfNeeded(String collectionName, Collection<?> collection,
        boolean needUnfold) {
        return unfoldCollectionIfNeeded(collectionName, collection, needUnfold, i -> i.toString());
    }

    /**
     * 按需每个元素一行的toString方法, 第三个参数用于将元素用定制的toString展开, 如元素对象中的Collection也展开
     * 
     * @param collection
     * @param needUnfold
     * @param toString
     * @return
     */
    public static <T> StringBuilder unfoldCollectionIfNeeded(String collectionName, Collection<T> collection,
        boolean needUnfold, Function<T, String> toString) {
        StringBuilder sb = nameString(collectionName);
        if (needUnfold && collection != null) {
            sb.append("[");
            if (collection.size() != 0) {
                collection.stream()
                    .forEach(i -> sb.append("\n").append(i == null ? null : toString.apply(i)).append(","));
                sb.setLength(sb.length() - 1); // remove the last comma
            }
            sb.append("]");
        } else {
            sb.append(collection);
        }
        return sb;
    }

    /**
     * 按需每个元素一行的toString方法
     * 
     * @param map
     * @param needUnfold
     * @return
     */
    public static StringBuilder unfoldMapIfNeeded(String mapName, Map<?, ?> map, boolean needUnfold) {
        return unfoldMapIfNeeded(mapName, map, needUnfold, k -> k.toString(), v -> v.toString());
    }

    /**
     * 按需每个元素一行的toString方法, 第三和第四个参数用于将key和value用定制的toString展开
     * 
     * @param map
     * @param needUnfold
     * @param keyToString map key的toString函数
     * @param valueToString map value的toString函数
     * @return
     */
    public static <K, V> StringBuilder unfoldMapIfNeeded(String mapName, Map<K, V> map, boolean needUnfold,
        Function<K, String> keyToString, Function<V, String> valueToString) {
        StringBuilder sb = nameString(mapName);
        if (needUnfold && map != null) {
            sb.append("[");
            if (map.size() != 0) {
                map.keySet().stream().forEach(k -> sb.append("\n")
                    .append(keyToString.apply(k)).append(", ")
                    .append(valueToString.apply(map.get(k))).append(","));
                sb.setLength(sb.length() - 1); // remove the last comma
            }
            sb.append("]");
        } else {
            sb.append(map);
        }
        return sb;
    }

    private static StringBuilder nameString(String name) {
        return new StringBuilder(StringUtils.isEmpty(name) ? "" : name + " ");
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b");
        System.out.println(unfoldCollectionIfNeeded("MyStr", list, true));
    }
}
