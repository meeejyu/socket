package com.socket.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ProtocolServer {
    
	public static void main(String[] args) {
        try {
			ServerSocket serverSocket = new ServerSocket(9500); // 포트번호를 9500번으로 지정
			while (true) {
				System.out.println("연결을 기다리는 중...");
				Socket socket = serverSocket.accept();
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("연결 수락됨" + isa.getHostName());
				// 소켓 -> 서버
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));																	// 데이터를 읽어옴
				String line = reader.readLine(); // 서버 클래스 안의 line변수 안에 "안녕하세요!"가 저장됨
				// 서버 -> 소켓
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); // 소켓으로 데이터를
																										// 바깥으로 보냄
				writer.println(line);
				writer.flush();
			}
		} catch (IOException e) {
            e.printStackTrace();
		}
    }
    
}
