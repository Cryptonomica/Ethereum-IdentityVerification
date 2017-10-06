pragma solidity ^0.4.15;


contract CryptonomicaVerify {

    /* ------------------ Smart Contract variables */
    // verifications: (acc => (verificationId => verificationData)
    // this stores verifications (represented by Verification struct) associated with Ethereum addresses
    mapping (address => mapping (uint => Verification)) public verification;

    // counter for verifications for acc
    mapping (address => uint) public numberOfVerifications; // this number is verification Id

    // ---------
    mapping (address => bool) public isManager;

    uint public priceForVerificationInWei;

    address public withdrawalAddress; // address to send Ether from this contract
    bool public withdrawalAddressFixed;

    address public owner;


    /* --------------------------- Constructor: */
    function CryptonomicaVerify(){
        owner = msg.sender;
        isManager[msg.sender] = true;
        withdrawalAddressFixed = false;
    }

    /* ------------------- Verification struct: */
    // this is created when user requests string to sign
    struct Verification {
    bytes32 stringToSign; //------------------------------------------------- 0
    string signedString;  //--------------------------------------------------1
    bool signedStringUploaded; // --------------------------------------------2
    // information that have to be added from the cryptonomica server:
    // string signedStringFromServer;
    string name; // first name + last name // --------------------------------3
    string keyFingerprint; // ------------------------------------------------4
    uint keyValidUntil; // unix time // --------------------------------------5
    uint birthDateYear; // ---------------------------------------------------6
    uint birthDateMonth; // --------------------------------------------------7
    uint birthDateDay; // ----------------------------------------------------8
    bool revoked; // ---------------------------------------------------------9
    uint revokedOn; // unix time // -----------------------------------------10
    }

    /* --------------------------- Utility functions */


    function stringToBytes32(string memory source) returns (bytes32 result) {
        // we use this to serve bytes32 instead of string to other smart contracts
        // see:
        // https://ethereum.stackexchange.com/questions/1081/how-to-concatenate-a-bytes32-array-to-a-string
        // https://ethereum.stackexchange.com/questions/2519/how-to-convert-a-bytes32-to-string
        assembly {
        result := mload(add(source, 32))
        }
    }

    // to covert bytes32 back to string use:
    function bytes32ToString(bytes32 x) constant returns (string) {
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

    /* -------------------------- Functions */


    // get unique string for verification request:
    function getStringToSignWithKey(string keyFingerprint) returns (bytes32) {
        bytes32 strToSign = sha3(// alias to keccak256(), returns (bytes32)
        msg.sender,
        block.blockhash(block.number),
        block.timestamp,
        block.blockhash(block.number - 250)
        );

        uint verificationId = numberOfVerifications[msg.sender]++;

        verification[msg.sender][verificationId].stringToSign = strToSign;
        verification[msg.sender][verificationId].keyFingerprint = keyFingerprint;
        // event:
        StringToSignRequested(msg.sender, keyFingerprint, verificationId, strToSign);
        return strToSign;
    }

    event StringToSignRequested(address forAccount, string forKeyFingerpint, uint AccountVerificationId, bytes32 stringToSign);

    // -------------<<<<<<<<<<<<<<<<<<<
    function uploadSignedString(uint verificationId, string signedString) payable returns (bool) {

        if (msg.value < priceForVerificationInWei) {
            throw;
        }

        if (verification[msg.sender][verificationId].signedStringUploaded) {
            throw;
        }

        verification[msg.sender][verificationId].signedString = signedString;
        verification[msg.sender][verificationId].signedStringUploaded = true;
        SignedStringUploaded(msg.sender, verificationId, true, "success", signedString);

        return true;
    }
    // event
    event SignedStringUploaded(address fromAccount, uint verificationId, bool result, string message, string signedString);

    // -----------
    function verify(// this can be called from cryptonomica account only
    address acc,
    uint verificationId,
    string name,
    uint keyValidUntil, // unix time
    uint birthDateYear,
    uint birthDateMonth,
    uint birthDateDay,
    string keyFingerprint
    ) returns (bool){

        if (!isManager[msg.sender]) {
            return false;
        }

        // bytes32 stringToSign; //
        // string signedString; //
        // string keyFingerprint;
        // bool signedStringUploaded; //

        // information that have to be added from the cryptonomica server:
        verification[acc][verificationId].name = name;
        // first name + last name
        verification[acc][verificationId].keyValidUntil = keyValidUntil;
        verification[acc][verificationId].birthDateYear = birthDateYear;
        verification[acc][verificationId].birthDateMonth = birthDateMonth;
        verification[acc][verificationId].birthDateDay = birthDateDay;

        VerificationAdded(
        acc,
        name,
        birthDateYear,
        birthDateMonth,
        birthDateDay,
        keyFingerprint,
        keyValidUntil // unix time
        );
        return true;
    }

    event VerificationAdded (
    address verifiedAccount,
    string name,
    uint birthDateYear,
    uint birthDateMonth,
    uint birthDateDay,
    string keyFingerprint,
    uint keyValidUntil // unix time
    );

    // this can be called by user only:
    function revoke(address acc, uint verificationId) returns (bool){
        if (msg.sender != acc || !isManager[msg.sender]) {
            return false;
        }
        verification[msg.sender][verificationId].revoked = true;
        // event:
        verificationRevoked(
        msg.sender,
        block.timestamp, // current block timestamp as seconds since unix epoch
        verificationId,
        verification[msg.sender][verificationId].keyFingerprint
        );
        return true;
    }

    event verificationRevoked(
    address verifiedAccount,
    uint revokedOn,
    uint verificationId,
    string keyFingerprint
    );

    /* administrative functions: */
    function addManager(address acc) returns (bool){
        if (msg.sender != owner) {
            return false;
        }
        isManager[acc] = true;
        ManagerAdded(acc, msg.sender);
        return true;
    }

    event ManagerAdded (address added, address addedBy);

    function removeManager(address manager) returns (bool){
        if (msg.sender != owner) {
            return false;
        }
        isManager[manager] = false;
        ManagerRemoved(manager, msg.sender);
        return true;
    }

    event ManagerRemoved(address removed, address removedBy);

    function setPriceForVerification(uint priceInWei) returns (bool){
        if (!isManager[msg.sender]) {
            return false;
        }
        priceForVerificationInWei = priceInWei;
        return true;
    }

    function withdraw() returns (bool){// can be called by any user;
        uint sum = this.balance;
        if (!withdrawalAddress.send(this.balance)) {// makes withdrawal and returns true or false
            return false;
        }
        Withdrawal(withdrawalAddress, sum);
        return true;
    }

    event Withdrawal(address to, uint sumInWei);

    function setWithdrawalAddress(address _withdrawalAddress) returns (bool){
        if (!isManager[msg.sender]) {
            return false;
        }
        if (withdrawalAddressFixed) {
            return false;
        }
        withdrawalAddress = _withdrawalAddress;
        WithdrawalAddressChanged(withdrawalAddress, msg.sender);
        return true;
    }

    event WithdrawalAddressChanged(address newWithdrawalAddress, address setBy);

    function fixWithdrawalAddress() returns (bool){
        if (!isManager[msg.sender]) {
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

}
