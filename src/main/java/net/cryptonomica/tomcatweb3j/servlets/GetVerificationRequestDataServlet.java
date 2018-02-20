package net.cryptonomica.tomcatweb3j.servlets;

import net.cryptonomica.tomcatweb3j.contracts.CryptonomicaVerificationFunctions;
import net.cryptonomica.tomcatweb3j.entities.VerificationRequestDataFromSC;
import net.cryptonomica.tomcatweb3j.utilities.ApiKeyUtils;
import net.cryptonomica.tomcatweb3j.utilities.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetSignedStringServlet")
public class GetVerificationRequestDataServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // (1) check API_KEY (throws Exception)
        ApiKeyUtils.checkApiKey(request);

        // (2) get data from request
        String addressStr = request.getParameter("address");

        // (3) get data from contract:
        String unverifiedFingerprint = CryptonomicaVerificationFunctions.getUnverifiedFingerprint(addressStr);
        String signedString = CryptonomicaVerificationFunctions.getSignedStringFromSC(addressStr);
        VerificationRequestDataFromSC verificationRequestDataFromSC = new VerificationRequestDataFromSC(
                unverifiedFingerprint,
                signedString
        );
        // (4) send response
        ServletUtils.sendJsonResponseFromObject(response, verificationRequestDataFromSC); // application/json
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
