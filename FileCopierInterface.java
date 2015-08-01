import java.io.*;

public interface FileCopierInterface
{
/*

	Lauren M DiGregorio
	October 16, 2013


	Interface meant to be used within FileUtility class.

	Class Variables
		none

	Constructors
		non

	Methods
		public void closeInput()
			[abstract] Method meant to close a given InputStream.

		public void closeOutput()
			[abstract] Method meant to close a given OutputStream.

		public copyFile()
			[abstract] Method meant to copy a file.

		public copyFile(boolean closeInputOnExit, boolean closeOutputOnExit)
			[abstract] Method meant to copy a file, passed boolean of whether both the InputStream and OutputStream should be closed upon exit.

		public copyFile(int bufferSize)
			[abstract] Method meant to copy a file, passed the size of given buffer.

		public copyFile(int bufferSize, boolean closeInputOnExit, boolean closeOutputOnExit)
			[abstract] Method meant to interact with the rest of the copyFile() methods in a recursive method of method overloading.

	Modification History
		November 24, 2013
			Interface is created and finished.
*/
	public void closeInput() throws IOException;

	public void closeOutput() throws IOException;

	public void copyFile() throws IOException;

	public void copyFile(boolean closeInputOnExit, boolean closeOutputOnExit) throws IOException;

	public void copyFile(int bufferSize) throws IOException;

	public void copyFile(int bufferSize, boolean closeInputOnExit, boolean closeOutputOnExit) throws IOException;

} // interface