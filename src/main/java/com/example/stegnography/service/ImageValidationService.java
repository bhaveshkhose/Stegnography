
package com.example.stegnography.service;

import com.example.stegnography.exception.InvalidImageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

@Service
public class ImageValidationService {

    private static final long MAX_FILE_SIZE = 10L * 1024 * 1024;

    public BufferedImage validateImage(MultipartFile file) {

        // 1. Check whether file exists
        if (file == null || file.isEmpty()) {
            throw new InvalidImageException("Image file is empty");
        }

        // 2. Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new InvalidImageException(
                    "Image size must not exceed 10 MB"
            );
        }

        // 3. Validate actual image format and content
        try (ImageInputStream input =
                     ImageIO.createImageInputStream(file.getInputStream())) {

            if (input == null) {
                throw new InvalidImageException("Invalid image file");
            }

            Iterator<ImageReader> readers =
                    ImageIO.getImageReaders(input);

            if (!readers.hasNext()) {
                throw new InvalidImageException(
                        "Uploaded file is not a valid image"
                );
            }

            ImageReader reader = readers.next();

            try {
                reader.setInput(input);

                // Only PNG is accepted
                if (!"png".equalsIgnoreCase(reader.getFormatName())) {
                    throw new InvalidImageException(
                            "Only PNG images are allowed"
                    );
                }

                BufferedImage image = reader.read(0);

                if (image == null) {
                    throw new InvalidImageException(
                            "Unable to decode image"
                    );
                }

                return image;

            } finally {
                reader.dispose();
            }

        } catch (IOException e) {
            throw new InvalidImageException(
                    "Unable to read image: " + e.getMessage()
            );
        }
    }

    public int characterLimit(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();

        int totalBit = width * height * 3;

        return totalBit/8 ;
    }
}
