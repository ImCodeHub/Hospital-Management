package com.Hospital.Hospital.Management.Service.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;


@Service
public class ImageService {

    @Value("${upload.dir}")
    private String uploadDir;

    // method to save image in directory
    public String saveImage(MultipartFile imageFile) throws IOException{
        if(imageFile!=null && !imageFile.isEmpty()){
            String imageFileName = System.currentTimeMillis()+"_"+imageFile.getOriginalFilename();
            Path path = Paths.get(uploadDir, imageFileName);
            Files.createDirectories(path.getParent());
            byte[] compressedImage = compressImage(imageFile);
            Files.write(path, compressedImage);
            return imageFileName;
            
        }
        return "image file not found.";
    }

    private byte[] compressImage(MultipartFile imageFile) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Thumbnails.of(imageFile.getInputStream())
        .size(800, 800)
        .outputFormat("jpg")
        .outputQuality(0.75)    //Compress to 75% quality
        .toOutputStream(baos);
        return baos.toByteArray();
    }

    // method to get the encoded image from the directory.
    public String getEncodedImageFromDirectory(String imageFile){
        Path imagePath = Paths.get(uploadDir).resolve(imageFile);
        try{
            byte[] imageBytes = Files.readAllBytes(imagePath);
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            return "data:image/jpeg;base64,"+ encodedImage;
        }catch(IOException e){
            return null;
        }
    }

}
