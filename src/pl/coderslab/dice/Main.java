package pl.coderslab.dice;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        startInf();
        int[] parameters = getParam();
        diceRollsInf(parameters);
        int result = diceRolls(parameters);
        System.out.println(result);
    }

    // start informations
    private static void startInf(){
        System.out.println("DICE");
        System.out.println("Please enter the parameters:");
        System.out.println("x - the number of dice rolls (if it is 1, skip it)");
        System.out.println("y - the number of walls of the dice (can be: D3, D4, D6, D8, D10, D12, D20, D100)");
        System.out.println("z - (optional) modification of a dice rolls (e.g. +15, -3)");
        System.out.println("\nRequired format:");
        System.out.println("xDy+z\n");
    }

    // get the parameters
    private static int[] getParam(){
        // parameters array
        int[] parameters = new int[3];

        // input
        Scanner input = new Scanner(System.in);

        // loop
        while(true) {
            System.out.print("Parameters: ");
            String line = input.nextLine();
            boolean formatCorr = checkFormatCorr(line);
            if (!formatCorr)
                continue;


            int indexD = line.indexOf('D');
            int indexPlus = line.indexOf('+');
            int indexMinus = line.indexOf('-');

            if ( (indexD == 0) && (indexPlus < 0) && (indexMinus < 0) ){
                optDy(parameters, line);
                break;
            } else if ( (indexPlus < 0) && (indexMinus < 0) ){
                optxD(parameters, line);
                break;
            } else if( (indexD == 0) ){
                parameters[0] = 1;
                line = line.replaceAll("D", "");
                if (indexMinus < 0) {
                    optExistZ(parameters, line, "\\+");
                    break;
                }
                else if(indexPlus < 0){
                    optExistZ(parameters, line, "-");
                    break;
                }
            } else {
                String[] lineParts = line.split("D");
                parameters[0] = Integer.parseInt(lineParts[0]);

                if ( (indexMinus < 0) ){
                    optExistZ(parameters, lineParts[1], "\\+");
                    break;
                } else if ( (indexPlus < 0) ){
                    optExistZ(parameters, lineParts[1], "-");
                    break;
                }
            }
        }

        return parameters;
    }

    // check correctness of data-format
    private static boolean checkFormatCorr(String line){
        if (line.indexOf('D') < 0){
            System.out.println("incorrect format! Must be xDy+z");
            return false;
        }


        try {
            String correct = line.replaceFirst("D", "");
            correct = correct.replaceFirst("\\+", "");
            correct = correct.replaceFirst("-", "");
            Integer.parseInt(correct);
        } catch (NumberFormatException e) {
            System.out.println("incorrect format! Must be xDy+z");
            return false;
        }

        try {
            Integer.parseInt(   String.valueOf(  line.charAt( line.indexOf('D') + 1 )  )   );
        } catch (NumberFormatException num) {
            System.out.println("incorrect format! Must be xDy+z");
            return false;
        } catch (StringIndexOutOfBoundsException oob){
            System.out.println("incorrect format! Must be xDy+z");
            return false;
        }
        return true;
    }

    // getParam: option without x and without z
    private static void optDy (int[] parameters, String line){
        parameters[0] = 1;
        parameters[1] = Integer.parseInt( line.replace("D", "") );
        parameters[2] = 0;
    }

    // getParam: option with x and without z
    private static void optxD (int[] parameters, String line){
        String[] lineParts = line.split("D");
        parameters[0] = Integer.parseInt(lineParts[0]);
        parameters[1] = Integer.parseInt(lineParts[1]);
        parameters[2] = 0;
    }

    // getParam: option with z
    private static void optExistZ (int[] parameters, String line, String z){
        String[] lineParts = line.split(z);
        parameters[1] = Integer.parseInt(lineParts[0]);
        if (z.equals("\\+"))
            parameters[2] = Integer.parseInt(lineParts[1]);
        else if ( z.equals("-") )
            parameters[2] = -Integer.parseInt(lineParts[1]);
    }

    // print retrieved information
    private static void diceRollsInf (int[] parameters){
        System.out.println();
        System.out.println("Number of dice rolls: " + parameters[0]);
        System.out.println("Number of dice walls: " + parameters[1]);
        System.out.println("Optional value to result: " + parameters[2]);
    }

    // dice rolls
    private static int diceRolls(int[] parameters){
        int result = 0;
        for (int i = 0; i < parameters[0]; i++) {
            result += oneDiceRoll(parameters[1], i+1);
        }
        System.out.println(parameters[2]);
        return result + parameters[2];
    }

    // single dice roll
    private static int oneDiceRoll(int walls, int counter){
        Random diceRoll = new Random();
        int roll = diceRoll.nextInt(walls)+1;
        System.out.println("Dice roll " + counter + ": " + roll);
        return roll;
    }

}
