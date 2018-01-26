package net.cryptonomica.tomcatweb3j.utilities;

import org.apache.commons.codec.binary.StringUtils;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Hash;

public class Web3jServices {

    // see:
    // https://github.com/web3j/web3j/blob/master/utils/src/main/java/org/web3j/crypto/Hash.java#L12
    public static String keccak256FromHexEncodedInputDataAsString(String hexInput) {
        return Hash.sha3(hexInput);
    }

    // see code of:
    // https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#toString-byte:A-
    // this uses the same idea
    // to convert byte[] to representation like '0xf463b5590fa2a73f5296122649ed544fd1c3eeb35a63b71a883a31e330d22412'
    public static String bytesArrayToHexString(byte[] a) {
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";
        StringBuilder b = new StringBuilder();
        b.append("0x");
        for (int i = 0; ; i++) {
            b.append(
                    Integer.toHexString(a[i])
            );
            if (i == iMax)
                return b.toString();
        }
    }

    /*
     * see:
     * https://ethereum.stackexchange.com/questions/23549/convert-string-to-bytes32-in-web3j
     * */
    public static Bytes32 stringToBytes32(String string) {
        byte[] byteValue = string.getBytes();
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return new Bytes32(byteValueLen32);
    }

    public static String bytes32toString(Bytes32 bytes32) {
        return StringUtils.newStringUsAscii(bytes32.getValue());
    }

}

