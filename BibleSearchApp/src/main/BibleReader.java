package main;

import static main.Constants.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.WordAppearance.DescriptionType;

/**
 * 
 * @author Alexander Luckett <lucketta@aston.ac.uk>
 *
 */
public class BibleReader {
	private List<Book> parsedBooks;
	private WordMap wordIndex;
	
	public BibleReader() {
		parsedBooks = new ArrayList<Book>(MAX_BOOKS);
		wordIndex = new WordMap(TOTAL_WORDS, TOTAL_WORDS_LOADFACTOR);
	}
	
	/**
	 * Reads in all books contained within the file array.
	 * 
	 * @param File[] book files
	 * @return boolean true if successful, false if not
	 */
	public ParsedBible readInBooks(File[] books) throws IOException {
		for(int j = 0; j < books.length; j++) {
			readInFile(books[j].getAbsolutePath()); // get the bath of the book, then inform readInFile(...) to read it in
		}
		
		return new ParsedBible(parsedBooks, wordIndex);
	}

	/**
	 * Processes a given file (book from the bible) using a filename passed through as
	 * a parameter. Assumes the file is in the /data package.
	 * 
	 * @param fileName filename of file to process (including .txt file extension)
	 * @throws IOException 
	 */
	private void readInFile(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));

		String bookTitle = reader.readLine(); // first line is always the book title
		int verseNumber = 1;
		int chapterNumber = 0;

		Book book = new Book(fileName, bookTitle);
		Chapter chapter = null;

		String currentLine;

		while((currentLine = reader.readLine()) != null) {
			if(currentLine.isEmpty())
				continue; // don't care if line is empty - go to next iteration

			if(currentLine.startsWith("CHAPTER") || currentLine.startsWith("PSALM")) {
				if(chapterNumber != 0)
					book.addChapter(chapter); // chapter not constructed until chapterNumber >0, so submit it to the book

				chapter = new Chapter(chapterNumber++);
				verseNumber = 1; // new chapter, must reset verse count to 1
			} else if(Character.isLetter(currentLine.trim().charAt(0))) { // condition met if is a description
				if(chapterNumber == 0) {
					book.setDescription(currentLine); // haven't processed a chapter yet, so it must be the book description
					parseLine(currentLine, bookTitle, verseNumber, chapterNumber, DescriptionType.BOOK);
				} else {
					chapter.setDescription(currentLine);
					parseLine(currentLine, bookTitle, verseNumber, chapterNumber, DescriptionType.CHAPTER);
				}
			} else {
				chapter.addVerse(parseLine(currentLine, bookTitle, verseNumber, chapterNumber, DescriptionType.NONE));
				verseNumber++;
			}
		}

		book.addChapter(chapter); // add last book of last chapter manually -> can't auto detect because no new line that starts with "CHAPTER/PSALM"
		parsedBooks.add(book); // finished processing book, add to collection
		
		reader.close();
	}
	
	/**
	 * Parses a verse, given the verse (line), verse number and chapter number.
	 * 
	 * @param line verse to process
	 * @param verseNumber number of this verse
	 * @param chapterNumber chapter number of this verse
	 * @param isDescription true if line is a description
	 * 
	 * @return Verse if descriptionType is BOOK/CHAPTER, else NONE.
	 */
	private Verse parseLine(String line, String bookName, int verseNumber, int chapterNumber, DescriptionType descriptionType) {
		String[] words = StringUtils.splitWords(line);

		if(descriptionType == DescriptionType.NONE)
			words[0] = null; // first word is number if not a description, so ignore it

		for(String word : words) {
			if(word != null)
				logAppearance(word, bookName, verseNumber, chapterNumber, descriptionType); // log each word in the index
		}

		if(descriptionType == DescriptionType.NONE)
			return new Verse(verseNumber, line);

		return null; // no need to return a verse if we're parsing a description
	}
	
	/**
	 * Logs an appearance of a single word, given:
	 * @param word word to log
	 * @param bookName
	 * @param verseNumber
	 * @param chapterNumber
	 * @param descriptionType description enum (BOOK/CHAPTER/NONE/ETC.)
	 */
	private void logAppearance(String word, String bookName, int verseNumber, int chapterNumber, DescriptionType descriptionType) {
		wordIndex.addWord(word, bookName, verseNumber, chapterNumber, descriptionType);
	}	
}
