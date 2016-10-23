package com.socket.http.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class HttpWebClient {
	private static final String colon = ":";
	private final static String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {
		/* String serverName = args[0];
	      int port = Integer.parseInt(args[1]);
	      String fileName = args[2];*/
		
		 String serverName = "127.0.0.1";
	      int port = 8080;
	      String fileName = "index.html";
	      String protocol = "http";
	      String protocolDelim = "://";

	      try {
	         System.out.println("Connecting to " + serverName + " on port " + port);
	         Socket client = new Socket(serverName, port);
	         
	        /* System.out.println("Just connected to " + client.getRemoteSocketAddress());
	         
	         DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());
	         BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

	         String sentence = inFromServer.readLine();
	         System.out.println(sentence);*/
	         
	         DataOutputStream dout=new DataOutputStream(client.getOutputStream());
	         dout.writeBytes("GET /index.html HTTP/1.1");
	         dout.writeBytes("Host: 127.0.0.1:8080");
	         dout.writeBytes("Connection: keep-alive");
	         dout.writeBytes("Cache-Control: max-age=0");
	         dout.writeBytes("Upgrade-Insecure-Requests: 1");
	         dout.writeBytes("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36	");
	         dout.writeBytes("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	         dout.writeBytes("Accept-Encoding: gzip, deflate, sdch");
	         dout.writeBytes("Accept-Language: en-GB,en-US;q=0.8,en;q=0.6");
	         dout.flush();  
	         dout.close();  
	         client.close();  
	         
	      }catch(IOException e) {
	         e.printStackTrace();
	      }
	      //Programe to hit the application using HTTP Client
//	      sendGet(protocol+protocolDelim+serverName+colon+port);
	      System.out.println("DONE");
	}
	
	private static void sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

}
