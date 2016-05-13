package com.newtalent.www.bstree;

import java.util.Iterator;



/**
 * 树 数据结构的公共操作，泛型接口
 * 注意泛型需要比较关键值，继承泛型Comparable
 * 
 * @author AllenLee
 *
 * @param <E>
 */
public interface Tree<E extends Comparable<E>> {
	
	/** 插入新节点 */
	public boolean insert(E e);
	
	/** 查找节点是否存在 */
	public boolean search(E e);
	
	/** 删除树节点 */
	public boolean delete(E e);
	
	/** 前序遍历 */
	public void preorderTraverse();
	
	/** 中序遍历 */
	public void inorderTraverse();
	
	/** 后序遍历 */
	public void postorderTraverse();
	
	/** 返回迭代器 */
	public Iterator iterator();
	
	/** 树节点个数 */
	public int getSize();
	
	/** 判空 */
	public boolean isEmpty();

}
