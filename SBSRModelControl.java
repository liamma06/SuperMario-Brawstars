//SuperBrawlStarsRun SBSR ModelControl (Logic, Computation, Network, Algorithm, and Data File Processing)
//Programmers: Bosco Zhang, Liam Ma, Nihal Sidhu
//Last Modified: Monday, June 10, 2024
//Version Number: 2.0 Beta

import java.awt.*;
import java.io.*;
import java.util.Map;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class SBSRModelControl extends JPanel implements ActionListener, KeyListener{
	//Properties **************************************************************************************************************

	//Connection
	/**Tells user things like host/client status and errors in the connection process when connect buton is pressed */
	public String strConnectionResult;

	/**The name the host enters in connection process*/
	public String strHostUsername;

	/**The name the client enters in connection process */
	public String strClientUsername;

	/**The IP of the computer */
	public String strIp;

	/**Port of the computer */
	public String strPort;

	/**The entered username */
	public String strUsername;

	/**The result of the connection method */
	public String strResult;

	/**Used to see who is host for extra privileges */
	public boolean blnHost = false;

	/**Used to see if jumping or not */
	public boolean blnjump = false;

	/**Used to count the number of players */
	public int intNumPlayers = 0;

	/**Cooldown for jump */
	public int intJumpCooldown = 0;

	//Chacter selection
	/**Sets which character the host is */
	public int intHostCharacter = 0;

	/**Sets which character the client is */
	public int intClientCharacter = 0;


	//Players entered
	/**Checks to see if the host is ready */
	public boolean blnHostReady = false;

	/**Checks to see if the client is ready */
	public boolean blnClientReady = false;
	
	/**Checks to see if the user is connected to a server. */
	public boolean blnConnect = false;

	//Play
	/**The number of players ready */
	public int intPlayersReady = 0;

	/**Timer to count the seconds it takes for players to reach end of map */
	public int intRaceTime = 0;

	//splitting ssm messages -> mode(chat/charcter/play/game/connection),user(host/client),action(message/game input),xcord,ycord
	
	/**Messages you send over SuperSocketMaster */
	String[] ssmMessage;

	/**Calls on SBSRViewTest */
	SBSRViewTest view;

	/**Calls on SuperSocketMaster */
	SuperSocketMaster ssm;

	//AnimationPanel

	/**Timer that runs 60fps Animations */
	public Timer theTimer = new Timer(1000/60,this);

	/**Timer that runs every second to count how long character takes to reach the end */
	public Timer RaceTimer = new Timer (1000, this);

	/**Character displacement in X */
	public double dblCharacterDefX = 0;

	/**Character displacement in Y */
	public double dblCharacterDefY = 0;
	
	/**Integer value for the y-coordinate component denoting the bottom point of the pole */
	public int intEndY = 0;
	
	//Demo Tutorial
	
	/**Communicates to the code whether the player is in demo mode */
	public boolean blnDemo = false;


	//Methods **************************************************************************************************************

	//setting up connection

	/**Method used to host and connect client to host*/
	public String connect(String ipField, String portField, String UsernameField){

		//If IP and Port Fields are left blank print connection result
		if(ipField.equals("") && portField.equals("")){
			strConnectionResult = "Enter a port number and/or IP Address\n";
		//If Username Field is left blank print connection result
		}else if(ipField.equals("") && !portField.equals("") && UsernameField.equals("")){
			strConnectionResult = "Enter Username";
		//Connecting the host 
		}else if(ipField.equals("") && !portField.equals("") && !UsernameField.equals("")){
			//ConnectionStatusLabel.setText("Starting chat in server mode\n");
			try{
				ssm = new SuperSocketMaster(Integer.parseInt(portField),this);
				ssm.connect();
				//Set host username to username entered
				strHostUsername = UsernameField;
				System.out.println("Host: "+strHostUsername);
				strConnectionResult = "(HOST) Your IP:" + ssm.getMyAddress();
				//You are host
				blnHost = true;
				//Enable play back button
				view.PlayBackButton.setEnabled(true);
				//Number of players are 1
				intNumPlayers = 1;
				//Disable username field
				view.UsernameField.setEnabled(false);
				//You are connected
				blnConnect = true;
				//Disable help menu button
				view.HelpMenuButton.setEnabled(false);
			}catch(NumberFormatException e){
				strConnectionResult = "Invalid Port Number";
			}
		//If username is left blank print connection result
		}else if(!ipField.equals("") && !portField.equals("") && UsernameField.equals("")){
			strConnectionResult = "Enter Username";
		//Connecting the Client
		}else if(!ipField.equals("") && !portField.equals("")&& !UsernameField.equals("")){
			//ConnectionStatusLabel.setText("Starting chat in client  mode\n");
			try{
				ssm = new SuperSocketMaster(ipField,Integer.parseInt(portField),this);
				ssm.connect();
				//Set client username to entered username
				strClientUsername = UsernameField;
				System.out.println("Client: " +strClientUsername);
				strConnectionResult = "(Client) Connected to: " + ipField;
				//You are not host
				blnHost = false;
				//disable play back button
				view.PlayBackButton.setEnabled(false);
				//increase number of players by 1
				intNumPlayers +=1;
				//disable username field
				view.UsernameField.setEnabled(false);
				//You have connected
				blnConnect = true;
				//disable help menu button
				view.HelpMenuButton.setEnabled(false);
			}catch(NumberFormatException e){
				strConnectionResult = "Invalid Port Number";
			}
		//If IP is entered but port is not entered
		}else if(!ipField.equals("") && portField.equals("")){
			strConnectionResult.equals("Need a portnumber or port/ip \n");
		}
		return strConnectionResult;
	}

	// method when someone died
	/**Method to run when player dies */
	public void playerDied(String strplayerUsername){
		//send message to chat
		intRaceTime = 0;
		ssm.sendText("server,death,"+strplayerUsername);
		view.ChatArea.append("[ Server ]: "+strplayerUsername + " has died\n");
		theTimer.stop();
		RaceTimer.stop();
		//Show death panel on left screen
		view.PlaySplitPane.setLeftComponent(view.DeathPanel);
		view.PlaySplitPane.setDividerLocation(720);
		view.theframe.revalidate();	
	}

	// method when someone reached the end
	/**Method to run when player reaches the end */
	public void playerReachedEnd(String strplayerUsername){
		//send message to chat
		
		if (ssm != null){
			ssm.sendText("server,win,"+strplayerUsername+","+intRaceTime);
		}
		if (blnDemo != true){
			view.ChatArea.append("[ Server ]: "+strplayerUsername + " has reached the end in "+intRaceTime+" s\n");
		} else if (blnDemo == true){
			view.ChatArea.append("[ Server ]: You have reached the end!\n");
		}
		//stop the timers
		theTimer.stop();
		RaceTimer.stop();
		//set left part of screen to win screen
		view.PlaySplitPane.setLeftComponent(view.WinPanel);
		view.PlaySplitPane.setDividerLocation(720);
		view.theframe.revalidate();	
		intRaceTime = 0;
	}

	//check play method
	/**Used to check if both players are ready so game can begin */
	public void checkPlay(){
		//If there are 2 ready players
		if(intPlayersReady == 2){
			//Host is ready
			blnHostReady = true;
			//Client is ready
			blnClientReady = true;
			System.out.println("Both players are ready");
		}
	}
	
	//Player movement 

	/**Checks which keys are pressed and excecutes code such as movement depending on which key is pressed */
	public void keyPressed(KeyEvent evt){

		//checking if key is pressed

		//return focus to animation panel when enter is pressed 
		if(evt.getSource() == view.ChatTextInput && evt.getKeyCode() == KeyEvent.VK_ENTER){
			view.AniPanel.requestFocusInWindow();
			return;
		}

		//Character movement 
		if(evt.getKeyCode() == KeyEvent.VK_UP){
			if (view.AniPanel.dblCharacterX < 3168 && view.AniPanel.dblCharacterY <=684){
				view.AniPanel.grabFocus();
				System.out.println("Key pressed JUMP: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
				intJumpCooldown++;
				dblCharacterDefY = -36;
			}
		//If the character is between two blocks, then both blocks underneath/above must be air in order for the character to move vertically.

		}else if(evt.getKeyCode() == KeyEvent.VK_LEFT){
			if (view.AniPanel.dblCharacterX < 3168){
				view.AniPanel.grabFocus();
				view.AniPanel.strCharacterDir = "left";
				System.out.println("Key pressed LEFT: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
				dblCharacterDefX = -6;
			}
		}else if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
			if (view.AniPanel.dblCharacterX < 3168){
				view.AniPanel.grabFocus();
				view.AniPanel.strCharacterDir = "right";
				System.out.println("Key pressed RIGHT: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
				dblCharacterDefX = 6;
			}
		}else{
			System.out.println("Invalid key");
		}
	}
	/**Checks which key is typed and focuses back to animation panel when enter is typed */
	public void keyTyped(KeyEvent evt){
		
		//return focus to animation panel when enter is pressed 
		if(evt.getSource() == view.ChatTextInput && evt.getKeyCode() == KeyEvent.VK_ENTER){
			view.AniPanel.requestFocusInWindow();
			return;
		}
	}
	
	/**Checks keys released and stops player movement */
	public void keyReleased(KeyEvent evt) {
		
		//checking if key is released
		
		if (evt.getKeyCode() == KeyEvent.VK_UP){
			blnjump = false;
			intJumpCooldown = 0;
			dblCharacterDefY = 0;
			
		}else if(evt.getKeyCode() == KeyEvent.VK_LEFT){
			view.AniPanel.grabFocus();
			System.out.println("Key pressed: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
			dblCharacterDefX = 0;
			
			try{
				ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+",left");
			} catch (NullPointerException e){
				System.out.println("Single Player Mode does not support socket networking.");
			}
			
		}else if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
			view.AniPanel.grabFocus();
			System.out.println("Key pressed: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
			dblCharacterDefX = 0;
			
			if (ssm != null){
				ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+",right");
			}
		}else{
			System.out.println("Invalid key");
		}

    }

  
	//Overriding action listener
	/**Overrides  action listener and check things like button presses*/
	public void actionPerformed(ActionEvent evt){
		//Connect menu button
		if(evt.getSource() == view.ConnectMenuButton){
			view.theframe.setContentPane(view.ConnectPanel);
			view.theframe.revalidate();
		//Back button
		
		//If back button on the play panel is pressed
		}else if(evt.getSource() == view.BackConnectButton){
			//Go back to menu panel
			view.theframe.setContentPane(view.MenuPanel);
			view.theframe.revalidate();
			
		//If connect button is pressed in the connect panel
		}else if(evt.getSource() == view.BackConnectButton){
			//Go back to menu panel
			view.theframe.setContentPane(view.MenuPanel);
			view.theframe.revalidate();
	
		}else if(evt.getSource() == view.ConnectButton){
			//Get entered IP
			strIp = view.ipField.getText();
			//Get entered port
			strPort = view.portField.getText();
			//Get entered username
			strUsername = view.UsernameField.getText();
			//getting the status result from the connect method
			String strResult = connect(strIp, strPort, strUsername);
			view.ConnectionStatusLabel.setText(strResult);

		
		//If PlayMenuButton on menu panel is pressed

		//Play menu button
		}else if(evt.getSource() == view.PlayMenuButton){
			//If you are host
			if(blnHost){
				//Go to map panel
				view.theframe.setContentPane(view.MapPanel);
				view.theframe.revalidate();
			} else{
				System.out.println("You are not Host or have not connected yet");
			}
		
		//Map selection
		//Level 1
		}else if (evt.getSource() == view.Map1Button){
			//Load map 1
			view.AniPanel.loadMap(1);
			ssm.sendText("Map,1");
			//Go to character panel
			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			ssm.sendText("character");
			intEndY = 612;
			ssm.sendText("EndY,"+intEndY);
			blnDemo = false;
			dblCharacterDefX = 0.0;
			dblCharacterDefY = 0.0;
			view.ChatArea.setText("");
		//Level 2
		}else if(evt.getSource() == view.Map2Button){
			//Load map 2
			view.AniPanel.loadMap(2);
			ssm.sendText("Map,2");
			//Go to character panel
			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			ssm.sendText("character");
			intEndY = 504;
			ssm.sendText("EndY,"+intEndY);
			blnDemo = false;
			dblCharacterDefX = 0.0;
			dblCharacterDefY = 0.0;
			view.ChatArea.setText("");
		
		//Tutorial (Demo) Map
		}else if(evt.getSource()==view.HelpMenuButton && blnConnect != true){
			//Load map 3
			view.AniPanel.loadMap(3);
			//Go to character panel
			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			theTimer.restart();
			view.ChatArea.setText("");
			
			//Automatically assigning "Colt" as the character.
			if (blnHost){
				intHostCharacter = 1;
			} else {
				intClientCharacter = 1;
			}
			//Load fist character
			view.AniPanel.loadCharacter(1);
			//Go to play split pane
			view.theframe.setContentPane(view.PlaySplitPane);
			view.theframe.revalidate();
			view.AniPanel.requestFocusInWindow();
			view.AniPanel.setFocusable(true);
			//Add key listener
			view.AniPanel.addKeyListener(this);
			//Do not show chat input
			view.ChatTextInput.setVisible(false);
			//Do not enable chat input
			view.ChatTextInput.setEnabled(false);
			//Enable play back button
			view.PlayBackButton.setEnabled(true);
			intJumpCooldown = 0;
			blnjump = false;
			intEndY=612;
			dblCharacterDefX = 0.0;
			dblCharacterDefY = 0.0;
			blnDemo = true;
			
			//Clearing chat for Demo Server Instructions
			view.ChatArea.setText("");
			view.ChatArea.setFont(new Font("Arial", Font.PLAIN, 12));
			view.ChatArea.append("\n");
			view.ChatArea.append("   [ Shelly ]: Howdy there NOOB! I'm Shelly, one of the\n");
			view.ChatArea.append("                    best brawlers in town!\n\n");
			view.ChatArea.append("   [ Shelly ]: Think you got what it takes? Follow my lead \n");
			view.ChatArea.append("                    and you'll become a PRO brawler in no time!\n\n ");
			view.ChatArea.append("   [ Server ]: Shelly wants you to meet her at the end of the map...\n");
			view.ChatArea.append("                    Keep right.\n");
			
			
			//Have a series of bricks blocking the character until they finish the task that is assigned."
			
			
			
		//Character selection
		}else if((evt.getSource() == view.Character1Button)){
			intRaceTime = 0;
			view.RaceTimerLabel.setText(String.valueOf(intRaceTime)+" s elapsed");
			if(blnHost){
				intHostCharacter = 1;
				System.out.println("Host Character: Colt");
			}else{
				intClientCharacter = 1;
				System.out.println("Client Character: Colt");
			}
			//Load charcter 1
			view.AniPanel.loadCharacter(1);
			ssm.sendText("characterChosen,1");
			//Show play split pane
			view.theframe.setContentPane(view.PlaySplitPane);
			view.theframe.revalidate();
			view.AniPanel.requestFocusInWindow();
        	view.AniPanel.setFocusable(true);
			view.AniPanel.addKeyListener(this);
			
			ssm.sendText("connection,"+strUsername);

			intPlayersReady++;

			view.ChatArea.append("[ Server ]: "+strUsername + " has connected\n");
			view.ChatArea.append("[ Server ]: "+intPlayersReady + " players connected\n");
			//run check play method above
			checkPlay();
			theTimer.restart();
			RaceTimer.restart();
			intJumpCooldown = 0;
			blnjump = false;
			
			
		}else if((evt.getSource() == view.Character2Button)){
			intRaceTime = 0;
			view.RaceTimerLabel.setText(String.valueOf(intRaceTime)+" s elapsed");
			
			if(blnHost){
				intHostCharacter = 2;
				System.out.println("Host Character: Dynamike");
			}else{
				intClientCharacter = 2;
				System.out.println("Client Character: Dynamike");
			}
			view.AniPanel.loadCharacter(2);
			ssm.sendText("characterChosen,2");
			//Show play split plane
			view.theframe.setContentPane(view.PlaySplitPane);
			view.theframe.revalidate();
			view.AniPanel.requestFocusInWindow();
        	view.AniPanel.setFocusable(true);
			view.AniPanel.addKeyListener(this);

			ssm.sendText("connection,"+strUsername);

			intPlayersReady++;

			view.ChatArea.append("[ Server ]: "+strUsername + " has connected\n");
			view.ChatArea.append("[ Server ]: "+intPlayersReady + " players connected\n");
			checkPlay();
			theTimer.restart();
			RaceTimer.restart();
			intJumpCooldown = 0;
			blnjump = false;
			
			
		//Text input Chat
		}else if(evt.getSource() == view.ChatTextInput){
			if(blnHost == true){
				ssm.sendText("chat,"+strHostUsername+","+view.ChatTextInput.getText());
				view.ChatArea.append(strHostUsername+": "+view.ChatTextInput.getText()+ "\n");
				view.ChatTextInput.setText("");
			}else if(blnHost == false){
				ssm.sendText("chat,"+strClientUsername+","+view.ChatTextInput.getText());
				view.ChatArea.append(strClientUsername+": "+view.ChatTextInput.getText()+ "\n");
				view.ChatTextInput.setText("");
			}
		//Animation Timer
		}else if(evt.getSource() == theTimer){
			blnjump = false;
			if(view.AniPanel.dblCharacterY >= 36 && intJumpCooldown < 4 && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY - 36)/36))] == 'a' && view.AniPanel.chrMap[(int) (Math.ceil((view.AniPanel.dblCharacterX)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY - 36)/36))] == 'a' && (view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY+36)/36))] != 'a' || view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY+36)/36))] != 'a')){
				view.AniPanel.dblCharacterY = view.AniPanel.dblCharacterY + dblCharacterDefY;
				blnjump = true;
			} else if (intJumpCooldown > 3){
				blnjump = false;
			}
			
			if (this.ssm != null){
				ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY);
			}
			
			if(view.AniPanel.chrMap != null && blnjump == false && (view.AniPanel.dblCharacterY) < view.AniPanel.intMapHeight-6 && view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY + 6)/36))] == 'a' && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY + 6)/36))] == 'a'){
				view.AniPanel.dblCharacterY = view.AniPanel.dblCharacterY + 6;
				if (ssm != null){
					ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY);
				}
			}
				
			//Bypass the border when the character falls out of the map to incite death 
				
			if (view.AniPanel.dblCharacterY >= 684 && view.AniPanel.dblCharacterY < 720){
				view.AniPanel.dblCharacterY = view.AniPanel.dblCharacterY + 6;
				if (ssm != null){
					ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY);
				}
			} else if (view.AniPanel.dblCharacterY >= 720 && blnDemo != true){
				view.AniPanel.intCharacterHP = 0;
				playerDied(strUsername);
				//Kill character
			} else if (view.AniPanel.dblCharacterY >= 720 && blnDemo == true){
				view.AniPanel.dblViewportX = 0;
				view.AniPanel.dblCharacterX = 324;
				view.AniPanel.dblCharacterY = 612;
			}

			//checking if bottom of pole is reached
			if(((int) view.AniPanel.dblCharacterX) == 3168 && ((int) view.AniPanel.dblCharacterY) == intEndY){
				System.out.println("end is reached");
				playerReachedEnd(strUsername);
			}

			if (view.AniPanel.strCharacterDir == "right"){
				if (view.AniPanel.dblCharacterX < 3168 && view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a' && view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a'){
					if (blnjump == true){
						if (view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY-6)/36))] == 'a'){
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						}
					} else if (blnjump != true && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))]=='a'){
						if (view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))] == 'a'){
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						}
					} else {
						view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
						view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
					}
				} 
			} else if (view.AniPanel.strCharacterDir == "left"){
				if (view.AniPanel.dblCharacterX > 324 && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a' && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a'){
					if (blnjump == true){
						if (view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY-6)/36))] == 'a'){
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						} 
					} else if (blnjump != true && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))]=='a'){
						if (view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))] == 'a'){
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						}
					} else {
						view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
						view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
					}
				}
			} 
			
			repaint();
			
			
		//Detecting Second-Based Signals from Race Timer and Counting them
		} else if (evt.getSource() == RaceTimer){
			intRaceTime++;
			view.RaceTimerLabel.setText(String.valueOf(intRaceTime)+" s elapsed");
		
		//Detecting SSM 
		}else if(evt.getSource() == ssm){
			ssmMessage = ssm.readText().split(",");
			//sending both host and client to the play screen
			if(ssmMessage[0].equals("character")){
				view.theframe.setContentPane(view.CharacterPanel);
				view.theframe.revalidate();
				System.out.println("Host has selected map, character selection");
			//Getting chat responses
			}else if(ssmMessage[0].equals("chat")){
					view.ChatArea.append(ssmMessage[1] + ": " + ssmMessage[2] + "\n");
			//reseting game for both players
			}else if(ssmMessage[0].equals("reset")){
				view.PlaySplitPane.setLeftComponent(view.AniPanel);
				view.PlaySplitPane.setDividerLocation(720);
				view.ChatArea.setText("");
				view.theframe.setContentPane(view.MenuPanel);
				view.theframe.revalidate();

				view.theframe.setContentPane(view.MenuPanel);
				view.theframe.revalidate();
			//getting server responses for player death and win
			}else if(ssmMessage[0].equals("server")){
				if(ssmMessage[1].equals("death")){
					view.ChatArea.append("[ Server ]: "+ssmMessage[2] + " has died\n");
				}else if(ssmMessage[1].equals("win")){
					view.ChatArea.append("[ Server ]: "+ssmMessage[2] + " has reached the end in "+ssmMessage[3]+" s\n");
					theTimer.stop();
					view.PlaySplitPane.setLeftComponent(view.DeathPanel);
					view.PlaySplitPane.setDividerLocation(720);
					view.theframe.revalidate();	
				}
			//Getting connection responses
			}else if(ssmMessage[0].equals("connection")){
				intPlayersReady += 1;
				view.ChatArea.append(ssmMessage[1] + " has connected\n");
				System.out.println("Players Ready: "+intPlayersReady);
				checkPlay();
			//Getting position updates of the opponent
			}else if(ssmMessage[0].equals("position")){
				view.AniPanel.dblOpponentX = Double.parseDouble(ssmMessage[1]);
				view.AniPanel.dblOpponentY = Double.parseDouble(ssmMessage[2]);
				if(ssmMessage.length == 4){
					view.AniPanel.strOpponentDir = ssmMessage[3];
				}else{
					view.AniPanel.strOpponentDir = view.AniPanel.strOpponentDir;
				}
			//loading the proper map for both players 
			}else if(ssmMessage[0].equals("Map")){
				view.AniPanel.loadMap(Integer.parseInt(ssmMessage[1]));
			//loading the proper character for both players
			}else if(ssmMessage[0].equals("characterChosen")){
				view.AniPanel.loadOpponent(Integer.parseInt(ssmMessage[1]));
			}//getting the y-coordinate of the end of the pole
			else if(ssmMessage[0].equals("EndY")){
				intEndY = Integer.parseInt(ssmMessage[1]);
			}
			else{
				System.out.println("Invalid SSM message");
			}
			
		} else if (evt.getSource() == view.PlayBackButton){
			view.PlaySplitPane.setLeftComponent(view.AniPanel);
			view.PlaySplitPane.setDividerLocation(720);
			view.theframe.setContentPane(view.MenuPanel);
			view.theframe.revalidate();

			if (ssm != null){
				ssm.sendText("reset");
			}
			
			intPlayersReady--;
			try{
				ssm.sendText("chat,[ Server ], "+strHostUsername+" left\n");
			} catch (NullPointerException e){
				System.out.println("Single Player Mode does not support socket networking.");
			}
			view.ChatArea.append("[ Server ]: "+strHostUsername+" left\n");
			intRaceTime = 0;
			
			//Re-Checking to disable "back button" for clients since the back button is enabled in Tutorial Mode (Single Player)
			if (blnHost != true){
				view.PlayBackButton.setEnabled(false);
			}
			dblCharacterDefX = 0.0;
			dblCharacterDefY = 0.0;
		}
	}

	//Constructor  **************************************************************************************************************
	/**Adding action listeners from the view */
	public SBSRModelControl(SBSRViewTest view){
		this.view = view;

		// Adding Event listeners

		//Main Menu
		view.PlayMenuButton.addActionListener(this);
		view.ConnectMenuButton.addActionListener(this);
		view.HelpMenuButton.addActionListener(this);
		
		//Connect Page
		view.ConnectButton.addActionListener(this);
		view.BackConnectButton.addActionListener(this);

		//Map page
		view.Map1Button.addActionListener(this);
		view.Map2Button.addActionListener(this);

		//Character page
		view.Character1Button.addActionListener(this);
		view.Character2Button.addActionListener(this);

		//Chat
		view.ChatTextInput.addActionListener(this);
		view.ChatTextInput.addKeyListener(this);
		view.PlayBackButton.addActionListener(this);
		
	}

	//Main program
	/** Executing main program */
	public static void main(String[] args){
		SBSRViewTest view = new SBSRViewTest();
		new SBSRModelControl(view);
	}
	
}
