package net.cryptonomica.tomcatweb3j.servlets;

import net.cryptonomica.tomcatweb3j.contracts.CryptonomicaVerificationFunctions;
import net.cryptonomica.tomcatweb3j.entities.VerificationStruct;
import net.cryptonomica.tomcatweb3j.utilities.ApiKeyUtils;
import net.cryptonomica.tomcatweb3j.utilities.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * get verification from the smart contract by eth address or fingerprint
 */
@WebServlet(name = "GetVerificationServlet")
// <url-pattern>/getVerification</url-pattern>
public class GetVerificationServlet extends HttpServlet {

    /* ---- Logger: */
    private static final Logger LOG = Logger.getLogger(GetVerificationServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // (1) check API_KEY (throws Exception)
        ApiKeyUtils.checkApiKey(request);

        // (2) get data from request
        String userEthAddressStr = request.getParameter("address");
        String fingerprintStr = request.getParameter("fingerprint");

        // (3) get data from contract
        VerificationStruct verification = null;
        if (userEthAddressStr != null && userEthAddressStr.length() == 42) {
            verification = CryptonomicaVerificationFunctions.getVerificationStruct(userEthAddressStr);
            // fingerprint is 20 bytes, in hexadecimal 40 symbols string representation.
            // fingerprints are stored as upper case strings like:
            // 57A5FEE5A34D563B4B85ADF3CE369FD9E77173E5
        } else if (fingerprintStr != null && fingerprintStr.length() == 40) {

            String addressString = CryptonomicaVerificationFunctions.getAddresFromFingerprint(fingerprintStr);
            verification = CryptonomicaVerificationFunctions.getVerificationStruct(userEthAddressStr);
        } else {
            throw new IOException("Wrong request data");
        }

        LOG.info(verification.toString());

        // (4) send response
        ServletUtils.sendJsonResponseFromObject(response, verification); //
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
