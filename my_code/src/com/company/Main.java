package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {

        String welcomeText = """
                    ======== Welcome to LockedMe.com =========
                    """;

        System.out.println(welcomeText);

        while (true) {

            String instructions = """
            ------------------------------------------
            Please enter one of the following options:
            1 - List Files
            2 - Interact with UI
            3 - Close Program
            ------------------------------------------
            Your choice:\t""" ;

            System.out.print(instructions);
            Scanner scanner = new Scanner(System.in);
            String user_input = scanner.nextLine();
            System.out.println();
            int choice;

            try {
                choice = Integer.parseInt(user_input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. You must enter a whole number.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    String currentWorkingDir = System.getProperty("user.dir");
                    Set<String> files = listFiles(currentWorkingDir);
                    if (files.isEmpty()) {
                        System.out.println("The current directory does not have any files in it.\n");
                        continue;
                    }
                    System.out.println("The current directory has the following files:");
                    files.stream().sorted(String.CASE_INSENSITIVE_ORDER).forEach(System.out::println);
                    break;
                case 2:
                    subOptionMenu();
                    break;
                case 3:
                    System.out.println("You have closed the program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Input. You must enter 1, 2 or 3.");
                    break;

            }
            System.out.println();
        }

    }

    private static void subOptionMenu() throws IOException {

        String subMenu = """
            Sub menu
            ------------------------------------------
            Please enter one of the following options:
            1 - Add file
            2 - Delete file
            3 - Search file
            4 - Main Menu
            ------------------------------------------
            Your choice:\t""" ;

        boolean exit = false;
        while (true) {

            System.out.print(subMenu);
            Scanner scanner = new Scanner(System.in);
            String user_input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(user_input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. You must enter a whole number.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    addFile();
                    break;
                case 2:
                    deleteFile();
                    break;
                case 3:
                    searchFile();
                    break;
                case 4:
                    mainMenu();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid Input. You must enter 1, 2, 3 or 4.");
                    break;
            }

            if (exit) {
                break;
            }

        }


    }

    private static void mainMenu() {
        System.out.println("You have chosen to return to the main menu");
    }

    private static void searchFile() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String fileName;
        while (true) {
            System.out.println("Note: The file must have the extension of doc, pdf, csv, xls, md, txt or iml.\n" +
                    "Please enter the name of the file to be found: ");
            fileName = scanner.nextLine();
            String strPattern = "^[a-zA-Z0-9_]+\\.(doc|pdf|csv|xls|md|txt|iml)$";
            if (!fileName.matches(strPattern)) {
                System.out.println("\nInvalid file name entered. Try again\n");
                continue;
            } else {
                break;
            }
        }


        String currentWorkingDir = System.getProperty("user.dir");
        Set<String> files = listFiles(currentWorkingDir);
        boolean notFound = true;
        for (String file : files) {
            if (file.equalsIgnoreCase(fileName)) {
                notFound = false;
                System.out.println(fileName + " is located in the current directory.\n");
                break;
            }
        }

        if (notFound) {
            System.out.println("File not found. " + fileName + " is not located in the current directory.\n");
        }

    }

    private static void deleteFile() {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Note: The file must have the extension of doc, pdf, csv, xls, md, txt or iml.\n" +
                    "Please enter the name of the file to be deleted: ");
            String fileName = scanner.nextLine();
            String strPattern = "^[a-zA-Z0-9]+\\.(doc|pdf|csv|xls|md|txt|iml)$";
            if (!fileName.matches(strPattern)) {
                System.out.println("\nInvalid file name entered. Try again\n");
                continue;
            }

            File stockFile = new File(fileName);
            if (stockFile.delete()) {
                System.out.println("The file named " + fileName + " was successfully deleted");

            } else {
                System.out.println("Error: file does not exist.");
            }

            break;

        }

    }



    private static void addFile() {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Note: The file added must have the extension of doc, pdf, csv, xls, md, txt or iml. \n" +
                    "Please enter the name of the file to be added: ");
            String fileName = scanner.nextLine();
            String strPattern = "^[a-zA-Z0-9]+\\.(doc|pdf|csv|xls|md|txt|iml)$";
            if (!fileName.matches(strPattern)) {
                System.out.println("\nInvalid file name entered. Try again!\n");
                continue;
            }

            File stockFile = new File(fileName);
            try {
                stockFile.createNewFile();
                break;
            } catch (IOException ioe) {
                System.out.println("File exists already. Try again!");
            }

        }

        System.out.println("The new file is now in the current directory");

    }

    private static Set<String> listFiles(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        }
        return fileList;
    }
}
