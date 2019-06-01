package pl.coderslab.lotto;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/*
    Jak wszystkim wiadomo, LOTTO to gra liczbowa polegająca na losowaniu 6 liczb z zakresu od 1 do 49.
    Zadaniem gracza jest poprawne wytypowanie losowanych liczb. Nagradzane jest trafienie 3, 4, 5 lub 6
    poprawnych liczb.
    Napisz program, który:
    zapyta o typowane liczby, przy okazji sprawdzi następujące warunki:
    czy wprowadzony ciąg znaków jest poprawną liczbą,
    czy użytkownik nie wpisał tej liczby już poprzednio,
    czy liczba należy do zakresu 1-49,
    po wprowadzeniu 6 liczb, posortuje je rosnąco i wyświetli na ekranie,
    wylosuje 6 liczb z zakresu i wyświetli je na ekranie,
    poinformuje gracza, czy trafił przynajmniej "trójkę".
 */
public class Main {

    public static void main(String[] args) {
        int[] lotteryNums = fillNumsArray();
        int[] userNums = getNums();
        int counter = checkFortune(userNums, lotteryNums);
        printInf(lotteryNums, userNums, counter);
    }

    // lottery
    private static int[] fillNumsArray(){
        int[] nums = new int[6];

        // fill the array
        for (int i = 0; i < nums.length; i++) {
            // random num
            nums[i] = randNum();

            // check if this num was drawn earlier
            for (int j = 0; j < i; j++) {
                // if it was: draw again
                if (nums[j] == nums[i])
                    i--;
            }
        }

        return nums;
    }

    // random nums
    private static int randNum(){
        Random lottery = new Random();
        return lottery.nextInt(49)+1;
    }

    // getting nums from user
    private static int[] getNums(){
        int[] userNums = new int[6];
        System.out.println("LOTTERY");
        System.out.println("Guess the numbers from lottery!");

        for (int i = 0; i < userNums.length; i++) {
            System.out.print((i+1) + ". number: ");
            userNums[i] = rightNumber(userNums, i);
        }

        return userNums;
    }

    // check conditions
    private static int rightNumber(int[] userNums, int counter){
        Scanner input = new Scanner(System.in);
        int num;

        while(true){

            // check if user typed a number
            while(!input.hasNextInt()){
                input.nextLine();
                System.out.println("It's not a number! Go again!");
                System.out.print((counter+1) + ". number: ");
            }
            num = input.nextInt();

            // check if number is in the range
            if (num < 1 || num > 49){
                System.out.println("This number is out of range! Go again!");
                System.out.print((counter+1) + ". number: ");
                continue;
            }

            // check if user typed this number earlier
            boolean found = false;
            for (int i = 0; i < counter; i++) {
                if (num == userNums[i]){
                    System.out.println("You typed this number earlier! Go again!");
                    System.out.print((counter+1) + ". number: ");
                    found = true;
                    break;
                }
            }

            if (!found)
                break;
        }

        return num;
    }

    // check fortune
    private static int checkFortune(int[] userNums, int[] lotteryNums){
        int counter = 0;

        for (int i = 0; i < userNums.length; i++) {
            for (int j = 0; j < lotteryNums.length; j++) {
                if (userNums[i] == lotteryNums[j])
                    counter++;
            }
        }

        return counter;
    }
    
    // print the result
    private static void printInf(int[] lotteryNums, int[] userNums, int counter){
        Arrays.sort(userNums);
        Arrays.sort(lotteryNums);
        System.out.println("Your nums: " + Arrays.toString(userNums));
        System.out.println("Lottery nums: " + Arrays.toString(lotteryNums));
        switch(counter){
            case 0:
            case 1:
            case 2:
                System.out.println("You loose!");
                break;
            case 3:
            case 4:
            case 5:
                System.out.println("Yes! You hit the " + counter + "!");
                break;
            case 6:
                System.out.println("WOW! YOU ARE LUCK! YOU HIT THE BANK!");
                break;
        }
    }
}
