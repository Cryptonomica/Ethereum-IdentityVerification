#!/usr/bin/env bash

# http://truffleframework.com
#https://github.com/trufflesuite/truffle

# install:
# sudo npm install -g truffle

# init ( see http://truffleframework.com/docs/getting_started/project ):
# truffle init


# truffle compile
# Writing artifacts to ./build/contracts
# see: https://github.com/trufflesuite/truffle-contract-schema

# (!!!)
# truffle deploy
# changes contacts data in ./build/contracts
# contract does not pass code verification on etherscan when deployed via truffle (Truffle v4.0.6)
# (at the same time if deployed with pure web3.js - O.K.)

### to deploy on Ropsten:
## (https://ethereum.stackexchange.com/questions/23279/what-is-steps-to-deploy-the-contract-on-ropsten-network/23320 )
# geth --fast --cache=1048 --testnet --unlock "0xmyaddress" --rpc --rpcapi "eth,net,web3" --rpccorsdomain '*' --rpcaddr localhost --rpcport 8545
# cd ./src/main/resources/
# truffle migrate --network ropsten
## (truffle deploy is an alias for truffle migrate. They both do the same thing)
## see: https://ethereum.stackexchange.com/questions/23065/truffle-migrate-vs-truffle-deploy
# geth attach ipc://${HOME}/.ethereum/testnet/geth.ipc

### Deploying Truffle Contracts to Rinkeby
## https://blog.abuiles.com/blog/2017/07/09/deploying-truffle-contracts-to-rinkeby/
# geth --rinkeby --rpc --rpcapi db,eth,net,web3,personal --unlock "0x3fAB7ebe4B2c31a75Cf89210aeDEfc093928A87D"
# cd ./src/main/resources/
# truffle migrate --network rinkeby
## (truffle deploy is an alias for truffle migrate. They both do the same thing)
## see: https://ethereum.stackexchange.com/questions/23065/truffle-migrate-vs-truffle-deploy
# geth attach ipc://${HOME}/.ethereum/rinkeby/geth.ipc
