package pl.coderslab.dice;

public class Main {

    public static void main(String[] args) {
        startInf();
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
}
