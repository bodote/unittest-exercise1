# Exercise on unit testing and using interfaces for...
- ... separating logic from the UI,
- for using a mock objects for testing,
- using a "unit test first" approach  

# Idea is..
..to practice the exercise several times until you can write down a clean solution very fast without googling 

# "jmore": 
- implement the core functions of the Unix command line tool "more" in java, with no extras.
- it should read a  text from an "InputStream".
- the "lines per page" are given on initialization 
- if sample text has more lines than "lines_per_page" , then "jmore" should print at most "lines_per_page" the text lines from the InputStream on the screen
- then wait for a "KeyEvent"  
- if ESC is pressed, the tool exits
- if any other key, the screen is cleared an the next "lines_per_page" are printed on the screen .
- an so on until end of input stream is reached 

## Important note on  handling key events: 
- not possible in plain Java using default library for any text-terminals like Windows "cmd" or Unix "xterm" or "bash"
- therefore you need to use JavaFX or Swing or AWT. JavaFX is the most modern one.
- the JavaFX front end must not contain any logic or loops, just glue - code!
-  all logic goes in to a java class that is fully independent from any user interface.
- only this user interface independent class should be unit tested.
- nevertheless give a text hint at the end of each page, what the user can do, what key does what of if the EOF is reached.
- DO NOT write a event loop to handle KeyEvents for yourself, use an "event handler" also called a  "call back" method instead. the event loop is internal to JavaFX/Swing/AWT.

# UNIT TEST - FIRST !
- strictly write the test first , then the implementation. 
- implement the JavaFX user interface not before all test are complete and running.

# Only use Java build-in libraries plus JUnit, nothing else

# Solution should start from command line like this: 
- java -cp build/libs/unittest-exercise1.jar de.brandad.javatar.JavaFXFrontEnd

# Run Tests from command line using gradle:
- ./gradlew test --rerun-tasks

# New Gradle-Projekt in Eclipse
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
	
# Fixing Eclipse "Access restriction: ...is not accessible due to restriction on required library" 
- because of https://bugs.eclipse.org/bugs/show_bug.cgi?id=431067  the "javafx" package is "not accessible" 
- to fix this go to "configure build-path"->"libraries"->"JRE System Library"->"Access Rules" -> "Edit" and add the Pattern "javafx/**** as  "Accessible" in order to get these lines into the  ".classpath" file 

```XML
  <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8/">
		<accessrules>
			<accessrule kind="accessible" pattern="javafx/**"/>
		</accessrules> 
	</classpathentry>
```
 - **OR** install Eclipse add-on: "e(fx)clipse"  from  http://www.eclipse.org/efxclipse/install.html 
	
