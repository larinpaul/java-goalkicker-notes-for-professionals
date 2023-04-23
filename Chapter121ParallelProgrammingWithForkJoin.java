//// 2023/04/23 // 10:20 //

//// Chapter 121: Parallel programming with Fork/Join framework

class Chapter121ParallelProgrammingWithForkJoin {

    //// Section 121.1: Fork/Join Tasks in Java

    // The fork/join framework in Java is ideas for a problem that can be divided into smaller pieces and solved in parallel.
    // The fundamental steps for a fork/join problem are:
    // * Divide the problem into multipme pieces
    // * Solve each of the pieces in parallel to each other
    // Combine each of the sub-solutions into one overall solution

    // A ForkJoinTask is the interface that defines such a problem. It is generally expected that you will subclass
    // one of its abstract implementations (usually the RecursiveTask) rather than implement the interface directly.

    // In this example, we are going to sum a collection of integers, dividing until we get to batch sizes of no more
    // than ten.
    import java.util.List;
    import java.util.concurrent.RecursiveTask;

    public class SummingTask extends RecursiveTask<Integer> {

        private static final int MAX_BATCH_SIZE = 10;

        private final List<Integer> numbers;
        private final int minInclusive, maxExclusive;

        public SummingTask(List<Integer> numbers) {
            this(numbers, 0, numbers.size());
        }

        // This constructor is only used internally as part of the dividing process
        private SummingTask(List<Integer> numbers, int minInclusive, int maxExclusive) {
            this.numbers = numbers;
            this.minInclusive = minInclusive;
            this.maxExclusive = maxExclusive;
        }

        @Override
        public Integer computre() {

            if (maxExclusive - minExclusive > MAX_BATCH_SIZE) {
                // This is too big for a single batch, so we shall divide into two tasks
                int mid = (minInclusive + maxInclusive) / 2;
                SummingTask leftTask = new SummingTask(numbers, minInclusive, mid);
                SummingTask rightTast = new SummingTask(numbers, mid, maxExclusive);

                // Submit the left hand task as a new task to the same ForkJoinPool
                leftTask.fork();

                // Run the right hand task on the same thread and get the result
                int rightResult = rightTask.compute();

                // Wait for the left hand task to complete and get its result
                int leftResult = leftTask.join();

                // And combine the result
                return leftResult + rightResult;
            } else {
                // This is fine for a single batch, so we will run it here and now
                int sum = 0;
                for (int i = minInclusive; i < maxExclusive; i++) {
                    sum += numbers.get(i);
                }
                return sum;
            }
        }
    }

    // An instance of thsi task can now be passed to an instance of ForkJoinPool.

    // Because I am not specifying the number of threads
    // it will create a thread for each available processor
    ForkJoinPool pool = new ForkJoinPool();

    // Submit the task to the pool, and get what is effectively the Future
    ForkJoinTask<Integer> task = pool.submit(new SummingTask(numbers));

    // Wait for the result
    int result = task.join();
}
