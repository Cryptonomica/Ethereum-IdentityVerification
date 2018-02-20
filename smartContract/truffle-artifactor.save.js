'use strict';

const fs = require("fs");

// see: https://github.com/trufflesuite/truffle-contract#full-example
// https://github.com/trufflesuite/truffle-artifactor#example
// https://github.com/trufflesuite/truffle-artifactor
var Artifactor = require("truffle-artifactor");
var artifactor = new Artifactor("./build"); // no '/' at the end

console.log(artifactor);

// for test:
for (var property in artifactor) {
    // if (artifactor.hasOwnProperty(property)) {
    console.log(property);
    // }
}

var truffleArtifactJSON = JSON.parse(
    fs.readFileSync('./build/contracts/CryptonomicaVerification.json', 'utf8')
);

artifactor.save(truffleArtifactJSON, 'CryptonomicaVerification.sol.js')
    .then(function (result) {
        console.log("artifactor.save:", result);
    }).catch(function (error) {
        console.log("artifactor.save:", error);
    }
); // (!!!) now artifactor.save produces .json file like 'truffle compile'



