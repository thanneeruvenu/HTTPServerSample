package com.socket.http.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.StringTokenizer;

public class HTTPWebServer extends Thread {

	Socket server = null;
	private static ServerSocket serverSocket;

	static final String HTML_START = "<html>"
			+ "<title>HTTP Server in java</title>" + "<body>";

	static final String HTML_END = "</body>" + "</html>";

	HTTPWebServer(Socket server) {
		this.server = server;
	}

	@Override
	public void run() {

		try {

			System.out.println("The Client " + server.getInetAddress() + ":"
					+ server.getPort() + " is connected");
		    long startTime = System.currentTimeMillis();

			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(server.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(
					server.getOutputStream());

			String requestString = inFromClient.readLine();
			System.out.println("The Client Request String is : "
					+ requestString);

			String headerLine = requestString;
			StringTokenizer stringTokenizer = new StringTokenizer(headerLine);
			String httpMethod = stringTokenizer.nextToken();
			String httpQueryString = stringTokenizer.nextToken();

			StringBuffer responseBuffer = new StringBuffer();
			responseBuffer
					.append("<b> This is the HTTP Server Home Page.... </b><BR>");
			responseBuffer.append("The HTTP Client request is ....<BR>");

			System.out.println("The HTTP request string is ....");

			while (inFromClient.ready()) {
				responseBuffer.append(requestString + "<BR>");
				System.out.println(requestString);
				requestString = inFromClient.readLine();
			}

			if (httpMethod.equals("GET")) {
				if (httpQueryString.equals("/")) {
					// The default home page
					sendResponse(200, responseBuffer.toString(), false,
							outToClient);
				} else {
					// This is interpreted as a file name
					String fileName = httpQueryString.replaceFirst("/", "");
					fileName = URLDecoder.decode(fileName);
					if (new File(fileName).isFile()) {
						sendResponse(200, fileName, true, outToClient);
					} else {
						sendResponse(
								404,
								"<b>The Requested resource not found ...."
										+ "Usage: http://127.0.0.1:8080 or http://127.0.0.1:8080/fileName</b>",
								false, outToClient);
					}
				}
			} else {
				sendResponse(
						404,
						"<b>The Requested resource not found ...."
								+ "Usage: http://127.0.0.1:8080 or http://127.0.0.1:8080/fileName</b>",
						false, outToClient);
			}

		    long elapsedTime = System.currentTimeMillis() - startTime;
		    System.out.println("Total elapsed http request/response time in milliseconds: " + elapsedTime);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendResponse(int statusCode, String responseString,
			boolean isFile, DataOutputStream outToClient) throws Exception {

		String statusLine = null;
		String serverdetails = "Server: Java HTTPServer";
		String contentLengthLine = null;
		String fileName = null;
		String contentTypeLine = "Content-Type: text/html" + "\r\n";
		FileInputStream fin = null;

		if (statusCode == 200)
			statusLine = "HTTP/1.1 200 OK" + "\r\n";
		else
			statusLine = "HTTP/1.1 404 Not Found" + "\r\n";

		if (isFile) {
			fileName = responseString;
			fin = new FileInputStream(fileName);
			contentLengthLine = "Content-Length: "
					+ Integer.toString(fin.available()) + "\r\n";
			if (!fileName.endsWith(".htm") && !fileName.endsWith(".html"))
				contentTypeLine = "Content-Type: \r\n";
		} else {
			responseString = HTTPWebServer.HTML_START + responseString
					+ HTTPWebServer.HTML_END;
			contentLengthLine = "Content-Length: " + responseString.length()
					+ "\r\n";
		}

		outToClient.writeBytes(statusLine);
		outToClient.writeBytes(serverdetails);
		outToClient.writeBytes(contentTypeLine);
		outToClient.writeBytes(contentLengthLine);
		outToClient.writeBytes("Connection: close\r\n");
		outToClient.writeBytes("\r\n");

		if (isFile)
			sendFile(fin, outToClient);
		else
			outToClient.writeBytes(responseString);

		outToClient.close();
	}

	public void sendFile(FileInputStream fin, DataOutputStream out)
			throws Exception {
		byte[] buffer = new byte[1024];
		int bytesRead;

		while ((bytesRead = fin.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
		fin.close();
	}

	public static void main(String[] args) throws Exception {
		 int portNumber = Integer.parseInt(args[0]);
		//int portNumber = 8080;
		serverSocket = new ServerSocket(portNumber);
		System.out.println("Waiting for client on port "
				+ serverSocket.getLocalPort() + "...");

		while (true) {
			Socket server = serverSocket.accept();
			(new HTTPWebServer(server)).start();
		}
	}

}
