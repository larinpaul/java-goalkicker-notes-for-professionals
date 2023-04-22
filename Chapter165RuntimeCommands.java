//// 2023/04/22 // 10:21 //

//// Section 165.1: Adding shutdown hooks

// Sometimes you need a piece of code to exectue when the program stops, such as releasing system resources that
// you open. You can make a thread run when the program stops with the addShutdownHook method:

class Chapter165RuntimeCommands {

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        ImportantStuff.someImportantIOStream.close();
    }));

}
