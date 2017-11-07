package de.brandad.javatar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class JavaFXFrontEnd extends Application implements MyOutputHandler {
	TextArea textArea;
	MyMore mymore;
	Stage primaryStage;
	InputStream fileinstream;
	Logger logger = Logger.getLogger(this.getClass().getName());
	@Override
	public void start(Stage stage) throws Exception {
		try {
			LogManager.getLogManager().readConfiguration(this.getClass().getClassLoader().getResourceAsStream("logging.properties"));
		} catch (SecurityException | IOException e) {
			logger.severe(e.getMessage());
		}
		// boilerplate code for javafx:
		this.primaryStage = stage;
		primaryStage.setTitle("MyMore");
		textArea = new TextArea("hallo text");
		textArea.setEditable(false);
		Scene scene = new Scene(textArea, 400, 400);
		primaryStage.setScene(scene);

		// my actual code
		setupMyMore();
		// boilerplate code again:
		primaryStage.show();
	}

	private void setupMyMore() throws FileNotFoundException, IOException {
		fileinstream = this.getClass().getClassLoader().getResourceAsStream("test.txt");
		mymore = new MyMore(fileinstream, 5, this);
		textArea.setOnKeyPressed(mymore::handle);
		mymore.printAPage();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override // implements InputOutputHandler
	public void printLine(String line) {
		textArea.setText(textArea.getText() + line + "\n");
	}

	@Override // implements InputOutputHandler
	public void close() {
		try {
			fileinstream.close();
			javafx.application.Platform.exit();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override // implements InputOutputHandler
	public void clearPage() {
		textArea.setText("");

	}

}
