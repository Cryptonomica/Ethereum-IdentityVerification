#!/usr/bin/env bash

screen -dmS Geth geth --rpcapi personal,db,eth,net,web3 --rpc --testnet

# geth attach ipc://${HOME}/.ethereum/testnet/geth.ipc