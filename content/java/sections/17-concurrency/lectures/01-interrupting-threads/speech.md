Today, we’ll explore how to interrupt threads in Java—a critical technique for managing thread execution safely and effectively. Whether you’re stopping a long-running task or cleaning up resources, understanding interruption is essential. Let’s dive in!

-------------------
-------------------

So, what is thread interruption? It’s a cooperative way to ask a thread to stop what it’s doing or take some action. Unlike forcefully killing a thread, which can leave things in a messy state, interruption relies on the thread itself to respond. The main method here is Thread.interrupt(), which sets a flag called the 'interrupted' status. It’s up to the thread to check this flag and decide what to do—think of it as politely tapping a worker on the shoulder rather than yanking them away.

-------------------
-------------------

Here’s how interruption works in practice. When you call interrupt() on a thread, it flips the interrupted flag to true. The thread can then check this flag in two ways: isInterrupted() checks it without resetting it, while interrupted()—a static method—checks and clears it. There’s a special case too: if a thread is blocked—like in sleep() or wait()—calling interrupt() will throw an InterruptedException, waking it up. This gives us a way to break out of blocking calls cleanly.

-------------------
-------------------

Let’s look at a simple example. Here, we have a thread that loops and prints 'Working...' as long as it’s not interrupted. In the run method, it checks isInterrupted() to decide when to stop. In main, we start the thread, let it run for 2 seconds with sleep(), and then call interrupt(). When that happens, the flag is set, the loop exits, and the thread prints a shutdown message. This shows the basic pattern: check the flag and respond accordingly.

-------------------
-------------------

Now, what about when a thread is blocked? Methods like sleep() or wait() throw InterruptedException if interrupted. Here’s an example: our thread prints 'Sleeping...' every second using sleep(). If it’s interrupted, the sleep() call throws an exception, and we catch it to print a message. Notice something important: catching the exception clears the interrupted flag, so we call interrupt() again to restore it. This ensures the thread knows it was interrupted, even after the exception.

-------------------
-------------------

Why bother restoring the interrupted status? When InterruptedException is thrown, it resets the flag to false, which could hide the fact that an interruption occurred. By calling Thread.currentThread().interrupt() in the catch block, we set it back to true. This is a best practice because it preserves the original intent—like passing a memo up the chain. It ensures that any code managing this thread later on can still detect the interruption and act accordingly.

-------------------
-------------------

Let’s see interruption in a practical scenario: stopping a worker thread gracefully. Here, we define a Runnable task that does work in a loop, checking isInterrupted() to know when to stop. We create a thread with this task, start it, and then call interrupt() to signal it to shut down. The thread exits the loop and prints a message. This pattern is common for background tasks—like a file processor or a server listener—where you need a clean way to stop without abrupt termination.

-------------------
-------------------

There are some pitfalls to watch out for. First, ignoring interruption—threads that never check the flag or handle exceptions won’t respond. Second, overusing interrupt()—it’s not a one-size-fits-all solution; use it when it makes sense. Third, misunderstanding how blocking works—if you don’t catch InterruptedException right, you might miss the signal. The tip here is to always design your threads with interruption in mind, making them responsive and robust.

-------------------
-------------------

That wraps up our lecture! Thread interruption is a polite, cooperative way to manage threads in Java. To recap: use interrupt() to signal a thread, check it with isInterrupted(), and handle InterruptedException carefully, restoring the status when needed. Make sure your threads are designed to cooperate with this mechanism. For next steps, look into thread pools—ExecutorService has methods like shutdownNow() that build on these ideas. Thanks for your attention
