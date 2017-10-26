package HelperMethods;
import java.util.*;

public class BinarySearch {

	public static ArrayList<Integer> binarySearch(ArrayList<Integer> input, int value){
		
		ArrayList<Integer> indexes= new ArrayList<Integer>();
		
		if (input.size()<1){
			indexes.add(-1);
		return  indexes;
		}
		
		int left = 0;
		int right= input.size()-1;
		
		
		while(left<= right){
			
			int mid = (left+right)/2;
			indexes.add(mid);
			
			if (input.get(mid)<value){
				
				right = mid-1;
				
			}else{
				
				left= mid+1;
			}
		}
		
		return indexes;
	}
	public static void main(String[] args) {
		
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("Enter the value");
		 int value = sc.nextInt();
		 
		 while(value!=-1){
			 
			 ArrayList<Integer> indexes = binarySearch(arrList, value);
			 int lastIndex = indexes.get(indexes.size()-1); // last index of the indexes
			int firstIndex =  indexes.get(0); //mid indexes of the indexes 
			int mid = indexes.get((0+indexes.size()-1)/2);
													
			
			 
			 if  (lastIndex ==-1){
				 arrList.add(value);
				 indexes.add(0,0);// so we wont get error in the next statement
			 }
			 else if (value> arrList.get(lastIndex)){
				 
				 arrList.add(lastIndex,value); 
			 }else if (value >arrList.get(firstIndex) && firstIndex != lastIndex){
				
				 arrList.add(firstIndex,value);
			 }else if (value> arrList.get(mid)){
				 
				 arrList.add(mid,value);
			 }
			 else{
				 arrList.add(value);
			 }
			 System.out.println(arrList);
			 System.out.println("Enter the value");
			 value = sc.nextInt();
			 
			 
		 }
		 
	}
}
