package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

/**
 * Completely unoptimised at this point. Did as a very basic concept of reading in the files.
 * 
 * @author Alex
 *
 */
public class BibleApp {
	
	public BibleApp() { };
	
	public static void main(String[] args) {
		readInFile();
	}
	
	private static void readInFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("1Chronicles.txt"));
						
			long startTime = System.currentTimeMillis();
			
			Book book = new Book(reader.readLine()); // first line is always the book title
			
			String newLine = reader.readLine();
			int lineNumber = 2;
			
			int chapterNumber = 1;
			Chapter chapter = new Chapter(chapterNumber);
			
			while(newLine != null) {
				if(newLine.startsWith("CHAPTER")) {
					if(chapterNumber != 1)
						book.addChapter(chapter); // submit chapter
					
					chapter = new Chapter(chapterNumber);
					chapterNumber++;
				} else if(newLine.equals("")) {
					
				} else {
					chapter.addVerse(parseVerse(newLine, lineNumber));
				}
				
				newLine = reader.readLine();
				lineNumber++;
			}
			
			long endTime = System.currentTimeMillis();
			
			System.out.println("Total time for 1 book: " + (endTime - startTime) + " milliseconds.");
			System.out.println();
			System.out.println();
			
			System.out.println("Book 1");
			System.out.println("============");
			System.out.println("Title      : " + book.getTitle());
			System.out.println("Description: " + book.getDescription());
			System.out.println("Chapters   : " + book.getChapters().size() + " total");
			
			for(Chapter bookChapter : book.getChapters()) {
			System.out.println("Verse " + bookChapter.getChapterNumber() + ": " + bookChapter.getVerses().size() + " verse(s)");
			}

						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private static Verse parseVerse(String line, int verseNumber) throws ParseException {
		String strVerseNumber = String.valueOf(verseNumber);
		String verseContent = line.substring(strVerseNumber.length()-1);
		
		Verse verse = new Verse(verseNumber);
		verse.addWord(verseContent);
		
		System.out.println("Verse " + verseNumber + " parsed.");
		
		return verse;
	}

}