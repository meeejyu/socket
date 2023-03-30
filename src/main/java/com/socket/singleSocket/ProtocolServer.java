package com.socket.singleSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ProtocolServer {
    
    public static void main(String[] args) {
        
        BufferedReader reader = null;
        PrintWriter writer = null;

        ServerSocket serverSocket = null;
        Socket socket = null;

        Scanner sc = new Scanner(System.in);

        try {
            serverSocket = new ServerSocket(9500);  
            System.out.println("서버 실행, 클라이언트 연결 대기");
            socket = serverSocket.accept();

            System.out.println("클라이언트 연결 완료");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

            while(true) {
                String intputMessage = reader.readLine(); // 데이터 읽기

                if("quit".equals(intputMessage)) {
                    break;
                }

                System.out.println("클라이언트가 준 메세지 : " + intputMessage);
                
                String outputMessage = sc.nextLine();
                writer.println(outputMessage);
                writer.flush();
                if("quit".equals(outputMessage)) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sc.close();
                socket.close();
                serverSocket.close();
                System.out.println("연결 종료");
            } catch (Exception e) {
                System.out.println("소켓통신 종료 시 에러");
            }
        }
    }
}
