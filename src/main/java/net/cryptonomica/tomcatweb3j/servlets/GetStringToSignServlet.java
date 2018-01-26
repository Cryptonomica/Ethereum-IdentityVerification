package net.cryptonomica.tomcatweb3j.servlets;

import net.cryptonomica.tomcatweb3j.contracts.CryptonomicaVerificationFunctions;
import net.cryptonomica.tomcatweb3j.utilities.ApiKeyUtils;
import net.cryptonomica.tomcatweb3j.utilities.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetStringToSignServlet")
public class GetStringToSignServlet extends HttpServlet {

    /* this is not for function requestStringToSignWithKey(string _fingerprint)
     * but for mapping (address => string) public stringToSign;
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // (1) check API_KEY (throws Exception)
        ApiKeyUtils.checkApiKey(request);

        // (2) get data from request
        String addressStr = request.getParameter("address");

        // (3) get data from contract
        String stringToSign = CryptonomicaVerificationFunctions.getStringToSignFromSC(addressStr);

        // (4) send response
        ServletUtils.sendTxtResponse(response, stringToSign); // 'text/plain'

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
