import java.io.*;
import java.net.*;

public class FileRequestMessage extends RequestMessage
{

/*
	 Lauren M DiGregorio
	 November 24, 2013


	 Implements the protocal for our server and client

	 Class Variables:
	  serialVersionUID
	   allows object to be serializable

	    fileOnClient
	    	holds the file that is stroed on the client

		fileOnServer
			Holds the file that is stored on the server


	 Constructors:
	    public FileRequestMessage(File fileOnServer, File fileOnClient)
			initializes all instance variables

	 Methods:

	  public void processRequest(Socket socket) throws IOException, ClassNotFoundException
	  	creates a FileMimeType map, gets the mimetype for the file on the server, creates a
	  	fileCopierInterface to correctly copy the file, estblishes a connection between client
	  	and server by creating a responseMessage, and finally begins the file copy method.

	 Modification History:
	  October 30, 2013
	   Original Version
	   */

 private File fileOnClient; // where we want to copy the file.
 private File fileOnServer; // exists on the server.
 private static final long serialVersionUID = 1L;

 public FileRequestMessage(File fileOnServer, File fileOnClient)
 {
  if(fileOnClient == null)
  {
   throw new IllegalArgumentException("Client file passed to [FileRequestMessage class] is null.");
  }

  if(fileOnServer == null)
  {
   throw new IllegalArgumentException("Server file passed to [FileRequestMessage class] is null.");
  }


  this.fileOnClient = fileOnClient;
  this.fileOnServer = fileOnServer;

 } //constructor

 public void processRequest(Socket socket)
 {
  //This class first checks the socket for null. Else,
  FileCopierInterface 	fileCopierInterface;
  FileMimeTypes   		fileMimeTypes;
  FileMimeType  		fileMimeType;
  ResponseMessage  		responseMessage;
  FileResponseMessage 	fileResponseMessage;

  if(socket == null)
  {
   throw new IllegalArgumentException("Socket passed to processRequest() method in [FileRequestMessage class] is null.");
  }

  try
  {

   fileMimeTypes = FileMimeTypes.newInstance();
   fileMimeType = fileMimeTypes.getFor(this.fileOnServer);

if(fileMimeType == null)
 {
  System.out.println(">>>> In FileRequestMessage.processRequest: No mime type for: " + this.fileOnServer+
                      "\nWill assume mime type is application/octet-stream. ");
 fileMimeType = new FileMimeType("application/octet-stream");
 fileMimeTypes.setFor(this.fileOnServer.getName(), fileMimeType);
}


   //here we get our file copier interface, thus input stream is is from the server (socket) and the output stream is client
   fileCopierInterface = FileUtility.getFileCopierObject(fileMimeType,
                                                         new FileInputStream(this.fileOnServer),
                                                         socket.getOutputStream());

   //set up connection to server
   fileResponseMessage = new FileResponseMessage(this.fileOnClient, fileMimeType);
   fileResponseMessage.sendMeTo(socket);

   //do copy
   fileCopierInterface.copyFile();

  }
  catch(Exception e){throw new RuntimeException("In FileRequestMessage: "+e.getMessage());  }


 } //processRequest() method

} //class