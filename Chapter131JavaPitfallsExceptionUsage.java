public class Chapter131JavaPitfallsExceptionUsage {

    // Section 131.1: Pitfall - Catching Throwable, Exception, 
    // Error or RuntimeException

    // Inexperienced thing that exceptions are a "burden"
    // so they try to deal with them and catch them all asap
    // this leads to code like this:

    ....
    try {
        InputStream is = new FileInputStream(fileName);
        // process the input
    } catch (Exception ex) {
        System.out.println("Could not open file " + fileName);
    }
    // This code will catch too many exceptions, e.g. NullPointerException

    // Better is to deal with the exceptions that ARE thrown.
    // FOr example, you can catch them and handle them in situ:
    try {
        InputStream is = new FileInputStream(fileName);
        // process the input
    } catch (FileNotFoundException ex) {
        System.out.println("Could not open file " + fileName);
    }

    // or you can declare them as thrown by the enclosing method.

    // There are very few situations where catching Exception is appropriate.
    // The only one that arises commonly is something like this:

    public static void main(String[] args) {
        try {
            // do stuff
        } catch (Exception ex) {
            System.err.println("Unfortunately an error has occurred. " +
                               "Please report this to X Y Z");
            // Write stacktrace to a log file.
            System.exit(1);
        }
    }
    // Here we genuinely want ot deal with all exceptions,
    // so catching Exception (or even Throwable) is correct
    // 1 - Also known as Pokemon Exception Handling.


    // Section 131.2: Pitfall - Ignoring or squashing exceptions

    // In practice, exception squashing frequently happens when
    // you use an IDE's auto-correction feature to "fix" a
    // compilation error caused by an unhandled exception
    // For example, you might see code like this:
    try {
        inputStream = new FileInputStream("someFile");
    } catch (IOException e) {
        /* add exception handling code here */
    }

    // Clearlt, the programmer has accepted the IDE's suggestion
    // to make the compilation error go away,
    // but the suggestion was inappropriate. (If the file open has failed,
    // the program should most likely do something about it,
    // With the above "correction", the program is liable to fail later;
    // e.g. with a NullPointerException because inputStream is not null)

    // There are examples of deliberately squashing an exception.
    try {
        selfie.show();
    } catch (InterruptedException e) {
        // It doesn't matter if showing the selfie is interrupted.
    }

    // Another conventional way to highlight that we are deliberately
    // squashing an exception without saying why
    // is to indicate this with the excpeiton variable's name, like this:
    try {
        selfie.show();
    } catch (InterruptedException ignored) {   }

    // Some IDEs (like Intellij IDEA) won't display a warning
    // about the empty catch block if the variable name is set to ignored.


    // Section 131.3: Pitfall - Throwing Throwable, Exception, Error or RuntimeException

    // While catching
    // the Throwable, Exception, Error and RUntimeException exceptions
    // is bad,
    // throwing them is even worse.

    try {
        InputStream is = new FileInputStream(someFile); // could throw IOException
        ...
        if (somethingBad) {
            throw new Exception(); // WRONG
        }
    } catch (IOException ex) {
        System.err.println("cannot open ...");
    } catch (Exception ex) {
        System.err.println("something bad happened"); // WRONG
    }

    // ...

    // Certain other patterns should be avoided. For example:
    try {
        doSomething();
    } catch (Exception ex) {
        report(ex);
        throw ex;
    }

    // Section 131.4: Pitfall - Using exceptions for normal flowcontorl

    // There is a mantra that some Java experts won't recite:
    // "Exceptioons should only be used for exceptional cases."

    // Compare these two ways of dealing with a parameter that could be null.

    public String tructateWorkOrNull(String word, int maxLength) {
        if (word == null) {
            return "";
        } else {
            return word.substring(0, Math(word.length(), maxLength));
        }
    }

    public String tructateWordOrNull(String word, int maxLegnth) {
        try {
            return word.substring(0, Math.min(word.length(), maxLength));
        } catch (NullPointerException ex) {
            return "";
        }
    }

    // Second version is both slower and less readable usually


    // Section 131.5: Pitfall - Directly subclassing `Throwable`


    // Section 131.6: Pitfall - Catching InterruuptedException


}
