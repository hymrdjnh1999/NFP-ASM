package app.client;

import java.io.Console;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import app.EncodeDeCode;

public class ClientWrite extends Thread {
    private Socket socket;
    private DataOutputStream dataOutputStream = null;
    static Scanner scanner = new Scanner(System.in);

    public ClientWrite(Socket socket2) {
        this.socket = socket2;

        try {

            OutputStream outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

        } catch (Exception e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            Console console = System.console();
            String userName = console.readLine("\nEnter yor name : ");
            ChatClient.setUserName(userName);
            String encode = EncodeDeCode.encode(userName);
            dataOutputStream.writeUTF(encode);
            System.out.print("Accept name " + userName + " type your message : ");
            String mess;
            do {
                mess = scanner.nextLine();
                encode = EncodeDeCode.encode(mess);
                dataOutputStream.writeUTF(encode);
            } while (!mess.equals("bye"));
            ChatClient.mainMenu();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}