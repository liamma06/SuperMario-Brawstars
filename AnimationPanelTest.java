import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.security.Key;
import java.awt.image.*;
import java.awt.event.*;

public class AnimationPanelTest extends JPanel{
    //Properties
    //Images and Terrain Tiles
    //Opponent image
    public Image imgOpponent;
    //Opponent image facing right
    public Image imgOpponentRight;
    //Opponent image facing left
    public Image imgOpponentLeft;
    //Character image (your character)
    public Image imgCharacter;
    //Character image facing right
    public Image imgCharacterRight;
    //Character image facing left
    public Image imgCharacterLeft;
    //Grass image
    public Image imgGrass;
    //Brick image
    public Image imgBrick;
    //Air image
    public Image imgAir;
    //Dirt image
    public Image imgDirt;
    //Pole image
    public Image imgPole;
    //Stair image
    public Image imgStair;
    //Underground dirt image
    public Image imgUndergroundDirt;
    //Hard block image
    public Image imgHardBlock;
    //Flag image
    public Image imgFlag;
    //Terrain image
    public Image imgTerrain;

    //Map
    BufferedReader br;
    public char[][] chrMap;//2D array to hold map layout
    public int intMapWidth = 3600;
    public int intMapHeight =720;
    public int intTilePixels = 36;

    //area that is being viewed
    public double dblViewportX = 0;

    //Character
    public double dblCharacterX;
    public double dblCharacterY; 
    public String strCharacterDir = "right";
    public int intCharacterHP = 3;

    //Opponent 
    public double dblOpponentX = 324;
    public double dblOpponentY = 612;
    public String strOpponentDir = "right";
  

	public int intMapX = 0;
	public int intMapY = 0;

    Timer timer;
    SBSRModelControl model;

    //Methods
    public void paintComponent(Graphics g){
        super.paintComponent(g);
		
		g.drawImage(imgTerrain,(int)(0-dblViewportX), 0, 3600, 720, null);
		
		for (int intCount = 0; intCount < 20; intCount++){
			for (int intCount2 = 0; intCount2 < 100; intCount2++){
					
					
					g.drawImage(imgAir, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
					
					switch(chrMap[intCount2][intCount]){
						//Grass
						case 'g':
							g.drawImage(imgGrass, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
						//Dirt
						case 'd':
							g.drawImage(imgDirt, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
						//Brick
						case 'b':
							g.drawImage(imgBrick, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
						//Air
						case 'a':
							g.drawImage(imgAir, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        case 'p':
							g.drawImage(imgPole, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        case 's':
							g.drawImage(imgStair, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        case 'h':
							g.drawImage(imgHardBlock, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        case 'u':
							g.drawImage(imgUndergroundDirt, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        case 'f':
							g.drawImage(imgFlag, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        }	
			}
		}
		
        //Load and draw character image according to which way he/she is facing.
        if (strCharacterDir.equals("right")){
				imgCharacter = imgCharacterRight;
		} else if (strCharacterDir.equals("left")){
				imgCharacter = imgCharacterLeft;
		} else {
				imgCharacter = imgCharacterRight;
		}

        //load and draw opponent image according to which way he/she is facing.
        if (strOpponentDir.equals("right")){
            imgOpponent = imgOpponentRight;
        } else if (strOpponentDir.equals("left")){
            imgOpponent = imgOpponentLeft;
        }

		if (intCharacterHP > 0){
			g.drawImage(imgCharacter,(int)((dblCharacterX - dblViewportX)), (int)((dblCharacterY)), 36, 36, null);
		} else {
			System.out.println("Character dead");
		}
        g.drawImage(imgOpponent, (int)((dblOpponentX - dblViewportX)), (int)((dblOpponentY)), 36, 36, null);
       
        repaint();
    }
    
    //load Map layout from csv file (Method is called upon in ModelControl)
    public void loadMap(int intMapSelection){
        chrMap = new char[intMapWidth][intMapHeight];
        try{
            if(intMapSelection == 1){
                br = new BufferedReader(new FileReader("Map1.csv"));
                System.out.println("map 1 shown");
                dblCharacterX = 324;
				dblCharacterY = 612; 
				dblViewportX = 0;
				intCharacterHP = 3;
				try{
					imgTerrain = ImageIO.read(new File("Map1Terrain.png"));
				} catch (IOException e){
					System.out.println("Error loading map terrain.");
				}
            }else if(intMapSelection == 2){
                br = new BufferedReader(new FileReader("Map2.csv"));
                System.out.println("map 2 shown");
                dblCharacterX = 324;
                dblCharacterY = 252;
                dblViewportX = 0;
                intCharacterHP = 3;
                try{
					imgTerrain = ImageIO.read(new File("Map2Terrain.png"));
                } catch (IOException e){
					System.out.println("Error loading map terrain.");
				}
            }else if(intMapSelection == 3){
                br = new BufferedReader(new FileReader("Map3.csv"));
                System.out.println("map 3 shown");
                dblCharacterX = 324;
                dblCharacterY = 612;
                dblViewportX = 0;
                intCharacterHP = 3;
                try{
					imgTerrain = ImageIO.read(new File("Map3Terrain.png"));
                } catch (IOException e){
					System.out.println("Error loading map terrain.");
				}
            }
            String line;
            int row = 0;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                for(int col = 0; col < parts.length; col++){
                    chrMap[col][row] = parts[col].charAt(0);
                }
                row++;
            }
            br.close(); 
        }catch(IOException e){
            System.out.println("Error reading map file");
        }catch(NullPointerException e){

        }
        repaint();
    }
    
    //loading the proper character image
    public void loadCharacter(int intCharacterSelection){
        try{
            if(intCharacterSelection == 2){
                imgCharacterRight = ImageIO.read(new File("Dynamike.png"));
                imgCharacterLeft = ImageIO.read(new File("Dynamike(Left).png"));
                System.out.println("loaded dynamike");
            }else if(intCharacterSelection == 1){
                imgCharacterRight = ImageIO.read(new File("Colt.png"));
                imgCharacterLeft = ImageIO.read(new File("Colt(Left).png"));
                System.out.println("loaded colt");
            }
        }catch(IOException e){
            System.out.println("Error loading character image");
        }
        repaint();
    }

    //loading the proper opponent image
    public void loadOpponent(int intOpponentSelection){
        try{
            if(intOpponentSelection == 2){
                imgOpponentRight = ImageIO.read(new File("Dynamike.png"));
                imgOpponentLeft = ImageIO.read(new File("Dynamike(Left).png"));
                System.out.println("loaded dynamike");
            }else if(intOpponentSelection == 1){
                imgOpponentRight = ImageIO.read(new File("Colt.png"));
                imgOpponentLeft = ImageIO.read(new File("Colt(Left).png"));
                System.out.println("loaded colt");
            }
        }catch(IOException e){
            System.out.println("Error loading opponent image");
        }
        repaint();
    }

    //Constructor
    public AnimationPanelTest(){
        //load images  
        try{
            imgDirt = ImageIO.read(new File("Dirt.png"));
            imgGrass = ImageIO.read(new File("Grass.png"));
            imgBrick = ImageIO.read(new File("Brick.png"));
            imgAir = ImageIO.read(new File("Air.png"));
            imgPole = ImageIO.read(new File("Pole.png"));
            imgStair = ImageIO.read(new File("Stair.png"));
            imgUndergroundDirt = ImageIO.read(new File("UGDirt.png"));
            imgHardBlock = ImageIO.read(new File("HardBlock.png"));
            imgFlag = ImageIO.read(new File("Flag.png"));
            
        }catch(IOException e){
            System.out.println("Error loading images");
        }
    }
}
