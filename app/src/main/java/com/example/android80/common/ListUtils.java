package com.example.android80.common;

import java.util.List;

public class ListUtils {

    public static <T> Object getItem(List<T> list, int index) {
        if (list != null &&  list.size() > index) {
            return list.get(index);
        }
        return null;

    }

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }
}
