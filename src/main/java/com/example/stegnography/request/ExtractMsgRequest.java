package com.example.stegnography.request;


import lombok.*;

import java.awt.image.BufferedImage;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtractMsgRequest {

    private int messageLength ;
    private BufferedImage image ;
}
