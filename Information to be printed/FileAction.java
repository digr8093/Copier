import java.io.*;

public interface FileAction
{
	/*
	Lauren M DiGregorio
	October 16, 2013
	CISC 230
	Jarvis

	Interface meant to be used within FileUtility class.

	Class Variables
		none

	Constructors
		none

	Methods
		public void after() throws IOException;
			[abstract] Method done before processFile(File).

		public void before() throws IOException;
			[abstract] Method done after processFile(File)

		public void processFile(File file) throws IOException;
			[abstract] Process file is specified way

	Modification History
		November 24, 2013
		Interface is created and finished.

		*/
	public void after() throws IOException;
	public void before() throws IOException;
	public void processFile(File file) throws IOException;
} //interface