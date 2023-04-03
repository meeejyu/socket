package com.socket.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiServerV2 {
    
    ServerSocket serverSocket = null;
    ArrayList<Socket> socketlist = new ArrayList<>();
    
    public static void main(String[] args) {
        MultiServerV2 protocolServer = new MultiServerV2();

        protocolServer.init();
    }

    public void init() {
        try {
            serverSocket = new ServerSocket(9500);  
            System.out.println("서버 실행, 클라이언트 연결 대기");
            
            while(true) {
                socketlist.add(serverSocket.accept());

                int mySocket = socketlist.size()-1;
                System.out.println("클라이언트 연결 완료");
                Thread thread = new ChatThreadV2(socketlist, mySocket);
                thread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                System.out.println("연결 종료");
            } catch (Exception e) {
                System.out.println("소켓통신 종료 시 에러");
            }
        }
    }
    
}

class ChatThreadV2 extends Thread {

    ArrayList<Socket> socketlist = new ArrayList<>();
    BufferedReader chatRead;
    PrintWriter chatWrite;
    int mySocket;

    public ChatThreadV2(ArrayList<Socket> socketlist, int mySocket){
        this.socketlist = socketlist;
        this.mySocket = mySocket;
        try {
            this.chatRead = new BufferedReader(new InputStreamReader(this.socketlist.get(mySocket).getInputStream()));
            this.chatWrite = new PrintWriter(this.socketlist.get(mySocket).getOutputStream(), true);                    
        } catch (Exception e) {
            System.out.println("ChatThreadV2 생성자 예외 : "+ e.getMessage());
        }
    }

    @Override
    public void run() {

        String name = "";
        String message = "";

        try {
            if(chatRead != null) {
                name = chatRead.readLine();

                System.out.println(name + "님이 접속했습니다.");
                chatWrite.println("> " + name + "님이 접속했습니다.");
                System.out.println("현재 접속자 수 : " + socketlist.size()+"명");
            }
            while(chatRead != null) {
                message = chatRead.readLine();
                if(message == null) {
                    break;
                }
                System.out.println(name + " >> "+ message);
                chatWrite.println(name + " >>> " + message);
            }
        } catch (Exception e) {
            System.out.println("ChatThreadV2 run 예외1 : " + e.getMessage());
        }
        finally {
            System.out.println(name + " 퇴장");
            chatWrite.println(name + "님이 퇴장하셨습니다.");
            try {
                chatRead.close();
                chatWrite.close();
                socketlist.get(mySocket).close();
                System.out.println("현재 접속자 수 : " + socketlist.size()+"명");

                socketlist.remove(mySocket);                        
                System.out.println("현재 접속자 수 : " + socketlist.size()+"명");

            } catch (Exception e) {
                System.out.println("ChatThreadV2 run 예외2 : " + e.getMessage());
            }
        }
    }
}

