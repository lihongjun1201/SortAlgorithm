package com.newtalent.www;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序 对于小整数键值的排序，桶排序可以耗时 O(n+N),空间O(n+N) n位待排元素个数,N为键值大小范围
 * 
 * @author AllenLee
 * 
 */
public class BucketSort {

	// 有N = 10个桶，代表0->9基数。
	// 每个桶都是一个ArrayList
	public static final int N = 10;

	public static void buckedSort(int[] list, int index) {
		// 定义N个桶数组
		ArrayList[] buckets = new ArrayList[N];

		// 分配桶
		for (int i = 0; i < N; i++) {
			buckets[i] = new ArrayList<Integer>();
		}

		// 将元素放入对应KEY值的桶中
		for (int i = 0, base = (int) Math.pow(10,index-1); i < list.length; i++) {
			
			int key = (list[i]/(base)) %  10;
			buckets[key].add(list[i]);
		}

		// 从桶中按序拿出各元素
		int newindex = 0;
		for (int i = 0; i < buckets.length; i++) {
			if (!buckets[i].isEmpty()) {
				for (int j = 0; j < buckets[i].size(); j++) {
					list[newindex++] = (int) buckets[i].get(j);
				}
			}
		}

	}


	public static void baseKeySort(int[] array) {

		//获取数组中最大元素
		int max = array[0];
		for (int i = 0; i < array.length; i++) {
			if (max < array[i]) {
				max = array[i];
			}
		}
		
		//获取最大整数有多少位数
		int n = String.valueOf(max).length();

		//按个，十，百，千....每位的值进行桶排序迭代
		for (int index = 1; index <= n; index++) {
			buckedSort(array, index);
		}
	}

	public static void main(String[] args) {
		int[] unsortedArray = { 331, 454, 230, 34, 343, 45, 59, 453455s, 345, 231,9 };
//		int[] unsortedArray = { 5,4,3,2,23,42,53,12 };
		baseKeySort(unsortedArray);
		
		//buckedSort(unsortedArray, 1);
		for (int i = 0; i < unsortedArray.length; i++) {
			System.out.print(unsortedArray[i] + " ");
		}

	}

}
