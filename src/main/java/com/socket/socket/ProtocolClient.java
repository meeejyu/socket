package com.socket.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ProtocolClient {
    
    public static void main(String[] args) throws IOException{
		
        //클라이언트 -> 소켓
        try {
            while(true) {
                Socket socket = new Socket("192.168.0.118", 9500);
    			System.out.println("연결성공");
                Scanner sc = new Scanner(System.in);
                String msg = sc.next();
                System.out.println("클라이언트 : "+msg);
		
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.println(msg);  //서버로 데이터를 전송한다. 
                writer.flush();   //버퍼 안에 있는 값들을 전부 비워준다. 
                System.out.println("데이터 전송 완료!");
                
                //소켓 -> 클라이언트
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = reader.readLine();
                System.out.println("데이터 받기 성공! : "+line);  //서버와 통신이 완료되어 "안녕하세요"라는 값을 가지고 온다.
                
                // writer.close();
                // reader.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
