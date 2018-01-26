pragma solidity ^0.4.15;


contract CryptonomicaAddressVerification {

    // ----- Constructor
    function CryptonomicaAddressVerification(){
    }

    // user postal address, should be maintained and can be changed separately from the key (struct Verification)
    mapping (address => Address) public userAddress;

    struct Address {
    // string country; //........... 1
    bytes2 country; //........... 1 // as above 2-letter country codes defined in ISO 3166
    bytes32 zipCode; //...........2
    bytes32 city; //..............3
    bytes32 addressLine1; //......4
    bytes32 addressLine2; //......5
    uint changedOn; // unix time..6
    }

    // ---

    function setAddr(// from 'manager' account only
    address acc,
    string _country,
    string _zipCode,
    string _city,
    string _addressLine1,
    string _addressLine2
    ) returns (bool){

        if (!isManager[msg.sender]) {
            return false;
        }

        addr[acc].country = _country;
        addr[acc].zipCode = _zipCode;
        addr[acc].city = _city;
        addr[acc].addressLine1 = _addressLine1;
        addr[acc].addressLine2 = _addressLine2;
        addr[acc].changedOn = block.timestamp;

        UserAddressChanged(
        acc,
        addr[acc].country,
        addr[acc].zipCode,
        addr[acc].city,
        addr[acc].addressLine1,
        addr[acc].addressLine2,
        addr[acc].changedOn
        );

        return true;
    }

    event UserAddressChanged(
    address account,
    string country,
    string zipCode,
    string city,
    string addressLine1,
    string addressLine2,
    uint changedOn
    );
}
