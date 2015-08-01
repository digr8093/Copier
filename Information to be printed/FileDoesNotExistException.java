import java.io.*;

public class FileDoesNotExistException extends FileCopierException
{
	/*
	Lauren M DiGregorio
	October 16, 2013
	CISC 230
	Jarvis

	Throws error if file does not exists.

	Class Variables
		serialVersionUID
		static final long that helps machines communicate

	Constructors
		public FileDoesNotExistException()
			initializes all class variables

	Methods
		none

	Modification History
		November 24, 2013
		Interface is created and finished.
	*/
	private static final long serialVersionUID = 1L;

	public FileDoesNotExistException(File file, String message)
	{
		super(file, message);
	} //FileDoesNotExistException() constructor

} //class