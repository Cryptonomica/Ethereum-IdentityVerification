#!/usr/bin/env bash

cp -vf ./build/contracts/CryptonomicaVerification.json ../../cryptonomica.github.io/app/ethereum/
cp -vf ./build/contracts/networks.js ../../cryptonomica.github.io/app/ethereum/
cp -vf ./contracts/CryptonomicaVerification.sol ../../cryptonomica.github.io/app/ethereum/
cp -vf ./contracts/CryptonomicaVerification.abi ../../cryptonomica.github.io/app/ethereum/
ls -latF ../../cryptonomica.github.io/app/ethereum/
