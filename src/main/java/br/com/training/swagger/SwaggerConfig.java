package br.com.training.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.training.controllers"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST, responseMessageForGET())
                .apiInfo(apiInfo());
    }

    private List<ResponseMessage> responseMessageForGET() {
        return new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder()
                    .code(500)
                    .message("Bad request")
                    .responseModel(new ModelRef("Error"))
                    .build());
        }};
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Multiple API's for vaccine management.")
                .description("This application allows the management of users, vaccines and details of diseases obtained through an external API. And the application of vaccines to registered users.")
                .version("2.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("Maurinei Miranda", "https://github.com/maurinei-miranda", "maurinei.cabral@tgmail.com"))
                .build();
    }

}
