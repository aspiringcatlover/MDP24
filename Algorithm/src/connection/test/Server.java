package connection.test;

import main.Constants;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Server {
	// This server basically test communication for the main system
	//this acts like the rpi --> msg from arduino/android
	public static void main(String args[]) {
		int counter=0;
		ConnectionServer server = ConnectionServer.getInstance();
		String message ;
		String sensorMessage;
		boolean acknowledge;
		Scanner sc = new Scanner(System.in);
		boolean exploring = false, completed = false, fastestpath = false;
		int[] pos = new int[]{1, 1};
		int direction = 2, count = 0;
		while (true) {
//			message = "ES|";
			System.out.print("Enter your command: ");
			message = sc.nextLine();
			server.sendMessage(message);
			if (message.equals(Constants.START_EXPLORATION)) {
				System.out.println("starting exploring");
				exploring = true;
			}
			if (message.equals(Constants.FASTEST_PATH)) {
				System.out.println("fastest path....");
				fastestpath = true;
			}
			if (message.equals(Constants.SEND_ARENA)) {
				completed = true;
			}
			while (exploring || fastestpath) {
				message = server.receiveMessage();
				System.out.println("receive..." + message);
				//if (message.compareTo("W|")==0|| message.compareTo("A|")==0||message.compareTo("D|")==0){
					//message = server.receiveMessage();
					if (message.contains("Z|")) {
						if (counter==12||counter==30||counter==43){
							server.sendMessage("6,6,6,100,6,6");
							System.out.println("sensor values send 6,6,6,100,6,6");
						}else{
							server.sendMessage("100,100,100,100,6,6");
							System.out.println("sensor values send 100,100,100,100,6,6");
						}

						counter++;
					}
					if (message.contains("END EXPLORATION")){
						break;
					}
			}
				//System.out.println("starting exploring/fastest path....");




				acknowledge = true;
				System.out.println("Message received: " + message);
				Pattern p = Pattern.compile("W\\d+[|]");

				if (p.matcher(message).matches()) {
					/*
					pos[0] = pos[0] + Constants.SENSORDIRECTION[direction][0];
					pos[1] = pos[1] + Constants.SENSORDIRECTION[direction][1];
					System.out.println(pos[0] + ", " + pos[1]);*/
					System.out.println("what is this suppose to be");
					
//						System.out.println("Enter sensor value:");
//						message = sc.nextLine();
				}
				else if (message.equals(Constants.TURN_LEFT)) {
					direction = (direction + 3)%4;
				}
				else if (message.equals(Constants.TURN_RIGHT)) {
					direction = (direction + 1)%4;
				}
				else if(message.equals(Constants.SENSE_ALL) || message.equals(Constants.CALIBRATE) || message.equals(Constants.RIGHTALIGN)) {
					System.out.println(pos[0] + ", " + pos[1]);
				}
				/*
 				else if (message.equals(Constants.END_TOUR) || message.contains("N")) {

					exploring = false;
					fastestpath = false;
					acknowledge = false;
				}*/
				else {
					acknowledge = false;
					System.out.println("Error.");
				}
				
				if (acknowledge) {
					if ((pos[0] == 1 && pos[1] == 12) || (pos[0] == 17 && pos[1] == 13) || (pos[0] == 18 && pos[1] == 2) || (pos[0] == 2 && pos[1] == 1)){
						sensorMessage = "" + Constants.SENSOR_RANGES[0][1] + "|" + Constants.SENSOR_RANGES[1][1] + "|" + Constants.SENSOR_RANGES[2][1] +
								"|" + Constants.SENSOR_RANGES[3][1] + "|" + Constants.SENSOR_RANGES[4][1] + "|" + Constants.SENSOR_RANGES[5][1] + "|1";
					}
					else if (((pos[0] == 1 && pos[1] == 13) || (pos[0] == 18 && pos[1] == 13) || (pos[0] == 18 && pos[1] == 1)) && count < 1){
						sensorMessage = "" + Constants.SENSOR_RANGES[0][0] + "|" + Constants.SENSOR_RANGES[1][0] + "|" + Constants.SENSOR_RANGES[2][0]
								+ "|" + Constants.SENSOR_RANGES[3][0] + "|" + Constants.SENSOR_RANGES[4][0] + "|" + Constants.SENSOR_RANGES[5][0] + "|1";
						count++;
					}
					else {
						sensorMessage = "100,100,100,100,100,100";
						count = 0;
					}
					try {
						TimeUnit.MILLISECONDS.sleep(1000);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
					server.sendMessage(sensorMessage);
				}
			}
		//System.out.println(server.receiveMessage());
		}

	}

