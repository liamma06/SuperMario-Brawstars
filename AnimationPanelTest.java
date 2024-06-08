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
    public Image ImgOpponent;
    public Image ImgCharacter;
    public Image ImgGrass;
    public Image ImgBrick;
    public Image ImgAir;
    public Image ImgDirt;

    //Map
    BufferedReader br;
    public char[][] Map;//2D array to hold map layout
    public int MapWidth = 1000;
    public int MapHeight = 20;
    public int TilePixels = 36;

    //area that is being viewed
    public double dblViewportX = 0;
    public double dblViewportY = 0;

    //Character
    public double CharacterX = 360;
    public double CharacterY = 612; 

    //Opponent 
    public double OpponentX = 0;
    public double OpponentY = 0;
  
	public int intMapX = 0;
	public int intMapY = 0;

    Timer timer;
    SBSRModelControl model;

    //Methods
    public void paintComponent(Graphics g){
        super.paintComponent(g);
		
		
		for (int intCount = 0; intCount < 20; intCount++){
			for (int intCount2 = 0; intCount2 < 20; intCount2++){
					
					
					g.drawImage(ImgAir, (int)(dblViewportX +((double)intCount2-1)*36), intCount*36, 36, 36, null);
					
					switch(Map[intCount2][intCount]){
						//Grass
						case 'g':
							g.drawImage(ImgGrass, (int)(dblViewportX +((double)intCount2-1)*36), intCount*36, 36, 36, null);
							break;
						//Dirt
						case 'd':
							g.drawImage(ImgDirt, (int)(dblViewportX +((double)intCount2-1)*36), intCount*36, 36, 36, null);
							break;
						//Brick
						case 'b':
							g.drawImage(ImgBrick, (int)(dblViewportX +((double)intCount2-1)*36), intCount*36, 36, 36, null);
							break;
						//Air
						case 'a':
							g.drawImage(ImgAir, (int)(dblViewportX +((double)intCount2-1)*36), intCount*36, 36, 36, null);
							break;
					}	
					
					
			}
		}
		
		repaint();
		
		        /* 

        //Adjust viewport based on character x position
        ViewportX = (int) CharacterX - ViewportWidth / 2;
        if(ViewportX < 0){
            ViewportX = 0;
        } else if(ViewportX + ViewportWidth > MapWidth ){
            ViewportX = MapWidth - ViewportWidth;
        }

        //Adjust viewport based on character y position
        ViewportY = (int) CharacterY - ViewportHeight / 2;
        if(ViewportY < 0){
            ViewportY = 0;
        } else if(ViewportY + ViewportHeight > MapHeight){
            ViewportY = MapHeight - ViewportHeight;
        }

        //Draw viewport(20x20 portion of map)
        for(int x = 0; x < ViewportWidth; x++){
            for(int y = 0; y < ViewportHeight; y++){
                int mapX = x + ViewportX;
                int mapY = y + ViewportY;

                if(mapX >= 0 && mapX < MapWidth && mapY >= 0 && mapY < MapHeight){
                    g.drawImage(ImgAir, x * TilePixels, y * TilePixels, TilePixels, TilePixels, null);
                    switch(Map[mapX][mapY]){
                        //grass
                        case 'g':
                            g.drawImage(ImgGrass, x * TilePixels, y * TilePixels, TilePixels, TilePixels, null);
                            break;
                        //brick
                        case 'b':
                            g.drawImage(ImgBrick, x * TilePixels, y * TilePixels, TilePixels, TilePixels, null);
                            break;
                        //air
                        case 'a':
                            g.drawImage(ImgAir, x * TilePixels, y * TilePixels, TilePixels, TilePixels, null);
                            break;
                        //dirt
                        case 'd':
							g.drawImage(ImgDirt, x * TilePixels, y * TilePixels, TilePixels, TilePixels, null);
							break;
                    }
                }
            }
        }
        //draw character
        g.drawImage(ImgCharacter,(int)((CharacterX - ViewportX)*TilePixels), (int)((CharacterY - ViewportY)*TilePixels), TilePixels, TilePixels, null);
        
        g.drawImage(ImgOpponent, (int)((OpponentX - ViewportX) * TilePixels), (int)((OpponentY - ViewportY)*TilePixels), TilePixels, TilePixels, null);
    
        repaint();

        */
    }
    
    //load Map layout from csv file
    public void loadMap(int intMapSelection){
        Map = new char[MapWidth][MapHeight];
        try{
            if(intMapSelection == 1){
                br = new BufferedReader(new FileReader("Map1.csv"));
                System.out.println("map 1 shown");
            }else if(intMapSelection == 2){
                br = new BufferedReader(new FileReader("Map2.csv"));
                System.out.println("map 2 shown");
            }
            String line;
            int row = 0;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                for(int col = 0; col < parts.length; col++){
                    Map[col][row] = parts[col].charAt(0);
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
            if(intCharacterSelection == 1){
                ImgCharacter = ImageIO.read(new File("Dynamike.png"));
                System.out.println("loaded dynamike");
            }else if(intCharacterSelection == 2){
                ImgCharacter = ImageIO.read(new File("Colt.jpg"));
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
            if(intOpponentSelection == 1){
                ImgOpponent = ImageIO.read(new File("Dynamike.png"));
                System.out.println("loaded dinamike");
            }else if(intOpponentSelection == 2){
                ImgOpponent = ImageIO.read(new File("Colt.jpg"));
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
            ImgOpponent = ImageIO.read(new File("Shelly.png"));
            ImgCharacter = ImageIO.read(new File("Dynamike.png"));
            ImgDirt = ImageIO.read(new File("Dirt.png"));
            ImgGrass = ImageIO.read(new File("Grass.png"));
            ImgBrick = ImageIO.read(new File("Brick.png"));
            ImgAir = ImageIO.read(new File("Air.png"));
        }catch(IOException e){
            System.out.println("Error loading images");
        }
    }



        
    
    
}
