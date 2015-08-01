import java.io.*;

abstract public class FileCopierException extends RuntimeException implements Serializable
{
/*
	Lauren M DiGregorio
	October 16, 2013


	Class that holds all of our errors

	Class Variables
		serialVersionUID
		static final long that helps machines communicate

		File
			File that holds the File object that caused the error

	Constructors
		public FileCopierException()
			initializes all class variables

	Methods
		public File getFile()
			Returns file that caused the error

	Modification History
		November 24, 2013
			Original file
			*/

	private static final long serialVersionUID = 1L;

	private File file;

	public FileCopierException(File file, String message)
	{
		super(message);
		this.file = file;
	} //FileCopierException() constructor

	public File getFile()
	{
		return this.file;
	} //getFile() method

} //class