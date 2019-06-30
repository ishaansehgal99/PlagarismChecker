import java.io.*; 
/**
 * Write a description of class CatchingPlagiaristsRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CatchingPlagiaristsRunner
{
    public static void main(String args[]) throws IOException{
        CatchingPlagiarists c = new CatchingPlagiarists(); 
        c.readDirectories(); 
        c.comparingMultipleFiles(); 		
    }
}
