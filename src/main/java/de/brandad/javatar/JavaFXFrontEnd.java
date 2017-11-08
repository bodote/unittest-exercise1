package de.brandad.javatar;

import java.io.InputStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class JavaFXFrontEnd extends Application implements MyOutputHandler{
	public static void main(String[] args) {
		launch(args);
	}
	private Stage primaryStage;
	private TextArea textArea;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.textArea = new TextArea();
		this.textArea.setEditable(false);
		this.primaryStage.setScene(new Scene(this.textArea));
		this.primaryStage.show();
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("mytest2.txt");
		MyMore myMore = new MyMore(inStream,this,3);
		myMore.printAPage();
		this.textArea.setOnKeyPressed(myMore::handle);
		
	}
	@Override
	public void printLine(String aLine) {
		this.textArea.appendText(aLine+"\n");
		
	}
	@Override
	public void clearPage() {
		this.textArea.clear();
		
	}
	@Override
	public void close() {
		this.primaryStage.close();
		
	}

}
