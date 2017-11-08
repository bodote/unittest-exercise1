package de.brandad.javatar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MyMore {

	private BufferedReader reader;
	private MyOutputHandler outputHandler;
	private int linesPerPage;
	private int lineCounter;

	public MyMore(InputStream inStream, MyOutputHandler outputHandler, int linesPerPage) {
		this.reader = new BufferedReader(new InputStreamReader(inStream));
		this.outputHandler = outputHandler;
		this.linesPerPage = linesPerPage;
		printAPage();
	}

	public void printAPage() {
		try {
			if (this.lineCounter == this.linesPerPage) {
				outputHandler.clearPage();
				lineCounter = 0;
			}

			String aLine = null;
			while (lineCounter < this.linesPerPage && (aLine = reader.readLine()) != null) {
				outputHandler.printLine(aLine);
				this.lineCounter++;
			}

		} catch (IOException e) {

			e.printStackTrace();
			outputHandler.printLine(e.toString());
		}

	}

	public void handle(KeyEvent escKey) {
		if (escKey.getCode() != KeyCode.ESCAPE)
			printAPage();
		else
			this.outputHandler.close();

	}

}
