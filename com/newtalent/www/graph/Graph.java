package com.newtalent.www.graph;

import java.util.List;

public interface Graph<V> {
	
	/** 返回图顶点个数 */ 
	public int getSize();

	/** 获取图的所有顶点 */
	public List<V> getAllVertices();
	
	/** 按索引坐标获取顶点元素 */
	public V getVertex(int index);
	
	/** 获取顶点元素的索引坐标 */
	public int getIndex(V v);
	
	/** 获取该顶点的所有相邻邻居顶点 */
	public List<Integer> getNeighbors(int index);
	
	/** 返回指定下标顶点 的 度数 */
	public int getDegree(int index);
	
	/** 返回该图的邻接矩阵 */
	public int[][] getAdjacentMatrix();
	
	/** 打印该图的邻接矩阵  */
	public void printAdjacentMatrix();
	
	/** 打印图的所有边 */
	public void printEdges();
	
	//dfs
	
	//bfs
	
}
