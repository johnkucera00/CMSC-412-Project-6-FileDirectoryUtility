/*
* File: Main.java
* Author: John Kucera
* Date: 2/19/2021
* Purpose: This Java program is designed to be a file/directory processing
* utility. It provides the user with options to exit the program, select
* a directory, list directory content of either first level or all levels,
* delete a file, display a file's hexadecimal view, encrypt a file,
* or decrypt a file.
*/

// importing necessary Java classes
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

// Main class
public class Main {
  // Initializing variables
  File[] directory;
  String directName = null;

  // Constructor for Main class
  public Main() {
    int menuSelection = 0;
    Scanner input = new Scanner(System.in);
    String inputFile;
    String inputFile2;
    String inputPassword;

    // Print welcome message
    System.out.println("\n****** Welcome to the Java File/Directory Utility! ******");
    
    // Loop menu after each task
    while (true) {
      try {
        // Print menu, scan in user selection
        System.out.print("\n0 - Exit\n" +
                        "1 - Select directory\n" +
                        "2 - List directory content (first level)\n" +
                        "3 - List directory content (all levels)\n" +
                        "4 - Delete file\n" +
                        "5 - Display file (hexadecimal view)\n" +
                        "6 - Encrypt file (XOR with password)\n" +
                        "7 - Decrypt file (XOR with password)\n" +
                        "Select option:\n\n");
        menuSelection = Integer.parseInt(input.nextLine().trim());
        
        // Perform task based on user selection
        switch (menuSelection) {
          // 0: Exit
          case 0:
            input.close();
            System.out.println("\nExiting program.");
            System.exit(0);
          
          // 1: Select Directory
          case 1:
            // Prompt user for directory name, use selectDirectory method
            System.out.println("\nPlease enter Directory Name: \n");
            inputFile = input.nextLine().trim();
            selectDirectory(inputFile);
            break;

          // 2: List directory content (first level)
          case 2:
            if (directorySelected()) {
              listLv1Content(directory);
            } // end of if
            break;

          // 3: List directory content (all levels)
          case 3:
            if (directorySelected()) {
              listAllContent(directory);
            } // end of if
            break;

          // 4: Delete file
          case 4:
            if (directorySelected()) {
              // Prompt user for file name, use deleteFile method
              System.out.println("\nPlease enter File Name: \n");
              inputFile = input.nextLine().trim();
              deleteFile(directName, inputFile);
            } // end of if
            break;
          
          // 5: Display File (hexadecimal view)
          case 5:
            if (directorySelected()) {
              // Prompt user for file name, use displayHex method
              System.out.println("\nPlease enter File Name: \n");
              inputFile = input.nextLine().trim();
              displayHex(directName, inputFile);
            } // end of if
            break;
          
          // 6: Encrypt file (XOR with Password)
          case 6:
            if (directorySelected()) {
              // Prompt user for password, then File Name, then use xor method
              System.out.println("\nPlease enter a password: \n");
              inputPassword = input.nextLine().trim();
              System.out.println("\nPlease enter the Name of the File whose contents you want to Encrypt: \n");
              inputFile = input.nextLine().trim();
              System.out.println("\nPlease enter the Name of the File you want the Encrypted Contents in: \n");
              inputFile2 = input.nextLine().trim();
              xor(directName, inputPassword, inputFile, inputFile2);
            } // end of if
            break;
          
          // 7: Decrypt file (XOR with Password)
          case 7:
            if (directorySelected()) {
              // Prompt user for password, then File Name, then use xor method
              System.out.println("\nPlease enter a password: \n");
              inputPassword = input.nextLine().trim();
              System.out.println("\nPlease enter the Name of the File whose contents you want to Decrypt: \n");
              inputFile = input.nextLine().trim();
              System.out.println("\nPlease enter the Name of the File you want the Decrypted Contents in: \n");
              inputFile2 = input.nextLine().trim();
              xor(directName, inputPassword, inputFile, inputFile2);
            } // end of if
            break;
          
          // Invalid Input
          default:
            System.out.println("\nInvalid input. Only integers 1 to 7 are accepted. Please try again.");
            break;
        } // end of switch
      } // end of try
      catch (NumberFormatException e) {
        System.out.println("\nInvalid input. Only integers 1 to 7 are accepted. Please try again.");
      } // end of catch
    } // end of while
  } // end of method
  
  // directorySelected method: determines if a directory has been selected
  public boolean directorySelected() {
    boolean selected = false;
    if (directName != null) { // directory has been selected
      selected = true;
    } // end of if
    else { // directory has not been selected, print message
      System.out.println("\nNo directory selected.");
    } // end of else
    return selected;
  } // end of method

