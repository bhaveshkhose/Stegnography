package com.example.stegnography.service;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StegnoService {


    //to set the bits in the binary
    public static int setLSB(int value , int bit ){
        return (value & ~1) | bit ;
    }


    //for inserting the msg inside the image
    public static void replaceBits(byte[] msg , BufferedImage image) throws IOException {

        int bitSize = msg.length * 8 ;
        int bitIndex = 0 ;

        for(int i = 0 ; i < image.getHeight() && bitIndex < bitSize ; i++){
            for(int j =0 ; j < image.getWidth() && bitIndex < bitSize ; j++){
                int rgb = image.getRGB(j , i);

                int r = (rgb >> 16) & 0xFF ;
                int g = (rgb >> 8) & 0xFF ;
                int b = rgb & 0xFF ;

                if(bitIndex < bitSize ){

                    int byteIndex = bitIndex / 8 ;

                    int bitposition = 7 - (bitIndex % 8);
                    int bit = (msg[byteIndex] >> bitposition) & 1 ;

                    r = setLSB(r , bit);

                    bitIndex++;

                }

                if(bitIndex < bitSize ){

                    int byteIndex = bitIndex / 8 ;

                    int bitposition = 7 - (bitIndex % 8);
                    int bit = (msg[byteIndex] >> bitposition) & 1 ;

                    g = setLSB(g , bit);

                    bitIndex++;

                }
                if(bitIndex < bitSize ){

                    int byteIndex = bitIndex / 8 ;

                    int bitposition = 7 - (bitIndex % 8);
                    int bit = (msg[byteIndex] >> bitposition) & 1 ;

                    b = setLSB(b , bit);

                    bitIndex++;

                }


                int newRgb =
                        (rgb & 0xFF000000)
                                | (r << 16)
                                | (g << 8)
                                | b;

                image.setRGB(j, i, newRgb);
            }


        }
        System.out.println("The msg in inserted successfully");

    }

    //for extracting the bits for the image
    public static void extractMsg() throws IOException{
        BufferedImage image = ImageIO.read(new File("src/output.png"));

        int value = 0 ;
        int bitsRead = 0 ;

        for(int i = 0 ; i < image.getHeight() && bitsRead < 8 ; i++) {
            for(int j = 0 ; j < image.getWidth() && bitsRead < 8 ; j++){

                int rgb = image.getRGB( j , i);
                int red = (rgb >> 16) & 0xFF ;
                int green = (rgb >> 8) & 0xFF ;
                int blue = rgb & 0xFF ;

                if(bitsRead < 8){

                    int bit = red & 1 ;
                    value = (value << 1) | bit ;
                    bitsRead++;
                }

                if(bitsRead < 8){

                    int bit = green & 1 ;
                    value = (value << 1) | bit ;
                    bitsRead++;
                }

                if(bitsRead < 8){

                    int bit = blue & 1 ;
                    value = (value << 1) | bit ;
                    bitsRead++;
                }
            }
        }

        System.out.println("Extracted ASCII value: " + value);
        System.out.println("Extracted character: " + (char) value);
    }


}
