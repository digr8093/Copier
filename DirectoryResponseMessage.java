import java.io.*;
import java.net.*;

public class DirectoryResponseMessage extends ResponseMessage
{
 /*
  Lauren M DiGregorio
  November 24, 2013


  Implements the protocal for our server and client

  Class Variables:
   serialVersionUID
    allows object to be serializable

   file
    holds file object

  Constructors:
    public DirectoryResponseMessage()
     initializes all instance variables

  Methods:

   public void processRequest(Socket socket) throws IOException, ClassNotFoundException
    this method holds the file being processed from the directoryRequestMessage. Is created in the SendAFile in the
    DirectoryRequestMessage

   public File getFile()
    returns file object held in the instance variable

  Modification History:
   October 30, 2013
    Original Version
*/
 private static final long serialVersionUID = 1L;
 private File file;

 public DirectoryResponseMessage() //asks for next file
 {
 } //constructor

 public void processResponse(Socket socket)
 {
DirectoryResponseMessage message;
  if(socket == null)
  {
   throw new IllegalArgumentException("Socket passed to processResponse() method in [DirectoryResponseMessage class] is null.");

  } //if
try
{
 this.file = (File)this.readObjectFrom(socket);
}
catch(Exception e) { throw new SystemGeneratedException(file, e); }


 } //processResponse() method

 public File getFile()
 {
  return this.file;
 } //getFile() method

} //class