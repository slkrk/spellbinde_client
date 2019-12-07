package pl.softlink.spellbinder.server;

import pl.softlink.spellbinder.server.connection.ServerRunnable;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(String args[]) {

        try {

            ServerSocket serverSocket = new ServerSocket(Config.CONNECTION_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                ServerRunnable serverTCPRunnable = new ServerRunnable(socket);
                Thread thread = new Thread(serverTCPRunnable);
                thread.start();
                System.out.println("Serwer: zakończono wątek.");

            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }




    public static void main2(String[] args) {


        try {
            ServerSocket serverSocket = new ServerSocket(5001);



            Socket socket = serverSocket.accept();



            InputStream inputStream = socket.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            System.out.println(bufferedReader.readLine());



            serverSocket.close();



        } catch (IOException e) {
            e.printStackTrace();
        }



//        InetAddress inetAddress = null;
//        try {
////            inetAddress = InetAddress.getByName("wp.pl");
//            inetAddress = InetAddress.getByName("localhost");
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(inetAddress.getHostAddress());
//        System.out.println(inetAddress);
//


    }
}
