package com.sgs.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/*
 * Simple HTTP server using sockets
 * 			serves a HTML file to client/Browser
 */
public class Main {
	
	public static int SERVER_PORT = 8080;

	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3); // Thread pool executor is initialized
		try {
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);				// listen to port 80800
			while(true) {
				Socket socket = serverSocket.accept();						// client
				executor.execute(new ResourceServerThread(socket));			// serve client 
			}
			//serverSocket.close();
			//executor.shutdown();											// terminate executor
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
}
