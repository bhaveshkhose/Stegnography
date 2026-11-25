package com.example.stegnography.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsertMsgRequest {

    private String massage ;
    private BufferedImage image ;
}
