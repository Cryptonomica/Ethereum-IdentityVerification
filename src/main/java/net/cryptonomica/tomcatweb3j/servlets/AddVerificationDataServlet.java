package net.cryptonomica.tomcatweb3j.servlets;

import com.google.gson.Gson;
import net.cryptonomica.tomcatweb3j.contracts.CryptonomicaVerificationFunctions;
import net.cryptonomica.tomcatweb3j.entities.AddVerificationDataObj;
import net.cryptonomica.tomcatweb3j.utilities.ApiKeyUtils;
import net.cryptonomica.tomcatweb3j.utilities.ServletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "AddVerificationDataServlet")
public class AddVerificationDataServlet extends HttpServlet {

    /* --- Logger: */
    // https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html
    // Find or create a logger for a named subsystem. If a logger has already been created with the given name it is returned.
    // Otherwise a new logger is created.
    // - When running Tomcat on unixes, the console output is usually redirected to the file named catalina.out
    private static final String className = AddVerificationDataServlet.class.getName();
    private static final Logger LOG = Logger.getLogger(className);
    private static final Gson GSON = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.info("AddVerificationDataServlet POST request received");

        // (1) check API_KEY (throws Exception)
        ApiKeyUtils.checkApiKey(request); // void

        // (2) get data from request
        AddVerificationDataObj addVerificationDataObj = new AddVerificationDataObj(request); // logs obj.toString()

        // (3) send transaction to contract and get transaction receipt
        TransactionReceipt transactionReceipt = CryptonomicaVerificationFunctions.addVerificationData(addVerificationDataObj);
        LOG.info("Tx Hash" + transactionReceipt.getTransactionHash());
        LOG.info(GSON.toJson(transactionReceipt));

        // (4) send response
        ServletUtils.sendJsonResponseFromObject(response, transactionReceipt);
    }

}
