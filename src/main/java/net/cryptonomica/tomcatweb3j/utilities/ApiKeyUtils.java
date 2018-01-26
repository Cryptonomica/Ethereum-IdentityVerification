package net.cryptonomica.tomcatweb3j.utilities;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;


public class ApiKeyUtils {

    /* --- Logger: */
    // https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html
    // Find or create a logger for a named subsystem. If a logger has already been created with the given name it is returned.
    // Otherwise a new logger is created.
    private static final Logger LOG = Logger.getLogger(ApiKeyUtils.class.getName());

    private static final String API_KEY = System.getenv("API_KEY");

    public static void checkApiKey(HttpServletRequest request) throws IOException {

        String apiKey = null;

        String apiKeyHeader =
                // returns null if the request does not have a header of that name
                request.getHeader("apiKey");

        String apiKeyParameter =
                // returns null if the parameter does not exist
                request.getParameter("apiKey");


        // get API key:
        if (apiKeyHeader == null && apiKeyParameter == null) {

            LOG.warning("API key is missing");
            // Exception -> servlet
            throw new IOException("API key is missing in the request");

        } else if (apiKeyHeader != null) {

            apiKey = apiKeyHeader;

        } else {

            apiKey = apiKeyParameter;
        }

        // check API key:
        if (!API_KEY.equals(apiKey)) {
            LOG.warning("API key is invalid");
            // Exception -> servlet
            throw new IOException("API key is invalid");
        }

    }
}
