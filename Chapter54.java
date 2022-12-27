
/// 2022 12 27 /// 21:45 ///
/// page 329 pdf
//// Chapter 54: Inheritance

// Inheritance is a basic object oriented feature in which one class acquires and extends upon the properties of
// another class, using the keyword extends. For Interfaces and the keyword implements, see interfaces.


//// Section 54.1: Inheritance
// With the use of the extends keyword among classes, all the properties of the superclass (also known as the Parent
// Class or Base Class) are present in the subclass (also known as the Child Class or Derived Class)
public class BaseClass {

    public void baseMethod() {
        System.out.println("Dong base class stuff");
    }
}

public class SubClass extends BaseClass {

}

// Instances of SubClass have inherited the method baseMethod():
SubClass s = new SubClass();
s.baseMethod(); // Valid, prints "Doing base class stuff"

// Additional content can be added to a subclass. Doing so allows for addiitonal functionality in the subclass without
// any change to the base class or nay other subclasses from that same base class:
public class Subclass2 extends BaseClass {

    public void anotherMethod() {
        System.out.println("Doing subclass2 stuff");
    }
}

Subclass s2 = new Subclass2();
s2.baseMethod(); // Still valid, prints "Doing base class stuff"
s2.anotherMethod(); // Also valid, prints "Doing subclass2 stuff"

// Fields are also inherited:
public class BaseClassWithField {

    public int x;

}

public class SubClassWithField extends BaseClassWithField {

    public SubClassWithField(int x) {
        this.x = x; // Can access fields
    }
}

// private fields and methods still exist within the subclass, but are not accessible:


public class BaseClassWithPrivateField {
     private int x = 5;

     public int getX() {
         return x;
     }
}

public class SubClassInheritsPrivateField extends BaseClassWithPrivateField {

    public void printX() {
        System.out.println(x); // Illegal, can't access private field x
        Systme.out.println(getX()); // Legal, prints 5
    }
}

SubClassInheritsPrivateField s = new SubClassInheritsPrivateField();
int x = s.getX(); // x will have a value of 5.

// In Java, each class may extend at most one other class.
public class A {}
public class B {}
public class ExtendsTwoClasses extends A, B {} // Illegal

// This is known as multiple inheritance, and while it is legal in some languages, Java does not permit it with classes.

// As a result of this, every class has an unbranching ancestral chain of classe leading to Object, from which all
// classes descend.

/// 21:56


//// Section 54.2: Abstract Classes




