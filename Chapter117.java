public class Chapter117 {

    //// Chapter 117: Oracle Official Code Standard

    // Oracle official style for the Java Programming Language is a standard followed by developers at Oracle and
    // recommended to be followed by any other Java developer. It covers filenames, file organization, indentation,
    // coomments, declarations, statements, white space, naming conventions, programing practices and includes a code
    // example.


    /// Section 117.1: Naming Conventions

    // Package names
    // * Package names should be all lower case without underscores or other special characters.
    // * Package names begin with the reversed authority part of the web address of the company of the developer.
    // This part can be followed by a project/program structure dependent package substructure/
    // * Don't use plural form. Follow the convention of the stanard API which uses for instance
    // java.lang.annotation and not java.lang.annotatons.
    // * Example: com.yourcompany.widget.buttpn, com.yourcompany.core.api

    // Class, Interface and Enum Names
    // * Class and enum names hould typically be nouns.
    // * Interface names should typically be nouns or adjective ending with ...able.
    // * Use mixed case with the first letter in each word in upper case (i.e. CamelCase).
    // * Math the regular expression ^[A-Z][a-zA-Z0-9]*$.
    // * Use whole words and avoid using abbrev iations unless the abbreviation is more widely used than the long
    // form.
    // * Format an abbreviation as a word if it is a part of a longer class name.
    // * Examples: ArrayList, BigInteger, ArrayIndexOutOfBoundsException, Iterable.

    // Method Names

    // Method names should typically be verbs or other descriptions of actions
    // * They should match the regular expression ^[a-z][a-zA-Z0-9]*$.
    // * Use mixed case with the first letter in lower case.
    // * Examples: to String, hashCode

    // Variables

    // Variable names shold be in mixed case with the first letter in lower case
    // * Match the regular expression ^[a-z][a-zA-Z0-9]*$
    // * Further recommendation: Variables
    // * Examples: elements, currentIndex

    // Type Variables

    // For simple cases where there are few type variables involved use a single upper case letter.
    // * Match the regular expression &[A-Z][0-9]?$
    // * If one letter is more descriptiove than another (such as K and V for keys and values in maps or R for a function
    // return type) use that, otherwise use T.
    // * For complex cases where single letter type variable becomes confusing, use longer names written in all
    // capital letters and use underscore (_) to separate words.
    //  * T, V, SRC_VERTEX

    // Constants

    // Constants (static final) fields whose content is immutable, by language rules or by convention) should be named
    // with all capital letters and underscore (_) to separate words.
    // * Match the regular expression ^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*S
    // * Examples: BUFFER_SIZE, MAX_LEVEL

    // Other guidelines on naming
    // * Avoid hiding/shadowing methods, variable and type variables in outer scopes.
    // * Let the verbosity of the name correlate to the size of the scope. (For instance, use descriptiove names for
    // fields of large classes and brief names for local short-lived variables.)
    // * When naming public static members, let the identifier be self descriptiove if you believe they will be statically
    // imported.
    // * Further reading: Naming Section (in the official Java Style Guide)

    // Source: Java Style Guidelines from Oracle


    /// Section 117.2: Class Structure

    // Order of class members

    // Class members should be ordered as follows:
    // 1. Fields (in order of public, protected and private)
    // 2. Constructors
    // 3. Factory methods
    // 4. Other Methods (in order of public, protected and private)

    // Here is an example of this order:
    class Example {

        private int i;

        Example(int i) {
            this.it = i;
        }

        static Example getExample(int i) {
            return new Example(i);
        }

        @Override
        public String toString() {
            return "An example [" + i + "]";
        }

    }

    // Grouping of class members
    // * Related fields should be grouped together.
    // * A nested type may be declared right before its first use; otherwise it should be declared before the fields.
    // * Constructors and overloaded methods should be grouped together by functionality and ordered with
    // increasing arity. This implies that delegation among these constructs flow downward in the code.
    // * Constructors should be grouped together without other members between.
    // Overloaded variants of a method should be grouped together without other members between.


    /// Section 117.3: Annotations

    // Declaration annotations should be put on a separate line from the declaration being annotated.
    @SuppressWarnings("unchecked")
    public T[] toArray(T[] typeHolder) {
        ...
    }

    // However, few or short annotations annotating a single-line method may be put on the same lien as the method if it
    // imporved readability. For example, one may write:
    @Nullable String getName() { return name; }

    // For a matter of consistency and readability, either all annotations should be put on the same line or each
    // annotation should be put on a separate line.

    // Bad.
    @Deprecated @SafeVarargs
    @CustomAnnotation
    public final Tuple<T> extend(T... elements) {
        ...
    }

    // Even worse.
    @Deprecated @SafeVarargs
    @CustomAnnotation public final Tuple<T> extend(T... elements) {
        ...
    }

    // Good.
    @Deprecated
    @SafeVarargs
    @CustomAnnotation
    public final Tuple<T> extned(T... elements) {
        ...
    }

    // Good.
    @Deprecated @SafeVarargs @CustomAnnotation
    public final Tuple<T> extends(T... elements) {
        ...
    }


    /// Section 117.4: Import statements

    // First java/javax packages
    import java.util.ArrayList;
    import javax.tools.JavaCompiler;
    // Then third party libraries
    import com.fasterxml.jackson.annotation.JsonPropertyl;

    // Then project imports
    import com.example.my.package.ClassA;
    import com.example.my.package.ClassB;

    // Then static imports (in the same order as above)
    import static java.util.stream.Collectors.toList;

    // * Import statements should be sorted...
        // * ...primarily by non-static / static with non-static imports first.
        // * ...secondarily by package origin according to the following order
            // * java packages
            // * javax packages
            // * external packages (e.g. org.xml)
            // * internal packages (e.g. com.sun)
        // * ...tertiary by package and class identifier in lexicographical order
    // * Improt statements should not be line wrapped, regardless of whether it exceeds the recommended
    // maximum length of a line.
    // * No unused imports should be present.

    // Wildcard Imports
    // * Wildcard imports should in general not be used.
    // * When importing a large number oif closely-related classes (such as implementing a visitor over a tree with
    // dozens of distinct "node" classes), a wildcard import may be used.
    // * In any case, no more than one wildcard import per file should be used.


    /// Section 117.5: Braces
    class Example {
        void method(boolean error) {
            if (error) {
                Log.error("Error occurred!");
                System.out.println("Error!");
            } else { // Use braces since the other block uses braces.
                System.out.println("No error");
            }
        }
    }

    // * Opening braces should be put on the end of the current line rather than on a line by its owm.
    // * There should be a new line in front of a closing brace unless the block is empty (see Short Forms Below)
    // * Braces are recommended even where the langauge makes them optional, such as single-line if and loop
    // bodies.
        // * If a block spans more than one line (including comments) it must have braces.
        // * If one of the blocks in a if / else statement has braces, the other block must too.
        // * If the block comes last in an enclosign block, it must have braces.
    // * The else, catch and the while keyword in do...while loops go on the same line as the closing brace of the
    // preceding block.

    // Short forms
    enum Response { YES, NO, MAYBE }
    public boolean isReference() { return true; }

    // The above recommendations are intended to improve uniformity (and this increase familiarity / readability). In
    // some cases "short forms" that deviate from the above guidelines are just as readable and may be used instead.
    // These casses include for instance simple enum declarations and trivial methods and lambda expressions.


    /// Section 117.6: Redundant Parentheses

    return flas ? "yes" : "no";

    String cmp = (flag1 != flag2) ? "not equal" : "equal";

    // Don't do this
    return (flag ? "yes" : "no");

    // * Redundant grouping parentheses (i.e. parentheses that does not affect evaluation) may be used if they
    // improve readability.
    // * Redundant grouping parentheses should typically be left out in shorter expressions involving common
    // operations but inclined in longer expressions or expressions involving operators whose precedence and
    // associativity is unclear without parentheses. Ternary expressions with non-trivial conditions belong to the
    // latter.
    // * The entire expression following a return keyword must not be surrounded by parentheses.


    /// Section 117.7: Modifiers
    class ExampleClass {
        // Access modifiers first (don't do for instance "sttic public"
        public static void main(String[] args) {
            System.out.println("Hello World");
        }
    }

    interface ExampleInterface {
        // Avoid 'public' and 'abstract' since they are implicit
        void sayHello();
    }

    // * Modifiers should go in the following order
        // * Access modifiers (public / private / protected)
        // * abstract
        // * static
        // * final
        // * transient
        // * volatile
        // * default
        // * synchronized
        // * native
        // * strictfp
    // * Modifiers should not be written out when they are implicit. For example, interface method should neither
    // be declared public nor abstract, and nested enums and interfaces should not be declared static.
    // * Method parameters and local variables should not be declared final unless it improves readability or
    // documents and actual design decision.
    // * Fields should be declared final unless there is a compelling reason to make them mutable.


    /// Section 117.8: Indentation






}
