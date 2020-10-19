package connection.test;
import connection.SocketConnection;

import java.util.Scanner;

// This is used to test your connection between PC and Rpi
public class Client {
	//client acts like as the algor component

	public static void main(String arg[]) {
		
		SocketConnection cs = SocketConnection.getInstance();
		String message = "";
		Scanner sc = new Scanner (System.in);
		while (message.toUpperCase().compareTo("bye".toUpperCase()) != 0) {
			System.out.print("Enter your message: ");
			message = sc.nextLine();
			cs.sendMessage(message);
			System.out.println("Waiting for server to reply...");
			System.out.println("Server: " + cs.receiveMessage());
		}
		cs.closeConnection();
	}
}
