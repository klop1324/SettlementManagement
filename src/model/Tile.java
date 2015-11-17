package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Tile {
	//===============================
	//==> DO NOT SKIP NUMBERS!!!! <==
	//===============================
	OIL(0, 'o'), PLATING(1, ' '), GRAVEL(2, 'G');
	
	int intRep;
	char charRep;
	Image image;
	
	Tile(int i, char c){
		this.intRep = i;
		this.charRep = c;
		
		try {
			this.image = ImageIO.read(new File("ImageSet/"+ this.intRep +".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public int getIntRepresentation(){
		return intRep;
	}
	public char getCharRepresentation(){
		return charRep;
	}
	public Image getImage(){
		return image;
	}
}
