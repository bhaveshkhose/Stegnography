package com.example.stegnography.controller;


import com.example.stegnography.request.InsertMsgRequest;
import com.example.stegnography.service.StegnoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("api/stego")
public class StegoController{

    public final StegnoService stegnoService ;

    public StegoController(StegnoService stegnoService) {

        this.stegnoService = stegnoService;
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
}
