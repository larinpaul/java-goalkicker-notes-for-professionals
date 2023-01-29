
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
        System.out.println("Doing base class stuff");
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
        System.out.println(getX()); // Legal, prints 5
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


/// 2022 12 29 /// 17:18 ///
//// Section 54.2: Abstract Classes

// An abstract class is a class marked with the abstract keyword. It, contrary to non-abstract classes, may contain
// abstract - implementation-less - methods. It is, however, valid to create an abstract class without abstract methods.

// An abstractclass cannot be instantiated. It can be sub-classed (extended) as long as the sub-class is either also
// asbtract, or implements all methods marked as abstract by super classes.

// An example of an abstract class:
public abstract class Component {
    private int x, y;

    public setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void render();
}

// The class must be marked abstract, when it has at least one abstract method. An abstract method is a method that
// has no implementation. Other methods can be declared within an abstract class that have implementation in order
// to provide common code for any sub-classes.

// Attempting to instantiate this class will provide a compile error:
//error: Component is abstract; cannot be instantiated
Component myComponent = new Component();

// However a class that extends Component, and provides an implementation for all of its abstract methods and can be
// instantiated

public class Button extends Component {

    @Override
    public void render() {
        //render a button
    }
}

public class TextBox extends Component {

    @Override
    public void render() {
        //render a textbox
    }
}

// Instances of inheriting classes also can be cast as the parent class (normal inheritance) and they provide a
// polymorphic effect when the abstract method is called.
Component myButton = new Button();
Component myTextBox = new TextBox();

myButton.tender(); // renders a button
myTextBox.render(); // renders a text box


// Abstract classes vs Interfaces

// Abstract classes and interfaces both provide a way to define method signatures while requiring the
// extending/implementing class to provide the implementation.

// There are two key differences between abstract classes and interfaces:
// * A class may only extend a single class, but may implement many interfaces
// * An abstract class can contain instance (non-static) fields, but interfaces may only contain static fields.

// Version < Java SE 8

// Methods declared in interfaces could not contain implementations, so abstract classes were used when it was
// useful to provide additional methods which implementations called the abstract methods.

// Version >= Java SE 8

// Java 8 allows interfaces to contain default methods, usually implemented using the other methods of the interface,
// making interfaces and abstract classes equally powerful in this regard.


// Anonymous subclasses of Abstract Classes

// As a convenience Java allows for instantiation of anonymous instances of subclasses of abstract classes, which
// provide implementations for the abstract methods upon creating the new object. Using the above example this
// could look like this:

Component myAnonymous Component = new Component() {
    @Override
    public void render() {
        // render a quick 1-time use component
    }
}


//// Section 54.3: Using 'final' to restrict inheritance and overriding

// Final classes

// When used in a class declaration, the final modifier prevents other classes from being declared that extend the
// class. A final class is a "leaf" class in the inheritance class hierarchy.

// This declares a final class
final class MyFinalClass {
    /* some code */
}

// Compilation error: cannot inherit from final MyFinalClass
cxlass MySubClass extends MyFinalClass {
    /* more code */
}

// Use-cases for final classes

// Final classes can be combined with a private constructor to control or prevetn the instantiation of a class. This can
// be used to create a so-called "utility class" that only defines static members; i.e. constants and static methods.

public final class UtilityClass {

    // Private constructor to replace the default visible constructor
    private UtilityClass() {}

    // Static members can still be used as usual
    public static int doSomethingCool() {
        return 123;
    }

}

// Immutable classes should also be declared as final. (An immutable class is one whose intances cannot be
// changed after they have been created; see the Immutable Objects topic. ) By doing this, you make it impossible to
// create a mutable subclass of an immutable class. That would violate the Liskov Substitution Principle which
// requires taht a subtype should obey the "behavioral contract" of its supertypes.

// From a practical perspective, declaring an immutable class to be final makes it easier to reason about program
// behavior. It also addresses security concerns in the scenario where untrusted code is executed in a security
// sandbox. (For instance, since String is declared as final, a trusted class does not need to worry that it might be
// tircked into accepting mutable subclass, which the untructed caller could then surreptitiously change.)

// One disadvantage of final classes is that they do not work with some mocking frameworks such as Mockito.
// Update: Mockito version 2 now supports mocking of final classes.


