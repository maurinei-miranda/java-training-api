package br.com.training;

import br.com.training.dto.VaccineForm;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
                        get(vaccinesUrl + vaccine.getName()))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseContent));
    }


    @Test
    @DisplayName(value = "POST /vaccines save a vaccine")
    public void postUser_Return201() throws Exception {
        LocalDate localDate = LocalDate.parse("2022-03-31");
        VaccineForm vaccineForm = new VaccineForm("Pfizer",
                "Covid-19",
                10,
                3,
                localDate,
                localDate);

        VaccineResponse vaccineResponse = new VaccineResponse("Pfizer",
                "Covid-19",
                "N/A",
                10,
                3,
                localDate,
                localDate);

        String jsonContent = objectMapper.writeValueAsString(vaccineForm);
        String jsonResponse = objectMapper.writeValueAsString(vaccineResponse);
        mockMvc.perform(
                        post(vaccinesUrl)
                                .contentType("application/json")
                                .content(jsonContent))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().string(jsonResponse));
    }

    @Test
    @DisplayName(value = "POST /vaccines post vaccines, bad request")
    public void postUser_Return400() throws Exception {
        LocalDate localDate = LocalDate.parse("2022-03-31");
        VaccineForm vaccineForm = new VaccineForm("",
                "Malaria - WHO",
                10,
                3,
                localDate,
                localDate);

        String jsonContent = objectMapper.writeValueAsString(vaccineForm);

        mockMvc.perform(
                        post(vaccinesUrl)
                                .contentType("application/json")
                                .content(jsonContent))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("name: must not be blank"));
    }

    @Test
    @DisplayName(value = "PUT /vaccines update a vaccine success")
    public void putUpdateVaccine_Return200() throws Exception {
        LocalDate localDate = LocalDate.parse("2022-04-03");
        VaccineForm vaccineForm = new VaccineForm("Pfizer",
                "Covid-19",
                30,
                5,
                localDate,
                localDate);

        VaccineResponse vaccineResponse = new VaccineResponse("Pfizer",
                "Covid-19",
                "N/A",
                30,
                5,
                localDate,
                localDate);


        String jsonRequest = objectMapper.writeValueAsString(vaccineForm);
        String jsonResponse = objectMapper.writeValueAsString(vaccineResponse);


        Vaccine vaccine = mapStructMapper.vaccineFormToVaccine(vaccineForm);
        doReturn(vaccine).when(vaccineService).findByName("Pfizer");

        mockMvc.perform(
                        put(vaccinesUrl + vaccine.getName())
                                .contentType("application/json")
                                .content(jsonRequest))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponse));
    }

    @Test
    @DisplayName(value = "PUT /vaccines update a vaccine fail")
    public void putUpdateVaccine_Return404() throws Exception {
        LocalDate localDate = LocalDate.parse("2022-04-03");
        VaccineForm vaccineForm = new VaccineForm("Pfizer",
                "",
                30,
                5,
                localDate,
                localDate);

        Vaccine vaccine = new Vaccine("Pfizer",
                "Covid-19",
                "N/A",
                30,
                5,
                localDate,
                localDate);

        String jsonRequest = objectMapper.writeValueAsString(vaccineForm);
        doReturn(vaccine).when(vaccineService).findByName("Pfizer");
        mockMvc.perform(
                        put(vaccinesUrl + vaccineForm.getName())
                                .contentType("application/json")
                                .content(jsonRequest)
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("diseaseName: must not be blank"));

    }

    @Test
    @DisplayName(value = "DELETE /vaccines - delete a vaccine")
    public void deleteVaccine_Return204() throws Exception {
        LocalDate localDate = LocalDate.parse("2022-04-03");
        Vaccine vaccine = new Vaccine("Pfizer",
                "Covid-19",
                "N/A",
                30,
                5,
                localDate,
                localDate);


        doReturn(vaccine).when(vaccineService).findByName(vaccine.getName());
        VaccineResponse vaccineResponse = mapStructMapper.vaccineToVaccineResponse(vaccine);
        String jsonResponse = objectMapper.writeValueAsString(vaccineResponse);
        mockMvc.perform(
                        delete(vaccinesUrl + vaccine.getName())
                )
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponse));
    }
}

