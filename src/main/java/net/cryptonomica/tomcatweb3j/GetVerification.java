package net.cryptonomica.tomcatweb3j;

import com.google.gson.Gson;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * get verification from the smart contract
 */
@WebServlet(name = "GetVerification")
public class GetVerification extends HttpServlet {

    /* --- Gson: */
    private static final Gson GSON = new Gson();

    private static Web3j web3 = Web3j.build(new HttpService()); // defaults to http://localhost:8545/
    private static final String password = System.getenv("KEY_PASS");
    private static final String pathToWalletFile = System.getenv("KEY_PATH"); //

    // see: https://github.com/conor10/web3j-javamag/blob/master/src/main/java/org/web3j/javamag/HelloWorld.java
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
    //
    private static final String API_KEY = System.getenv("API_KEY");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // check API_KEY

        String requestApiKey = request.getHeader("apiKey"); //
        if (!API_KEY.equals(requestApiKey)) {
            throw new IOException("API key is invalid");
        }

        String addressStr = request.getParameter("address");
        Address address = new Address(addressStr);
        //

        // create contract:
        Credentials credentials;
        File file = new File(pathToWalletFile);
        try {
            credentials = WalletUtils.loadCredentials(
                    password,
                    file // <<<<
            );
        } catch (Exception e) {
            throw new IOException("");
        }
        CryptonomicaVerify cryptonomicaVerifyContract = CryptonomicaVerify.load(
                "0x23f06d3B6a719A6F90880E5b7Fb6A98016c2f532", // TestNet <------------------------------------------ <<<
                web3,
                credentials,
                GAS_PRICE,
                GAS_LIMIT
        );

        Integer numberOfVerifications = null;
        try {
            numberOfVerifications = cryptonomicaVerifyContract
                    .numberOfVerifications(address)
                    .get()
                    .getValue()
                    .intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        HashMap<Integer, ArrayList> verificationsArrayList = new HashMap<>();

        for (Integer i = 0; i <= numberOfVerifications; i++) {

            Uint256 number = new Uint256(i);
            List verification = null;
            try {
                verification = cryptonomicaVerifyContract.verification(address, number).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(verification);
            verificationsArrayList.put(i, arrayList);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //
    }
}
