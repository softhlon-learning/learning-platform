Alright, everyone, welcome to our live coding session on Java nested classes! Today, we’re building a Library Management System to show all four types of nested classes in action. We’ll manage books, categories, borrowing records, and sorting—all live. I’m starting with a blank file in my IDE, and we’ll code this step-by-step. Let’s create a new class called Library and get rolling!

-------------------
-------------------

First, I’m setting up our Library class—it’s the outer class that’ll hold everything. I’ll add two fields: name for the library’s name and a List called books to store our book objects. I need to import ArrayList and List from java.util—watch me type those imports. Then, I’ll add a constructor to initialize the name and create an empty list. This is our foundation, and all nested classes will live inside this!

-------------------
-------------------

Now, let’s add our first nested class: a static nested class called BookCategory. I’m typing public static class BookCategory inside Library. This represents book genres like Fiction or Science, and it’s static because categories don’t depend on a specific library instance—they’re universal. I’ll add a categoryName field and a static totalCategories counter to track how many we create. Watch me add a constructor and some getters—see how totalCategories increments? This shows static behavior since it’s shared across all instances

-------------------
-------------------

Next up is an inner class, Book, so I’ll type public class Book inside Library. This isn’t static because each book belongs to a specific library instance. I’m adding fields for title, category (using our BookCategory), and an isBorrowed flag. The constructor sets these up, and I’ll add a borrowBook method—notice how I use Library.this.name to access the outer library’s name. This shows the inner class’s tight connection to its outer instance. I’ll also throw in a getTitle getter for later.

-------------------
-------------------

Let’s make it easy to add books. I’m adding an addBook method to Library. It takes a title and category, creates a new Book object—see how I just call new Book() since we’re inside Library?—and adds it to our books list. This is how we’ll populate our library. It’s simple, but it ties our inner class into the system nicely

-------------------
-------------------

Now for a local class—something defined inside a method. I’m creating a recordBorrow method in Library. Inside it, I’ll type class BorrowRecord—this tracks who borrowed what, but it’s only needed here, so it’s local. It has fields for the borrower, book, and library name, which I grab with Library.this.name. I’ll add a constructor and a printRecord method, then instantiate and use it right away. This keeps borrowing logic contained and temporary

-------------------
-------------------

For our final nested type, let’s add an anonymous class to sort books by title. I’ll create a sortBooksByTitle method and use Collections.sort. I need to import Collections and Comparator—watch me add those up top. Now, I’ll pass an anonymous Comparator with new Comparator<Book>() and override compare to sort by title. It’s nameless and inline, perfect for this one-off task. Then I’ll print the sorted list—simple but powerful!

-------------------
-------------------

Let’s test everything with a main method. I’ll create a Library instance called 'City Library'. Then, I’ll make two BookCategory objects—Fiction and Science—using the static nested class syntax. I’ll add three books with addBook, borrow the first one, record it with our local class, and sort them with the anonymous class. Finally, I’ll print the total categories—should be 2. Let’s run it and see the output!

-------------------
-------------------

Here’s the output—everything works! We see the book borrowed, the record printed, the sorted list, and two categories. We’ve covered static nested classes with BookCategory, inner classes with Book, local classes in recordBorrow, and an anonymous class for sorting. This system ties them all together in a practical way. Any questions? That’s our session—hope you enjoyed it!

