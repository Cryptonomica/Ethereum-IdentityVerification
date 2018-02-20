package net.cryptonomica.tomcatweb3j.entities;

import com.google.gson.Gson;
import org.web3j.tuples.generated.Tuple9;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * like struct in smart contract
 */
public class VerificationStruct implements Serializable {

    /* - Solidity:
    mapping(address => Verification) public verification; // (!) Gas requirement: infinite
    struct Verification {
        // all string have to be <= 32 chars
        string fingerprint; // ................................................0
        uint keyCertificateValidUntil; // .....................................1
        string firstName; // ..................................................2
        string lastName;// ....................................................3
        uint birthDate; //  ...................................................4
        string nationality; //  ...............................................5
        uint verificationAddedOn;// ...........................................6
        uint revokedOn; // ....................................................7
        string signedString; //................................................8
        // uint256 signedStringUploadedOnUnixTime; //... Stack too deep
    }
    */

    private String fingerprint; // .....................................................0
    private Integer keyCertificateValidUntil; // .......................................1
    private String firstName; // .......................................................2
    private String lastName;// .........................................................3
    private Integer birthDate; //  .....................................................4
    private String nationality; //  ....................................................5
    private Integer verificationAddedOn;// .............................................6
    private Integer revokedOn; // ......................................................7
    private String signedString; //.....................................................8


    /* ----- Constructors */

    public VerificationStruct() {
    }

    public VerificationStruct(
            String fingerprint, // .....................................................0
            Integer keyCertificateValidUntil, // .......................................1
            String firstName, // .......................................................2
            String lastName, // ........................................................3
            Integer birthDate, //  .....................................................4
            String nationality, //  ....................................................5
            Integer verificationAddedOn,// .............................................6
            Integer revokedOn, // ......................................................7
            String signedString //......................................................8
    ) {
        this.fingerprint = fingerprint;
        this.keyCertificateValidUntil = keyCertificateValidUntil;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.verificationAddedOn = verificationAddedOn;
        this.revokedOn = revokedOn;
        this.signedString = signedString;
    }

    /*
    public Verification(List<Type> verificationStruct) {
        this.fingerprint = (String) verificationStruct.get(0).getValue();
        this.keyCertificateValidUntil = (Integer) verificationStruct.get(1).getValue();
        this.firstName = (String) verificationStruct.get(2).getValue();
        this.lastName = (String) verificationStruct.get(3).getValue();
        this.birthDate = (Integer) verificationStruct.get(4).getValue();
        this.nationality = (String) verificationStruct.get(5).getValue();
        this.verificationAddedOn = (Integer) verificationStruct.get(6).getValue();
        this.revokedOn = (Integer) verificationStruct.get(7).getValue();
    }*/

    public VerificationStruct(Tuple9<String, BigInteger, String, String, BigInteger, String, BigInteger, BigInteger, String> tuple9) {
        this.fingerprint = tuple9.getValue1();
        this.keyCertificateValidUntil = tuple9.getValue2().intValueExact();
        this.firstName = tuple9.getValue3();
        this.lastName = tuple9.getValue4();
        this.birthDate = tuple9.getValue5().intValueExact();
        this.nationality = tuple9.getValue6();
        this.verificationAddedOn = tuple9.getValue7().intValueExact();
        this.revokedOn = tuple9.getValue8().intValueExact();
        this.signedString = tuple9.getValue9();
    }

    /* ---- to String */
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    /* ----- Getters and Setters */

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Integer getKeyCertificateValidUntil() {
        return keyCertificateValidUntil;
    }

    public void setKeyCertificateValidUntil(Integer keyCertificateValidUntil) {
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

    public Integer getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Integer birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getVerificationAddedOn() {
        return verificationAddedOn;
    }

    public void setVerificationAddedOn(Integer verificationAddedOn) {
        this.verificationAddedOn = verificationAddedOn;
    }

    public Integer getRevokedOn() {
        return revokedOn;
    }

    public void setRevokedOn(Integer revokedOn) {
        this.revokedOn = revokedOn;
    }

    public String getSignedString() {
        return signedString;
    }

    public void setSignedString(String signedString) {
        this.signedString = signedString;
    }
}
