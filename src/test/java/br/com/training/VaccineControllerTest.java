package br.com.training;

import br.com.training.dto.VaccineResponse;
import br.com.training.interfaces.MapStructMapper;
import br.com.training.models.Vaccine;
import br.com.training.services.VaccineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class VaccineControllerTest {

    final String vaccinesUrl = "/vaccines/";
    @MockBean
    private VaccineService vaccineService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MapStructMapper mapStructMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName(value = "GET /vaccines get vaccine List")
    public void getVaccinesList_Return200() throws Exception {
        LocalDate localDate = LocalDate.parse("2022-03-31");
        List<Vaccine> vaccineList = new ArrayList<>();
        Vaccine vaccine = new Vaccine("Pfizer",
                "Covid-19",
                "Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus. ",
                10,
                3,
                localDate,
                localDate);
        vaccineList.add(vaccine);
        doReturn(vaccineList).when(vaccineService).findAllVaccines();
        VaccineResponse vaccineResponse = mapStructMapper.vaccineToVaccineResponse(vaccine);
        String responseContent = objectMapper.writeValueAsString(vaccineResponse);
        mockMvc.perform(
                        get(vaccinesUrl))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("[" + responseContent + "]"));
    }

    @Test
    @DisplayName(value = "GET /vaccines get a vaccine by name")
    public void getVaccineByName_Return200() throws Exception {
        LocalDate localDate = LocalDate.parse("2022-03-31");
        Vaccine vaccine = new Vaccine("Pfizer",
                "Covid-19",
                "Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus. ",
                10,
                3,
                localDate,
                localDate);
        doReturn(vaccine).when(vaccineService).findByName(vaccine.getName());
        VaccineResponse vaccineResponse = mapStructMapper.vaccineToVaccineResponse(vaccine);
        String responseContent = objectMapper.writeValueAsString(vaccineResponse);
        mockMvc.perform(
                        get(vaccinesUrl+vaccine.getName()))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseContent));
    }

}
