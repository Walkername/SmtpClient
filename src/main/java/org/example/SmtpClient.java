package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SmtpClient {
    public static void main(String[] args) throws Exception {
        startSmtpClient();
    }

    private static void startSmtpClient() throws Exception {
        String sender = "<username@gmail.com>";
        String recipient = "<user123@mail.ru>";

        Socket socket = new Socket("104.237.130.88", 25);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String response = in.readLine();
        if (!response.startsWith("220")) {
            System.out.println("220 reply not received from server");
        }
        System.out.println(response + "\n");

        String heloCommand = "HELO student\r\n";
        out.printf(heloCommand);
        response = in.readLine();
        if (!response.startsWith("250")) {
            System.out.println("250 reply not received from server");
        }
        System.out.println(response + "\n");

        String mailFromCommand = "MAIL FROM: " + sender + "\r\n";
        out.printf(mailFromCommand);
        response = in.readLine();
        if (!response.startsWith("250")) {
            System.out.println("250 reply not received from server");
        }
        System.out.println(response + "\n");

        String rcptToCommand = "RCPT TO: " + recipient + "\r\n";
        out.printf(rcptToCommand);
        response = in.readLine();
        if (!response.startsWith("250")) {
            System.out.println("250 reply not received from server");
        }
        System.out.println(response + "\n");

        out.printf("DATA\r\n");
        out.printf("From: " + sender + "\r\n");
        out.printf("To: " + recipient + "\r\n");
        out.printf("Subject: batman docs \r\n");
        response = in.readLine();
        if (!response.startsWith("354")) {
            System.out.println("354 reply not received from server");
        }
        System.out.println(response + "\n");

        String dataMessage = "\r\n Superman, go back to Krypton!";
        out.printf(dataMessage);

        String endMessage = "\r\n.\r\n";
        out.printf(endMessage);
        response = in.readLine();
        if (!response.startsWith("250")) {
            System.out.println("250 reply not received from server");
        }
        System.out.println(response + "\n");

        String quitCommand = "QUIT\r\n";
        out.printf(quitCommand);
        response = in.readLine();
        if (!response.startsWith("221")) {
            System.out.println("221 reply not received from server");
        }
        System.out.println(response + "\n");

        in.close();
        out.close();
        socket.close();
    }
}