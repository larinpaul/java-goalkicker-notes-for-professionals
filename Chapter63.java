import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Chapter63 {

    // A concurrent collection is a [Collection][1] which permits access by more than one thread at the same time. Different
    // threads can typically iterate through the contents of the collection and add or remove elements. The collection is
    // responsible for ensuring that the collection doesn't become corrups. [1]:
    // http://stackoverflow.com/documentation/Java/90/collections#t=201612221936497298484


    //// Section 63.1: Thread=-safe Collections

    // By default, the various Collection types are not thread-safe.

    // However, it's fairly easy to make a collection thread-safe.

    List<String> threadSafeList = Collections.synchronizedList(new ArrayList<String>());
    Set<String> threadSafeSet = Collections.synchronizedSet(new HashSet<String>());
    Map<String, String> threadSafeMap = Collections.synchronizedMap(new HashMap<String, String()>);

    // When you make a thread-safe collection, you should never access it through the original collection, only through
    // the thread-safe wrapper.

    // Version >= Java SE 5

    // Starting with Java 5, java.util.collections has several new thread-safe collections that don't need the various
    // Collections.synchronized methods.
    List<String> threadSafeList = new CopyOnWriteArrayList<String>();
    Set<String> threadSafeSet = new ConcurrentHashSet<String>();
    Map<String, String> threadSafeMap = new ConcurrentHashMap<String, String>();


    //// Section 63.2: Insertion into ConcurrentHashMap

    public class InsertIntoConcurrentHashMap {

        public static void main(String[] args) {
            ConcurrentHashMap<Integer, SomeObject> concurrentHashMap = new ConcurrentHashMap<>();

            SomeObject value = new SomeObject();
            Integer key = 1;

            SomeObject previousValue = concurrentHashMap.putIfAbsent(1, value);
            if (previousValue != null) {
                // Then some other value was mapped to key = 1. 'value' that was passed to
                // putIfAbset method is NOT inserted, hence, any other thread which calls
                // concurrentHashMap.get(1) would NOT receive a reference to the 'value'
                // that your thread attempted to insert. Decide how you wish to handle
                // this situation.
            } else {
                // 'value' reference is mapped to key = 1.
            }
        }
    }


    //// Section 63.3: Concurrent Collections

    // Concurrent collections are a generalization of thread-safe collections, that allow for a broader usage in a concurrent
    // environment.

    // While thread-sage collections have safe element addition or removal from multiple threads, they do not necessarily
    // have safe iteration in the same context (one may not be able to safely iterate through the collection in one thread,
    // while another one modifies it by adding/removing elements).

    // This is where concurrent collections are used.

    // As iteration is often the base implementation of several bulk methods in collections, like addAll, removeAll, or also
    // collection copying (through a constructor, or other means), sorting, ... the use case for concurrent collections is
    // actually pretty large.

    // For example, the Java SE 5 java.util.concurrent.CopyOnWriteArrayList is a thread safe and concurrent List
    // implementation, its javadoc states:

    // The "snapshot" style iterator method uses a reference to the state of the array at the point that the
    // iterator was created. This array never changes during the lifetime of the iterator, so interference is
    // impossible and the iterator is guaranteed not to throw ConcurrentModificationException.

    // Therefore, the following code is safe:
    public class ThreadSafeAndConcurrent {

        public static final List<Integer> LIST = new CopyOnWriteArrayList<>();

        public static void main(String[] args) throws InterruptedException {
            Thread modifier = new Thread(new ModifierRunnable());
            Thread iterator = new Thread(new IteratorRunnable());
            modifier.start();
            iterator.start();
            modifier.join();
            iterator.join();
        }

        public static final class ModifierRunnable implements Runnable {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 50000; i++) {
                        LIST.add(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static final class IteratorRunnable implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10000; i++) {
                    long total = 0;
                    for(Integer inList : LIST) {
                        total += inList;
                    }
                    System.out.println(total);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Another concurrent collection regarding iteration is COncurrentLinkedQueue, which states:

    // Iterators are weakly consistent, returning elements reflecting the state of the queue at some point at or
    // since the creation of the iterator. They do not throw java.util.ConcurrentModificationException, and may
    // process concurrently with other operations. Elements contained in the queue since the creation of the
    // iterator will be returned exactly once.

    // One should check the javadocs to see if a collection is concurrent, or not. The attributes of the iterator returned by
    // the iterator() method ("fail fast", "weakly consistent", ...) is the most important attribute to look for.

    // Thread safe but non-concurrent examples

    // In the above code, changing the LIST declaration to
    public static final List<Integer> LIST = Collections.synchronizedList(new ArrayList<>());

    // Could (and statistically will on most modern, multi CPI/core architectures) lead to exceptions

    // Synchronized collections from the Collections utility methods are thread safe for addition/removal of elements,
    // but not iteration (unless the underlying collection being passed to it already is).

}
