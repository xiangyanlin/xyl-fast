package com.xyl.fast.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Function;

/**
 * 并发处理
 *
 * @author xiangyanlin
 * @date 2022/3/20
 */
public class StepTwoServer {
    ServerSocket serverSocket;
    Function<String, String> handler;

    public StepTwoServer(Function<String, String> handler) {
        this.handler = handler;
    }

    public static void main(String[] args) throws IOException {
        StepTwoServer server = new StepTwoServer(req -> "HTTP/1.1 200\n\n Good!\n");
        server.listen(8001);

    }

    private void listen(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            this.accept();
        }
    }

    private void accept() throws IOException {
        Socket socket = serverSocket.accept();
        new Thread(() -> {
            try {
                handler(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handler(Socket socket) throws IOException {
        try {
            System.out.println("A Socket Create");
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream));
            StringBuilder requestBuilder = new StringBuilder();
            String line = "";
            while (true) {
                line = reader.readLine();
                if (null == line || line.isEmpty()) {
                    break;
                }
                requestBuilder.append(line).append("\n");
            }
            String request = requestBuilder.toString();
            System.out.println(request);
            String response = this.handler.apply(request);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(response);
            bufferedWriter.flush();
            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
