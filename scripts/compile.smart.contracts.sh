#!/usr/bin/env bash

# see:
## https://github.com/web3j/web3j#java-smart-contract-wrappers

solc ./src/main/resources/*.sol --bin --abi --optimize -o ./src/main/resources/

#
~/LIBs/web3j-2.3.1/bin/web3j solidity generate ./src/resources/CryptonomicaVerify.bin ./src/main/resources/CryptonomicaVerify.abi -o ./src/main/java -p net.cryptonomica.tomcatweb3j