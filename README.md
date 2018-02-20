# Ethereum-IdentityVerification

## Smart contract deployment 

(1) (required)

'Owner' of the contract after deployment have to set price for verification in wei: 

```
function setPriceForVerification(uint priceInWei) public returns (bool) 
```

## Using smart contract 


### String to sign with key

"I hereby confirm that the address <address lowercase> is my Ethereum address"

### Sign this string  

Using any OpenPGP soft sign this string. 

In console: 

```bash
echo "String to sign" > string.to.sign.plaintext.txt 

gpg2 --clearsign --default-key 'Viktor Ageyev <ageyev@international-arbitration.org.uk>' string.to.sign.plaintext.txt 
```

It has to be a cleartext signed message, like this: 

```
-----BEGIN PGP SIGNED MESSAGE-----
Hash: SHA256

String to sign
-----BEGIN PGP SIGNATURE-----
Version: GnuPG v2

iQEcBAEBCAAGBQJaAu3fAAoJEGp7IeKETHmA4b4H/03CqZBsqQwzqx4qWY8Xo94r
Ha7fk4FBO1Fe8h+zVT6C3MCZc8ZX2B0SsQafGNZMhugDpUCds+DPO2zf7wguBk+U
SvtQ3xi5iicT0RubcV2hI4jhbL4DvVBv7DeCab44HxAfl7leW3Ft6LAmrR/Hcrc7
njo6Nz3T/NvSFNaQXBK1uP8bCZMhc+zqOg5C9uEAVNtKa2iPTm6vbuQGDdp9UKBg
BI6OrTYF4QGeUlxL2A6DpRiZfX0NXlPXjLL0b34fc05xWVXWJjhAw85VgdQwIgmH
zBQMjW3e9dnDJodrosl+TQ1LGkQLy+R6BPX5JoBDa59F0ncyfO+Z+ILrv+hLLbQ=
=TEa2
-----END PGP SIGNATURE-----
```

# Store signed message in smart contract 

call: 

```
function uploadSignedString(string _fingerprint, string _signedString) public payable {
```

ascii armored text containing signed string and the signature have to be properly formatted, i.e.: 

```
            "-----BEGIN PGP SIGNED MESSAGE-----\n" +
            "Hash: SHA256\n" +
            "\n" +
            "String to sign\n" +
            "-----BEGIN PGP SIGNATURE-----\n" +
            "Version: GnuPG v2\n" +
            "\n" +
            "iQEcBAEBCAAGBQJaAu3fAAoJEGp7IeKETHmA4b4H/03CqZBsqQwzqx4qWY8Xo94r\n" +
            "Ha7fk4FBO1Fe8h+zVT6C3MCZc8ZX2B0SsQafGNZMhugDpUCds+DPO2zf7wguBk+U\n" +
            "SvtQ3xi5iicT0RubcV2hI4jhbL4DvVBv7DeCab44HxAfl7leW3Ft6LAmrR/Hcrc7\n" +
            "njo6Nz3T/NvSFNaQXBK1uP8bCZMhc+zqOg5C9uEAVNtKa2iPTm6vbuQGDdp9UKBg\n" +
            "BI6OrTYF4QGeUlxL2A6DpRiZfX0NXlPXjLL0b34fc05xWVXWJjhAw85VgdQwIgmH\n" +
            "zBQMjW3e9dnDJodrosl+TQ1LGkQLy+R6BPX5JoBDa59F0ncyfO+Z+ILrv+hLLbQ=\n" +
            "=TEa2\n" +
            "-----END PGP SIGNATURE-----";
```
for input on frontend use textarea input type, not text input type 
(see: https://stackoverflow.com/questions/21698065/whats-the-difference-between-textarea-and-input-type-text-in-angularjs )

# Request signature verification from cryptonomica.net web-site 

Cryptonomica server requests from smart contract for given Ethereum address: 


1) signed string ( mapping(address => string) public signedString; )

2) (yet unverified) fingerprint ( mapping(address => string) public unverifiedFingerprint; )

than get PublicKey (if uploaded to Cryptonomica server and verified by Cryptonomica service) for this fingerprint and verify signature 

Code to verify signature: 

```
net.cryptonomica.tomcatweb3j.utilities.PGPTools.verifyText(String plainText, PGPPublicKey publicKey)
```

if signature verified (```true```) server send verification data connected to key with given fingerprint 
to smart contract.

Solidity:
```
    // from 'manager' account only
    // (!) Gas requirement: infinite
    function addVerificationData(
        address _acc, //
        string _fingerprint, // "57A5FEE5A34D563B4B85ADF3CE369FD9E77173E5"
        bytes20 _fingerprintBytes20, // "0x57A5FEE5A34D563B4B85ADF3CE369FD9E77173E5"
        uint _keyCertificateValidUntil, //
        string _firstName, //
        string _lastName, //
        uint _birthDate, //
        string _nationality) public {
```

Java: 

net.cryptonomica.tomcatweb3j.servlets.AddVerificationDataServlet  

# Get Verification data for Ethereum Address: 

net.cryptonomica.tomcatweb3j.servlets.GetVerificationServlet 






