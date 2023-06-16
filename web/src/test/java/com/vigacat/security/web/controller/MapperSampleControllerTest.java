package com.vigacat.security.web.controller;

import com.vigacat.security.persistence.dto.sample.SampleDto2;
import com.vigacat.security.service.component.ModelMapperSampleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class MapperSampleControllerTest {

  @InjectMocks
  private MapperSampleController mapperSampleController;

  @Mock
  private ModelMapperSampleService modelMapperSampleService;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(mapperSampleController).build();
  }

  @Test
  public void getMappedObject() throws Exception {

    SampleDto2 sampleDto2Victor = SampleDto2.builder()
        .id(1L)
        .name("victor")
        .build();

    Mockito.when(modelMapperSampleService.getSampleMappedObject())
        .thenReturn(sampleDto2Victor);

    mockMvc.perform(MockMvcRequestBuilders.get("/model-mapper/sample")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print());
  }


}
