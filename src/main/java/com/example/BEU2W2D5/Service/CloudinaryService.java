package com.example.BEU2W2D5.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private static Cloudinary cloudinary;

    public static void deleteImageByUrl(String imageUrl) {
        try {
            Map result = cloudinary.uploader().destroy(getPublicIdFromUrl(imageUrl), ObjectUtils.emptyMap());
            System.out.println(result.get("result"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problemi nell'eliminazione dell'immagine dal server");
        }
    }

    private static String getPublicIdFromUrl(String imageUrl) {
        int startIndex = imageUrl.lastIndexOf("/") + 1;
        int endIndex = imageUrl.lastIndexOf(".");
        return imageUrl.substring(startIndex, endIndex);
    }
}