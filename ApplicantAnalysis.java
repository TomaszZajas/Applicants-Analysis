import java.util.*;
import java.io.*;


/*
 * This program reads applicant grades from a CSV (Comma Separated Values) file and calculates
 * each applicants points total. To use the program you need to specify the CSV filename and the
 * cutoff value. Use the following format
 * 
 *             ApplicantAnalysis filepath cutoff
 *             
 * If the filepath contains spaces then enclose it in quotation marks (e.g. "The filename has spaces.CSV").
 */
public class ApplicantAnalysis {

    public static void main(String[] args) {
        if(args.length == 2) {
            // File containg applicant information
            String filePath = args[0];              
            // Course points cutoff - passed in the command line as a String and converted to an integer
            int cutoff = Integer.parseInt(args[1]); 
            // TreeMap stores the applicant Number and associated points total (i.e. ID ---> Points)
            TreeMap<String,Integer> candidateScores = calculateApplicantScores(filePath);
            if(candidateScores != null) {
                // LinkedList stores a list of applicantNumbers containing the applicants with Points >= cutoff
                LinkedList<String> chosenApplicants = select(candidateScores,cutoff);
                // LinkedList toString method will automatically be used to display list of successful applicantNumbers
                if(chosenApplicants != null) {
                    System.out.println(chosenApplicants);
                } else {
                    System.out.println("There are no applicants with sufficient points for the course!");  
                }                    
            } else {
                System.out.println("There are no applicants for the course!");  
            }
        } else {
            // Program command line is incorrect
            System.out.println("Command Line format error.");
            System.out.println("Use 'ApplicantAnalysis filepath cutoff'");
            System.out.println("For example - ApplicantAnalysis LM999.CSV 390'");
        }
    }

    public static TreeMap<String,Integer> calculateApplicantScores(String filePath) {
        try {
            // Create a File object to access the file
            File fileHandle = new File(filePath);                                
            // Create an instance of the Scanner to actually read the file
            Scanner csvFile = new Scanner(fileHandle);
            // TreeMap stores the applicant applicantNumber and associated points total (i.e. ID ---> Points)
            TreeMap<String,Integer> candidates = new TreeMap<String,Integer>();
            // Read through the CSV file of Applicant Numbers and  LCE grades  
            // and calculate the applicant points scores
            while(csvFile.hasNext()){                                            
                // Read the next applicant data line (applicantNumber followed by grades - comma separated)
                String applicantDetails = csvFile.nextLine();  
                // Find end of applicant Number (i.e. first comma)
                int posFirstComma = applicantDetails.indexOf(",");                          
                // Extract the applicant Exam Number
                String applicantID = applicantDetails.substring(0,posFirstComma);  
                // Extract the part of the CSV line that contains the grades (i.e. from position after first comma)
                String applicantGrades = applicantDetails.substring(posFirstComma+1);
                // Use String split operation to create array from grades
                String[] grades = applicantGrades.split(",");
                // For testing purposes we might want to display the data
                //System.out.printf("\nThis applicant : %s - %s\n",applicantID, Arrays.toString(applicantGrades));
                // Use the "pointsScore" method to calculate the applicants points total and 
                // add the applicantNumber and points score to the TreeMap
                candidates.put(applicantID,pointsScore(grades));
            }
            // Return the TreeMap
            return candidates;
        } catch (IOException e) {
            // If there is some problem with the file we just report it
            System.out.printf("Cannot access the file named '%s'!\n",filePath);
            return null;
        }
    }

    public static LinkedList<String> select(TreeMap<String,Integer> candidateScores,int cutoff) {
        LinkedList<String> accepted = new LinkedList<String>();//linked list where the students who meet the cutoff point will be stored
         
        for(Map.Entry<String, Integer> entry : candidateScores.entrySet()){//traverses TreeMap using a foreach loop
            String key  = entry.getKey();//gets the student number from the TreeMap
            Integer value = entry.getValue(); //gets the amount of points the student recieved from the TreeMap
            
            if(value>=cutoff){//if the points value is larger or equal to the cutoff value the student number is stored in a linkedlist
                accepted.add(key);
            }else{//if it is not the loop moves onto the next student 
                ;
            }
        }
        
        
        
        // The null value is included here simply to make the program compile.
        // You need to replace this with the LinkedList you have created.
        return accepted;  
    }

    public static int pointsScore(String[] subjectGrades) {
        int points = 0; //int that will store the value of points recieved
        Integer[] scores = new Integer[subjectGrades.length];//array that will store individual scores in descending order
        
        Map<String,Integer> grades;
        grades = new TreeMap<String,Integer>(); //TreeMap containing all possible leaving cert grades and their correlating points
                                                //Each grade that the student recvieved is a key in the TreeMap and the value it gives out is how many points that grade is worth
        
        grades.put("H1", 100);
        grades.put("H2", 88);
        grades.put("H3", 77);
        grades.put("H4", 66);
        grades.put("H5", 56);
        grades.put("H6", 46);
        grades.put("H7", 37);
        grades.put("H8", 0);
        grades.put("O1", 56);
        grades.put("O2", 46);
        grades.put("O3", 37);
        grades.put("O4", 28);
        grades.put("O5", 20);
        grades.put("O6", 12);
        grades.put("O7", 0);
        grades.put("O8", 0);
        
        
        for(int i = 0; i<subjectGrades.length; i++){ //stores the integer value of each grade the student revieved into the array scores
            scores[i] = grades.get(subjectGrades[i]);
        }
        Arrays.sort(scores, Collections.reverseOrder()); //sorts the array in descending order so that the six best grades come first
        
        for(int i = 0; i<6; i++){
            points = scores[i] + points; //adds the points of the best six grades
        }
        
        
        
        // You need to replace this with the points total you have calculated.
        return points; 
    }
}