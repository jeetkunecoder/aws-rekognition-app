package com.admios.rekognition;

import java.util.ArrayList;
import java.util.List;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.Celebrity;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesRequest;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import com.amazonaws.util.IOUtils;

public class Celebrities {
  public List<String> findCelebrity() {
    String photo = "input.jpg";
    ByteBuffer imageBytes = null;
    
    try {
      InputStream inputStream = new FileInputStream(new File(photo));
      imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
    } catch (Exception e) {
      System.out.println("Failed to load file " + photo);
      System.exit(1);
    }
    
    AmazonRekognition amazonRekognition = AmazonRekognitionClientBuilder
        .standard()
        .withRegion(Regions.US_WEST_2)
        .build();
				
    RecognizeCelebritiesRequest request = new RecognizeCelebritiesRequest()
    		.withImage(new Image().withBytes(imageBytes));
    
    System.out.println("Looking for celebrities in image " + photo + "\n");

    RecognizeCelebritiesResult result = amazonRekognition.recognizeCelebrities(request);

    //Display recognized celebrity information
    List<Celebrity> celebs = result.getCelebrityFaces();
    System.out.println(celebs.size() + " celebrity(s) were recognized.\n");
    List<String> metadata = new ArrayList<String>();
    
    for (Celebrity celebrity: celebs) {
    	 // Name
       System.out.println("Celebrity recognized: " + celebrity.getName());
       metadata.add("Celebrity recognized: " + celebrity.getName());
        
       // Id
       System.out.println("Celebrity ID: " + celebrity.getId());
       metadata.add("Celebrity ID: " + celebrity.getId());
      
       // Position
       BoundingBox boundingBox = celebrity.getFace().getBoundingBox();
       System.out.println("position: " +
         boundingBox.getLeft().toString() + " " +
         boundingBox.getTop().toString());
       metadata.add("position: " +
         boundingBox.getLeft().toString() + " " +
         boundingBox.getTop().toString());
      
       // Extra info
       System.out.println("Further information (if available):");
       metadata.add("Further information (if available):");
      
       for (String url: celebrity.getUrls()){
         System.out.println(url);
         metadata.add(url);
       }
       System.out.println();
    }
    
    System.out.println(result.getUnrecognizedFaces().size() + " face(s) were unrecognized.");
    metadata.add(result.getUnrecognizedFaces().size() + " face(s) were unrecognized.");
     
    return metadata;
  }
}
