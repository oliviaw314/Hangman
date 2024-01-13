import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main (String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("/Users/olivia/Downloads/HangmanWordsList.txt"));
        List<String> words = new ArrayList<>();

        int badGuessesCount = 0;
        int goodGuessesCount = 0;

        while (scanner.hasNext()) {
            words.add(scanner.nextLine());
        }
        Random rand = new Random();
        String word = words.get(rand.nextInt(words.size()));

        List<Character> playerGuesses = new ArrayList<>();
        System.out.println("Welcome to Hangman! *Note: If you want to guess the full word at any time, press 1, if you want to have a hint at any time, press 2 (you only have 3 hints available)*");
        StringBuilder newWord = new StringBuilder();
        for (int i=0; i<word.length(); i++) {
            newWord.append("_");
        }
        System.out.println("Your word is: "+newWord);
        everything(word, playerGuesses, badGuessesCount, newWord, goodGuessesCount);
    }
    public static void everything(String word, List<Character> playerGuesses, int badGuessesCount, StringBuilder newWord, int goodGuessesCount) throws FileNotFoundException {
        int hintUses = 0;
        while (8 >= badGuessesCount) {
            System.out.println("Please enter a letter:");
            Scanner keyboard = new Scanner(System.in);
            String guessLetter = keyboard.nextLine().toUpperCase();

            if (guessLetter.equals("1")) {
                System.out.println("Please enter the full word!");
                String wordGuess = keyboard.nextLine().toUpperCase();
                if (wordGuess.equals(word)) {
                    System.out.println("Congratulations! You guessed the word");
                    System.out.println("The word is " + word);
                    System.exit(0);
                } else {
                    System.out.println("Sorry, that's not the word.");
                    badGuessesCount++;
                    System.out.println(updateDashes(word, playerGuesses, newWord));
                }
            }
            else if (guessLetter.equals("2")) {
                if (hintUses<=3) {
                    hintUses++;
                    System.out.println("Would you like to guess a number from 1-5 (press 1) or solve a math question (press 2)?");
                    System.out.println("You only get the hint if you get this correct!");
                    int userInput = keyboard.nextInt();
                    if (userInput==1) {
                        Random rand = new Random();
                        int number = rand.nextInt(5)+1;
                        System.out.println("Guess the number");
                    if (keyboard.nextInt()==number) {
                        System.out.println("You guessed it!");
                        System.out.println(hint(playerGuesses, word));
                        System.out.println(updateDashes(word, playerGuesses, newWord));
                    }
                    else {
                        System.out.println("Sorry, that's incorrect.");
                        System.out.println(updateDashes(word, playerGuesses, newWord));
                    }
                }
                    if (userInput==2) {
                        Random rand = new Random();
                        int number = rand.nextInt(20);
                        int number2 = rand.nextInt(20);
                        System.out.println("What's " + number + " times " + number2 + "?");
                        if (keyboard.nextInt() == number * number2) {
                            System.out.println("Correct!");
                            System.out.println(hint(playerGuesses, word));
                            System.out.println(updateDashes(word, playerGuesses, newWord));
                        } else {
                            System.out.println("Sorry, that's incorrect.");
                            System.out.println(updateDashes(word, playerGuesses, newWord));
                        }
                    }
                }
                else {
                    System.out.println("Sorry, you used up all of your attempts to get a hint.");
                }
            }
            else {
                playerGuesses.add(guessLetter.charAt(0));
                int rightLetterOrNot = 0;
                for (int i = 0; i < word.length(); i++) {
                    if (guessLetter.equals(word.substring(i, i + 1))) {
                        int guessesLeft = 9-badGuessesCount;
                        System.out.println("That letter is in the word! You have "+ guessesLeft+" guesses left.");
                        System.out.println(updateDashes(word, playerGuesses, newWord));
                        goodGuessesCount++;
                        rightLetterOrNot++;
                        break;
                    }
                }
                if (rightLetterOrNot == 0) {
                    badGuessesCount++;
                    int guessesLeft = 9-badGuessesCount;
                    System.out.println("Sorry, this letter is not in the word. You have "+ guessesLeft+" guesses left.");
                    System.out.println("Hangman:");
                    System.out.println(printHangman(badGuessesCount));
                    if (8>=badGuessesCount) {
                        System.out.println(updateDashes(word, playerGuesses, newWord));
                    }
                }
            }
        }
        System.out.println("The word was " + word);
        System.exit(0);
    }


    public static String updateDashes(String word, List<Character> playerGuesses, StringBuilder newWord) {
        newWord.setLength(0);
        for (int i = 0; i < word.length(); i++) {
            if (playerGuesses.contains(word.charAt(i))) {
                    newWord.append(word.charAt(i));
            } else {
                newWord.append("_");
            }
        }
        return "This is the word now: "+newWord;
    }

    public static StringBuilder printHangman(int badGuessesCount) {
        StringBuilder stringBuilder = new StringBuilder();
        if (badGuessesCount==1) {
            stringBuilder.append("-------\n");
            stringBuilder.append("|      \n");
        }
        if (badGuessesCount==2) {
            stringBuilder.append("-------\n");
            stringBuilder.append("|      |\n");
        }
        if (badGuessesCount==3) {
            stringBuilder.append("-------\n");
            stringBuilder.append("|      |\n");
            stringBuilder.append("       0\n");
        }
        if (badGuessesCount==4) {
            stringBuilder.append("-------\n");
            stringBuilder.append("|      |\n");
            stringBuilder.append("       0\n");
            stringBuilder.append("       |\n");
        }
        if (badGuessesCount==5) {
            stringBuilder.append("-------\n");
            stringBuilder.append("|      |\n");
            stringBuilder.append("     \\ 0\n");
            stringBuilder.append("       |\n");
        }
        if (badGuessesCount==6) {
            stringBuilder.append("-------\n");
            stringBuilder.append("|      |\n");
            stringBuilder.append("     \\ 0 /\n");
            stringBuilder.append("       |\n");
        }
        if (badGuessesCount==7) {
            stringBuilder.append("-------\n");
            stringBuilder.append("|      |\n");
            stringBuilder.append("     \\ 0 /\n");
            stringBuilder.append("       |\n");
            stringBuilder.append("       |\n");
            stringBuilder.append("Don't let him die!\n");
        }
        if (badGuessesCount==8) {
            stringBuilder.append("-------\n");
            stringBuilder.append("|      |\n");
            stringBuilder.append("     \\ 0 /\n");
            stringBuilder.append("       |\n");
            stringBuilder.append("      / \n");
            stringBuilder.append("He's dying....\n");
        }
        if (badGuessesCount==9) {
            stringBuilder.append("-------\n");
            stringBuilder.append("|      |\n");
            stringBuilder.append("     \\ 0 /\n");
            stringBuilder.append("       |\n");
            stringBuilder.append("      / \\\n");
            stringBuilder.append("Oops! You killed him.");
        }
        return stringBuilder;
    }

    public static String hint(List<Character> playerGuesses, String word) {
        char hint = ' ';
        for (int i=0; i<word.length(); i++) {
            if (!playerGuesses.contains(word.charAt(i))) {
                hint = word.charAt(i);
                break;
            }
        }
        playerGuesses.add(hint);
        return "One of the letters are "+hint;
    }
}
