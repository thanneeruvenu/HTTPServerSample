# Socket_POC

JDK Version : 1.8
IDE : Eclipse

Start HttpServer -> 

Note : we need to start server from IDE only

RUN in IDE Eclipse:
------------------
Right click of HTTPWebServer class and choose RUN configuration option and pass port number in arguments 

Note: Port number is optional. if you don't pass then it will take by default 8080 port


open any browser and give HTTP GET request as shown below

1.http://127.0.0.1:8080/

outcome : 

This is the HTTP Server Home Page.... 
The HTTP Client request is ....
GET / HTTP/1.1
Host: 127.0.0.1:8080
Connection: keep-alive
Cache-Control: max-age=0
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
Accept-Encoding: gzip, deflate, sdch, br
Accept-Language: en-US,en;q=0.8,en-AU;q=0.6


2.http://127.0.0.1:8080/index.html

outcome : display the content of file 

3.http://127.0.0.1:8080/index1.html (Wrong file name)

outcome : The Requested resource not found ....Usage: http://127.0.0.1:8080 or http://127.0.0.1:8080/fileName

4.if you give wrong URL then it will give same above error message with 404 error status code as header

Start HttpServerClient -> 

compile :
---------
javac com.socket.http.client.HttpWebClient.java

RUN :
-----
java com.socket.http.client.HttpWebClient <address> <port> <file_name>

(OR)

RUN in IDE :
----------
Right click of HttpWebClient class and choose RUN configuration option and pass address , port number and filename as in arguments 

Note: Port number is optional. if you don't pass then it will take by default 8080 port.

outcome : 

Connected
Message send
HTTP/1.1 200 OK
Server: Java HTTPServerContent-Type: text/html
Content-Length: 11
Connection: close

Hello world


