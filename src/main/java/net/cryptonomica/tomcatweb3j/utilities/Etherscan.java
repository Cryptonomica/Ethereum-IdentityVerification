package net.cryptonomica.tomcatweb3j.utilities;

import com.google.gson.Gson;
import net.cryptonomica.tomcatweb3j.entities.EtherscanTxStatus;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Etherscan {

    /* --- Gson: */
    private static final Gson GSON = new Gson();

    public static EtherscanTxStatus getTransaction(String etherscanBaseUrl, String etherscanApiKey, String txHash) throws IOException {

        // https://rinkeby.etherscan.io/api?module=transaction&action=getstatus&txhash=0x15f8e5ea1079d9a0bb04a4c58ae5fe7654b5b2b4463375ff7ffb490aa0032f3a&apikey=YourApiKeyToken

        String url = etherscanBaseUrl + "api"; //
        String charset = java.nio.charset.StandardCharsets.UTF_8.name();

        String query = String.format("module=%s&action=%s&txhash=%sapikey=%s",
                URLEncoder.encode("transaction", charset),
                URLEncoder.encode("getstatus", charset),
                URLEncoder.encode(txHash, charset),
                URLEncoder.encode(etherscanApiKey, charset)
        );
        URLConnection connection = new URL(url + "?" + query).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"); // Do as if you're using Chrome 41 on Windows 7
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream inputStream = connection.getInputStream();

        // NB: does not close inputStream, you can use IOUtils.closeQuietly for that
        String response = IOUtils.toString(inputStream, java.nio.charset.StandardCharsets.UTF_8);

        EtherscanTxStatus etherscanTxStatus = GSON.fromJson(response, EtherscanTxStatus.class);

        return etherscanTxStatus;

    }

}
