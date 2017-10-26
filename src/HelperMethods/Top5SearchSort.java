package HelperMethods;
import java.util.*;

public class Top5SearchSort {
	
	public static void main(String[] args) {
		
		Scanner sc= new Scanner(System.in);
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		
		System.out.println("Enter your input");
		int input = sc.nextInt();
		int i =-1;
		
		while (input != -1){
			
			if (i ==-1){
				arrList.add(input);
				i++;
			}else{
			 int k =i;
			 
			 while(k>=0 && input> arrList.get(k)){
				 k--;
			 }
			 
			 arrList.add(k+1, input);
			 i++;
			}
			
			 System.out.println("Enter your input");
			 input = sc.nextInt();
			 
		}
		
		System.out.println(arrList);
	}

}
