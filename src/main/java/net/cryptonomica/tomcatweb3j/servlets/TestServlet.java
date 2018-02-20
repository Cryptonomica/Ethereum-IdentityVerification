package net.cryptonomica.tomcatweb3j.servlets;

import net.cryptonomica.tomcatweb3j.entities.DataCurrentNetwork;
import net.cryptonomica.tomcatweb3j.utilities.DataCurrentNetworkFactory;
import net.cryptonomica.tomcatweb3j.utilities.ServletUtils;
import org.web3j.protocol.Web3j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/*
 * --- for tests
 * */

@WebServlet(name = "TestServlet")
// tomcatweb3j.cryptonomica.net/testServlet
public class TestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ...
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TestServletResponse testServletResponce = new TestServletResponse();

        DataCurrentNetwork dataCurrentNetwork = DataCurrentNetworkFactory.getDataCurrentNetworkInstance();
        Web3j web3j = dataCurrentNetwork.web3j;

        // (0)
        testServletResponce.ethIsSyncing = web3j.ethSyncing().send().isSyncing();

        // (1)
        // https://github.com/ethereum/wiki/wiki/JavaScript-API#web3ethblocknumber
        // The number of the most recent block
        testServletResponce.ethBlockNumber = web3j.ethBlockNumber().send().getBlockNumber().intValue();

        // (2)
        /*
        # tomcat8 system variables
        /usr/share/tomcat8/bin/catalina.sh :
        #   Do not set the variables in this script. Instead put them into a script
        #   setenv.sh in CATALINA_BASE/bin to keep your customizations separate.
        sudo touch /usr/share/tomcat8/bin/setenv.sh
        # see: https://unix.stackexchange.com/questions/4335/how-to-insert-text-into-a-root-owned-file-using-sudo
        echo "export TEST_VAR=testEnvironmentVariableValue" | sudo tee -a /usr/share/tomcat8/bin/setenv.sh > /dev/null
        # or:
        sudo vim /usr/share/tomcat8/bin/setenv.sh
        * */
        testServletResponce.testEnvironmentVariable = System.getenv("TEST_VAR");

        // (3)
        testServletResponce.user = System.getProperty("user.name");
        if (testServletResponce.user.contains("tomcat")) { // on server: tomcat8
            testServletResponce.user = "ubuntu";
        }

        // (4)
        testServletResponce.timeOnServer = new Date();

        // send response:
        ServletUtils.sendJsonResponseFromObject(response, testServletResponce); // application/json
    }

    private class TestServletResponse {

        private Boolean ethIsSyncing; //............................0
        private Integer ethBlockNumber; //..........................1
        private String testEnvironmentVariable; //..................2
        private String user; //.....................................3
        private Date timeOnServer; //...............................4

        private TestServletResponse() {
        }

    }

}
