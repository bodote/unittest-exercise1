# Exercise on unit testing and using interfaces for...
- ... separating logic from the UI
- and for using mock objects for testing

# Task "jmore":
- implement the core functions of the Unix command line tool "more", with no extras.
- it should read a  text from an "InputStream".
- the "lines per page" are given on initialization 
- "jmore" should print at most "lines per page" the text lines from the InputStream on the screen
- then wait for a "KeyEvent" 
- if ESC is pressed, the tool exits
- if any other key, the screen is cleared an the next "lines per page" are printed on the screen .

## Important note on  handling key events: 
- not possible in plain Java using default library for any text-terminals like Windows "cmd" or Unix "xterm" or "bash"
- therefore you need to use JavaFX or Swing or AWT. JavaFX is the most modern one.
- the JavaFX front end must not contain any logic or loops, just glue - code!
-  all logic goes in to a java class that is fully independent from any user interface.
- only this user interface independent class should be unit tested.
- nevertheless give a text hint at the end of each page, what the user can do, what key does what of if the EOF is reached.
- DO NOT write a event loop to handle KeyEvents for yourself, use an "event handler" also called a  "call back" method instead. the event loop is internal to JavaFX/Swing/AWT.

# TEST - FIRST !
- strictly write the test first , then the implementation. 
- implement the JavaFX user interface not before all test are complete and running.

# Only use Java default libraries plus JUnit, nothing else

# Start from command line: 
- java -cp build/libs/dojo_gradle.jar de.bas.dojo.more.JavaFXFrontEnd2

# Run Tests from command line:
- ./gradlew test --rerun-tasks

# Neues Gradle-Projekt in Eclipse
New Project -> Gradle Project-> name -> ok

# Hints:
- use an Interface "OutputHandler" to handle and decouple all calls to the  JavaFX front end.  Also use this in Interface in the unit tests. 
- the jmore class has a constructor that takes a InputStream, the OutputHandler, and the number of lines per page
- use Interface "OutputHandler" to instantiate a "mock" object for testing.
- if using JavaFX: to create a KeyEvent in the unit test use: 
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
private KeyEvent escKeyEvent = 
     new KeyEvent(null,null,null,null,null,KeyCode.ESCAPE,false,false,false,false);
	
