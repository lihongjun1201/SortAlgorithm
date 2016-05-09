package com.newtalent.www;

import java.util.Arrays;

/**
 * 归并排序
 * 
 * @author AllenLee
 * 
 */
public class MergeSort {

	/**
	 * 归并排序，递归分治法 不断这版拆分，分到只有一个元素，此时进行归并数组，递归返回。
	 * 
	 * @param array
	 */
	public static void mergeSort(int[] array) {
		if (array.length > 1) {
			// 获取左右子数组
			int mid = array.length / 2;
			int[] left = Arrays.copyOfRange(array, 0, mid);
			int[] right = Arrays.copyOfRange(array, mid, array.length);

			// 递归归并排序左子数组
			mergeSort(left);

			// 递归归并排序右子数组
			mergeSort(right);

			// 归并左右子数组
			int[] afterMergeArray = merge(left, right);
			System.arraycopy(afterMergeArray, 0, array, 0,
					afterMergeArray.length);
		}
	}

	/**
	 * 讲两个有序数组按升序归并到一个更大的数组中
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static int[] merge(int[] left, int[] right) {
		// 创建一个新的数组用于存放归并后的元素
		int[] newList = new int[left.length + right.length];

		int i = 0, j = 0, k = 0;
		while (i < left.length && j < right.length) {
			if (left[i] < right[j]) {
				newList[k++] = left[i++];
			} else {
				newList[k++] = right[j++];
			}
		}

		// 添加剩余的数组，二者只有其一执行
		while (i < left.length) {
			newList[k++] = left[i++];
		}

		while (j < right.length) {
			newList[k++] = right[j++];
		}

		return newList;
	}

}
