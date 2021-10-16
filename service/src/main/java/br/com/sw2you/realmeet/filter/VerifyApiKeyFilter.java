package br.com.sw2you.realmeet.filter;

import static org.apache.commons.lang3.StringUtils.isBlank;

import br.com.sw2you.realmeet.domain.entity.Client;
import br.com.sw2you.realmeet.domain.repository.ClientRepository;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

public class VerifyApiKeyFilter extends GenericFilterBean {
    private static final Logger GET_LOGGER = LoggerFactory.getLogger(VerifyApiKeyFilter.class);
    private static final String HEADER_API_KEY = "api-key";

    private final ClientRepository clientRepository;

    public VerifyApiKeyFilter(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;

        var apiKey = httpRequest.getHeader(HEADER_API_KEY);

        if (!isBlank(apiKey) && this.isValidApiKey(apiKey)) {
            chain.doFilter(request, response);
        } else {
            this.sendUnauthorizedError(httpResponse, apiKey);
        }
    }

    private boolean isValidApiKey(String apiKey) {
        return this.clientRepository.findById(apiKey)
            .filter(Client::getActive)
            .stream()
            .peek(c -> GET_LOGGER.info("Valid API Key: '{}' ({})", c.getApiKey(), c.getDescription()))
            .findFirst()
            .isPresent();
    }

    private void sendUnauthorizedError(HttpServletResponse response, String apiKey) throws IOException {
        var errorMessage = isBlank(apiKey) ? "API Key is missing" : "API Key is invalid";
        GET_LOGGER.error(errorMessage);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentLength(errorMessage.length());
        response.setContentType("plain/text");

        try (Writer out = response.getWriter()) {
            out.write(errorMessage);
        }
    }
}
