package net.cryptonomica.tomcatweb3j.servlets;

import net.cryptonomica.tomcatweb3j.utilities.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TestServlet")
// <url-pattern>/testServlet</url-pattern>
public class TestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ...
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        final String TEST_VAR = System.getenv("TEST_VAR");
        String USER = System.getProperty("user.name");
        if (USER.contains("tomcat")) { // on server: tomcat8
            USER = "ubuntu";
        }
        ServletUtils.sendTxtResponse(response, USER);
    }

}
