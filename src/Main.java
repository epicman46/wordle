import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    // ANSI escape codes for colors
    public static final String RESET = "\033[0m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String ORANGE_BACKGROUND = "\033[0;103m";

    // main method
    public static void main(String[] args) {

        String word = ""; // Variable to store the selected word from the file
        String playAgain; // Variable to track if the user wants to play again

        // Loop for playing the game multiple times
        do {
            // Try to read and select a random word from the wordleWords file
            try {
                File myObj = new File("wordleWords.txt");
                Scanner myReader = new Scanner(myObj);

                // Select a random word from the file
                for (int i = 0; i < (int) (2315 * Math.random()); i++) {
                    word = myReader.nextLine();
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            // Initialize the scanner for user input
            Scanner userInput = new Scanner(System.in);

            // Introduction and user input for their name
            System.out.println("Welcome to My wordle game");
            instructions();  // Call to instructions method
            example();       // Call to example method
            System.out.println("Please enter your name: ");
            String name = userInput.nextLine();
            String userGuess = "";

            // Loop to allow the user 6 guesses
            for (int i = 6; i > 0 && !userGuess.equals(word); i--) {
                System.out.println("You have " + i + " guesses remaining");
                userGuess = userInput.nextLine().toLowerCase();

                // While loop to validate if the word length is correct (5 characters)
                while (true) {
                    if (userGuess.length() == 5) {
                        break;  // If valid length, exit the loop
                    } else {
                        System.out.println("Invalid input, please enter a 5-letter word.");
                        userGuess = userInput.nextLine().toLowerCase();  // Get a valid word
                    }
                }

                // Check if the user-guessed word is in the word list
                if (!validityOfUserGuessedWord(userGuess)) {
                    System.out.println("This word is not part of the word list.");
                    i++;  // Give the user another chance without decrementing the guesses
                    continue;
                }

                // Show feedback to the user on their guess
                System.out.println(showWordColours(userGuess, word));
            }

            // Check if the user guessed correctly or ran out of guesses
            if (userGuess.equals(word)) {
                System.out.println("Congratulations " + name + "! You've guessed the word correctly.");
            } else {
                System.out.println("Sorry " + name + ", the word was " + word + ".");
            }

            // Ask if the user wants to play again
            System.out.println("Do you want to play again? (y/n)");
            playAgain = userInput.nextLine();

        } while (playAgain.equalsIgnoreCase("y"));

        System.out.println("Thanks for playing!");
    }

    // Method for showing instructions of the game
    public static void instructions() {
        // Example instructions
        System.out.println("The game will give you 6 chances to guess a 5-letter word.");
    }

    // Method for showing an example to the user
    public static void example() {
        // Example output
        System.out.println("Example: If the word is 'apple' and you guessed 'ample'.");
    }

    // Method to show colored feedback for the guessed word
    public static String showWordColours(String userGuess, String correctWord) {
        char[] userGuessArray = userGuess.toCharArray();
        char[] correctWordArray = correctWord.toCharArray();
        int[] initializing = new int[5];  // Initialize color indicators: 1 for correct, 2 for wrong position, 0 for incorrect

        // Loop to check and assign colors
        for (int x = 0; x < 5; x++) {
            if (userGuessArray[x] == correctWordArray[x]) {
                initializing[x] = 1;  // Correct letter in the correct position (Green)
            }
        }

        for (int x = 0; x < 5; x++) {
            if ((userGuessArray[x] == correctWordArray[0]) && (initializing[0] == 0)) {
                initializing[x] = 2;  // Correct letter in the wrong position (Orange)
            }
            // Additional checks for other positions...
        }

        // Create the string with colors
        StringBuilder coloursOfUserGuessWord = new StringBuilder();
        for (int j = 0; j < userGuessArray.length; j++) {
            if (initializing[j] == 1) {
                coloursOfUserGuessWord.append(GREEN_BACKGROUND).append(userGuessArray[j]).append(RESET);
            } else if (initializing[j] == 2) {
                coloursOfUserGuessWord.append(ORANGE_BACKGROUND).append(userGuessArray[j]).append(RESET);
            } else {
                coloursOfUserGuessWord.append(RED_BACKGROUND).append(userGuessArray[j]).append(RESET);
            }
        }
        return coloursOfUserGuessWord.toString();
    }

    // Method to check if the user-guessed word is in the word list
    public static boolean validityOfUserGuessedWord(String userInput) {
        boolean validity = false;
        try {
            File myObj = new File("wordleWords.txt");
            Scanner myReader = new Scanner(myObj);

            // Loop to check if the guessed word exists in the word list
            for (int i = 0; i < 2315; i++) {
                if (myReader.nextLine().equals(userInput)) {
                    validity = true;
                    break;  // Break loop if the word is found
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return validity;
    }
}
