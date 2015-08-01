import java.net.*;
import java.io.*;
public class FileResponseMessage extends ResponseMessage
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

		fileMimeType
			Holds the fileMimeType associated with the fileOnClient


	 Constructors:
	   public FileResponseMessage(File fileOnClient, FileMimeType fileMimeType)
		initializes all instance variables

	 Methods:

	  public void processRequest(Socket socket) throws IOException, ClassNotFoundException
	  	makes directory if the directory does not exist, creates a fileCopierInterface,
	  	and then begins the copy funnel to the client

	 Modification History:
	  October 30, 2013
	   Original Version
	*/

 private File 			fileOnClient;
 private FileMimeType 	fileMimeType;

 public FileResponseMessage(File fileOnClient, FileMimeType fileMimeType)
 {

  if(fileOnClient == null)
  {
   throw new IllegalArgumentException("fileOnClient is null");
   //create an error message
  }
  if(fileMimeType == null)
  {
   throw new IllegalArgumentException("fileMimeType is null");
  }

  this.fileOnClient = fileOnClient;
  this.fileMimeType = fileMimeType;
 }//end constructor

 public void processResponse(Socket socket)
 {
  FileCopierInterface 	fileCopierInterface;
  FileMimeTypes   		fileMimeTypes;
  FileMimeType  		fileMimeType;
  RequestMessage  		requestMessage;

  if( socket == null )
  {
   throw new IllegalArgumentException("Socket in FileResponseMessage.processFile() is equal to null");
  }

  try
  {
   if(this.fileOnClient.getParentFile() != null)
   {
    this.fileOnClient.getParentFile().mkdirs();
   }

   //set up connection for file copier objects
 	fileCopierInterface = FileUtility.getFileCopierObject(this.fileMimeType, socket.getInputStream(), new FileOutputStream(this.fileOnClient));

   //Do the copy
   fileCopierInterface.copyFile();


  }
  catch(Exception e){
   //throw new FileAlreadyExistsException(this.fileOnClient, "File already Exists");
   //RequestMessage requestMessage2 = new FileRequestMessage(null, null);
   //requestMessage2.processRequest(socket);
   throw new RuntimeException(e.getMessage());


   }
 }

}//end class