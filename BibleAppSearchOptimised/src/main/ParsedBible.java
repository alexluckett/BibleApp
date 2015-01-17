package main;

import java.util.List;

public class ParsedBible {
	private List<Book> bibleBooks;
	private WordMap wordIndex;
	
	public ParsedBible(List<Book> bibleBooks, WordMap wordIndex) {
		this.bibleBooks = bibleBooks;
		this.wordIndex = wordIndex;
	}
	
	public List<Book> getBibleBooks() {
		return bibleBooks;
	}
	
	public WordMap getWordIndex() {
		return wordIndex;
	}
	
	/**
	 * Get verses from a range of verse numbers
	 * 
	 * @author Ash
	 */
	public void printVerseRange(int bookId, int chapterNumber, String verses) {
		boolean midFound = false;
		String startingNumber = "";
		String endingNumber = "";
		
		//Cycles through the verses string, checking where the '-' is, then sorts the first number and last number
		//based on where the '-' is
		for(int i = 0; i < verses.length(); i++){
			if(verses.substring(i, i+1).equalsIgnoreCase("-")){
				midFound = true;
			}

			if(midFound == false){
				startingNumber += verses.substring(i, i+1);
			}

			if(midFound == true && !(verses.substring(i, i+1).equalsIgnoreCase("-"))){
				endingNumber += verses.substring(i, i+1);
			}
		}

		StringBuilder sb = new StringBuilder();
		int startVerseNumber = 0;
		int endVerseNumber = 0;
		try{
			startVerseNumber = Integer.parseInt(startingNumber);
			endVerseNumber = Integer.parseInt(endingNumber);
		} catch(NumberFormatException e){
			sb.append("Invalid range of verses\n");
		}

		if(startVerseNumber > endVerseNumber) { // validation to make sure a valid range is entered
			sb.append("First verse number was higher than the last! - Swapping numbers around!\n\n");
			int temp = startVerseNumber;
			startVerseNumber = endVerseNumber;
			endVerseNumber = temp;			
		}

		long startTime = System.currentTimeMillis();
		long endSearch = startTime;

		try {
			for(int i = startVerseNumber; i <= endVerseNumber; i++)
				//Get the actual verse from the data entered
				sb.append(bibleBooks.get(bookId).getChapter(chapterNumber).getVerse(i-1).getText() + "\n");
			
			endSearch = System.currentTimeMillis();
		} catch (Exception e) {
			sb.append("Book/verse/chapter does not exist.");
		}
		
		sb.append("\n" + sb);
		
		long endTime = System.currentTimeMillis();
		
		sb.append("TIME TAKEN TO SEARCH: " + getTimerText(startTime, endSearch));
		sb.append("TIME TAKEN TO SEARCH AND PRINT: " + getTimerText(startTime, endTime));
		
		System.out.println(sb);
	}
	
	/**
	 * Searches through books and finds occurrences of search terms.
	 * 
	 * @param searchTerm search term
	 * @author Alex
	 */
	public void printSearch(String searchTerm) {
		StringBuilder sb = new StringBuilder();

		long startTime = System.currentTimeMillis();
		List<WordAppearance> appearances = wordIndex.getAppearances(searchTerm);
		long endSearch = System.currentTimeMillis();

		if(appearances != null && appearances.size() != 0) {
			sb.append("\"" + searchTerm + "\" found! Occurances: " + appearances.size() + "\n");

			for(WordAppearance appearance : appearances)
				sb.append(appearance);
			
			sb.append("Occurances: " + appearances.size() + "\n");
		} else {
			sb.append("No search results found.\n");
		}
		
		sb.append("\n" + sb);

		long endTime = System.currentTimeMillis();
		
		sb.append("TIME TAKEN TO SEARCH: " + getTimerText(startTime, endSearch));
		sb.append("TIME TAKEN TO SEARCH AND PRINT: " + getTimerText(startTime, endTime));
		
		System.out.println(sb);
	}
	
