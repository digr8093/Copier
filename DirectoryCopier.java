import java.io.*;
import java.net.*;

public class DirectoryCopier implements Runnable
{
 /*

    December 4, 2013

    This class will make a copy of a directory
    CISC 230 project
 */

 private FileFilter            fileFilter;
 private InetSocketAddress     serverAddress;
 private File                  sourceDirectory;
 private File                  targetDirectory;


 public DirectoryCopier ( File sourceDirectory, File targetDirectory, InetSocketAddress serverAddress, FileFilter fileFilter) throws IOException
 {
  // There is only so much validity checking that can be done.
  // Since the sourceDirectory is on the remote machine, it is not possible to check it for other than null.
  // If the server and client are the same machine and the sourceDirectory contains the targetDirectory
  // bad things will happen.

  if(sourceDirectory == null)         { throw new IllegalArgumentException("Source directory parameter is null."); }
  if(targetDirectory == null)         { throw new IllegalArgumentException("Target directory parameter is null."); }
  if(serverAddress   == null)         { throw new IllegalArgumentException("InetSocketAddress parameter is null."); }
  if(fileFilter      == null)         { throw new IllegalArgumentException("FileFilter parameter is null."); }

  this.fileFilter         = fileFilter;
  this.serverAddress      = serverAddress;
  this.sourceDirectory    = sourceDirectory;
  this.targetDirectory    = targetDirectory;
 }


 public void run()
 {
  DirectoryResponseMessage directoryResponseMessage;
  File                     fileToBeCopiedFromServer;
  File                     localFile;
  RequestMessage           requestMessage;
  ResponseMessage          responseMessage;
  Socket                   server;
  Socket                   socket;

  server = null;
  try
  {
print("Step 0 is next");
   // Step 0: open a Socket connection to the server
   server = new Socket(this.serverAddress.getAddress(), this.serverAddress.getPort());

print("Step 1 is next");
   // Step 1: send DirectoryRequest message to server
   new DirectoryRequestMessage ( this.sourceDirectory, this.fileFilter ).sendMeTo(server);
print("Step 2 is next");
   // Step 2: get the response from the server: could be ErrorResponseMessage or a DirectoryResponseMessage
   responseMessage = (ResponseMessage) Message.readMessageFrom(server);

print("Step 3 is next");
   // Step 3: have the response message process itself. this will cause an exception to be
   //         raised if the message is an ErrorResponsMessage
   responseMessage.processResponse(server);

print("Step 4 is next");
   // Step 4: if we are here, the message wasn't an ErrorResponseMessage. If it wasn't an ErrorResponseMessage,
   //         then the call to processResponse has read the first File object from the server to be copied
   directoryResponseMessage = ( DirectoryResponseMessage ) responseMessage;
   fileToBeCopiedFromServer = directoryResponseMessage.getFile();


print("Step 5 loop is next");
   // Step 5: Loop until the File object sent back from the server is null
   while(fileToBeCopiedFromServer != null)
   {
print("Step 6 in loop: "+fileToBeCopiedFromServer);
    //  use the file name of this file to help create the local file (where it will be copied)
    localFile = createLocalFileName(fileToBeCopiedFromServer);

    //  create a file copy request for this file
    requestMessage = new FileRequestMessage( fileToBeCopiedFromServer, localFile );

    //  send the request to the server
    socket = new Socket(this.serverAddress.getAddress(), this.serverAddress.getPort());
    requestMessage.sendMeTo( socket );

    //  read the server's response to this request
    responseMessage = (ResponseMessage) Message.readMessageFrom(socket);

    try
    {
     //  call the response message's process method. if it is an error, it will
     //  throw the exception otherwise it will do the copy
print("       in loop processing: " + fileToBeCopiedFromServer + " to " + localFile);
     responseMessage.processResponse( socket );
    }
    catch(FileCopierException fce)
    {
     // Something bad happened while trying to copy the file.
     System.out.println("Exception thrown while copying from: " + fce.getFile() + " " + fce.getMessage());
    }
    //  get the next file from the server
    directoryResponseMessage.processResponse(server);
    fileToBeCopiedFromServer = directoryResponseMessage.getFile();
print("Step 6a at end of  loop: "+fileToBeCopiedFromServer);
   }

print("Step 7 loop is finished");
  }
  catch (Exception e) {e.printStackTrace(); throw new RuntimeException("In DirectoryCopier.run(); " + e.getMessage() );   }
  finally
  {
   try { server.getInputStream().close();  } catch(Exception e) {}
   try { server.getOutputStream().close(); } catch(Exception e) {}
   try { server.close();                   } catch(Exception e) {}
print("Step 8 DirectoryCreator.run() has ended.");
}

 }  // run

 private File createLocalFileName(File theSourceFile)
 {
  int    loc;
  String name;
  String sourceFile;
  String sourceDirectory;

  sourceDirectory = this.sourceDirectory.toString();
  sourceFile      = theSourceFile.toString();

  //  what about case sensitivity?
  loc  = sourceFile.indexOf(sourceDirectory);
  name = sourceFile.substring(loc+sourceDirectory.length());
  return new File(this.targetDirectory, name);
 }
 private void print(String message) { System.out.println(message);  }

}