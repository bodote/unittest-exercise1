package de.brandad.javatar;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.LogManager;
import java.util.logging.Logger;


import org.junit.BeforeClass;
import org.junit.Test;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MoreJTest {
	@BeforeClass
	public void setUp() throws SecurityException, IOException {
		LogManager.getLogManager().readConfiguration(this.getClass().getClassLoader().getResourceAsStream("logging.properties"));
	}
	Logger logger = Logger.getGlobal();
	KeyEvent escKey = new KeyEvent(null, null, null, null, null, KeyCode.ESCAPE, false, false,false, false);
    KeyEvent nonEscKey = new KeyEvent(null, null, null, null, null, KeyCode.SPACE, false, false,false, false);
	enum counterName { closeCalls };
	@Test
	public void testOneLineThenCallClose() {
		
		InputStream inStream = new ByteArrayInputStream("1st line".getBytes());
		final HashMap<counterName,Integer> counter = new HashMap<>();
		counter.put(counterName.closeCalls,new Integer(0));
		MyOutputHandler mockOutputHandler = new MyOutputHandler() { 
			private int linecounter=0;;
			@Override
			public void printLine(String aLine) {
				this.linecounter++;
			}
			@Override
			public void close() {
				assertEquals(1,linecounter);;
				counter.put(counterName.closeCalls, counter.get(counterName.closeCalls)+1);
			}
			@Override
			public void clearPage() {
			}
		}; 
		
		MyMore myMore = new MyMore(inStream,mockOutputHandler,1);
		
		myMore.handle(escKey);
		
		//assertEquals(1,mockOutputHandler.getLinescounter());
		assertEquals((Integer)1,counter.get(counterName.closeCalls));
	}
	@Test
	public void test4LinesOn2PagesThenCallClose() {
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("mytest.txt");
		final HashMap<counterName,Integer> counter = new HashMap<>();
		counter.put(counterName.closeCalls,new Integer(0));
		MyOutputHandler mockOutputHandler = new MyOutputHandler() {
			
			private int linecounter=0;
			private int pagecounter=0;

			@Override
			public void printLine(String aLine) {
				this.linecounter++;
				
			}
			
			@Override
			public void close() {
				assertEquals(4,linecounter);
				assertEquals(1,pagecounter);
				counter.put(counterName.closeCalls, counter.get(counterName.closeCalls)+1);
				
			}
			
			@Override
			public void clearPage() {
				this.pagecounter++;
				
				
			}

		
		}; 
		
		MyMore myMore = new MyMore(inStream,mockOutputHandler,3);
	
		myMore.handle(nonEscKey);
		
		myMore.handle(escKey);
		
		
		assertEquals((Integer)1,counter.get(counterName.closeCalls));
	}

}
