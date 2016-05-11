package com.newtalent.www;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;


/**
 * �����
 * ���ݽṹΪ����
 * �±��0��ʼ
 * ��i�Žڵ�������±꣺ 2i + 1
 * ��i�Žڵ�������±꣺ 2i + 2 
 *  
 * @author AllenLee
 *
 * @param <E>
 */
public class Heap<E extends Comparable> {
	private List<E> heapArray = new ArrayList<E>();

	/**
	 * ���캯��
	 * �������鹹����
	 * @param elements
	 */
	public Heap(E[] elements) {
		for (int i = 0; i < elements.length; i++) {
			addElement(elements[i]);
		}
	}
	
	/**
	 * �����������Ԫ�أ�Ҫά�ֶ�����
	 * �ڶѵײ�����Ԫ��
	 * �Ե����ϲ��ϱȽϣ�����Ԫ�ذ�����ǡ����λ��
	 * @param e
	 */
	public void addElement(E e) {
		
		heapArray.add(e);
		int currentIndex = heapArray.size()-1;
		
		
		
		while(currentIndex >= 0){
			//���ڵ��±�Ϊ ��i-1��/2;
			int parentIndex = (currentIndex-1)/2;
					
			if ( heapArray.get(currentIndex)
					.compareTo(heapArray.get(parentIndex)) > 0  ) {
				
				E tempMax = heapArray.get(currentIndex);
				heapArray.set(currentIndex,heapArray.get(parentIndex));
				heapArray.set(parentIndex, tempMax);
			}
			else {
				break;  //�Ѿ����ɴ���ѣ��˳�����
			}
			
			currentIndex = parentIndex ;
		}
	}
	
	/**
	 * ɾ���Ѷ�
	 * �����һ��Ҷ�ӽڵ�����Ϊ�Ѷ�
	 * �Զ��������µ���ά�ִ��������
	 * @return  �Ѷ�Ԫ��
	 */
	public E removeTop() {
		if (heapArray.isEmpty()) {
			return null;
		}
		
		E top = heapArray.get(0);		
		heapArray.set(0, heapArray.get( heapArray.size()-1 ) );
		heapArray.remove(heapArray.size()-1 );
		
		//�Զ����µ��������
		int currentIndex = 0;
		while (currentIndex < heapArray.size()) {
			int leftChildIndex = currentIndex*2 + 1;
			int rightChildIndex = currentIndex*2 + 2;
			
			//��ȡ���Һ����нϴ�ڵ���±�
			if (leftChildIndex >= heapArray.size()) {
				break;      //ֻ���������������ڵ�Ķ�
			}
			
			int maxIndexOfchilds = leftChildIndex;
			if (rightChildIndex < heapArray.size()) {
				if (heapArray.get(maxIndexOfchilds)
						.compareTo(heapArray.get(rightChildIndex)) < 0) {
					maxIndexOfchilds = rightChildIndex;
				}
			}
			
			if (heapArray.get(currentIndex)
						.compareTo(heapArray.get(maxIndexOfchilds)) < 0) {
				//�����ڵ����뺢�ӽڵ㣬�򸸽ڵ��½�
				E temp = heapArray.get(maxIndexOfchilds);
				heapArray.set(maxIndexOfchilds, heapArray.get(currentIndex));
				heapArray.set(currentIndex, temp);
				
				currentIndex = maxIndexOfchilds; 
			}
			else {
				break;   //����ѵ������
			}	
		}
		
		return top;
	}
	
	public int getSize() {
		return heapArray.size();
	}
}
