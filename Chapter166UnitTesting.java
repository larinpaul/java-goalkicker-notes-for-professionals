//// 2023/04/30 //

//// Chapter 166: Unit Testing

class Chapet166UnitTesting {

    // Unit testing is an intergral part of test-driven development, and an important feature for building any robust
    // application. In Java, Unit testing is almost exclusively performed using external libraries and frameworks, most of
    // which have their own documentation tag. This stub serves as a means of introducing the reader to the tools
    // abialable, and their respective documentation.

    //// Section 166.1: What is Unit Testing?

    // This is a bit of a primer. It's mostly put it in because documentation is forced to have an example, even if it's
    // intended as a stub article. If you already know unit-testing basics, feel free to skip forward to the remarks, where
    // specific frameworks are metnioned.

    // Unit testing is ensuring that a given module behaves as expected. In large-scale application, ensuring the
    // appropriate execution of mosules in a vacuum is an integral part of ensuring application fidelity.

    // Consoder the following (trivial) pseudo-example:
    class Example {
        public static void main (String args[]) {
            new Example();
        }

        // Application-lvel test.
        public Example() {
            Consumer c = new Consumer();
            System.out.printf("VALUE = " + c.getVal());
        }

        // Your Module.
        class Consumer (
                private Capitalizer c;

                public Consumer() {
                    c = new Capitalizer();
                }

                public String getVal() {
                    return c.getVal();
            }
        )

        // Another team's module.
        class Capitalizer {
            private DataReader dr;

            public Capitalizer() {
                dr = new DataReader();
            }

            public String getVal() {
                return dr.readVal().toUpperCase();
            }
        }

        // Another team's module.
        class DataReader {
            public String readVal() {
                // Refers to a file somewhere in your application deployment, or
                // perhaps retrieved over a deployment-specific network.
                File f;
                String s = "data";
                // ... Read data from f into s ...
                return s;
            }
        }
    }

    // So this example is trivial; DataReader gets the data from a file, passes it into the Capitalizer, which converts all the
    // characters to upper-case, which then gets passed to the Consumer. But the DataReader is heavily-linked to our
    // application environment, so we defer testing of this chain util we are ready to deploy a test release.

    // Now, assume, somewhere along the way in a release, for reasons unknown, the getVal() method in Capitalizer
    // changed from returning a toUpperCase() String to a toLowerCase() String:

    // Another team's module.
    class Capitalizer {
        ...

        public String getVal() {
            return dr.readVal().toLowerCase();
        }
    }

    // Clearly, this breaks expected behavior. But, because of the arduous processes involved with execution of the
    // DataReader, we won't notice this until our next test deployment. So days/weels/months go by with this bug sitting
    // in our system, and then the product manager sees this, and instantly turns to you, the team leader associated with
    // the Consumer. "Why is this happening? What did you gous change?" Obvriously, you're clueless. You have no idea
    // what's going on. You didn't change any code that should be touching this; why is it suddenly broken?

    // Eventually, after discussion between the teams and collaboration, the issue is traced, and the problem solved. But,
    // it begs the question; how could this have been prevented?

    // There are two obvious things:

    // Tests need to be automated

    // Our reliance upon manual testing let this bug go unnoticed fat yoo long. We need a way to atomate the process
    // under which bugs are introduced instantly. Not 5 weeks from now. Now 5 days from now. Not 5 minutes from now.
    // Right now.

    // You have to appreciate that, in this example, I've expressed one very trivial bug that was introduced and
    // unnoticed. In an industrial application, with dozens of modules constantly being updated, these can creep in all
    // over the place. You fix something with one module, only to realize that the very behavior you "fixed" was relied
    // upon in some manner elswehere (either internally or externally).

    // Without rigorous validation, things will creep into the system. It's possible that, if neglected far enough, this will
    // result in so much extra work trying to fix changes (and then fixing those fixes, etc.), that a product will actally
    // increase in remaining work as effort is put into it. You do not want to be in this situation.

    // Tests need to be fine-grained

    // The second problem noted in our above example is the amount of time it took to trace the bug. The product
    // managed pinged you when the testers noticed it, you investigated and found that the Capitalizer was returning
    // seemingly basd data, you pinged the Capitalizer team with your findings, they investigated, etc. etc. etc.

    // The same point I made above about the quality and difficulty of this trivial example hold here. Obviously anyone
    // reasonably well-versed with Java could find the introduced problem quickly. But it's often much, much more
    // difficult to trace and communicate issues. Maybe the Capitalizer team provided you a JAR with no source. Maybe
    // they're located on the other side of the world, and communication hours are very limited (perhaps to e-mail that
    // get sent once daily). It can result in bugs taking weeks or longer to trace (and, gain, there could be several of
    // these for a given release).

    // In otder to mitigate agaisnt this, we want rigorous testing on as fine a level as possible (you also want coarse-
    // grained testing to ensure modules interact property, but that's not our focal point here). We want to rigorously
    // specify how all outward-facinf functionality (at minimum) operated, and tests for that functionality.

    // Enter unit-testing

    // Imagine if we hada test, specifically ensuring that the getVal() method of Capitalizer returned a capitalized
    // String for a given input String. Furthermore, imagine that test was run before we even committed any code. The bug
    // introduced into the system (that is, toUpperCase() being replaced with toLowerCase()) would cause no issues
    // because the bug would never BE introduced into the system. We would atch it in a test, the developer would
    // (hopefully) realize their mistake, and an aletrnative solution would be reached as to how to introduce their intended
    // effect.

    // There's some omissions made here as to how to implement these tests, but those are covered in the framework-
    // specific documentation. Hopwefully, this serves as an example of why using testing is important.

}
