package com.socket.socket;

import java.net.Socket;
import java.util.Scanner;

public class MultiClient {
    
    public static void main(String[] args) {
        System.out.println("이름을 입력하세요 : ");
        Scanner scanner = new Scanner(System.in);
        String s_name = scanner.nextLine();

        try {

            String serverIP = "localhost";
            if(args.length > 0) {
                serverIP = args[0];
            }
            
            Socket socket = new Socket(serverIP, 9999);
            System.out.println("서버와 연결되었습니다.");

            Thread receiver = new Receiver(socket);
            receiver.start();

            Thread sender = new Sender(socket, s_name);
            sender.start();
        } catch (Exception e) {
            System.out.println("예외 발생 > MultiClient > " + e.getMessage());
        }
    }
}
