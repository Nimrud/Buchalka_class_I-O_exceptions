package m01_exceptions;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int x = getIntLBYL();
        System.out.println("Podana liczba to: " + x);
    }

    // LBYL (Look Before You Leap) - jedno z podejść w programowaniu,
    // polega na wcześniejszym sprawdzaniu warunków, a dopiero potem wykonywaniu operacji
    private static int getIntLBYL(){
        Scanner s = new Scanner(System.in);
        boolean isValid = true;
        System.out.print("Podaj liczbę: ");
        String input = s.next();
        for (int i = 0; i < input.length(); i++){
            if (!Character.isDigit(input.charAt(i))){
                isValid = false;
                break;
            }
        }
        if (isValid){
            return Integer.parseInt(input);
        }
        return 0;
    }

    // EAFP (Easy to Ask for Forgiveness than Permission) - drugie podejście,
    // najpierw próbujemy wykonać operację, a potem dbamy o zajęcie się ew. błędami
    private static int getIntEAFP(){
        Scanner s = new Scanner(System.in);
        System.out.print("Podaj liczbę: ");
        try{
            return s.nextInt();
        } catch (InputMismatchException e){
            return 0;
        }
    }
}
