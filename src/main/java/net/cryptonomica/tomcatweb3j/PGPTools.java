package net.cryptonomica.tomcatweb3j;


import com.google.gson.Gson;
import org.bouncycastle.bcpg.ArmoredInputStream;
import org.bouncycastle.openpgp.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by viktor on 11/01/16. - modified 2016-04-05 (
 * was a bug (?) reading userID in key DSA + ElGamal in BC 1.54 )
 */
public class PGPTools {

    /* --- Gson: */
    private static final Gson GSON = new Gson();

//    <!-- was a bug reading userID in key DSA + ElGamal --> :
//    /**
//     * A simple routine that opens a key ring file and loads the first available key
//     * suitable for encryption.
//     *
//     * @param input data stream containing the public key data
//     * @return the first public key found.
//     * @throws IOException
//     * @throws PGPException
//     */
//    public static PGPPublicKey readPublicKeyFromInputStream (InputStream input) throws IOException, PGPException {
//        PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(
//                PGPUtil.getDecoderStream(input), new JcaKeyFingerprintCalculator());
//        //
//        // we just loop through the collection till we find a key suitable for encryption, in the real
//        // world you would probably want to be a bit smarter about this.
//        Iterator keyRingIter = pgpPub.getKeyRings();
//        while (keyRingIter.hasNext()) {
//            PGPPublicKeyRing keyRing = (PGPPublicKeyRing) keyRingIter.next();
//
//            Iterator keyIter = keyRing.getPublicKeys();
//            while (keyIter.hasNext()) {
//                PGPPublicKey key = (PGPPublicKey) keyIter.next();
//
//                if (key.isEncryptionKey()) {
//                    return key;
//                }
//            }
//        }
//
//        throw new IllegalArgumentException("Can't find encryption key in key ring.");
//    }
//    <!-- was a bug reading userID in key DSA + ElGamal -->

    /// NEW:
    private static PGPPublicKey readPublicKey(InputStream iKeyStream) throws IOException {
        PGPPublicKeyRing newKey = new PGPPublicKeyRing(new ArmoredInputStream(iKeyStream));
        return newKey.getPublicKey();
    }

    public static PGPPublicKey readPublicKeyFromString(String armoredPublicPGPKeyBlock)
            throws IOException, PGPException {

        InputStream in = new ByteArrayInputStream(armoredPublicPGPKeyBlock.getBytes());
        PGPPublicKey pgpPublicKey = readPublicKey(in);
        in.close();
        return pgpPublicKey;
    }

    // ---------------------------- NEW (2017-05-29):
    public static Boolean verifySignedString(
            String signedString,
            String pgpPublicKeyAsciiArmored // pgpPublicKeyData.getAsciiArmored().getValue()
    ) throws Exception {

        // see: https://stackoverflow.com/questions/9660967/bouncy-castle-no-such-provider-exception
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        //
        PGPPublicKey publicKey = readPublicKeyFromString(pgpPublicKeyAsciiArmored);
        Boolean result = verifyText(signedString, publicKey);
        return result;
    }

    private static Boolean verifyText(String plainText, PGPPublicKey publicKey) throws Exception {
        String pattern = "-----BEGIN PGP SIGNED MESSAGE-----\\r?\\n.*?\\r?\\n\\r?\\n(.*)\\r?\\n(-----BEGIN PGP SIGNATURE-----\\r?\\n.*-----END PGP SIGNATURE-----)";
        Pattern regex = Pattern.compile(pattern, Pattern.CANON_EQ | Pattern.DOTALL);
        Matcher regexMatcher = regex.matcher(plainText);
        if (regexMatcher.find()) {
            String signedDataStr = regexMatcher.group(1);
            String signatureStr = regexMatcher.group(2);

            ByteArrayInputStream signedDataIn = new ByteArrayInputStream(signedDataStr.getBytes("UTF8"));
            ByteArrayInputStream signatureIn = new ByteArrayInputStream(signatureStr.getBytes("UTF8"));

            Boolean result = verifyFile(signedDataIn, signatureIn, publicKey);
            return result;
        }
        throw new Exception("Cannot recognize input data");
    }

    public static Boolean verifyFile(
            InputStream signedDataIn, // signed data
            InputStream signatureIn, //  signature
            PGPPublicKey pgpPublicKey) //  key
            throws Exception {

        // see: https://stackoverflow.com/questions/9660967/bouncy-castle-no-such-provider-exception
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        signatureIn = PGPUtil.getDecoderStream(signatureIn);
        //dataIn = PGPUtil.getDecoderStream(dataIn); // not needed
        PGPObjectFactory pgpObjectFactory = new PGPObjectFactory(signatureIn);

        PGPSignatureList pgpSignatureList = null;
        Object o;

        // get adn check: pgpObjectFactory.nextObject()
        try {
            o = pgpObjectFactory.nextObject();
            if (o == null)
                throw new Exception("pgpObjectFactory.nextObject() returned null");
        } catch (Exception ex) {

            throw new Exception("Invalid input data"); //
        }

        if (o instanceof PGPCompressedData) {

            PGPCompressedData pgpCompressedData = (PGPCompressedData) o;
            pgpObjectFactory = new PGPObjectFactory(pgpCompressedData.getDataStream());
            pgpSignatureList = (PGPSignatureList) pgpObjectFactory.nextObject();

        } else {
            pgpSignatureList = (PGPSignatureList) o;
        }

        int ch;

        // A PGP signatureObject
        // https://www.borelly.net/cb/docs/javaBC-1.4.8/pg/index.html?org/bouncycastle/openpgp/PGPSignature.html
        PGPSignature signatureObject = pgpSignatureList.get(0);

        if (pgpPublicKey == null)
            throw new Exception("Cannot find key 0x"
                    + Integer.toHexString((int) signatureObject.getKeyID()).toUpperCase()
                    + " in the pubring"
            );

        signatureObject.initVerify(pgpPublicKey, "BC");

        while ((ch = signedDataIn.read()) >= 0) {
            signatureObject.update((byte) ch);
        }

        if (signatureObject.verify()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    } // end of verifyFile()


}