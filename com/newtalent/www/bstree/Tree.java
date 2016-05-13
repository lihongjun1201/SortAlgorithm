package com.newtalent.www.bstree;

import java.util.Iterator;



/**
 * �� ���ݽṹ�Ĺ������������ͽӿ�
 * ע�ⷺ����Ҫ�ȽϹؼ�ֵ���̳з���Comparable
 * 
 * @author AllenLee
 *
 * @param <E>
 */
public interface Tree<E extends Comparable<E>> {
	
	/** �����½ڵ� */
	public boolean insert(E e);
	
	/** ���ҽڵ��Ƿ���� */
	public boolean search(E e);
	
	/** ɾ�����ڵ� */
	public boolean delete(E e);
	
	/** ǰ����� */
	public void preorderTraverse();
	
	/** ������� */
	public void inorderTraverse();
	
	/** ������� */
	public void postorderTraverse();
	
	/** ���ص����� */
	public Iterator iterator();
	
	/** ���ڵ���� */
	public int getSize();
	
	/** �п� */
	public boolean isEmpty();

}
