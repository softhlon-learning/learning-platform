Hello again, and welcome to lecture on inner classes! These are the non-static siblings of nested classes, and they’re all about close relationships with their outer class. Unlike static nested classes, inner classes need an outer instance to exist, which opens up some unique possibilities. Today, we’ll see how they work and why they’re so useful in specific scenarios. Let’s jump right in!

-------------------
-------------------

An inner class is a nested class without the static keyword. That means it’s tied to an instance of the outer class—you can’t create it without one. This dependency is what makes inner classes special and powerful. They’re like loyal companions that always stick close to their parent object. It’s a design choice that suits certain tight-knit relationships in code.

-------------------
-------------------

Inner classes come with some neat features. They can access all members of the outer class—even the private ones—because they’re part of the family. They also hold an implicit reference to the outer instance, which is how they stay connected. This makes them super flexible for tasks that need deep integration. It’s like having a backstage pass to the outer class!

-------------------
-------------------

Here’s the syntax for an inner class. You define it inside the outer class without static, like this InnerClass inside OuterClass. It can use the outer class’s fields, like data, directly. In this example, calling display() shows the private data value, proving that access. It’s a simple setup with big potential!

-------------------
-------------------

To create an inner class object, you need an outer class instance first. You instantiate it with this outer.new InnerClass() syntax—notice the dot-new combo. Then you can call its methods as usual, like display() here, which prints 'Data: 10'. It’s a two-step process that reinforces their connection. This is different from the standalone static nested classes we saw last time!

-------------------
-------------------

Let’s look at a real-life example: a smartphone system. We have a Smartphone class with an inner Battery class. A battery’s state—like its charge level—is tied to a specific phone, not just any phone. By nesting Battery inside Smartphone, we reflect that dependency and let it access the phone’s details directly. It’s a perfect fit for modeling hardware components!

-------------------
-------------------

When should you use inner classes? They’re ideal for tightly related objects, like parts of a whole, or for helper classes in event handling—like button listeners in GUIs. They also shine when you need encapsulated logic that’s specific to an instance. Their access to private members makes them powerful in these cases. It’s all about context and connection!

-------------------
-------------------

That’s our look at inner classes! In the next lecture, we’ll explore local classes, which take nesting to another level by living inside methods. They’re super specific and temporary, which makes them intriguing. I hope you’re enjoying how nested classes unfold in Java.
