Hello everyone, welcome to this lecture on the Foreign Function & Memory API—or FFM API—in Java! Today we’ll explore this powerful tool that bridges Java with native code and memory. Introduced through Project Panama and finalized in Java 22, the FFM API lets us call native functions and manage off-heap memory safely and efficiently. Let’s dive into why it matters and how it works!

-------------------
-------------------

Why do we need the FFM API? Java runs in a secure, managed environment—the JVM—which isolates it from native code. That’s great for safety but a hassle when we need to use fast C libraries or hardware-level features. Traditionally, we’ve used JNI—Java Native Interface—but it’s clunky, requires tons of boilerplate, and can crash if you mess up. The FFM API, finalized in Java 22 with JEP 454, fixes this. It simplifies calling native code, boosts safety and performance, and aims to replace JNI with a cleaner, modern approach.

-------------------
-------------------

The FFM API comes from Project Panama, a long-running effort to connect Java with the native world. It has two big pieces: the Foreign Function part lets us call native code—like C or C++ functions—directly from Java, and the Foreign Memory part lets us manage memory outside the JVM’s heap safely. It lives in the java.lang.foreign package. It started as an incubator in Java 17, went through preview stages in 19 to 21, and became a permanent feature in Java 22. As of today, it’s a stable, production-ready tool.

-------------------
-------------------

Let’s break down the FFM API’s key components. Linker is how we hook Java to native functions—it’s platform-specific and follows the local ABI. MemorySegment is a chunk of off-heap memory we can work with. Arena controls that memory’s lifecycle, making sure it’s cleaned up properly. FunctionDescriptor describes a native function’s signature—like its parameters and return type. MethodHandle is what we use to actually call that function from Java. And SymbolLookup finds the native code we want to use, like a function in a library. Together, these make the magic happen.

-------------------
-------------------

Here’s a simple example: calling the C strlen function, which counts a string’s length. We start by getting a Linker for native calls and a SymbolLookup to find strlen in the standard library. We grab its address, then create a MethodHandle with a FunctionDescriptor saying it takes a memory address and returns a long. Using an Arena, we allocate off-heap memory for 'Hello, Java!', call strlen via the handle, and print the result—12. No JNI mess—just clean Java code talking to C!

-------------------
-------------------

Memory management is a big deal with native code, and Arena handles it. There are three flavors: ofConfined for single-threaded use with manual closing, ofShared for multi-threaded, and ofAuto where the JVM decides when to clean up. In this snippet, we use ofConfined to allocate 100 bytes of off-heap memory, aligned to 8 bytes. When the try-with-resources block ends, the Arena frees it automatically. This prevents leaks or dangling pointers—way safer than raw JNI memory handling.

-------------------
-------------------

For something trickier, let’s sort with C’s qsort. We link to qsort, which takes an array, sizes, and a comparator function. Here, we define a Java compare method and turn it into an upcall stub—a pointer native code can call back into Java. We pass this to qsort along with our data. This shows both downcalls—Java to C—and upcalls—C to Java. It’s powerful stuff: native sorting with a Java comparator, all without JNI’s glue code.

-------------------
-------------------

Why pick FFM over JNI? Simplicity—no need for C boilerplate or header files; it’s all Java. Safety—it’s type-safe at compile time and manages memory to avoid crashes. Performance—it’s four to five times faster than JNI, thanks to tight JVM integration. Portability—the Linker adapts to any platform’s ABI. And it’s modern, using MethodHandle instead of JNI’s old tricks. It’s a game-changer for native integration.

-------------------
-------------------

Where does FFM shine? Game development—integrate with OpenGL or Vulkan for fast graphics. Machine learning—bind to TensorFlow or PyTorch for speed. Legacy systems—talk to old C code without rewriting it. High-performance apps—process big data off-heap. A pro tip: use the jextract tool to auto-generate Java bindings from C headers. It’s not in the JDK yet, but it saves tons of time.

-------------------
-------------------

That’s our wrap-up! The FFM API is Java’s modern bridge to the native world. Key takeaways: use Linker and MethodHandle to call native functions, and Arena with MemorySegment to manage memory. It’s safer, faster, and simpler than JNI. As of today, it’s fully baked in Java 22 and beyond—so try it out! Let's dive deeper with Project Panama for more goodies.