// Final methods

// The final modifier can also be applied to methods to prvent them being overriden in sub-classes:
public class MyClassWithFinalMethod {

    public final void someMethod() {

    }
}

public class MySubClass extends MyClassWithFinalMethod {

    @Override
    public void someMethod() { // Compiler error (overriden method that is final)

    }
}

// Final methods are typically used when you want to restrict what a subclass can change in a class without forbidding
// subclasses entirely.

// The final modifier can also be applied to variables, but the meaning of final for variables in unrelated to
// inheritance.


//// Section 54.4: The Liskov Substitution Principle

// Substitutability is a principle in object-oriented programming introduced by Barbara Liskov in 1987 conference
/// keynote stating that, if class B is a subclass of class A, then wherever A is expected, B can be used instead:
class A {...}
class B extends A {...}

public void method(A obj) {...}

A a = new B(); // Assignment OK
method(new B()); // Passing as parameter OK

// This also applies when the type is an interface, where there doesn't need to be any hierarchical relationship between
// the objects:
interface Foo {
    void bar();
}

class A implements Foo {
    void bar() {...}
}

class B implements Foo {
    void var() {...}
}

List<Foo> foos = new ArrayList<>();
foos.add(new A()); // OK
foos.add(new B()); // OK

// Now the list contains objects that are not from the same class hierarchy.


//// Section 54.5: Abstract class and Interface usage: "Is-a"
//// relation vs "Has-a" capability

// When to use abstract classes: To implement the same or different behaviour among multiple related objects

// When to use itnerfaces: to implement a contract by multiple unrelated objects

// Abstract classes create "is a" relations while interfaces provide "has a" capability.

// This can be seen in the code below:
public class InterfaceAndAbstractClassDemo {
    public static void main(String args[]){

        Dog dog = new Dog("Jack", 16);
        Cat cat = new Cat("Joe", 20);


        System.out.println("Dog:"+dog);
        System.out.println("Cat:"+cat);

        dog.remember();
        dog.protectOwner();
        Leadn dl = dog;
        dl.learn();

        cat.remember();
        cat.protectOwner();

        Climb c = cat;
        c.climb();

        Man man = new Man("Ravindra",40);
        System.out.println(man);

        Climb cm = man;
        cm.climb();
        Think t = man;
        t.think();
        Learn l = man;
        l.learn();
        Apply a = man;
        a.apply();
    }
}

abstract class Animal {
    String name;
    iny lifeExpectancy;
    public Animal(String name, int lifeExpectancy) {
        this.name = name;
        this.lifeExpectancy = lifeExpectancy;
    }
    public abstract void remember();
    public abstract void protectOwner();

    public String toString() {
    return this.getClass().getSimpleName()+":"+name+":"+lifeExpectancy;
    }
}

class Dog extends Animal implements Learn {
    public Dog(String name, int age) {
        super(name, age);
    }
    public void remember(){
        System.out.println(this.getClass().getSimpleName()+" can remember for 5 minutes");
    }
    public void protectOwner() {
        System.out.println(this.getClass().getSimpleName()+" will protect the owner");
    }
    public void learn() {
        System.out.println(this.getClass().getSimpleName()+ " can learn:");
    }
}

class Cat extends Animal implements Climb {
    public Cat(String name, int age) {
        super(name, age);
    }
    public void remember() {
        System.out.println(this.getClass().getSimpleName() + " can remember for 16 hours");
    }
    public void protectOwner() {
        System.out.println(this.getClass().getSimpleName()+ " won't protect owner");
    }
    public void climb() {
        System.out.println(this.getClass().getSimpleName()+ " can climb");
    }
}

interface Climb {
    void climb();
}

interface Think {
    void think();
}

interface Learn {
    void learn();
}

interface Apply {
    void apply();
}

class Man implements Think, Learn, Apply, Climb {
    String name;
    int age;

