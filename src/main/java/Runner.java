import java.util.Scanner;

public class Runner {
    private static int DIVIDEND;
    private static int DIVISOR;


    public static void main(String[] args) {

        System.out.println("Dividend and divisor must be integer!!!");
        System.out.println("Enter the dividend:");
        Scanner scn = new Scanner(System.in);
        DIVIDEND = scn.nextInt();

        System.out.println("Enter the divisor:");
        DIVISOR = scn.nextInt();

        Division division = new Division(DIVIDEND, DIVISOR);
        division.printDivision();

        System.out.println();
    }
}

