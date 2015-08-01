import java.net.*;
import java.io.*;

abstract public class RequestMessage extends Message
{
/*
	Lauren M DiGregorio
	November 24, 2013
	CISC 230
	Jarvis

	Implements the protocal for our server and client

	Class Variables:
			serialVersionUID
				allows object to be serializable

	Constructors:
			public RequestMessage()
				default
	Methods:

		abstract public void processRequest(Socket socket) throws IOException,ClassNotFoundException;
			Implemented in subclasses DirectoryRequestMessage and FileRequestMessage

	Modification History:
		October 30, 2013
			Original Version

		*/
	private static final long serialVersionUID = 1L;

	public RequestMessage()
	{

	} //constructor

	abstract public void processRequest(Socket socket) throws IOException,ClassNotFoundException;

} //class