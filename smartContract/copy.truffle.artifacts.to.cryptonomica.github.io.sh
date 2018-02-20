#!/usr/bin/env bash

cp -v ./build/contracts/CryptonomicaVerification.json ../../cryptonomica.github.io/app/ethereum/
cp -v ./build/contracts/networks.js ../../cryptonomica.github.io/app/ethereum/
cp -v ./contracts/CryptonomicaVerification.sol ../../cryptonomica.github.io/app/ethereum/
cp -v ./contracts/CryptonomicaVerification.abi ../../cryptonomica.github.io/app/ethereum/
ls -latF ../../cryptonomica.github.io/app/ethereum/
