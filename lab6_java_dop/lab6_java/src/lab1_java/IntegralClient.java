package lab1_java;

import java.io.*;
import java.net.*;

public class IntegralClient {
    public static void main(String[] args) {
        final String SERVER_HOST = "localhost";
        final int SERVER_PORT = 9876;

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("TCP Client started. Waiting for task...");

            String taskLine;
            while ((taskLine = in.readLine()) != null) {
                System.out.println("Received task: " + taskLine);

                if (taskLine.startsWith("TASK:")) {
                    String[] parts = taskLine.split(":");
                    if (parts.length != 5) continue;

                    int clientId = Integer.parseInt(parts[1]);
                    double a = Double.parseDouble(parts[2]);
                    double b = Double.parseDouble(parts[3]);
                    double h = Double.parseDouble(parts[4]);

                    double result = calculateIntegral(a, b, h);
                    String response = "RESULT:" + clientId + ":" + result + "\n";

                    out.write(response);
                    out.flush();

                    System.out.println("Sent result to server: " + response);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double calculateIntegral(double a, double b, double h) {
        double sum = 0.0;
        double x = a;

        while (x + h <= b) {
            sum += (h / 2.0) * (Math.cos(x) + Math.cos(x + h));
            x += h;
        }

        if (x < b) {
            double last_h = b - x;
            sum += (last_h / 2.0) * (Math.cos(x) + Math.cos(b));
        }

        return sum;
    }
}
