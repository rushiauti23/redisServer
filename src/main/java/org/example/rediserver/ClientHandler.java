package org.example.rediserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private RedisStore redisStore;

    public ClientHandler(Socket clientSocket, RedisStore redisStore){
        this.socket = clientSocket;
        this.redisStore = redisStore;
    }


    @Override
    public void run() {

        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String inputLine;
            while((inputLine = in.readLine())!=null){
                String[] tokens = inputLine.split(" ");
                String command = tokens[0].toUpperCase();

                switch (command){
                    case "SET":
                        if(tokens.length==3){
                            redisStore.set(tokens[1], tokens[2]);
                        }else{
                            out.println("ERROR: Invalid SET syntax");
                        }
                        break;

                    case "GET":
                        if(tokens.length==2){
                            String value = redisStore.get(tokens[1]);
                            out.println(value!=null?value:"(nil)");
                        }else{
                            out.println("ERROR: Invalid GET syntax");
                        }
                        break;

                    case "DEL":
                        if(tokens.length==2){
                            redisStore.delete(tokens[1]);
                        }else{
                            out.println("ERROR: Invalid DEL syntax");
                        }
                        break;

                    default:
                        out.println("ERROR: Invalid GET syntax");
                        break;
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
