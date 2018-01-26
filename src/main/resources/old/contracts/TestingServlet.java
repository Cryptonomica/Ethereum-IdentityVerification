package old.contracts;

import net.cryptonomica.tomcatweb3j.testing.TestEntity;
import net.cryptonomica.tomcatweb3j.utilities.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "TestingServlet")
public class TestingServlet extends HttpServlet {

    /* --- Logger: */
    // https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html
    // Find or create a logger for a named subsystem. If a logger has already been created with the given name it is returned.
    // Otherwise a new logger is created.
    // - When running Tomcat on unixes, the console output is usually redirected to the file named catalina.out
    private static final String className = TestingServlet.class.getName();
    private static final Logger LOG = Logger.getLogger(className);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("[" + className + "] started");

        TestEntity testEntity = new TestEntity(
                "some string",
                33,
                true,
                new Object()
        );

        ServletUtils.sendJsonResponseFromObject(
                //  response, ServletUtils.getAllRequestData(request)
                response, testEntity
        );
        // on cryptonomica-server:
        // byte[] httpResponseContentBytes = httpResponse.getContent();
        // String httpResponseContentString = new String(httpResponseContentBytes, StandardCharsets.UTF_8);
        // "{\"string\":\"some string\",\"integer\":33,\"aBoolean\":true,\"object\":{}}\n"
        // and
        // TestEntity testEntity = new Gson().fromJson(httpResponseContentString, TestEntity.class);
        // - works correctly


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
