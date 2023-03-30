package com.socket.singleSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ProtocolClient {
    
    public static void main(String[] args) {
		
        Socket socket = null;
        Scanner sc = new Scanner(System.in);
        BufferedReader reader = null;
        PrintWriter writer = null;

        //클라이언트 -> 소켓
        try {
            socket = new Socket("192.168.0.118", 9500);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("연결성공");

            while(true) {
                String msg = sc.next();
                System.out.println("클라이언트 : "+msg);
		
                writer.println(msg);  //서버로 데이터를 전송한다. 
                writer.flush();   //버퍼 안에 있는 값들을 전부 비워준다. 
                System.out.println("데이터 전송 완료!");

                if("quit".equals(msg)) {
                    break;
                }
                
                //소켓 -> 클라이언트
                String line = reader.readLine();
                System.out.println("데이터 받기 성공! : "+line);  //서버가 보낸 메세지 값을 가지고 온다.
                
            }
        } catch (IOException e) {
            System.out.println("에러 출력 : "+e.getMessage());
        } finally {
            try {
                sc.close();
                if(socket != null) {
                    socket.close();
                    System.out.println("서버 연결 종료");
                }
            } catch (Exception e) {
                System.out.println("에러 출력 : "+e.getMessage());
            }
        }
        
    }
    
}
