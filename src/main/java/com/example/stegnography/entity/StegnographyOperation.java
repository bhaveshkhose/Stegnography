package com.example.stegnography.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "steganography_operations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StegnographyOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation_type", nullable = false, length = 20)
    private String operationType;

    @Column(name = "image_name", length = 255)
    private String imageName;

    @Column(name = "image_width")
    private Integer imageWidth;

    @Column(name = "image_height")
    private Integer imageHeight;

    @Column(name = "image_size_bytes")
    private Long imageSizeBytes;

    @Column(name = "byte_storage_limit" )
    private Long byteStorageLimit ;

    @Column(name = "message_length")
    private Integer messageLength;

    @Column(name = "processing_time_ms")
    private Long processingTimeMs;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
