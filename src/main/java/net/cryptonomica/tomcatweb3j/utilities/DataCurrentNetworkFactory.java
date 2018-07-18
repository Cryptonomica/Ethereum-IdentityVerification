package net.cryptonomica.tomcatweb3j.utilities;

import net.cryptonomica.tomcatweb3j.contracts.CryptonomicaVerification;
import net.cryptonomica.tomcatweb3j.entities.DataCurrentNetwork;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Logger;

// import org.web3j.protocol.ipc.UnixIpcService;

public class DataCurrentNetworkFactory {

    private static final Logger LOG = Logger.getLogger(DataCurrentNetworkFactory.class.getName());

    // see: https://github.com/conor10/web3j-javamag/blob/master/src/main/java/org/web3j/javamag/HelloWorld.java
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    //    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(7_800_000);

    public static DataCurrentNetwork getDataCurrentNetworkInstance() {

        DataCurrentNetwork dataCurrentNetwork = new DataCurrentNetwork();

        Web3j web3 = Web3j.build(new HttpService()); // defaults to http://localhost:8545/

        /*
        String USER = System.getProperty("user.name");
        if (USER.contains("tomcat")) { // on server: tomcat8
            USER = "ubuntu";
        }
        */

        try {

            String netVersion = web3.netVersion().send().getNetVersion();
            LOG.info("web3.netVersion().send().getNetVersion(): " + netVersion + " (String)");
            // cat /var/log/tomcat8/catalina.out
            // ...
            // Dec 11, 2017 4:48:53 AM net.cryptonomica.tomcatweb3j.utilities.DataCurrentNetworkFactory getDataCurrentNetworkInstance
            // INFO: web3.netVersion().send().getNetVersion(): 4 (String)

            if (netVersion.equals("1")) { // MainNet

                String cryptonomicaVerificationContractAddress = Constants.MainNetContractAddres;
                // web3 = Web3j.build(new UnixIpcService("/home/" + USER + "/.ethereum/geth.ipc"));
                Credentials credentials = Credentials.create(System.getenv("MAINNET_PRIVATE_KEY"));

                LOG.info("credentials.getAddress() : " + credentials.getAddress());

                if (!credentials.getAddress().equalsIgnoreCase(Constants.MainNetOwnerAddress)) {
                    LOG.severe("(!) check contract owner address");
                }

                dataCurrentNetwork.web3j = web3;
                dataCurrentNetwork.cryptonomicaVerification = CryptonomicaVerification.load(
                        cryptonomicaVerificationContractAddress,
                        web3,
                        credentials,
                        GAS_PRICE,
                        GAS_LIMIT
                );
                dataCurrentNetwork.etherscan = "https://etherscan.io/";

            } else if (netVersion.equals("3")) { // Ropsten
                // ...
            } else if (netVersion.equals("4")) { // Rinkeby

                String cryptonomicaVerificationContractAddress = Constants.RinkebyContractAddres;
                // web3 = Web3j.build(new UnixIpcService("/home/" + USER + "/.ethereum/rinkeby/geth.ipc"));
                Credentials credentials = Credentials.create(System.getenv("RINKEBY_PRIVATE_KEY"));

                dataCurrentNetwork.web3j = web3;
                dataCurrentNetwork.cryptonomicaVerification = CryptonomicaVerification.load(
                        cryptonomicaVerificationContractAddress,
                        web3,
                        credentials,
                        GAS_PRICE,
                        GAS_LIMIT
                );
                dataCurrentNetwork.etherscan = "https://rinkeby.etherscan.io/";

            } else if (netVersion.length() == 13) { // TestRPC
                // ...
            }
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        }

        // (!) can be null
        return dataCurrentNetwork;
    }

}
