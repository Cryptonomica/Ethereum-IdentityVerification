package net.cryptonomica.tomcatweb3j.contracts;

import com.google.gson.Gson;
import net.cryptonomica.tomcatweb3j.entities.AddVerificationDataObj;
import net.cryptonomica.tomcatweb3j.entities.DataCurrentNetwork;
import net.cryptonomica.tomcatweb3j.entities.VerificationStruct;
import net.cryptonomica.tomcatweb3j.utilities.DataCurrentNetworkFactory;
import net.cryptonomica.tomcatweb3j.utilities.Web3jServices;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple9;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;

/*
transaction receipt example:
{"transactionHash":"0xaaaf335e1d981be091995ac4affe3c9f32fb046fc6fad4660090755c76251267","transactionIndex":"0x4","blockHash":"0x070e19f881d84b24b3def54b3485a2c1dd28110936ad7ce52683539f696ed40f","blockNumber":"0x13dc40","cumulativeGasUsed":"0x4befc","gasUsed":"0xb046","from":"0x3fab7ebe4b2c31a75cf89210aedefc093928a87d","to":"0xd0ea44485a8cca314cff417893d18257ec091ef3","logs":[{"removed":false,"logIndex":"0x0","transactionIndex":"0x4","transactionHash":"0xaaaf335e1d981be091995ac4affe3c9f32fb046fc6fad4660090755c76251267","blockHash":"0x070e19f881d84b24b3def54b3485a2c1dd28110936ad7ce52683539f696ed40f","blockNumber":"0x13dc40","address":"0xd0ea44485a8cca314cff417893d18257ec091ef3","data":"0x000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000038d7ea4c68000","topics":["0x665d155f71ad96c4a04629d54ef9fb27ef57911253588f2ee93474cd02fa3f53","0x0000000000000000000000003fab7ebe4b2c31a75cf89210aedefc093928a87d"]}],"logsBloom":"0x00000000000000000000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000220000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000008000000000000000000000000000000000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000000000"}

* */

public class CryptonomicaVerificationFunctions {

    /* --- Logger */
    private static final Logger LOG = Logger.getLogger(CryptonomicaVerificationFunctions.class.getName());

    /* --- Gson: */
    private static final Gson GSON = new Gson();

    /* --- Contract instance. (!) can be null */
    private static DataCurrentNetwork dataCurrentNetwork = DataCurrentNetworkFactory.getDataCurrentNetworkInstance();
    private static CryptonomicaVerification cryptonomicaVerification = dataCurrentNetwork.cryptonomicaVerification;
    private static Web3j web3j = dataCurrentNetwork.web3j;

    // function setPriceForVerification(uint priceInWei) public returns (bool)
    // see: https://etherconverter.online
    public static TransactionReceipt setPriceForVerification(Long priceInWei) throws IOException {

        TransactionReceipt transactionReceipt;

        if (cryptonomicaVerification == null) {
            // -> to servlet or object/class calling this function
            throw new IOException("Contract can not be loaded");
        }

        BigInteger priceInWeiBigInteger = BigInteger.valueOf(priceInWei);

        try {

            transactionReceipt = cryptonomicaVerification.setPriceForVerification(priceInWeiBigInteger).send();

            LOG.info(GSON.toJson(transactionReceipt));
            int transactionReceiptHashCode = transactionReceipt.hashCode();

            final List<Log> logs = transactionReceipt.getLogs();
            for (Log log : logs) {
                System.out.println(
                        "log.getType(): " + log.getType()
                                + " log.hashCode(): " + log.hashCode()
                                + " log.getData(): " + log.getData()
                );
            }

            String txHash = transactionReceipt.getTransactionHash();
            final List<CryptonomicaVerification.PriceChangedEventResponse> priceChangedEvents = cryptonomicaVerification.getPriceChangedEvents(transactionReceipt);
            System.out.println(priceChangedEvents);

        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

        return transactionReceipt;
    }

    public static String getSignedStringFromSC(String userAddress) throws IOException {

        String signedString = null;

        if (cryptonomicaVerification == null) {
            // -> to servlet or object/class calling this function
            throw new IOException("Contract can not be loaded");
        }

        try {
            signedString = cryptonomicaVerification.signedString(userAddress).send();
            LOG.info("signedString for " + userAddress + " : ");
            LOG.info(signedString);

        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new IOException(e.getMessage());
        }

        return signedString;
    }

    public static String getUnverifiedFingerprint(String userAddress) throws IOException {
        String unverifiedFingerprint = null;
        if (cryptonomicaVerification == null) {
            // -> to servlet or object/class calling this function
            throw new IOException("Contract can not be loaded");
        }
        try {
            unverifiedFingerprint = cryptonomicaVerification.unverifiedFingerprint(userAddress).send();
            LOG.info("unverifiedFingerprint for " + userAddress + " : ");
            LOG.info(unverifiedFingerprint);

        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new IOException(e.getMessage());
        }
        return unverifiedFingerprint;
    }

    public static String getAddresFromFingerprint(String fingerprint) throws IOException {
        String userAddress = null;
        if (cryptonomicaVerification == null) {
            // -> to servlet or object/class calling this function
            throw new IOException("Contract can not be loaded");
        }

        byte[] fingerprintBytes20 = Web3jServices.bytes20FromHexString(fingerprint).getValue();

        try {
            userAddress = cryptonomicaVerification.addressAttached(fingerprintBytes20).send();
            LOG.info("userAddress for " + fingerprint + " : " + userAddress);
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new IOException(e.getMessage());
        }
        return userAddress;
    }

    public static VerificationStruct getVerificationStruct(String userAddress) throws IOException {

        if (cryptonomicaVerification == null) {
            // -> to servlet
            throw new IOException("Contract can not be loaded");
        }

        VerificationStruct verification = null;
        try {
            Tuple9 tuple9 = cryptonomicaVerification.verification(userAddress).send();
            verification = new VerificationStruct(tuple9);
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new IOException(e.getMessage());
        }

        System.out.println(verification);

        return verification;
    }

    /* Solidity:
    function addVerificationData(
        address _acc, //
        string _fingerprint, // "57A5FEE5A34D563B4B85ADF3CE369FD9E77173E5"
        bytes20 _fingerprintBytes20, // "0x57A5FEE5A34D563B4B85ADF3CE369FD9E77173E5"
        uint _keyCertificateValidUntil, //
        string _firstName, //
        string _lastName, //
        uint _birthDate, //
        string _nationality) public {
    */
    public static TransactionReceipt addVerificationData(AddVerificationDataObj addVerificationDataObj) throws IOException {

        if (cryptonomicaVerification == null) {
            throw new IOException("Contract can not be loaded");
        }

        TransactionReceipt transactionReceipt = null;
        try {
            transactionReceipt = cryptonomicaVerification.addVerificationData(
                    addVerificationDataObj.getAcc(),
                    addVerificationDataObj.getFingerprint(),
                    addVerificationDataObj.getFingerprintBytes20(),
                    addVerificationDataObj.getKeyCertificateValidUntil(),
                    addVerificationDataObj.getFirstName(),
                    addVerificationDataObj.getLastName(),
                    addVerificationDataObj.getBirthDate(),
                    addVerificationDataObj.getNationality()
            ).send();

            LOG.info(transactionReceipt.getTransactionHash());
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new IOException(e.getMessage());
        }
        return transactionReceipt;
    }

}
