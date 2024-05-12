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

// 19:30

// Section 57.2: Consuming Streams

// 2024 05 12 12:48

// A Stream will only be traversed when there is a terminal operation,
// like count(), collect() or forEach().
// Otherwise, no operation on the Stream will be performed.

// In the following example, no terminal operation is added to the Stream,
// so the filter() operation will not be invoked and
// no output will be produced because peek() is NOT a terminal operation.

IntStream.range(1, 10).filter(a -> a % 2 == 0).peek(System.out::println);
// Live on Ideone

// This is a Stream sequence with a valid terminal operation, thus an output is produced.

// You could also use forEach instead of peek:
IntStream().range(1, 10).filter(a -> a % 2 == 0).forEach(System.out.println);
// Output:
// 2
// 4
// 6
// 8

// After the terminal operation is performed, the Stream is consumer and cannot be reused.

// Although a given stream object cannot be reused,
// it's easy to create a reusable Iterable, that delegates to a stream pipeline.
// This can be useful for returning a modified view of a live data set
// without having to collect results into a temporary structure.

List<String> list = Arrays.asList("FOO", "BAR");
Iterable<String> iterable = () -> list.stream().map(String::toLowerCase).iterator();

for (String str : iterable) {
	System.out.println(str);
}
for (String str : iterable) {
	System.out.println(str);
}
// Output:
// foo
// bar
// foo
// bar

// This works because Iterable declares a single abstract method Iterator<T> iterator().
// That makes it effectively a functional interface,
// implemented by a lambda that creates a new stream on each call.

// In general, a stream operates as shown in the following image:

// 				Predicate
//					v
// transaction -> filter -> collect

// Note: Argument checks are always performed, even without a terminal operation:
try {
	IntStream.range(1, 10).filter(null);
} catch (NullPointerExepction e) {
	System.out.println("We got a NullPointerExepction as null was passed as an argument to filter()");
}
// Output:
// We got a NullPointerException as null was passed as an argument to filter()



//# Section 57.3: Creating a Frequency Map

// The groupingBy(classifier, downstream) collector allows the collection
// of Stream elements into a Map by classifying each element in a group
// and performing a downstream operation on the elements classified in the same group.

// A classic example of this principle is to use a Map to count the occurrences
// of elements in a Stream. In this example, the classifier is simply
// the identity function, which returns the element as-is.
// The downstream operation counts the number of equal elements, using counting().
Stream.of("apple", "orange", "banana", "apple")
		.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
		.entrySet()
		.forEach(System.out::println);
		
// The downstream operation is itself a collector (Collectors.counting())
// that operates on elements of type String and produces a result of type Long.
// The result of the collect mehtod call is a Map<String, Long>.
// This would produce the following output:
// banana=1
// orange=1
// apple=2


//# Section 57.4: Infinite Streams

// It is possible to generate a Stream does not end.
// Calling a terminal method on an infinite Stream causes the Stream
/// the enter an infinite loop. The limit method of a Stream can be used
// to limit the number of terms of the Stream that Java processes.

// This example generates a Stream of all natural numbers, starting with the number 1.
// Each successuve term of the Stream is one higher than the previous.
// By calling the limit method of this Stream, only the first five terms
// of the Stream are considered and printed.

// Generate infinite stream - 1, 2, 3, 4, 5, 6, 7, ...
IntStream naturalNumbers = IntStream.iterate(1, x -> x + 1);

// Print out only the first 5 termns.
naturalNumbers.limit(5).forEach(System.out::println);
// Output:
// 1
// 2
// 3
// 4
// 5

// Another way of generating an infinite stream is using the Stream.generate method.
// This method takes a labmda of type Supplier.

// Generate an infinite stream of random numbers
Stream<Double> infiniteRandomNumbers = Stream.generate(Math::random);

// Print out only the first 10 random numbers
infiniteRandomNumbers.limit(10).forEach(System.out::println);


// Section 57.5: Collect Elements of a Stream into a Collection

// Collect with toList() and toSet()

// Elements from a Stream can be easily collected into a container
// by using the Stream.collect operation:
System.out.println(Arrays.
	.asList("apple", "banana", "pear", "kiwi", "orange")
	.stream()
	.filter(s -> s.contains("a"))
	.collect(Collectors.toList())
);
// prints: [apple, banana, pear, orange]

// Other collection instances, such as a Set, can be made
// by using other Collects built-in methods. 
// For example, Collectos.toSet() collects the elements of a Stream into a Set.














