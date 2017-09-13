package com.maptools.controller;

/**
 * Created by mr on 2017/9/4.
 */
public class SortTest {

    public static void main(String[] args) {
        int[] a = {5, 10, 7, 6, 8, 9, 4, 12};
        sort(a, 0, 7);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + "  ");
        }
    }

    public static void sort(int[] a, int l, int r) {
        //左侧下标大于右侧下标，结束
        if (l >= r) return;
        int last = r;//7
        int first = l;//0
        //第一个值
        int key = a[l];//5基准值
        while (first < last) {
            //a[6]
            while (a[last] > key && first < last) {
                last--;
            }
            //a[0]=a[6]
            a[first] = a[last];
            //5, 10, 7, 6, 8, 9, 4, 12
            //4, 10, 7, 6, 8, 9, 4, 12

            while (key > a[first] && first < last) {
                first++;
            }
            a[last] = a[first];
            //4, 10, 7, 6, 8, 9, 10, 12
        }
        a[first] = key;
        //4 5 7 6 8 9 10 12
        sort(a, l, first - 1);
        sort(a, first + 1, r);
    }

}
