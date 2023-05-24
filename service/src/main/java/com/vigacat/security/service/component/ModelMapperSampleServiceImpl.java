package com.vigacat.security.service.component;

import com.vigacat.security.persistence.dto.sample.SampleDto1;
import com.vigacat.security.persistence.dto.sample.SampleDto2;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelMapperSampleServiceImpl implements ModelMapperSampleService {

    private final ModelMapper modelMapper;

    @Override
    public SampleDto2 getSampleMappedObject() {
        final SampleDto1 sampleDto1 = SampleDto1.builder()
                .id(1L)
                .name("name dto 1")
                .build();
        return modelMapper.map(sampleDto1, SampleDto2.class);
    }

}
