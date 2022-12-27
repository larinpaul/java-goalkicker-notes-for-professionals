
/// 2022 12 27 /// 23:08 ///
/// Page 346 pdf
//// Chapter 46: Console I/O


//// Section 56.1: Reading user input from the console

// Using BufferedReader:
System.out.println("Please type your name and press Enter.")

BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
try {
    String name = reader.readLine();
    System.out.println("Hello, " + name + "!");
} catch(IOException e) {
    System.out.println("An error occurred: " + e.getMessage());
}

// The following imports are needed for this code:
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


//// Using Scanner:
// Version >= Java SE 5
System.out.println("Pleasetype your name and Press Enter");

Scanner scanner = new Scanner(System.in);
String name = scanner.nextLine();

System.out.println("Hello, " + name + "!");

// The following import is needed for this example:
import java.util.Scanner;

// To read more than one line, invoke scanner.nextLine() repeatedly:
System.out.println("Please enter your first and your last name, on separate lines.");

Scanner scanner = new Scanner(System.in);
String firstName = scanner.nextLine();
String lastName = scanner.nextLine();

System.out.println("Hello, " + firstName + " " + lastName + "!");

// There are two methods for obtaining Strings, next() and nextLine(). next() returns text up the first space
// (also known as a "token"), and nextLine() returns all the text that the user inputted until pressing enter.

// Scanner also provides utility methods for reading data types other than String. These include:
scanner.nextByte();
scanner.nextShort();
scanner.nextInt();
scanner.nextLong();
scanner.nextFloat();
scanner.nextDouble();
scanner.nextBigInteger();
scanner.nextBigDecimal();

// Prefixing any of these methods with has (as in hasNextLine(), hasNextInt()) returns true if the stream has any
// more of the request type. Note: These methods will crash the program if the input is not of the requested type (for
// example, typing "a" for nextInt() ). You can use a try {} catch() {} to prevent this (see: Exceptions)
Scanner scanner = new Scanner(System.in); // Create the scanner
scanner.useLocale(Locale.US); // Set number format excepted
System.out.println("Please input a float, decimal separator is .");
if (scanner.hasNextFloat()) { // Check if it is a float
    float fValue = scanner.nextFloat(); // retrieve the value directly as float
    System.out.println(fValue + " is a float");
} else {
    String sValue = scanner.next(); // We can not retrieve as float
    System.out.println(sValue + " is not a float");
}


// Using System.console:
// Version >= Java SE 6
String name = System.console().readLine("Please type your name and press Enter%n");

System.out.printf("Hello, %s!", name);

// To read passwords (without echoing as in unix terminal)
char[] password = System.console().readPassword();

// Advantages:
// * Reading methods are synchronized
// * Format string syntax can be used

// Note: This will only work if the program is run from a real command line without redirecting the standard input and
// output streams. It does not work when the program is run from within certain IDEs, such as Eclipse. For code that
// works within IDEs and with stream redirection, see the other examples.

/// 23:25


//// Section 56.2: Aligning strings in console