	/**
	 * Searches through books to find the chapters which refer to the book and chapter number 
	 * 
	 * @author Chaz
	 */
	public void printChapterLookup(int bookId, int chapterNumber) {
		StringBuilder sb = new StringBuilder();
		
		if(bookId >= 0 && chapterNumber > 0) {
			long startTime = System.currentTimeMillis();
			long endSearch = startTime;

			try {
				Chapter chapterFound = bibleBooks.get(bookId).getChapter(chapterNumber);
				List<Verse> v = chapterFound.getVerses();
				
				endSearch = System.currentTimeMillis();

				sb.append("Chapter " + (chapterFound.getChapterNumber()+1) + "\n");				
				if(chapterFound.getDescription() != null) // if there is a description, add this to the chapter printout
					sb.append("Description: " + chapterFound.getDescription() + "\n");

				for(Verse verse : v) {
					sb.append(verse.getText() + "\n"); // print out each verse
				}
			} catch (Exception e) {
				sb.append("Invalid chapter or book number");
			}
			
			sb.append("\n" + sb);
			
			long endTime = System.currentTimeMillis();
			
			sb.append("TIME TAKEN TO SEARCH: " + getTimerText(startTime, endSearch));
			sb.append("TIME TAKEN TO SEARCH AND PRINT: " + getTimerText(startTime, endTime));
		} else {
			sb.append("Please enter a valid book ID and/or chapter number.\n");
		}
		
		System.out.println(sb);
	}
	
	/**
	 * Searches through books to find the verse which is needed
	 * 
	 * @author Ash
	 */
	public void printVerseLookup(int bookId, int chapterNumber, int verseNumber) {
		StringBuilder sb = new StringBuilder();

		long startTime = System.currentTimeMillis();

		try {
			Verse answer = bibleBooks.get(bookId).getChapter(chapterNumber).getVerse(verseNumber-1);
			sb.append("Verse: " + answer.getText() + "\n"); // pull in verse from parameters in method
		} catch (Exception e) {
			sb.append("Book/chapter/verse number is incorrect.\n");
		}

		long endSearch = System.currentTimeMillis();

		sb.append("\n" + sb); // print out results
		
		long endTime = System.currentTimeMillis();
		
		sb.append("TIME TAKEN TO SEARCH: " + getTimerText(startTime, endSearch));
		sb.append("TIME TAKEN TO SEARCH AND PRINT: " + getTimerText(startTime, endTime));
		
		System.out.println(sb);
	}
	
	/**
	 * Searches through the books, and returns the verse which the word is found in
	 * 
	 * @author Ash
	 */
	public void printVerseByWord(String statementToSearch) {
		StringBuilder sb = new StringBuilder(); 
		long starttime = System.currentTimeMillis();

		List<WordAppearance> searchWord = wordIndex.getAppearances(statementToSearch);  // list of appearances for this search term
		
		long endSearch = System.currentTimeMillis();

		if(searchWord != null){
			sb.append(statementToSearch + " found!" +  "\n");

			for(WordAppearance appearance : searchWord) {
				int bookId = 0;

				for(int appearanceId = 0; appearanceId < bibleBooks.size(); appearanceId++){ // get book Id from the search term
					if(bibleBooks.get(appearanceId).getTitle().equals(appearance.getBook())){
						bookId = appearanceId;
					}
				}

				Verse appearanceVerse = bibleBooks.get(bookId).getChapter(appearance.getChapter()).getVerse(appearance.getVerse()-1);
				
				sb.append(appearance + appearanceVerse.getText() + " \n"); // log the appearance in the StringBuilder 
			} 
		} else {
			sb.append("Word not found!");
		}

		sb.append("\n" + sb);
		
		long endtime = System.currentTimeMillis();
		
		sb.append("TIME TAKEN TO SEARCH: " + getTimerText(starttime, endSearch));
		sb.append("TIME TAKEN TO SEARCH AND PRINT: " + getTimerText(starttime, endtime));
		
		System.out.println(sb);
	}
	
	private String getTimerText(long startTime, long endTime) {
		long totalTime = endTime - startTime;
		
		return (totalTime == 0) ? "<0 milliseconds" : totalTime + " milliseconds"; // if 0ms, print <0, else the actual ms
	}
}