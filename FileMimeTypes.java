import java.util.*;
import java.net.*;
import java.io.*;

public class FileMimeTypes
{
 /*

 Lauren M DiGregorio
 November 30, 2013
 CISC 230
 Jarvis

 Creates a map that holds all the file mimetypes for all files processed

 Class Variables
  singletonInstance
   Static final mime type that holds whether or not the object has been created

  map
   hashmap theholds the extension without the period as the key and the FileMimeType as the value

  caseSenstiveFileSystem
    boolean that tells the user and program if the operating system is case sensitive or not

 Constructor
  FileMimeTypes
   creates a new hashmap and checks to see if the operating system is case sensitive

 Methods
  synchronized public static FileMimeTypes getInstance()
   returns singleton instance variable once it is created

  synchronized public static FileMimeTypes newInstance()
   returns getInstance()

  private void makeIntoKey(String fileNameOrExtension)
   Takes a string and puts only new keys into map

  public boolean fileNamesAreCaseSensitive()
   Tells if the operating system is case senstive or not

  synchronized public FileMimeType getFor(String fileNameOrExtension)
   should return mimeType or null
   return null if it cannot get extension
   checks the map to see if it extsists in the map

  public FileMimeType getFor(File file)
   over loaded method that eventually calls getFor(String)

  synchronized public FileMimeType setFor(String fileNameOrExtension, FileMimeType fileMimeType)
    this method filters information then calls the makeIntoKey() with the correct extension
    this is called assuming we have already called getFor() method to check whether or not the
    key already exists in map

  public int size()
   returns the size of the map

  public String toString()
   returns a string version of the hashmap

 Modification History:
  Novemeber 30, 2013
  Origianl Version


 */
 private static final  FileMimeTypes      singletonInstance = new FileMimeTypes();
 private     HashMap<String, FileMimeType>  map;
 private boolean   caseSenstiveFileSystem;

 private FileMimeTypes()
 {
  this.caseSenstiveFileSystem = new File("A.A").equals(new File("a.a"));
  this.map      = new HashMap<String, FileMimeType>();
  setFor("txt", new FileMimeType("text/plain"));
  setFor("dat", new FileMimeType("text/plain"));
  setFor("pdf", new FileMimeType("application/pdf"));
  setFor("java", new FileMimeType("text/java"));
  setFor("jpg", new FileMimeType("image/jpg"));
  setFor("class", new FileMimeType("application/java-class"));
  //need to implement caseSenstivity here

 }//constructor

 synchronized public static FileMimeTypes getInstance()
 {
  return FileMimeTypes.singletonInstance;
 }//end getInstance()

 synchronized public static FileMimeTypes newInstance()
 {
  return FileMimeTypes.getInstance();
 }

 private void makeIntoKey(String fileNameOrExtension)
 {
  //get rid of period here
  FileMimeType temp;
  String mimeType;

  mimeType = URLConnection.getFileNameMap().getContentTypeFor(fileNameOrExtension);
  temp = new FileMimeType(fileNameOrExtension);

  this.map.put( fileNameOrExtension, temp);
 }
 public boolean fileNamesAreCaseSensitive()
 {
  boolean result;
  result = false;
  if(this.caseSenstiveFileSystem)
  {
   result = true;
  }
  return result;
 }//end fileNameAreCaseSensitive

 synchronized public FileMimeType getFor(String fileNameOrExtension)
 {
  //should return mimeType or null
  //return null if it cannot get extension
  //checks the map to see if it extsists in the map
  int    temp;
  String    finalExtension;
  FileMimeType  fileMimeType;

  temp = fileNameOrExtension.lastIndexOf('.');

  if(temp < 0)
  {
   finalExtension = fileNameOrExtension;
  }
  else
  {
   finalExtension = fileNameOrExtension.substring(temp+1, fileNameOrExtension.length());
  }

  if(map.containsKey(finalExtension))
  {
   //looks for the extension in map to see if there is an object associated with that extension
   //if it finds it, return that object in the map

   fileMimeType = map.get(finalExtension);
  }
  else
  {
   //if the extension is not found in the map, return null
   fileMimeType = null;
  }

  return fileMimeType;
 }

 public FileMimeType getFor(File file)
 {
  //over loaded method
  if( !file.isFile() )
  {
   throw new IllegalArgumentException("FileMimeTypes.getFor(File): File input is not a file: "+file);
  }
  if( !file.exists() )
  {
   throw new IllegalArgumentException("FileMimeTypes.getFor(File): File does not exist: "+file);
  }
  return getFor(file.getName());
 }
 synchronized public FileMimeType setFor(String fileNameOrExtension, FileMimeType fileMimeType)
 {
  //this method filters information then calls the makeIntoKey() with the correct extension
  //this is called assuming we have already called getFor() method to check whether or not the
  //key already exists in map

  FileMimeType  mimeObjectInMap;
  FileMimeType  returnFileMimeType;
  String   finalExtension;
  int    temp;

  mimeObjectInMap = getFor(fileNameOrExtension);

  temp = fileNameOrExtension.lastIndexOf('.'); //need to make sure periods arent included

  if(temp < 0)
  //this holds the extension of the file
  //this code is copied from above. ultimately this should be pulled and done by a method or class
  //but right now my focus is getting the functionality of this class correct
  {
   finalExtension = fileNameOrExtension;
  }
  else
  {
   finalExtension = fileNameOrExtension.substring(temp + 1, fileNameOrExtension.length());
  }

  if( mimeObjectInMap != null)
  //this means the the extension exists in map as a key
  {
   //now we want to compare if the objects are the same
   if( mimeObjectInMap.equals(fileMimeType) )
   {
    //means the fileMimeType is identical in the map
    returnFileMimeType = mimeObjectInMap;
   }//end if
   else
   {
    //the objects are not the same and so we are going to update the
    //object that is in the map
    //we want only the extension
    map.put(finalExtension, fileMimeType);
    returnFileMimeType = fileMimeType;
   }//end else
  }//if(mimeObjectInMap != null)

  else
  {
   //this means the file is equal to null and we need to add object to our map
   map.put(finalExtension, fileMimeType);
   returnFileMimeType = fileMimeType;
  }
  return returnFileMimeType;
 }

 public int size()
 {
  return this.map.size();
 }
 public String toString()
 {
  Iterator i;
  String result;

  result = "";

  i = map.entrySet().iterator();
  while(i.hasNext())
  {
    Map.Entry mEntry = (Map.Entry) i.next();
    result = "" + mEntry.getKey() + " : " + mEntry.getValue() + "\n";
  }

  return result;
 }

}//class