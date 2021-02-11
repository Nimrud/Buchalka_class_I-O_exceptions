package m02_catch_throw;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int result = divide();
        System.out.println("Wynik dzielenia to: " + result);
    }
    private static int divide(){
        int x, y;
        try{
            x = getInt();
            y = getInt();
        } catch (NoSuchElementException e){
            throw new ArithmeticException("Wprowadzono znak niebędący cyfrą");
        }

        System.out.println("Wprowadzono liczbę x=" + x + " oraz liczbę y=" + y);

        try{
            return x/y;
        } catch (ArithmeticException e){
            throw new ArithmeticException("Nie można podzielić przez 0");
        }
    }

    private static int getInt(){
        Scanner s = new Scanner(System.in);
        System.out.print("Wprowadź liczbę: ");
        while (true) {
            try {
                return s.nextInt();
            } catch (InputMismatchException e){
                // ponowne wczytanie liczby:
                s.nextLine();
                System.out.println("Wprowadź liczbę używając wyłącznie cyfr z zakresu od 0 do 9");
            }
        }
    }
}
