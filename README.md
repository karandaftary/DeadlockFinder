# Deadlock Finder
Identifies deadlocks in a given dumpfile

## Running this app
This is a command line app in java. To run it follow these steps: 
- Open Command Prompt (in Windows) or Terminal (in Mac/Linux) 
- Navigate to this folder (the folder containing files from this repo)
- Type `java -jar Start.jar`

The app will start executing test cases and printing out the response. For some test cases you may need to wait up to 10 seconds to ensure a deadlock is formed.

## Navigating code
- `Start.java` Entry point
- `Dump.java` Contains necessary methods to create a sample dump file with locks and threads
- `MyLib.java` Tiny library that contains the core element: findDeadLock() method.
- `MyLock.java` An implemenentation of a Lock that can be acquired or released by a thread
- `MyThread.java` Extends Java's basic thread class which can be run to perform deadlocks

