package com.train.machine;

import com.train.machine.controller.StationsController;
import com.train.machine.entities.StationsMatchDto;
import com.train.machine.utils.ReadStationsFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TrainTicketMachineApplication.class)
//@SpringBootTest
@AutoConfigureMockMvc
@Import(ReadStationsFile.class)
@WebMvcTest(controllers = StationsController.class)
class TrainTicketMachineApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ReadStationsFile readStationsFile;

    @MockBean
    private StationsController stationsController;

    @Test
    void contextLoads() {
        assertThat(readStationsFile.readFromInputStream().size()).isEqualTo(362);
    }

    @Test
    void getStation() throws Exception {
        var stationsMatchDto = new ResponseEntity(
                StationsMatchDto.builder().stations(List.of("Coimbra-B",
                        "Coimbra")).nextCharMatches(List.of('-')).build(), HttpStatus.OK);

        when(stationsController.getStations("Coimbra")).thenReturn(stationsMatchDto);
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/stations?station=Coimbra")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        var expected = "{\"stations\":[\"Coimbra-B\",\"Coimbra\"],\"nextCharMatches\":[\"-\"]}";
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    void getStationDontMatchDiacritics() throws Exception {
        var stationsMatchDto = new ResponseEntity(StationsMatchDto.builder().build(), HttpStatus.OK);

        when(stationsController.getStations("Sao")).thenReturn(stationsMatchDto);
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/stations?station=Sao")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        var expected = "{\"stations\":null,\"nextCharMatches\":null}";
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    void getStationFailure() throws Exception {
        var stationsMatchDto = new ResponseEntity(StationsMatchDto.builder().build(), HttpStatus.OK);

        when(stationsController.getStations("")).thenReturn(stationsMatchDto);
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/stations")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
    }
}
