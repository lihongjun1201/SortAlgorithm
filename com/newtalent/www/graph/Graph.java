package com.newtalent.www.graph;

import java.util.List;

public interface Graph<V> {
	
	/** ����ͼ������� */ 
	public int getSize();

	/** ��ȡͼ�����ж��� */
	public List<V> getAllVertices();
	
	/** �����������ȡ����Ԫ�� */
	public V getVertex(int index);
	
	/** ��ȡ����Ԫ�ص��������� */
	public int getIndex(V v);
	
	/** ��ȡ�ö�������������ھӶ��� */
	public List<Integer> getNeighbors(int index);
	
	/** ����ָ���±궥�� �� ���� */
	public int getDegree(int index);
	
	/** ���ظ�ͼ���ڽӾ��� */
	public int[][] getAdjacentMatrix();
	
	/** ��ӡ��ͼ���ڽӾ���  */
	public void printAdjacentMatrix();
	
	/** ��ӡͼ�����б� */
	public void printEdges();
	
	//dfs
	
	//bfs
	
}
