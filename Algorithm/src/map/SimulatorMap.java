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

import main.*;

public class SimulatorMap extends JFrame {

	private static final long serialVersionUID = 1L;
	private int goal_coverage_perc;
	private int actual_coverage_perc;
	private MapPanel map;
	private boolean isExpSelected;
	private int mapChoice;
	private int steps_per_sec;
	private int time_limit_ms;
	private String mdf_string;
	private String[][] sample_map;
	private boolean startSimulation;
	private boolean startActual;
	private boolean startExp;
	private boolean startFp;

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
		String[] mapChoices = { "Map 1", "Map 2", "Map 3", "Map 4", "Map 5" };
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

		// exploration button
		JButton fpButton = new JButton();
		fpButton.setText("Start Fastest Path");
		settingsContainer.add(fpButton);

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
				}

				if (optionExp.isSelected()) {
					isExpSelected = true;
				} else if (optionFp.isSelected()) {
					isExpSelected = false;
				}

				refreshMap();

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

		setLocationRelativeTo(null);
		setVisible(true);
	}

	// get sample map

	public static String[][] getSampleMap(int mapChoice){
		String[][] temp_sample_map = new String[Constants.HEIGHT][Constants.WIDTH];
		try {
			String path_name = new File("").getAbsolutePath();
//			path_name = System.getProperty("user.dir")+"/Algorithm/src/sample_map/map" +Integer.toString(mapChoice) + ".txt";
//			System.out.println(path_name);
			path_name = "src/sample_map/map" + Integer.toString(mapChoice) + ".txt";
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

	// refresh new map according to map choice
	public void refreshMap() {
		getContentPane().remove(map);
		sample_map = getSampleMap(mapChoice);
		map = new MapPanel(sample_map);
		add(map);
		getContentPane().invalidate();
		getContentPane().validate();
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
}
