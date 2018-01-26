pragma solidity ^0.4.15;


contract AddressToString {

    address public owner; //
    address public latestTestAddressResult;

    string public latestTestAddressAsStringResult;

    function AddressToString(){
        owner = msg.sender;
    }

    /* ethereum address to string */
    // https://ethereum.stackexchange.com/questions/8346/convert-address-to-string
    // https://ethereum.stackexchange.com/a/8447/1964
    function addressToAsciiString(address _address) public constant returns (string) {
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

    function msgSenderAddressToStringTest() public returns (string) {
        latestTestAddressAsStringResult = addressToAsciiString(msg.sender);
        MsgSenderAddressToStringTest(msg.sender, latestTestAddressAsStringResult);
        return latestTestAddressAsStringResult;
    } //
    event MsgSenderAddressToStringTest(address msgSenderAddress, string msgSenderAsString);

}