package com.newtalent.www;


/**
 * 插入排序
 * @author AllenLee
 *
 */
public class InsertSort {
	
	
	public static void insertSort(int[] a) {
		int insertIndex = 0 ,insertValue;
		
		//将数组第一个元素当做有序列，从第二个元素开始逐个比较，如果比前面小，则需要插入
		for (int i = 1; i < a.length; i++) {
			if (a[i-1] > a[i] ) {
				//暂存插入值
				 insertValue = a[i];
				
				 //将前面有序数组部分，比将要插入的值大的元素从前向后移动一格
				for (int j = i-1 ; j>=0 && a[j] > insertValue; j--) {
					a[j+1] = a[j];
					insertIndex = j;
				}
				
				//在正确位置插入，index最小为0，即要插到头部
				a[insertIndex] = insertValue;
			}
		}
	}
	

	public static void main(String[] args) {
		int[] unsortedArray = {25,42,13,62,11};
		
		insertSort(unsortedArray);
		
		for (int i = 0; i < unsortedArray.length; i++) {
			System.out.print(unsortedArray[i] + " ");
		}
	}

}
