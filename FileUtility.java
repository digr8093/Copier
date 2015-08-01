
import java.net.*;
import java.util.*;
import java.io.*;

public abstract class FileUtility
{
 /*
 Lauren M DiGregorio
 November 16, 2013


 This class compares two files and tells us if they are the same or not.  This class
 contains a nested called FileReader.  FileReader helps read and create a buffer for the
 FileUtility class

 Constructor
  FileUtility
   Provides a default constructor for the class

 Methods
  public boolean filesHaveIdenticalContents(File file1, File file2)
   method that will return true or false indicating whether the two input
   files are identical or not.  Has a default buffer size of 1024

  public boolean filesHaveIdenticalContents(File file1, File file2, int bufferSize)
   method that will return true or false indicating whether the two input
   files are identical or not.

 Modification History:
  Novemeber 16, 2013
   Origianl Version

*/
 private static final long serialVersionUID = 1L;
 private FileUtility()
 {
 }//end constructor
 public static boolean filesHaveIdenticalContents(File file1, File file2) throws IOException
 {
  //create a default buffer and then call the other method
  int defaultBufferSize;

  if(!file1.exists())  	{ throw new IllegalArgumentException("File1 does not exist: " + file1.getAbsolutePath());}
  if(!file2.exists())  	{throw new IllegalArgumentException("File2 does not exist: " + file2.getAbsolutePath());}
  if(file1 == null)  	{throw new IllegalArgumentException("File1 is equal to null");}
  if(file2 == null)  	{throw new IllegalArgumentException("File2 is equal to null");}
  if(!file1.isFile())  	{throw new IllegalArgumentException("File is not a file: " + file1.getAbsolutePath());}
  if(!file2.isFile())  	{throw new IllegalArgumentException("File is not a file: " + file1.getAbsolutePath());}

  defaultBufferSize = 1024;
  return filesHaveIdenticalContents(file1, file2, defaultBufferSize);
 }

 public static boolean filesHaveIdenticalContents(File file1, File file2, int bufferSize) throws IOException
 {
  FileReader 	fileIn1;
  FileReader 	fileIn2;
  int   		intReadFile1;
  int   		intReadFile2;
  boolean  		result;
  byte[]  		buffer;

  result = true;

  if(!file1.exists())  {throw new IllegalArgumentException("File1 does not exist: " + file1.getAbsolutePath());}
  if(!file2.exists())  {throw new IllegalArgumentException("File2 does not exist: " + file2.getAbsolutePath());}
  if(!file1.isFile())  {throw new IllegalArgumentException("File is not a file: " + file1.getAbsolutePath());}
  if(!file2.isFile())  {throw new IllegalArgumentException("File is not a file: " + file1.getAbsolutePath());}
  if(file1 == null)  {throw new IllegalArgumentException("File is equal to null: " + file1.getAbsolutePath());}
  if(file2 == null)  {throw new IllegalArgumentException("File is equal to null: " + file2.getAbsolutePath());}
  if(bufferSize <= 0)  {throw new IllegalArgumentException("Buffer Size must be equal to one or more.  The input is: " + bufferSize);}

  if(file1.length() != file2.length()){result = false;}

  //create 2 InputStream objects so we are able to compare the two binaray files


  fileIn1 = new FileUtility.FileReader( new FileInputStream(file1), bufferSize);
  fileIn2 = new FileUtility.FileReader( new FileInputStream(file2), bufferSize);

  intReadFile1 = fileIn1.read();
  intReadFile2 = fileIn2.read();

  while (intReadFile1 >= 0 && intReadFile2 >=0 && result)
  {

   if(intReadFile1 != intReadFile2)
   {
    result = false;
   }
   intReadFile1 = fileIn1.read();
   intReadFile2 = fileIn2.read();

  }//end while

   //close the streams
  fileIn1.close(file1);
  fileIn2.close(file2);

  return result;


 }//end filesHaveIdenticalContents()

 public static FileCopierInterface getFileCopierObject(FileMimeType fileMimeType, InputStream inputStream, OutputStream outputStream)
 {
  FileCopierInterface result;

  if(inputStream == null) {throw new IllegalArgumentException("InputStream is equal to null");}
  if(outputStream == null){throw new IllegalArgumentException("OutputStream is equal to null");}

  if(fileMimeType.typeIsText())
  {
   result = new CopyATextFile(inputStream, outputStream);
  }
  else
  {
   result = new CopyANonTextFile(inputStream, outputStream);

  }//end else
  return result;
 }//end FileCopierInterface getFileCopierObject(FileMimeType fileMimeType, InputStream inputStream, OutputStream outputStream)

