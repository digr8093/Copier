import java.net.*;
import java.io.*;
import java.lang.*;

public class Message implements Serializable
{
 /*
 Lauren M DiGregorio
 November 24, 2013
 CISC 230
 Jarvis

 Implements the protocal for our server and client

 Class Variables:
   none

 Constructors:
   public Message()
    default
 Methods:

  public static Message readMessageFrom(Socket socket) throws IOException, ClassNotFoundException
   reads object from socket and wraps it as a Message and then returns it.

  public static Object readObjectFrom(Socket socket) throws IOException, ClassNotFoundException
   creates a new InputStream from the passed socket and reads the object it's passing.

  public static void writeObjectTo(Socket socket, Object object) throws IOException, ClassNotFoundException
   creates a new OutputStream from the passed socket and writes to it the passed object.

  public void sendMeTo(Socket socket) throws IOException, ClassNotFoundException
   calls to the writeObjectTo method with passed socket and the readObject of that socket.

 Modification History:
  October 30, 2013
   Original Version

  */
 public Message()
 {

 } //constructor

 public static Message readMessageFrom(Socket socket) throws IOException, ClassNotFoundException
 {

  Message message;
  //System.out.println("readMessageFrom() method in [Message class] started.");

  if(socket == null)
  {
   throw new IllegalArgumentException("Illegal argument passed in readMessageFrom() method in Message class.");
  } //if

  message = (Message) readObjectFrom(socket);

  return message;
 } //readMessageFrom() method

 public static Object readObjectFrom(Socket socket) throws IOException, ClassNotFoundException
 {


  if(socket == null)
  {
   throw new IllegalArgumentException("Illegal argument passed in readObjectFrom() method in Message class.");
  } //if

  return new ObjectInputStream(socket.getInputStream()).readObject();
 } //readObjectFrom() method

 public static void writeObjectTo(Socket socket, Object object) throws IOException, ClassNotFoundException
 {

      new ObjectOutputStream(socket.getOutputStream()).writeObject(object);

 } //writeObjectTo() method

 public void sendMeTo(Socket socket) throws IOException, ClassNotFoundException
 {

  if(socket == null)
  {
   throw new IllegalArgumentException("Illegal argument passed in sendMeTo() method in Message class.");
  } //if

  writeObjectTo(socket, this);

 } //sendMeTo() method

} //class