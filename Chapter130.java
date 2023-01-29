public class Chapter130 {
    //// 2023.01.29 // 21:23 //
    //// Chapter 130: Common Java Pitfalls

    // This topic outlines osme of the common mistakes made by beginners in Java.

    // This includes any common mistakes in use of the Java language or understanding of the run-time environment.

    // Mistakes associated with specific APIs can be described in topics specific to those APIs. Strings are a special case;
    // they're covered in the Java Language Specification. Details other than common mistakes can be described in this
    // topic on Strings.


    //// Section 130.1: Pitfall: using == to compare primitive wrappers
    // objects such as Integer

    // (This pitfall applies equally to all primitive wrapper types, but we will illustrate it for Integer and int.)

    // When working with Integer objects, it is tempring to use == to compare values, because that is what you would do
    // with int values. And in some cases this will seem to work:
    Integer int1_1 = Integer.valueOf("1");
    Integer int1_2 = Integer.valueOf(1);

    System.out.println("int1_1 == int1_2: " + (int1_1 == int1_2)); // true
    System.out.println("int1_1 equals int1_2: " + int1_1.equals(int1_2)); // true

    // Here we created two Integer objects with the value 1 and compare then (Int this case we created one from a
    // String and one from an int literal. There are other alternatives). Also, we observe that the two comparison
    // method (== and equals) both yield true.

    // This behavior changes when we choose different values:
    Integer int2_1 = Integer.valueOf("1000");
    Integer int2_2 = Integer.valueOf(1000);

    System.out.println("int2_1 == int2_2: " + (int2_1 == int2_2)); // false
    System.out.println("int2_1 equals int2_2: " + int2_1.equals(int2_2)); // true

    // In this case, only the equals comparison tields the correct result.

    // The reson for this difference in behavior is, that the JVM maintains a cache of Integer objects for the range -128 to
    // 127. (The upper value can be overriden with the system property "java.lang.Integer.IntegerCache.high" or the JVM
    // argument "-XX:AutoBoxCacheMax=size"). For values in this range, the Integer.valueOf() will return the cached
    // value ratehr than creating a new one.

    // Thus, in the first example the Integer.valueOf(1) and Integer.valueOf("1") calls returned the same cached
    // Integer instance. By contracts, in the second example the Integer.valueOf(1000) and Integer.valueOf("1000")
    // both created and returned new Integer objects.

    // The == operator for reference types tests for reference equality (i.e. the same object). THerefore, in the first
    // example int1_1 == int1_2 is true because the references are the same. In the second example int2_1 == int2_2
    // is false because the references are different.


    /// Section 130.2: Pitfall: using == to compare strings

    // A common mistake for Java beginners is to use the == operator to test if two strings are equal. For example:
    public class Hello {
        public static void main(String[] args) {
            if (args.length > 0) {
                if (args[0] == "hello") {
                    System.out.println("Hello back to you");
                } else {
                    System.out.println("Are you feeling grumpy today?");
                }
            }
        }
    }

    // The above program is supposed to test the first command line argument and print different messages when it and
    // isn't the word "hello". But the problem is that it won't work. That program will output "Are you feeling grumpy
    // today?" no matter what the first command line argument is.

    // In this particular case the String "hello" is put in the string ppol while the String args[0] resides on the heap. This
    // means there are two objects representing the same literal, each with its reference. Since == tests for references, not
    // actual equality, the comparison will yield a false msot of the times. This doesn't mean that it will always do so.

    // When you use == to test strings, what you are actually testing is if two String objects are the same Java object.
    // Unfortunately, that is not what string equality means in Java. In fact, the correct way to test strings is to use the
    // equals(Object) method. For a pair of strings, we usually want to test if they consist of the same characters in the
    // same order.
    public class Hello2 {
        public static void main(String[] args) {
            if (args.length > 0) {
                if (args[0].equals("hello")) {
                    System.out.println("Hello back to you");
                } else {
                    System.out.println("Are you feeling grumpy today?");
                }
            }
        }
    }

    // But it actualyl gets worse. The problem is that == will give the expected answer in some circustances. For example
    public class Test1 {
        public static void main(String[] args) {
            String s1 = "hello";
            String s2 = "hello";
            if (s1 == s2) {
                System.out.println("same");
            } else {
                System.out.println("different");
            }
        }
    }

    // Interestingly, this will print "same", even though we are testing the strings the wrong way. Why is that? Because the
    // Java Language Specification (Section 3.10.5: String Literals) stipulates that any two strings >>literal<< consisting of
    // the same character will actually be represented by the same Java object. Hence, the == test will give true for equal
    // literals. (The string literals are "interned" and added to a shared "string pool" when your code is loaded, but that is
    // actually an impolementation detail.)

    // To add to the confusion, the Java Language Specification also stipulates that when you have a compile-time
    // constant expression that concatenates two string literals, that is equivalent to a single liteal. Thus:
    public class Test1 {
        public static void main(String[] args) {
            String s1 = "hello";
            String s2 = "hel" + "lo";
            String 3 = " mum";
            if (s1 == s2) {
                System.out.println("1. same");
            } else {
                System.out.println("1. different");
            }
            if (s1 + s3 == "hello mum") {
                System.out.println("2. same");
            } else  {
                System.out.println("2. different");
            }
        }
    }

    // This will output "1. same" and "2. different". In the first case, the + expression is evaluated at compile time and we
    // compare one String object with itself. In the second case, it is evaluated at run time and we compare two different
    // String objects.

    // In summary, using == to test strings in Java is almost always incorrect, but it is not guaranteed to give the wrong
    // answer.


    /// Section 130.3: Pitfall: forgetting to free resources














}
