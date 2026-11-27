package com.example.stegnography.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StegnographyOperationRequest {

    private String operationType;

    private String imageName;

    private Integer imageWidth;

    private Integer imageHeight;

    private Long imageSizeBytes;

    private Long byteStorageLimit;

    private Integer messageLength;

    private Long processingTimeMs;

    private String status;
}