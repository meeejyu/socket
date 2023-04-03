package com.socket.exSocket;

import java.net.Socket;
import java.util.Scanner;

import com.socket.chat.Receiver;
import com.socket.chat.Sender;

public class ExClient {
    
    public static void main(String[] args) {

        System.out.println("이름을 입력하세요 : ");
        Scanner scanner = new Scanner(System.in);
        String s_name = scanner.nextLine();

        // 이름 입력후 소켓 생성 -> 리시버가 받음
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
            // run()에서 메세지를 받음
            sender.start();
        } catch (Exception e) {
            System.out.println("예외 발생 > MultiClient > " + e.getMessage());
        }
    }
}
