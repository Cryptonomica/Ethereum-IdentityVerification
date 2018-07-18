#!/usr/bin/env bash

# first run
# screen -dmS Geth geth --fast --cache=2048 --jitvm
#screen -dmS Geth geth --rpcapi personal,db,eth,net,web3 --rpc
screen -dmS Geth geth --rpcapi personal,db,eth,net,web3 --rpc --cache=4096 --nousb
# no need to unlock account, this will be done by web3j
