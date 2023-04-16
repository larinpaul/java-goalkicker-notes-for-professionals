//// 2023/04/15 // 21:39 //

//// Chapter 79: Interfaces

// An interface is a reference type, similar to a class, which can be declared by using interface keyword. Interfaces can
// contain only constants, method signatures, default methods, static methods, and nested types.
// Method bodies exist only for default methods and static method. Like abstract classes, interfaces cannot be instantiateed
// they can only be implemented by classes or extended by other interfaces. Interface is a common way to achieve
// full abstraction in java.

//// Section 79.1: Implementing multiple interfaces

// A Java class can implement multipla interfaces

public interface NoiseMaker {

    String noise = "Making Noise"; // Interface variables are public static final by default

    String makeNoise(); // Interfaces methods are public abstract by default

}

public interface FoodEater {
    void eat(Food food);
}

public class Cat implements NoiseMakes, FoodEater {
    @Override
    public String makeNoise() {
        return "meow";
    }

    @Override
    public void eat(Food food) {
        System,out.println("meows appreciatively");
    }
}

// Notice how the Cat class MUST implement the inherited abstract methods in both the interfaces. Furthermore,
// notice how a class can practically implement as many interfaces as needed (there is a limit of 65,535
/// due to JVM Limitation).

NoiseMaker noiseMaker = new Cat(); // Valid
FoodEater foodEater = new Cat(); // Valid
Cat cat = new Cat(); // Valid

Cat invalid1 = new NoiseMaker(); // Invalid
Cat invalid2 = new FoodEater(); // Invalid

// Note:
// 1. All variables declared in an interface are public static final
// 2. All methods declared in an interface methods are public abstract (This statement is valid only through
// Java 7. From Java 8, you are allowed to have methods in an interface, which need not be abstract;
// such methods are known as default methods)
// 3. Interfaces cannot be declared as final
// 4. If more than one interface declares a method that has identical signature, then effectively it is treated as only
// one method and you cannot distinguish from which interface the method is implemented.
// 5. A corresponding InterfaceName.class file would be generated for each interface, upon compilation.


//// Section 79.2: Declaring and Implementing an Interface

// Declaration of an interface using the interface keyword:
public interface Animal {
    String getSound(); // Interface methods are public by default
}

// Override Annotation
@Override
public String getSound() {
    // Code goes here...
}

// This forces the compiler to check that we are overriding and prevents the program from defining a new method or
// messing up the method signature.

// Interfaces are implemented using the implements keyword.
public class Cat implements Animal {

    @Override
    public String getSound() {
        return "meow";
    }
}

public class Dog implements Animal {

    @Override
    public String getSound() {
        return "woof";
    }
}

// In the example, classes Cat and Dog MUST define the getSound() method as methods of an interface are inherently
// abstract (with the exception of default methods).

// Using the interfaces
Animal cat = new Cat();
Animal dog = new Dog();
System.out.println(cat.getSound()); // prints "meow"
System.out.println(dog.getSound()); // prints "woof"


//// Section 79.3: Extending an interface
// An interface can extend another interface via the extends keyword.

public interface BasicResourceService {
    Resource getResource();
}

public interface ExtendedResourceService extends BasicResourceService {
    void updateResource(Resource resource);
}

// Now a class implementing ExtendedResourceService will need to implement both getResource() and updateResource().

// Extending multiple interfaces

// Unlike classes, the extends keyword can be used to extend multiple interfaces (Separated by commas) allowing for
// combinations of interfaces into a new interface

public interface BasicResourceService {
    Resource getResource();
}

public interface AlternateResourceService {
    Resource getAlternateResource();
}

public interface ExtendedResourceService extends BasicResourceService, AlternateResourceService {
    Resource updateResource(Resource resource);
}

// In this case a calss implementing ExtendedResourceService will need to implement getResource(),
// getAlternateResource(), and updateResource().


//// Section 79.4: Usefulness of interfaces

// Interfaces can be extremely helpful in many cases. For example, say you had a list of animals you wanted to
// loop through the list, each printing the sound they make.
{cat, dog, bird}

// One way to do this would be to use interfaces. This would allow for the same method to be called on all
// of the classes
public interface Animal {
    public String getSound();
}

// Any class that implements Animal must also have a getSound() method in them, yet they can all have different
// implementations
public class Dog implements Animal {
    public String getSound() {
        return "Woof";
    }
}

public class Cat implements Animal {
    public String getSound() {
        return "Meow";
    }
}

public class Bird implements Animal {
    public String getSound() {
        return "Chirp";
    }
}

// We now have three different classes, each of which has a getSound() method. Because all of these classes
// implement the Animal interface, which declares the getSound() method, any instance of an Animal can have
// getSound() called on it
Animal dog = new Dog();
Animal cat = new Cat();
Animal bird = new Bird();

dog.getSound(); // "Woof"
cat.getSound(); // "Meow"
bird.getSound(); // "Chirp"

// Because each of these is an Animal, we could even put the animals in a list, loop through them,
// and print out their sounds
Animal[] animals = { new Dog(), new Cat(), new Bird() };
for (Animal animal : animals) {
    System.out.println(animal.getSound());
}

