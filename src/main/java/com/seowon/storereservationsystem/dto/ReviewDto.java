package com.seowon.storereservationsystem.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private String userName;
    private String reservedDt;
    private String storeName;

    @Size(max = 10000)
    private String content;
}
