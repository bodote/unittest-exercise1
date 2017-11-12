
# Exercise on unit testing and using interfaces for...
- ... separating logic from the UI,
- for using a mock objects for testing,
- using a "unit test first" approach  

# Idea is..
..to practice the exercise several times until you can write down a clean solution very fast without googling 

( lets get started with the description of the task: )
# ReWrite "More" in Java using "test-first" unit tests and implement a JavaFX UI: 
- implement the core functions of the Unix command line tool "more" in java, with no extras.
- the class should read a  text from an "InputStream".
- the "lines per page" are given on initialization and can be hard coded in your test or UI,  for example use "3"
- if the sample text has more lines than "lines_per_page" given, then "jmore" should print at most the given maximum "lines_per_page"  from the InputStream in the window
- then wait for a "KeyEvent"  (use javafx.scene.input.KeyEvent)
- if KeyEvent.getKeyCode() == KeyCode.ESC is pressed, the program should exit 
- if any other key is pressed , the window  should be  cleared and the next page should be printed on the window .
- an so on until the end of input stream is reached.
- program should exit only if KeyEvent.getKeyCode() == KeyCode.ESC  , otherwise it should just wait

##  A Important note on the User Interface : 
- handling keyboard events is NOT possible in plain Java using default java-libraries for any text-terminals like Windows "cmd" or Unix "xterm" or "bash"
- therefore you need to use **JavaFX** or Swing or AWT. **JavaFX** is the most modern one. therefore we will use that.
- the JavaFX front end MUST NOT contain any logic or loops, just instantiation  and glue - code!
- all logic goes in to one java class called "jmore" that should be  fully independent from any user interface libraries.
- only this  user interface independent class should be unit tested, not the JavaFX - UI

- DO NOT write a event loop to handle KeyEvents for yourself, use an "event handler" also called a  "call back" method instead. the actual event loop is already build-into  JavaFX/Swing/AWT.
- in the JavaFX FrontEnd instantiate an InputStream simply from this.getClass().getClassLoader().getResourceAsStream("mytest.txt"); 
- (optionally you can print a hint as an additional text-line  at the  bottom of each page, that explains the  key-commands to the user.)

# UNIT TEST - FIRST !
- strictly write the tests first , then the implementation. 
- implement the JavaFX user interface not before all test plus the implementation are complete and running.

# Only use Java build-in libraries plus JUnit, nothing else
----------------------------
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
	
