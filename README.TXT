PROJECT TITLE: Catching Plagarists
AUTHOR: Ishaan Sehgal
DATE: May 21st, 2018
DESCRIPTION: This program can read the small, medium or large directories of .txt files. It has two stages, the initialization stage and the processing stage. During the initialization stage each file is stored as a string called "phrase." This string is then parsed into Nphrases. Each N-word phrase is stored into a HashSet and then added to the queue (LinkedList) of HashSets. Progress is printed by displaying what % of files have been parsed. In the second stage (processing), the queue is converted to an object array. Using an array, makes the retrieval of HashSet objects quicker than using the queue to fetch the elements. Then the duplicateValueCountBetweenFiles(HashSet m1, HashSet m2) is called which compares two HashSets using the retainAll() method, which retains the number of matches between the HashSets. If the value returned is greater the the minNumOfDuplications set by the user, then in a HashMap<Integer, String> the count and the file names are recorded. The sortAndPrint(HashMap h) is then called which sorts the keys of the HashMap and prints out the respective files associated with the keys. 
ESTIMATED RUNNING TIME:  
SMALL DATA SET - 1 - 4 Seconds
MEDIUM DATA SET - 3 - 7 Seconds
LARGE DATA SET - 330 - 360 Seconds (5.5 - 6 minutes)
COMPLEXITY: 
The Big-O-Complexity of the initialization stage is O(N^2) * O(X^2), X is based on the number of .txt files. There is a O(N^2) complexity because the readFilesInDirectories(String directory) is in a for-loop calls the readFiles(String filename) function which contains a for-loop processing each file into the string "phrase". The Big-O-Complexity of the processing stage is O(N^2) * O(logx) the HashSet .retainAll() function is simply O(1). The insertion of values into the val HashMap takes O(logx) this is considerable amount of time (adds 3 minutes). If given more time I would find a way to avoid using the put() method on the HashMap, this would save me three minutes of run time. Overall, could be better.
HOW TO START THIS PROJECT: Main method of CatchingPlagaristsRunner class. 
USER INSTRUCTIONS: Must input the directory to be read. The n in the number of N-word phrases. And the minimum # of duplications of a phrase needed to be displayed as plagiarized. 
