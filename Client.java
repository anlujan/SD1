import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 12345);
            System.out.println("Connected to the server!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (true) {
                System.out.print("Enter a number to check if it's prime (or 'exit' to quit): ");
                String userInput = reader.readLine();

                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }

                try {
                    int number = Integer.parseInt(userInput);
                    writer.println(number);
                    String response = serverReader.readLine();
                    System.out.println("Is the number prime? " + response);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
