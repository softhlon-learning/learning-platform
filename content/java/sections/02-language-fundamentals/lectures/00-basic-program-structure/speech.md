A Java program primarily consists of classes, methods, and statements. Here’s how they work together:


Class Declaration.

Every Java program must have at least one class. The class is the blueprint from which individual objects are created. The name of the class should match the name of the file it's in (with .java extension), following Java's naming conventions (e.g., MyFirstProgram.java).

- public is an access modifier indicating that the class can be used by any other class.
- class is a keyword to define a class. 
- MyFirstProgram is the name of the class.

Main Method.
Inside the class, there should be a main method, which is the entry point where the Java Virtual Machine (JVM) begins execution of the program.

- public means this method can be called from any other class.
- static means you don't need to create an instance of the class to call this method.
- void indicates that this method doesn't return any value.
- main is the method name; it's case-sensitive.
- String[] args is an array of command-line arguments passed to the program.


Statements.
Within the main method, you write statements. These are the instructions or commands that the program will execute.
In this example System.out.println() is used to output text to the console.


Comments.
Comments in Java are used to add explanations or notes within your code, which don't affect how the program runs.
There are three types. Here's how you can use them:


Single-line Comment.
Use // for comments that are on one line.

Multi-line Comment.
Use /* to start and */ to end a comment that spans multiple lines or within a line.

Documentation Comment.
Use /** to start and */ to end comments that are meant for generating documentation with tools like JavaDoc. These can include tags like @param for parameters or @return for return values.

Comments help in understanding the code, making maintenance easier, and documenting your code for others (or future you) to understand the intent and functionality.


Basic Syntax.
Java is case-sensitive; 
Statements must end with a semicolon ;.
Curly braces {} define the scope of classes, methods, and code blocks.
You can see complete example combining these elements.

This structure forms the skeleton of any Java application. As you delve deeper, you'll encounter more complex structures like interfaces, inheritance, and exception handling, but this basic setup is where every Java program begins.

