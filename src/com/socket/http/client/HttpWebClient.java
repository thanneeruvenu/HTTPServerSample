package com.socket.http.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpWebClient {

	public static void main(String[] args) {
		/* String serverName = args[0];
	      int port = Integer.parseInt(args[1]);
	      String fileName = args[2];*/
		
		 String serverName = "127.0.0.1";
	      int port = 8080;
	      String fileName = "index.html";

	      try {
	         System.out.println("Connecting to " + serverName + " on port " + port);
	         Socket client = new Socket(serverName, port);
	         
	         System.out.println("Just connected to " + client.getRemoteSocketAddress());
	         
	         DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());
	         BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

	         String sentence = inFromServer.readLine();
	         System.out.println(sentence);
	         
	         client.close();
	      }catch(IOException e) {
	         e.printStackTrace();
	      }
	}

}
