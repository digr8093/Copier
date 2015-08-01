import java.io.*;

public class SystemGeneratedException extends FileCopierException
{
/*
	Lauren M DiGregorio
	October 16, 2013
	CISC 230
	Jarvis

	Throws generic error if file does not exists.

	Class Variables
		serialVersionUID
		static final long that helps machines communicate

	Constructors
		public SystemGeneratedException(File file, Exception exception)
			initializes all class variables

	Methods
		public Exception getException()
			returns the error held in the instance variable

	Modification History
		November 24, 2013
		Interface is created and finished.
	*/
	private Exception exception;
	private static final long serialVersionUID = 1L;

	public SystemGeneratedException(File file, Exception exception)
	{
		super(file,"System generated exception was sent to user.");
		this.exception = exception;
	} //SystemGeneratedException() constructor

	public Exception getException()
	{
		return this.exception;
	} //getException() method
} //class