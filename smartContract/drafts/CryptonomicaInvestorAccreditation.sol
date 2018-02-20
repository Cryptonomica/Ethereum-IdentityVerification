pragma solidity ^0.4.19;

contract CryptonomicaInvestorAccreditation {

    function CryptonomicaInvestorAccreditation(){
        //
    }

    /* -------------------- Investor Verification -------------------- */
    mapping(address => bool) public isAccreditedInvestor;
    mapping(address => string) public investorVerifiedByStr; // description of a person who verified investor status
    mapping(address => address) public investorVerificationAddedByAddress; // eth address of a person who verified investor status
    mapping(address => uint) public investorVerificationAddedOn; //
    mapping(address => uint) public investorVerificationValidUntil; // 0 - if not set
    mapping(address => uint) public investorVerificationRevokedOn; // 0 - if not revoked
    mapping(address => bool) public canVerifyInvestors;

}
