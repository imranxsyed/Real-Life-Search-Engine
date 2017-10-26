package HelperMethods;
import java.util.*;

public class GetKeyWords {
	
	public static final String delim = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUZWXYZ";

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("input?");
		String input = sc.next();
		
		int length = input.length();
		
		while(length>0 && !(Character.isLetter(input.charAt(length-1)))){
			
			length--;
		}
		input = input.substring(0,length);
		StringTokenizer st= new StringTokenizer(input,delim);
		
		
		System.out.println(input);
		System.out.println(st.countTokens());
		System.out.println("Process Complete");



	}

}
