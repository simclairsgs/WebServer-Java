package com.sgs.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ResourceServerThread implements Runnable{
	private final Socket socket;
	
	ResourceServerThread(Socket socket){								// receiver socket from main thread
		this.socket = socket;
	}
	
	public void run() {
		
		try {
			InputStreamReader bir = new InputStreamReader(socket.getInputStream());
    	 	BufferedReader br = new BufferedReader(bir);
    	 	PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
    	 	String reqData= br.readLine().split(" ")[1];
    	 	String fileName="/index.html";
    	 	if(reqData.equals("/"));
    	 	else fileName = reqData;
    	 	try {
    	 		FileReader file = new FileReader("resources"+fileName);	
    	 		BufferedReader bfr = new BufferedReader(file);
    	 		 printWriter.println("HTTP/1.1 200 OK");							// set HTTP - Headers
    	         printWriter.println("Content-Type: text/html");
    	         printWriter.println("Content-Length: " + new File("resources/"+fileName).length());
    	         printWriter.println("\r\n");
    	         String line = bfr.readLine();
    	         while (line != null)
    	         {		
    	         	printWriter.println(line);
    	 			line = bfr.readLine();
    	         }
    	         bfr.close();
    	         bfr.close();
    	         printWriter.close();
    	 	}
    	 	catch(IOException e) {
    	 		PrintWriter error404 = new PrintWriter(socket.getOutputStream());
    	 		error404.println("HTTP/1.1 404 NOT FOUND");							// set HTTP - Headers
    	 		error404.println("Content-Type: text/html");
    	 		error404.println("\r\n");
    	 		error404.write("<center><h2>404 NOT FOUND ERROR</h2><br>REQUESTED RESOURCE IS NOT FOUND IN THE SERVER</center>");
    	 		error404.close();
    	 	}
    	 	
		}catch(Exception e) {
			PrintWriter error500 = null;
			try {
				error500 = new PrintWriter(socket.getOutputStream());
				error500.println("HTTP/1.1 500 INTERNAL SERVER ERROR");							// set HTTP - Headers
				error500.println("Content-Type: text/html");
				error500.println("\r\n");
				error500.write("<center><h2>500 INTERNAL SERVER ERROR</h2>Contact Administrator or simclair.sgs@gmail.com</center>");
		 		error500.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		/*
		File file = new File("resources/index.html");						// get HTML file
        PrintWriter printWriter = null;
        try {
        	 	InputStreamReader bir = new InputStreamReader(socket.getInputStream());
        	 	BufferedReader br = new BufferedReader(bir);
        	 	String[] reqData= br.readLine().split(" ");
        	 	
			printWriter = new PrintWriter(socket.getOutputStream());	// Get output stream of socket
		} catch (IOException e2) {
			e2.printStackTrace();
		}
      
        BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));			// read HTML file 
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
        printWriter.println("HTTP/1.1 200 OK");							// set HTTP - Headers
        printWriter.println("Content-Type: text/html");
        printWriter.println("Content-Length: " + file.length());
        printWriter.println("\r\n");
        String line = null;
		try {
			line = reader.readLine();									// read and write each line to output stream
		} catch (IOException e) {
			e.printStackTrace();
		}
        while (line != null)
        {
        	  printWriter.println(line);

            try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        try {
			reader.close();											// close reader
		} catch (IOException e) {
			e.printStackTrace();
		}
        printWriter.close();										// close printWriter
    */
	}
}