import java.io.*;

public interface FileFilter
{
	/*
	Lauren M DiGregorio
	October 16, 2013


	Interface meant to be used within FileUtility class.

	Class Variables
		none

	Constructors
		none

	Methods
		public boolean accept()
			returns true or false based on if the file is accepted by the fileFilter

	Modification History
		November 24, 2013
		Interface is created and finished.
		*/

	public boolean accept(File file);
} //interface