 private static class FileReader
 {
  /*
  Lauren M DiGregorio
  November 24, 2013
  CISC 230
  Jarvis

  This class is a nested helper class in FileUtility. This class helps read and compare two Files

  Class Variables:
   buffer
    this variable is in the nested class and is the buffer will use to store the file's
    information in memory.  This is a byte array

   bytesRead
    This is in the nested class. This variable holds the number of bytes read

   positionInBuffer
    This is also in the nested class. This holds the position of the last read file in the buffer.

   source
    Source is in the nested class. It turns the file into an FileInputStream object so the
    files contents can be read into the buffer.

  Constructors:
    FileReader(InputStream, int bufferSize)
     checks to makes sure bufferSize is greater than zero and initializes buffer, bytesRead,
     poistionInBuffer, source in the nested class.


  Methods:
    public void close()
     closes the InputStream object that is created in the nested class

    public void read()
     returns the last sport in the buffer.  The last spot is equal to the number of bytes read

   */

  private byte[]  buffer;
  private int   bytesRead;
  private int   positionInBuffer;
  private InputStream source;


  public FileReader( InputStream source, int bufferSize)
  {
   if(source == null){throw new IllegalArgumentException("InputStream is equal to null");}

   if( bufferSize < 1 )
   {
    throw new IllegalArgumentException("Input for buffer is invalid. Input must be greater than one.");
   }
   this.source    = source;
   this.buffer    = new byte[bufferSize];
   this.bytesRead    = 0;
   this.positionInBuffer = 0;
  }//end constructor

  public void close(File file) throws IOException
  {
   if(!file.exists())
   {
    throw new IllegalArgumentException("File does not exist: " + file.getAbsolutePath());
   }

   if(!file.isFile())
   {
    throw new IllegalArgumentException("File is not a file: " + file.getAbsolutePath());
   }
   if(file == null)
   {
    throw new IllegalArgumentException("File is equal to null: " + file.getAbsolutePath());
   }

   this.source.close();
  }// end close

  public int read() throws IOException
  {
   int result;
   this.positionInBuffer = this.positionInBuffer + 1;
   if(this.positionInBuffer >= this.bytesRead)
   {
    this.positionInBuffer = 0;
    this.bytesRead = this.source.read(this.buffer);

   }// end if(this.positionInBuffer > this.bytesRead)

   result =  this.buffer[this.positionInBuffer];

   if(this.bytesRead < 0)
   {
    result = this.bytesRead;
   }
   return result;

  }//end read()

 }//end class FileReader

 abstract private static class CopyAFile implements FileCopierInterface
 {
  /*
  Lauren M DiGregorio
  November 24, 2013
  CISC 230
  Jarvis

  This class is the parent class to CopyANonTextFile and CopyATextFile. The implemenation
  of FileCopierInterface is hidden in this class.

  Class Variables:
   inputStream
    This is an inputStream object

   outputStream
    This is an outputStream object

   DEFAULT_BUFFER_SIZE
    Is an integar that holds a default buffer size of 8196

  Constructors:
    CopyAFile(InputStream inputStream, OutputStream outputStream)
     Initializes inputStream and outputStream instance variables for the object

  Methods:

   public void closeInput() throws IOException
    Is the concrete implementation for the FileCopier class.
    closes the InputStream object that is created in the nested class

   public void closeOutput() throws IOException
    Is the concrete implementation for the FileCopier class. This class closes the outPut stream
    object in the instance variable

   public void copyFile() throws IOException
    Is the concrete implementation for the FileCopier class.  This method is overloaded and will use
    the instance variable two true booleans and the DEFAULT_BFFER_SIZE in order to call the
    correct copyFile method. This will close the stream objects upon exit.

   public void copyFile(boolean closeInputOnExit, boolean closeOutputOnExit) throws IOException
    Uses the DEFAUL_BUFFER_SIZE to call over loaded method copyFile.  User can specify whether or
    not he wants inputStream or outputStream to close on exit as indicated by the signature of the method

   public void copyFile(int bufferSize) throws IOException
    Uses two true boolean expressions to call the overloaded method copyFile()

   public void copyFile(int bufferSize, boolean closeInputOnExit, boolean closeOutputOnExit) throws IOException
    This method calls the final method doTheFileCopy(). This method will determine whether or not the file
    attempted to be copied is text or not and then call doTheFileCopy(int bufferSize).

   abstract void doTheFileCopy(int bufferSize)
    This method will be implemented int two different ways.  One by CopyANonTextFile() and the other will be
    implemented by CopyATextFile().

   public InputStream getInputStream()
    returns the inputStream instance variable

   public OutputStream getOutputStream()
    returns the outputStream instance variable

  */

  private InputStream  inputStream;
  private OutputStream  outputStream;
  private static int   DEFAULT_BUFFER_SIZE = 4192;

  public CopyAFile(InputStream inputStream, OutputStream outputStream)
  {

   if(inputStream == null)
   {
    throw new IllegalArgumentException("InputStream is equal to null");
   }
   if(outputStream == null )
   {
    throw new IllegalArgumentException("OutputStream is equal to null");
   }

   this.inputStream = inputStream;
   this.outputStream = outputStream;

  }// end constructor

  public void closeInput() throws IOException
  {
   this.inputStream.close();
  }// end closeInput()

  public void closeOutput() throws IOException
  {
   this.outputStream.close();
  }//end closeOut

