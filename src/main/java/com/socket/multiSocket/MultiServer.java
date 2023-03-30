package com.socket.multiSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    
	ServerSocket serverSocket = null;
	Socket socket = null;
	
	public MultiServer() {

	}

	public void init() {
		
		try {
			serverSocket = new ServerSocket(9999);
			System.out.println("서버 시작");

			while (true) {
				socket = serverSocket.accept();

				Thread mst = new MultiServerT(socket);
				mst.start(); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		
		MultiServer ms = new MultiServer();
		ms.init();

    }
    
}

class MultiServerT extends Thread {

	Socket socket;
	PrintWriter out = null;
	BufferedReader in = null;

	public MultiServerT(Socket socket) {
		this.socket = socket;
		try {
			out = new PrintWriter(this.socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (Exception e) {
			System.out.println("예외 : " + e.getMessage());
		}
	}

	@Override
	public void run() {
		
		String name = "";
		String s = "";

		try {
			if(in != null) {
				name = in.readLine();

				System.out.println(name + " 접속");
				out.println("> "+name+"님이 접속했습니다.");
			}
			while(in != null) {
				s = in.readLine();
				if(s == null) break;

				System.out.println(name + " >> "+ s);
				sendAllMsg(name, s);
			}
		} catch (Exception e) {
			System.out.println("예외 : "+e.getMessage());
		}
		finally {
			System.out.println(Thread.currentThread().getName() + " 종료");
			
			try {
				in.close();
				out.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sendAllMsg(String name, String msg) {
		try {
			out.println("> "+ name + " ==> " +msg);
		} catch (Exception e) {
			System.out.println("예외 : "+e.getMessage());
		}
	}

}
