package search;
import java.io.BufferedReader;
import java.io.*;
import java.util.*;


public class LittleSearchEngineApp {
	static Scanner  sc; static BufferedReader bf;
	public static final  int keyword = 0;

	public static void main(String[] args)throws Exception {
		
		LittleSearchEngine object = new LittleSearchEngine();
		boolean access = true;
	
		bf = new BufferedReader (new FileReader("noiseWords.txt"));//adding noise words
		 String word1="";
		 
	while ((word1 = bf.readLine())!=null) {
		
		word1= word1.trim();										
		object.noiseWords.put(word1,word1);
		
	} 
	
	while (access)	{
		
		
	try {
		
		
		System.out.println("to test getword-------> press 0"
				+ "\nto test loadWord------> press 1"
				+ "\nto test mergeKeyword--> press 2"
				+ "\nto test lastOccurence-> press 3"
				+ "\nto test the makeIndex-> press 4"
				+ "\nto test top5 Search---> press 5"
				+"\nto reset all the data-> press 10"
				+ "\nto Quit---------------> press 6");
		
		
		sc = new Scanner(System.in);
		int input= sc.nextInt();
		
		while (input !=6){
			
		switch(input){
		
		case 0:  System.out.println("Enter your input"); //case 0
		System.out.println("Result: "+object.getKeyWord(sc.next()));
		break;
		
		
		case 1:  // case 1
			
		System.out.println("Enter the name of your file");
		String fileName = sc.next();
		
		System.out.println("Result: "+object.loadKeyWords(fileName));
			break;
			
			
		case 2: //case 2 // fist we have repeat case 1
		
			System.out.println("first you have to hash each file to merger them..\nHow many files to be hashed?");
			int userInput = sc.nextInt();
			
			for (int i =0; i<userInput ; i++){
				
				System.out.println("Enter the name of your file");
				fileName = sc.next();
				object.mergeKeyWords(object.loadKeyWords(fileName));
			}
			
			System.out.println("Words Index: "+object.keywordsIndex);
			break;
			
			
		case 3 : 
			
			System.out.println("enter the name of the files");
			
			fileName = sc.next(); 
			bf = new BufferedReader(new FileReader(fileName));
			
			
			String key="";
			
			while((fileName=bf.readLine())!=null){
				
				StringTokenizer st = new StringTokenizer(fileName.trim(),",");
				
				Occurrence toInsert = new Occurrence(key=st.nextToken(), Integer.parseInt(st.nextToken()));
				object.Insert(toInsert,"key");
				
			
			}
			ArrayList<Occurrence> toSend = object.keywordsIndex.get("key");
			System.out.println("Result Indexes: "+object.insertLastOccurrence(toSend));
			break;
			
		case 4: 
			System.out.println("Enter the name of the file where all the names of the other files are stored");
			fileName = sc.next();
			object.makeIndex(fileName, "tp2.txt");
			
			System.out.println("\n\nnoise words = "+object.noiseWords);
			System.out.print("\nkey words Index = "+object.keywordsIndex);
			break;
			
		case 5: 
			//first adding the words to keywords index
			object.makeIndex("docs.txt", "noisewords.txt");
			System.out.println("NOTE: All the words in KeywordsIndex are retrieved from the 'docs.txt' file.\n"
					+ "Change the name of the files in docs.txt if you wish to store different words in the KeywordsIndex\n");
			
			//now asking the inputs from the user by which you will search it in keywordsIndex
			System.out.println("Enter your first word");
			String word= sc.next();
			System.out.println("Enter you second word");
			String word2 = sc.next();
			System.out.println("\n");
			
			System.out.println("\nResult List : "+object.top5search(word, word2));
			break;
			
			
		case 10: //resetting all the stored data by making a new object
			//keywordsIndex and noisewordsIndex
			
			System.out.println("resetting...");
			object= new LittleSearchEngine(); //resets everything
			System.out.println("resetted successfully");
			
			
			bf = new BufferedReader (new FileReader("noiseWords.txt"));//adding noise words
			  word1="";
		while ((word1 = bf.readLine())!=null) {
			word1= word1.trim();										

			object.noiseWords.put(word1,word1);
		}	
		break;
		
		
			
			
		
		}
		
		System.out.println("\n\nto test getword-------> press 0"
				+ "\nto test loadWord------> press 1"
				+ "\nto test mergeKeyword--> press 2"
				+ "\nto test lastOccurence-> press 3"
				+ "\nto test the makeIndex-> press 4"
				+ "\nto test top5 Search---> press 5"
				+"\nto rest all the data--> press 10"
				+ "\nto Quit---------------> press 6");
		input = sc.nextInt();
		
		}
		
		if (input == 6){
			
			System.out.println("\n\tPROGRAM TERMINATED");
			access = false;
		}
	}
	catch (InputMismatchException e){
		
		System.out.println("\n\tWRONG INPUT!--> Program resetting...");
		System.out.println("\tResetted successfully\n");
		}
	catch(FileNotFoundException e){
		
		System.out.println("\n\tWRONG INPUT!--> Program resetting...");
		System.out.println("\tResetted successfully\n");
		}
	
	
	}
	
	
		
		
	
		
		

	}
	
	
	

}
