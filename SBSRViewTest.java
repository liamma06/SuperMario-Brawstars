//SuperBrawlStarsRun SBSR View (Graphical User Interfaces)
//Programmers: Bosco Zhang, Liam Ma, Nihal Sidhu
//Last Modified: Thursday, June 6, 2024
//Version Number: 2.0 Beta

//Importing Java Swing and GUI toolkits for graphical setup
import java.io.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.awt.*;

//Defining
public class SBSRViewTest{

		//Properties
    	public JFrame theframe = new JFrame("Super Mario Brawlstars");

    	//Main Menu
    	public JPanel MenuPanel = new JPanel();
    	public JButton PlayMenuButton;
		public JButton ConnectMenuButton;
		public JButton HelpMenuButton;

		//Connect screen
		public JPanel ConnectPanel = new JPanel();
		public JTextField ipField;
		public JTextField portField;
		public JButton ConnectButton;
		public JButton BackConnectButton;
		public JLabel ConnectionStatusLabel;
		public JLabel UsernameLabel;
		public JLabel IPLabel;
		public JLabel PortLabel;
		public JTextField UsernameField;

		//Play screen
		public JPanel PlayPanel = new JPanel();
		public JButton PlayBackButton;
		public JButton DemoOkButton;

		//Play(Chat screen)
		public JPanel ChatPanel = new JPanel();

		//Area that displays chat messages
		public JTextArea ChatArea;

		//Scroll pane for the chat
		public JScrollPane ChatScroll;

		//Text field where chat messages are inputted
		public JTextField ChatTextInput;	
		
		//Map Selection Screen
		public JPanel MapPanel = new JPanel();
		public JLabel Map1Label;
		public JLabel Map2Label;
		public JButton Map1Button;
		public JButton Map2Button;
		public JLabel Map1ImageLabel;
		public JLabel Map2ImageLabel;
		public ImageIcon Map1Image;
		public ImageIcon Map2Image;

		//Character selection screen
		public JPanel CharacterPanel = new JPanel();
		public JLabel Character1Label;
		public JLabel Character2Label;
		public JButton Character1Button;
		public JButton Character2Button;    
		public JLabel Character1ImageLabel;
		public JLabel Character2ImageLabel;
		public ImageIcon Character1Image;
		public ImageIcon Character2Image;

		//Help screen
		public JPanel HelpPanel = new JPanel();

		//Animation Panel
		AnimationPanelTest AniPanel = new AnimationPanelTest();

		//Split Pane for game and chat
		public JSplitPane PlaySplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		public JLabel RaceTimerLabel;

		//Death panel
		public JPanel DeathPanel = new JPanel();
		public JLabel DeathLabel;

		//Win panel
		public JPanel WinPanel = new JPanel();
		public JLabel WinLabel;

		//GUI Methods ~ See "SBSRModelControl.java"

