package net.cryptonomica.tomcatweb3j.entities;

import com.google.gson.Gson;
import net.cryptonomica.tomcatweb3j.utilities.Web3jServices;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class AddVerificationDataObj {

    /* --- Logger: */
    // https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html
    // Find or create a logger for a named subsystem. If a logger has already been created with the given name it is returned.
    // Otherwise a new logger is created.
    // - When running Tomcat on unixes, the console output is usually redirected to the file named catalina.out
    private static final String className = Web3jServices.class.getName();
    private static final Logger LOG = Logger.getLogger(className);

    /* --- Gson: */
    private static final Gson GSON = new Gson();

    private String acc;
    private Integer keyCertificateValidUntil;
    private String firstName;
    private String lastName;
    private Integer birthDate;
    private String nationality;

    /* --- Constructors */

    public AddVerificationDataObj() {
        //
    }

    public AddVerificationDataObj(HttpServletRequest request) {
        this.acc = request.getParameter("acc");
        this.keyCertificateValidUntil = Integer.parseInt(request.getParameter("keyCertificateValidUntil"));
        this.firstName = request.getParameter("firstName");
        this.lastName = request.getParameter("lastName");
        this.birthDate = Integer.parseInt(request.getParameter("birthDate"));
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

}
