package com.newtalent.www;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;


/**
 * 大根堆
 * 数据结构为数组
 * 下标从0开始
 * 第i号节点的左孩子下标： 2i + 1
 * 第i号节点的左孩子下标： 2i + 2 
 *  
 * @author AllenLee
 *
 * @param <E>
 */
public class Heap<E extends Comparable> {
	private List<E> heapArray = new ArrayList<E>();

	/**
	 * 构造函数
	 * 传入数组构建堆
	 * @param elements
	 */
	public Heap(E[] elements) {
		for (int i = 0; i < elements.length; i++) {
			addElement(elements[i]);
		}
	}
	
	/**
	 * 往堆中添加新元素，要维持堆特性
	 * 在堆底插入新元素
	 * 自底向上不断比较，将新元素安放在恰当的位置
	 * @param e
	 */
	public void addElement(E e) {
		
		heapArray.add(e);
		int currentIndex = heapArray.size()-1;
		
		
		
		while(currentIndex >= 0){
			//父节点下标为 （i-1）/2;
			int parentIndex = (currentIndex-1)/2;
					
			if ( heapArray.get(currentIndex)
					.compareTo(heapArray.get(parentIndex)) > 0  ) {
				
				E tempMax = heapArray.get(currentIndex);
				heapArray.set(currentIndex,heapArray.get(parentIndex));
				heapArray.set(parentIndex, tempMax);
			}
			else {
				break;  //已经建成大根堆，退出迭代
			}
			
			currentIndex = parentIndex ;
		}
	}
	
	/**
	 * 删除堆顶
	 * 将最后一个叶子节点提升为堆顶
	 * 自顶向下重新调整维持大根堆性质
	 * @return  堆顶元素
	 */
	public E removeTop() {
		if (heapArray.isEmpty()) {
			return null;
		}
		
		E top = heapArray.get(0);		
		heapArray.set(0, heapArray.get( heapArray.size()-1 ) );
		heapArray.remove(heapArray.size()-1 );
		
		//自顶向下调整大根堆
		int currentIndex = 0;
		while (currentIndex < heapArray.size()) {
			int leftChildIndex = currentIndex*2 + 1;
			int rightChildIndex = currentIndex*2 + 2;
			
			//获取左右孩子中较大节点的下标
			if (leftChildIndex >= heapArray.size()) {
				break;      //只有三个或者两个节点的堆
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
				//若父节点下与孩子节点，则父节点下降
				E temp = heapArray.get(maxIndexOfchilds);
				heapArray.set(maxIndexOfchilds, heapArray.get(currentIndex));
				heapArray.set(currentIndex, temp);
				
				currentIndex = maxIndexOfchilds; 
			}
			else {
				break;   //大根堆调整完成
			}	
		}
		
		return top;
	}
	
	public int getSize() {
		return heapArray.size();
	}
}
