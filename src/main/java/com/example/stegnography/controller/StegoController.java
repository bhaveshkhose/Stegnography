package com.example.stegnography.controller;


import com.example.stegnography.request.ExtractMsgRequest;
import com.example.stegnography.request.InsertMsgRequest;
import com.example.stegnography.response.StegnographyOperationResponse;
import com.example.stegnography.service.ImageValidationService;
import com.example.stegnography.service.StegnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("api/stego")
public class StegoController{

    public final StegnoService stegnoService ;
    public final ImageValidationService imageValidationService;

    public StegoController(
            StegnoService stegnoService,
            ImageValidationService imageValidationService) {

        this.stegnoService = stegnoService;
        this.imageValidationService = imageValidationService;
    }

    @PostMapping(
            value = "/insertMsg",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> insertMsg(
            @RequestParam("message") String msg ,
            @RequestParam("image")MultipartFile image
            ) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
        InsertMsgRequest insertMsgRequest = new InsertMsgRequest(msg , bufferedImage);

        String result = stegnoService.replaceBits(insertMsgRequest);

        return ResponseEntity.ok(result);

    }

    @GetMapping(
            value = "/extractMsg",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> extractMsg(
            @RequestParam int msgLength ,
            @RequestParam MultipartFile inputImage
    ) throws IOException {

        BufferedImage image = ImageIO.read(inputImage.getInputStream());
        ExtractMsgRequest extractMsgRequest = new ExtractMsgRequest(msgLength , image);

        String msg = stegnoService.extractMsg(extractMsgRequest);


        return ResponseEntity.ok(msg);

    }

    @PostMapping(
            value = "/inspectImg",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> inspectImg(MultipartFile image){
        try {
            BufferedImage bufferedImage = imageValidationService.validateImage(image);
            int charCount = imageValidationService.characterLimit(bufferedImage);

            return ResponseEntity.status(HttpStatus.OK).body("Character can hide "+charCount);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Error :"+e.getMessage()
            );
        }
    }


    @PostMapping(
        value = "/stegoOperation",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<StegnographyOperationResponse> StegnographyOperation(
            @RequestParam String msg ,
            @RequestParam MultipartFile image
    ){
        BufferedImage bufferedImage = imageValidationService.validateImage(image);
        Long byteStorageLimit = (long)imageValidationService.characterLimit(bufferedImage);
        String imageName = image.getOriginalFilename();
        int imageWidth = bufferedImage.getWidth();
        int imageHeight = bufferedImage.getHeight();
        int msgLength = msg.length();




    }

}