  // selectDirectory method: for Option 1
  public void selectDirectory(String inputDirectory){
    File testIfDirectory = new File(inputDirectory);

    // Input directory exists
    if (testIfDirectory.isDirectory()) {
      directory = new File(inputDirectory).listFiles();
      directName = inputDirectory;
      System.out.println("\nDirectory " + directName + " selected.");
    } // end of if

    // Input directory does not exist
    else {
      System.out.println("\nDirectory " + inputDirectory + " not found.");
    } // end of else
  } // end of method

  // listLv1Content method: for Option 2
  public void listLv1Content(File[] contents) {
    System.out.println("\nListing files and subdirectories from first level of " + directName + ":\n");

    // Print contents of directory, labeling subdirectories and files accordingly
    for (File file : contents) {
      if (file.isDirectory()) { // Subdirectory
        System.out.println("Subdirectory: " + file.getName());
      } // end of if
      else { // File
        System.out.println("File: " + file.getName());
      } // end of else
    } // end of for
  } // end of method

  // listAllContent method: for Option 3
  public void listAllContent(File[] contents) {
    // Only display this message in the beginning of list
    if (contents == directory) {
      System.out.println("\nListing files and subdirectories from all levels of " + directName + ":\n");
    } // end of if

    // Print contents of directory, labeling subdirectories and files accordingly
    for (File file : contents) {
      if (file.isDirectory()) { // Subdirectory
        System.out.println("Subdirectory: " + file.getName());
        listAllContent(file.listFiles()); // Call the same method on subdirectory
      } // end of if
      else { // File
        System.out.println("File: " + file.getName());
      } // end of else
    } // end of for
  } // end of method
  
  // deleteFile method: for Option 4
  public void deleteFile(String directoryName, String inputFile) {
    String fileName = inputFile;
    File file = new File(".//" + directoryName + "//" + fileName);

    // Deleting file
    if (file.delete()) {
      System.out.println("\n" + file.getName() + " deleted.");
    } // end of inner if
    else {
      System.out.println("\nFile could not be found or deleted.");
    } // end of else
  } // end of method

  // displayHex method: for Option 5
  public void displayHex(String directory, String inputFile) {
    // Initialization
    String fileName = inputFile;
    int streamRead = 0;
    int currentCount = 0;
    int total = 0;

    try {
      // Creating an input stream for the file
      FileInputStream stream = new FileInputStream(".//" + directory + "//" + fileName);
      
      // Data offset
      System.out.print(String.format("\n%08X", total) + ": ");

      // Using Stringbuilder to print hexadecimal values
      while ((streamRead = stream.read()) != -1) {
        StringBuilder streamsb = new StringBuilder();
        streamsb.append(String.format("%02X ", streamRead));
        System.out.print(streamsb.toString());
        currentCount++;
        total++;
        
        // Go to next line after 16 hexadecimal values, print data offset
        if (currentCount == 16) {
          currentCount = 0;
          System.out.print("\n" + String.format("%08X", total) + ": ");
        } // end of if
      } // end of while
      System.out.print("\n");
      stream.close();
    } // end of try
    catch (IOException e) {
      System.out.println("\nIOException: Input File not found or unreadable.");
    } // end of catch       
  } // end of method

  // xor method: for Options 6 and 7
  public void xor(String directory, String inputPw, String inputF, String inputF2) {
    // Initialization
    String password = inputPw;
    String file1Name = inputF;
    String file2Name = inputF2;
    String after = "";

    try {
      // Encrypting or Decrypting using the password
      Path filePath = Paths.get(".//" + directory + "//" + file1Name);
      byte[] before = Files.readAllBytes(filePath); 
      int len = before.length;
      for (int i = 0; i < len; i++) {
        int c = i % password.length();
        after += Character.toString((char)(before[i] ^ password.charAt(c))); 
      } // end of for
      
      // Write to other file
      FileWriter fileWriter = new FileWriter(".//" + directory + "//" + file2Name);
      fileWriter.write(after);
      fileWriter.close();

      // Print message
      System.out.println("\nXOR Cipher has been performed on " + inputF + ". The results have been placed in " + inputF2 + ".");
    } // end of try
    catch (IOException e) {
      System.out.println("\nIOException: Input File(s) not found or unreadable.");
    } // end of catch   
  } // end of method

  // main method, calls Main constructor
  public static void main(String[] args) {    
    Main menu = new Main();
  } // end of main method
} // end of class