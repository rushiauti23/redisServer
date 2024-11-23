package org.example.rediserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisServer {

    private static final int PORT = 6379;

    public static void main(String[] args) {
        RedisStore redisStore = new RedisStore();

        try(ServerSocket socket = new ServerSocket(PORT, 50, InetAddress.getByName("0.0.0.0"))){
            System.out.println("Redis-like Server running on port "+PORT);
            while(true){
                Socket clientSocket = socket.accept();
                new Thread(new ClientHandler(clientSocket, redisStore)).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
