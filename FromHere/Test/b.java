import java.io.*;
import java.util.*;

public class FileList
{
 /*
    Patrick L. Jarvis
    September 26, 2012

    Simulate a stack of File objects
 */

 private Vector<File> fileList;

 public FileList()                { fileList = new Vector<File>(); }

 private Vector getFileList()     { return this.fileList; }

 public boolean isEmpty()         { return size() < 1;                             }
 public void    put(File file)    { this.fileList.insertElementAt(file, 0);        }
 public int     size()            { return this.fileList.size();                   }
 public File    take()            { return this.fileList.remove(0);                }

}