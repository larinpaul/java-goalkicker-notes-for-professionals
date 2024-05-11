//# Chapter 57 Streams

// 2024 05 11 19:00

// A Stream represetns a sequence of elements and stupports different kind of operations
// to perform computations upon those elements. With Java 8, Collection interface
// has two methods to generate a Stream: stream() and parallelStream().
// Stream operations are either intermediate or terminal. Intermediate operations
// return a Stream so multiple intermediate operations can be chained
// before the Stream is closed.
// Terminal operations are either void or return a non-stream result.


//# Section 57.1: Using Streams

// A Stream sequence of elements upon which sequential and parallel aggregate operations
// can be performed. Any given Stream ca npotentially have an unlimited
// amount of data flowing through it. As a result, data received from a Stream
// is processed individually as it arrives, as opposed to performing batch processing
// on the data altogether. When combined with lambda expressions they provide
// a concise way to perform operations on sequnces of data using
// a functional approach.

// Example: (see it work on Ideone)

Stream<String> fruitsStream = Stream.of("apple", "banana", "pear", "kiwi", "orange");

fruitStream.filter(s -> s.contains("a"))
			.map(String::toUpperCase)
			.sorted()
			.forEach(System.out::println);
// Output:
// APPLE
// BANANA
// ORANGE
// PEAR

// The operations performed by the above code can be summarized as follows:
// 1. Create a Stream<String> containing a sequenced ordered Stream of fruit String
// elements using the static factory method Stream.of(values).
// 2. The filter() operation retains only elements that match a given predicate
// (the elements that when tested by the predicate return true)
// The predicate is given as a lambda expression.
// 3. The map() operation transforms each element using a given functon, called a mapper.
// NOTE that the map() operation will return a stream with a different generic type
// if the mapping function returns a type different to its input parameter.
// For example on a Stream<String> calling .map(String::isEmpty) returns a Stream<Boolean>
// 4. The sorted() operation sorts the elements of the Stream according to their natural ordering
// (lexifcographically, in the case of String).
// 5. Finally, the forEach(action) operation performs an action of the Stream,
// passing it to a Consumer.
// In the example, each element is simply being printed to the console.
// This operation is a terminal operation,
// thius making it impossible to operate on it again.

//				Predicate Function Comparator
// fruitStream -> filter -> map -> sorted -> forEach

// Operations (as seen above) are chained together to form 
// what can be seen as a query on the data.

// Closing Streams
// NOTE that a STREAM generally DOES NOT HAVE to be closed.
// It is only required to close streams that operate on IO channels.
// Most Stream types don't operate on resources and therefore don't require closing.

// The Stream interface extends AutoCloseable. Strteams can be closed by calling
// the close method or by using try-with-resource statements.

// An exaple use case where a Stream should be closed
// is when you create a Stream of lines from a file:
try (Stream<String> lines = Files.lines(Paths.get("somePath")))) {
	lines;forEach(System.out::println);
}

// The Stream interface also declares the Stream.onClose() method which allows you
// to registed Runnable handlers which will be called when the stream is closed.
// An example use case is where code which produces a stream needs
// to know when it is consumed to performs some cleanup.
public Stream<String> streamAndDelete(Path path) throws IOException {
	return Files.lines(path).onClose(() -> someClass.deletePath(path));
}
// The run handler will only execute if the close9) method gets called,
// either explicitly or implicitly by a try-with-resources statement.


//* Processing Order

// A Stream object's processing can be sequential or parallerl.

// In a sequential mode, the elements are processed in the order of the source of the Stream.
// If the Stream is ordered (such as a SortedMap implementation or a List)
// the processing is guaranteed to match the ordering of the source.
// In other cases, however, care should be taken not to depend on the ordering
// (see: is the Java HashMap keySet() iteration order consistent?).

// Example:
List<Integer> integerList = Arrays.asList(0, 1, 2, 3, 42);

// sequential
long howManyOddnumbers = integerList.stream()
									.filter(e -> (e % 2) == 1)
									.count();
									
System.out.println(howManyOddNumbers); // Output: 2

// Live on Ideone

// Parallel mode allows the use of multiple threads on multiple cores
// but there is no guarantee of the order in which elements are processed.

// If multiple methods are called on a sequential Stream,
// ot every method has to be invoked. For example, if a Stream is filtered
// and the number of elements is reduced to one, a subsequent call
// to a method such as sort will not occur. This can increase the performance
// of a sequential Stream - an optimization that is not possible with a parallel Stream.

// Example:

// paraller
long howManyOddNumbersParallel = integerList.parallelStream()
											.filter(e -> (e % 2) == 1)
											.count();
									
System.out.println(howManyOddNumbersParallel); // Output: 2

// Live on Ideone


// Differences from Containers (or Collections)

// Containers are more focused on how the elements are stored and how those
// elements can be accessed efficiently.
// A Stream, on the other hand, doesn't provide direct access and manipulation to its elements;
// it is more dedicate to the group of objects as a collective entity
//  it is more dedicated to the group of objects as a collective entity
// and performing operations on that entity as a whole.
// Stream and Collection are separate high-level abstractions for these differing purposes.


// Section 57.2: Consuming Streams

















