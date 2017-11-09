package de.brandad.javatar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MyMore {
	Logger logger = Logger.getGlobal();
	private BufferedReader reader;
	private MyOutputHandler outputHandler;
	private int linesPerPage;
	
	public MyMore(InputStream inStream, MyOutputHandler outputHandler, int linesPerPage) {
		this.reader = new BufferedReader(new InputStreamReader(inStream));
		this.outputHandler = outputHandler;
		this.linesPerPage = linesPerPage;
		printAPage();
	}

	private void printAPage() {
		try {
			int lineCounter = 0;
			String aLine = null;
			while (lineCounter < this.linesPerPage && (aLine = reader.readLine()) != null) {
				outputHandler.printLine(aLine);
				lineCounter++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			outputHandler.printLine(e.toString());
		}
	}

	public void handle(KeyEvent aKeyEvent) {
		logger.info("aKeyEvent.getCode()="+aKeyEvent.getCode());
		if (aKeyEvent.getCode() != KeyCode.ESCAPE) {
			outputHandler.clearPage();
			printAPage();
		} else
			this.outputHandler.close();

	}

}
