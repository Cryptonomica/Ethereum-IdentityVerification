package net.cryptonomica.tomcatweb3j.entities;

public class EtherscanTxStatus {
    public String status;
    public String message;
    public Result result;

    private class Result {
        public String isError;
        public String errDescription;
    }

}
