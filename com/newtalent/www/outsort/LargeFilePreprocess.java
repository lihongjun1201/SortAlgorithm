package com.newtalent.www.outsort;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.print.attribute.standard.OutputDeviceAssigned;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * 生成大文件
 * @author AllenLee
 *
 */
public class LargeFilePreprocess {

	
	/**
	 * 创建一个具有八十万个int整数的“大文件”，（假设内存放不下）
	 * @param filename
	 * @throws IOException
	 */
	public static void createLargeFile(String filename) throws IOException {
		try {
			DataOutputStream output = new DataOutputStream(
					new BufferedOutputStream(
					new FileOutputStream(filename)));
			
			System.out.println("creating...");
			for (int i = 0; i < 84; i++) {
				output.writeInt((int) (Math.random() * 1000000));
			}
			
			output.close();
			System.out.println("done！");
			
			DataInputStream input = new DataInputStream(
					new FileInputStream(filename));
			for (int i = 0; i < 20; i++) {
				System.out.print(input.readInt() + " ");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将大文件内部分成多个小分段
	 * 将小段文件排序，并将所有元素输出到f1中
	 * f1中包含多个有序的小Segment
	 * 
	 * @param segSize  内存允许的小分段大小
	 * @param filename
	 * @param tmpSegFileName
	 * @return
	 * @throws IOException
	 */
	public static int getSmallSortedSegments(int segSize,
											 String largeFileName,
											 String f1) throws IOException {
		//能够读入内存的限制大小数组
		int[] list = new int[segSize];
		
		//读取大文件
		//装饰器模式		
		DataInputStream inputStream	= new DataInputStream(
					new BufferedInputStream(
						new FileInputStream(largeFileName)));
		
		//输出文件流
				DataOutputStream outputStream = new DataOutputStream(
									new BufferedOutputStream(
										new FileOutputStream(f1)));
				
				
		
		
		int outSegFileCount = 0;
		
		while(inputStream.available() > 0){
		outSegFileCount++;
		
		//读取限制大小个元素到数组
		int i = 0;
		for (; inputStream.available() > 0 && i < segSize; i++) {
		list[i] = inputStream.readInt();
		}
		
		//排序
		Arrays.sort(list);
		
		
		//输出到小段文件
		for (int j = 0; j < i; j++) {
		outputStream.writeInt(list[j]);
		}
		

		System.out.println("输出其中一個smallSegment"+outSegFileCount);
		
		}
		
		//关闭流
		inputStream.close();
		outputStream.close();
		
		return outSegFileCount;
	}
	
	/**
	 * 将f1文件中的前一半分段复制到临时文件f2中
	 * 
	 * @param segmentsNum
	 * @param segmentSize
	 * @param f1
	 * @param tempf2
	 * @throws IOException
	 */
	public static void copyFirstHalf2TempFile(int segmentsNum,
											  int segmentSize,
											  DataInputStream f1,
											  DataOutputStream tempf2) throws IOException {
		for (int i = 0; i < (segmentsNum/2) * segmentSize; i++) {
			tempf2.writeInt(f1.readInt());
		}
	}
	
	/**
	 * 归并两个分段
	 * 将合并后有序的大分段输出到文件f3中
	 * 
	 * @param segSize
	 * @param f1
	 * @param f2
	 * @param f3
	 * @throws IOException
	 */
	public static void mergeTwoSegments(int segSize,
										DataInputStream f1,
										DataInputStream f2,
										DataOutputStream f3) throws IOException {
		int intValueInF1 = f1.readInt();
		int intValueInF2 = f2.readInt();
		
		int f1Count = 1, f2Count = 1;
		
		while(true){
			if (intValueInF1 < intValueInF2) {
				f3.writeInt(intValueInF1);
				
				//如果文件f1输入流读完，或者读入的整数个数大于等于分段大小
				//则直接将f2写入f3
				if (f1.available() == 0 || f1Count++ >= segSize) {
					f3.writeInt(intValueInF2);
					break;
				}
				else {
					//如果f1还有数据，则继续读入
					intValueInF1 = f1.readInt();
				}
			}
			else {
				f3.writeInt(intValueInF2);
				
				if (f2.available() == 0 || f2Count++ >= segSize) {
					f3.writeInt(intValueInF1);
					break;
				}
				else {
					intValueInF2 = f2.readInt();
				}		
			}
		}
		
		//将f1 或 f2 中剩余的数据写入f3即可，二只选一
		while (f1.available() > 0 && f1Count++ < segSize) {
			f3.writeInt(f1.readInt());
		}
		
		while (f2.available() > 0 && f2Count++ < segSize) {
			f3.writeInt(f2.readInt());
		}
	}
	
	/**
	 * 将f1文件（只剩下后半段） 与 f2（f1的前一半）中所有的分段进行归并
	 * 输出到f3文件中
	 * 
	 * @param segNums
	 * @param segSize
	 * @param f1
	 * @param f2
	 * @param f3
	 * @throws IOException
	 */
	public static void mergeAllSegments(int segNums,
										int segSize,
										DataInputStream f1,
										DataInputStream f2,
										DataOutputStream f3) throws IOException  {
		for (int i = 0; i < segNums; i++) {
			mergeTwoSegments(segSize, f1, f2, f3);
		}
		
		//原文件f1的分段若为奇数，则会多出一段，将该部分数据写入f3
		while(f1.available() > 0)
			f3.writeInt(f1.readInt());
		
	}
	
//	public static int getSmallSortedSegments(int segSize,
//											String LargeFilename) throws IOException {
//		//能够读入内存的限制大小数组
//		int[] list = new int[segSize];
//		
//		//读取大文件
//		//装饰器模式		
//		DataInputStream inputStream	= new DataInputStream(
//										new BufferedInputStream(
//											new FileInputStream(LargeFilename)));
//		
//		
//		
//		
//		int outSegFileCount = 0;
//		
//		while(inputStream.available() > 0){
//			outSegFileCount++;
//			
//			//读取限制大小个元素到数组
//			int i = 0;
//			for (; inputStream.available() > 0 && i < segSize; i++) {
//				list[i] = inputStream.readInt();
//			}
//			
//			//排序
//			Arrays.sort(list);
//			
//			//输出文件流
//			DataOutputStream outputStream = new DataOutputStream(
//												new BufferedOutputStream(
//													new FileOutputStream("segment"+outSegFileCount + ".dat")));
//			
//			
//			//输出到小段文件
//			for (int j = 0; j < i; j++) {
//				outputStream.writeInt(list[j]);
//			}
//
//			outputStream.close();
//			System.out.println("输出smallSegment"+outSegFileCount);
//		}
//		
//		//关闭流
//		inputStream.close();
//		
//	
//		return outSegFileCount;
//	}

	public static void main(String[] args) throws IOException {
		
		LargeFilePreprocess.createLargeFile("BigFile.dat");
		LargeFilePreprocess.getSmallSortedSegments(10, "BigFile.dat","f1.dat");
		
	}
}
