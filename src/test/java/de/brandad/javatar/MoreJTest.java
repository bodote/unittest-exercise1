package de.brandad.javatar;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MoreJTest {
	KeyEvent escKey = new KeyEvent(null, null, null, null, null, KeyCode.ESCAPE, false, false,false, false);
    KeyEvent nonEscKey = new KeyEvent(null, null, null, null, null, KeyCode.SPACE, false, false,false, false);
	
	@Test
	public void testOneLineThenCallClose() {
		InputStream inStream = new ByteArrayInputStream("1st line".getBytes());
		final ArrayList<Integer> closeCallCounter = new ArrayList<>();
		closeCallCounter.add(new Integer(0));
		MyOutputHandler mockOutputHandler = new MyOutputHandler() {
			
			private int lineCounter=0;;

			@Override
			public void printLine(String aLine) {
				this.lineCounter++;
				
			}
			
			@Override
			public void close() {
				assertEquals(1,lineCounter);;
				closeCallCounter.set(0, closeCallCounter.get(0)+1);
				
			}
			
			@Override
			public void clearPage() {
			
				
			}

			
		}; 
		
		MyMore myMore = new MyMore(inStream,mockOutputHandler,1);
		myMore.printAPage();
		myMore.handle(escKey);
		
		//assertEquals(1,mockOutputHandler.getLinesCounter());
		assertEquals((Integer)1,closeCallCounter.get(0));
	}
	@Test
	public void test4LinesOn2PagesThenCallClose() {
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("mytest.txt");
		final ArrayList<Integer> closeCallCounter = new ArrayList<>();
		closeCallCounter.add(new Integer(0));
		MyOutputHandler mockOutputHandler = new MyOutputHandler() {
			
			private int lineCounter=0;
			private int pageCounter=0;

			@Override
			public void printLine(String aLine) {
				this.lineCounter++;
				
			}
			
			@Override
			public void close() {
				assertEquals(4,lineCounter);
				assertEquals(1,pageCounter);
				closeCallCounter.set(0, closeCallCounter.get(0)+1);
				
			}
			
			@Override
			public void clearPage() {
				this.pageCounter++;
				
				
			}

		
		}; 
		
		MyMore myMore = new MyMore(inStream,mockOutputHandler,3);
		myMore.printAPage();
		myMore.handle(nonEscKey);
		
		myMore.handle(escKey);
		
		
		assertEquals((Integer)1,closeCallCounter.get(0));
	}

}
