package com.gjw.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 反射工具类
 *
 * @author YipChiuHei
 */
public class ReflectUtils {

    /**
     * 驼峰命名 处理带有下划线的数据库列名
     *
     * @param columnName
     * @param getterOrSetter 0代表生成get方法名 1代表set方法名 其他代表返回驼峰命名
     * @return
     */
    public static String camelCase(String columnName, int getterOrSetter) {
        String[] words = columnName.split("_");
        if (words.length == 0) {
            if (getterOrSetter == 0) {
                return "get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length());
            } else if (getterOrSetter == 1) {
                return "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length());
            } else {
                return columnName;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            String after = "";
            if (i == 0) {
                if (getterOrSetter == 0) {
                    after = "get" + word.substring(0, 1).toUpperCase() + word.substring(1, word.length());
                } else if (getterOrSetter == 1) {
                    after = "set" + word.substring(0, 1).toUpperCase() + word.substring(1, word.length());
                } else {
                    after = word;
                }
            } else {
                after = word.substring(0, 1).toUpperCase() + word.substring(1, word.length());
            }
            stringBuilder.append(after);
        }
        return stringBuilder.toString();
    }

    /**
     * 通用集合转map
     *
     * @param datalist  集合
     * @param keyColumn 作为key的字段名
     * @param clazz     对象类型
     * @return
     */
    public static <T> Map<String, T> toMap(List<T> datalist, String keyColumn, Class clazz) {
        Map<String, T> map = new HashMap<>();
        try {
            T t;
            Method getMethod = clazz.getMethod(camelCase(keyColumn, 0));
            for (int i = 0; i < datalist.size(); i++) {
                t = datalist.get(i);
                String key = (String) getMethod.invoke(t);
                if (!map.containsKey(key)) {
                    map.put(key, t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 通用集合转map
     *
     * @param datalist  集合
     * @param keyColumn 作为key的字段名
     * @param clazz     对象类型
     * @return
     */
    public static <K,T> Map<K, List<T>> toMapList(List<T> datalist, String keyColumn, Class clazz,Map<K, List<T>> map) {
        try {
            T t;
            Method getMethod = clazz.getMethod(camelCase(keyColumn, 0));
            for (int i = 0; i < datalist.size(); i++) {
                t = datalist.get(i);
                K key = (K) getMethod.invoke(t);
                if (map.containsKey(key)) {
                    map.get(key).add(t);
                } else {
                    List<T> list = new ArrayList<>();
                    list.add(t);
                    map.put(key, list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}
