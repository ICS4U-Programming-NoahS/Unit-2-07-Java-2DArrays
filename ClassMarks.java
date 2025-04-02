import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.util.Random;

/**
* The ClassMarks program reads students.txt and assignments.txt.
* It saves the data from each of the files into 2 separate arrays of strings.
* It calls the function GenerateMarks that will take 2 the arrays as arguments.
* It outputs the results to a CSV file called marks.csv
* The function called generateMarks will randomly generate all the marks.
* The mean is 75 and the standard deviation is 10.

*
* @author Noah Smith
* @version 1.0
* @since 2025-04-01
*/

final class ClassMarks {

    /**
    * The maximum mark.
    */
    public static final int MAX_MARK = 100;

    /**
    * The minimum mark.
    */
    public static final int MIN_MARK = 0;

    /**
     * This is a private constructor used to satisfy the
     * style checker.
     *
     * @exception IllegalStateException Utility class.
     * @see IllegalStateException
    */
    private ClassMarks() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * This is the generateMarks method.
     * @param arrayAssignments Array of strings containing assignment names.
     * @param arrayStudents Array of strings containing student names.
     * @return 2D array of marks
     */

    public static String[][] generateMarks(
            final String[] arrayAssignments, final String[] arrayStudents
        ) {

        // Initialize the random class in order to generate random
        // numbers in a gaussian distribution
        Random random = new Random();

        // Create 2D array
        String[][] marks =
        new String[arrayStudents.length + 1][arrayAssignments.length + 1];

        // iterate through the array of student names
        for (
            int studentIndex = 0;
            studentIndex < (arrayStudents.length + 1);
            studentIndex++) {

            // iterate through the array of assignments
            for (
                int assignmentIndex = 0;
                assignmentIndex < (arrayAssignments.length + 1);
                assignmentIndex++) {

                // Generate random marks with mean 75 and standard deviation 10
                // and convert it to an integer
                int mark = (int) (random.nextGaussian() * 10 + 75);

                // convert the mark from an int to a string
                // mark is between 0 and 100
                marks[studentIndex][assignmentIndex] =
                Integer.toString(Math.max(MIN_MARK, Math.min(MAX_MARK, mark)));
            }
        }

        // return marks as a 2d array of strings
        return marks;
    }

    /**
     * This is the main method.
     *
     * @param args Unused.
     */

    public static void main(final String[] args) throws Exception {

        // Declare the paths to the students file
        File studentsFile = new File("./students.txt");

        // Declare the path to the assignments file
        File assignmentsFile = new File("./assignments.txt");

        // Create scanner objects to read files
        Scanner scannerStudents = new Scanner(studentsFile);
        Scanner scannerAssignments = new Scanner(assignmentsFile);

        // Read students from the file as a list
        ArrayList<String> studentsList = new ArrayList<String>();

        // Loop until there are no more students in the file
        while (scannerStudents.hasNextLine()) {

            // Add the student of the current line to the end of the arrayList
            String student = scannerStudents.nextLine();
            studentsList.add(student + " ");
        }

        // Read assignments from the file as a list
        ArrayList<String> assignmentsList = new ArrayList<String>();

        // Loop until there are no more assignments in the file
        while (scannerAssignments.hasNextLine()) {

            // Add the assignment of the current line
            // to the end of the arrayList
            String assignment = scannerAssignments.nextLine();
            assignmentsList.add(assignment);
        }

        // Initialize the arrays of strings
        String[] arrayStudents = new String[studentsList.size()];
        String[] arrayAssignments = new String[assignmentsList.size()];

        // convert the arrayLists into arrays of strings
        for (int counter1 = 0; counter1 < studentsList.size(); counter1 += 1) {
            arrayStudents[counter1] = studentsList.get(counter1);
        }

        for (
            int counter1 = 0;
            counter1 < assignmentsList.size();
            counter1 += 1
            ) {

            // Assign the current assignment from the list to the array
            arrayAssignments[counter1] = assignmentsList.get(counter1);
        }

        // close both scanner objects
        scannerStudents.close();
        scannerAssignments.close();

        // call marks
        String[][] marks = generateMarks(arrayAssignments, arrayStudents);

        // create a file writer object
        FileWriter fileWriter = new FileWriter("Marks.csv");

        // Write the header row (Assignments)
        fileWriter.append("Student Name ");

        // iterate through the array of assignments
        for (String assignment : arrayAssignments) {
            // Separate each assignment name with a comma and space
            fileWriter.append(", " + assignment);
        }

        // Go to the next line to move on to the next row in the table
        fileWriter.append("\n");

        // Write the marks for each student
        for (
            int studentIndex = 0;
            studentIndex < arrayStudents.length;
            studentIndex++
            ) {

            // Display the name of the students
            fileWriter.append(arrayStudents[studentIndex] + " ");

            // Iterate through the array of students
            for (
                int assignmentIndex = 0;
                assignmentIndex < arrayAssignments.length;
                assignmentIndex++
                    ) {

                // Append the mark for the current student and assignment
                fileWriter.append(", "
                + marks[studentIndex + 1][assignmentIndex + 1]);
            }

            // add a newline to move to the next row of the array
            fileWriter.append("\n");
        }

        // close the file writer
        fileWriter.close();

        // Display a success message
        System.out.println("The file has been written successfully.");

    }
}
