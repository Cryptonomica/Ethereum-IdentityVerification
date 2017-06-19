#!/usr/bin/env bash

# see:
## https://github.com/web3j/web3j#java-smart-contract-wrappers

solc ./src/main/webapp/WEB-INF/smartcontracts/*.sol --bin --abi --optimize -o ./src/main/webapp/WEB-INF/smartcontracts/

#
~/LIBs/web3j-2.2.1/bin/web3j solidity generate ./src/main/webapp/WEB-INF/smartcontracts/CryptonomicaVerify.bin ./src/main/webapp/WEB-INF/smartcontracts/CryptonomicaVerify.abi -o ./src/main/java -p net.cryptonomica.tomcatweb3j