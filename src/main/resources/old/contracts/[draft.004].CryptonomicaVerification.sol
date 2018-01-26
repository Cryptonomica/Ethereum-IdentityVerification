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

    mapping (address => string) public stringToSign; // string
    // (string to sign can be requested only one time for address
    // if not yet requested value is 0 )
    // unix time online converter: https://www.epochconverter.com
    // for coders: http://www.convert-unix-time.com
    mapping (address => uint256) public stringToSignRequestedOnUnixTime; //
    mapping (address => uint256) public signedStringUploadedOnUnixTime; //
    // this will be longer than 32 char, and have to be properly formatted (with "\n")
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

    //    function priceForVerificationInEth() constant public returns (uint){
    //        return priceForVerificationInWei / (10 ** 18);
    //    }

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

    // see also:
    // https://ethereum.stackexchange.com/questions/2519/how-to-convert-a-bytes32-to-string
    // https://ethereum.stackexchange.com/questions/1081/how-to-concatenate-a-bytes32-array-to-a-string
    function bytes32ToString(bytes32 _bytes32) public constant returns (string){
        // string memory str = string(_bytes32);
        // TypeError: Explicit type conversion not allowed from "bytes32" to "string storage pointer"
        // thus we should convert bytes32 to bytes (to dynamically-sized byte array)
        bytes memory bytesArray = new bytes(32);
        for (uint256 i; i < 32; i++) {
            bytesArray[i] = _bytes32[i];
        }
        return string(bytesArray);
    }

    /* ethereum address to string */
    // https://ethereum.stackexchange.com/questions/8346/convert-address-to-string
    // https://ethereum.stackexchange.com/a/8447/1964
    function addressToAsciiString(address _address) private constant returns (string) {
        bytes memory s = new bytes(40);
        for (uint i = 0; i < 20; i++) {
            byte b = byte(uint8(uint(_address) / (2 ** (8 * (19 - i)))));
            byte hi = byte(uint8(b) / 16);
            byte lo = byte(uint8(b) - 16 * uint8(hi));
            s[2 * i] = char(hi);
            s[2 * i + 1] = char(lo);
        }
        // without "0x"
        return string(s);
    } //
    function char(byte b) private constant returns (byte c) {
        if (b < 10) return byte(uint8(b) + 0x30);
        else return byte(uint8(b) + 0x57);
    }

    function addressToString(address _address) public constant returns (string) {
        return stringsConcatenation("0x", addressToAsciiString(_address));
    }

    function messageSenderAddressToString() public constant returns (string){
        return addressToString(msg.sender);
    }

    /* --------------------- strings functions begin        */
    /* --- https://github.com/Arachnid/solidity-stringutils */

    struct slice {uint _len; uint _ptr;}

    function memcpy(uint dest, uint src, uint len) private {
        for (; len >= 32; len -= 32) {
            assembly {
            mstore(dest, mload(src))
            }
            dest += 32;
            src += 32;
        }
        uint mask = 256 ** (32 - len) - 1;
        assembly {
        let srcpart := and(mload(src), not(mask))
        let destpart := and(mload(dest), mask)
        mstore(dest, or(destpart, srcpart))
        }
    }

    /*
     * @dev Returns a slice containing the entire string.
     * @param self The string to make a slice from.
     * @return A newly allocated slice containing the entire string.
     */
    function toSlice(string self) internal returns (slice) {
        uint ptr;
        assembly {
        ptr := add(self, 0x20)
        }
        return slice(bytes(self).length, ptr);
    }

    /*
     * @dev Returns a newly allocated string containing the concatenation of `self` and `other`.
     * @param self The first slice to concatenate.
     * @param other The second slice to concatenate.
     * @return The concatenation of the two strings.
     */
    function concat(slice self, slice other) internal returns (string) {
        var ret = new string(self._len + other._len);
        uint retptr;
        assembly {retptr := add(ret, 32)}
        memcpy(retptr, self._ptr, self._len);
        memcpy(retptr + self._len, other._ptr, other._len);
        return ret;
    }

    /*
     * @dev Joins an array of slices, using `self` as a delimiter, returning a newly allocated string.
     * @param self The delimiter to use.
     * @param parts A list of slices to join.
     * @return A newly allocated string containing all the slices in `parts`, joined with `self`.
     */
    function join(slice self, slice[] parts) internal returns (string) {
        if (parts.length == 0)
        return "";
        uint length = self._len * (parts.length - 1);
        for (uint i = 0; i < parts.length; i++)
        length += parts[i]._len;
        var ret = new string(length);
        uint retptr;
        assembly {retptr := add(ret, 32)}
        for (i = 0; i < parts.length; i++) {
            memcpy(retptr, parts[i]._ptr, parts[i]._len);
            retptr += parts[i]._len;
            if (i < parts.length - 1) {
                memcpy(retptr, self._ptr, self._len);
                retptr += self._len;
            }
        }
        return ret;
    }
    /* --------------------- strings -- end   */

    function stringsConcatenation(string str1, string str2) public constant returns (string) {
        return concat(toSlice(str1), toSlice(str2));
    } //

    function stringsJoin(string str1, string str2, string str3) public constant returns (string) {
        slice memory delimiter = toSlice(" ");
        // see: http://solidity.readthedocs.io/en/v0.4.15/types.html#arrays
        // http://solidity.readthedocs.io/en/v0.4.15/types.html#allocating-memory-arrays
        slice[] memory slicesArray = new slice[](3);
        slicesArray[0] = toSlice(str1);
        slicesArray[1] = toSlice(str2);
        slicesArray[2] = toSlice(str3);
        string memory result = join(delimiter, slicesArray);
        return result;
    } //

    /* -------------------- Verification functions : ---------------------- */

    // from user acc:
    // get unique string for verification request
    // user have to sign a string representing bytes32,
    // i.e. something like:
    // "0x3fa1c7685df6c89b5065bd8fd4d439492d4d3f451f15b8c14beed64689480e08"
    function requestStringToSignWithKey(string _fingerprint) public returns (string) {

        // (!) string can be requested only one time for account
        // if never requested, stringToSignRequestedOnUnixTime[msg.sender] value should be 0 (zero)
        require(stringToSignRequestedOnUnixTime[msg.sender] == 0);
        // store information about this request:
        stringToSignRequestedOnUnixTime[msg.sender] = block.timestamp;

        // fingerprint should be uppercase 40 symbols
        require(bytes(_fingerprint).length == 40);

        string memory str1 = "I hereby confirm that the address";
        string memory str2 = messageSenderAddressToString();
        string memory str3 = "is my Ethereum address";

        stringToSign[msg.sender] = stringsJoin(str1, str2, str3);

        // bytes32
        fingerprint[msg.sender] = stringToBytes32(_fingerprint);
        // string
        verification[msg.sender].fingerprint = _fingerprint;

        // event:
        StringToSignRequested(msg.sender, _fingerprint, stringToSign[msg.sender]);

        return stringToSign[msg.sender];
    }

    event StringToSignRequested(address indexed forAccount, string indexed forKeyFingerpint, string stringToSignProvided);

    // ---
    // from user acc
    function uploadSignedString(string _signedString) public payable returns (bool) {

        // check length of the uploaded string,
        // it expected to be a 64 chars string signed with OpenPGP standard signature
        // bytes: Dynamically-sized byte array,
        // see: http://solidity.readthedocs.io/en/develop/types.html#dynamically-sized-byte-array
        // if (bytes(_signedString).length > 1000) {//
        //    revert();
        //    // (payable)
        // }
        // --- not needed: if string is to big -> out of gas

        // check payment :
        if (msg.value < priceForVerificationInWei) {
            revert();
            // (payable)
        }

        // if never requested, stringToSignRequestedOnUnixTime[msg.sender] value should be 0 (zero)
        if (stringToSignRequestedOnUnixTime[msg.sender] == 0) {
            revert();
            // (payable)
        }

        // if signed string already uploaded, should revert
        if (signedStringUploadedOnUnixTime[msg.sender] != 0) {
            revert();
            // (payable)
        }

        // string
        signedString[msg.sender] = _signedString;
        // uint256 - Unix Time
        signedStringUploadedOnUnixTime[msg.sender] = block.timestamp;

        SignedStringUploaded(msg.sender, signedString[msg.sender]);

        return true;
    }

    event SignedStringUploaded(address indexed fromAccount, string uploadedString);

    // ---
    // from 'manager' account only
    function addVerificationData(
    address acc, //
    uint _keyCertificateValidUntil, //
    string _firstName, //
    string _lastName, //
    uint _birthDate, //
    string _nationality) public returns (bool) {

        // (!!!) only manager can add verification data
        require(isManager[msg.sender]);

        // check input
        // (we can check this also on front end)
        require(bytes(_firstName).length <= 32);
        require(bytes(_lastName).length <= 32);
        require(bytes(_nationality).length == 2);
        // like "IL" or "US"

        // check if signed string uploaded
        require(signedStringUploadedOnUnixTime[acc] != 0);
        // to make possible adding verification only one time:
        require(verificationAddedOn[acc] == 0);

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
    address verificationAddedByAccount
    );

    // ---
    // from user or 'manager' account
    function revokeVerification(address acc, string _fingerprint) public returns (bool) {

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
    } //
    // only by new owner
    function changeOwnerAccept() public returns (bool) {
        require(msg.sender == newOwner);
        OwnerChanged(owner, newOwner);
        owner = newOwner;
        return true;
    } //
    event OwnerChanged(address indexed from, address indexed to);

    // ---
    // only owner
    function addManager(address acc) public returns (bool) {
        require(msg.sender == owner);
        isManager[acc] = true;
        ManagerAdded(acc, msg.sender);
        return true;
    }

    event ManagerAdded (address indexed added, address indexed addedBy);

    // ---
    // only owner
    function removeManager(address manager) public returns (bool) {
        require(msg.sender == owner);
        isManager[manager] = false;
        ManagerRemoved(manager, msg.sender);
        return true;
    }

    event ManagerRemoved(address indexed removed, address indexed removedBy);

    // ---

    function setPriceForVerification(uint priceInWei) public returns (bool) {
        // see converter on https://etherconverter.online

        // only by manager
        require(isManager[msg.sender]);
        uint oldPrice = priceForVerificationInWei;
        priceForVerificationInWei = priceInWei;
        PriceChanged(oldPrice, priceForVerificationInWei, msg.sender);
        return true;
    }

    event PriceChanged(uint from, uint to, address indexed changedBy);

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

    event Withdrawal(address indexed to, uint sumInWei, address indexed by, bool success);

    // only owner
    function setWithdrawalAddress(address _withdrawalAddress) public returns (bool) {

        require(msg.sender == owner);
        require(!withdrawalAddressFixed);

        WithdrawalAddressChanged(withdrawalAddress, _withdrawalAddress, msg.sender);

        withdrawalAddress = _withdrawalAddress;

        return true;
    }

    event WithdrawalAddressChanged(address indexed from, address indexed to, address indexed changedBy);

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

    // one time only
    event WithdrawalAddressFixed(address withdrawalAddressFixedAs, address fixedBy);

}
