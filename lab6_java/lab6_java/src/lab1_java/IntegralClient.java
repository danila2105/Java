package lab1_java;

import java.net.*;
import java.io.*;

public class IntegralClient {
    private static final int SERVER_PORT = 9876;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket(); // Автоматически выбирает порт
            int clientPort = clientSocket.getLocalPort();

            System.out.println("Client started. Listening on port: " + clientPort);

            // Отправляем серверу информацию о своём порте
            InetAddress serverAddress = InetAddress.getByName("localhost");
            String registerMessage = "REGISTER:" + clientPort;
            byte[] registerData = registerMessage.getBytes("UTF-8");
            DatagramPacket registerPacket = new DatagramPacket(registerData, registerData.length, serverAddress, SERVER_PORT);
            clientSocket.send(registerPacket);

            byte[] receiveData = new byte[BUFFER_SIZE];

            while (true) {
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
                System.out.println("Received task: " + message);

                if (message.startsWith("TASK:")) {
                    String[] parts = message.split(":");
                    if (parts.length != 5) {
                        System.err.println("Invalid task format: " + message);
                        continue;
                    }

                    int clientId = Integer.parseInt(parts[1]);
                    double a = Double.parseDouble(parts[2]);
                    double b = Double.parseDouble(parts[3]);
                    double h = Double.parseDouble(parts[4]);

                    double result = calculateIntegral(a, b, h);

                    // **Отправляем результат серверу с подтверждением**
                    boolean acknowledged = false;
                    while (!acknowledged) {
                        try {
                            String response = "RESULT:" + clientId + ":" + result;
                            byte[] sendData = response.getBytes("UTF-8");
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
                            clientSocket.send(sendPacket);
                            System.out.println("Result sent to server: " + result);

                            // **Ждём подтверждение (ACK)**
                            clientSocket.setSoTimeout(1000);
                            DatagramPacket ackPacket = new DatagramPacket(receiveData, receiveData.length);
                            clientSocket.receive(ackPacket);

                            String ackMessage = new String(ackPacket.getData(), 0, ackPacket.getLength(), "UTF-8");
                            if (ackMessage.startsWith("ACK:" + clientId)) {
                                acknowledged = true;
                                System.out.println("ACK received from server. Result confirmed.");
                            }
                        } catch (SocketTimeoutException e) {
                            System.out.println("No ACK received, resending result...");
                        }
                    }
                    clientSocket.setSoTimeout(0); // **Сбрасываем таймаут после завершения подтверждения**
                }
            } catch (IOException e) {
                e.printStackTrace();
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
