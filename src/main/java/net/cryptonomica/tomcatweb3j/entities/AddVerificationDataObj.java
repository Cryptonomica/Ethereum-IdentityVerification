package net.cryptonomica.tomcatweb3j.entities;

import com.google.gson.Gson;
import net.cryptonomica.tomcatweb3j.utilities.Web3jServices;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.logging.Logger;

public class AddVerificationDataObj {

    /* --- Logger: */
    // https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html
    // Find or create a logger for a named subsystem. If a logger has already been created with the given name it is returned.
    // Otherwise a new logger is created.
    // - When running Tomcat on unixes, the console output is usually redirected to the file named catalina.out
    private static final Logger LOG = Logger.getLogger(AddVerificationDataObj.class.getName());

    /* --- Gson: */
    private static final Gson GSON = new Gson();

    /* --- data fields */

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
    private String acc;
    private String fingerprint;
    private byte[] fingerprintBytes20;
    private BigInteger keyCertificateValidUntil;
    private String firstName;
    private String lastName;
    private BigInteger birthDate;
    private String nationality;

    /* --- Constructors */

    public AddVerificationDataObj() {
    }

    public AddVerificationDataObj(HttpServletRequest request) {
        this.acc = request.getParameter("acc");
        this.fingerprint = request.getParameter("fingerprint");
        this.fingerprintBytes20 = Web3jServices.bytes20FromHexString(request.getParameter("fingerprint")).getValue();
        // see: https://stackoverflow.com/questions/2646049/what-is-the-most-effective-way-to-create-biginteger-instance-from-int-value
        this.keyCertificateValidUntil = BigInteger.valueOf(
                // Integer.parseInt(request.getParameter("keyCertificateValidUntil"))
                Integer.parseUnsignedInt(
                        request.getParameter("keyCertificateValidUntil")
                )
        );
        this.firstName = request.getParameter("firstName");
        this.lastName = request.getParameter("lastName");
        // see: https://stackoverflow.com/questions/2646049/what-is-the-most-effective-way-to-create-biginteger-instance-from-int-value
        this.birthDate = BigInteger.valueOf(
                Integer.parseUnsignedInt(
                        request.getParameter("birthDate")
                )
        );
        this.nationality = request.getParameter("nationality");

        LOG.info(this.toString());
    }

    /* --- to String */
    @Override
    public String toString() {
        return GSON.toJson(this);
    }

    /* --- Getters and Setters */

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public byte[] getFingerprintBytes20() {
        return fingerprintBytes20;
    }

    public void setFingerprintBytes20(byte[] fingerprintBytes20) {
        this.fingerprintBytes20 = fingerprintBytes20;
    }

    public BigInteger getKeyCertificateValidUntil() {
        return keyCertificateValidUntil;
    }

    public void setKeyCertificateValidUntil(BigInteger keyCertificateValidUntil) {
        this.keyCertificateValidUntil = keyCertificateValidUntil;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigInteger getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(BigInteger birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
