pragma solidity ^0.4.18;


/*
an example how to use CryptonomicaVerification contract
developed by cryptonomica.net, 2017
last version: 2017-11-21
github: https://github.com/Cryptonomica/
*/

// ......................................................

contract CryptonomicaVerification {
    // describe functions from CryptonomicaVerification smart contract that you need to use
    // see: https://dappsforbeginners.wordpress.com/tutorials/interactions-between-contracts/
    mapping(address => bytes32) public fingerprint; // ...........................................................0
    mapping(address => uint) public keyCertificateValidUntil; // unix time .......................................1
    mapping(address => bytes32) public firstName; // .............................................................2
    mapping(address => bytes32) public lastName; // ..............................................................3
    mapping(address => uint) public birthDate; // unix time ......................................................4
    mapping(address => bytes32) public nationality; //      ......................................................5
    mapping(address => uint) public verificationAddedOn; // unix time ............................................6
    mapping(address => uint) public revokedOn; // unix time, returns uint256: 0 if verification is not revoked ...7
    //
    function stringToBytes32(string memory source) public constant returns (bytes32);

    function bytes32ToString(bytes32 x) public constant returns (string);
}


// ......................................................

contract UseCryptonomicaVerification {

    address owner;

    CryptonomicaVerification cryptonomicaVerification;

    address cryptonomicaVerificationAddress;

    /* --------- LEDGER */
    mapping(address => bool) isUsCitizen;
    //
    mapping(address => bytes32) public fingerprint; // ...........................................................0
    mapping(address => uint) public keyCertificateValidUntil; // unix time .......................................1
    mapping(address => string) public firstName; // ..............................................................2
    mapping(address => string) public lastName; // ...............................................................3
    mapping(address => uint) public birthDate; // unix time ......................................................4
    // Nationality - from user passport or id document:
    // 2-letter country codes defined in ISO 3166
    // like returned by Locale.getISOCountries() in Java (upper case)
    // see: https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
    mapping(address => bytes32) public nationality; //      ......................................................5
    mapping(address => uint) public verificationAddedOn; // unix time ............................................6
    mapping(address => uint) public revokedOn; // unix time, returns uint256: 0 if verification is not revoked ...7


    /* ------------------------------------------- Constructor */
    // Constructor must be public or internal
    function UseVerification() public {
        owner = msg.sender;
    }

    function setCryptonomicaVerificationAddress(address _cryptonomicaVerificationAddress) public {
        require(msg.sender == owner);
        cryptonomicaVerificationAddress = _cryptonomicaVerificationAddress;
        cryptonomicaVerification = CryptonomicaVerification(_cryptonomicaVerificationAddress);
    }

    /* --- (!) - utilities : */

    // see also:
    // https://ethereum.stackexchange.com/questions/2519/how-to-convert-a-bytes32-to-string
    // https://ethereum.stackexchange.com/questions/1081/how-to-concatenate-a-bytes32-array-to-a-string
    function bytes32ToString(bytes32 _bytes32) public pure returns (string){
        // string memory str = string(_bytes32);
        // TypeError: Explicit type conversion not allowed from "bytes32" to "string storage pointer"
        // thus we should convert bytes32 to bytes (to dynamically-sized byte array)
        bytes memory bytesArray = new bytes(32);
        for (uint256 i; i < 32; i++) {
            bytesArray[i] = _bytes32[i];
        }
        return string(bytesArray);
    }

    // Main functions

    function checkUsCitizen(address userAddress) public returns (bool){
        // see:
        // https://ethereum.stackexchange.com/questions/13616/accessing-a-public-mapping-within-a-contract-from-a-different-contract
        isUsCitizen[userAddress] = cryptonomicaVerification.nationality(userAddress) == cryptonomicaVerification.stringToBytes32("US");
        return isUsCitizen[userAddress];
    }

    // 0
    function requestFingerprint(address userAddress) public returns (bytes32){
        fingerprint[userAddress] = cryptonomicaVerification.fingerprint(userAddress);
        return fingerprint[userAddress];
    }

    // 1
    function requestKeyCertificateValidUntil(address userAddress) public returns (uint){
        keyCertificateValidUntil[userAddress] = cryptonomicaVerification.keyCertificateValidUntil(userAddress);
        return keyCertificateValidUntil[userAddress];
    }

    // 2
    function requestUserData(address userAddress) public returns (bool) {
        bytes32 firstNameBytes32 = cryptonomicaVerification.firstName(userAddress);
        firstName[userAddress] = bytes32ToString(firstNameBytes32);

        bytes32 lastNameBytes32 = cryptonomicaVerification.lastName(userAddress);
        lastName[userAddress] = bytes32ToString(lastNameBytes32);

        birthDate[userAddress] = cryptonomicaVerification.birthDate(userAddress);
        // event:
        UserDataReceived(firstName[userAddress], lastName[userAddress], birthDate[userAddress]);
        return true;

    }

    event UserDataReceived(
        string userFirstName,
        string userLastName,
        uint userBirthDate
    );

}
