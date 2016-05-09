package com.newtalent.www;

import java.util.Arrays;

/**
 * �鲢����
 * 
 * @author AllenLee
 * 
 */
public class MergeSort {

	/**
	 * �鲢���򣬵ݹ���η� ��������֣��ֵ�ֻ��һ��Ԫ�أ���ʱ���й鲢���飬�ݹ鷵�ء�
	 * 
	 * @param array
	 */
	public static void mergeSort(int[] array) {
		if (array.length > 1) {
			// ��ȡ����������
			int mid = array.length / 2;
			int[] left = Arrays.copyOfRange(array, 0, mid);
			int[] right = Arrays.copyOfRange(array, mid, array.length);

			// �ݹ�鲢������������
			mergeSort(left);

			// �ݹ�鲢������������
			mergeSort(right);

			// �鲢����������
			int[] afterMergeArray = merge(left, right);
			System.arraycopy(afterMergeArray, 0, array, 0,
					afterMergeArray.length);
		}
	}

	/**
	 * �������������鰴����鲢��һ�������������
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static int[] merge(int[] left, int[] right) {
		// ����һ���µ��������ڴ�Ź鲢���Ԫ��
		int[] newList = new int[left.length + right.length];

		int i = 0, j = 0, k = 0;
		while (i < left.length && j < right.length) {
			if (left[i] < right[j]) {
				newList[k++] = left[i++];
			} else {
				newList[k++] = right[j++];
			}
		}

		// ���ʣ������飬����ֻ����һִ��
		while (i < left.length) {
			newList[k++] = left[i++];
		}

		while (j < right.length) {
			newList[k++] = right[j++];
		}

		return newList;
	}

}
