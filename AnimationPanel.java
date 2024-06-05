import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class AnimationPanel extends JPanel{ 
    //Properties
    //Images
    public Image ImgCharacter;
    public Image ImgGrass;
    public Image ImgBrick;
    public Image ImgAir;

    //Map
    public char[][] Map;//2D array to hold map layout
    public int MapWidth = 1000;
    public int MapHeight = 20;
    public int TilePixels = 36;

    //area that is being viewed
    public int ViewportX;
    public int ViewportY;
    public int ViewportWidth = 20;
    public int ViewportHeight = 20;

    //Character
    public int CharacterX;
    public int CharacterY; 

    Timer timer;
    
    //Methods
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,400,540);
        //draw visible map
        for(int x = 0; x < ViewportWidth; x++){
            for(int y = 0; y < ViewportHeight; y++){
                int mapX = x + CharacterX - ViewportWidth / 2;
                int mapY = y + CharacterY - ViewportHeight / 2;
                if(mapX >= 0 && mapX < MapWidth && mapY >= 0 && mapY < MapHeight){
                    g.drawImage(ImgAir, x * TilePixels, y * TilePixels, TilePixels, TilePixels, null);
                    switch(Map[mapX][mapY]){
                        case 'g':
                            g.drawImage(ImgGrass, x * TilePixels, y * TilePixels, TilePixels, TilePixels, null);
                            break;
                        case 'b':
                            g.drawImage(ImgBrick, x * TilePixels, y * TilePixels, TilePixels, TilePixels, null);
                            break;
                        case'a':
                            g.drawImage(ImgAir, x * TilePixels, y * TilePixels, TilePixels, TilePixels, null);
                            break;
                    }
                }
            }
        }
        //draw character
        g.drawImage(ImgCharacter, CharacterX * TilePixels, CharacterY * TilePixels, TilePixels, TilePixels, null);
        repaint();
    }

    //Constructor
    public AnimationPanel(){
        //load images
        try{
            //ImgCharacter = ImageIO.read(new File("Character.png"));
            //ImgGrass = ImageIO.read(new File("Grass.png"));
            //ImgBrick = ImageIO.read(new File("Brick.png"));
            ImgAir = ImageIO.read(new File("Air.png"));
        }catch(IOException e){
            System.out.println("Error loading images");
        }

        //load Map layout from csv file
        Map = new char[MapWidth][MapHeight];
        try{
            BufferedReader br = new BufferedReader(new FileReader("Map1.csv"));
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
        }
        repaint();
        
    }
}