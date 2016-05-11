package com.newtalent.www;

public class QuickSort {
	
	//快排
	public static void quickSort(int[] a) {
		_quickSort(a, 0, a.length -1);
	}
	
	//递归
	public static void _quickSort(int[] a,int low,int high) {
	
		if (low < high) {
			//int pivot = partition(a, low, high);
			int pivot = partition2(a, low, high);
			
			_quickSort(a, low, pivot-1);
			_quickSort(a, pivot+1, high);
		}
	}

	//划分
	public static int partition(int[] a,int l ,int h) {
		int low = l,high = h;
		int pivot = a[low];
		while (low < high) {
			while (low < high && a[high] >= pivot ) 
				high--;
			
			//交换
			int temp = a[low];
			a[low] = a[high];
			a[high] = temp;
			
			while (low < high && a[low] <= pivot ) 
				low++;
			
			//交换
			temp = a[high];
			a[high] = a[low];
			a[low] = a[high];
		}
		
		a[low] = pivot;
		return low;
	}
	
	//----------------------------------------------------------
	
	//算法导论版本
	
/*    p = 3
 *     i j
 *       5 1 4 2 3
 *       
 *       5 1 4 2 5
 */
	
	public static int partition2 (int[] a,int low ,int high) {
		int pivot = a[high];
		int i = low-1 , j = low;
			
		for ( ; j < a.length; j++) {
			if (a[j] < pivot) {
				i++;
				
				int temp = a[i];
				a[i] = a[j];
				a[j] = temp;
				
			}
		}
		
		int temp = a[i+1];
		a[i+1] = a[high];
		a[high] = temp;
		
		return i+1;
	}
	
	
	
	public static void main(String[] args) {
		int[] unsortedArray = {5,4,2,1,3,4};
//		int[] unsortedArray = {5,4,3,2,12,5,3,4,6,1};
		
//		System.out.println(partition2(unsortedArray, 0,unsortedArray.length-1 ));
		_quickSort(unsortedArray,0,unsortedArray.length-1);
		
		for (int i = 0; i < unsortedArray.length; i++) {
			System.out.print(unsortedArray[i] + " ");
		}
	}

}
