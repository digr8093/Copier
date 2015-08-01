

import java.io.*;
import java.util.*;

public class FileList
{
/*
	Lauren M DiGregorio
	October 12, 2013
	CISC 230
	Javis

	This class adds and subtracts to the vector of type Files.
	The vector of files is set up like a stack.

	Class Variables:
		fileList
			fileList is a vector of Files that contain
			the full list of the path names for one directory

	Constructors
		public FileList()
			initializes the instance variable fileList to
			be set to have a length of 1 and to be a file
			object
	Methods
		private Vector getFileList()
			returns a copy of the instance variable fileList

		public boolean isEmpty()
			this method asks the question of if the object
			being put into the stack is a file or directory.
			Also checks to make sure the value being passes
			is not a null value

		public void put(File)
			this method takes a File object as its parameter
			and then puts it on the top of the fileList instance
			variable like a stack

		public int size()
			returns an integer that holds the size of the
			fileList vector

		public File take()
			takes one File object off the top of the stack

	Modification History:
		October 12, 2013
			Original Version

		October 14, 2013
			updated put() to access the instance variable directly
*/
	private Vector <File> fileList;

	public FileList()
	{
		fileList = new Vector<File>();
	}

	private Vector getFileList()
	{
		return this.fileList;
	}

	public boolean isEmpty()
	{
		return this.fileList.isEmpty();
	}

	public void put(File file)
	{
		this.fileList.addElement(file);
	}

	public int size()
	{
		return this.fileList.size();
	}

	public File take()
	{
		File file;

		if (this.fileList.size() < 1)
		{
			file = null;
		}

		else
		{
			file = fileList.remove(this.fileList.size()-1);
		}
		return file;

	}

}//Example