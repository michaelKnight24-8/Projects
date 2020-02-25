package practice;

import java.net.SecureCacheResponse;
import java.util.Scanner;

public class Main {

    /**TODO
     *
     *  LET THE USER SPECIFICY HOW MANY NUMBERS, AND WHICH ALGORITHM THEY ARE GOING TO USE
     *  COUNT THE AMOUNT OF COMPARES, AND LET THE USER SEE THAT, AND HOW LONG IT TOOK IN SECONDS
     *  once the user chooses the sorting method, and how much, call the method sending in the
     *  amount of numbers they want to be sorted. Fill the array with random numbers. Then copy that array
     *  before we sort it with the algorithm. Call the sort() method on the copy. Once the original is done
     *  being sorted via the algorithm, compare the two.
     *  <---------I M P O R T A N T------->
     *      START THE TIMER after I'VE ALREADY SORTED THE COPY SO THAT
     *      THE SORTING TIME DOENS'T FACTOR INTO THE ACTUAL TIME
     *      function to display the info, another to call the correct class
     */

    public static String [] sortNames = {"Bubble", "Selection", "Insertion", "Merge", "Quick", "Compare all sorts"};
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        boolean compare = false;
        displayMenu();
        int size = getSize();
        int choice = getSort();
        float start = System.currentTimeMillis();
        String sortName = "";
        switch (choice) {
            case 1:
                new Bubble(size);
                sortName = "Bubble";
                break;
            case 2:
                new Selection(size);
                sortName = "Selection";
                break;
            case 3:
                new Insertion(size);
                sortName = "Insertion";
                break;
            case 4:
                new Merge(size);
                sortName = "Merge";
                break;
            case 5:
                new Quick(size);
                sortName = "Quick";
                break;
            case 6:
                compareAllSorts(size);
                compare = true;
                break;
        }

        if (!compare) {
            System.out.println(sortName + " sort took " + Float.toString((System.currentTimeMillis() - start) / 1000)
                    + " seconds to correctly sort " + size + " numbers!");
        }
    }

    public static void displayMenu() {
        System.out.println("This will be a representation of how fast various sorting algorithms are");
        System.out.println("The algorithms that will be used in this program are: ");
        for (int i = 0; i < sortNames.length; i++) {
            System.out.println(i + 1 + ": " + sortNames[i]);
        }
    }

    public static int getSort() {
        int choice;
        System.out.println("Enter the number of a sort you would like to use");
        while (true) {
            try {
                choice = Integer.parseInt(scan.nextLine());
                if (choice >= 1 && choice <= 6) break;
                else
                    System.out.println("Please choose an option from the list above");
            } catch (NumberFormatException e) {
                System.out.println("Please choose an option from the list above");
            }
        }
        return choice;
    }

    public static int getSize() {
        int size;
        System.out.println("How many numbers are we going to be sorting? (number between 10000 and 40000)");
        while (true) {
            try {
                size = Integer.parseInt(scan.nextLine());
                if (size >= 10_000 && size <= 40_000) break;
                else
                    System.out.println("Error! Please enter a number between 10000 and 40000");
            }
            catch (NumberFormatException e) {
                System.out.println("Error! Please enter a number between 10000 and 40000 (No commas)");
            }
        }
        return size;
    }

    public static void compareAllSorts(int size) {
        Sorts [] sortList = { new Bubble(size), new Selection(size), new Insertion(size),
                new Merge(size), new Quick(size) };

        System.out.println("      Sort Name  |  Time  |   Compares   ");
        System.out.println(" ----------------+--------+--------------");
        for (var sort : sortList){
            sort.sort();
        }
    }
}
