package com.socket.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    
    ServerSocket serverSocket = null;
    Socket socket = null;

    /*
     * 내부 클래스는 다른 클래스 내부에 정의되며, 외부 클래스의 인스턴스 내에서만 사용됩니다.
     * 내부 클래스의 인스턴스를 생성하기 전에 외부 클래스의 인스턴스를 먼저 생성하고, 
     * 이를 참조하여 내부 클래스의 인스턴스를 생성해야 합니다. 
     */
    public static void main(String[] args) {
        MultiServer protocolServer = new MultiServer();

        protocolServer.init();
    }

    public void init() {
        try {
            serverSocket = new ServerSocket(9500);  
            System.out.println("서버 실행, 클라이언트 연결 대기");
            
            while(true) {
                socket = serverSocket.accept();

                System.out.println("클라이언트 연결 완료");
                Thread thread = new ChatThread(socket);
                thread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // socket.close();
                serverSocket.close();
                System.out.println("연결 종료");
            } catch (Exception e) {
                System.out.println("소켓통신 종료 시 에러");
            }
        }
    }
    
}
/*
     * ChatThread 클래스를 내부 클래스로 만든 이유는 Server 클래스와 ChatThread 클래스가 서로 긴밀하게 관련되어 있기 떄문

    ChatThread 클래스는 Server 클래스에서 생성되며, 생성된 Socket 객체를 사용하여 클라이언트와 통신합니다. 
    또한 ChatThread 클래스는 Server 클래스의 멤버 변수와 메서드에 접근할 수 있다.
    예를 들어 ChatThread 클래스에서 Server 클래스의 멤버 변수를 사용하여 서버에서 관리하는 클라이언트 목록을 관리할 수 있습니다.   

    따라서 ChatThread 클래스를 내부 클래스로 만들면, Server 클래스와 ChatThread 클래스 간의 관계를 보다 간결하고 명확하게 표현할 수 있다. 
    또한 ChatThread 클래스를 외부 클래스로 만들 경우, 불필요한 public 접근 제어자를 사용해야 하는 등 코드의 가독성이 떨어질 수 있다.
*/
class ChatThread extends Thread {

    Socket socket;
    BufferedReader chatRead;
    //서버에 메세지를 보내는 출력스트림을 저장하기 위한 필드
    PrintWriter chatWrite;

    public ChatThread(Socket socket){
        this.socket = socket;
        try {
            this.chatRead = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            //서버에 메세지를 보내는 출력스트림을 저장하기 위한 필드
            this.chatWrite = new PrintWriter(this.socket.getOutputStream(), true);                    
        } catch (Exception e) {
            System.out.println("ChatThread 생성자 예외 : "+ e.getMessage());
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
            System.out.println("ChatThread run 예외1 : " + e.getMessage());
        }
        finally {
            System.out.println(name + " 퇴장");
            chatWrite.println(name + "님이 퇴장하셨습니다.");
            try {
                chatRead.close();
                chatWrite.close();
                socket.close();                        
            } catch (Exception e) {
                System.out.println("ChatThread run 예외2 : " + e.getMessage());
            }
        }
    }
}

