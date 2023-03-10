package com.socket.socket;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Sender extends Thread{
    
    Socket socket;
    PrintWriter out = null;
    String name;

    public Sender(Socket socket, String name) {
        this.socket = socket;

        try {
            out = new PrintWriter(this.socket.getOutputStream(), true);
            this.name = name;            
        } catch (Exception e) {
            System.out.println("예외 > Sender > 생성장 : "+e.getMessage());
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        try {
            out.println(name);

            while(out!=null) {
                try {
                    String scan = scanner.nextLine();
                    if(scan.equalsIgnoreCase("Q")) {
                        break;
                    }
                    else {
                        out.println(scan);
                    }
                } catch (Exception e) {
                    System.out.println("예외 > Sender > run1 : "+e.getMessage());
                }
            }
            out.close();
            socket.close();
        }
        catch(Exception e) {
            System.out.println("예외 > Sender > run2 : "+e.getMessage());
        }
    }

}
