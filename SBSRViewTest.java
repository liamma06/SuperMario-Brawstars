//SuperBrawlStarsRun SBSR View (Graphical User Interfaces)
//Programmers: Bosco Zhang, Liam Ma, Nihal Sidhu
//Last Modified: June 15, 2024
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

	//Properties   ************************************************************************************************************************
    	
	//Main frame with title
    public JFrame theframe = new JFrame("Super Mario Brawlstars");

    //Main Menu >>>>>>>>>>>>>>>>>>>>>>>>

	//Main menu panel
    public JPanel MenuPanel = new JPanel();
	public JLabel MenuImageLabel;
	public ImageIcon MenuImage;

	//Play button
    public JButton PlayMenuButton;

	//Connect menu button
	public JButton ConnectMenuButton;
	public JButton HelpMenuButton;

	//Connect screen >>>>>>>>>>>>>>>>>>>>>>>>

	//Connect panel where users connect to or create a server
	public JPanel ConnectPanel = new JPanel();

	//Where players enter IP address of host
	public JTextField ipField;

	//Where players enter the port to connect to
	public JTextField portField;

	//Connect button used to connect to a server (as a client) or create one (as a host) by processing their inputted values through the SSM
	public JButton ConnectButton;

	//Back button to be used to go back from the connect panel to the menu panel
	public JButton BackConnectButton;

	//Tells you if you have successfully conected or not
	public JLabel ConnectionStatusLabel;

	//Used to label the username textfield
	public JLabel UsernameLabel;

	//Used to name the IP textfield
	public JLabel IPLabel;

	//Used to name the port textfield
	public JLabel PortLabel;

	//Space where player enters their username
	public JTextField UsernameField;

	//Play screen >>>>>>>>>>>>>>>>>>>>>>>>
	public JPanel PlayPanel = new JPanel();
	public JButton PlayBackButton;

	//Play(Chat screen)
	public JPanel ChatPanel = new JPanel();

	//Text area that displays chat messages
	public JTextArea ChatArea;

	//Scroll pane for the chat
	public JScrollPane ChatScroll;

	//Text field where chat messages are inputted
	public JTextField ChatTextInput;	
		
	//Map Selection Screen >>>>>>>>>>>>>>>>>>>>>>>>

	//Map selection panel
	public JPanel MapPanel = new JPanel();
	
	//Used to name map 1
	public JLabel Map1Label;
	
	//Used to name map 2
	public JLabel Map2Label;
	
	//Button used to select map 1 (Brawl Springs)
	public JButton Map1Button;
		
	//Button used to select map 2 (Spooky Caverns)
	public JButton Map2Button;

	//Character selection screen >>>>>>>>>>>>>>>>>>>>>>>>
	
	//Character selection panel
	public JPanel CharacterPanel = new JPanel();
		
	//Used to name character 1 (Colt)
	public JLabel Character1Label;
		
	//Used to name character 2 (Dynamike)
	public JLabel Character2Label;
		
	//Button used to select character 1 (Colt)
	public JButton Character1Button;
		
	//Button used to select character 2 (Dynamike)
	public JButton Character2Button;
	
	//Label used to display Map 1 Icon
	public JLabel Map1ImageLabel;
	
	//Label used to display Map 2 Icon
	public JLabel Map2ImageLabel;

	//Label used to display Character 1 (Colt) Icon
	public JLabel Character1ImageLabel;
	
	//Label used to display Character 2 (Dynamike) Icon
	public JLabel Character2ImageLabel;

	//Help "screen" >>>>>>>>>>>>>>>>>>>>>>>>
	public JPanel HelpPanel = new JPanel();

	//Animation Panel
	AnimationPanelTest AniPanel = new AnimationPanelTest();

	//Split Pane for game and chat
	public JSplitPane PlaySplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	//Label used to show progressing second-based timer for players
	public JLabel RaceTimerLabel;

	//Death panel
	public JPanel DeathPanel = new JPanel();
	public JLabel DeathLabel;

	//Win panel
	public JPanel WinPanel = new JPanel();
	public JLabel WinLabel;

	//GUI Methods ~ See "SBSRModelControl.java"   ************************************************************************************************************************

	//Constructor
	public SBSRViewTest(){
        

		//Menu options 
		MenuPanel.setPreferredSize(new Dimension(1280,720));
		MenuPanel.setLayout(null);

		//Main Menu Component Location, Size, Font, and Adding Constructor Operations
		//Play menu button	
		PlayMenuButton = new JButton("Play");
		PlayMenuButton.setSize(300,60);
		PlayMenuButton.setLocation(490,350);
		MenuPanel.add(PlayMenuButton);

		//Connect menu button
		ConnectMenuButton = new JButton("Connect");
		ConnectMenuButton.setSize(300,60);
		ConnectMenuButton.setLocation(490,420);
		MenuPanel.add(ConnectMenuButton);

		//Help menu button
		HelpMenuButton = new JButton("Tutorial (Demo)");
		HelpMenuButton.setSize(300,60);
		HelpMenuButton.setLocation(490,490);
		MenuPanel.add(HelpMenuButton);

		//Menu image label
		MenuImageLabel = new JLabel(AniPanel.MenuImage);
		MenuImageLabel.setSize(1280,720);
		MenuImageLabel.setLocation(0, 0);
		MenuPanel.add(MenuImageLabel);
			

		//ConnectPanel Component Location, Size, Font, and Adding Constructor Operations
		ConnectPanel.setLayout(null);

		//Username label
		UsernameLabel = new JLabel("Username:");
		UsernameLabel.setSize(200,50);
		UsernameLabel.setLocation(50,50);
		UsernameLabel.setFont(new Font("Arial", Font.BOLD,30));
		ConnectPanel.add(UsernameLabel);

		//Username field
		UsernameField = new JTextField();
		UsernameField.setSize(250,50);
		UsernameField.setLocation(210,50);
		UsernameField.setFont(new Font("Arial", Font.PLAIN, 30));
		ConnectPanel.add(UsernameField);

		//IP label
		IPLabel = new JLabel("IP:");
		IPLabel.setSize(200,50);
		IPLabel.setLocation(340,150);
		IPLabel.setFont(new Font("Arial", Font.BOLD,30));
		ConnectPanel.add(IPLabel);

		//IP field
		ipField = new JTextField();
		ipField.setSize(500,200);
		ipField.setLocation(100,200);
		ipField.setFont(new Font("Arial", Font.PLAIN, 40));
		ConnectPanel.add(ipField);

		//Port label
		PortLabel = new JLabel("Port:");
		PortLabel.setSize(200,50);
		PortLabel.setLocation(900,150);
		PortLabel.setFont(new Font("Arial", Font.BOLD,30));
		ConnectPanel.add(PortLabel);

		//Port field
		portField = new JTextField();
		portField.setSize(500,200);
		portField.setLocation(680,200);
		portField.setFont(new Font("Arial", Font.PLAIN, 40));
		ConnectPanel.add(portField);

		//Conncect button
		ConnectButton = new JButton("Connect");
		ConnectButton.setSize(300,100);
		ConnectButton.setLocation(490, 500);
		ConnectButton.setFont(new Font("Arial", Font.BOLD, 30));
		ConnectPanel.add(ConnectButton);

		//Connection status label
		ConnectionStatusLabel = new JLabel("");
		ConnectionStatusLabel.setSize(400,100);
		ConnectionStatusLabel.setLocation(850,20);
		ConnectionStatusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		ConnectPanel.add(ConnectionStatusLabel);

		//Back connect button
		BackConnectButton = new JButton("Back");
		BackConnectButton.setSize(200,75);
		BackConnectButton.setLocation(50,550);
		ConnectPanel.add(BackConnectButton);

		//Map Selection Panel Component Location, Size, Font, and Adding Constructor Operations
		MapPanel.setLayout(null);

		//Map 1 Label
		Map1Label = new JLabel("Level 1-1: Brawl Springs");
		Map1Label.setSize(350,50);
		Map1Label.setLocation(160,75);
		Map1Label.setFont(new Font("Arial",Font.BOLD,30));
		MapPanel.add(Map1Label);

		//Map 2 Label
		Map2Label = new JLabel("Level 1-2: Spooky Caverns");
		Map2Label.setSize(400,50);
		Map2Label.setLocation(735,75);
		Map2Label.setFont(new Font("Arial",Font.BOLD,30));
		MapPanel.add(Map2Label);

		//Map 1 button
		Map1Button = new JButton("Select");
		Map1Button.setSize(200,50);
		Map1Button.setLocation(230,550);
		Map1Button.setFont(new Font("Arial",Font.PLAIN,20));
		MapPanel.add(Map1Button);

		//Map 2 button
		Map2Button = new JButton("Select");
		Map2Button.setSize(200,50);
		Map2Button.setLocation(830,550);
		Map2Button.setFont(new Font("Arial",Font.PLAIN,20));
		MapPanel.add(Map2Button);

		//Adding the map icon images

		//Map 1 Image label
		Map1ImageLabel = new JLabel(AniPanel.Map1Image);
		Map1ImageLabel.setSize(375,375);
		Map1ImageLabel.setLocation(145, 135);
		MapPanel.add(Map1ImageLabel);

		//Map 2 Image label
		Map2ImageLabel = new JLabel(AniPanel.Map2Image);
		Map2ImageLabel.setSize(375, 375);
		Map2ImageLabel.setLocation(740, 135);
		MapPanel.add(Map2ImageLabel);

		//Character Selection Panel Component Location, Size, Font, and Adding Constructor Operations
		CharacterPanel.setLayout(null);
			
		//Character 1 label
		Character1Label = new JLabel("Colt");
		Character1Label.setSize(200,50);
		Character1Label.setLocation(300,75);
		Character1Label.setFont(new Font("Arial",Font.BOLD,30));
		CharacterPanel.add(Character1Label);

		//Character 2 label
		Character2Label = new JLabel("Dynamike");
		Character2Label.setSize(200,50);
		Character2Label.setLocation(880,75);
		Character2Label.setFont(new Font("Arial",Font.BOLD,30));
		CharacterPanel.add(Character2Label);

		//Character 1 button
		Character1Button = new JButton("Select");
		Character1Button.setSize(200,50);
		Character1Button.setLocation(250,550);
		Character1Button.setFont(new Font("Arial",Font.PLAIN,20));
		CharacterPanel.add(Character1Button);

		//Character 2 button
		Character2Button = new JButton("Select");
		Character2Button.setSize(200,50);
		Character2Button.setLocation(830,550);
		Character2Button.setFont(new Font("Arial",Font.PLAIN,20));
		CharacterPanel.add(Character2Button);

		//Adding the map icon images
		//Character 1 image
		Character1ImageLabel = new JLabel(AniPanel.Character1Image);
		Character1ImageLabel.setSize(375,375);
		Character1ImageLabel.setLocation(145, 135);
		CharacterPanel.add(Character1ImageLabel);

		//Character 2 image
		Character2ImageLabel = new JLabel(AniPanel.Character2Image);
		Character2ImageLabel.setSize(375, 375);
		Character2ImageLabel.setLocation(740, 135);
		CharacterPanel.add(Character2ImageLabel);

		//Help Panel
		HelpPanel.setLayout(null);


		//Death screen Component Location, Size, Font, and Adding Constructor Operations
		//Death panel
		DeathPanel.setLayout(null);
		DeathPanel.setPreferredSize(new Dimension(880,720));
		DeathPanel.setBackground(Color.BLACK);

		//Death label
		DeathLabel = new JLabel("You lost.");
		DeathLabel.setSize(400,100);
		DeathLabel.setLocation(50, 50);
		DeathLabel.setForeground(Color.RED);
		DeathLabel.setFont(new Font("Arial", Font.BOLD, 50));
		DeathPanel.add(DeathLabel);

		//Win screen Component Location, Size, Font, and Adding Constructor Operations
		//Win panel
		WinPanel.setLayout(null);
		WinPanel.setPreferredSize(new Dimension(880,720));
		WinPanel.setBackground(Color.BLACK);

		//Win label
		WinLabel = new JLabel("You have won!");
		WinLabel.setSize(400,100);
		WinLabel.setLocation(50, 50);
		WinLabel.setForeground(Color.GREEN);
		WinLabel.setFont(new Font("Arial", Font.BOLD, 50));
		WinPanel.add(WinLabel);


		//PlayScreen Component Location, Size, Font, and Adding Constructor Operations
		//Play back button
		PlayBackButton = new JButton ("Back");
		PlayBackButton.setSize(170, 70);
		PlayBackButton.setLocation(350, 600);
		PlayBackButton.setFont(new Font("Arial", Font.BOLD, 30));
		ChatPanel.add(PlayBackButton);
			
		ChatPanel.setLayout(null);
		ChatPanel.setPreferredSize(new Dimension(450,720));

		ChatArea = new JTextArea();
		ChatArea.setEditable(false);

		//chat scroll
		ChatScroll = new JScrollPane(ChatArea);
		ChatScroll.setSize(380,450);
		ChatScroll.setLocation(85,50);
		ChatPanel.add(ChatScroll);
			
		//chat text input
		ChatTextInput = new JTextField();
		ChatTextInput.setSize(386,50);
		ChatTextInput.setLocation(82,500);
		ChatPanel.add(ChatTextInput);

		//Animation Panel Component Location, Size, Font, and Adding Constructor Operations
		AniPanel.setPreferredSize(new Dimension(880,720));
		
		//race timer label
		RaceTimerLabel = new JLabel();
		RaceTimerLabel.setSize(170, 70);
		RaceTimerLabel.setLocation(0,0);
		RaceTimerLabel.setForeground(Color.WHITE);
		RaceTimerLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
		AniPanel.add(RaceTimerLabel);
			

		//Split Pane for game (AniPanel) and chat (ChatPanel)
		//Play split pane
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





 
