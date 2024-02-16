package com.tepkeven.bankservices.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Tep Keven",
            email = "teapkevin@gmail.com",
            url = "http://localhost:8080"
        ),
        description = "Bank Services Demo for MBanq with Spring Security and JUnit test. <br/><br/> User's Test Account: <b> phone = 012330246, password = helloworld.</b> <br/> Admin's Test Account: <b> phone = 012330245, password = helloworld. </b>",
        title = "Tep Keven's Tasks",
        version = "1.0",
        license = @License(
            name = "Tep Keven",
            url = "http://localhost:8080"
        )
    ),
    servers = {
        @Server(
            description = "Local Development Environment",
            url = "http://localhost:8080"
        )
    } 
)
public class OpenApiConfig {
    
}
