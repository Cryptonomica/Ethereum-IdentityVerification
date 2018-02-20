package net.cryptonomica.tomcatweb3j.utilities;


import com.google.gson.Gson;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Logger;

/**
 * utilities to use in servlets
 */
public class ServletUtils {

    /* ---- Logger: */
    private static final Logger LOG = Logger.getLogger(ServletUtils.class.getName());
    /* --- Gson: */
    private static final Gson GSON = new Gson();

    public static String getRequestParameters(HttpServletRequest request) {

        // see: http://edwin.baculsoft.com/2014/04/logging-http-request-parameters-using-http-servlet/

        String result = "{";

        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parametername = enumeration.nextElement();
            result += "\"" + parametername + "\":\"" + request.getParameter(parametername) + "\",";
        }
        result = removeLastComma(result) + "}";
        // LOG.warning(result);
        return result;
    } // end of getRequestParameters()

    public static String getCookies(HttpServletRequest request) {
        String result = "[";
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            result += "";
        } else {
            for (Cookie cookie : cookies) {
                result += "{"
                        + "\"name\": \"" + cookie.getName() + "\","
                        + "\"value\": \"" + cookie.getValue() + "\","
                        + "\"domain\": \"" + cookie.getDomain() + "\","
                        + "\"path\": \"" + cookie.getPath() + "\","
                        + "\"comment\": \"" + cookie.getComment()
                        + "},";
            }
            result = removeLastComma(result);
        }
        result += "]";
        // LOG.warning(result);
        return result;
    } // end of getCookies()

    public static String getRequestHeaders(HttpServletRequest request) {

        String result = "{";

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            result += "\"" + headerName + "\":\"" + request.getHeader(headerName) + "\",";
        }
        result = removeLastComma(result) + "}";
        // LOG.warning(result);
        return result;
    } // end of getRequestHeaderNames()

    private static String removeLastComma(String str) {
        int commaIndex = str.lastIndexOf(","); // -1 if there is no such occurrence.
        if (commaIndex > 0 && commaIndex == str.length() - 1) {
            str = str.substring(0, commaIndex);
        }
        return str;
    }

    public static String getAllRequestData(HttpServletRequest request) {

        String headerNamesStr = ServletUtils.getRequestHeaders(request);
        String requestParametersStr = ServletUtils.getRequestParameters(request);
        String cookiesStr = ServletUtils.getCookies(request);
        String requestDataSrt = "{\"request\": "
                //
                + "{"
                + "\"requestHeaders\": " + headerNamesStr + ","
                + "\"requestParameters\": " + requestParametersStr + ","
                + "\"cookies\":" + cookiesStr
                + "}"
                //
                + "}";
        return requestDataSrt;
    }

    public static void sendTxtResponse(HttpServletResponse response, final String text) throws IOException {

        // https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types
        response.setContentType("text/plain");

        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter(); //get the stream to write the data
        pw.println(text);
        pw.close(); //closing the stream

    } // end of sendTxtResponse()

    private static void sendJsonResponse(HttpServletResponse response, final String jsonStr) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter(); //get the stream to write the data
        pw.println(jsonStr);
        pw.close(); //closing the stream

    } // end of sendJsonResponse()

    public static void sendJsonResponseFromObject(HttpServletResponse response, Object object) throws IOException {
        LOG.info("object:");
        LOG.info(object.getClass() + ": " + object.toString());
        String jsonStr = GSON.toJson(object);
        LOG.info(jsonStr);
        sendJsonResponse(response, jsonStr);
    }

}