  public void copyFile() throws IOException
  {
   copyFile(DEFAULT_BUFFER_SIZE, true, true);
  }//end copyFile()

  public void copyFile(boolean closeInputOnExit, boolean closeOutputOnExit) throws IOException
  {
   copyFile(DEFAULT_BUFFER_SIZE, closeInputOnExit, closeOutputOnExit);
  }//end copyFile()

  public void copyFile(int bufferSize) throws IOException
  {
   if(bufferSize <= 0 )
   {
    bufferSize = DEFAULT_BUFFER_SIZE;
   }
   copyFile(bufferSize, true, true);
  }//end copyFile()

  public void copyFile(int bufferSize, boolean closeInputOnExit, boolean closeOutputOnExit) throws IOException
  {

   if(bufferSize <= 0 )
   {
    bufferSize = DEFAULT_BUFFER_SIZE;
   }

   doTheFileCopy(bufferSize);
   if(closeInputOnExit)
   {
    closeInput();
   }
   if(closeOutputOnExit)
   {
    closeOutput();
   }
  }//end copyFile()

  abstract public void doTheFileCopy(int bufferSize) throws IOException;

  public InputStream getInputStream()
  {
   return this.inputStream;
  }//end getInputStream

  public OutputStream getOutputStream()
  {
   return this.outputStream;
  }//end getOutputStream()

 }//end nested class CopyAFile

 public static class CopyATextFile extends CopyAFile
 {

  /*
  Lauren M DiGregorio
  November 24, 2013
  CISC 230
  Jarvis

  This class is sub class of CopyAFile. Will copy only Text files.

  Constructors:
   CopyAFile(InputStream inputStream, OutputStream outputStream)
    inoutStream and outputStream in the super class

  Methods:

   public void doTheFileCopy() throws IOException
    Is the concrete implementation for the FileCopier class.
    closes the InputStream object that is created in the nested class

  */

  public CopyATextFile( InputStream inputStream, OutputStream outputStream )
  {
   super(inputStream, outputStream);
  }//end constructor

  public void doTheFileCopy( int bufferSize ) throws IOException
  {
   // use buffered wreader and printWriter and then we are doing some sort of wrapping
   BufferedReader  fileIn;
   PrintWriter  fileOut;
   String   record;

   if(bufferSize <= 0)
   {
    throw new IllegalArgumentException("Buffer size must be greater or equal to one. You input: " + bufferSize);
   }

   //create InputStream object for source file
   fileIn = new BufferedReader(new InputStreamReader(super.inputStream), bufferSize);

   //create OutputStream object for destination file
   fileOut = new PrintWriter ( new BufferedWriter ( new OutputStreamWriter( super.outputStream ) ) );

   //read bytes from source file and write to destination file


   record = fileIn.readLine();

   while (record != null)
   {
    fileOut.print(record);
    record = fileIn.readLine();

   }//end while

    //close the streams
   fileIn.close();
   fileOut.close();

  }//end method

 }//end CopyATextFile class

 public static class CopyANonTextFile extends CopyAFile
 {
  /*
  Lauren M DiGregorio
  November 24, 2013
  CISC 230
  Jarvis

  This class is sub class of CopyAFile. Will copy only NONText files.

  Constructors:
   CopyANonFile(InputStream inputStream, OutputStream outputStream)
    inoutStream and outputStream in the super class

  Methods:

   public void doTheFileCopy() throws IOException
    Is the concrete implementation for the FileCopier class.
    closes the InputStream object that is created in the nested class

  */
  public CopyANonTextFile(InputStream inputStream, OutputStream outputStream)
   {
    super(inputStream, outputStream);
   }//end constructor

  public void doTheFileCopy( int bufferSize ) throws IOException
  {

   InputStream  fileIn;
   OutputStream  fileOut;
   int    numberOfBytes;
   byte[]   buffer;
   String    source;
   String    destintation;

   if(bufferSize <=0 )
   {
    throw new IllegalArgumentException("Buffer size is too small must be equal to one or greater.");
   }

   //create FileInputStream object for source file
   fileIn = super.inputStream;

   //create FileOutputStream object for destination file
   fileOut = super.outputStream;

   buffer = new byte[bufferSize];
   numberOfBytes = 0;

   //read bytes from source file and write to destination file
   numberOfBytes = fileIn.read(buffer);
   while (numberOfBytes > 0)
   {
    fileOut.write(buffer, 0, numberOfBytes);
    numberOfBytes = fileIn.read(buffer);

   }//end while

    //close the streams
   fileIn.close();
   fileOut.close();

  }//end method

 }//end class



/*public static void main(String[] args) throws IOException
{
 File in;
 File out;
 FileMimeType mimeType;

 mimeType = new FileMimeType("text/plain");

 in = new File("ALittleTest.txt");
 out = new File("ALittleTestCopy.txt");
 FileUtility.getFileCopierObject(mimeType, new FileInputStream(in), new FileOutputStream(out)).copyFile();
 System.out.println(FileUtility.filesHaveIdenticalContents(in, out));


}*/







}//end class