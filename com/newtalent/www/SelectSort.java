package com.newtalent.www;


/**
 * 选择排序
 * @author AllenLee
 *
 */
public class SelectSort {
	
	/**
	 * 选择排序
	 * 从头到尾，每次选择剩余数组中最小的一个，换到前面来。
	 * @param a
	 */
	public static void selectSort(int[] a) {
		for (int i = 0; i < a.length; i++) {
			int min = a[i], minIndex = i ;
			for (int j = i; j < a.length; j++) {
				if (min > a[j]) {
					min = a[j];
					minIndex = j;
				}
			}
			a[minIndex] = a[i];
			a[i] = min;
		}
	}
	

	public static void main(String[] args) {
		int[] unsortedArray = { 331, 454, 230, 34, 343, 45, 59, 453455, 345, 231,9 };
//		int[] unsortedArray = {5,4,3,2,1};
		selectSort(unsortedArray);
		for (int i = 0; i < unsortedArray.length; i++) {
			System.out.print(unsortedArray[i] + " ");
		}
	}

}
