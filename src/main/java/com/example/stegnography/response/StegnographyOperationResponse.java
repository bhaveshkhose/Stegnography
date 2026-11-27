package com.example.stegnography.response;



import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StegnographyOperationResponse {

    private Long id;

    private String operationType;

    private String imageName;

    private Integer imageWidth;

    private Integer imageHeight;

    private Long imageSizeBytes;

    private Long byteStorageLimit ;

    private Integer messageLength;

    private Long processingTimeMs;

    private String status;

    private LocalDateTime createdAt;
}
