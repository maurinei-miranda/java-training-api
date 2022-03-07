package br.com.training.interfaces;

import br.com.training.models.Root;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "diseases", url = "https://disease-info-api.herokuapp.com")
public interface DiseasesClient {
    @GetMapping("/diseases")
    Root findAll();
}
