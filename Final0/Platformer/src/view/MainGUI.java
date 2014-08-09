package view;


import java.io.File;

import javax.swing.JFrame;

import controller.UserInput;


public class MainGUI {
	private static JFrame frame;
	
	public static void display() {
		frame = new JFrame("Mario");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String dir = new File("").getAbsolutePath();
		UserInput.addLevel(frame,dir + "/maps/simple.txt",dir+"/images/backgrounds/clouds.jpg");
		frame.setSize(585,8*33+33);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		display();
	}
}
