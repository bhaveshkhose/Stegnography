package com.example.stegnography.service;

import com.example.stegnography.request.ExtractMsgRequest;
import com.example.stegnography.request.InsertMsgRequest;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class StegnoService {


    //to set the bits in the binary
    public static int setLSB(int value , int bit ){
        return (value & ~1) | bit ;
    }

    //for inserting the msg inside the image
    public String replaceBits(InsertMsgRequest insertMsgRequest) throws IOException {


        // Convert message into bytes
        byte[] msg =
                insertMsgRequest.getMassage().getBytes(StandardCharsets.UTF_8);

        int bitSize = msg.length * 8;
        int bitIndex = 0;

        for (int i = 0;
             i < insertMsgRequest.getImage().getHeight() && bitIndex < bitSize;
             i++) {

            for (int j = 0;
                 j < insertMsgRequest.getImage().getWidth() && bitIndex < bitSize;
                 j++) {

                int rgb = insertMsgRequest.getImage().getRGB(j, i);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Insert bit into RED
                if (bitIndex < bitSize) {

                    int byteIndex = bitIndex / 8;
                    int bitPosition = 7 - (bitIndex % 8);

                    int bit =
                            (msg[byteIndex] >> bitPosition) & 1;

                    r = setLSB(r, bit);

                    bitIndex++;
                }

                // Insert bit into GREEN
                if (bitIndex < bitSize) {

                    int byteIndex = bitIndex / 8;
                    int bitPosition = 7 - (bitIndex % 8);

                    int bit =
                            (msg[byteIndex] >> bitPosition) & 1;

                    g = setLSB(g, bit);

                    bitIndex++;
                }

                // Insert bit into BLUE
                if (bitIndex < bitSize) {

                    int byteIndex = bitIndex / 8;
                    int bitPosition = 7 - (bitIndex % 8);

                    int bit =
                            (msg[byteIndex] >> bitPosition) & 1;

                    b = setLSB(b, bit);

                    bitIndex++;
                }

                // Create modified RGB
                int newRgb =
                        (rgb & 0xFF000000)
                                | (r << 16)
                                | (g << 8)
                                | b;

                insertMsgRequest.getImage().setRGB(j, i, newRgb);
            }
        }

        // Check whether the image had enough capacity
        if (bitIndex < bitSize) {
            return "Message is too large for this image";
        }

        // Save encoded image
        Path outputPath =
                Paths.get("uploads/encoded/encoded_" + UUID.randomUUID() + ".png");

        Files.createDirectories(outputPath.getParent());

        ImageIO.write(insertMsgRequest.getImage(), "png", outputPath.toFile());

        return "Message inserted successfully. File saved at: "
                + outputPath;
    }

    //for extracting the bits for the image
    public String extractMsg(ExtractMsgRequest extractMsgRequest) throws IOException {

        BufferedImage image = extractMsgRequest.getImage();

        StringBuilder message = new StringBuilder();

        int bitIndex = 0;

        for (int charIndex = 0; charIndex < extractMsgRequest.getMessageLength(); charIndex++) {

            int value = 0;

            for (int bit = 0; bit < 8; bit++) {

                int pixelIndex = bitIndex / 3;

                int pixelX = pixelIndex % image.getWidth();
                int pixelY = pixelIndex / image.getWidth();

                int rgb = image.getRGB(pixelX, pixelY);

                int colorIndex = bitIndex % 3;

                int extractedBit;

                if (colorIndex == 0) {
                    int red = (rgb >> 16) & 0xFF;
                    extractedBit = red & 1;
                }
                else if (colorIndex == 1) {
                    int green = (rgb >> 8) & 0xFF;
                    extractedBit = green & 1;
                }
                else {
                    int blue = rgb & 0xFF;
                    extractedBit = blue & 1;
                }

                value = (value << 1) | extractedBit;

                bitIndex++;
            }

            message.append((char) value);
        }

        return message.toString();
    }


}
