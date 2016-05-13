package com.newtalent.www.outsort;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class OutSort {
	public static final int MAX_ARRAY_SIZE = 10;
	public static final int BUFFER_SIZE = 10;
	
	
	public static void main(String[] args) throws Exception {
		
		createLargeFile("BigFile.dat");
		
		outSort("BigFile.dat","sortedFile.dat");
		
		displayFile("sortedFile.dat");
		
	}
	
	public static void outSort(String sourcefile,String targetfile) throws Exception {
		int segmentsNum = getSmallSortedSegments(MAX_ARRAY_SIZE, sourcefile, "f1.dat");
		
		merge(segmentsNum, MAX_ARRAY_SIZE, "f1.dat", "f2.dat", "f3.dat", targetfile);		
	}
	
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
			
			System.out.println("正在创建大文件" + filename );
			System.out.println("请稍后...\n");
			for (int i = 0; i < 84; i++) {
				output.writeInt((int) (Math.random() * 100000));
			}
			
			output.close();
			System.out.println("创建完成！\n");

			
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
				
		
		System.out.println("开始将大文件" + largeFileName +  "分成多个小段...");
		
		
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
		

		System.out.println("输出 ：有序Segment"+outSegFileCount + "到文件 " + f1);
		
		}
		
		System.out.println("分段完成！准备递归归并各分段!\n");
		System.out.println("正在归并，请稍后...");
		
		//关闭流
		inputStream.close();
		outputStream.close();
		
		return outSegFileCount;
	}
	
	/**
	 * 递归调用归并排序
	 * 
	 * @param segNums
	 * @param segSize
	 * @param f1
	 * @param f2
	 * @param f3
	 * @param targetfile
	 * @throws Exception
	 */
	public static void merge(int segNums,int segSize,
					String f1,String f2,String f3,String targetfile) throws Exception {
		System.out.println("此时待归并段个数： " + segNums );
		
		
		if (segNums > 1) {
			//将f1 拆分为两半，分别有序，依旧按照最大内存数组大小 ，递归归并
			mergeOneStep(segNums, segSize, f1, f2, f3);
			System.out.println( "归并 " + f1 + " 与 " + f2 + ",写入临时文件  " + f3 + "...\n");
			
			//递归归并，待归并段数减少，每段长度增加，更换临时文件
			merge( (segNums + 1)/2 , segSize * 2, f3, f1, f2, targetfile);
			
			
		}
		else {
			File sortedFile = new File(targetfile);
			//若存在，则删除
			if (sortedFile.exists()) {
				sortedFile.delete();
			}
			//改名
			new File(f1).renameTo(sortedFile);
		}

	}
	
	public static void mergeOneStep (int segNums,int segSize,
			String f1,String f2,String f3) throws Exception {
		
		//读入f1输入流
		DataInputStream f1Input = new DataInputStream(
				new BufferedInputStream(new FileInputStream(f1),BUFFER_SIZE));
		
		//输出f2输出流
		DataOutputStream f2Output = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream(f2),BUFFER_SIZE));
		
		//将f1 前半部分复制到 f2中
		System.out.println("将" + f1 + "前半部分移入 " + f2 + "..."  );
		copyFirstHalf2TempFile(segNums, segSize, f1Input, f2Output);
		f2Output.close();
		
		//获取f2输入流
		DataInputStream f2Input = new DataInputStream(
						new BufferedInputStream(new FileInputStream(f2),BUFFER_SIZE));
				
		//获取f3输出流，存放归并 f1，与 f2 后的数据
		DataOutputStream f3Output = new DataOutputStream(
						new BufferedOutputStream(new FileOutputStream(f3),BUFFER_SIZE));
				
		//归并f1（剩下原来的 后半部） 和 f2（f1原来的前半部）
		//输出到f3中
		mergeAllSegments(segNums/2, segSize, f1Input, f2Input, f3Output);
		
		//关闭所有流
		f1Input.close();
		f2Input.close();
		f3Output.close();
		
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
	
	

	/**
	 * 打印排好序的大文件 前 100个数
	 * @param fileName
	 */
	private static void displayFile(String fileName) {
		System.out.println("\n大文件外部归并排序完成！\n结果如下(前50个元素)：");
		

		try {
			DataInputStream  inputStream = new DataInputStream(
					new BufferedInputStream(
							new FileInputStream(fileName)));
			
			for (int i = 0; i < 50; i++) {
				System.out.println(inputStream.readInt());
			}
			
			inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
	
}
