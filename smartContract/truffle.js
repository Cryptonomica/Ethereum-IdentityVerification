module.exports = {
  // See <http://truffleframework.com/docs/advanced/configuration> to customize your Truffle configuration!
    networks: {
        development: {
            host: "localhost",
            port: 8545,
            network_id: "*" // Match any network id
        },
        rinkeby: {
            host: "localhost", // Connect to geth on the specified
            port: 8545,
            from: "0x3fAB7ebe4B2c31a75Cf89210aeDEfc093928A87D", // <<< address as on server ----------------------- <<<
            network_id: 4,
            gas: 4612388 // Gas limit used for deploys
        }
    }
};
