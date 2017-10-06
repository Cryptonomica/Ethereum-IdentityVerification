package net.cryptonomica.tomcatweb3j;

import org.web3j.abi.datatypes.Type;

import java.io.Serializable;
import java.util.List;

/**
 * like struct in smart contract
 */
public class Verification implements Serializable {

    private String stringToSign; //------------------------------------------------- 0
    private String signedString; //--------------------------------------------------1
    private Boolean signedStringUploaded; // ----------------------------------------2
    private String name; // first name + last name // -------------------------------3
    private String keyFingerprint; // -----------------------------------------------4
    private Integer keyValidUntil; // unix time // ----------------------------------5
    private Integer birthDateYear; // -----------------------------------------------6
    private Integer birthDateMonth; // ----------------------------------------------7
    private Integer birthDateDay; // ------------------------------------------------8
    private Boolean revoked; // -- --------------------------------------------------9
    private Integer revokedOn; // unix time // -------------------------------------10

    public Verification() {
    }

    public Verification(String stringToSign, String signedString, Boolean signedStringUploaded, String name,
                        String keyFingerprint, Integer keyValidUntil, Integer birthDateYear, Integer birthDateMonth,
                        Integer birthDateDay, Boolean revoked, Integer revokedOn) {
        this.stringToSign = stringToSign;
        this.signedString = signedString;
        this.signedStringUploaded = signedStringUploaded;
        this.name = name;
        this.keyFingerprint = keyFingerprint;
        this.keyValidUntil = keyValidUntil;
        this.birthDateYear = birthDateYear;
        this.birthDateMonth = birthDateMonth;
        this.birthDateDay = birthDateDay;
        this.revoked = revoked;
        this.revokedOn = revokedOn;
    }

    public Verification(List<Type> verificationStruct) {

        this.stringToSign = (String) verificationStruct.get(0).getValue();
        this.signedString = (String) verificationStruct.get(1).getValue();
        this.signedStringUploaded = (Boolean) verificationStruct.get(2).getValue();
        this.name = (String) verificationStruct.get(3).getValue();
        this.keyFingerprint = (String) verificationStruct.get(4).getValue();
        this.keyValidUntil = (Integer) verificationStruct.get(5).getValue();
        this.birthDateYear = (Integer) verificationStruct.get(6).getValue();
        this.birthDateMonth = (Integer) verificationStruct.get(7).getValue();
        this.birthDateDay = (Integer) verificationStruct.get(8).getValue();
        this.revoked = (Boolean) verificationStruct.get(9).getValue();
        this.revokedOn = (Integer) verificationStruct.get(10).getValue();

    }

    public String getStringToSign() {
        return stringToSign;
    }

    public void setStringToSign(String stringToSign) {
        this.stringToSign = stringToSign;
    }

    public String getSignedString() {
        return signedString;
    }

    public void setSignedString(String signedString) {
        this.signedString = signedString;
    }

    public Boolean getSignedStringUploaded() {
        return signedStringUploaded;
    }

    public void setSignedStringUploaded(Boolean signedStringUploaded) {
        this.signedStringUploaded = signedStringUploaded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyFingerprint() {
        return keyFingerprint;
    }

    public void setKeyFingerprint(String keyFingerprint) {
        this.keyFingerprint = keyFingerprint;
    }

    public Integer getKeyValidUntil() {
        return keyValidUntil;
    }

    public void setKeyValidUntil(Integer keyValidUntil) {
        this.keyValidUntil = keyValidUntil;
    }

    public Integer getBirthDateYear() {
        return birthDateYear;
    }

    public void setBirthDateYear(Integer birthDateYear) {
        this.birthDateYear = birthDateYear;
    }

    public Integer getBirthDateMonth() {
        return birthDateMonth;
    }

    public void setBirthDateMonth(Integer birthDateMonth) {
        this.birthDateMonth = birthDateMonth;
    }

    public Integer getBirthDateDay() {
        return birthDateDay;
    }

    public void setBirthDateDay(Integer birthDateDay) {
        this.birthDateDay = birthDateDay;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }

    public Integer getRevokedOn() {
        return revokedOn;
    }

    public void setRevokedOn(Integer revokedOn) {
        this.revokedOn = revokedOn;
    }
}