    public Man(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public void think() {
        System.out.println("I can think:" + this.getClass().getSimpleName());
    }
    public void learn() {
        System.out.println("I can learn:"+this.getClass().getSimpleName());
    }
    public void apply() {
        System.out.println("I can apply:"+this.getClass().getSimpleName());
    }

    public void climb() {
        System.out.println("I can climb:" + this.getClass().getSimpleName());
    }
    public String toString() {
        return "Man :"+name+":Age:"+age;
    }
}

// output:
// Dog:Dog:Jack:16
// Cat:Cat:Joe:20
// Dog can remember for 5 minutes
// Dog will protect owner
// Dog can learn:
// Cat can remember for 16 hours
// Cat won't protect owner
// Cat can climb
// Man :Ravindra:Age:40
// I can Climb:Man
// I can think:Man
// I can learn:Man
// I can apply:Man

// Key notes:
// 1. Animal is an abstract class with shared attributes: name and lifeExpectancy and abstract methods:
// remember() and protectOwner(). Dog and Cat are Animals that have implemented the remember() and
// protectOwner() methods.
// 2. Can can climb() but Dog cannot. Dog can think() but Cat cannot. These specific capabilities are added to Cat
// and Dog by implementation.
// 3. Man is not an Animal but he can Think, Learn, Apply, and Climb.
// 4. Cat is not a Man but it can Climb.
// 5. Dog is not a Man but it can Learn.
// 6. Man is neither a Cat not a Dog but can have some of the capabilities of the latter two without extending
// Animal, Cat, or Dog. This is done with Interfaces.
// 7. Even though Animal is an abstract class, it has a constructor, unlike an interface.

// TL;DR:
// Unrelated classes can have capabiulities through interfaces, but related classes change the behavior through extension of
// base classes.

// Refer to the Java documentation page to understand which one to use in a specific use case.

// Consider using abstract classes if...
// 1. You want to share code among several closely related classes.
// 2. You expect that classes that extned your abstract class have many common methodds or fields, or require
// access modifiers other than public (such as protected and private).
// 3. You want to declare non-static or non-final fields.

// Consider using interfaces if...
// 1. You expect that unrelated classes would implement your interface. For example, many unrelared objects can
// implement the Serializable interface.
// 2. You want to specify the behaviour of a particular data type but are not concerned about who implements its
// behaviour.
// 3. You want to take advanteage of multiple inheritance of type.


/// page 337 pdf
//// Section 54:6: Static Inheritance

// Static method can be inherited similarly to normal methods, however unlike normal methods it is impossible to
// create "abstract" methods in order to force statiuc method overriding. Writing a method with the same signature as
// a static method in a super class appears to be a form of overridingm but really this simply creates a new function
// and hides the other one.
public class BaseClass {

    public static int num = 5;

    public static void sayHello() {
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        BaseClass.sayHello();
        System.out.println("BaseClass's num: "+ BaseClass.num);

        SubClass.sayHello();
        // This will be different form the above statement's output, since it runs
        // a different method
        SubClass.sayHello(true);

        StaticOverride.sayHello();
        System.out.println("StaticOverride's num: " + StaticOverride.num);
    }
}

public class SubClass extends BaseClass {

    // Inherits the sayHello function, but does not override it
    public static void sayHello(boolean test) {
        System.out.println("Hey");
    }
}

public static class StaticOverride extends BaseClass {

    // Hides the num field from BaseClass
    // You can erven changethe type, since this doesn't affect the signature
    public static void sayHello(boolean test) {
        System.out.println("Hey");
    }
}

public static class StaticOverride extends BaseClass {

    // Hides the num field from BaseClass
    // You can even change the tpye, since this doesn't affect the signature
    public static String num = "test";

