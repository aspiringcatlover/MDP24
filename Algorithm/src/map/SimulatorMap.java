package map;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SimulatorMap extends JFrame {

	private int goal_coverage_perc;
	private int actual_coverage_perc;
	private MapPanel map;

	// constructor
	public SimulatorMap(String[][] sample_map) {
		actual_coverage_perc = 0;
		map = new MapPanel(sample_map);
		// simulator frame
		setTitle("Maze Simulator");
		setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(10000, 10000);
		add(map);

		// goal coverage percentage field
		JLabel goalCovPer = new JLabel();
		goalCovPer.setText("Enter goal coverage percentage :");
		add(goalCovPer);
		JTextField goalCovPerField = new JTextField(10);
		add(goalCovPerField);

		// goal coverage percentage field
		JLabel stepsPerSec = new JLabel();
		stepsPerSec.setText("Enter steps per second :");
		add(stepsPerSec);
		JTextField stepsPerSecField = new JTextField(10);
		add(stepsPerSecField);

		// goal coverage percentage field
		JLabel timePer = new JLabel();
		timePer.setText("Enter time in mm:ss  :");
		add(timePer);
		JTextField timeField = new JTextField(10);
		add(timeField);

		// submit button
		JButton submit = new JButton("Submit");
		submit.setBounds(100, 120, 140, 40);
		add(submit);

		// action listener for submit button
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println(goalCovPerField.getText());
				Scanner input1 = new Scanner(System.in);
				goal_coverage_perc = input1.nextInt();
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

}
