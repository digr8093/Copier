import java.io.*;

public class FileAlreadyExistsException extends FileCopierException
{
	/*
	Lauren M DiGregorio
	October 16, 2013


	Throws error if file already exists.

	Class Variables
		serialVersionUID
		static final long that helps machines communicate

	Constructors
		public FileAlreadyExistsException()
			initializes all class variables

	Methods
		public File getException()
			Returns file that caused the error

	Modification History
		November 24, 2013
		Interface is created and finished.
		*/
	private static final long serialVersionUID = 1L;

	public FileAlreadyExistsException(File file, String message)
	{
		super(file, message);
	} //FileAlreadyExistsException

} //class