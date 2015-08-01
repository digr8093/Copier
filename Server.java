import java.net.*;
import java.io.*;
public class Server implements Runnable
{
/*
 Lauren M DiGregorio
 November 24, 2013
 CISC 230
 Jarvis

 This class is the is the platform in which the client and server interact

 Class Variables:
  serverSocket
   The socket associated with the server

  portNumber
   int that hold the port number of the server socket

 Constructors:
   public Server(int portNumber)
    Initializes the port number that the client wil interact with

 Methods:

  public int getPortNumber()
   returns the port number the client and server are interacting through

  public void run()
   creates a new thread using a nested class called Handler.

  public boolean isServerRunning()
   returns boolean stating if the server is running or not

  private void setServerToNull()
   Sets the server to null

  public void stopServer()
   terminates the server

  public void writeLogMessage(String message)
   Writes message to DOS window to let user know what has been completed

 Modification History:
  October 20, 2013
   Original Version


 */


 private ServerSocket serverSocket; //is able to take inputStream and outputStreams, this is able to be wrapper in differ readers/writers
 private int portNumber;

 public Server(int portNumber)
 {
  if(portNumber < 0)
  {
   throw new IllegalArgumentException("Invalid port number. Attempted port number is " + portNumber);
  }

  this.portNumber = portNumber;
  this.setServerSocketToNull();
 }//end constructor

 synchronized private void setServerSocketToNull()
 {
  //use this as a flag. Helps tell us if the server is running or not
  this.serverSocket = null;

 }//end setServerSocketToNull

 synchronized public boolean isServerRunning()
 {
  return this.serverSocket != null;
 }//end serverIsRuning

 public int getPortNumber()
 {
  return this.portNumber;
 }//end getPortNumber

 public void run()
 {
  ServerSocket  server;//is on the server side and reads the client information
  Socket   client; //is on the client side. reads the server information
  server = null;
  try
  {
   //do this with local variables rather than instance variables so we shut down more gracefully

   this.serverSocket = new ServerSocket(this.getPortNumber());

   server = this.serverSocket;

   if(this.getPortNumber() == 0)
   {
    this.portNumber = server.getLocalPort();
   }
   this.writeLogMessage("Server has started on port " + this.getPortNumber());

   //loop until close

   while(this.isServerRunning())
   {
    //to tell if the server is listening for the client, we use accept()
    //accept is a blocking statement, wont move until someone contacts our port and IP

    client = server.accept(); //models a connection

    if(this.isServerRunning())
    {
     new Thread( new Handler( client ) ).start(); //opening a stream to our writeMessage in our nested class handler //start or run?
    }//if
   }//end while
   writeLogMessage("Server has stopped normally");
  }//end try
  catch(IOException e)
  {
   System.out.println("Error occuring because of server " + e.getMessage());
  }

  finally
  {
   //finally will always run
   //we will use this to do our "clean up" when shutting down the server
   try
   {
    this.serverSocket.close();
   }
   catch(Exception e)
   {
    System.out.println("Error occuring because server was unsuccessfully closed");
   }

   this.setServerSocketToNull();
  }
 }//end run()

 synchronized public void stopServer() throws IOException
 {
  //ping the server to wake it up
  InetAddress inetAddress;

  //capture inet address
  inetAddress = this.serverSocket.getInetAddress();

  //stop server by setting server to null, set flag
  this.setServerSocketToNull();

  //ping server, touches the server, will say the server is not running because of our previous statement, tests the flag
  new Socket(inetAddress, this.getPortNumber());

 }//end stopServer()

 public void writeLogMessage(String message)
 {
  //Used to write out messages to user and error check.
  System.out.println(message);
 }

 private class Handler implements Runnable
 {
 /*
  Lauren M DiGregorio
  November 24, 2013
  CISC 230
  Jarvis

  Class reads the inputStream from the client, casts what is gets
  as a message object, and then the message processes itself

  Class Variables:
   socket
    The socket associated with the server and client

  Constructors:
    public Handler(Socket socket)
     Initializes the socket for communication between client and server
  Methods:

   public void run()
    begins server and client protocal for communication.  Creates a
    RequestMessage that will be read by the server.


  Modification History:
   October 20, 2013
    Original Version

  */

  private Socket socket;

  //this is to help identify the error messages
  public Handler(Socket client)
  {

   if(client == null)
   { throw new IllegalArgumentException("Client variable passed in constructor of Handler class is null."); }

   this.socket = client;
   //run();

  }//end constructor

  public void run()
  {
   RequestMessage requestMessage;
   try
   {
    //sends request message to server
    requestMessage = (RequestMessage)Message.readMessageFrom(this.socket);
    requestMessage.processRequest(this.socket);
    //Server.this.writeLogMessage( "I got this message: " + requestMessage + " from " + socket );
   }
   catch(Exception e)
   {
    //if we end up here, one reason may be the thing sent did not follow correct protocal
    Server.this.writeLogMessage("Handler.run() caught exception " + e.getMessage());
    e.printStackTrace();
   }
   finally
   {
    //want to clean up if it didnt close propery

      try {this.socket.getInputStream().close();  } catch (Exception e){}
      try {this.socket.getOutputStream().close(); } catch (Exception e){}
      try {this.socket.close();                   } catch (Exception e){}

   }//end finally
  }//end run()



 }//end nested class
}//end class
