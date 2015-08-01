import java.io.*;
import java.net.*;

public class ErrorResponse extends ResponseMessage
{
	/*
	Lauren M DiGregorio
	October 16, 2013

	Class that holds all of our errors

	Class Variables
		serialVersionUID
		static final long that helps machines communicate

		exception
			Holds the exception that was created

	Constructors
		public ErrorResponse(FileCopierException exception) throws IOException
			initializes all class variables

	Methods
		public void processResponse(Socket socket)
			Throws a RuntimeException

	Modification History
		November 24, 2013
		Interface is created and finished.
			*/

	private FileCopierException exception;
	private static final long serialVersionUID = 1L;

	public ErrorResponse(FileCopierException exception) throws IOException
	{
		if(exception == null)
		{
			throw new IllegalArgumentException("Illegal argument passed to [ErrorResponse class], exception was null.");
		} //if
		this.exception = exception;
	} //constructor

	public void processResponse(Socket socket)
	{
		throw new RuntimeException(this.exception);
		//throw new FileCopierException(new DirectoryResponseMessage().getFile(), this.exception.toString());
	} //processResponse() method

} //class