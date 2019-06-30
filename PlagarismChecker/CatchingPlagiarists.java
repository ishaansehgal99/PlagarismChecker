import java.util.*; 
import java.io.*; 

/**
 * Detects plagarism among numerous text files. 
 * 
 * @author Ishaan Sehgal 
 * @version May 21st, 2018
 */
@SuppressWarnings("unchecked")
public class CatchingPlagiarists
{
    private ArrayList<File> directories = new ArrayList<File>();
    private List<String> files = new ArrayList<String>();
    private static long startTime = 0, endTime = 0; 
    private Queue<HashSet> q = new LinkedList<HashSet>(); 
    private static int numOfFiles = 0; 
    private static int minNumOfDuplications = 0; 
    private static int nWordPhrases = 0; 

    /**Reads Directories
     */
    public void readDirectories() throws IOException{
        File dir = new File(".");
        for(File folder : dir.listFiles()){
            if(folder.isDirectory()) {
                directories.add(folder);
            }
        }

        System.out.println("Which data set would you like to use? (1: Small, 2: Medium, 3: large)");
        Scanner input = new Scanner(System.in); 
        int dataSet = input.nextInt(); 
        System.out.println("What n for n-word phrases would you like to compare? (> 1)"); 
        nWordPhrases = input.nextInt(); 
        System.out.println("What minimum number of duplications would you like to consider?");
        minNumOfDuplications = input.nextInt(); 

        if(dataSet == 1){
            readFilesInDirectories(directories.get(1).toString());    //Location of small data set.         
        }
        else if(dataSet == 2){
            readFilesInDirectories(directories.get(0).toString()); //Location of medium data set.
        }
        else if(dataSet == 3){
            readFilesInDirectories(directories.get(2).toString()); //Location of large data set.
        }

    }

    /**Reads files in from directory represented as a string
     */
    public void readFilesInDirectories(String directory) throws IOException{
        startTime = System.nanoTime();
        File dir = new File(directory); 
        String[] temp = dir.list();
        numOfFiles = temp.length; 

        for(int i = 0; i < temp.length; i++)
        {
            if(temp[i].endsWith(".txt")) {  //Only .txt files to be processed
                files.add(temp[i]);
                readFiles(directory.toString() + "/" + temp[i]); 
            }
        }

    }

    /**Stores the entire string as a phrase using BufferedReader and then
    calls processingPhrases*/
    public void readFiles(String fileName) throws IOException{
        BufferedReader file = new BufferedReader(new FileReader(fileName)); //Quicker than Scanner //
        String phrase = ""; 
        String lineCheck = ""; 

        while((lineCheck = file.readLine()) != null)
        {
            phrase += lineCheck.toLowerCase(); 
        }
        file.close(); 
        processingPhrases(phrase); 

    }

    /**The entire file is represented as the string "phrase". Here using substrings and indexOf
     * String is parsed into N-word phrases stored in a HashSet and then HashSet is put into a 
     * Queue (LinkedList) of HashSets.
     */
    public void processingPhrases(String phrase){
        String Nphrase = "", line = "", word = "content"; //Word is given an initial value so while loop is entered initially.  
        int progress; //Displays progess of initialization stage. 

        HashSet<String> hash = new HashSet<String>(); 
        while(!word.isEmpty()){
            line = new String (phrase); 
            word = line.substring(0, line.indexOf(" "));   //First Word. 

            for(int i = 0; i < nWordPhrases; i++){
                Nphrase += word; // Add that one word. 
                if(i < nWordPhrases - 1){ //Add space between words, but not the last word
                    Nphrase += " "; 
                }

                line = line.substring(line.indexOf(" ") + 1) + " "; // Move up one word in the line. 
                word = line.substring(0, line.indexOf(" ")); // Read that one word from the line. 

            }
            phrase = phrase.substring(phrase.indexOf(" ") + 1); //File string gets moved up one word each loop. 
            hash.add(Nphrase); 
            Nphrase = ""; //Clear Nphrase
        }
        q.add(hash); 

        progress = (int)((q.size()/(double)numOfFiles) * 100); 
        System.out.print('\u000C');
        System.out.println("Initializing " + progress + "%");

    }

    /**Finds number of duplicate values among two HashSets. 
     */
    public int duplicateValueCountBetweenFiles(HashSet m1, HashSet m2){ 
        HashSet hash = (HashSet)m1.clone(); 
        hash.retainAll(m2);
        return hash.size();     
    }

    /**Iterates through all files in data set, Calling the 
     * duplicateValueCountBetweenFiles(HashSet m1, HashSet m2) multiple times.
     * If value returned is greater than minNumOfDuplications set by user count and
     * the associated files are stored in a HashMap. 
       */
    public void comparingMultipleFiles(){
        int count = 0; 
        int progress; 
        int counter = 0;
        HashMap<Integer, String> val = new HashMap(); 

        Object arr[] = q.toArray(); 

        for(int file = 0; file < numOfFiles; file++){
            for(int i = file + 1; i < numOfFiles; i++){
                count = duplicateValueCountBetweenFiles((HashSet)(arr[file]) , (HashSet)(arr[i])); 
                if(count > minNumOfDuplications){
                    val.put(count, files.get(file) + " " + files.get(i));
                }
                progress = (int)((file/(double)numOfFiles) * 100); 
                System.out.print("Processing "+ progress + "%");
                System.out.print('\u000C');
            }
        }
        System.out.print('\u000C');
        System.out.print(sortAndPrint(val)); 
        System.out.println("Success!\n");
        endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000000;
        System.out.println("*****This run took " + duration + " seconds.*****");
    }

    /**Prints out the results: what is stored in the HashMap. The HashMap keys are the
     * count these are ordered in an arraylist. Contents of Arraylist and associated files
     * are printed. 
       */
    public String sortAndPrint(HashMap h){
        String result = ""; 
        List<Integer> mapKeys = new ArrayList<Integer>(h.keySet());
        Collections.sort(mapKeys);
        for(int i = mapKeys.size()-1; i >= 0; i--){
            int count = mapKeys.get(i); 
            //Double.valueOf((double)(Math.round(((count/(double)numOfNPhrases[i]) * 100) * 100))  /100) + "% Plagarized\t" 
            result += count + "\t" + String.valueOf(h.get(count)) + "\n"; 
        }
        return result; 
    }
}