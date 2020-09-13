package map;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SimulatorMap extends JFrame {

	private int goal_coverage_perc;
	private int actual_coverage_perc;
	private MapPanel map;
	//exploration = true
	//fastest path = false
	private boolean isExpSelected;
	private int mapChoice;

	// constructor
	public SimulatorMap(String[][] sample_map) {
		
		actual_coverage_perc = 0;
		map = new MapPanel(sample_map);
		// simulator frame
		setTitle("Maze Simulator");
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(10000, 10000);
		add(map);
		
		//radio button style for exploration/fastest path selection
		JRadioButton optionExp = new JRadioButton("Exploration");
        JRadioButton optionFp = new JRadioButton("Fastest Path");
        optionFp.setBounds(100, 120, 140, 40);
        add(Box.createHorizontalStrut(10));
        
        ButtonGroup group = new ButtonGroup();
        group.add(optionExp);
        group.add(optionFp);
 
        FlowLayout layout1 = new FlowLayout();
        layout1.setHgap(10);
        setLayout(layout1);
 
        add(optionExp);
        add(optionFp);
 
        pack();
		
        
		//selection button style for exploration/fastest path selection
//		// exploration selection button
//		JButton exploration = new JButton("Exploration");
//		exploration.setBounds(100, 120, 140, 40);
//		add(exploration);
//		
//		// fastest path selection button
//		JButton fastestPath = new JButton("Fastest Path");
//		fastestPath.setBounds(100, 120, 140, 40);
//		add(fastestPath);
//		
//		
//		// mouse listener for exploration selection button
//		exploration.addMouseListener(new MouseListener() {
//			@Override
//			public void mousePressed(MouseEvent me) {
//				exploration.setBackground(Color.GREEN);
//				isExpSelected = true;
//			}
//
//			@Override
//			public void mouseClicked(MouseEvent e) {
//
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//
//			}
//		});
//		setLocationRelativeTo(null);
//		setVisible(true);
//		
//		// mouse listener for fastest path selection button
//				fastestPath.addMouseListener(new MouseListener() {
//					@Override
//					public void mousePressed(MouseEvent me) {
//						fastestPath.setBackground(Color.GREEN);
//						isExpSelected = false;
//					}
//
//					@Override
//					public void mouseClicked(MouseEvent e) {
//
//					}
//
//					@Override
//					public void mouseReleased(MouseEvent e) {
//
//					}
//
//					@Override
//					public void mouseEntered(MouseEvent e) {
//
//					}
//
//					@Override
//					public void mouseExited(MouseEvent e) {
//
//					}
//				});
//				setLocationRelativeTo(null);
//				setVisible(true);

		// goal coverage percentage field
		JLabel goalCovPer = new JLabel();
		goalCovPer.setText("Enter goal coverage percentage :");
		add(goalCovPer);
		JTextField goalCovPerField = new JTextField(10);
		add(goalCovPerField);

		// steps per second field
		JLabel stepsPerSec = new JLabel();
		stepsPerSec.setText("Enter steps per second :");
		add(stepsPerSec);
		JTextField stepsPerSecField = new JTextField(10);
		add(stepsPerSecField);

		// time field
		JLabel timePer = new JLabel();
		timePer.setText("Enter time in mm:ss  :");
		add(timePer);
		JTextField timeField = new JTextField(10);
		add(timeField);
		
		//drop down menu to select map
		JLabel selectMap = new JLabel("Select map:");
        add(selectMap);
        String[] mapChoices = {"Map 1", "Map 2", "Map 3", "Map 4", "Map 5"};
        final JComboBox<String> mapChoiceMenu = new JComboBox<String>(mapChoices);
        add(mapChoiceMenu);

		// apply settings button
		JButton applySettings = new JButton("Apply Settings");
		applySettings.setBounds(100, 120, 140, 40);
		add(applySettings);

		// mouse listener for apply settings button
		applySettings.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent me) {
				goal_coverage_perc = Integer.parseInt(goalCovPerField.getText());
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
				} 
				else if (optionFp.isSelected()) {
					isExpSelected = false;
				}
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

	public boolean getOption() {
		return isExpSelected;
	}
}
