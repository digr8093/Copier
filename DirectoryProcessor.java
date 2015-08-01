import java.io.*;
import java.util.*;

public class DirectoryProcessor
{
 /*
 Lauren M DiGregorio
 October 16, 2013


 This class specifies how we want to process the file we pass this class

 Class Variables:
  directoryLister
   is type DirectoryLister and goes through all the files in the directory
   and "touches" them

  fileFilter
   implements FileFilter interface.  As our DirectoryProcessor goes through and
   processes each file, fileFilter will filter the results based on the file
   extension and whether the extension is case sensitive.

 Constructors:
  DirectoryProcessor( File startingDirectory, FileFilter fileFilter )
   initializes our directoryLister and fileFilter instance variable.

  DirectoryProcessor( File startingDirectory )
   initializes our directoryLister with our startingDirectory.  Also


 Methods:
  public void run()
   goes through each of the files

  abstract public void processFile(File file, FileFilter fileFilter)
   abstract method will be realized in the the subclass DirectorySize.
   This class will indicate what process methods will be applied to
   each file as it is "touched".

  public AllFilesFileFilter()
   This is a nested class that allows for the conditions for all files to
   be processed.

 Modification History:
  October 12, 2013
   Origianl Version

  Novemebr 2, 2013
   added the nested class
*/
 private DirectoryLister directoryLister;
 private FileAction  fileAction;


 public DirectoryProcessor(File startingDirectory, FileAction fileAction)
 {
  //We will only take a starting directory and will check to make sure it is a directory, not equal to null, and it exists

  if(!startingDirectory.exists())
  {
   throw new IllegalArgumentException("This directory does not exist");
  }
  if(startingDirectory == null)
  {
   throw new IllegalArgumentException("This directory is equal to null");
  }
  if(!startingDirectory.isDirectory())
  {
   throw new IllegalArgumentException("The startingDirectory is not a directory");
  }
  if(fileAction == null)
  {
   throw new IllegalArgumentException("FileAction is equal to null");
  }
     this.directoryLister = new DirectoryLister(startingDirectory);
     this.fileAction = fileAction;
 }//end constructor

 public void run()
 {
  File file;

  file = this.directoryLister.next();

  try
  {


  this.fileAction.before();
  while( file != null)
  {
   this.fileAction.processFile(file);
   file = directoryLister.next();

  }//end while

 this.fileAction.after();

}
catch(Exception e)
{
 e.printStackTrace();
 throw new RuntimeException("InDirectoryProcessor.run(). File is " + file + "\n"+e.getMessage());
}
 }//end run()



}//end class