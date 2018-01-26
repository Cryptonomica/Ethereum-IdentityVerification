package net.cryptonomica.tomcatweb3j.utilities;

import com.google.gson.Gson;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentVerifierBuilderProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 */
public class PGPTools {

    /* ---- Logger */
    private static final Logger LOG = Logger.getLogger(PGPTools.class.getName());
    /* --- Gson: */
    private static final Gson GSON = new Gson();

    public static PGPPublicKey readPublicKeyFromInputStream(InputStream input) throws IOException, PGPException {

        PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(
                PGPUtil.getDecoderStream(input), new JcaKeyFingerprintCalculator());
        //
        // we just loop through the collection till we find a key suitable for encryption, in the real
        // world you would probably want to be a bit smarter about this.
        Iterator keyRingIter = pgpPub.getKeyRings();
        while (keyRingIter.hasNext()) {
            PGPPublicKeyRing keyRing = (PGPPublicKeyRing) keyRingIter.next();

            Iterator keyIter = keyRing.getPublicKeys();
            while (keyIter.hasNext()) {
                PGPPublicKey key = (PGPPublicKey) keyIter.next();

                if (key.isMasterKey()) { // <<< (!!!) check for master key, not for encryption sub-key
                    // return public key found
                    return key;
                }
            }
        }

        throw new IllegalArgumentException("Can't find encryption key in key ring.");
    }


    public static PGPPublicKey readPublicKeyFromString(String armoredPublicPGPkeyBlock)
            throws IOException, PGPException {

        InputStream in = new ByteArrayInputStream(armoredPublicPGPkeyBlock.getBytes());
        PGPPublicKey pgpPublicKey = readPublicKeyFromInputStream(in);
        in.close();
        return pgpPublicKey;
    }

    // ---------------------------- NEW (2017-05-29):
    public static Boolean verifySignedString(
            String signedString,
            String pgpPublicKeyAsciiArmored // pgpPublicKeyData.getAsciiArmored().getValue()
    ) throws Exception {
        PGPPublicKey publicKey = readPublicKeyFromString(pgpPublicKeyAsciiArmored);
        Boolean result = verifyText(signedString, publicKey);
        return result;
    }

    public static Boolean verifyText(String plainText, PGPPublicKey publicKey) throws Exception {

        String pattern = "-----BEGIN PGP SIGNED MESSAGE-----\\r?\\n.*?\\r?\\n\\r?\\n(.*)\\r?\\n(-----BEGIN PGP SIGNATURE-----\\r?\\n.*-----END PGP SIGNATURE-----)";

        Pattern regex = Pattern.compile(pattern, Pattern.CANON_EQ | Pattern.DOTALL);

        Matcher regexMatcher = regex.matcher(plainText);

        // if input test is a signed plaintext
        if (regexMatcher.find()) {

            String signedDataStr = regexMatcher.group(1);
            LOG.warning("signedDataStr: ");
            LOG.warning(signedDataStr);

            String signatureStr = regexMatcher.group(2);
            LOG.warning("signatureStr: ");
            LOG.warning(signatureStr);

            ByteArrayInputStream signedDataIn = new ByteArrayInputStream(signedDataStr.getBytes("UTF8"));
            ByteArrayInputStream signatureIn = new ByteArrayInputStream(signatureStr.getBytes("UTF8"));

            Boolean result = verifyFile(signedDataIn, signatureIn, publicKey);
            LOG.warning("verification result: " + result);

            return result;
        }

        throw new Exception("Cannot recognize input data");
    }

    public static Boolean verifyFile(
            InputStream signedDataIn, // signed data
            InputStream signatureIn, //  signature
            PGPPublicKey pgpPublicKey) //  key
            throws Exception {
        signatureIn = PGPUtil.getDecoderStream(signatureIn);
        //dataIn = PGPUtil.getDecoderStream(dataIn); // not needed
        PGPObjectFactory pgpObjectFactory = new PGPObjectFactory(
                signatureIn,
                new JcaKeyFingerprintCalculator() // <<<< TODO: check if this is correct
        );

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
            pgpObjectFactory = new PGPObjectFactory(
                    pgpCompressedData.getDataStream(),
                    new JcaKeyFingerprintCalculator() // <<<< TODO: check if this is correct
            );
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

//        signatureObject.initVerify(
//                pgpPublicKey,
//                "BC"
//        );
        // https://www.borelly.net/cb/docs/javaBC-1.4.8/pg/org/bouncycastle/openpgp/PGPSignature.html#initVerify(org.bouncycastle.openpgp.PGPPublicKey,%20java.security.Provider)
        // Deprecated. use init(PGPContentVerifierBuilderProvider, PGPPublicKey)

        signatureObject.init(
                new JcaPGPContentVerifierBuilderProvider(),
                pgpPublicKey
        );

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