    // Cannot use @Override annotation, since this is static
    // This overrides the sayHello method form BaseClass
    public static void sayHello() {
        System.out.println("Static says Hi");
    }

}

// Running any of these classes produces the output:

// Hello
// BaseClass's num: 5
// Hello
// Hey
// Static says Hi
// StaticOverride's num: test

// Note that unlike normal inheritance, in static inheritance methods are not hidden. You can always call the base
// sayHello method by using BaseClass.sayHello(). But classes do inherit static methods if no methods with the
// same signature are foudn in the subclass. If two method's signatures vary, both methods can be run from the
// subclass, even if the name is the same.

// Static fields hide each other in a similar way.


//// Section 54.7: Programming to an interface

// The idea behind programming to an interface is to base the code primarily on itnterfaces and only use concrete
// classes at the time of intantiation. In this context, good code dealing with e.g. Java collections will look something
// like this (not that the method itself is of any use at all, just illustration):
public <T> Set<T> toSet(Collection<T> collection) {
    return Sets.newHashSet(collection);
}

// while bad code might look like this:
public <T> HasSet<T> toSet(ArrayList<T> collection) {
    return Sets.newHashSet(collection);
}

// Not only the former can be applied to a wider choice of argumments, its results will be more compatible with the code
// provided by other developers that generally adhere to the concept of programming to an interface. However, the
// most important reasons to use the former are:
// * most of the time the context, in which the result is used, does not and sohuld not need that many details as
// the concrete implementation provides;
// * adhering to an interface forces cleaner code and less hacks such as yet another public method gets added to
// a class serving some specific scenarion;
// * the code is more testable as interfaces are easily mockable;
// * finally, the concept helps even if only one implementation is expected (at least for testability).

// So how can one easily apply the concept of programming to an interface when writing new code having in mind
// one particular implementation? One option that we commonly use is a combination of the following patterns:
// * programming to an interface
// * factory
// * builder

// The following example based on these principles is a simplified and truncated version of RPC implementation
// written for a number of different protocols:
public interface RemoteInvoker {
    <RQ, RS> CompletableFuture<RS> invoke(PQ request, Class<RS> responseClass);
}

// The above interface is not supposed to be instantiated directly via a factory, isntead we derive further more
// concrete interfaces, one for HTTP invocation and one for AMQP, each then having a factory and a builder to
// construct instances, which in turn are also instances of the above interfaces:
public interface AmqpInvoker extends RemoteInvoker {
    static AmqpInvokerBuilder with(String instanceId, ConnectionFactory factory) {
        return new AmqpInvokerBuilder(instanceId, factory);
    }
}

// Instances of RemoteInvoker for the use with AMQP can now be constructed as easily as (or more involved depending
// on the builder):
RemoteInvoker invoker = AmqpInvoker.with(instanceId, factory)
        .requestRouter(route)
        .build();

// And an invocation of a request is as easy as:
Response res = invoker.invoke(new Request(data), Response.calss).get();

// Due to Java 8 permitting placing of static methods directly into interfaces, the intermediate factory has become
// implicit in the above code replaced with AmqpInvoker.with(). In Java prior to version 8, the same effect can be
// achieved with an inner Factory class:
public interface AmqpInvoker extends RemoteInvoker {
    class Factory {
        public static AmqpInvokerBuilder with(String instanceId, ConnectionFactory factory) {
            return new AmqpInvokerBuilder(instanceId, factory);
        }
    }
}

// The corresponding instantation would then turn into:
RemoteInvoker invoker = AmqpInvoker.Factory.with(instanceId, factory)
        .requestRouter(router)
        .build();

// The builder used above could look like this (although this is a simplification as the actual one permits defining of up
// to 15 parameters deviating form defaults). Note that hte consturct is not public, so it is specifically usable only form
// the above AmqpInvoker interface:
public class AmqpInvokerBuilder {
    ...
    AmqpInvokerBuilder(String instanceId, ConnectionFactory factory) {
        this.instanceId = isntanceId;
        this.factory = factory;
    }

    public AmqpInvokerBuilder requestRouter(RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
        return this;
    }

    public AmqpInvoker build() throws TimeoutException, IOException {
        return new AmqpInvokerImpl(instanceId, factory, requestRouter);
    }
}

// Generally, a buidler can also be generated using a tool like FreeBuilder.

// Finally, the standard (and the only expected) implementation of this interface is defined as a package-local class to
// enforce th use of the interface, the factory and the builder:
class AmqpInvokerImpl implements AmqpInvoker {
    AmqpInvokerImpl(String instanceId, COnnectionFactory factory, RequestRouter requestRouter) {
        ...
    }

