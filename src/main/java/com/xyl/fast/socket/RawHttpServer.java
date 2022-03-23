package com.xyl.fast.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xiangyanlin
 * @date 2022/3/20
 */
public class RawHttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("A Socket Create");
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream));
            StringBuilder requestBuilder = new StringBuilder();
            String line = "";
            while (!(line = reader.readLine()).isEmpty()) {
                requestBuilder.append(line + "\n");
            }
            String request = requestBuilder.toString();
            System.out.println(request);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("HTTP/1.1 200\n\n HelloWorld!\n");
            bufferedWriter.flush();
            socket.close();
        }

    }
}
