import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter directory path: ");
        String root = sc.nextLine();

        System.out.println("Enter the target letter: ");
        char letter = sc.next().charAt(0);

        System.out.println("Enter the output file path: ");
        String output = sc.nextLine();

        try {
            DirectoryProcessor dp = new DirectoryProcessor(root, letter, output);
            dp.process();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
   }
    }
}