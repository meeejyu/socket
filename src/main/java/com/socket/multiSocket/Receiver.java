package com.socket.multiSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class Receiver extends Thread{
    
    Socket socket;
    BufferedReader in = null;

    public Receiver(Socket socket) {
        this.socket = socket;

        // BufferedReader는 버퍼를 이용해서 읽고 쓰는 함수
        // 버퍼를 사용하기 때문에 입출력 효율이 좋다, 버퍼를 이용해서 한번에 모아뒀다가 전송해줌
        // 많은 데이터를 입력 받아야할때 사용
        // readLine() 한줄을 읽어서 string으로 변환
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (Exception e) {
            System.out.println("예외 Reciver 생성자: "+e.getMessage());
        }
    }

    @Override
    public void run() {
        while(in != null) {
            try {
                System.out.println("Thread Receive : "+in.readLine());
            } catch (SocketException se) {
                System.out.println("SocketException 에러 : "+se.getMessage());
            } catch (Exception e) {
                System.out.println("예외 > Receiver > run1 " +e);
            }
        }

        try {
            in.close();
        } catch (Exception e) {
            System.out.println("예외 > Receiver > run2 " +e);
        }
    }

}
