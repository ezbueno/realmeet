package br.com.sw2you.realmeet.core;

import br.com.sw2you.realmeet.Application;
import br.com.sw2you.realmeet.api.ApiClient;
import java.net.MalformedURLException;
import java.net.URL;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public abstract class BaseIntegrationTest {
    @Autowired
    private Flyway flyway;

    @LocalServerPort
    private int serverPort;

    private final String protocol = "http";

    private final String host = "localhost";

    @BeforeEach
    void setup() throws Exception {
        this.setupFlyway();
        this.setupEach();
    }

    protected void setupEach() throws Exception {}

    protected void setLocalHostBasePath(ApiClient apiClient, String path) throws MalformedURLException {
        apiClient.setBasePath(new URL(this.protocol, this.host, this.serverPort, path).toString());
    }

    private void setupFlyway() {
        this.flyway.clean();
        this.flyway.migrate();
    }
}
