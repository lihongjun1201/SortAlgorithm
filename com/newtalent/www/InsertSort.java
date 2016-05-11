package com.newtalent.www;


/**
 * ��������
 * @author AllenLee
 *
 */
public class InsertSort {
	
	
	public static void insertSort(int[] a) {
		int insertIndex = 0 ,insertValue;
		
		//�������һ��Ԫ�ص��������У��ӵڶ���Ԫ�ؿ�ʼ����Ƚϣ������ǰ��С������Ҫ����
		for (int i = 1; i < a.length; i++) {
			if (a[i-1] > a[i] ) {
				//�ݴ����ֵ
				 insertValue = a[i];
				
				 //��ǰ���������鲿�֣��Ƚ�Ҫ�����ֵ���Ԫ�ش�ǰ����ƶ�һ��
				for (int j = i-1 ; j>=0 && a[j] > insertValue; j--) {
					a[j+1] = a[j];
					insertIndex = j;
				}
				
				//����ȷλ�ò��룬index��СΪ0����Ҫ�嵽ͷ��
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
