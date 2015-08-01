import java.io.*;
import java.net.*;
public class FileMimeType implements Serializable
{
	/*
	Lauren M DiGregorio
	November 30, 2013
	CISC 230
	Jarvis

	Holds the fileMimeType for a file

	Class Variables
		serialVersionUIL = 42L
			this variable allows the class to be serializeable

		subType
			String that hols the subtype

		private String type;
			string that holds the type

	Constructor
		FileMimeType( String mimeType )
			breaks the string into type and subtype and passes values to initialize

		FileMimeType( String type, String subType )
			passes values to initialize
	Methods
		private void initialize( String type, String subType )
			sets instance variables type and subtype

		public boolean equals(Object other)
			over rides equals to compare to fileMimeType objects

		public int hashCode()
			over ridden method that returns the ascode for the fileMimeType

		public String getMimeType()
			returns full mime type

		public String getType()
			returns the type

		public String getSubType()
			returns subtype

		public String toString()
				returns mime type

		public int size()
			returns the size of the map

		public String toString()
			returns a string version of the hashmap

		public boolean subTypeIs(String subType)
			returns true if passed value is the same as the object's subtype

		public boolean typeIs(String type )
			returns true if passed value is the same as the object's type

		public boolean typeIsApplication()
			returns true if passed value is the same as application

		public boolean typeIsAudio()
			returns true if passed value is the same as audio

		public boolean typeIsImage()
			returns true if passed value is the same as Image

		public boolean typeIsText()
			returns true if passed value is the same as text

		public boolean typeIsVideo()
			returns true if passed value is the same as video

	Modification History:
		Novemeber 30, 2013
		Origianl Version

		*/
	private static final long serialVersionUIL = 1L ;
	private String subType;
	private String type;

	public FileMimeType( String mimeType )
	{
		String[] hold;
		hold = mimeType.split("/");

		initialize( hold[0], hold[1]);
	}//end constructor

	public FileMimeType( String type, String subType )
	{
		initialize( type, subType );
	}//end constructor

	private void initialize( String type, String subType )
	{
		this.subType = subType;
		this.type = type;
	}//end initialize()

	public boolean equals(Object other)
	{
		FileMimeType hold;

		hold =(FileMimeType)other;

		return this.getMimeType().equals( hold.getMimeType());
	}
	public int hashCode()
	{
		return getMimeType().hashCode();
	}

	public String getMimeType()
	{
		return this.type + "/" + this.subType;

	}//end getMimeType()
	public String getType()
	{
		return this.type;
	}
	public String getSubType()
	{
		return this.subType;
	}

	public String toString()
	{
		return "The TYPE is " + this.type + " and the SUBTYPE is " + this.subType;
	}
	public boolean subTypeIs(String subType)
	{
		//equals method
		return subType.equals(this.subType);
	}
	public boolean typeIs(String type )
	{
		return this.type.equals(type);
	}

	public boolean typeIsApplication()
	{
		return this.typeIs("application");
	}
	public boolean typeIsAudio()
	{
		return this.typeIs("audio");
	}
	public boolean typeIsImage()
	{
		return this.typeIs("image");
	}
	public boolean typeIsText()
	{
		return this.typeIs("text");
	}
	public boolean typeIsVideo()
	{
		return this.typeIs("video");
	}

}//end class