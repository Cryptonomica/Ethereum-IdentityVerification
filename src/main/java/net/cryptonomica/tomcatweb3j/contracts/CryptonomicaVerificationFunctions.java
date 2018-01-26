package net.cryptonomica.tomcatweb3j.contracts;

import com.google.gson.Gson;
import net.cryptonomica.tomcatweb3j.entities.AddVerificationDataObj;
import net.cryptonomica.tomcatweb3j.entities.Verification;
import net.cryptonomica.tomcatweb3j.utilities.DataCurrentNetworkFactory;
import net.cryptonomica.tomcatweb3j.utilities.Web3jServices;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

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
    private static CryptonomicaVerification cryptonomicaVerification = DataCurrentNetworkFactory.getDataCurrentNetworkInstance().cryptonomicaVerification;
    private static Web3j web3j = DataCurrentNetworkFactory.getDataCurrentNetworkInstance().web3j;

    // function setPriceForVerification(uint priceInWei) public returns (bool)
    // see: https://etherconverter.online
    public static TransactionReceipt setPriceForVerification(Long priceInWei) throws IOException {

        if (cryptonomicaVerification == null) {
            // -> to servlet
            throw new IOException("Contract can not be loaded");
        }

        TransactionReceipt transactionReceipt = null;
        BigInteger priceInWeiBigInteger = BigInteger.valueOf(priceInWei);

        try {
            transactionReceipt = cryptonomicaVerification.setPriceForVerification(priceInWeiBigInteger).send();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

        LOG.info(GSON.toJson(transactionReceipt));
        transactionReceipt.hashCode();
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

        return transactionReceipt;
    }

    // mapping (address => string) public stringToSign; // string
    public static String getStringToSignFromSC(String userAddress) throws IOException {

        if (cryptonomicaVerification == null) {
            // -> to servlet
            throw new IOException("Contract can not be loaded");
        }

        String stringToSign = null;
        try {
            stringToSign = cryptonomicaVerification.stringToSign(userAddress).send();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new IOException(e.getMessage());
        }

        LOG.info("String stringToSign: " + stringToSign);

        return stringToSign;

    }

    public static String getSignedStringFromSC(String userAddress) throws IOException {

        if (cryptonomicaVerification == null) {
            // -> to servlet
            throw new IOException("Contract can not be loaded");
        }

        String signedString = null;
        try {
            signedString = cryptonomicaVerification.signedString(userAddress).send();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new IOException(e.getMessage());
        }

        LOG.info("signedString for " + userAddress + " : ");
        LOG.info(signedString);

        return signedString;
    }


    public static Verification getVerificationStructObj(String userAddress) throws IOException {

        if (cryptonomicaVerification == null) {
            // -> to servlet
            throw new IOException("Contract can not be loaded");
        }

        Verification verification = null;
        try {
            verification = new Verification(cryptonomicaVerification.verification(userAddress).send());
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new IOException(e.getMessage());
        }

        System.out.println(verification);

        return verification;
    }

    public static String getBytes32FingerprintToAddress(String fingerprint) throws IOException {

        if (cryptonomicaVerification == null) {
            // -> to servlet
            throw new IOException("Contract can not be loaded");
        }

        Bytes32 bytes32 = Web3jServices.stringToBytes32(fingerprint);
        String addressString = null;

        try {
            addressString = cryptonomicaVerification.bytes32FingerprintToAddress(bytes32.getValue()).send();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new IOException(e.getMessage());
        }

        System.out.println(addressString);

        return addressString;
    }

    /* Solidity:
    function addVerificationData(
        address acc,
        uint _keyCertificateValidUntil,
        string _firstName,
        string _lastName,
        uint _birthDate,
        string _nationality
    ) public returns (bool) { ...
    */
    public static TransactionReceipt addVerificationData(AddVerificationDataObj addVerificationDataObj) throws IOException {

        if (cryptonomicaVerification == null) {
            // -> to servlet
            throw new IOException("Contract can not be loaded");
        }

        TransactionReceipt transactionReceipt = null;
        try {
            transactionReceipt = cryptonomicaVerification.addVerificationData( // String acc, BigInteger _keyCertificateValidUntil, String _firstName, String _lastName, BigInteger _birthDate, String _nationality
                    addVerificationDataObj.getAcc(), // String acc
                    BigInteger.valueOf(addVerificationDataObj.getKeyCertificateValidUntil()), // BigInteger _keyCertificateValidUntil
                    addVerificationDataObj.getFirstName(), // tring _firstName
                    addVerificationDataObj.getLastName(), // String _lastName
                    BigInteger.valueOf(addVerificationDataObj.getBirthDate()), // BigInteger _birthDate
                    addVerificationDataObj.getNationality() //String _nationality

            ).send();
            ;
            LOG.info(transactionReceipt.getTransactionHash());
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new IOException(e.getMessage());
        }
        return transactionReceipt;
    }

}
