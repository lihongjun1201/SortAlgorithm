package com.newtalent.www;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Ͱ���� ����С������ֵ������Ͱ������Ժ�ʱ O(n+N),�ռ�O(n+N) nλ����Ԫ�ظ���,NΪ��ֵ��С��Χ
 * 
 * @author AllenLee
 * 
 */
public class BucketSort {

	// ��N = 10��Ͱ������0->9������
	// ÿ��Ͱ����һ��ArrayList
	public static final int N = 10;

	public static void buckedSort(int[] list, int index) {
		// ����N��Ͱ����
		ArrayList[] buckets = new ArrayList[N];

		// ����Ͱ
		for (int i = 0; i < N; i++) {
			buckets[i] = new ArrayList<Integer>();
		}

		// ��Ԫ�ط����ӦKEYֵ��Ͱ��
		for (int i = 0, base = (int) Math.pow(10,index-1); i < list.length; i++) {
			
			int key = (list[i]/(base)) %  10;
			buckets[key].add(list[i]);
		}

		// ��Ͱ�а����ó���Ԫ��
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

		//��ȡ���������Ԫ��
		int max = array[0];
		for (int i = 0; i < array.length; i++) {
			if (max < array[i]) {
				max = array[i];
			}
		}
		
		//��ȡ��������ж���λ��
		int n = String.valueOf(max).length();

		//������ʮ���٣�ǧ....ÿλ��ֵ����Ͱ�������
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