    @Override
    public <RQ, RS> CompletableFuture<RS> invoke(final RQ request, final Class<RS> respClass) {
        ...
    }

}

// Meanwhile, this pattern proved to be very efficient in developing all our new code no matter how simple or
// complext the functionality is.


//// Section 54.8: Overridign in Inheritance

// Overriding in Inheritance is used when you use an already defined method from a suepr class in a sub class, but in a
// different way than how the method was originally designed in the super class. Overriding allows the user to reuse
// code by using existing material and modifying it to suit the user's needs better.

// The following example demonstrates how ClassB overroides the functionality of ClassA by changing what gets sent
// out through the printing method:

// Example:
public static void main(String[] args) {
    ClassA a = new ClassA();
    ClassA b = new ClassB();
    a.printing();
    b.printing();
}

class ClassA {
    public void printing() {
        System.out.println("A");
    }
}

class ClassB extends ClassA {
    public void printing() {
        System.out.println("B");
    }
}

// Output:
// A
// B


/// page 341 pdf
//// Section 54.9: Variable shadowing

// Variables are SHAODWS an methods are OVERRIDEN. Which varaible will be used depends on the class that the
// variablei s declared of. Which method will be used depends on the actual class of the object that is referenced by
// the variable
class Car {
    public int gearRatio = 8;

    public String accelerate() {
        return "Accelerate : Car";
    }
}

class SportsCar extends Car {
    public int gearRatio = 9;

    public String accelerate() {
        return "Accelerate : SportsCar";
    }

    public void test() {

    }

    public static void main(String[] args) {

        Car car = new SportsCar();
        System.out.println(car.gearRatio + " " + car.accelerate());
        // will print out 8 Accelerate : SportsCar
    }
}


//// Section 54.10: Narrowing and Widening of object references

// Casting an isntance of a base class to a subclass as in : b = (B) a; is called narrowing (as you are trying to narrow
// the base class object to a more specific class object) and needs an explicit type-cast.

// Casting an instance of a subclass to a base class as in: A a = b; is called widening and does not need a type-cast.

// To illustrate, consider the following class declarations, and test code:
class Vehicle{

}

class Car extends Vehicle {

}

class Truck extends Vehicle {

}

class MotorCycle extends Vehicle {

}

class Test {
    public static void main(String[] args) {

        Vehicle vehicle = new Car();
        Car car = new Car();

        vehicle = car; // is valid, no cast needed

        Car c = vehicle // not valid
        Car c = (Car) vehicle; // valid
    }
}

// The statement Vehicle vehicle = new Car(); is a valid Java statement. Every instance of Car is also a Vehicle.
// Therefore, the assignment is legal without the need for an explicit type-cast.

// On the other hand, Car c = vehicle; is not valid. The static type of the vehicle variable is Vehicle which means
// that it could refer to an instance of Car, Truck, MotorCycle, or any other current or future subclass
// of Vehicle. (Or indeed, an instance of Vehicle itself, since we did not declare it as an abstract class.)
// The assignment cannot be allowed, since that might lead to car referring to a Truck` instance.

// To prevent this situtation, we need to add an explicit type-cast:
Car c = (Car) vehicle;

// The type-cast tells the compiler that we expect the value of vehicle to be a Car or a subclass of Car. If necessary,
// compiler will insert code to perform a run-time type check. If the check fails, then a ClassCastException will be
// thrown when the code is executed.

// Note that not all type-casts are valid. For example:
String s = (String) vehicle; // not valid

// The Java compiler knows that an instance that is type compatible with Vehicle cannot ever be type compatible with
// String. The type-cast could never succeed, and the JLS mandates that this gives in a compilation error.


//// Section 54.11: Inheritance and Static Methods

// In Java, parent and child classes both can have static methods with the same name. But in such cases implementation
// of static method in child is hiding parent class' implementation, it's not method overriding. For example:

class StaticMethodTest {

    // static method and inheritance
    public static void main(String[] args) {
        Parent p = new Child();
        p.staticMethod(); // prints Inside Parent
        ((Child) p).staticMethod(); // prints Inside Child
    }

    static class Parent {
        public static void staticMethod() {
        System.out.println("Inside Parent");
        }
    }

    static class Child extends Parent {
        public static void staticMethod() {
            System.out.println("Inside Child");
        }
    }
}

// Statuc method are bind to a class not to an instance and this method binding happens at compile time. Since in the
// first call to staticMethod(), parent class reference p was used, Parent's version of staticMethod() is invoked. In
// the second case, we did not cast p into Child class, Child's staticMethod() executed.

/// 19:08 ///