// Because the order of the array is Dog, Cat, and then Bird, "Woof Meow Chrip" will be printed to the console

// Interfaces can also be used as the return value for functions. For example, returning a Dog if the input is "dog",
// Cat if the input is "cat", and Bird if it is "bird", and then printing the sound of that animal could be done using
public Animal getAnimalByName(String name) {
    switch(name.toLowerCase()) {
        case "dog":
            return new Dog();
        case "cat":
            return new Cat():
        case "bird":
            return new Bird();
        default:
            return null;
        }
}

public String getAnimalSoundByName(String name) {
    Animal animal = getAnimalByName(name);
    if (animal == null) {
        return null;
        } else {
        return animal.getSound();
        }
        }

String dogSound = getAnimalSoundByName("dog"); // "Woof"
String catSound = getAnimalSoundByName("cat"); // "Meow"
String birdSound = getAnimalSoundByName("bird"); // "Chirp"
String lightbulbSound = getAnimalSoundByName("lightbulb"); // null

// Interfaces are also useful for extensibility, because if you want to add a new type of Animal,
// you wouldn't need to change anything with the operations you perform to them.


//// Section 79.5: Default methods

// Introduced in Java 8, default methods are a way of specifying an implementation inside an interface.
// This could be used to aboid the typical "Base" or "Abstract" class by providing a partial implementation
// of an interface, and restricting the subclasses hierarchy.

// Observer pattern implementation

// For example, it's possible to implement the Observer-Listener pattern directly into the interface,
// providing more flexibility to the implementing classes.
interface Observer {
    void onAction(String a);
}

interface Observable {
    public abstract List<Observer> getObservers();

    public default void addObserver(Observer e) {
        getObservers().add(o);
    }

    public default void notify(String something) {
        for (Observer l : getObservers()) {
            l.onAction(something);
        }
    }
}

// Now, any class can be made "Observable" just by implementing the Observable interface, while being free to be
// part of a different class hierarchy.

abstract class Worker {
    public abstract void work();
}

public class MyWorker extends Worker implements Observable {

    private List<Observer> myObservers = new ArrayList<Observer>();

    @Override
    public List<Observer> getObservers() {
        return myObservers;
    }

    @Override
    public void work() {
        notify("Started work");

        // Code goes here...

        notify("Completed work");
    }

    public static void main(String[] args) {
        MyWorker w = new MyWorker();

        w.addListener(new Observer() {
            @Override
            public void onAction(String a) {
                System.out.println(a + " (" + new Date() + ")");
            }
        });

        w.work();
    }
}


// Diamond problem

// The compiler in Java 8 is aware of the diamond problem which is caused when a class is implementing interfaces
// containing a method with the same signature.

// In order to solve it, an implementing class must override the shared method and provide its own implementation.
interface InterfaceA {
    public defualt String getName() {
        return "a";
    }
}

interface InterfaceB {
    public default String getName() {
        return "b";
    }
}

public class ImpClass implements InterfaceA, InterfaceB {

    @Override
    public String getName() {
        // Must provide its own implementation
        return InterfaceA.super.getName() + InterfaceB.super.getName()
    }

    public static void main(String[] args) {
        ImpClass c = new ImpClass();

        System.out.println(c.getName()); // Prints "ab"
        System.out.println(((InterfaceA)c).getName()); // Prints "ab"
        System.out.println(((InterfaceB)c).getName()); // Prints "ab"
    }
}

// There's still the issue of having methods with the same name and parameters with different return types,
// which will not compile.

// Use default methods to resolve compatibility issues

// The default method implementations come in very handy if a method is added to an interface in an existing system
// where the interface is used by several classes.

// To aboid breaking up the entire system, you can provide a default method implementation when you add a method
// to an interface. This way, the system will still compile and the actual implementation can be done step by step.

// For more information, see the Default Methods topic.

/// 22:27



//// 2023/04/16 // 11:41 //
//// Section 79.6: Modifiers in Interfaces

// The Oracle Java Style Guide states:

// Modifiers should not be written out when they are implicit.

// (See modifiers in Oracle Official Code Standard for the context and a link to the actual Oracle document)

// This style guidance applies particularly to interfaces. Let's consider the following code snippet:
interface I {
    public static final int VARIABLE = 0;

    public abstract void method();

    public static void staticMethod() { ... }
    public default void defaultMethod() { ... }
}

// Variables

// All interface variables are implicitly constants with implicit public (accessible for all), static (areaccessible
// by interface name) and final (must be initialized during declaration) modifiers:
public static final int VARIABLE = 0;

// Methods:
// 1. All methods which don't provide implementation are implicitly public and abstract.
public abstract void method();
// Version >= Java SE 8

// 2. All methods with static or default modifier must provide implementation and are implicitly public.

// After all of the above changes have been applied, we will get the following:
interface I {
    int VARIABLE = 0;

    void method();

    static void staticMethod() { ... }
    default void defaultMethod() { ... }
}


//// Section 79.7: Using Interfaces with Generics

