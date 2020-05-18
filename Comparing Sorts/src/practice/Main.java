package practice;

import java.net.SecureCacheResponse;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static String [] sortNames = {"Bubble", "Selection", "Insertion", "Merge", "Quick", "Compare all sorts"};
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        boolean compare = false;
        displayMenu();
        int size = getSize();
        int choice = getSort() - 1;
        //list of all the sorts for ease of access
        Sorts [] sortList = { new Bubble(size), new Selection(size), new Insertion(size),
                new Merge(size), new Quick(size) };

        if (choice != 5) {
            //call the correct sorting algorithm
            System.out.println(sortList[choice].getName() + " sort took " + sortList[choice].sort()
                    + " seconds to correctly sort " + size + " numbers!");
        }
        else compareAllSorts(sortList);
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
        System.out.println("How many numbers are we going to be sorting? (number between 10000 and 100000)");
        while (true) {
            try {
                size = Integer.parseInt(scan.nextLine());
                if (size >= 10_000 && size <= 100_000) break;
                else
                    System.out.println("Error! Please enter a number between 10000 and 100000");
            }
            catch (NumberFormatException e) {
                System.out.println("Error! Please enter a number between 10000 and 100000 (No commas)");
            }
        }
        return size;
    }

    public static void compareAllSorts(Sorts [] sortList) {
        System.out.println(" |    Sort Name   |   Time   |   Compares   ");
        System.out.println(" +----------------+----------+--------------");
        for (var sorts : sortList){
            String sortTime = Double.toString(sorts.sort());
            System.out.println(" | " + sorts.getName() + getSpacing(sorts.getName(), 15) + "|  " +
                    sortTime + getSpacing(sortTime, 8) + "|   " + sorts.getCompares());
        }
    }

    //small function get the spacing correct for the table
    public static String getSpacing(String name, int baseLength) {
        String spacing = "";
        int spaceLength = baseLength - name.length();

        for (int i = 0; i < spaceLength; i++) {
            spacing += " ";
        }
        return spacing;
    }
}
