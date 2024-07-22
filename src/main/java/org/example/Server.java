package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static ServerSocket serverSocket;
    ExecutorService executor = Executors.newFixedThreadPool(64);


    public void startServer() {
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("Сервер запущен и ожидает подключения");
        } catch (IOException e) {
            System.out.println("что то пошло не так");
            e.printStackTrace();
        }
    }

    public void requestProcessing(List<String> validPaths) {
        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                executor.execute(new ClientHandler(clientSocket, validPaths));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
