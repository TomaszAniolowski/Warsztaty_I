package pl.coderslab.guessNumber2_guessingByComputer;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        startInf();
        boolean guessed = guessing(1, 1000);
        finalInf(guessed);
    }

    // start the game
    private static void startInf() {
        System.out.println("HELLO!");
        System.out.println("Think a number 1-1000 and I will guess it in max 10 moves");
        System.out.println("Please give me the answers Y(yes) or N(no)");
        System.out.println("If you want to interrupt, type 0.");
        System.out.println("Let's rock!");
    }

    // guessing
    private static boolean guessing(int min, int max){
        // variables for answers
        char win;
        char tooSmall;
        char tooLarge;

        // variable for checking if user made a mistake or cheating.
        int lastGuess = 0;

        // loop
        while (true){
            // computer guess
            int guess = ((max-min)/2 )+min;

            // if guess is this same as previous: somewhere user typed incorrect answer
            if(lastGuess == guess){
                System.out.println("You was cheatting somewhere (or just made a mistake)!");
                return false;
            }

            // guessing
            System.out.print("My guess is " + guess + ". ");

            // getting an answer (with question as param to repeat it if user typed incorrect char)
            win = getAnswer("Did I win? ");

            // Did i win?
            if (win == 'Y')
                return true;
            else if (win == '0')
                return false;

            // Too large?
            tooLarge = getAnswer("Too large? ");
            if (tooLarge == '0')
                return false;

            // if it is too large change the range and save this guess as lastGuess
            else if (tooLarge == 'Y') {
                max = guess;
                lastGuess = guess;
                continue;
                // if not
            } else{

                // Too small?
                tooSmall = getAnswer("Too small? ");
                if (tooSmall == '0')
                    return false;

                // if not win, not tooLarge and not tooSmall: user cheat!
                else if (tooSmall == 'N')
                    System.out.println("Do not cheat!");

                    // if it is too small change the range and save this guess as lastGuess
                else {
                    min = guess;
                    lastGuess = guess;
                    continue;
                }
            }
        }

    }

    // getting an answer
    private static char getAnswer(String question){
        // input
        Scanner input = new Scanner(System.in);

        // loop
        while (true){
            System.out.print(question);
            String answer = input.nextLine().toUpperCase();

            // if user typed smth another than y, n or 0: loop is not break
            if(!answer.equals("Y") && !answer.equals("N") && !answer.equals("0")) {
                System.out.println("Answer Y/N (or 0 if you want to stop)!");
                continue;
            } else
                return answer.charAt(0);
        }
    }

    // finish the game
    private static void finalInf(boolean guessed){
        if (guessed)
            System.out.println("Hooray!\nThank you, Bye!");
        else
            System.out.println("The fun is over! Bye!");
    }

}
