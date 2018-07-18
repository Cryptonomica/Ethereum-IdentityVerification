#!/usr/bin/env bash

cd ./smartContract
./web3j.compile.smart.contracts.sh
nodejs ./deploy.CryptonomicaVerification.js

### change contract address in
# ./smartContract/build/contracts/networks.js
# net.cryptonomica.tomcatweb3j.utilities.Constants
# on front end

# ./server.scripts/deploy.to.server.sh

# truffle compile
# ./copy.truffle.artifacts.to.cryptonomica.github.io.sh

