package com.socket.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class Receiver extends Thread{
    
    Socket socket;
    BufferedReader in = null;

    public Receiver(Socket socket) {
        this.socket = socket;

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
