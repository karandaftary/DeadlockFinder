import java.util.Arrays;

public class Start {

    public static void main(String[] args) throws InterruptedException {

        System.err.println("\nFinding Deadlocks in a Dump file"); 
        System.err.println("Printing out sample tests instead of unit tests to keep the demonstration simple, yet cover the basics.");

        runAllTests();

        // exit out of this app to avoid hogging up resources after demonstration
        System.exit(0);
    }

    private static void runAllTests() {
        // SETUP
        boolean cyclic = true;
        int defaultNumLocks = 4;
        int defaultNumThreads = 4;
        

        /* EXECUTE TEST */
        // deadlock scneario: cyclic threads 
        testFindingDeadLock("CYCLIC THREADS", defaultNumLocks, defaultNumThreads, cyclic);
        // one thread
        testFindingDeadLock("1 THREAD", defaultNumLocks, 1, cyclic);
        // simple case: 2 threads, and 2 locks
        testFindingDeadLock("2 LOCKS 2 THREADS", 2, 2, cyclic);
        // acyclic threads
        testFindingDeadLock("ACYCLIC THREADS", defaultNumLocks, defaultNumThreads, !cyclic);
    }

    private static void testFindingDeadLock(String testCaseTitle, int numLocks, int numThreads, boolean cyclic) {
        // setup
        System.err.println("\nTesting: " + testCaseTitle);
        Dump sampleFile = new Dump(numLocks, numThreads, cyclic);
        sampleFile.startThreads();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // act
        MyThread[] affectedThreads = MyLib.findDeadLock(sampleFile);

        // report
        System.err.println("Threads involved in deadlock " + Arrays.toString(affectedThreads));        
    }

}