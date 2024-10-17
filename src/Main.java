import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Main {

    // initializing variables for colours
    public static final String RESET = "\033[0m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String ORANGE_BACKGROUND = "\033[0;103m";



    // main method
    public static void main(String[] args) {


        String word = "";
        //read wordleWords

// variable for if the user wants to play again
        String playAgain;
        do{
// function for selecting a random word from the wordleWords file
            try {
                File myObj = new File("wordleWords.txt");
                Scanner myReader = new Scanner(myObj);
                for (int i = 0; i < (int)(2315 * Math.random()); i++) {
                    word = myReader.nextLine();
                    //assign word a random value
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

//initializing variable for scanner
            Scanner userInput = new Scanner(System.in);

// introduction
//System.out.println(word);
            System.out.println("Welcome to My wordle game");
            instructions();
            example();
            System.out.println("Please enter your name: ");
            String name = userInput.nextLine();
            String userGuess = "";


//for loop being used for keeping track of the user word guess
            for (int i = 6; i > 0 && !userGuess.equals(word); i--){
                System.out.println("You have " + i + " guesses remaining");
                userGuess = userInput.nextLine().toLowerCase();

// while loop being used for repeating the program if the word is longer or smaller than 5 characters
                while(true){
                    if (userGuess.length() != 5){
                        System.out.println("Your word must be 5 characters long\n");
                        System.out.println("You have " + i + " guesses remaining");
                        userGuess = userInput.nextLine().toLowerCase();

//process for if the user guesses the correct word
                    }else if (userGuess.equals(word)){
                        System.out.println(processing(userGuess, word));
                        System.out.println("Good job " + name + ", you won");
                        break;

// process for if the user inputs the incorrect word at the final guess
                    }else if (i == 1){
                        System.out.println(processing(userGuess,word));
                        System.out.println("HAHA you lost");
                        System.out.println("The correct word was: " + word);
                        break;

// process if word isn't part of word list
                    }else if (!validityOfUserGuessedWord(userGuess)){
                        System.out.println("Not part of word list");
                        System.out.println("you have " + i + " guesses remaining\n");
                        userGuess = userInput.nextLine().toLowerCase();


//process for if the user guess length is 5, and it is a valid word
                    }else if (validityOfUserGuessedWord(userGuess)) {
                        System.out.println(processing(userGuess, word));
                        System.out.println();
                        break;
                    }

                }

            }
            System.out.println("Do you wish to play again? (y). (Type any other letter to terminate)");
            playAgain = userInput.next();
            if (playAgain.equalsIgnoreCase("n")){
                break;
            }

            userInput.close();

        } while (playAgain.equalsIgnoreCase("y"));


    }
//end of main method

    //method of instructions
    public static void instructions (){
        System.out.println();
        System.out.println("Guess the WORDLE in 6 tries");
        System.out.println();
        System.out.println("Each guess must be a valid 5 letter word. Hit the enter button to submit");
        System.out.println();
        System.out.println("After each guess, the color of the tiles will change to show how close your guess was to the word.");

//end of instruction
    }

    // method of examples
    public static void example (){

//correct letter and correct spot example
        String word1 = "weary";
        System.out.println();
        System.out.println("Examples");
        System.out.println();
        System.out.println(GREEN_BACKGROUND + word1.charAt(0) + RESET + word1.substring(1,5));

        System.out.println("The letter W is part of the word and is in the correct spot");

//correct letter but incorrect spot example
        String word2 = "pills";
        System.out.println();
        System.out.println();
        System.out.println(word2.charAt(0) + ORANGE_BACKGROUND + word2.charAt(1) + RESET + word2.substring(2,5));

        System.out.println("The letter I is part of the word but it isn't in its correct spot");

//incorrect letter example
        String word3 = "vague";
        System.out.println();
        System.out.println();
        System.out.println(word3.substring(0,3) + RED_BACKGROUND + word3.charAt(3) + RESET + word3.charAt(4));

        System.out.println("The letter U is not part of the word");
    }
//end of method examples


    // word processing method
    public static String processing (String arrOfUserGuess, String arrOfCorrectWord){

//converting correct word and user guess strings to char arrays
        char [] userGuessArray = arrOfUserGuess.toCharArray();
        char [] correctWordArray = arrOfCorrectWord.toCharArray();

// initializing the array
        int[] initializing = {0, 0, 0, 0, 0};
        for (int i = 0; i < 5; i++){
            if (userGuessArray[i] == correctWordArray[i]){
                initializing[i] = 1;
            }
        }

        for (int x = 0; x < 5; x++){
            if ((userGuessArray[x] == correctWordArray[0]) && (initializing[0] == 0)) {
                initializing[x] = 2;
            }
            if ((userGuessArray[x] == correctWordArray[1]) && (initializing[1] == 0)) {
                initializing[x] = 2;
            }
            if ((userGuessArray[x] == correctWordArray[2]) && (initializing[2] == 0)) {
                initializing[x] = 2;
            }
            if ((userGuessArray[x] == correctWordArray[3]) && (initializing[3] == 0)) {
                initializing[x] = 2;
            }
            if ((userGuessArray[x] == correctWordArray[4]) && (initializing[4] == 0)) {
                initializing[x] = 2;
            }

        }


        StringBuilder coloursOfUserGuessWord = new StringBuilder();
        for (int j = 0; j < arrOfUserGuess.length(); j++) {
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

    //method for checking if the word is part of word list
    public static boolean validityOfUserGuessedWord(String userInput){
        boolean validity = false;
        try {
            File myObj = new File("wordleWords.txt");
            Scanner myReader = new Scanner(myObj);
            for (int i = 0; i < 2315; i++) {
                if(myReader.nextLine().equals(userInput)){
                    validity = true;
                    break;
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
