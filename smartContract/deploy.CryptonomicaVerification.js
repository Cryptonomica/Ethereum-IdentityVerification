'use strict';

/* ------------------------------------------------------------*/
// to have access to node, run with --rpc , and unlock account:
// geth --testnet --rpc --unlock 0
// for testrpc, just run:
// testrpc
// for rinkeby:
// geth --rinkeby --rpc --rpcapi db,eth,net,web3,personal --unlock "0x3fAB7ebe4B2c31a75Cf89210aeDEfc093928A87D"
//
// JS console
// MainNet:
// geth attach
// testrpc:
// geth attach http://localhost:8545
// Ropsten:
// geth attach ipc://${HOME}/.ethereum/testnet/geth.ipc
// Rinkeby:
// geth attach ipc://${HOME}/.ethereum/rinkeby/geth.ipc
/* ------------------------------------------------------------*/

var rinkebyOwnerAddress = "0x3fAB7ebe4B2c31a75Cf89210aeDEfc093928A87D";
var ownerAddress = rinkebyOwnerAddress;

// https://log4js-node.github.io/log4js-node/
// https://github.com/log4js-node/log4js-node
var log4js = require('log4js');
log4js.configure({
    appenders: {
        everything: {type: 'file', filename: 'all-the-logs.log'}
    },
    categories: {
        default: {appenders: ['everything'], level: 'debug'}
    }
});
var logger = log4js.getLogger();
logger.level = 'debug';
logger.debug("**********************");
logger.debug("log4js logger started");

const fs = require("fs");

// https://github.com/ethereum/solc-js
// https://www.npmjs.com/package/solc
const solc = require('solc');

// https://www.npmjs.com/package/jsonfile
var jsonfile = require('jsonfile');

// web3 instantiation
// see: https://github.com/ethereum/web3.js/blob/master/example/node-app.js
// https://github.com/ethereum/wiki/wiki/JavaScript-API#adding-web3
// (!) use "web3": "^0.20.1" like truffle-contract uses, not the last version!
var Web3 = require('web3');
var web3 = new Web3();
web3.setProvider(new web3.providers.HttpProvider('http://localhost:8545'));

// Web3.js API Reference:
// https://github.com/ethereum/wiki/wiki/JavaScript-API#web3js-api-reference
if (web3.isConnected()) {
    web3.version.getNetwork(function (error, result) {
            if (error) {
                console.log(error);
                logger.debug(error);
            } else {
                console.log("web3.version.getNetwork:", result);
                logger.debug("web3.version.getNetwork:", result);

                web3.eth.defaultAccount = ownerAddress;

                // // https://github.com/ethereum/wiki/wiki/JavaScript-API#web3ethcontract
                // var source = fs.readFileSync('./contracts/CryptonomicaVerification.sol', 'utf8');
                // console.log(source);
                // var compiledContract = solc.compile(source, 1); // setting 1 as second parameter activates the optimiser
                // // console.log(compiledContract);
                // var contractName;
                // for (var property in compiledContract.contracts) {
                //     contractName = property;
                // }
                // console.log(contractName);
                // var abi = compiledContract.contracts[contractName].interface;
                // var bytecode = compiledContract.contracts[contractName].bytecode;
                // console.log(bytecode);
                // // var gasEstimate = web3.eth.estimateGas({data: bytecode});
                // // var Contract = web3.eth.contract(JSON.parse(abi));

                var bin = fs.readFileSync('./contracts/CryptonomicaVerification.bin', 'utf8');
                var bytecode = '0x' + bin;

                var abi = JSON.parse(
                    fs.readFileSync('./contracts/CryptonomicaVerification.abi', 'utf8')
                );

                // https://github.com/ethereum/wiki/wiki/JavaScript-API#web3ethcontract
                var Contract = web3.eth.contract(abi);
                // Deploy contract syncronous: The address will be added as soon as the contract is mined.
                // Additionally you can watch the transaction by using the "transactionHash" property
                var contractInstance = Contract.new(
                    {
                        data: bytecode,
                        gas: 6000000, // <<
                        from: ownerAddress
                    }
                );

                console.log(contractInstance);
                console.log('contractInstance.transactionHash: ', contractInstance.transactionHash); // The hash of the transaction, which created the contract
                console.log('contractInstance.address: ', contractInstance.address); // undefined at start, but will be auto-filled later
                // verification on etherscan works

                logger.debug('contractInstance.transactionHash: ', contractInstance.transactionHash);
                logger.debug('contractInstance.address: ', contractInstance.address);

            }
        }
    );
} else {
    console.log('web3 is not connected to Ethereum node');
    logger.debug('web3 is not connected to Ethereum node');
}

