package com.socket.http.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpWebClient {
	public static void main(String[] args) throws Exception {

		String host = null;
		Integer port = null;
		String fileName = "";
		if (args.length > 0) {
			host = args[0];
		}

		if (args.length > 1) {
			try {
				port = Integer.valueOf(args[1]);
			} catch (NumberFormatException e) {
				port = 8080;
				fileName = args[1];
			}
		}

		if (args.length > 2) {
			fileName = args[2];
		}
		Socket s = new Socket();

		PrintWriter s_out = null;
		BufferedReader s_in = null;

		try {
			s.connect(new InetSocketAddress(host, port));
			System.out.println("Connected");

			// writer for socket
			s_out = new PrintWriter(s.getOutputStream(), true);
			// reader for socket
			s_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		}

		// Host not found
		catch (UnknownHostException e) {
			System.err.println("Don't know about host : " + host);
			System.exit(1);
		}

		long startTime = System.currentTimeMillis();
		// Send message to server
		String message = "GET /" + fileName + " HTTP/1.1\r\n\r\n";
		s_out.println(message);

		System.out.println("Message send");

		// Get response from server
		String response;
		while ((response = s_in.readLine()) != null) {
			System.out.println(response);
		}

		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out
				.println("Total elapsed http request/response time in milliseconds: "
						+ elapsedTime);

		s_out.close();
		s_in.close();
		s.close();

	}
}
