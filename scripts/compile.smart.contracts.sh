#!/usr/bin/env bash

# see:
## https://github.com/web3j/web3j#java-smart-contract-wrappers

#solc ./src/main/resources/*.sol --bin --abi --optimize -o ./src/main/resources/
solc ./src/main/resources/contracts/CryptonomicaVerification.sol --bin --abi --optimize --gas --overwrite -o ./src/main/resources/contracts/
echo "------------------"
ls ./src/main/resources/contracts -a

#
~/LIBs/web3j-3.1.1/bin/web3j solidity generate ./src/main/resources/contracts/CryptonomicaVerification.bin ./src/main/resources/contracts/CryptonomicaVerification.abi -o ./src/main/java -p net.cryptonomica.tomcatweb3j.contracts
#~/LIBs/web3j-2.3.1/bin/web3j solidity generate ./src/main/resources/contracts/CryptonomicaVerification.bin ./src/main/resources/contracts/CryptonomicaVerification.abi -o ./src/main/java -p net.cryptonomica.tomcatweb3j.contracts
#~/LIBs/web3j-3.0.1/bin/web3j solidity generate ./src/main/resources/contracts/CryptonomicaVerification.bin ./src/main/resources/contracts/CryptonomicaVerification.abi -o ./src/main/java -p net.cryptonomica.tomcatweb3j.contracts
