pragma solidity ^0.4.15;


// an example how to use CryptonomicaVerification contract

// ......................................................

contract CryptonomicaVerification {
    // describe functions from CryptonomicaVerification smart contract that you need to use
    // see: https://dappsforbeginners.wordpress.com/tutorials/interactions-between-contracts/
    mapping (address => bytes32) public nationality;
}


// ......................................................

contract UseVerification {

    address owner;

    CryptonomicaVerification cryptonomicaVerification;

    address cryptonomicaVerificationAddress;


    /* ------------------------------------------- Constructor */
    function UseVerification() {
        owner = msg.sender;
    }

    function setCryptonomicaVerificationAddress(address _cryptonomicaVerificationAddress) {
        require(msg.sender == owner);
        cryptonomicaVerificationAddress = _cryptonomicaVerificationAddress;
        cryptonomicaVerification = CryptonomicaVerification(_cryptonomicaVerificationAddress);

    }

    function stringToBytes32(string memory source) returns (bytes32 result) {
        // we use this to serve bytes32 instead of string to other smart contracts
        // see:
        // https://ethereum.stackexchange.com/questions/1081/how-to-concatenate-a-bytes32-array-to-a-string
        // https://ethereum.stackexchange.com/questions/2519/how-to-convert-a-bytes32-to-string
        assembly {
        result := mload(add(source, 32))
        }
    }

    function isUsCitizen(address userAddress) private returns (bool){
        // see:
        // https://ethereum.stackexchange.com/questions/13616/accessing-a-public-mapping-within-a-contract-from-a-different-contract

        return cryptonomicaVerification.nationality(userAddress) == stringToBytes32("US");
    }

}
