#!/usr/bin/env bash

# create nodejs module in IntelliJ IDEA, then:

npm -v
sudo npm install npm@latest -g

npm uninstall express serve-favicon hbs body-parser cookie-parser morgan debug
# removed 84 packages in 0.729s
rm -r bin public routes views
mkdir contracts

# (!) use "web3": "^0.20.1" like truffle-contract uses, not the last version!
# use solc-js version as last solc version
npm install log4js jsonfile solc@0.4.19 web3@0.20.1 truffle-artifactor




