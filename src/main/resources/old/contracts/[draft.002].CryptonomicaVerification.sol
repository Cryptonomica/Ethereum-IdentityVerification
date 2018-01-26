pragma solidity ^0.4.15;


/*
developed by cryptonomica.net, 2017
last version: 2017-11-21
github: https://github.com/Cryptonomica/
*/

contract CryptonomicaVerification {

    /* ---------------------- Verification Data */

    // Ethereum address is connected to OpenPGP public key data from Cryptonomica.net
    // Ethereum address can be connected to one OpenPGP key only, and one time only
    // If OpenPGP key expires, user have to use another Ethereum address for new OpenPGP public key
    // But user can verify multiple Ethereum accounts with the same OpenPGP key

    // ---- mappings to store verification data, to make it accessible for other smart contracts
    // we store sting data as bytes32 (http://solidity.readthedocs.io/en/develop/types.html#fixed-size-byte-arrays)
    // !!! -> up to 32 ASCII letters,
    // see: https://ethereum.stackexchange.com/questions/6729/how-many-letters-can-bytes32-keep

    // OpenPGP Message Format https://tools.ietf.org/html/rfc4880#section-12.2 : "A V4 fingerprint is the 160-bit SHA-1 hash ..."
    // thus fingerprint is fingerprint is 20 bytes, in hexadecimal 40 symbols string representation.
    // fingerprints are stored as upper case strings like:
    // 57A5FEE5A34D563B4B85ADF3CE369FD9E77173E5
    // see: https://crypto.stackexchange.com/questions/32087/how-to-generate-fingerprint-for-pgp-public-key
    mapping (address => bytes32) public fingerprint; // ..............................................................0
    mapping (address => uint) public keyCertificateValidUntil; // unix time ..........................................1
    mapping (address => bytes32) public firstName; // ................................................................2
    mapping (address => bytes32) public lastName; // .................................................................3
    mapping (address => uint) public birthDate; // unix time .........................................................4
    // Nationality - from user passport or id document:
    // 2-letter country codes defined in ISO 3166
    // like returned by Locale.getISOCountries() in Java (upper case)
    // see: https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
    mapping (address => bytes32) public nationality; //      .........................................................5
    mapping (address => uint256) public verificationAddedOn; // unix time ............................................6
    mapping (address => uint256) public revokedOn; // unix time, returns uint256: 0 if verification is not revoked ...7

    /* user have to sign the string that shows the hex representation of bytes32 (like
    see: https://github.com/ethereum/wiki/wiki/JavaScript-API#web3tohex
    https://github.com/web3j/web3j/issues/80#issuecomment-293722938
    https://github.com/web3j/web3j/blob/master/src/main/java/org/web3j/utils/Numeric.java#L200 */
    mapping (address => bytes32) public stringToSignBytes32;
    //    mapping (address => string) public stringToSign; // string


    // unix time
    // (string to sign can be requested only one time for address
    // if not yet requested value is 0 )
    // unix time online converter: https://www.epochconverter.com
    // for coders: http://www.convert-unix-time.com
    mapping (address => uint256) public stringToSignRequestedOnUnixTime;

    // this will be longer than 32 char
    mapping (address => string) public signedString; //

    /* the same data as above stored as a struct:
    struct will be returned as 'List' in web3j (only one function call needed) */
    mapping (address => Verification) public verification;


    struct Verification {
    // all string have to be <= 32 chars
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

    uint public priceForVerificationInWei; // see converter on https://etherconverter.online/
    function priceForVerificationInEth() constant public returns (uint){
        return priceForVerificationInWei / (10 ** 18);
    }

    address public withdrawalAddress; // address to send Ether from this contract
    bool public withdrawalAddressFixed = false; // this can be smart contract with manages ETH from this SC

    /* ------------------------------------------- Constructor */
    function CryptonomicaVerification() {
        owner = msg.sender;
        isManager[msg.sender] = true;
        withdrawalAddress = msg.sender;
    }

    /* -------------------- Utility functions : ---------------------- */

    function stringToBytes32(string memory source) public constant returns (bytes32 result) {

        // require(bytes(source).length <= 32); // causes error
        // but string have to be max 32 chars

        // https://ethereum.stackexchange.com/questions/9603/understanding-mload-assembly-function
        // http://solidity.readthedocs.io/en/latest/assembly.html
        assembly {
        result := mload(add(source, 32))
        }
    }

    // https://ethereum.stackexchange.com/questions/2519/how-to-convert-a-bytes32-to-string
    // https://ethereum.stackexchange.com/questions/1081/how-to-concatenate-a-bytes32-array-to-a-string
    function bytes32ToString(bytes32 x) public constant returns (string) {
        // bytes: dynamically-sized byte array, not a value-type
        // see: http://solidity.readthedocs.io/en/v0.4.15/types.html#dynamically-sized-byte-array
        bytes memory bytesString = new bytes(32);
        uint charCount = 0;
        for (uint j = 0; j < 32; j++) {
            // byte is an alias for bytes1 (fixed-size byte array)
            // see: http://solidity.readthedocs.io/en/v0.4.15/types.html#fixed-size-byte-arrays
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
        string memory result = string(bytesStringTrimmed);
        return result;
    }

    function bytes32ToStr(bytes32 _bytes32) public constant returns (string){
        // string memory str = string(_bytes32);
        // TypeError: Explicit type conversion not allowed from "bytes32" to "string storage pointer"
        // thus we should convert bytes32 to bytes (to dynamically-sized byte array)
        bytes memory bytesArray = new bytes(32);
        for (uint256 i; i < 32; i++) {
            bytesArray[i] = _bytes32[i];
        }
        return string(bytesArray);
    }

    /* -------------------- Verification functions : ---------------------- */

    // from user acc:
    // get unique string for verification request
    // user have to sign a string representing bytes32,
    // i.e. something like:
    // "0x3fa1c7685df6c89b5065bd8fd4d439492d4d3f451f15b8c14beed64689480e08"
    function requestStringToSignWithKey(string _fingerprint) public returns (bytes32) {

        // (!) string can be requested only one time for account
        // if never requested, stringToSignRequestedOnUnixTime[msg.sender] value should be 0 (zero)
        require(stringToSignRequestedOnUnixTime[msg.sender] == 0);
        // store information about this request:
        stringToSignRequestedOnUnixTime[msg.sender] = block.timestamp;

        // fingerprint should be uppercase 40 symbols
        require(bytes(_fingerprint).length == 40);

        stringToSignBytes32[msg.sender] = keccak256(// returns bytes32
        msg.sender,
        block.blockhash(block.number - 7),
        block.blockhash(block.number - 121),
        // http://solidity.readthedocs.io/en/v0.4.15/frequently-asked-questions.html#are-timestamps-now-block-timestamp-reliable
        // block.timestamp,
        block.blockhash(block.number - 250)
        );

        // bytes32
        fingerprint[msg.sender] = stringToBytes32(_fingerprint);
        // string
        verification[msg.sender].fingerprint = _fingerprint;

        // event:
        StringToSignRequested(msg.sender, _fingerprint, stringToSignBytes32[msg.sender]);

        // bytes32
        return stringToSignBytes32[msg.sender];

    }

    event StringToSignRequested(
    address indexed forAccount,
    string indexed forKeyFingerpint,
    bytes32 indexed stringToSignProvided
    );

    // ---
    // from user acc
    function uploadSignedString(string _signedString) public payable returns (bool) {

        // check length of the uploaded string,
        // it expected to be a 64 chars string signed with OpenPGP standard signature
        // bytes: Dynamically-sized byte array,
        // see: http://solidity.readthedocs.io/en/develop/types.html#dynamically-sized-byte-array
        if (bytes(_signedString).length > 1000) {//
            revert();
            // (payable)
        }

        // check payment :
        if (msg.value != priceForVerificationInWei) {
            revert();
            // (payable)
        }

        // check if string to sign created (if not created it's empty)
        if (stringToSignBytes32[msg.sender] == bytes32(0)) {// ( stringToSign[msg.sender] - bytes32)
            revert();
            // (payable)
        }

        // if signed string already uploaded should revert
        // we check if signedString[msg.sender] is empty or not
        // see: https://ethereum.stackexchange.com/q/11039/1964
        if (bytes(signedString[msg.sender]).length > 0) {
            revert();
            // (payable)
        }

        signedString[msg.sender] = _signedString;
        // string

        SignedStringUploaded(msg.sender, signedString[msg.sender]);

        return true;
    }

    event SignedStringUploaded(
    address indexed fromAccount,
    string uploadedString
    );

    // ---
    // from 'manager' account only
    function addVerificationData(
    address acc,
    uint _keyCertificateValidUntil,
    string _firstName,
    string _lastName,
    uint _birthDate,
    string _nationality
    ) public returns (bool) {

        require(isManager[msg.sender]);
        require(bytes(_firstName).length <= 32);
        require(bytes(_lastName).length <= 32);
        require(bytes(_nationality).length == 2);
        // like "IL" or "US"

        if (!isManager[msg.sender]) {
            return false;
        }

        // data added to verification[acc] and to the corresponding mappings
        // uint
        verification[acc].keyCertificateValidUntil = keyCertificateValidUntil[acc] = _keyCertificateValidUntil;

        // string
        verification[acc].firstName = _firstName;
        // bytes32
        firstName[acc] = stringToBytes32(_firstName);

        // string
        verification[acc].lastName = _lastName;
        // bytes32
        lastName[acc] = stringToBytes32(_lastName);

        // uint (UNIX time)
        verification[acc].birthDate = birthDate[acc] = _birthDate;

        // string
        verification[acc].nationality = _nationality;
        // bytes32
        nationality[acc] = stringToBytes32(_nationality);

        // uint (UNIX time)
        verification[acc].verificationAddedOn = verificationAddedOn[acc] = block.timestamp;


        VerificationAdded(
        verification[acc].fingerprint,
        acc,
        keyCertificateValidUntil[acc],
        verification[acc].firstName,
        verification[acc].lastName,
        birthDate[acc],
        verification[acc].nationality,
        // stringToSignBytes32[acc],
        // signedString[acc],
        // verificationAddedOn[acc],
        msg.sender
        );

        return true;
    }

    event VerificationAdded (
    string indexed forFingerprint, // (1) indexed
    address indexed verifiedAccount, // (2) indexed
    uint keyCertificateValidUntilUnixTime,
    string userFirstName,
    string userLastName,
    uint userBirthDate,
    string userNationality, // (3) indexed
    // bytes32 stringToSignForUser,
    // string signedStringUploaded,
    // uint verificationAddedOnUnixTime,
    address verificationAddedByAccount
    );

    // ---
    // from user or 'manager' account
    function revoke(address acc, string _fingerprint) public returns (bool) {

        require(msg.sender == acc || isManager[msg.sender]);
        require(bytes(_fingerprint).length == 20);

        // string
        require(keccak256(verification[acc].fingerprint) == keccak256(_fingerprint));

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

    event VerificationRevoked (
    address indexed revocedforAccount,
    string indexed withFingerprint,
    uint revokedOnUnixTime,
    address indexed revokedBy
    );


    /* -------------------- Administrative functions : ---------------------- */

    // to avoid mistakes owner (super admin) should be changed in two steps
    // change is valid when accepted from new owner address
    address private newOwner;

    // only owner
    function changeOwnerStart(address _newOwner) public returns (bool) {
        require(msg.sender == owner);

        newOwner = _newOwner;

        return true;
    }

    // only owner
    function changeOwnerAccept() public returns (bool) {
        require(msg.sender == newOwner);

        owner = newOwner;

        OwnerChanged(newOwner);

        return true;
    }

    event OwnerChanged(
    address indexed to
    );

    // ---
    // only owner
    function addManager(address acc) public returns (bool) {

        require(msg.sender == owner);

        isManager[acc] = true;
        ManagerAdded(acc, msg.sender);
        return true;
    }

    event ManagerAdded (
    address indexed added,
    address indexed addedBy
    );

    // ---
    // only owner
    function removeManager(address manager) public returns (bool) {

        require(msg.sender == owner);

        isManager[manager] = false;
        ManagerRemoved(manager, msg.sender);
        return true;
    }

    event ManagerRemoved(
    address indexed removed,
    address indexed removedBy
    );

    // ---

    function setPriceForVerification(uint priceInWei) public returns (bool) {
        // see converter on https://etherconverter.online/

        // only owner
        require(isManager[msg.sender]);

        uint oldPrice = priceForVerificationInWei;

        priceForVerificationInWei = priceInWei;

        PriceChanged(oldPrice, priceForVerificationInWei, msg.sender);

        return true;
    }

    event PriceChanged(
    uint from,
    uint to,
    address indexed changedBy
    );

    // ---
    // ! can be called by any user
    function withdrawAllToWithdrawalAddress() public returns (bool) {
        // about <address>.send(uint256 amount) and <address>.transfer(uint256 amount)
        // see: http://solidity.readthedocs.io/en/latest/units-and-global-variables.html?highlight=transfer#address-related
        // https://ethereum.stackexchange.com/questions/19341/address-send-vs-address-transfer-best-practice-usage
        uint sum = this.balance;
        if (!withdrawalAddress.send(this.balance)) {// makes withdrawal and returns true or false
            Withdrawal(withdrawalAddress, sum, msg.sender, false);
            return false;
        }
        Withdrawal(withdrawalAddress, sum, msg.sender, true);
        return true;
    }

    event Withdrawal(
    address indexed to,
    uint sumInWei,
    address indexed by,
    bool success
    );

    // ---
    // only owner
    function setWithdrawalAddress(address _withdrawalAddress) public returns (bool) {

        require(msg.sender == owner);

        require(!withdrawalAddressFixed);

        address oldWithdrawalAddress = withdrawalAddress;
        withdrawalAddress = _withdrawalAddress;

        WithdrawalAddressChanged(oldWithdrawalAddress, withdrawalAddress, msg.sender);
        return true;
    }

    event WithdrawalAddressChanged(
    address indexed from,
    address indexed to,
    address indexed changedBy
    );

    // ---
    // only owner
    function fixWithdrawalAddress(address _withdrawalAddress) public returns (bool) {

        require(msg.sender == owner);

        require(withdrawalAddress == _withdrawalAddress);
        require(!withdrawalAddressFixed);

        withdrawalAddressFixed = true;
        WithdrawalAddressFixed(withdrawalAddress, msg.sender);

        return true;
    }

    event WithdrawalAddressFixed(// only one time
    address withdrawalAddressFixedAs,
    address fixedBy
    );

}
