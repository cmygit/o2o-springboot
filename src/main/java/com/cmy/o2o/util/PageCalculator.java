package com.cmy.o2o.util;

/**
 * Author : cmy
 * Date   : 2018-03-04 23:08.
 * desc   :
 */
public class PageCalculator {
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
