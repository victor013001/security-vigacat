package com.vigacat.security.service.component;

import com.vigacat.security.persistence.dto.sample.SampleDto1;
import com.vigacat.security.persistence.dto.sample.SampleDto2;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class ModelMapperSampleServiceImplTest {

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private ModelMapperSampleServiceImpl modelMapperSampleService;

  @Test
  public void getSampleMappedObjectTest() {

    SampleDto2 sampleDto2 = SampleDto2.builder()
        .id(1L)
        .name("name dto 1")
        .build();

    Mockito.when(modelMapper.map(Mockito.any(SampleDto1.class), Mockito.eq(SampleDto2.class)))
        .thenReturn(sampleDto2);

    final SampleDto2 sampleDto2Response = modelMapperSampleService.getSampleMappedObject();

    Assertions.assertThat(sampleDto2Response)
        .hasFieldOrPropertyWithValue("id",1L)
        .hasFieldOrPropertyWithValue("name", "name dto 1");

  }
}
