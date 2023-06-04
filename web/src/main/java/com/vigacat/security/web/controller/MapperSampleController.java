package com.vigacat.security.web.controller;

import com.vigacat.security.persistence.dto.sample.SampleDto2;
import com.vigacat.security.service.component.ModelMapperSampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/model-mapper/sample")
@RequiredArgsConstructor
public class MapperSampleController {

    private final ModelMapperSampleService modelMapperSampleService;

    @GetMapping
    public ResponseEntity<SampleDto2> getMappedObject() {
        return ResponseEntity.ok(modelMapperSampleService.getSampleMappedObject());
    }

}
