Now, we’ll explore, in more details, how to use the URL class in Java to work with web resources. URLs are the backbone of internet communication, and Java makes it easy to create and use them for networking tasks. Let’s get started!"

-------------------
-------------------

First, what’s a URL? It stands for Uniform Resource Locator—it’s an address that points to a resource on the internet, like 'http://example.com'. A URL has parts: the protocol, like 'http' or 'https', the host—think 'example.com'—an optional port, a path to a specific resource, and sometimes a query string with parameters. In Java, the URL class in java.net handles all of this, giving us a simple way to work with these addresses.

-------------------
-------------------

Why use URLs in Java? They simplify web-based networking by providing a high-level abstraction. Instead of dealing with raw sockets and protocols yourself, URL does the heavy lifting. You can use it to fetch web pages, download files, or call APIs—like getting weather data. It’s a clean shortcut that skips the low-level connection details, making your code easier to write and maintain.

-------------------
-------------------

Let’s see how to create a URL object. The simplest way is with a single string—like new URL("http://example.com"). Just import java.net and you’re set. You can also build it piece by piece: specify the protocol, host, port, and path—like this example with 'https' and port 443. One catch: if the URL is malformed—say, you typo the protocol—it throws a MalformedURLException, so you’ll need to handle that.

-------------------
-------------------

Once you have a URL object, you can break it down with handy methods. getProtocol() gives you 'http', getHost() returns 'example.com', getPort() gets the port—or -1 if it’s not set—getPath() grabs the path, and getQuery() pulls any query parameters. Here’s an example: we create a URL with a port and query, then print the host. This is great for inspecting or manipulating URLs in your code.

-------------------
-------------------

Now, let’s fetch data. The openStream() method opens a connection to the URL and returns an InputStream. In this example, we wrap it in a BufferedReader to read a webpage line by line—like the HTML from 'example.com'. We loop through the lines, print them, and close the stream when done. This is a basic way to grab web content, like a mini browser, and it’s all built into the URL class.

-------------------
-------------------

For more control, use URLConnection. You get it with url.openConnection(), then access the input stream like before. Here’s a snippet: same idea as openStream(), but now you can do more—like set request headers, handle POST requests, or check response codes. It’s still simple but gives you extra knobs to tweak, especially for APIs or custom web interactions.

-------------------
-------------------

Networking means exceptions, so let’s handle them. MalformedURLException pops up if the URL’s wrong—like a missing colon. IOException covers network issues, like no connection or a 404 error. Here’s how to wrap your code: a try-catch block catches both, printing helpful messages. This keeps your program from crashing and lets you debug what went wrong.

-------------------
-------------------

Here’s a practical example: downloading a file. We create a URL pointing to 'file.txt', then use openStream() to get the data. We write it to a local file with FileOutputStream, reading chunks into a buffer until there’s nothing left. The try-with-resources ensures streams close automatically, and we catch IOException for errors. Run this, and you’ve got a file downloader—simple and effective!

-------------------
-------------------

That’s it for our lecture! The URL class simplifies web networking in Java. Key takeaways: create URLs with a string or parts, fetch data using openStream() or URLConnection, and always handle exceptions.

