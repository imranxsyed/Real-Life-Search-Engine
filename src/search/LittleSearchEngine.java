package search;
//My java file
import java.io.*;
import java.util.*;

/**
 * This class encapsulates an occurrence of a keyword in a document. It stores
 * the document name, and the frequency of occurrence in that document.
 * Occurrences are associated with keywords in an index hash table.
 * 
 * @author Sesh Venugopal
 * 
 */
class Occurrence {
	/**
	 * Document in which a keyword occurs.
	 */
	String document;

	/**
	 * The frequency (number of times) the keyword occurs in the above document.
	 */
	int frequency;

	/**
	 * Initializes this occurrence with the given document,frequency pair.
	 * 
	 * @param doc
	 *            Document name
	 * @param freq
	 *            Frequency
	 */
	public Occurrence(String doc, int freq) {
		document = doc;
		frequency = freq;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + document + "," + frequency + ")";
	}

}

/**
 * This class builds an index of keywords. Each keyword maps to a set of
 * documents in which it occurs, with frequency of occurrence in each document.
 * Once the index is built, the documents can searched on for keywords.
 *
 */
public class LittleSearchEngine {

	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and
	 * the associated value is an array list of all occurrences of the keyword
	 * in documents. The array list is maintained in descending order of
	 * occurrence frequencies.
	 */
	HashMap<String, ArrayList<Occurrence>> keywordsIndex;

