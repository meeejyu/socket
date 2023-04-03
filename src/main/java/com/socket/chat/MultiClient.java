package com.socket.chat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MultiClient {
    
    public static void main(String[] args) {
		
        Socket socket = null;
        System.out.println("이름을 입력하세요 : ");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();

        //클라이언트 -> 소켓
        try {
            // 이름 받고 소켓 받기
            String serverIP = "localhost";
            if(args.length > 0) {
                serverIP = args[0];
            }

            socket = new Socket(serverIP, 9500);

            // socket = new Socket("192.168.0.118", 9500);
            System.out.println("서버와 연결되었습니다.");

            // 메세지 읽기만 하기
            // 리시버를 빼니까 클라이언트에 메세지 안감
            Thread receiver = new Receiver(socket);
            receiver.start();

            // 메세지 보내기만 하기
            Thread sender = new Sender(socket, name);
            sender.start();

        } catch (IOException e) {
            System.out.println("에러 출력 : "+e.getMessage());
        }
    }
    
}
