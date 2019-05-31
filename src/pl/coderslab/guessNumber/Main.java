package pl.coderslab.guessNumber;

/*
 * Napisz prostą grę w zgadywanie liczb. Komputer ma wylosować liczbę w zakresie od 1 do 100.
 *
 * Następnie:
 * 1. wypisać: "Zgadnij liczbę" i pobrać liczbę z klawiatury;
 * 2. sprawdzić, czy wprowadzony napis, to rzeczywiście liczba i w razie błędu wyświetlić komunikat: "To
 * nie jest liczba", po czym wrócić do pkt. 1;
 * 3. jeśli liczba podana przez użytkownika jest mniejsza niż wylosowana, wyświetlić komunikat: "Za
 * mało!", po czym wrócić do pkt. 1;
 * 4. jeśli liczba podana przez użytkownika jest większa niż wylosowana, wyświetlić komunikat: "Za dużo!",
 * po czym wrócić do pkt. 1;
 * 5. jeśli liczba podana przez użytkownika jest równa wylosowanej, wyświetlić komunikat: "Zgadłeś!", po
 * czym zakończyć działanie programu.
 */

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // range of lottery
        int num1 = 1;
        int num2 = 100;

        // random nums
        int number = randomNum(num1, num2);

        // guessing
        boolean win = lessMore(number, num1, num2);

        // print the information
        if(win)
            System.out.print("\nYou win! Bye!");
        else
            System.out.print("\nYou stoped the game. Bye!");
    }

    // random nums
    private static int randomNum(int bound1, int bound2){
        Random lottery = new Random();
        return lottery.nextInt(100)+1;
    }

    // guessing
    private static boolean lessMore(int rightNumber, int bound1, int bound2){
        // basic information for a user
        System.out.print("I drew a number from the range ");
        System.out.println(bound1 + " - " + bound2);
        System.out.println("Guess the number!");
        System.out.println("(if you want to stop the game type a 0)\n");

        // variable for a guess
        int guess;

        // loop
        do {
            guess = getNum();
            if (guess == 0)
                return false;
            else if (guess < rightNumber)
                System.out.println("Too small! Let's try again!");
            else if (guess > rightNumber)
                System.out.println("Too large! Let's try again!");
        } while (guess != rightNumber);

        return true;
    }

    // get num from user
    private static int getNum (){
        Scanner scan = new Scanner(System.in);
        System.out.print("Your guess: ");

        // while user is typing wrong data...
        while (!scan.hasNextInt()){
            scan.nextLine();
            System.out.println("It's not a number! Try again: ");
        }
        return scan.nextInt();
    }
}
