#!/usr/bin/env bash

# see:
## https://github.com/web3j/web3j#java-smart-contract-wrappers

echo "------ solc:"
echo "solc ./contracts/CryptonomicaVerification.sol --bin --abi --optimize --gas --overwrite -o ./contracts/"
solc ./contracts/CryptonomicaVerification.sol --bin --abi --optimize --gas --overwrite -o ./contracts/ > ./contracts/CryptonomicaVerification.Gas.Estimation.txt
echo "----- ls ./contracts -a"
ls ./contracts -a

#
echo "----- web3j:"
~/LIBs/web3j-3.2.0/bin/web3j solidity generate ./contracts/CryptonomicaVerification.bin ./contracts/CryptonomicaVerification.abi -o ../src/main/java -p net.cryptonomica.tomcatweb3j.contracts
