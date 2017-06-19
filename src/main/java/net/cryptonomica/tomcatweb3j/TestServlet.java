package net.cryptonomica.tomcatweb3j;

import com.google.gson.Gson;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


/**
 *  this is for testing web3j features
 */

@WebServlet(name = "TestServlet")
public class TestServlet extends HttpServlet {

    private class ResponseObj {
        String str1;
        String str2;
        Map<String, String> sysEnv; // for System.getenv() http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getenv--
        String sysEnvTest; // for http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getenv-java.lang.String-
        Boolean bool;
        Integer integer;
        List<String> stringList;

        public ResponseObj() {
        }

        public ResponseObj(String str1, Boolean bool, Integer integer) {
            this.str1 = str1;
            this.bool = bool;
            this.integer = integer;
        }
    }

    // static String testNetIpc = "/home/ubuntu/.ethereum/testnet/geth.ipc";
    // static Web3j web3 = Web3j.build(new UnixIpcService(testNetIpc)); //
    private static Web3j web3 = Web3j.build(new HttpService()); // defaults to http://localhost:8545/

    // see: https://github.com/conor10/web3j-javamag/blob/master/src/main/java/org/web3j/javamag/HelloWorld.java
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        //
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send(); // sync
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();

        // EthAccounts ethAccounts = web3.ethAccounts().send();
        // List<String> accounts = ethAccounts.getAccounts();
        List<String> accounts = web3.ethAccounts().send().getAccounts();

        Credentials credentials = null;
        String credentialsStr = null;

        String password = System.getenv("KEY_PASS");
        String pathToWalletFile = System.getenv("KEY_PATH");
        // String pathToWalletFile = "/WEB-INF/UTC--2017-06-18T03-11-13.235052023Z--6c992fb9531c9285ca9aba80c32a6307";
        // File file = new File(context.getRealPath(pathToWalletFile));
        if (password != null && pathToWalletFile != null) {
            File file = new File(pathToWalletFile);
            try {
                credentials = WalletUtils.loadCredentials(
                        password,
                        file // <<<<
                );
                credentialsStr = credentials.toString();
            } catch (CipherException e) {
                e.printStackTrace();
            }
        }

        CryptonomicaVerify cryptonomicaVerifyContract = CryptonomicaVerify.load(
                "0x23f06d3B6a719A6F90880E5b7Fb6A98016c2f532", // TestNet <------------------------------------------ <<<
                web3,
                credentials,
                GAS_PRICE,
                GAS_LIMIT
        );

        Bool isManagerResult = null;
        try {
            isManagerResult = cryptonomicaVerifyContract.isManager(
                    new Address(accounts.get(0))
            ).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // TransactionReceipt transactionReceipt = null;
        // try {
        //     transactionReceipt = cryptonomicaVerifyContract.setPriceForVerification(
        //             new Uint256(new BigInteger("1000000000000000000"))
        //     ).get();
        // } catch (Exception e) {
        //     throw new IOException("Smart Contract Exception");
        // }

        Boolean isManagerResultBoolean = isManagerResult.getValue();
        // Double blockNumber = transactionReceipt.getBlockNumber().doubleValue();

        List<Type> verificationData;
        try {
            verificationData = cryptonomicaVerifyContract.verification(
                    new Address(accounts.get(0)),
                    new Uint256(new BigInteger("0"))
            ).get();
        } catch (Exception e) {
            throw new IOException("Smart Contract Exception");
        }

        // create response object
        ResponseObj responseObj = new ResponseObj("text (string)", isManagerResultBoolean, 33);
        responseObj.stringList = new ArrayList<>();
        for (Type type : verificationData) {
            responseObj.stringList.add(
                    type.getTypeAsString()
            );
        }
        responseObj.str1 = clientVersion;
        responseObj.str2 = credentialsStr;
        // see: https://stackoverflow.com/questions/531694/how-can-i-get-system-variable-value-in-java
        // http://docs.oracle.com/javase/8/docs/api/java/lang/System.html
        //
        // responseObj.sysEnv = System.getenv();
        responseObj.sysEnvTest = System.getenv("TEST_ENV_VAR");

        // see:
        // https://stackoverflow.com/questions/2010990/how-do-you-return-a-json-object-from-a-java-servlet
        String json = new Gson().toJson(responseObj);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
