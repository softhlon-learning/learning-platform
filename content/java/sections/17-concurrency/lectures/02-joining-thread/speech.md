In this lecture, we’ll explore how to use the join() method in Java to coordinate threads. This is a key tool for making sure one thread waits for another to finish, and by the end, you’ll know how to apply it in your programs. Let’s get started!

-------------------
-------------------

So, what does 'joining' mean in Java? The join() method makes one thread wait until another thread finishes its work. It’s a synchronization tool that helps coordinate threads when you need things to happen in a specific order. For example, you might want to wait for a data-processing thread to complete before displaying the results. It’s a method built into the Thread class, and it’s super useful for managing dependencies between threads.

-------------------
-------------------

Here’s how join() works. You call it on a thread object—like thread.join()—and the thread that calls it will pause until the target thread is done. For instance, if the main thread calls join() on a worker thread, main waits until the worker finishes. There are also timed versions: join(long millis) waits for a set time, and there’s an even more precise version with nanoseconds. One thing to note: if the waiting thread is interrupted, it throws an InterruptedException, so you’ll need to handle that

-------------------
-------------------

Let’s look at a basic example. We have a Worker thread that simulates some work by sleeping for 2 seconds. In main, we start the worker and then call join() on it. This means main will wait until the worker finishes before printing its message. When you run this, you’ll see 'Worker starting...', a 2-second pause, then 'Worker finished.' and 'Main continues.' in that order. Without join(), main might finish first, but join() enforces the sequence we want.

-------------------
-------------------

Now, what if you don’t want to wait forever? That’s where timed join() comes in. In this example, we use join(1000) to wait up to 1 second. The worker still takes 2 seconds to finish, so after 1 second, main moves on and prints its message, even though the worker isn’t done yet. The output shows 'Worker starting...', then 'Main done waiting.' after 1 second, and finally 'Worker finished.' after 2. This is handy when you want a timeout instead of an indefinite wait.

-------------------
-------------------

What about multiple threads? You can call join() on each one. Here, we have two workers, 'Task 1' and 'Task 2'. We start both, then call join() on each. Main won’t print 'All tasks complete.' until both threads are done. They might finish at different times, but join() ensures main waits for both. This is great for scenarios like waiting for multiple downloads or calculations to complete before moving on.

-------------------
-------------------

Since join() blocks, it can be interrupted, just like sleep(). If that happens, it throws an InterruptedException. In this snippet, we wrap join() in a try-catch block. If main is interrupted while waiting, it prints a message and restores the interrupted status with Thread.currentThread().interrupt(). This is a best practice we’ve seen before—it keeps the interruption signal intact for later handling (as we've learnt in previous lesson). It’s rare, but good to plan for.

-------------------
-------------------

So, where do you use join()? One case is waiting for initialization—maybe a setup thread loads resources before the app starts. Another is aggregating results, like waiting for multiple threads to compute parts of a problem. It’s also useful for cleanup, ensuring worker threads finish before the program exits. The tip here: use join() when the order of operations matters—it’s all about controlling the flow.

-------------------
-------------------

There are a few pitfalls to watch for. One is deadlocks—if two threads call join() on each other, they’ll wait forever, stuck. Another is overusing join()—not every thread needs to be waited on; sometimes they can run independently. Also, don’t ignore timeouts—use the timed version if a thread might hang. The tip: test your thread coordination thoroughly to catch these issues early.

-------------------
-------------------

That’s it for our lecture! The join() method is a simple yet powerful way to synchronize threads by waiting for them to finish. Key takeaways: use it for tasks where order matters, handle timeouts and interruptions properly, and coordinate multiple threads with ease. Thanks for listening.