	/**
	 * The hash table of all noise words - mapping is from word to itself.
	 */
	HashMap<String, String> noiseWords;

	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String, ArrayList<Occurrence>>(1000, 2.0f);
		noiseWords = new HashMap<String, String>(100, 2.0f);
	}

	/**
	 * This method indexes all keywords found in all the input documents. When
	 * this method is done, the keywordsIndex hash table will be filled with all
	 * keywords, each of which is associated with an array list of Occurrence
	 * objects, arranged in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile
	 *            Name of file that has a list of all the document file names,
	 *            one name per line
	 * @param noiseWordsFile
	 *            Name of file that has a list of noise words, one noise word
	 *            per line
	 * @throws FileNotFoundException
	 *             If there is a problem locating any of the input files on disk
	 */
	public void Insert(Occurrence object, String key){ //for littleSearchEnginApp's purposes
		
		if (keywordsIndex.containsKey(key)){
			
			ArrayList<Occurrence> temp = keywordsIndex.get(key);
			temp.add(object);
		}else{
			
			ArrayList<Occurrence> temp = new ArrayList<Occurrence>();
			temp.add(object);
			keywordsIndex.put(key, temp);	
			
		}
		
	} 
	
	public void makeIndex(String docsFile, String noiseWordsFile) throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.put(word, word);
		}

		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String, Occurrence> kws = loadKeyWords(docFile);
			mergeKeyWords(kws);
		}

	}

	/**
	 * Scans a document, and loads all keywords found into a hash table of
	 * keyword occurrences in the document. Uses the getKeyWord method to
	 * separate keywords from other words.
	 * 
	 * @param docFile
	 *            Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated
	 *         with an Occurrence object
	 * @throws FileNotFoundException
	 *             If the document file is not found on disk
	 */
	public HashMap<String, Occurrence> loadKeyWords(String docFile) throws FileNotFoundException {
		
		HashMap<String, Occurrence> toLoad = new HashMap<String, Occurrence>(1000, 2.0f);//making a new hasmap to load eveyrthing
		
		try {//checking weather the file name exists
			Scanner sc = new Scanner(new File(docFile)); //gets file into sc

			while (sc.hasNextLine()) { //runs until the file is empty

				StringTokenizer st = new StringTokenizer(sc.nextLine()); //tokenizes every line to get each word

			 while (st.hasMoreTokens()) { // goes through word by word until the line ends

			   String value = st.nextToken().toLowerCase(); // getting a value not
													// knowing if it is an
													// eligible word or not;
				String word = getKeyWord(value);

				if (word != null) {//checkes weather the word is eligible

				boolean exists = toLoad.containsKey(word);//checks weather the words exists

				if (exists) { // if the word exists already

				Occurrence hold = toLoad.remove(word); // removing
																	// an
																	// existing
																	// word
				hold.frequency += 1; // increasing frequency
				toLoad.put(word, hold); // putting it back

				} 
				
				else { // if the word does not exist

				Occurrence toInsert = new Occurrence(docFile, 1); // making
																				// a
																				// new
																				// object
				toLoad.put(word, toInsert); // adding the new value
				}
				} // if the word is not eligible, jut goes to to the next
						// value

				}
			}
			return toLoad; // returns the hashmap with all the loaded values

		} catch (FileNotFoundException e) {// catches if the filename was not found
			throw new FileNotFoundException(); //throws exception
		}

	}

	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document must
	 * be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash
	 * table. This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws
	 *            Keywords hash table for a document
	 */
	public void mergeKeyWords(HashMap<String, Occurrence> kws) {
		// COMPLETE THIS METHOD
		
		Set<String> keyWords = kws.keySet();
		boolean exists = false;
		
		
		for (Iterator<String> iterator = keyWords.iterator(); iterator.hasNext();) {

			String key = iterator.next().toLowerCase();
			Occurrence object = kws.get(key);
			
			exists =keywordsIndex.containsKey(key);//checks if the word already exists in the keywordsIndex
			
			if (exists){// if the words exists in keywords index
				
				ArrayList<Occurrence> arrList = keywordsIndex.get(key); //receives arrayList for that word
				
				arrList.add(object); // adds that words into the arrayList
				insertLastOccurrence(arrList);//positin the last entry accordingly
				
				
			}else{// if the word does not exists
				
			ArrayList<Occurrence> arrList = new ArrayList<Occurrence>(); //makes a new arraylist
			arrList.add(object);// adds that object into the new arrayList
			keywordsIndex.put(key, arrList);//adds the above arrayList into the keywordsIndex
			
			}

		}
		

	}

	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped
	 * of any TRAILING punctuation, consists only of alphabetic letters, and is
	 * not a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word
	 *            Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyWord(String word) {

		String delim = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		int length = word.length();

		while (length > 0 && !(Character.isLetter(word.charAt(length - 1)))) {

			length--;
		}
		word = word.substring(0, length);

		StringTokenizer st = new StringTokenizer(word, delim);
		boolean noiseCheck = noiseWords.containsValue(word.toLowerCase());

		if (st.countTokens() == 0 && noiseCheck == false) {

			return word.toLowerCase();
		}
		return null;

	}

	/**
	 * Inserts the last occurrence in the parameter list in the correct position
	 * in the same list, based on ordering occurrences on descending
	 * frequencies. The elements 0..n-2 in the list are already in the correct
	 * order. Insertion of the last element (the one at index n-1) is done by
	 * first finding the correct spot using binary search, then inserting at
	 * that spot.
	 * 
	 * @param occs
	 *            List of Occurrences
	 * 
	 * @return Sequence of mid point indexes in the input list checked by the
	 *         binary search process, null if the size of the input list is 1.
	 *         This returned array list is only used to test your code - it is
	 *         not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {

		ArrayList<Integer> indexes= new ArrayList<Integer>(); // to send back
																// with filled
																// indexes

		if (occs.size() < 2) { // if only one element in the arrayList

			
			return null;//should never happpen, made sure by the makeIndex method
		}

		Occurrence value = occs.remove(occs.size() - 1); // taking out the last
															// object

		 indexes = binarySearch(occs, value);//sends it to binary search
		 
		 
		 	int lastIndex = indexes.get(indexes.size()-1); // last index of the indexes
			int firstIndex =  indexes.get(0); //first indexes of the indexes 
			int mid = indexes.get((0+indexes.size()-1)/2);//middle index
			
			if (value.frequency> occs.get(lastIndex).frequency){
				 
				 occs.add(lastIndex,value); 
			 }
			
			else if (value.frequency >occs.get(firstIndex).frequency && firstIndex != lastIndex){
				
				 occs.add(firstIndex,value);
				 
			 }
			
			else if (value.frequency>occs.get(mid).frequency){
				 
				 occs.add(mid,value);
			 }
			
			
			 else{
				 occs.add(value);
			 }
		return indexes;
	}

	private ArrayList<Integer> binarySearch(ArrayList<Occurrence> input, Occurrence value) {

		ArrayList<Integer> indexes = new ArrayList<Integer>();

		int left = 0;
		int right = input.size() - 1;
		

		while (left <= right) {

			int mid = (left + right) / 2;
			indexes.add(mid);
			

			if (input.get(mid).frequency < value.frequency) {

				right = mid - 1;

			}
			else if(input.get(mid).frequency== value.frequency){
				
				break;
			}
			else {

				left = mid + 1;
			}
		}
		return indexes;

	}

	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or
	 * kw2 occurs in that document. Result set is arranged in descending order
	 * of occurrence frequencies. (Note that a matching document will only
	 * appear once in the result.) Ties in frequency values are broken in favor
	 * of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and
	 * kw2 is in doc2 also with the same frequency f1, then doc1 will appear
	 * before doc2 in the result. The result set is limited to 5 entries. If
	 * there are no matching documents, the result is null.
	 * 
	 * @param kw1
	 *            First keyword
	 * @param kw1
	 *            Second keyword
	 * @return List of NAMES of documents in which either kw1 or kw2 occurs,
	 *         arranged in descending order of frequencies. The result size is
	 *         limited to 5 documents. If there are no matching documents, the
	 *         result is null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		
		kw1= kw1.toLowerCase(); kw2= kw2.toLowerCase();

		ArrayList<String> outputNames = new ArrayList<String>(); // the arrrayList method returns
		
		
		
		boolean existskw1 = keywordsIndex.containsKey(kw1); //checking if the list exists for word kw1
		boolean existskw2 =  keywordsIndex.containsKey(kw2);//checking if the list exists for word kw2
		ArrayList<Occurrence> kw1List = new ArrayList<Occurrence>();//the arrayList where all the values of kw1 goes
		ArrayList<Occurrence> kw2List=  new ArrayList<Occurrence>();//the arrayList where all the values of kw2 goes
		boolean sameValue =false; //variable for checking weather the frequencies of some two object is the same
		
		if (existskw1){ // check if list exists for word kw1, adds the values of kw1List if yes, else keeps kw1List empty. necessary for the nor changing the actual list
			
			for (int i=0 ; i<keywordsIndex.get(kw1).size();i++){
			
				kw1List.add(keywordsIndex.get(kw1).get(i));
			
			}
		}
		if (existskw2){ // check if list exists for word kw2, adds the values of kw1List if yes, else keeps kw2List empty. neccessary for not changing the actual list
			
			for (int i=0 ; i<keywordsIndex.get(kw2).size();i++){
				
				kw2List.add(keywordsIndex.get(kw2).get(i));
			
			}
			
		}
		
		if (!(existskw1 || existskw2)){ //checks if neither of the list exists for kw1 and kw2, returns null if yes else proceeds
			
			return null;
		}
		
		
		System.out.println(kw1+" List: "+kw1List+"\n"+kw2+" List: "+kw2List); //printing the lists.. for checking purposes. it makes it easier what was the actual list for kw1 and kw2
		
		if (existskw1 && existskw2){ // if both of the list occurs
			
			for (int i=0 ; (i<5 && existskw1 && existskw2); ){//runs the loop until the outputNames arrayList has reached 5 or one of the list becomes empty
				
					
					int value = kw1List.get(0).frequency; //getting first value of kw1 from its kw1List
					int secondValue =kw2List.get(0).frequency;//getting first value of kw2 from its kw2List
				
				if (value>=secondValue){ //checking if the first value of kw1List is greater than first value of kw2List
					
					sameValue = value== secondValue; //check weather value and secondValue have the same frequencies
					int index =outputNames.indexOf( kw1List.get(0).document);//gets the index of the recent value in kw1List if it exists else gets -1
					
					if (index == -1){ //making sure it does not exists in the outputNames list, and proceeds
						
					outputNames.add(kw1List.get(0).document); //adds the value to outputNames
					kw1List.remove(0); //removes that value of kw1List
					i++; //increments i to keep track of how many values so far in the outputNames arrayList
					
					}
					else{// if the recent value exists in outputNames arrayList, then it just removes it from the kw1List
						
						kw1List.remove(0); //removing the value
					}
					
					existskw1 = kw1List.size()<1? false : true; //checking if the kw1List becomes empty for loop conditional
					
					
				}
				
				if ((secondValue>value) ||(sameValue)){//checking if the frequencies were either the same or secondvalue it greater than value, and proceeds
					
					int index = outputNames.indexOf(kw2List.get(0).document);//gets the index of the recent value in kw2List if it exists else gets -1
					
					if (index==-1){ //making sure it does not exists in the outputNames list, and proceeds
						
					outputNames.add(kw2List.get(0).document);//adds the value to outputNames
					kw2List.remove(0);//removes that value of kw2List
					i++; //increments i to keep track of how many values so far in the outputNames arrayList
					
					}
					else{// if the recent value exists in outputNames arrayList, then it just removes it from the kw2List
						
						kw2List.remove(0);  //removing the value
					}
					
					existskw2 = kw2List.size()<1? false : true;//checking if the kw1List becomes empty for loop conditional
					}
			
				
			}
		}
		 if (existskw1){//checks weather kw1List exists and proceeds.
				
				for ( int i= outputNames.size(); (i< 5 && kw1List.size()>=1) ; ){//runs until outputNames has 5 value  and kw1List has 0 values
					
					int index =outputNames.indexOf( kw1List.get(0).document); //gets the index of the recent value in kw1List if it exists else gets -1
					
					if (index ==-1){ //making sure it does not exists in the outputNames list, and proceeds
						
					outputNames.add(kw1List.get(0).document);//adds the value to outputNames
					kw1List.remove(0);//removes that value of kw1List
					i++;//increments i to keep track of how many values so far in the outputNames arrayList
					
					}else{// if the recent value exists in outputNames arrayList, then it just removes it from the kw1List
						
						kw1List.remove(0); //removing the value
					}
				}
		 } if (existskw2){//checks weather kw1List exists and proceeds.
				
				for (int i= outputNames.size(); (i<5 && kw2List.size()>=1); ){//runs until outputNames has 5 value  and kw2List has 0 values
					
					int index =outputNames.indexOf( kw2List.get(0).document);//gets the index of the recent value in kw1List if it exists else gets -1
					
					if (index ==-1){//making sure it does not exists in the outputNames list, and proceeds
						
						outputNames.add(kw2List.get(0).document);//adds the value to outputNames
						kw2List.remove(0);//removes that value of kw1List
						i++;//increments i to keep track of how many values so far in the outputNames arrayList
					
					}else{// if the recent value exists in outputNames arrayList, then it just removes it from the kw2List
						
						kw2List.remove(0);//removing the value
					}
					
					
				}
			}
		 
		 

		return outputNames;//returns outputNames
	}

	
}