		//Constructor
		public SBSRViewTest(){
        

			//Menu options 
			MenuPanel.setPreferredSize(new Dimension(1280,720));
			MenuPanel.setLayout(null);
        

			PlayMenuButton = new JButton("Play");
			PlayMenuButton.setSize(300,60);
			PlayMenuButton.setLocation(490,400);
			MenuPanel.add(PlayMenuButton);

			ConnectMenuButton = new JButton("Connect");
			ConnectMenuButton.setSize(300,60);
			ConnectMenuButton.setLocation(490,500);
			MenuPanel.add(ConnectMenuButton);

			HelpMenuButton = new JButton("Tutorial (Demo)");
			HelpMenuButton.setSize(300,60);
			HelpMenuButton.setLocation(490,600);
			MenuPanel.add(HelpMenuButton);

			//ConnectPanel
			ConnectPanel.setLayout(null);

			UsernameLabel = new JLabel("Username:");
			UsernameLabel.setSize(200,50);
			UsernameLabel.setLocation(50,50);
			UsernameLabel.setFont(new Font("Arial", Font.BOLD,30));
			ConnectPanel.add(UsernameLabel);

			UsernameField = new JTextField();
			UsernameField.setSize(250,50);
			UsernameField.setLocation(210,50);
			UsernameField.setFont(new Font("Arial", Font.PLAIN, 30));
			ConnectPanel.add(UsernameField);

			IPLabel = new JLabel("IP:");
			IPLabel.setSize(200,50);
			IPLabel.setLocation(340,150);
			IPLabel.setFont(new Font("Arial", Font.BOLD,30));
			ConnectPanel.add(IPLabel);

			ipField = new JTextField();
			ipField.setSize(500,200);
			ipField.setLocation(100,200);
			ipField.setFont(new Font("Arial", Font.PLAIN, 40));
			ConnectPanel.add(ipField);

			PortLabel = new JLabel("Port:");
			PortLabel.setSize(200,50);
			PortLabel.setLocation(900,150);
			PortLabel.setFont(new Font("Arial", Font.BOLD,30));
			ConnectPanel.add(PortLabel);

			portField = new JTextField();
			portField.setSize(500,200);
			portField.setLocation(680,200);
			portField.setFont(new Font("Arial", Font.PLAIN, 40));
			ConnectPanel.add(portField);

			ConnectButton = new JButton("Connect");
			ConnectButton.setSize(300,100);
			ConnectButton.setLocation(490, 500);
			ConnectButton.setFont(new Font("Arial", Font.BOLD, 30));
			ConnectPanel.add(ConnectButton);

			ConnectionStatusLabel = new JLabel("");
			ConnectionStatusLabel.setSize(400,100);
			ConnectionStatusLabel.setLocation(850,20);
			ConnectionStatusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			ConnectPanel.add(ConnectionStatusLabel);

			BackConnectButton = new JButton("Back");
			BackConnectButton.setSize(200,75);
			BackConnectButton.setLocation(50,550);
			ConnectPanel.add(BackConnectButton);

			//Map Selection Panel
			MapPanel.setLayout(null);

			Map1Label = new JLabel("Map 1");
			Map1Label.setSize(200,50);
			Map1Label.setLocation(300,75);
			Map1Label.setFont(new Font("Arial",Font.BOLD,30));
			MapPanel.add(Map1Label);

			Map2Label = new JLabel("Map 2");
			Map2Label.setSize(200,50);
			Map2Label.setLocation(880,75);
			Map2Label.setFont(new Font("Arial",Font.BOLD,30));
			MapPanel.add(Map2Label);

			Map1Button = new JButton("Select");
			Map1Button.setSize(200,50);
			Map1Button.setLocation(250,550);
			Map1Button.setFont(new Font("Arial",Font.PLAIN,20));
			MapPanel.add(Map1Button);

			Map2Button = new JButton("Select");
			Map2Button.setSize(200,50);
			Map2Button.setLocation(830,550);
			Map2Button.setFont(new Font("Arial",Font.PLAIN,20));
			MapPanel.add(Map2Button);

			//adding the map icon images
			try {
            	Map1Image = new ImageIcon(ImageIO.read(new File("Map1Icon.png")));
            	Map2Image = new ImageIcon(ImageIO.read(new File("Map2Icon.png")));
			} catch (IOException e) {
				System.out.println("Error loading map images.");
			}

			Map1ImageLabel = new JLabel(Map1Image);
			Map1ImageLabel.setSize(375,375);
			Map1ImageLabel.setLocation(145, 135);
			MapPanel.add(Map1ImageLabel);

			Map2ImageLabel = new JLabel(Map2Image);
			Map2ImageLabel.setSize(375, 375);
			Map2ImageLabel.setLocation(740, 135);
			MapPanel.add(Map2ImageLabel);

			//Character Selection Panel 
			CharacterPanel.setLayout(null);

			Character1Label = new JLabel("Colt");
			Character1Label.setSize(200,50);
			Character1Label.setLocation(300,75);
			Character1Label.setFont(new Font("Arial",Font.BOLD,30));
			CharacterPanel.add(Character1Label);

			Character2Label = new JLabel("Dynamike");
			Character2Label.setSize(200,50);
			Character2Label.setLocation(880,75);
			Character2Label.setFont(new Font("Arial",Font.BOLD,30));
			CharacterPanel.add(Character2Label);

			Character1Button = new JButton("Select");
			Character1Button.setSize(200,50);
			Character1Button.setLocation(250,550);
			Character1Button.setFont(new Font("Arial",Font.PLAIN,20));
			CharacterPanel.add(Character1Button);

			Character2Button = new JButton("Select");
			Character2Button.setSize(200,50);
			Character2Button.setLocation(830,550);
			Character2Button.setFont(new Font("Arial",Font.PLAIN,20));
			CharacterPanel.add(Character2Button);

			//adding the map icon images
			try {
            	Character1Image = new ImageIcon(ImageIO.read(new File("Character1.png")));
            	Character2Image = new ImageIcon(ImageIO.read(new File("Character2.png")));
			} catch (IOException e) {
				System.out.println("Error loading map images.");
			}

			Character1ImageLabel = new JLabel(Character1Image);
			Character1ImageLabel.setSize(375,375);
			Character1ImageLabel.setLocation(145, 135);
			CharacterPanel.add(Character1ImageLabel);

			Character2ImageLabel = new JLabel(Character2Image);
			Character2ImageLabel.setSize(375, 375);
			Character2ImageLabel.setLocation(740, 135);
			CharacterPanel.add(Character2ImageLabel);

			//Help Panel
			HelpPanel.setLayout(null);


			//Death screen
			DeathPanel.setLayout(null);
			DeathPanel.setPreferredSize(new Dimension(880,720));
			DeathPanel.setBackground(Color.BLACK);

			DeathLabel = new JLabel("You lost.");
			DeathLabel.setSize(400,100);
			DeathLabel.setLocation(50, 50);
			DeathLabel.setForeground(Color.RED);
			DeathLabel.setFont(new Font("Arial", Font.BOLD, 50));
			DeathPanel.add(DeathLabel);

			//Win screen
			WinPanel.setLayout(null);
			WinPanel.setPreferredSize(new Dimension(880,720));
			WinPanel.setBackground(Color.BLACK);

			WinLabel = new JLabel("You have won!");
			WinLabel.setSize(400,100);
			WinLabel.setLocation(50, 50);
			WinLabel.setForeground(Color.GREEN);
			WinLabel.setFont(new Font("Arial", Font.BOLD, 50));
			WinPanel.add(WinLabel);


			//PlayScreen Components
			PlayBackButton = new JButton ("Back");
			PlayBackButton.setSize(170, 70);
			PlayBackButton.setLocation(350, 600);
			PlayBackButton.setFont(new Font("Arial", Font.BOLD, 30));
			ChatPanel.add(PlayBackButton);
			
			DemoOkButton = new JButton("Ok");
			DemoOkButton.setSize(170, 70);
			DemoOkButton.setLocation(50, 600);
			DemoOkButton.setFont(new Font("Arial", Font.BOLD, 30));
			DemoOkButton.setEnabled(false);
			DemoOkButton.setVisible(false);
			ChatPanel.add(DemoOkButton);
			
			ChatPanel.setLayout(null);
			ChatPanel.setPreferredSize(new Dimension(450,720));

			ChatArea = new JTextArea();
			ChatArea.setEditable(false);

			ChatScroll = new JScrollPane(ChatArea);
			ChatScroll.setSize(380,450);
			ChatScroll.setLocation(85,50);
			ChatPanel.add(ChatScroll);
			
			ChatTextInput = new JTextField();
			ChatTextInput.setSize(386,50);
			ChatTextInput.setLocation(82,500);
			ChatPanel.add(ChatTextInput);

			//Animation Panel
			AniPanel.setPreferredSize(new Dimension(880,720));
			
			RaceTimerLabel = new JLabel();
			RaceTimerLabel.setSize(170, 70);
			RaceTimerLabel.setLocation(0,0);
			RaceTimerLabel.setForeground(Color.WHITE);
			RaceTimerLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
			AniPanel.add(RaceTimerLabel);
			

			//Split Pane for game and chat
			PlaySplitPane.setLeftComponent(AniPanel);
			PlaySplitPane.setRightComponent(ChatPanel);
			PlaySplitPane.setDividerLocation(720);
			PlaySplitPane.setOneTouchExpandable(false);
			PlaySplitPane.setDividerSize(0);
			
			theframe.setContentPane(MenuPanel);
			theframe.pack();
			theframe.setResizable(false);
			theframe.setVisible(true);
			theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}





 
