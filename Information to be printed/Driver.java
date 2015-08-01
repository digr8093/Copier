import java.io.*;
import java.net.*;

public class Driver
{
 /*
    Lauren DiGregorio
    December 4, 2013
    CISC 230
  	Jarvis

    This class helps test the copying of directories into another location
 */

 public static void main (String[] args) throws IOException
 {
  DirectoryCopier   directoryCopier;
  FileFilter        fileFilter;
  InetSocketAddress serverInetSocketAddress;
  Server            server;
  File              sourceDirectory;
  File              targetDirectory;

  // initialize stuff
  fileFilter              = new AllFilesFilter();

  fileFilter              = new OmitClassFilesFilter();

  serverInetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 12345);
  sourceDirectory         = new File("G:\\_St thomas\\CISC 230\\week 12.2\\");
  targetDirectory         = new File("G:\\_St thomas\\Sample\\");

  //  create and thread off the server
  server = new Server(serverInetSocketAddress.getPort());
  new Thread(server).start();

  // wait until the server starts running
  while(!server.isServerRunning())
  {
   try
   {
    Thread.sleep(100);
   }
   catch(Exception e) {}
  } //while

  // create the DirectoryCopier object
  directoryCopier = new DirectoryCopier(sourceDirectory, targetDirectory, serverInetSocketAddress, fileFilter);

  // thread it off
  new Thread(directoryCopier).start();
  System.out.println(">>>>>>>>>  Note: he server thread will continue to run until the DOS window is closed. <<<<<<<<<");
 } // main


 private static class AllFilesFilter implements FileFilter, Serializable
 {
  public boolean accept(File file) { return file != null; }
 }

 private static class OmitClassFilesFilter implements FileFilter, Serializable
 {
  public boolean accept(File file) { return file != null && !file.getName().toLowerCase().endsWith(".class"); }
 }
} // class

//throw erro rin directory procecssor?
//fileMimetype, when do we create a mime and how is it used later?
//difference between runtime and illegalArgument and how do we choose to use them?