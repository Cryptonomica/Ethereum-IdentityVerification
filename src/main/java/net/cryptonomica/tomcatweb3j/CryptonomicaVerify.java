package net.cryptonomica.tomcatweb3j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.2.1.
 */
public final class CryptonomicaVerify extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b5b60058054600160a060020a03191633600160a060020a03169081179091556000908152600260205260409020805460ff191660011790556004805460a060020a60ff02191690555b5b611293806100656000396000f300606060405236156100ee5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166309905bdb81146100f057806321b8092e146101125780632d06177a14610142578063312a5db814610172578063325d23471461023c5780633ccfd60b1461026a57806376e57d4b1461028e578063893880a3146102b25780638da5cb5b146102d65780639db66f9614610302578063a1f7d4c714610365578063ac18de431461054b578063b4871f5a1461057b578063eac449d9146105e3578063f2bcd02214610616578063f3ae241514610642578063fb20dc8014610672575bfe5b34156100f857fe5b610100610699565b60408051918252519081900360200190f35b341561011a57fe5b61012e600160a060020a036004351661069f565b604080519115158252519081900360200190f35b341561014a57fe5b61012e600160a060020a0360043516610758565b604080519115158252519081900360200190f35b341561017a57fe5b604080516020600460443581810135601f810184900484028501840190955284845261012e948235600160a060020a031694602480359560649492939190920191819084018382808284375050604080516020601f60808a01358b0180359182018390048302840183018552818452989a8a359a838101359a9581013599506060810135985090965060a001945091928101919081908401838280828437509496506107e795505050505050565b604080519115158252519081900360200190f35b341561024457fe5b610100600160a060020a0360043516610aa1565b60408051918252519081900360200190f35b341561027257fe5b61012e610ab3565b604080519115158252519081900360200190f35b341561029657fe5b61012e610b44565b604080519115158252519081900360200190f35b34156102ba57fe5b61012e610b54565b604080519115158252519081900360200190f35b34156102de57fe5b6102e6610c0b565b60408051600160a060020a039092168252519081900360200190f35b60408051602060046024803582810135601f810185900485028601850190965285855261012e9583359593946044949392909201918190840183828082843750949650610c1a95505050505050565b604080519115158252519081900360200190f35b341561036d57fe5b610384600160a060020a0360043516602435610dcd565b604080518c81528a15159181019190915260a0810187905260c0810186905260e081018590526101008082018590528315156101208301526101408201839052610160602083018181528d5460026001821615909402600019011692909204908301819052606083019060808401906101808501908f9080156104485780601f1061041d57610100808354040283529160200191610448565b820191906000526020600020905b81548152906001019060200180831161042b57829003601f168201915b505084810383528c54600260001961010060018416150201909116048082526020909101908d9080156104bc5780601f10610491576101008083540402835291602001916104bc565b820191906000526020600020905b81548152906001019060200180831161049f57829003601f168201915b505084810382528b54600260001961010060018416150201909116048082526020909101908c9080156105305780601f1061050557610100808354040283529160200191610530565b820191906000526020600020905b81548152906001019060200180831161051357829003601f168201915b50509e50505050505050505050505050505060405180910390f35b341561055357fe5b61012e600160a060020a0360043516610e2b565b604080519115158252519081900360200190f35b341561058357fe5b610100600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843750949650610eb795505050505050565b60408051918252519081900360200190f35b34156105eb57fe5b61012e600160a060020a0360043516602435611014565b604080519115158252519081900360200190f35b341561061e57fe5b6102e661116b565b60408051600160a060020a039092168252519081900360200190f35b341561064a57fe5b61012e600160a060020a036004351661117a565b604080519115158252519081900360200190f35b341561067a57fe5b61012e60043561118f565b604080519115158252519081900360200190f35b60035481565b600160a060020a03331660009081526002602052604081205460ff1615156106c957506000610753565b60045460a060020a900460ff16156106e357506000610753565b6004805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03848116919091179182905560408051928216835233909116602083015280517f753c764050d1f78bb2dfd7e11775a4b6608d0dfefdd8392d028b8459a346e5bb9281900390910190a15060015b919050565b60055460009033600160a060020a0390811691161461077957506000610753565b600160a060020a03808316600081815260026020908152604091829020805460ff191660011790558151928352339093169282019290925281517f05a4006f300442cf8b7fdb885f5ee958812020bffb5c5a8e655fde64e5f987ed929181900390910190a15060015b919050565b600160a060020a03331660009081526002602052604081205460ff16151561081157506000610a95565b600160a060020a0389166000908152602081815260408083208b845282529091208851610846926003909201918a01906111c7565b5085600060008b600160a060020a0316600160a060020a0316815260200190815260200160002060008a81526020019081526020016000206005018190555084600060008b600160a060020a0316600160a060020a0316815260200190815260200160002060008a81526020019081526020016000206006018190555083600060008b600160a060020a0316600160a060020a0316815260200190815260200160002060008a81526020019081526020016000206007018190555082600060008b600160a060020a0316600160a060020a0316815260200190815260200160002060008a8152602001908152602001600020600801819055507f206b70bda3731fd29e08feb60fa8d402b456387a33e252f9179d8c7ce1971d0a8988878787878c6040518088600160a060020a0316600160a060020a0316815260200180602001878152602001868152602001858152602001806020018481526020018381038352898181518152602001915080519060200190808383600083146109e6575b8051825260208311156109e657601f1990920191602091820191016109c6565b505050905090810190601f168015610a125780820380516001836020036101000a031916815260200191505b5083810382528551815285516020918201918701908083838215610a51575b805182526020831115610a5157601f199092019160209182019101610a31565b505050905090810190601f168015610a7d5780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390a15060015b98975050505050505050565b60016020526000908152604090205481565b600454604051600091600160a060020a033081168031939290911691903180156108fc029185818181858888f193505050501515610af45760009150610b40565b60045460408051600160a060020a0390921682526020820183905280517f7fcf532c15f0a6db0bd6d0e038bea71d30d808c7d98cb3bf7268a95bf5081b659281900390910190a1600191505b5090565b60045460a060020a900460ff1681565b600160a060020a03331660009081526002602052604081205460ff161515610b7e57506000610c08565b60045460a060020a900460ff1615610b9857506000610c08565b6004805474ff0000000000000000000000000000000000000000191660a060020a179081905560408051600160a060020a03928316815233909216602083015280517fff51cf04e7fbddffc521b8673e9282b10a91c659c12eea9ef99182bd9a95ff719281900390910190a15060015b90565b600554600160a060020a031681565b6000600354341015610c2c5760006000fd5b600160a060020a03331660009081526020818152604080832086845290915290206002015460ff1615610c5f5760006000fd5b600160a060020a03331660009081526020818152604080832086845282529091208351610c94926001909201918501906111c7565b50600160a060020a0333908116600081815260208181526040808320888452825291829020600201805460ff19166001908117909155825193845283820188905291830182905260a0606084018181526007918501919091527f737563636573730000000000000000000000000000000000000000000000000060c085015260e06080850181815288519186019190915287517f25c5d529ed00342a066acf9424285f578d255b749965f4a8f7b9e410061ab1e596958a95948a94919391926101008501918601908083838215610d86575b805182526020831115610d8657601f199092019160209182019101610d66565b505050905090810190601f168015610db25780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15060015b92915050565b600060208181529281526040808220909352908152208054600282015460058301546006840154600785015460088601546009870154600a8801549697600181019760ff97881697600383019760049093019692959294929316908b565b60055460009033600160a060020a03908116911614610e4c57506000610753565b600160a060020a03808316600081815260026020908152604091829020805460ff191690558151928352339093169282019290925281517f3e902a6ee93dd5b2d48bd1009c7701a481be512b1ef73dbed2f95ea44c59ea88929181900390910190a15060015b919050565b604080516c01000000000000000000000000600160a060020a0333169081028252438040601484015242603484015260f919014060548301528251918290036074019091206000918252600160208181528484208054928301905583815284842082855281529383208281558551939492939192610f3e92600490920191908701906111c7565b506040805133600160a060020a0381168252918101839052606081018490526080602080830182815288519284019290925287517f977b32fc79081a63050ea95d96de6e3b6da3bfae4ae72d752050d47af13b62ea949389938793899360a08401918701908083838215610fcd575b805182526020831115610fcd57601f199092019160209182019101610fad565b505050905090810190601f168015610ff95780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a18192505b5050919050565b600082600160a060020a031633600160a060020a03161415806110505750600160a060020a03331660009081526002602052604090205460ff16155b1561105d57506000610dc7565b33600160a060020a038116600081815260208181526040808320878452825291829020600981018054600160ff19909116811790915583519485524292850183905292840187905260806060850181815260049092018054600261010096821615969096026000190116949094049085018190527f86f8e538a3c90219afbfc7b970688c11eaf5187ff454780b8ec533f6d92771659594929388939092909160a0830190849080156111505780601f1061112557610100808354040283529160200191611150565b820191906000526020600020905b81548152906001019060200180831161113357829003601f168201915b50509550505050505060405180910390a15060015b92915050565b600454600160a060020a031681565b60026020526000908152604090205460ff1681565b600160a060020a03331660009081526002602052604081205460ff1615156111b957506000610753565b50600381905560015b919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061120857805160ff1916838001178555611235565b82800160010185558215611235579182015b8281111561123557825182559160200191906001019061121a565b5b50610b40929150611246565b5090565b610c0891905b80821115610b40576000815560010161124c565b5090565b905600a165627a7a723058208a0dfaf4cc11b6e4abb9cf117d5bcf07a824b44cb8940ae7d0f8ec903e83b38b0029";

    private CryptonomicaVerify(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private CryptonomicaVerify(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<StringToSignRequestedEventResponse> getStringToSignRequestedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("StringToSignRequested", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<StringToSignRequestedEventResponse> responses = new ArrayList<StringToSignRequestedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            StringToSignRequestedEventResponse typedResponse = new StringToSignRequestedEventResponse();
            typedResponse.forAccount = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.forKeyFingerpint = (Utf8String) eventValues.getNonIndexedValues().get(1);
            typedResponse.AccountVerificationId = (Uint256) eventValues.getNonIndexedValues().get(2);
            typedResponse.stringToSign = (Bytes32) eventValues.getNonIndexedValues().get(3);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<StringToSignRequestedEventResponse> stringToSignRequestedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("StringToSignRequested", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, StringToSignRequestedEventResponse>() {
            @Override
            public StringToSignRequestedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                StringToSignRequestedEventResponse typedResponse = new StringToSignRequestedEventResponse();
                typedResponse.forAccount = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.forKeyFingerpint = (Utf8String) eventValues.getNonIndexedValues().get(1);
                typedResponse.AccountVerificationId = (Uint256) eventValues.getNonIndexedValues().get(2);
                typedResponse.stringToSign = (Bytes32) eventValues.getNonIndexedValues().get(3);
                return typedResponse;
            }
        });
    }

    public List<SignedStringUploadedEventResponse> getSignedStringUploadedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("SignedStringUploaded", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<SignedStringUploadedEventResponse> responses = new ArrayList<SignedStringUploadedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            SignedStringUploadedEventResponse typedResponse = new SignedStringUploadedEventResponse();
            typedResponse.fromAccount = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.verificationId = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.result = (Bool) eventValues.getNonIndexedValues().get(2);
            typedResponse.message = (Utf8String) eventValues.getNonIndexedValues().get(3);
            typedResponse.signedString = (Utf8String) eventValues.getNonIndexedValues().get(4);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SignedStringUploadedEventResponse> signedStringUploadedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("SignedStringUploaded", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, SignedStringUploadedEventResponse>() {
            @Override
            public SignedStringUploadedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                SignedStringUploadedEventResponse typedResponse = new SignedStringUploadedEventResponse();
                typedResponse.fromAccount = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.verificationId = (Uint256) eventValues.getNonIndexedValues().get(1);
                typedResponse.result = (Bool) eventValues.getNonIndexedValues().get(2);
                typedResponse.message = (Utf8String) eventValues.getNonIndexedValues().get(3);
                typedResponse.signedString = (Utf8String) eventValues.getNonIndexedValues().get(4);
                return typedResponse;
            }
        });
    }

    public List<VerificationAddedEventResponse> getVerificationAddedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("VerificationAdded", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<VerificationAddedEventResponse> responses = new ArrayList<VerificationAddedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            VerificationAddedEventResponse typedResponse = new VerificationAddedEventResponse();
            typedResponse.verifiedAccount = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.name = (Utf8String) eventValues.getNonIndexedValues().get(1);
            typedResponse.birthDateYear = (Uint256) eventValues.getNonIndexedValues().get(2);
            typedResponse.birthDateMonth = (Uint256) eventValues.getNonIndexedValues().get(3);
            typedResponse.birthDateDay = (Uint256) eventValues.getNonIndexedValues().get(4);
            typedResponse.keyFingerprint = (Utf8String) eventValues.getNonIndexedValues().get(5);
            typedResponse.keyValidUntil = (Uint256) eventValues.getNonIndexedValues().get(6);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<VerificationAddedEventResponse> verificationAddedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("VerificationAdded", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, VerificationAddedEventResponse>() {
            @Override
            public VerificationAddedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                VerificationAddedEventResponse typedResponse = new VerificationAddedEventResponse();
                typedResponse.verifiedAccount = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.name = (Utf8String) eventValues.getNonIndexedValues().get(1);
                typedResponse.birthDateYear = (Uint256) eventValues.getNonIndexedValues().get(2);
                typedResponse.birthDateMonth = (Uint256) eventValues.getNonIndexedValues().get(3);
                typedResponse.birthDateDay = (Uint256) eventValues.getNonIndexedValues().get(4);
                typedResponse.keyFingerprint = (Utf8String) eventValues.getNonIndexedValues().get(5);
                typedResponse.keyValidUntil = (Uint256) eventValues.getNonIndexedValues().get(6);
                return typedResponse;
            }
        });
    }

    public List<VerificationRevokedEventResponse> getVerificationRevokedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("verificationRevoked", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<VerificationRevokedEventResponse> responses = new ArrayList<VerificationRevokedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            VerificationRevokedEventResponse typedResponse = new VerificationRevokedEventResponse();
            typedResponse.verifiedAccount = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.revokedOn = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.verificationId = (Uint256) eventValues.getNonIndexedValues().get(2);
            typedResponse.keyFingerprint = (Utf8String) eventValues.getNonIndexedValues().get(3);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<VerificationRevokedEventResponse> verificationRevokedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("verificationRevoked", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, VerificationRevokedEventResponse>() {
            @Override
            public VerificationRevokedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                VerificationRevokedEventResponse typedResponse = new VerificationRevokedEventResponse();
                typedResponse.verifiedAccount = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.revokedOn = (Uint256) eventValues.getNonIndexedValues().get(1);
                typedResponse.verificationId = (Uint256) eventValues.getNonIndexedValues().get(2);
                typedResponse.keyFingerprint = (Utf8String) eventValues.getNonIndexedValues().get(3);
                return typedResponse;
            }
        });
    }

    public List<ManagerAddedEventResponse> getManagerAddedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ManagerAdded", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ManagerAddedEventResponse> responses = new ArrayList<ManagerAddedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ManagerAddedEventResponse typedResponse = new ManagerAddedEventResponse();
            typedResponse.added = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.addedBy = (Address) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ManagerAddedEventResponse> managerAddedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ManagerAdded", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ManagerAddedEventResponse>() {
            @Override
            public ManagerAddedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ManagerAddedEventResponse typedResponse = new ManagerAddedEventResponse();
                typedResponse.added = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.addedBy = (Address) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public List<ManagerRemovedEventResponse> getManagerRemovedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ManagerRemoved", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ManagerRemovedEventResponse> responses = new ArrayList<ManagerRemovedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ManagerRemovedEventResponse typedResponse = new ManagerRemovedEventResponse();
            typedResponse.removed = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.removedBy = (Address) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ManagerRemovedEventResponse> managerRemovedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ManagerRemoved", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ManagerRemovedEventResponse>() {
            @Override
            public ManagerRemovedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ManagerRemovedEventResponse typedResponse = new ManagerRemovedEventResponse();
                typedResponse.removed = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.removedBy = (Address) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public List<WithdrawalEventResponse> getWithdrawalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Withdrawal", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<WithdrawalEventResponse> responses = new ArrayList<WithdrawalEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            WithdrawalEventResponse typedResponse = new WithdrawalEventResponse();
            typedResponse.to = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.sumInWei = (Uint256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WithdrawalEventResponse> withdrawalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Withdrawal", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, WithdrawalEventResponse>() {
            @Override
            public WithdrawalEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                WithdrawalEventResponse typedResponse = new WithdrawalEventResponse();
                typedResponse.to = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.sumInWei = (Uint256) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public List<WithdrawalAddressChangedEventResponse> getWithdrawalAddressChangedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("WithdrawalAddressChanged", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<WithdrawalAddressChangedEventResponse> responses = new ArrayList<WithdrawalAddressChangedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            WithdrawalAddressChangedEventResponse typedResponse = new WithdrawalAddressChangedEventResponse();
            typedResponse.newWithdrawalAddress = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.setBy = (Address) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WithdrawalAddressChangedEventResponse> withdrawalAddressChangedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("WithdrawalAddressChanged", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, WithdrawalAddressChangedEventResponse>() {
            @Override
            public WithdrawalAddressChangedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                WithdrawalAddressChangedEventResponse typedResponse = new WithdrawalAddressChangedEventResponse();
                typedResponse.newWithdrawalAddress = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.setBy = (Address) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public List<WithdrawalAddressFixedEventResponse> getWithdrawalAddressFixedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("WithdrawalAddressFixed", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<WithdrawalAddressFixedEventResponse> responses = new ArrayList<WithdrawalAddressFixedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            WithdrawalAddressFixedEventResponse typedResponse = new WithdrawalAddressFixedEventResponse();
            typedResponse.withdrawalAddress = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.fixedBy = (Address) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WithdrawalAddressFixedEventResponse> withdrawalAddressFixedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("WithdrawalAddressFixed", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, WithdrawalAddressFixedEventResponse>() {
            @Override
            public WithdrawalAddressFixedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                WithdrawalAddressFixedEventResponse typedResponse = new WithdrawalAddressFixedEventResponse();
                typedResponse.withdrawalAddress = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.fixedBy = (Address) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Future<Uint256> priceForVerificationInWei() {
        Function function = new Function("priceForVerificationInWei", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setWithdrawalAddress(Address _withdrawalAddress) {
        Function function = new Function("setWithdrawalAddress", Arrays.<Type>asList(_withdrawalAddress), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> addManager(Address acc) {
        Function function = new Function("addManager", Arrays.<Type>asList(acc), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> verify(Address acc, Uint256 verificationId, Utf8String name, Uint256 keyValidUntil, Uint256 birthDateYear, Uint256 birthDateMonth, Uint256 birthDateDay, Utf8String keyFingerprint) {
        Function function = new Function("verify", Arrays.<Type>asList(acc, verificationId, name, keyValidUntil, birthDateYear, birthDateMonth, birthDateDay, keyFingerprint), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint256> numberOfVerifications(Address param0) {
        Function function = new Function("numberOfVerifications", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> withdraw() {
        Function function = new Function("withdraw", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Bool> withdrawalAddressFixed() {
        Function function = new Function("withdrawalAddressFixed", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> fixWithdrawalAddress() {
        Function function = new Function("fixWithdrawalAddress", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Address> owner() {
        Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> uploadSignedString(Uint256 verificationId, Utf8String signedString) {
        Function function = new Function("uploadSignedString", Arrays.<Type>asList(verificationId, signedString), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<List<Type>> verification(Address param0, Uint256 param1) {
        Function function = new Function("verification", 
                Arrays.<Type>asList(param0, param1), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> removeManager(Address manager) {
        Function function = new Function("removeManager", Arrays.<Type>asList(manager), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> getStringToSignWithKey(Utf8String keyFingerprint) {
        Function function = new Function("getStringToSignWithKey", Arrays.<Type>asList(keyFingerprint), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> revoke(Address acc, Uint256 verificationId) {
        Function function = new Function("revoke", Arrays.<Type>asList(acc, verificationId), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Address> withdrawalAddress() {
        Function function = new Function("withdrawalAddress", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Bool> isManager(Address param0) {
        Function function = new Function("isManager", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setPriceForVerification(Uint256 priceInWei) {
        Function function = new Function("setPriceForVerification", Arrays.<Type>asList(priceInWei), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<CryptonomicaVerify> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(CryptonomicaVerify.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<CryptonomicaVerify> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(CryptonomicaVerify.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static CryptonomicaVerify load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CryptonomicaVerify(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static CryptonomicaVerify load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CryptonomicaVerify(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class StringToSignRequestedEventResponse {
        public Address forAccount;

        public Utf8String forKeyFingerpint;

        public Uint256 AccountVerificationId;

        public Bytes32 stringToSign;
    }

    public static class SignedStringUploadedEventResponse {
        public Address fromAccount;

        public Uint256 verificationId;

        public Bool result;

        public Utf8String message;

        public Utf8String signedString;
    }

    public static class VerificationAddedEventResponse {
        public Address verifiedAccount;

        public Utf8String name;

        public Uint256 birthDateYear;

        public Uint256 birthDateMonth;

        public Uint256 birthDateDay;

        public Utf8String keyFingerprint;

        public Uint256 keyValidUntil;
    }

    public static class VerificationRevokedEventResponse {
        public Address verifiedAccount;

        public Uint256 revokedOn;

        public Uint256 verificationId;

        public Utf8String keyFingerprint;
    }

    public static class ManagerAddedEventResponse {
        public Address added;

        public Address addedBy;
    }

    public static class ManagerRemovedEventResponse {
        public Address removed;

        public Address removedBy;
    }

    public static class WithdrawalEventResponse {
        public Address to;

        public Uint256 sumInWei;
    }

    public static class WithdrawalAddressChangedEventResponse {
        public Address newWithdrawalAddress;

        public Address setBy;
    }

    public static class WithdrawalAddressFixedEventResponse {
        public Address withdrawalAddress;

        public Address fixedBy;
    }
}
