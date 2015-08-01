
import java.io.*;
public class DirectoryLister
{
	/*
	Lauren DiGregorio
	October 24, 2013


	This class will list files and directories.  FileList is constructed like
	a stack

	Class Variables:
		list
			A list of all files and directories. Is type FileList.

	Constructors:
		public DirectoryLister(File startingDirectory)
			Passed a directory to initialize our instance variables

	Methods:
		private FileList getList()
			returns list

		private File getFromList()
			returns whichever file is on top of the stack

		private void putInList(File file)
			adds a file to the top of file list

		public File next()
			returns a file which is also removed from the list

	Modification History:
		October 9, 2013
			Original Version

		October 10, 2013
			Added public File next()

		October 14, 2013
			Now adds startingDirectory to the vector

	*/
	private FileList list;

	public DirectoryLister(File startingDirectory)
	{
		//checks to see if the starting directory is a directory and not equal to null

		if(startingDirectory == null)
		{
			throw new IllegalArgumentException("File parameter is null.");
		}
		if(!startingDirectory.isDirectory())
		{
			throw new IllegalArgumentException("File parameter is not a directory.");
		}

		//if both of the two previous conditions are met, we create a new FileList object and add the starting directory
		//to the beginning of the list

		this.list = new FileList();
		this.putInList(startingDirectory);

	}//end constructor

	public void putInList(File file)//adds a file to the file list
	{
		//as ling as the input file is not equal to null, add file to the list
		if(file == null)
		{
			throw new IllegalArgumentException("File parameter is null.");
		}

		this.list.put(file);  // put the file on the list

	} // putInList

	private FileList getList()
	//returns list
	{
		return this.list;
	}
	private File getFromList()
	{
		//returns whichever file is on top of the stack
		return this.getList().take();
	}


	public File next()//returns a file which is also removed from the list
	{
		File[] fileArray;
		File result;
		if(this.list.isEmpty())
		{
			result = null;
		}//end if

		else
		{
			result = getFromList();

			//  if the file is a directory, put its contents on the list
			if(result.isDirectory())
			{

	 			fileArray = result.listFiles();
				if(fileArray != null)
				{
	  				for(int i=0; i<fileArray.length; i++)
	  				{
						this.list.put(fileArray[i]);
					}
	 			}  // if(fileArray != null)

			}//end result.isDirectory()

 		}//end else

 		return result;

	}//end next()

}//end class