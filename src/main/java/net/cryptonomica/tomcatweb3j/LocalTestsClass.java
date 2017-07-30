package net.cryptonomica.tomcatweb3j;

import com.google.gson.Gson;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This is for testing web3j on local machine with running Ethereum node (Geth)
 * run:
 * geth --rpcapi personal,db,eth,net,web3 --rpc --testnet
 * geth attach ipc://${HOME}/.ethereum/testnet/geth.ipc
 */
public class LocalTestsClass {

    /* --- Gson: */
    private static final Gson GSON = new Gson();

    private static Web3j web3 = Web3j.build(new HttpService()); // defaults to http://localhost:8545/

    // see: https://github.com/conor10/web3j-javamag/blob/master/src/main/java/org/web3j/javamag/HelloWorld.java
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

    public static void main(String[] args) {

        System.out.println("------------------------------------------------------");
        System.out.println("web3.toString()");
        System.out.println(web3.toString());
        System.out.println("------------------------------------------------------");


        // read file with credentials data
        // see:
        // line 0-1: info
        // line 2: path to key file
        // line 3: password for key file
        String credentialsFilePath = "/home/viktor/.ethereum/testnet/keystore/credentials.txt";
        List<String> credentialsList = null;
        try {
            credentialsList = Files.readAllLines(Paths.get(credentialsFilePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("credentialsList:");
        System.out.println(credentialsList);
        final String pathToWalletFile = credentialsList.get(2);
        final String password = credentialsList.get(3);


        Credentials credentials = null;
        File file = new File(pathToWalletFile);
        System.out.println("------------------------------------------------------");
        System.out.println("file.getPath() : " + file.getPath());
        System.out.println("file.exists() : " + file.exists());
        System.out.println("file.getAbsolutePath() : " + file.getAbsolutePath());
        try {
            System.out.println("getCanonicalPath() : " + file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("file.canRead() : " + file.canRead());
        System.out.println("file.canWrite() : " + file.canWrite());
        System.out.println("file.canExecute() : " + file.canExecute());
        System.out.println("file.getName() : " + file.getName());
        System.out.println("ffile.getParent() : " + file.getParent());

        try {
            credentials = WalletUtils.loadCredentials(
                    password,
                    file // <<<<
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (credentials == null) {
            System.out.println("credentials can not be read :(");
            return;
        }

        String contractAddressStr = "0x23f06d3B6a719A6F90880E5b7Fb6A98016c2f532";// TestNet-------------------------- <<<
        CryptonomicaVerify cryptonomicaVerifyContract = CryptonomicaVerify.load(
                contractAddressStr,
                web3,
                credentials,
                GAS_PRICE,
                GAS_LIMIT
        );


        Address ownerAddress = null;
        try {
            ownerAddress = cryptonomicaVerifyContract.owner().get();
            System.out.println("------------------------------------------------------");
            System.out.println("ownerAddress.getTypeAsString() : " + ownerAddress.getTypeAsString());
            System.out.println("ownerAddress.getValue() : " + ownerAddress.getValue());
            System.out.println("ownerAddress.toString() : " + ownerAddress.toString());
            System.out.println("------------------------------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Address address = ownerAddress;
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

        System.out.println("------------------------------------------------------");
        System.out.println("Number of verifications for address: " + ownerAddress.toString()
                + " : " + numberOfVerifications);
        System.out.println("------------------------------------------------------");

        HashMap<Integer, ArrayList> verificationsArrayList = new HashMap<>();

        for (Integer i = 0; i < numberOfVerifications; i++) {

            System.out.println("i: " + i);

            Uint256 number = new Uint256(i);
            List verification = null;
            try {
                verification = cryptonomicaVerifyContract.verification(address, number).get();
                System.out.println("verification:");
                System.out.println(verification);

                Bytes32 bytes32 = (Bytes32) verification.get(0);
                byte[] bytesArr = bytes32.getValue();
                System.out.println("------------------------------------------------------");
                System.out.println("string to sign (hex string representation of bytes32)");
                System.out.println("bytesArr.toString():" + bytesArr.toString());

                System.out.println(
                        Numeric.toHexString(
                                bytes32.getValue() // works! the same value as shown on etherscan
                                // see: https://ropsten.etherscan.io/address/0x23f06d3b6a719a6f90880e5b7fb6a98016c2f532#readContract
                        )
                );
                System.out.println("------------------------------------------------------");

                Utf8String keyFingerprintUtf8String = (Utf8String) verification.get(4);
                String keyFingerprint = keyFingerprintUtf8String.getValue(); // works! the same value as shown on etherscan
                System.out.println("keyFingerprint: " + keyFingerprint
                        + " (stored in SC as " + keyFingerprintUtf8String.getTypeAsString()
                        + " (" + keyFingerprintUtf8String.getClass().getName() + "))"
                );

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(verification);
            verificationsArrayList.put(i, arrayList);
        } // end of for loop

        System.out.println("------------------------------------------------------");
        System.out.println(GSON.toJson(verificationsArrayList));
        System.out.println("------------------------------------------------------");

        // --------------
        Uint256 verificationNumber = new Uint256(1);
        Utf8String signedString = new Utf8String(TestPGPTools.signedString1);
        TransactionReceipt transactionReceipt = null;
        try {
            System.out.println("cryptonomicaVerifyContract.uploadSignedString");
            transactionReceipt = cryptonomicaVerifyContract.uploadSignedString(verificationNumber, signedString).get();
            System.out.println("transactionReceipt: ");
            System.out.println(GSON.toJson(transactionReceipt));

            // ----------------
            // get the next available nonce
            EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(
                    address.toString(), DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            // create our transaction

            // RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
            //         nonce, GAS_PRICE, GAS_LIMIT, contractAddressStr, new BigInteger());
            //
            // Transaction.createFunctionCallTransaction(address, );

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // ----------------
        try {
            List verification1 = cryptonomicaVerifyContract.verification(address, new Uint256(1)).get();
            System.out.println("verification1");
            System.out.println(GSON.toJson(verification1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
