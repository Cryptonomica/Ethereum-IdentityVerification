#!/usr/bin/env bash

# http://truffleframework.com

# install:
# sudo npm install -g truffle ethereumjs-testrpc

# init ( see http://truffleframework.com/docs/getting_started/project ):
# truffle init


# truffle compile
# Writing artifacts to ./build/contracts

# (!!!)
# truffle deploy
# changes contacts data in ./build/contracts

# to deploy on Ropsten:
# (https://ethereum.stackexchange.com/questions/23279/what-is-steps-to-deploy-the-contract-on-ropsten-network/23320 )
# $ geth --fast --cache=1048 --testnet --unlock "0xmyaddress" --rpc --rpcapi "eth,net,web3" --rpccorsdomain '*' --rpcaddr localhost --rpcport 8545
# truffle migrate --network ropsten
