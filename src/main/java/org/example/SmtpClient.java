package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SmtpClient {
    public static void main(String[] args) throws Exception {
        startSmtpClient();
    }

    private static String sendMsg(PrintWriter out, BufferedReader in, String message) throws Exception {
        out.printf(message);
        String response = in.readLine();
        System.out.println(response + "\n");
        return response;
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

        response = sendMsg(out, in, "HELO student\r\n");
        if (!response.startsWith("250")) {
            System.out.println("250 reply not received from server");
        }

        response = sendMsg(out, in, "MAIL FROM: " + sender + "\r\n");
        if (!response.startsWith("250")) {
            System.out.println("250 reply not received from server");
        }

        response = sendMsg(out, in, "RCPT TO: " + recipient + "\r\n");
        if (!response.startsWith("250")) {
            System.out.println("250 reply not received from server");
        }

        response = sendMsg(
                out,
                in,
                "DATA\r\n"
                + "From: " + sender + "\r\n"
                + "To: " + recipient + "\r\n"
                + "Subject: batman docs \r\n"
        );
        if (!response.startsWith("354")) {
            System.out.println("354 reply not received from server");
        }

        String dataMessage = "\r\n Superman, go back to Krypton!";
        out.printf(dataMessage);

        response = sendMsg(out, in, "\r\n.\r\n");
        if (!response.startsWith("250")) {
            System.out.println("250 reply not received from server");
        }

        response = sendMsg(out, in, "QUIT\r\n");
        if (!response.startsWith("221")) {
            System.out.println("221 reply not received from server");
        }

        in.close();
        out.close();
        socket.close();
    }
}