"# Simple Distributed Systems application using sockets" 

<h2>Dependencies</h2>
<p>After downloading the project folder and opening it in eclipse you first need to add the JSoup.jar file that came with this package.To add the JSoup.jar file to the project follow these steps:
  
  Right-click on the Project → Build Path → Configure Build Path. Under Libraries tab, click Add Jars or Add External JARs and select the .jar file you want to add.
  
<img src = "https://i.stack.imgur.com/A6xgq.png" alt="Image of setting to add .jar file" height="300" width="500">
</p>

<h2>Instructions</h2>
<ul>
  <li>Modify line 212 in the Second_Screen.java file to represent your desktop path, change<br> 
    <code>Path outputPath = Paths.get("C:\\Users\\100583384\\Desktop\\Hint.html");</code><br>
    to your desktop path. To do so, you just need to replace 10058384 with your username. </li>
  <li>Modify line 75 in Client.java file<br>
    <code>Path outputPath = Paths.get("C:\\Users\\100583384\\Desktop\\outputQuestions.txt");</code><br>
    to your desktop path. To do so, you just need to replace 10058384 with your username.</li>
</ul>  
<p>
  After making the above changes you can now run the application. To run the application you first need to start the Server.java file and then you can run the Client_GUI.java which will start the client application. To start the Server.java file through CMD you first need to navigate to the directory of the Server.java file, then you can run the command<br>
  <code>java Server.java</code> . To start the Client_GUI you need to open a new terminal and navigate to the file then execute the command <code>java Client_GUI.java</code></p>
