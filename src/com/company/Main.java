package com.company;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                System.out.println("Invalid Input. You must enter a number.\n");
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
                    choice = subOptionMenu();
                    subOption();
                    user_input = scanner.nextLine();
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

    private static String subOptionMenu() {

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

//        Scanner scanner = new Scanner(System.in);
//        String user_input = scanner.nextLine();
//        try {
//            choice = Integer.parseInt(user_input);
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid Input. You must enter a number.\n");
//            continue;
//        }
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
