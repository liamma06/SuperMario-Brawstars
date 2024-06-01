import java.io.*;
import java.awt.event.*;

public class SBSRModelControl implements ActionListener{
	//Properties

	//Connection
	public String strConnectionResult;
	public String strHostUsername;
	public String strClientUsername;
	public boolean blnHost = false;
	public int intNumPlayers = 0;
	public String strIp;
	public String strPort;
	public String strUsername;
	public String strResult;

	//splitting ssm messages -> mode(chat/play/game),user(host/client),action(message/game input),xcord,ycord
	String[] ssmMessage;

	SBSRViewTest view;
	SuperSocketMaster ssm;
	

	
	//Methods

	//setting up connection
	public String connect(String ipField, String portField, String UsernameField){
		if(ipField.equals("") && portField.equals("")){
			strConnectionResult = "Enter a port number and/or IP Address\n";
		}else if(ipField.equals("") && !portField.equals("") && UsernameField.equals("")){
			strConnectionResult = "Enter Username";
		}else if(ipField.equals("") && !portField.equals("") && !UsernameField.equals("")){
			//ConnectionStatusLabel.setText("Starting chat in server mode\n");
			try{
				ssm = new SuperSocketMaster(Integer.parseInt(portField),this);
				ssm.connect();
				strHostUsername = UsernameField;
				System.out.println("Host: "+strHostUsername);
				strConnectionResult = "(HOST) Your IP:" + ssm.getMyAddress();
				blnHost = true;
				intNumPlayers = 1;
				view.UsernameField.setEnabled(false);
			}catch(NumberFormatException e){
				strConnectionResult = "Invalid Port Number";
			}
		}else if(!ipField.equals("") && !portField.equals("") && UsernameField.equals("")){
			strConnectionResult = "Enter Username";
		}else if(!ipField.equals("") && !portField.equals("")&& !UsernameField.equals("")){
			//ConnectionStatusLabel.setText("Starting chat in client  mode\n");
			try{
				ssm = new SuperSocketMaster(ipField,Integer.parseInt(portField),this);
				ssm.connect();
				strClientUsername = UsernameField;
				System.out.println("Client: " +strClientUsername);
				strConnectionResult = "(Client) Connected to: " + ipField;
				blnHost = false;
				intNumPlayers +=1;
				view.UsernameField.setEnabled(false);
			}catch(NumberFormatException e){
				strConnectionResult = "Invalid Port Number";
			}
		}else if(!ipField.equals("") && portField.equals("")){
			strConnectionResult.equals("Need a portnumber or port/ip \n");
		}
		return strConnectionResult;
	}

	//overridding action listener
	public void actionPerformed(ActionEvent evt){
		//Connect menu button
		if(evt.getSource() == view.ConnectMenuButton){
			view.theframe.setContentPane(view.ConnectPanel);
			view.theframe.revalidate();
		//back button
		}else if(evt.getSource() == view.BackButton){
			view.theframe.setContentPane(view.MenuPanel);
			view.theframe.revalidate();
		//Help Menu button
		}else if(evt.getSource() == view.HelpMenuButton){
			view.theframe.setContentPane(view.HelpPanel);
		//Connect button
		}else if(evt.getSource() == view.ConnectButton){
			strIp = view.ipField.getText();
			strPort = view.portField.getText();
			strUsername = view.UsernameField.getText();
			//getting the status result from the connect method
			String strResult = connect(strIp, strPort, strUsername);
			view.ConnectionStatusLabel.setText(strResult);
		//Play menu button
		}else if(evt.getSource() == view.PlayMenuButton){
			if(blnHost){
				view.theframe.setContentPane(view.MapPanel);
				view.theframe.revalidate();
			} else{
				System.out.println("You are not Host or have not connected yet");
			}
		//Map selection
		}else if (evt.getSource() == view.Map1Button || evt.getSource() == view.Map2Button){
			view.theframe.setContentPane(view.PlayPanel);
			view.theframe.revalidate();
			ssm.sendText("play");
		//Text input 
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
		//Detecting SSM 
		}else if(evt.getSource() == ssm){
			ssmMessage = ssm.readText().split(",");
			if(ssmMessage[0].equals("play")){
				view.theframe.setContentPane(view.PlayPanel);
				view.theframe.revalidate();
				System.out.println("Host has selected map, Game starts");
			}else if(ssmMessage[0].equals("chat")){
				view.ChatArea.append(ssmMessage[1] + ": " + ssmMessage[2] + "\n");
			}
		}
	}

	//Constructor
	public SBSRModelControl(SBSRViewTest view){
		this.view = view;

		// adding Action listeners

		//Main Menu
		view.PlayMenuButton.addActionListener(this);
		view.ConnectMenuButton.addActionListener(this);
		view.HelpMenuButton.addActionListener(this);
		
		//Connect Page
		view.ConnectButton.addActionListener(this);
		view.BackButton.addActionListener(this);

		//Map page
		view.Map1Button.addActionListener(this);
		view.Map2Button.addActionListener(this);

		//Help page
		view.HelpMenuButton.addActionListener(this);

		//Chat
		view.ChatTextInput.addActionListener(this);
	}

	//Main program
	public static void main(String[] args){
		SBSRViewTest view = new SBSRViewTest();
		new SBSRModelControl(view);
	}
	
}
