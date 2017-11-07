package de.brandad.javatar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MoreJTest {
	InputStream inStream;
	Logger logger = Logger.getLogger(this.getClass().getName());
	@Before
	public void setup() {
		try {
			LogManager.getLogManager().readConfiguration(this.getClass().getClassLoader().getResourceAsStream("logging.properties"));
		} catch (SecurityException | IOException e) {
			logger.severe(e.getMessage());
		}
	}

	@Test
	public void printOneLineThenEscape() {
		String teststring = "one line12 \n";
		inStream = new BufferedInputStream(new ByteArrayInputStream(teststring.getBytes()));

		MyMore mymore = new MyMore(inStream, 20, new MyOutputHandler() {
			int totalLines = 0;
			int pageCounter = 0;

			@Override
			public void printLine(String line) {
				totalLines++;
			}

			@Override
			public void close() {
				assertEquals(2, totalLines); // 2 because "press any key or escape" needs an additional line
				assertEquals(1, pageCounter);
			}

			@Override
			public void clearPage() {
				pageCounter++;
			}
		});

		try {
			mymore.printAPage();
			mymore.handle(new KeyEvent(null, teststring, teststring, KeyCode.ESCAPE, false, false, false, false));
			inStream.close();
		} catch (IOException e) {
			fail("exception" + e.getMessage());
		}

	}
	
	@Test
	public void printThreeLinesOnTwoPagesThenEscape() {
		String teststring = "one line1 \none line2 \none line3 \n";
		inStream = new BufferedInputStream(new ByteArrayInputStream(teststring.getBytes()));

		MyMore mymore = new MyMore(inStream, 2, new MyOutputHandler() {
			int totalLines = 0;
			int pageCounter = 0;

			@Override
			public void printLine(String line) {
				totalLines++;
			}

			@Override
			public void close() {
				assertEquals(5, totalLines); // 2 more because "press any key or escape" needs an additional line
				assertEquals(2, pageCounter);
			}

			@Override
			public void clearPage() {
				pageCounter++;
			}
		});

		try {
			mymore.printAPage(); // only the first time at start, all other pages are triggered by keyevents
			mymore.handle(new KeyEvent(null, teststring, teststring, KeyCode.SPACE, false, false, false, false));
			mymore.handle(new KeyEvent(null, teststring, teststring, KeyCode.A, false, false, false, false));
			mymore.handle(new KeyEvent(null, teststring, teststring, KeyCode.ESCAPE, false, false, false, false));
			inStream.close();
		} catch (IOException e) {
			fail("exception" + e.getMessage());
		}

	}

}
