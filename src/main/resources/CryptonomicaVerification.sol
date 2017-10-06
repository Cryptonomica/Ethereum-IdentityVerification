pragma solidity ^0.4.17;


// developed by cryptonomica.net, 2017
// github: https://github.com/Cryptonomica/

contract CryptonomicaVerification {

    /* ---------------------- Verification Data */

    // Ethereum address is connected to OpenPGP public key data from Cryptonomica.net
    // Ethereum address can be connected to one OpenPGP key only, and one time only
    // If OpenPGP key expires user have to use another Ethereum address for new OpenPGP public key
    // But user can verify multiple Ethereum accounts with the same OpenPGP key

    // ---- mappings to store verification data, to make it accessible for other smart contracts
    // we store sting data as bytes32 (http://solidity.readthedocs.io/en/develop/types.html#fixed-size-byte-arrays)
    // !!! -> up to 32 ASCII letters,
    // see: https://ethereum.stackexchange.com/questions/6729/how-many-letters-can-bytes32-keep

    // fingerprints stored as upper case strings like:
    // 57A5FEE5A34D563B4B85ADF3CE369FD9E77173E5
    // fingerprint is 20 bytes
    // see: https://crypto.stackexchange.com/questions/32087/how-to-generate-fingerprint-for-pgp-public-key)
    // - 40 symbols string representation
    mapping (address => bytes20) public fingerprint; // ...........................................................0
    mapping (address => uint) public keyCertificateValidUntil; // unix time .......................................1
    mapping (address => bytes32) public firstName; // .............................................................2
    mapping (address => bytes32) public lastName; // ..............................................................3
    mapping (address => uint) public birthDate; // unix time ......................................................4
    // Nationality - from user passport or id document:
    // 2-letter country codes defined in ISO 3166
    // like returned by Locale.getISOCountries() in Java (upper case)
    // see: https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
    mapping (address => bytes2) public nationality; //      .......................................................5

    mapping (address => uint) public verificationAddedOn; // unix time ............................................6
    mapping (address => uint) public revokedOn; // unix time, returns uint256: 0 if verification is not revoked ...7

    /* user have to sign the hex string representation of bytes32
see: https://github.com/ethereum/wiki/wiki/JavaScript-API#web3tohex
https://github.com/web3j/web3j/issues/80#issuecomment-293722938
https://github.com/web3j/web3j/blob/master/src/main/java/org/web3j/utils/Numeric.java#L200 */
    mapping (address => bytes32) public stringToSignBytes32;

    mapping (address => string) public stringToSign; // string
    // this will be longer than 32 char
    mapping (address => string) public signedString; //

    /* the same data as above stored as a struct:
    struct will be returned as 'List' in web3j (only one function call needed) */
    mapping (address => Verification) public verification;

    struct Verification {
    string fingerprint; // ..................................................0
    uint keyCertificateValidUntil; // .......................................1
    string firstName; // ....................................................2
    string lastName;// ......................................................3
    uint birthDate; //  .....................................................4
    string nationality; //  .................................................5
    uint verificationAddedOn;// .............................................6
    uint revokedOn; // ......................................................7
    }

    /*  ---------------------------------- Administrative Data */

    address public owner; // smart contract owner (super admin)
    mapping (address => bool) public isManager; // list of managers

    uint public priceForVerificationInWei; //

    address public withdrawalAddress; // address to send Ether from this contract
    bool public withdrawalAddressFixed; // this can be smart contract with manages ETH from this SC

    /* ------------------------------------------- Constructor */
    function CryptonomicaVerification() public {
        owner = msg.sender;
        isManager[msg.sender] = true;
        withdrawalAddress = msg.sender;
        withdrawalAddressFixed = false;
    }

    /* -------------------- Utility functions : ---------------------- */

    // --- Strings to bytes

    function stringToBytes32(string memory source) internal pure returns (bytes32 result) {
        require(bytes(source).length <= 32);
        assembly {
        result := mload(add(source, 32))
        }
    }

    function stringToBytes20(string memory source) internal pure returns (bytes20 result) {
        require(bytes(source).length == 20);
        assembly {
        result := mload(add(source, 20))
        }
    }

    function stringToBytes2(string memory source) internal pure returns (bytes2 result) {
        require(bytes(source).length == 2);
        assembly {
        result := mload(add(source, 2))
        }
    }

    // to covert bytes32 back to string use:

    function bytes32ToString(bytes32 x) internal pure returns (string) {
        bytes memory bytesString = new bytes(32);
        uint charCount = 0;
        for (uint j = 0; j < 32; j++) {
            byte char = byte(bytes32(uint(x) * 2 ** (8 * j)));
            if (char != 0) {
                bytesString[charCount] = char;
                charCount++;
            }
        }
        bytes memory bytesStringTrimmed = new bytes(charCount);
        for (j = 0; j < charCount; j++) {
            bytesStringTrimmed[j] = bytesString[j];
        }
        return string(bytesStringTrimmed);
    }

    /* -------------------- Verification functions : ---------------------- */

    // get unique string for verification request:
    function requestStringToSignWithKey(string _fingerprint) public returns (string) {// from user acc, and not from another SC

        bytes32 strToSign = keccak256(// returns bytes32
        msg.sender,
        block.blockhash(block.number),
        block.timestamp,
        block.blockhash(block.number - 250)
        );

        fingerprint[msg.sender] = stringToBytes20(_fingerprint);
        // bytes20
        verification[msg.sender].fingerprint = _fingerprint;
        // string

        stringToSignBytes32[msg.sender] = strToSign;
        // bytes32
        stringToSign[msg.sender] = bytes32ToString(strToSign);
        // string

        // event:
        StringToSignRequested(msg.sender, _fingerprint, strToSign);

        // return strToSign;
        return stringToSign[msg.sender];

    }

    event StringToSignRequested(address forAccount, string forKeyFingerpint, bytes32 stringToSignProvided);

    // ---

    function uploadSignedString(string _signedString) public payable returns (bool) {// from user acc

        // check payment :
        if (msg.value != priceForVerificationInWei) {
            revert();
            // return payment if not right sum
        }
        // check if string to sign created (if not created it's empty)
        if (stringToSignBytes32[msg.sender] == bytes32(0)) {// ( stringToSign[msg.sender] - bytes32)
            revert();
            // (payable)
        }

        // if signed string already uploaded should throw
        // thus we check if signedString[msg.sender] is empty or not
        // see: https://ethereum.stackexchange.com/questions/11039/how-can-you-check-if-a-string-is-empty-in-solidity
        if (bytes(signedString[msg.sender]).length > 0) {
            revert();
            // (payable)
        }

        signedString[msg.sender] = _signedString;
        // string

        SignedStringUploaded(msg.sender, signedString[msg.sender]);

        return true;
    }

    event SignedStringUploaded(address fromAccount, string uploadedString);

    // ---

    function addVerificationData(// from 'manager' account only
    address acc,
    uint _keyCertificateValidUntil,
    string _firstName,
    string _lastName,
    uint _birthDate,
    string _nationality
    ) public returns (bool){

        require(isManager[msg.sender]);
        require(bytes(_firstName).length <= 32);
        require(bytes(_lastName).length <= 32);
        require(bytes(_nationality).length == 2);

        if (!isManager[msg.sender]) {
            return false;
        }

        // data added to verification[acc] and to the corresponding mappings
        verification[acc].keyCertificateValidUntil = keyCertificateValidUntil[acc] = _keyCertificateValidUntil;
        // uint

        verification[acc].firstName = _firstName;
        // string
        firstName[acc] = stringToBytes32(_firstName);
        // bytes32

        verification[acc].lastName = _lastName;
        // string
        lastName[acc] = stringToBytes32(_lastName);
        // bytes32

        verification[acc].birthDate = birthDate[acc] = _birthDate;
        // uint (UNIX time)

        verification[acc].nationality = _nationality;
        // string
        nationality[acc] = stringToBytes2(_nationality);
        // bytes2

        verification[acc].verificationAddedOn = verificationAddedOn[acc] = block.timestamp;
        // uint (UNIX time)

        VerificationAdded(
        verification[acc].fingerprint,
        // keyCertificateValidUntil[acc],
        // verification[acc].firstName,
        // verification[acc].lastName,
        // birthDate[acc],
        // verification[acc].nationality,
        stringToSign[acc],
        signedString[acc],
        verificationAddedOn[acc],
        msg.sender
        );

        return true;
    }

    event VerificationAdded (
    string forFingerprint,
    // uint keyCertificateValidUntilUnixTime,
    // string userFirstName,
    // string userLastName,
    // uint userBirthDate,
    // string userNationality,
    string stringToSignForUser,
    string signedStringUploaded,
    uint verificationAddedOnUnixTime,
    address verificationAddedByAccount
    );

    // ---

    function revoke(address acc, string _fingerprint) public returns (bool){// from user or 'manager' account

        require(msg.sender == acc || isManager[msg.sender]);
        require(bytes(_fingerprint).length == 20);
        require(keccak256(verification[acc].fingerprint) == keccak256(_fingerprint));
        // string

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

    event VerificationRevoked (address revocedforAccount, string withFingerprint, uint revokedOnUnixTime, address revokedBy);


    /* -------------------- Administrative functions : ---------------------- */

    function changeOwner(address newOwner) public returns (bool){// only owner

        require(msg.sender == owner);

        owner = newOwner;

        OwnerChanged(newOwner, msg.sender);
        return true;
    }

    event OwnerChanged(address to, address changedBy);

    // ---

    function addManager(address acc) public returns (bool){// only owner

        require(msg.sender == owner);

        isManager[acc] = true;
        ManagerAdded(acc, msg.sender);
        return true;
    }

    event ManagerAdded (address added, address addedBy);

    // ---

    function removeManager(address manager) public returns (bool){// only owner

        require(msg.sender == owner);

        isManager[manager] = false;
        ManagerRemoved(manager, msg.sender);
        return true;
    }

    event ManagerRemoved(address removed, address removedBy);

    // ---

    function setPriceForVerification(uint priceInWei) public returns (bool){// only owner

        require(msg.sender == owner);

        uint oldPrice = priceForVerificationInWei;

        priceForVerificationInWei = priceInWei;

        PriceChanged(oldPrice, priceForVerificationInWei, msg.sender);

        return true;
    }

    event PriceChanged(uint from, uint to, address changedBy);

    // ---

    function withdraw() public returns (bool){// ! can be called by any user;

        uint sum = this.balance;
        if (!withdrawalAddress.send(this.balance)) {// makes withdrawal and returns true or false
            return false;
        }
        Withdrawal(withdrawalAddress, sum, msg.sender);
        return true;
    }

    event Withdrawal(address to, uint sumInWei, address by);

    // ---

    function setWithdrawalAddress(address _withdrawalAddress) public returns (bool){// only owner

        require(msg.sender == owner);
        require(!withdrawalAddressFixed);

        address oldWithdrawalAddress = withdrawalAddress;
        withdrawalAddress = _withdrawalAddress;

        WithdrawalAddressChanged(oldWithdrawalAddress, withdrawalAddress, msg.sender);
        return true;
    }

    event WithdrawalAddressChanged(address from, address to, address changedBy);

    // ---

    function fixWithdrawalAddress(address _withdrawalAddress) public returns (bool){// only owner

        require(msg.sender == owner);

        require(withdrawalAddress == _withdrawalAddress);
        require(!withdrawalAddressFixed);

        withdrawalAddressFixed = true;
        WithdrawalAddressFixed(withdrawalAddress, msg.sender);
        return true;
    }

    event WithdrawalAddressFixed(address withdrawalAddressFixedAs, address fixedBy);

}
