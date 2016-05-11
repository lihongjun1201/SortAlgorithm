package com.newtalent.www;


/**
 * ð������
 * @author AllenLee
 *
 */
public class BubbleSort {

	/**
	 * ����ͨ��ð������
	 * @param a
	 */
	public static void bubbleSort(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = 0; j < a.length -1 - i; j++) {
				if (a[j] > a[j+1]) {
					int temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;
				}
			}		
		}
	}
	
	/**
	 * �Ľ����ð������
	 * ���flay��־
	 * @param args
	 */
	public static void bubbleSort_withFlag(int[] a) {
		boolean needNextPass = true;
		for (int i = 0; i < a.length - 1 && needNextPass ; i++) {
			needNextPass = false;
			for (int j = 0; j < a.length -1 - i; j++) {
				if (a[j] > a[j+1]) {
					int temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;
					
					needNextPass = true;
				}	
			}		
		}
	}
	
	
	/**
	 * �ݹ�ʵ��
	 * @param a
	 * @param length
	 */
	public static void bubbleSort_recursive(int[] a,int length) {
			if (length > 1) {
				bubbleSort_recursive(a,length - 1);	
				
				for (int j = 0; j < length - 1; j++) {
					for (int i = 0; i < length - 1 - j; i++) {
						if (a[i] > a[i+1]) {
							int temp = a[i];
							a[i] = a[i+1];
							a[i+1] = temp;
					}	
				}			
			}
		}
	}
	
	
	public static void main(String[] args) {
		int[] unsortedArray = {6,7,3,2,5,4,1};
		bubbleSort_recursive(unsortedArray,unsortedArray.length);
		for (int i = 0; i < unsortedArray.length; i++) {
			System.out.print(unsortedArray[i] + " ");
		}
	}

}
