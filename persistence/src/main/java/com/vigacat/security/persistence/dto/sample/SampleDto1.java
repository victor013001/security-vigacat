package com.vigacat.security.persistence.dto.sample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleDto1 {

    private Long id;
    private String name;

}
