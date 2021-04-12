package Util;

import java.util.*;

/**
 * @author Jason
 * @version 1.0.0
 * @Description 判断对象是否为空的工具类
 */
public class EmptyUtil {

    /**
     * 对于String类型的非空判断
     *
     * @param str 需要判断的字符串
     * @return true：字符串为空（包含全空格字符串"   "、"null"、"undefined"），false：字符串不为空
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim()) || "undefined".equalsIgnoreCase(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 对于StringBuffer类型的非空判断
     *
     * @param str 需要判断的字符串
     * @return true：空，false：非空
     */
    public static boolean isNullOrEmpty(StringBuffer str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 对于String数组的非空判断
     *
     * @param str 需要判断的字符串数组
     * @return true：空，false：非空
     */
    public static boolean isNullOrEmpty(String[] str) {
        return (str == null || str.length == 0);
    }

    /**
     * 对于单个对象的判断
     *
     * @param obj 需要判断的对象
     * @return true：空，false：非空
     */
    public static boolean isNullOrEmpty(Object obj) {
        return (obj == null || "".equals(obj));
    }

    /**
     * 对于数组类型的非空判断
     *
     * @param obj 需要判断的数组
     * @return true：空，false：非空
     */
    public static boolean isNullOrEmpty(Object[] obj) {
        return (obj == null || obj.length == 0);
    }

    /**
     * 对于Collection集合的非空判断
     *
     * @param collection 需要判断的Collection集合对象
     * @return true：空，false：非空
     * @SuppressWarnings("rawtypes") 传递参数时传递带泛型的参数
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * 对于Map集合的非空判断
     *
     * @param map 需要判断的Map集合
     * @return true：空，false：非空
     * @SuppressWarnings("rawtypes") 传递参数时传递带泛型的参数
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(HashMap map) {
        return (map == null || map.isEmpty());
    }


    /**
     * 删除List集合中的空元素
     *
     * @param list 需要去空的List集合
     * @param <T>
     * @return 去空后的集合
     */
    public static <T> List<T> removeNullElements(List<T> list) {
        List<T> newList = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
            if (!isNullOrEmpty(list.get(i))) {
                newList.add(list.get(i));
            }
        }
        return newList;
    }
}
