pragma solidity ^0.4.11;


// see:
// https://ethereum.stackexchange.com/questions/1081/how-to-concatenate-a-bytes32-array-to-a-string
// https://ethereum.stackexchange.com/questions/2519/how-to-convert-a-bytes32-to-string

contract StringBytesConversion {

    /* -------- String to bytes*/
    // --- Strings to bytes

    function stringToBytes32(string memory source) private returns (bytes32 result) {
        if (bytes(source).length > 32) {throw;}
        assembly {
        result := mload(add(source, 32))
        }
    }

    function stringToBytes20(string memory source) private returns (bytes20 result) {
        if (bytes(source).length > 20) {throw;}
        assembly {
        result := mload(add(source, 20))
        }
    }

    function stringToBytes2(string memory source) private returns (bytes2 result) {
        if (bytes(source).length > 2) {throw;}
        assembly {
        result := mload(add(source, 2))
        }
    }

    function stringToBytes32bis(string memory source) returns (bytes32 result){
        // see:
        // http://solidity.readthedocs.io/en/develop/frequently-asked-questions.html?highlight=bytes32#what-is-the-relationship-between-bytes32-and-string-why-is-it-that-bytes32-somevar-stringliteral-works-and-what-does-the-saved-32-byte-hex-value-mean
        bytes32 somevar = "stringLiteral";
        // works
        // result = source; // << but this not (TypeError: Type string memory is not implicitly convertible to expected type bytes32)
    }

    /* -------- Bytes to string */

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

    // --test
    function testStringConversion(string _str) returns (string){
        bytes32 bs32 = stringToBytes32(_str);
        return string(bytes32ToString(bs32));
    }

    // ------------
    function bytes32ArrayToString(bytes32[] data) returns (string) {
        bytes memory bytesString = new bytes(data.length * 32);
        uint urlLength;
        for (uint i = 0; i < data.length; i++) {
            for (uint j = 0; j < 32; j++) {
                byte char = byte(bytes32(uint(data[i]) * 2 ** (8 * j)));
                if (char != 0) {
                    bytesString[urlLength] = char;
                    urlLength += 1;
                }
            }
        }
        bytes memory bytesStringTrimmed = new bytes(urlLength);
        for (i = 0; i < urlLength; i++) {
            bytesStringTrimmed[i] = bytesString[i];
        }
        return string(bytesStringTrimmed);
    }

}