// Let's say you want to define an interface that allows publishing / consulting data to and from different
// types of channels (e.g. AMQP, JMS, etc), but you want to be able to switch out of the implementation detauls...

// Let's define a basic IO interface that can be re-used across multiple implementations:
public interface IO<IncomingTyoe, OutgoingType> {

    void publish(OutgoingType data);
    IncomingType consume();
    IncomingType RPCSubmit(OutgoingType data);

}

// Now I can instantiate that interface, but since we don't have default implementation for those methods, it'll
// need an implementation when we instantiate it:
IO<String, String> mockIO = new IO<String, String>() {

    private String channel = "somechannel";

    @Override
    public void publish(String data) {
        System.out.println("Publishing " + data + " to " + channel);
    }

    @Override
    public String consume() {
        System.out.println("Consuming from " + channel);
        return "some useful data";
    }

    @Override
    public String RPCSubmit(String data) {
        return "received " + data + " just now ";
    }

};

mockIO.consume(); // prints: Consuming from somechannel
mickIO.publish("TestData"); // Publishing TestData to somechannel
System.out.println(mockIO.RPCSubmit("TestData")); // received TestData just now

// We can also do something more useful with that interace, let's say we want to use it
// to wrap some basic RabbitMQ functions:
public class RabbitMQ implements IO<String, String> {

    prviate String exchange;
    private String queue;

    public RabbitMQ(String exchange, String queue) {
        this.exchange = exchange;
        this.queue = queue;
    }

    @Override
    public void publish(String data) {
        rabbit.basicPublish(exchange, queue, data.getBytes());
    }

    @Override
    public String consume() {
        return rabbit.basicConsume(exchange, queue);
    }

    @Override
    public String RPCSubmit(String data) {
        return rabbit.rpcPublish(exchange, queue, data);
    }

}

// Let's say I want to use this IO interface now as a way to count visits to my website since my last system restart
// and then be able to display the total number of visits - you can do something like this:
import java.util.concurrent.atomic.AtomicLong;

public class VisitCouner implements IO<Long, Integer> {

    private static AtomicLong websiteCounter = new AtomicLong(0);

    @Override
    public void publish(Integer count) {
        websiteCounter.addAndGet(count);
    }

    @Override
    public Long consume() {
        return websiteCounter.get();
    }

    @Override
    public Long RPCSubmit(Integer count) {
        return websiteCounter.addAndGet(count);
    }
}

// Now let's use the VisitCounter:
VisitCounter counter = new VisitCouner();

// just had 4 visits, yay
counter.publish(4);
// just had another visit, yay
counter.publish(1);

// get data for stats counter
System.out.println(counter.consume()); // prints 5

// show data for stats counter page, but include that as a page view
System.out.println(counter.RPCSubmit(1)); // prints 6

// When implementing multiple interfaces, you can't implement the same interface twice.
// That also applies to generic interfaces. Thus, the following code is invalid, and will result in a compile error:
interface Printer<T> {
    void print(T value);
}

// Invalid!
class SystemPrinter implements Printer<Double>, Printer<Integer> {
    @Override public void print(Double d) { System.out.println("Decimal: " + d); }
    @Override public void print(Integer i) { System.out.println("Discrete: " + i); }
}


//// Section 79.8: Strengthen bounded type parameters

// Bounded type parameters allow you to set restrictions on generic type arguments:
class SomeClass {

}

class Demo<T extends SomeClass> {

}

// But a type parameter can only bind to a single class type.

// An interface type can be bound to a type that already had a binding. This is achieved using the & symbol:
interface SomeInterface {

}

class GenericClass<T extends SomeClass & SomeInterface> {

}

// This strengthens the bind, potentially requiring type arguments to derive from multiple types.

// Multiple interface types can be bound to a type parameter:
class Demo<T extends SomeClass & FirstInterface & SecondInterface> {

}

// But should be used with caution. Multiple interface bindings is usually a sing of a code smell,
// suggesting that a new type should be created which acts as an adapter for the other types:
interface NewInterface extends FirstInterface, SecondInterface {

}


//// Section 79.9: Implementing interfaces in an abstract class
// A method defined in an interface is by default public abstract. When an abstract class implements an
// interface, any methods which are defined in the interface do not have to be implemented by the abstract
// class. This is because a class that is declared abstract cn contain abstract method declarations. It is therefore
// the responsibility of the first concrete su-class to implement any abstract methods inherited from any interfaces
// and/or the abstract class.
public interface NoiseMakes() {
    void makeNoise();
}

public abstract class Animal implements NoiseMaker {
    // Does not need to declare or implement makeNoise()
    public abstract void eat();
}

// Because Dog is concrete, it must define both makeNoise() and eat()
public class Dog extends Animal {
    @Override
    public void makeNoise() {
        System.out.println("Borf borf");
    }

    @Override
    public void eat() {
        System.out.println("Dog eats some kibble.")
    }
}

// From Java 8 onward it is possible for an interface to declare default implementations of methods which means
// the method won't be abstract, therefore any concrete sub-classes will not be forced to implements the method
// but will inherit the default implementation unless overridden.
