package m02_catch_throw;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            int result = divide();
            System.out.println("Wynik dzielenia to: " + result);
        } catch (ArithmeticException e){
            System.out.println(e.toString());
            System.out.println("Nie można wykonać operacji!");
        }
    }

    private static int divide(){
        int x, y;
        try{
            x = getInt();
            y = getInt();
            System.out.println("Wprowadzono liczbę x=" + x + " oraz liczbę y=" + y);
            return x/y;
        } catch (NoSuchElementException e){
            throw new ArithmeticException("Wprowadzono znak niebędący cyfrą");
        } catch (ArithmeticException e){
            throw new ArithmeticException("Nie można podzielić przez 0");
        }
    }

    // Przy wielu blokach catch kolejność jest ważna!
    // są one sprawdzane od góry - i jeśli pierwszy z nich pasuje, to reszta jest ignorowana!

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
