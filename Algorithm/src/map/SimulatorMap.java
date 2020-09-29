package map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.ImageView;

import exploration.Exploration;
import exploration.ExplorationApp;
import fastest_path.FastestPathApp;
import main.*;
import robot.ActualRobot;
import robot.Robot;
import robot.SimulatorRobot;

public class SimulatorMap extends JFrame {

	private static final long serialVersionUID = 1L;
	private int goal_coverage_perc;
	private int actual_coverage_perc;
	private MapPanel map;
	private boolean isExpSelected;
	private int mapChoice;
	private int steps_per_sec;
	private int time_limit_ms;
	private int waypoint_x;
	private int waypoint_y;
	private String mdf_string;
	private String[][] sample_map;
	private boolean startSimulation;
	private boolean startActual;
	private boolean startExp;
	private boolean startFp;
	private ExplorationApp explorationApp;
	private FastestPathApp fastestPathApp;
	private static Robot robot;
	private boolean mapExplored=false;
	public Thread tSimExplore;

	// constructor
	public SimulatorMap() {

		// simulation will only start when button "apply settings" is clicked
		startSimulation = false;

		// actual will only start when button
		startActual = false;

		// default sample map shown is map 1
		mapChoice = 1;

		String[][] sample_map = new String[Constants.HEIGHT][Constants.WIDTH];

		// default sample map shown is map 1
		sample_map = getSampleMap(mapChoice);

		actual_coverage_perc = 0;
		map = new MapPanel(sample_map);

		// simulator frame
		setTitle("Maze Simulator");
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(10000, 10000);

		JPanel settingsContainer = new JPanel();

		add(settingsContainer);

		settingsContainer.setLayout(new GridLayout(0, 1));
		settingsContainer.setBorder(new CompoundBorder(new TitledBorder("Settings"), new EmptyBorder(8, 0, 0, 0)));

		// drop down menu to select map
		JLabel selectMap = new JLabel("Select map:");
		settingsContainer.add(selectMap);
		String[] mapChoices = { "Map 1", "Map 2", "Map 3", "Map 4", "Map 5", "Map 6", "Map 7", "Map 8"};
		final JComboBox<String> mapChoiceMenu = new JComboBox<String>(mapChoices);
		mapChoiceMenu.setBackground(Color.PINK);
		settingsContainer.add(mapChoiceMenu);

//		 radio button style for exploration/fastest path selection
//		JRadioButton optionExp = new JRadioButton("Exploration");
//		JRadioButton optionFp = new JRadioButton("Fastest Path");
//		optionFp.setBounds(100, 120, 140, 40);
//		add(Box.createHorizontalStrut(10));
//
//		ButtonGroup group = new ButtonGroup();
//		group.add(optionExp);
//		group.add(optionFp);
//
//		settingsContainer.add(optionExp);
//		settingsContainer.add(optionFp);
		
		JSeparator s = new JSeparator();
		s.setOrientation(SwingConstants.HORIZONTAL);
		settingsContainer.add(s);
		s.setBounds(0, 0, 1, 1);

		// goal coverage percentage field
		JLabel goalCovPer = new JLabel();
		goalCovPer.setText("Enter goal coverage percentage :");
		settingsContainer.add(goalCovPer);
		JTextField goalCovPerField = new JTextField(5);
		settingsContainer.add(goalCovPerField);

		// steps per second field
		JLabel stepsPerSec = new JLabel();
		stepsPerSec.setText("Enter steps per second :");
		settingsContainer.add(stepsPerSec);
		JTextField stepsPerSecField = new JTextField(5);
		settingsContainer.add(stepsPerSecField);

		// time field
		JLabel timePer = new JLabel();
		timePer.setText("Enter time in mm:ss  :");
		settingsContainer.add(timePer);
		JTextField timeField = new JTextField(5);
		timeField.setBounds(0, 0, 10, 10);
		settingsContainer.add(timeField);

		//waypoint x field
		JLabel waypointX = new JLabel();
		waypointX.setText("Enter waypoint (x) :");
		settingsContainer.add(waypointX);
		JTextField waypointXField = new JTextField(5);
		waypointXField.setBounds(0, 0, 10, 10);
		settingsContainer.add(waypointXField);

		//waypoint y field
		JLabel waypointY = new JLabel();
		waypointY.setText("Enter waypoint (y) :");
		settingsContainer.add(waypointY);
		JTextField waypointYField = new JTextField(5);
		waypointYField.setBounds(0, 0, 10, 10);
		settingsContainer.add(waypointYField);

		// mdf string
		JLabel mdfString = new JLabel();
		mdfString.setText("Enter MDF string  :");
		settingsContainer.add(mdfString);
		JTextField mdfField = new JTextField(5);
		mdfField.setBounds(0, 0, 10, 10);
		settingsContainer.add(mdfField);
		
		JSeparator s1 = new JSeparator();
		s1.setOrientation(SwingConstants.HORIZONTAL);
		settingsContainer.add(s1);
		s1.setBounds(0, 0, 1, 1);
		
		// exploration button
		JButton expButton = new JButton();
		expButton.setText("Start Exploration");
		settingsContainer.add(expButton);

		// fastest path button
		JButton fpButton = new JButton();
		fpButton.setText("Start Fastest Path");
		settingsContainer.add(fpButton);

		JSeparator s2 = new JSeparator();
		s2.setOrientation(SwingConstants.HORIZONTAL);
		settingsContainer.add(s2);
		s2.setBounds(0, 0, 1, 1);

		// print status button
		JButton statusButton = new JButton();
		statusButton.setText("Print Status");
		settingsContainer.add(statusButton);

		//print settings info
		JLabel statusInfo = new JLabel();
		statusInfo.setText("Status Info :");
		settingsContainer.add(statusInfo);
		JTextField statusInfoField = new JTextField(5);
		statusInfoField.setBounds(0, 0, 10, 50);
		settingsContainer.add(statusInfoField);

//		// apply settings button
//		JButton applySettings = new JButton("Apply");
//		applySettings.setBounds(100, 120, 140, 40);
//		applySettings.setBackground(Color.PINK);
//		settingsContainer.add(applySettings);
//
//		// stop button
//		JButton stop = new JButton("Stop");
//		stop.setBounds(100, 120, 140, 40);
//		stop.setBackground(Color.PINK);
//		settingsContainer.add(stop);

		// add map
		add(map);

		// mouse listener for apply settings button
		expButton.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent me) {
				startExp = true;
				System.out.println(startExp);
				
				// control goal coverage percentage
				goal_coverage_perc = Integer.parseInt(goalCovPerField.getText());

				// control time limit for simulator
				String time_limit_raw = timeField.getText();
				String[] parts = time_limit_raw.split(":");
				int mins = Integer.parseInt(parts[0]);
				int sec = Integer.parseInt(parts[1]);
				time_limit_ms = (mins * 60 + sec) * 1000;

				//waypoint x
				waypoint_x = Integer.parseInt(waypointXField.getText());

				//waypoint y
				waypoint_y = Integer.parseInt(waypointYField.getText());

				// map descriptor format string
				mdf_string = mdfField.getText();

				// control steps per second of robot
				steps_per_sec = Integer.parseInt(stepsPerSecField.getText());

				// control map choice
				String mapChoiceStr = mapChoiceMenu.getItemAt(mapChoiceMenu.getSelectedIndex());
				switch (mapChoiceStr) {
				case "Map 1":
					mapChoice = 1;
					break;
				case "Map 2":
					mapChoice = 2;
					break;
				case "Map 3":
					mapChoice = 3;
					break;
				case "Map 4":
					mapChoice = 4;
					break;
				case "Map 5":
					mapChoice = 5;
					break;
				case "Map 6":
					mapChoice = 6;
					break;
				case "Map 7":
					mapChoice = 7;
					break;
				case "Map 8":
					mapChoice = 8;
					break;
				}

				/*
				if (optionExp.isSelected()) {
					isExpSelected = true;
				} else if (optionFp.isSelected()) {
					isExpSelected = false;
				}*/



				refreshMap();
				MapPanel simulateMap = new MapPanel(SimulatorMap.getSampleMap(mapChoice));

				for (int i = 0; i < 3; i++) {
					for (int r = 0; r < 3; r++) {
						map.setExploredForGridCell(i, r, true);
					}
				}

                robot = new SimulatorRobot(map, steps_per_sec, simulateMap);
                startExploration(robot, time_limit_ms, goal_coverage_perc, steps_per_sec, false);
                mapExplored=true;
                actual_coverage_perc =  (int) robot.getMap().getActualPerc();


				System.out.println("Goal coverage percentage: " + goal_coverage_perc);
				System.out.println("Actual coverage percentage: " + actual_coverage_perc);
				System.out.println("Is exploring: " + isExpSelected);
				System.out.println("Map choice: " + mapChoice);
				System.out.println("Steps per second: " + steps_per_sec);
				System.out.println("Time limit in ms: " + time_limit_ms);
				System.out.println("MDF String: " + mdf_string);

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

		});

        fpButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
				if (mapExplored){
					//+ waypoint set
					map = robot.getMap();
					map.setWayPoint(getWaypointX(), getWaypointY());
					System.out.println("x map" + map.getWayPoint()[0] +" "+map.getWayPoint()[1]);
					robot.setMap(map);
					System.out.println("x" + robot.getMap().getWayPoint()[0] +" "+robot.getMap().getWayPoint()[1]);
					startFastestPath();
				}
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


		statusButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				statusInfoField.setText("Is exploring: " + isExpSelected + " , " + "Actual coverage percentage: " + actual_coverage_perc + " , " + "MDF String: " + mdf_string);
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// get sample map

	public static String[][] getSampleMap(int mapChoice){
		String[][] temp_sample_map = new String[Constants.HEIGHT][Constants.WIDTH];
		try {
			String path_name = new File("").getAbsolutePath();
			path_name = System.getProperty("user.dir")+"/src/sample_map/map" +Integer.toString(mapChoice) + ".txt";
//			System.out.println(path_name);
			//path_name = "src/sample_map/map" + Integer.toString(mapChoice) + ".txt";
			//C:\Users\CeciliaLee\IdeaProjects\MDP24\Algorithm\src\sample_map\map2.txt
			File myObj = new File(path_name);
			Scanner myReader = new Scanner(myObj);
			int col = 0;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] arrOfStr = data.split("");
				for (int row = 0; row < arrOfStr.length; row++) {
					temp_sample_map[row][col] = arrOfStr[row];
				}
				col++;
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return temp_sample_map;
	}


    public void startExploration(Robot robot, int time, int percentage, int speed, boolean image_recognition){

		if (tSimExplore == null || !tSimExplore.isAlive()) {
			tSimExplore = new Thread(new ExplorationApp(robot, time,percentage, speed, image_recognition));
			//tSimExplore.start();
			System.out.println("start simulator map once");
		} else {
			tSimExplore.interrupt();
		}

		/*
		if (!tSimExplore.isAlive()){
			this.robot = ExplorationApp.getRobot();
			System.out.println("ROBOT IS PASSED TO FASTEST PATH");
		}*/


        //get robot this might have problem because asychronous
//        this.robot = explorationApp.getRobot();
    }

    public void startFastestPath(){
		/*
		map = Exploration.getMap();
		robot.setMap(map);*/
		map = robot.getMap();
		System.out.println("fastest path map");
		for (int col = 0; col < 15; col++) {
			for (int row = 0; row < 20; row++){
				printGridCell(map.getGridCell(row, col));
			}
			System.out.println();
		}
		fastestPathApp = new FastestPathApp(robot);
        fastestPathApp.start();
    }

	// refresh new map according to map choice
	public void refreshMap() {
		getContentPane().remove(map);
		sample_map = getSampleMap(1);
		map = new MapPanel(sample_map);
		add(map);
		getContentPane().invalidate();
		getContentPane().validate();
	}

	public void printGridCell(GridCell gridCell) {
		// O for obstacle
		// E for explored
		// U for unexplored
		if (gridCell.getObstacle()) {
			System.out.print("O");
		} else if (gridCell.getExplored()) {
			System.out.print("E");
		} else {
			System.out.print("U");
		}
	}

	// getters and setters
	public void setGoalCoveragePerc(int goal_coverage_perc) {
		this.goal_coverage_perc = goal_coverage_perc;
	}

	public int getGoalCoveragePerc() {
		return goal_coverage_perc;
	}

	public void setActualCoveragePerc(int actual_coverage_perc) {
		this.actual_coverage_perc = actual_coverage_perc;
	}

	public int getActualCoveragePerc() {
		return actual_coverage_perc;
	}

	public void setMap(MapPanel map) {
		this.map = map;
	}

	public MapPanel getMap() {
		return map;
	}

	public boolean getIsExpSelected() {
		return isExpSelected;
	}

	public void setMapChoice(int mapChoice) {
		this.mapChoice = mapChoice;
	}

	public int getMapChoice() {
		return mapChoice;
	}

	public void setStepsPerSec(int steps_per_sec) {
		this.steps_per_sec = steps_per_sec;
	}

	public int getStepsPerSec() {
		return steps_per_sec;
	}

	public void setTimeLimitMs(int time_limit_ms) {
		this.time_limit_ms = time_limit_ms;
	}

	public int getTimeLimitMs() {
		return time_limit_ms;
	}

	public void setStartSimulation(boolean startSimulation) {
		this.startSimulation = startSimulation;
	}

	public boolean getStartSimulation() {
		return startSimulation;
	}

	public boolean getStartActual() {
		return startActual;
	}
	
	public boolean getStartExp() {
		return startExp;
	}

	public boolean getStartFp() {
		return startFp;
	}

	public int getWaypointX() {
		return waypoint_x;
	}

	public int getWaypointY() {
		return waypoint_y;
	}

	public static Robot getRobot() {
		return robot;
	}

	public static void setRobot(Robot robot) {
		SimulatorMap.robot = robot;
	}
}
