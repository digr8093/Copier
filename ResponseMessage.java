import java.io.*;
import java.net.*;

abstract public class ResponseMessage extends Message
{
	/*
		Lauren M DiGregorio
		November 24, 2013


		Implements the protocal for our server and client

		Class Variables:
				serialVersionUID
					allows object to be serializable

		Constructors:
				public ResponseMessage()
					default
		Methods:

			abstract public void processRequest(Socket socket) throws IOException,ClassNotFoundException;
				Implemented in subclasses DirectoryResponseMessage, ErrorResponseMessage, and FileResponseMessage

		Modification History:
			October 30, 2013
				Original Version

		*/
	private static final long serialVersionUID = 1L;

	public ResponseMessage()
	{

	} //constructor

	abstract public void processResponse(Socket socket);

} //class