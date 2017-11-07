package de.brandad.javatar;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MyMore implements EventHandler<KeyEvent>{

	private BufferedReader bufferedReader;
	private MyOutputHandler inOutHandler;
	private int linesPerPage;
	
	Logger logger = Logger.getLogger(this.getClass().getName());

	public MyMore(InputStream inStream, int linesPerPage, MyOutputHandler inOutHandler) {
		this.bufferedReader = new BufferedReader(new InputStreamReader(inStream));
		this.inOutHandler = inOutHandler;
		this.linesPerPage = linesPerPage;
		
	}

	public void printAPage() throws IOException {
		String line = null;
		int lineCounter = 1;
		while (lineCounter <= this.linesPerPage && (line = bufferedReader.readLine()) != null ) {
			if (lineCounter == 1)  inOutHandler.clearPage();
			inOutHandler.printLine(line);
			lineCounter++;
		}
		if (lineCounter != 1) inOutHandler.printLine("press any key or escape");
	}
	@Override
	public void handle(KeyEvent event) {
		logger.fine("keyEvent.code:" + event.getCode());
		try {
			if (event.getCode() == KeyCode.ESCAPE) {
				inOutHandler.close();
			} else {
				printAPage();
			}
		} catch (Exception e) {
			inOutHandler.printLine("exeption:"+e.getMessage());
		}
		
	}

	

}
