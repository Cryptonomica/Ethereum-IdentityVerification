pragma solidity ^0.4.11;


// developed by cryptonomica.net
// 2017
// github: https://github.com/Cryptonomica/ 

contract CryptonomicaVerification {

    /* Verification Data */
    // Ethereum address is connected to OpenPGP public key data from Cryptonomica.net
    // Ethereum address can be connected to one OpenPGP key only, and one time only
    // If OpenPGP key expires user have to use another Ethereum address for new OpenPGP public key
    // fingerprints stored as upper case strings like 57A5FEE5A34D563B4B85ADF3CE369FD9E77173E5 */
    mapping (address => string) public fingerprint; // ............................................................0
    mapping (address => uint) public keyCertificateValidUntil; // unix time .......................................1
    mapping (address => string) public name; // first name + last name ............................................2
    mapping (address => uint) public birthDate; // unix time ......................................................3
    // Nationality - from user passport or id document:
    // 2-letter country codes defined in ISO 3166
    // like returned by Locale.getISOCountries() in Java
    // see: https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
    mapping (address => string) public nationality; //      .......................................................4
    /* user have to sign the hex string representation of bytes32
    see: https://github.com/ethereum/wiki/wiki/JavaScript-API#web3tohex
    https://github.com/web3j/web3j/issues/80#issuecomment-293722938
    https://github.com/web3j/web3j/blob/master/src/main/java/org/web3j/utils/Numeric.java#L200 */
    mapping (address => bytes32) public stringToSign; // ..........................................................5
    mapping (address => string) public signedString; //............................................................6
    mapping (address => uint) public verificationAddedOn; // unix time ............................................7
    mapping (address => uint) public revokedOn; // unix time, returns uint256: 0 if verification is not revoked ...8

    /* struct will be returned as 'List' in web3j (one function call) */
    mapping (address => Verification) public verification;

    struct Verification {
    string fingerprint; // ..................................................0
    uint keyCertificateValidUntil; // .......................................1
    string name; // .........................................................2
    uint birthDate; //  .....................................................3
    string nationality; //  .................................................4
    bytes32 stringToSign; //.................................................5
    string signedString; //..................................................6
    uint verificationAddedOn;// .............................................7
    uint revokedOn; // ......................................................8
    }

    // user postal address, should be maintained and can be changed separately from the key (struct struct Verification)
    mapping (address => Address) public addr;

    struct Address {
    string country; //........... 1
    string zipCode; //............2
    string city; //...............3
    string addressLine1; //.......4
    string addressLine2; //.......5
    uint changedOn; // unix time..6
    }

    /* Administrative Data */
    address public owner; //
    mapping (address => bool) public isManager; //
    uint public priceForVerificationInWei; //
    address public withdrawalAddress; // address to send Ether from this contract
    bool public withdrawalAddressFixed;

    /* Constructor*/
    function CryptonomicaVerification(){
        owner = msg.sender;
        isManager[msg.sender] = true;
        withdrawalAddressFixed = false;
    }

    /* -------------------- Utility functions : ---------------------- */

    function stringsAreEqual(string str1, string str2) private returns (bool){
        // see:
        // https://ethereum.stackexchange.com/questions/4559/operator-not-compatible-with-type-string-storage-ref-and-literal-string
        if (sha3(str1) == sha3(str2)) {
            return true;
        }
        else {
            return false;
        }
    }

    function isEmptyString(string _str) private returns (bool){
        // see:
        // https://ethereum.stackexchange.com/questions/11039/how-can-you-check-if-a-string-is-empty-in-solidity
        bytes memory str = bytes(_str);
        if (str.length == 0) {
            // is an empty string
            return true;
        }
        else {
            // is not an empty string
            return false;
        }
    }

    function notEmptyString(string _str) private returns (bool){
        // see:
        // https://ethereum.stackexchange.com/questions/11039/how-can-you-check-if-a-string-is-empty-in-solidity
        bytes memory str = bytes(_str);
        if (str.length == 0) {
            // is an empty string
            return false;
        }
        else {
            // is not an empty string
            return true;
        }
    }

    /* -------------------- Verification functions : ---------------------- */

    // get unique string for verification request:
    function getStringToSignWithKey(string _fingerprint) returns (bytes32) {// from user acc

        bytes32 strToSign = sha3(// alias to keccak256(), returns (bytes32)
        msg.sender,
        block.blockhash(block.number),
        block.timestamp,
        block.blockhash(block.number - 250)
        );

        fingerprint[msg.sender] = _fingerprint;
        verification[msg.sender].fingerprint = _fingerprint;
        //
        stringToSign[msg.sender] = strToSign;
        verification[msg.sender].stringToSign = strToSign;

        // event:
        StringToSignRequested(msg.sender, _fingerprint, strToSign);

        return strToSign;
    }

    event StringToSignRequested(address forAccount, string forKeyFingerpint, bytes32 stringToSign);

    // ---

    function uploadSignedString(string _signedString) payable returns (string) {// from user acc

        // check if payment for verification provided:
        if (msg.value < priceForVerificationInWei) {
            throw;
        }
        // check if string to sign created
        if (stringToSign[msg.sender] == 0x0000000000000000000000000000000000000000000000000000000000000000) {
            throw;
            // (payable)
        }
        // if signed string already uploaded return :
        if (notEmptyString(signedString[msg.sender])) {
            throw;
            // (payable)
        }

        verification[msg.sender].signedString = _signedString;
        signedString[msg.sender] = _signedString;

        SignedStringUploaded(msg.sender, signedString[msg.sender]);

        return _signedString;
    }

    event SignedStringUploaded(address fromAccount, string signedString);

    // ---

    function verify(// from 'manager' account only
    address acc,
    uint _keyCertificateValidUntil,
    string _name,
    uint _birthDate,
    string _nationality
    ) returns (bool){

        if (!isManager[msg.sender]) {
            return false;
        }

        verification[acc].keyCertificateValidUntil = keyCertificateValidUntil[acc] = _keyCertificateValidUntil;
        verification[acc].name = name[acc] = _name;
        verification[acc].birthDate = birthDate[acc] = _birthDate;
        verification[acc].nationality = nationality[acc] = _nationality;
        verification[acc].verificationAddedOn = verificationAddedOn[acc] = block.timestamp;

        VerificationAdded(
        fingerprint[acc],
        keyCertificateValidUntil[acc],
        name[acc],
        birthDate[acc],
        nationality[acc],
        stringToSign[acc],
        signedString[acc],
        verificationAddedOn[acc],
        msg.sender
        );

        return true;
    }

    event VerificationAdded (
    string fingerprint,
    uint keyCertificateValidUntil,
    string name,
    uint birthDate,
    string nationality,
    bytes32 stringToSign,
    string signedString,
    uint verificationAddedOn,
    address verificationAddedBy
    );

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

    // ---

    function revoke(address acc, string _fingerprint) returns (bool){// from user or 'manager' account

        if (msg.sender != acc || !isManager[msg.sender]) {
            throw;
        }
        if (!stringsAreEqual(fingerprint[acc], _fingerprint)) {
            throw;
        }

        verification[acc].revokedOn = revokedOn[acc] = block.timestamp;

        // event
        VerificationRevoked(
        acc,
        _fingerprint,
        block.timestamp,
        msg.sender
        );

        return true;
    }

    event VerificationRevoked (address forAccount, string fingerprint, uint revokedOn, address revokedBy);

    /* -------------------- Administrative functions : ---------------------- */

    function changeOwner(address newOwner) returns (bool){// only owner

        if (msg.sender != owner) {
            return false;
        }
        owner = newOwner;
        OwnerChanged(msg.sender, newOwner, msg.sender);
        return true;
    }

    event OwnerChanged(address from, address to, address changedBy);

    // ---

    function addManager(address acc) returns (bool){// only owner

        if (msg.sender != owner) {
            return false;
        }
        isManager[acc] = true;
        ManagerAdded(acc, msg.sender);
        return true;
    }

    event ManagerAdded (address added, address addedBy);

    // ---

    function removeManager(address manager) returns (bool){// only owner

        if (msg.sender != owner) {
            return false;
        }
        isManager[manager] = false;
        ManagerRemoved(manager, msg.sender);
        return true;
    }

    event ManagerRemoved(address removed, address removedBy);

    // ---

    function setPriceForVerification(uint priceInWei) returns (bool){// only owner

        if (msg.sender != owner) {
            return false;
        }
        uint oldPrice = priceForVerificationInWei;
        priceForVerificationInWei = priceInWei;
        PriceChanged(oldPrice, priceForVerificationInWei, msg.sender);
        return true;
    }

    event PriceChanged(uint from, uint to, address changedBy);

    // ---

    function withdraw() returns (bool){// ! can be called by any user;

        uint sum = this.balance;
        if (!withdrawalAddress.send(this.balance)) {// makes withdrawal and returns true or false
            return false;
        }
        Withdrawal(withdrawalAddress, sum, msg.sender);
        return true;
    }

    event Withdrawal(address to, uint sumInWei, address by);

    // ---

    function setWithdrawalAddress(address _withdrawalAddress) returns (bool){// only owner

        if (msg.sender != owner) {
            return false;
        }
        if (withdrawalAddressFixed) {
            return false;
        }
        address oldWithdrawalAddress = withdrawalAddress;
        withdrawalAddress = _withdrawalAddress;
        WithdrawalAddressChanged(oldWithdrawalAddress, withdrawalAddress, msg.sender);

        return true;
    }

    event WithdrawalAddressChanged(address from, address to, address changedBy);

    // ---

    function fixWithdrawalAddress(address _withdrawalAddress) returns (bool){// only owner
        if (msg.sender != owner) {
            return false;
        }
        // to prevent errors:
        if (withdrawalAddress != _withdrawalAddress) {
            return false;
        }
        if (withdrawalAddressFixed) {
            return false;
        }

        withdrawalAddressFixed = true;
        WithdrawalAddressFixed(withdrawalAddress, msg.sender);
        return true;
    }

    event WithdrawalAddressFixed(address withdrawalAddress, address fixedBy);

    // ---

}
