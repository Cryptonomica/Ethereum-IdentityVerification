#!/usr/bin/env bash

# https://docs.web3j.io/getting_started.html#start-a-client
geth --rpcapi personal,db,eth,net,web3 --rpc --rinkeby

# geth attach ipc://${HOME}/.ethereum/rinkeby/geth.ipc