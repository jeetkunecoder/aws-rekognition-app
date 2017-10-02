package com.admios.rekognition;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class ImageDemo {

	private static final String PATH = "/Users/myusername/Documents/workspace/aws-rekognition-app/input.jpg";

	public static void main(String[] args){
		try {
			Celebrities celebs = new Celebrities();
			List<String> metadata = celebs.findCelebrity();
			new ImageDemo(PATH, metadata);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ImageDemo(final String filename, final List<String> meta) throws Exception {
		SwingUtilities.invokeLater(new Runnable()
		{
	    public void run() {
	    	JFrame editorFrame = new JFrame("Image Demo");
	    	editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    	
	    	BufferedImage image = null;
	    	try {
	    		image = ImageIO.read(new File(filename));
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		System.exit(1);
	    	}
	    	
	    	StringBuilder sb = new StringBuilder();
	    	
	    	for (String s: meta) { 
	    		sb.append(s);
	    		sb.append(System.getProperty("line.separator"));
	    	}
	    	
	    	ImageIcon imageIcon = new ImageIcon(image);
	    	JLabel jLabel = new JLabel();
	    	jLabel.setText(sb.toString());
	    	jLabel.setIcon(imageIcon);
	    	editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);
	        
	    	editorFrame.pack();
	    	editorFrame.setLocationRelativeTo(null);
	    	editorFrame.setVisible(true);
	    }	
		});
	}
}
