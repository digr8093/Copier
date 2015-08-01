import java.io.*;
import java.net.*;

public class DirectoryRequestMessage extends RequestMessage
{
	/*
	 Lauren M DiGregorio
	 November 24, 2013
	 

	 Implements the protocal for our server and client

	 Class Variables:
	  serialVersionUID
	   allows object to be serializable

	  directory
	   holds file object

	  fileFilter
	   holds a fileFilter object

	 Constructors:
	   public DirectoryRequestMessage()
		initializes all instance variables

	 Methods:

	  public void processRequest(Socket socket) throws IOException, ClassNotFoundException
	   this class will check to make sure socket is not equal to null and creates a directoryProcessor with the help
	   of the SendAFile class.  As directoryProcessor touches every single file in the directory, SendAFile will
	   create new DirectoryResponseMessage (the constructor takes a file) and sends the newly created object and sends it
	   to the socket

	 Modification History:
	  October 30, 2013
	   Original Version
	*/
 private static final long  serialVersionUID = 1L;
 private File     directory;
 private FileFilter    fileFilter;

 public DirectoryRequestMessage(File directory, FileFilter fileFilter) //Error checks passed object values and initialized class variables with those passed values.
 {

  if(fileFilter == null)  {throw new IllegalArgumentException("Passed FileFilter in [DirectoryRequestMessage] is null.");} //if
  if(directory == null)  {throw new IllegalArgumentException("Passed directory file in [DirectoryRequestMessage] is null.");} //if

  this.directory = directory;
  this.fileFilter =fileFilter;

 } //constructor

 public void processRequest(Socket socket) throws IOException, ClassNotFoundException
 {
  DirectoryProcessor  	directoryProcessor;
  DirectoryLister  		directoryList;
  ResponseMessage  		responseMessage;

  if(socket == null)
  {
   throw new IllegalArgumentException("Passed socket in processRequest() method in [DirectoryRequestMessage class] is null.");
  } //if

  try
  {
   //if we are in here we are not an error
   //process itself to see if any errors are raised
   //check directory for norm stuff (null, exists, is directory) create errorResponseMessage or not
   responseMessage = null;
   if(!this.directory.exists())
   {
    responseMessage = new ErrorResponse(new FileDoesNotExistException(this.directory, "File does not exist: " + this.directory ));
   }
   if(!this.directory.isDirectory())
   {
    responseMessage = new ErrorResponse (new FileDoesNotExistException(this.directory, "File is not a directory: " + this.directory));
   }
   if(responseMessage != null)
   {
    responseMessage.sendMeTo(socket);
   }
   else
   {
    responseMessage = new DirectoryResponseMessage();
    responseMessage.sendMeTo(socket);

   directoryProcessor = new DirectoryProcessor(this.directory, new SendAFile(this.fileFilter, socket));


   directoryProcessor.run();
   }

  }//end try
  catch(Exception e)
  {

   throw new RuntimeException(e.getMessage());
  }

 } //processRequest() method


private class SendAFile implements FileAction
{
 /*
  Lauren M DiGregorio
  November 24, 2013
  CISC 230
  Jarvis

  Sends a DirectoryRequestMessage to the Server

  Class Variables:
   serialVersionUID
    allows object to be serializable

   socket
    socket that the connection between client and server

   fileFilter
    holds a fileFilter object

  Constructors:
    public SendAFile()
     initializes all instance variables

  Methods:

   public void after()
    this is going to send null as a responseMessage to indicate the copying is done

   public void before()
    this method does not currently do anything

   public void processFile()
    this method sends DirectoryRequestMessage to the server

  Modification History:
   October 30, 2013
    Original Version
   */
 private FileFilter  fileFilter;
 private Socket   socket;

 public SendAFile(FileFilter fileFilter, Socket socket)
 {
  if(fileFilter == null ) 	{throw new IllegalArgumentException("fileFilter in Handler() is equal to null");}
  if(socket   	== null) 	{throw new IllegalArgumentException("socket in Handler() is equal to null");}

  this.fileFilter  = fileFilter;
  this.socket   = socket;

 } //constructor

 public void after()
 {

  try
  {
   	Message.writeObjectTo(socket,null);
  }
  catch(Exception e)
  {
   e.printStackTrace();
   System.out.println("DirectoryRequestMessage.after(): " + e.getMessage());
   throw new SystemGeneratedException(null, e);
   }
 } //after() method

 public void before()
 {

 } //before() method

 public void processFile(File file)
 {


  try
  {
   if(file.isFile() && file.exists() && fileFilter.accept(file))
   {
    Message.writeObjectTo(this.socket, file);

   }
  }//end try
  catch(Exception e)
  {
   e.printStackTrace();
   throw new RuntimeException("in SendAFile: " +e.getMessage());
  }//end catch

 } //processFile() method

} //SendAFile class

} //DirectoryRequestMessage class