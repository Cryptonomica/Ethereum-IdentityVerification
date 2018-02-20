package net.cryptonomica.tomcatweb3j.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes20;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class CryptonomicaVerification extends Contract {
    private static final String BINARY = "6060604052608060405190810160405280604c81526020017f492068657265627920636f6e6669726d2074686174207468652061646472657381526020017f73203c61646472657373206c6f776572636173653e206973206d79204574686581526020017f7265756d20616464726573730000000000000000000000000000000000000000815250600c9080516200009c92916020019062000103565b506011805460a060020a60ff02191690553415620000b957600080fd5b600e8054600160a060020a033316600160a060020a031991821681179092556000828152600f60205260409020805460ff19166001179055601180549091169091179055620001a8565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200014657805160ff191683800117855562000176565b8280016001018555821562000176579182015b828111156200017657825182559160200191906001019062000159565b506200018492915062000188565b5090565b620001a591905b808211156200018457600081556001016200018f565b90565b61196480620001b86000396000f3006060604052600436106101955763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166302c29349811461019a5780630851903b146101cd57806309905bdb146102575780630dc2968c1461027c57806321b8092e1461031257806322652e05146103315780632d06177a1461035057806339b51e7c1461036f57806345d6f02e1461038e57806347889c42146103ad578063546e1959146103cc5780635b3d0bc1146103eb57806361b027b01461040a57806365455fdc1461044b578063735dca661461046a57806376e57d4b146105b0578063836afead146105c35780638da5cb5b146105e25780639201de551461061157806392b4e132146106275780639538833c146108e25780639a159bf6146108f5578063ac18de4314610914578063b17a98b614610933578063c668844514610946578063cfb5192814610959578063d6e87b44146109aa578063e998d2fa146109cf578063ec945269146109ee578063f2bcd02214610a0d578063f3ae241514610a20578063fb20dc8014610a3f575b600080fd5b34156101a557600080fd5b6101b9600160a060020a0360043516610a55565b604051901515815260200160405180910390f35b61025560046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528181529291906020840183838082843750949650610b2195505050505050565b005b341561026257600080fd5b61026a610cf4565b60405190815260200160405180910390f35b341561028757600080fd5b61029b600160a060020a0360043516610cfa565b60405160208082528190810183818151815260200191508051906020019080838360005b838110156102d75780820151838201526020016102bf565b50505050905090810190601f1680156103045780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561031d57600080fd5b610255600160a060020a0360043516610daa565b341561033c57600080fd5b61029b600160a060020a0360043516610e4b565b341561035b57600080fd5b610255600160a060020a0360043516610ec8565b341561037a57600080fd5b61026a600160a060020a0360043516610f3c565b341561039957600080fd5b61026a600160a060020a0360043516610f4e565b34156103b857600080fd5b61026a600160a060020a0360043516610f60565b34156103d757600080fd5b610255600160a060020a0360043516610f72565b34156103f657600080fd5b61026a600160a060020a0360043516610feb565b341561041557600080fd5b610429600160a060020a0360043516610ffd565b6040516bffffffffffffffffffffffff19909116815260200160405180910390f35b341561045657600080fd5b61026a600160a060020a036004351661101e565b341561047557600080fd5b61025560048035600160a060020a03169060446024803590810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094966bffffffffffffffffffffffff19873516966020808201359750919550606081019450604090810135860180830194503592508291601f83018190048102019051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052818152929190602084018383808284378201915050505050509190803590602001909190803590602001908201803590602001908080601f01602080910402602001604051908101604052818152929190602084018383808284375094965061103095505050505050565b34156105bb57600080fd5b6101b9611303565b34156105ce57600080fd5b61026a600160a060020a0360043516611313565b34156105ed57600080fd5b6105f5611325565b604051600160a060020a03909116815260200160405180910390f35b341561061c57600080fd5b61029b600435611334565b341561063257600080fd5b610646600160a060020a03600435166113f0565b60405180806020018a81526020018060200180602001898152602001806020018881526020018781526020018060200186810386528f8181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156106f75780601f106106cc576101008083540402835291602001916106f7565b820191906000526020600020905b8154815290600101906020018083116106da57829003601f168201915b505086810385528d54600260001961010060018416150201909116048082526020909101908e90801561076b5780601f106107405761010080835404028352916020019161076b565b820191906000526020600020905b81548152906001019060200180831161074e57829003601f168201915b505086810384528c54600260001961010060018416150201909116048082526020909101908d9080156107df5780601f106107b4576101008083540402835291602001916107df565b820191906000526020600020905b8154815290600101906020018083116107c257829003601f168201915b505086810383528a54600260001961010060018416150201909116048082526020909101908b9080156108535780601f1061082857610100808354040283529160200191610853565b820191906000526020600020905b81548152906001019060200180831161083657829003601f168201915b50508681038252875460026000196101006001841615020190911604808252602090910190889080156108c75780601f1061089c576101008083540402835291602001916108c7565b820191906000526020600020905b8154815290600101906020018083116108aa57829003601f168201915b50509e50505050505050505050505050505060405180910390f35b34156108ed57600080fd5b61025561142b565b341561090057600080fd5b61026a600160a060020a03600435166114b6565b341561091f57600080fd5b610255600160a060020a03600435166114c8565b341561093e57600080fd5b6101b9611539565b341561095157600080fd5b61029b611620565b341561096457600080fd5b61026a60046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094965061168b95505050505050565b34156109b557600080fd5b6105f56bffffffffffffffffffffffff1960043516611698565b34156109da57600080fd5b610255600160a060020a03600435166116b3565b34156109f957600080fd5b61026a600160a060020a03600435166117e1565b3415610a1857600080fd5b6105f56117f3565b3415610a2b57600080fd5b6101b9600160a060020a0360043516611802565b3415610a4a57600080fd5b610255600435611817565b600e5460009033600160a060020a03908116911614610a7357600080fd5b601154600160a060020a03838116911614610a8d57600080fd5b60115460a060020a900460ff1615610aa457600080fd5b6011805474ff0000000000000000000000000000000000000000191660a060020a17908190557fff51cf04e7fbddffc521b8673e9282b10a91c659c12eea9ef99182bd9a95ff7190600160a060020a031633604051600160a060020a039283168152911660208201526040908101905180910390a1506001919050565b601054341015610b3057600080fd5b600160a060020a0333166000908152600a602052604090205415610b5357600080fd5b8151602814610b6157600080fd5b600160a060020a0333166000908152600160205260409020828051610b8a92916020019061188f565b50600160a060020a0333166000908152600960205260409020818051610bb492916020019061188f565b50600160a060020a0333166000818152600a602090815260408083204290556009909152908190207f3c21c5143ac760b44e24852cca9d3858a3487646fe0fa6c591aa3199cc65531a9185919051808060200180602001838103835285818151815260200191508051906020019080838360005b83811015610c40578082015183820152602001610c28565b50505050905090810190601f168015610c6d5780820380516001836020036101000a031916815260200191505b50838103825284546002600019610100600184161502019091160480825260209091019085908015610ce05780601f10610cb557610100808354040283529160200191610ce0565b820191906000526020600020905b815481529060010190602001808311610cc357829003601f168201915b505094505050505060405180910390a25050565b60105481565b60016020528060005260406000206000915090508054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610da25780601f10610d7757610100808354040283529160200191610da2565b820191906000526020600020905b815481529060010190602001808311610d8557829003601f168201915b505050505081565b600e5433600160a060020a03908116911614610dc557600080fd5b60115460a060020a900460ff1615610ddc57600080fd5b601154600160a060020a033381169183821691167f15fc95cfd5d20b9661cf80c2719d8e3180bee6d89379a03b7b14ca6ac6adea9860405160405180910390a46011805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b60096020528060005260406000206000915090508054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610da25780601f10610d7757610100808354040283529160200191610da2565b600e5433600160a060020a03908116911614610ee357600080fd5b600160a060020a038082166000818152600f602052604090819020805460ff1916600117905533909216917f05a4006f300442cf8b7fdb885f5ee958812020bffb5c5a8e655fde64e5f987ed905160405180910390a350565b60076020526000908152604090205481565b600a6020526000908152604090205481565b60056020526000908152604090205481565b600e5433600160a060020a03908116911614610f8d57600080fd5b6012805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a038381169182179092559033167f856621e76473127fb731503843c14f7b85c6c36fc405c3ea121471425f54fd7960405160405180910390a350565b60026020526000908152604090205481565b6000602081905290815260409020546c010000000000000000000000000281565b60046020526000908152604090205481565b600160a060020a0333166000908152600f602052604090205460ff16151561105757600080fd5b600160a060020a0388166000908152600a6020526040902054151561107b57600080fd5b600160a060020a0388166000908152600760205260409020541561109e57600080fd5b600160a060020a0388166000908152600d602052604090208780516110c792916020019061188f565b50600160a060020a03881660008181526020818152604080832080546c010000000000000000000000008c0473ffffffffffffffffffffffffffffffffffffffff19918216179091556bffffffffffffffffffffffff198b168452600b83528184208054909116851790559282526002808252838320899055600d909152919020600181018790550184805161116192916020019061188f565b5061116b8461168b565b600160a060020a038916600090815260036020818152604080842094909455600d9052919020018380516111a392916020019061188f565b506111ad8361168b565b600160a060020a0389166000908152600460208181526040808420949094556005808252848420879055600d90915292909120908101849055018180516111f892916020019061188f565b506112028161168b565b600160a060020a038916600081815260066020818152604080842095909555600781528483204290819055600d909152918490209081019190915590917f2014ad745d5cc240a4565583f1c0cf348b91f892966a154d418c591911ea04bf9190339051600160a060020a0382166020820152604080825283546002600019610100600184161502019091160490820181905281906060820190859080156112ea5780601f106112bf576101008083540402835291602001916112ea565b820191906000526020600020905b8154815290600101906020018083116112cd57829003601f168201915b5050935050505060405180910390a25050505050505050565b60115460a060020a900460ff1681565b60086020526000908152604090205481565b600e54600160a060020a031681565b61133c611909565b611344611909565b600060206040518059106113555750595b818152601f19601f8301168101602001604052905091505b60208110156113e95783816020811061138257fe5b1a7f0100000000000000000000000000000000000000000000000000000000000000028282815181106113b157fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a90535060010161136d565b5092915050565b600d60205260009081526040902060018101546004820154600683015460078401546002850192600386019290916005870191906008880189565b60125433600160a060020a0390811691161461144657600080fd5b601254600e54600160a060020a0391821691167fb532073b38c83145e3e5135377a08bf9aab55bc0fd7c1179cd4fb995d2a5159c60405160405180910390a3601254600e805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055565b60036020526000908152604090205481565b600e5433600160a060020a039081169116146114e357600080fd5b600160a060020a038082166000818152600f602052604090819020805460ff1916905533909216917f3e902a6ee93dd5b2d48bd1009c7701a481be512b1ef73dbed2f95ea44c59ea88905160405180910390a350565b601154600090600160a060020a03308116803192909116903180156108fc0290604051600060405180830381858888f1935050505015156115ca57601154600160a060020a0333811691167f78746de4b42c369479b14075849ee3378535cb810d96e74712e26a7924f7b021836000604051918252151560208201526040908101905180910390a36000915061161c565b601154600160a060020a0333811691167f78746de4b42c369479b14075849ee3378535cb810d96e74712e26a7924f7b021836001604051918252151560208201526040908101905180910390a3600191505b5090565b600c8054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610da25780601f10610d7757610100808354040283529160200191610da2565b6000602082015192915050565b600b60205260009081526040902054600160a060020a031681565b80600160a060020a031633600160a060020a031614806116eb5750600160a060020a0333166000908152600f602052604090205460ff165b15156116f657600080fd5b600160a060020a0380821660008181526008602090815260408083204290819055600d909252918290206007810182905533909416937fc6f2b8565550ea0e6941e2f0f6b7e65e5eb1fdccb33e0c7815af0f3ce5669cff929091905160208101829052604080825283546002600019610100600184161502019091160490820181905281906060820190859080156117cf5780601f106117a4576101008083540402835291602001916117cf565b820191906000526020600020905b8154815290600101906020018083116117b257829003601f168201915b5050935050505060405180910390a350565b60066020526000908152604090205481565b601154600160a060020a031681565b600f6020526000908152604090205460ff1681565b600160a060020a0333166000908152600f602052604081205460ff16151561183e57600080fd5b506010805490829055600160a060020a0333167f665d155f71ad96c4a04629d54ef9fb27ef57911253588f2ee93474cd02fa3f53828460405191825260208201526040908101905180910390a25050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106118d057805160ff19168380011785556118fd565b828001600101855582156118fd579182015b828111156118fd5782518255916020019190600101906118e2565b5061161c92915061191b565b60206040519081016040526000815290565b61193591905b8082111561161c5760008155600101611921565b905600a165627a7a72305820f6e41870e54639a4a504839fc4699dc5b30c782578046cdd6a4886103f3f53630029";

    protected CryptonomicaVerification(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CryptonomicaVerification(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<SignedStringUploadedEventResponse> getSignedStringUploadedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("SignedStringUploaded", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<SignedStringUploadedEventResponse> responses = new ArrayList<SignedStringUploadedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            SignedStringUploadedEventResponse typedResponse = new SignedStringUploadedEventResponse();
            typedResponse.fromAccount = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.fingerprint = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.uploadedString = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SignedStringUploadedEventResponse> signedStringUploadedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("SignedStringUploaded", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, SignedStringUploadedEventResponse>() {
            @Override
            public SignedStringUploadedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                SignedStringUploadedEventResponse typedResponse = new SignedStringUploadedEventResponse();
                typedResponse.fromAccount = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.fingerprint = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.uploadedString = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<VerificationAddedEventResponse> getVerificationAddedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("VerificationAdded", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<VerificationAddedEventResponse> responses = new ArrayList<VerificationAddedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            VerificationAddedEventResponse typedResponse = new VerificationAddedEventResponse();
            typedResponse.verifiedAccount = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.forFingerprint = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.verificationAddedByAccount = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<VerificationAddedEventResponse> verificationAddedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("VerificationAdded", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, VerificationAddedEventResponse>() {
            @Override
            public VerificationAddedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                VerificationAddedEventResponse typedResponse = new VerificationAddedEventResponse();
                typedResponse.verifiedAccount = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.forFingerprint = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.verificationAddedByAccount = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<VerificationRevokedEventResponse> getVerificationRevokedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("VerificationRevoked", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<VerificationRevokedEventResponse> responses = new ArrayList<VerificationRevokedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            VerificationRevokedEventResponse typedResponse = new VerificationRevokedEventResponse();
            typedResponse.revocedforAccount = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.revokedBy = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.withFingerprint = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.revokedOnUnixTime = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<VerificationRevokedEventResponse> verificationRevokedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("VerificationRevoked", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, VerificationRevokedEventResponse>() {
            @Override
            public VerificationRevokedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                VerificationRevokedEventResponse typedResponse = new VerificationRevokedEventResponse();
                typedResponse.revocedforAccount = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.revokedBy = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.withFingerprint = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.revokedOnUnixTime = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<ChangeOwnerStartedEventResponse> getChangeOwnerStartedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ChangeOwnerStarted", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ChangeOwnerStartedEventResponse> responses = new ArrayList<ChangeOwnerStartedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ChangeOwnerStartedEventResponse typedResponse = new ChangeOwnerStartedEventResponse();
            typedResponse.startedBy = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ChangeOwnerStartedEventResponse> changeOwnerStartedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ChangeOwnerStarted", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ChangeOwnerStartedEventResponse>() {
            @Override
            public ChangeOwnerStartedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ChangeOwnerStartedEventResponse typedResponse = new ChangeOwnerStartedEventResponse();
                typedResponse.startedBy = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<OwnerChangedEventResponse> getOwnerChangedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("OwnerChanged", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<OwnerChangedEventResponse> responses = new ArrayList<OwnerChangedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            OwnerChangedEventResponse typedResponse = new OwnerChangedEventResponse();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnerChangedEventResponse> ownerChangedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("OwnerChanged", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnerChangedEventResponse>() {
            @Override
            public OwnerChangedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                OwnerChangedEventResponse typedResponse = new OwnerChangedEventResponse();
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<ManagerAddedEventResponse> getManagerAddedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ManagerAdded", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ManagerAddedEventResponse> responses = new ArrayList<ManagerAddedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ManagerAddedEventResponse typedResponse = new ManagerAddedEventResponse();
            typedResponse.added = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.addedBy = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ManagerAddedEventResponse> managerAddedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ManagerAdded", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ManagerAddedEventResponse>() {
            @Override
            public ManagerAddedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ManagerAddedEventResponse typedResponse = new ManagerAddedEventResponse();
                typedResponse.added = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.addedBy = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<ManagerRemovedEventResponse> getManagerRemovedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ManagerRemoved", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ManagerRemovedEventResponse> responses = new ArrayList<ManagerRemovedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ManagerRemovedEventResponse typedResponse = new ManagerRemovedEventResponse();
            typedResponse.removed = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.removedBy = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ManagerRemovedEventResponse> managerRemovedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ManagerRemoved", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ManagerRemovedEventResponse>() {
            @Override
            public ManagerRemovedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ManagerRemovedEventResponse typedResponse = new ManagerRemovedEventResponse();
                typedResponse.removed = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.removedBy = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<PriceChangedEventResponse> getPriceChangedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("PriceChanged", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<PriceChangedEventResponse> responses = new ArrayList<PriceChangedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            PriceChangedEventResponse typedResponse = new PriceChangedEventResponse();
            typedResponse.changedBy = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<PriceChangedEventResponse> priceChangedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("PriceChanged", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, PriceChangedEventResponse>() {
            @Override
            public PriceChangedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                PriceChangedEventResponse typedResponse = new PriceChangedEventResponse();
                typedResponse.changedBy = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.from = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.to = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<WithdrawalEventResponse> getWithdrawalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Withdrawal", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<WithdrawalEventResponse> responses = new ArrayList<WithdrawalEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            WithdrawalEventResponse typedResponse = new WithdrawalEventResponse();
            typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.by = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sumInWei = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.success = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WithdrawalEventResponse> withdrawalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Withdrawal", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, WithdrawalEventResponse>() {
            @Override
            public WithdrawalEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                WithdrawalEventResponse typedResponse = new WithdrawalEventResponse();
                typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.by = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.sumInWei = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.success = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<WithdrawalAddressChangedEventResponse> getWithdrawalAddressChangedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("WithdrawalAddressChanged", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<WithdrawalAddressChangedEventResponse> responses = new ArrayList<WithdrawalAddressChangedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            WithdrawalAddressChangedEventResponse typedResponse = new WithdrawalAddressChangedEventResponse();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.changedBy = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WithdrawalAddressChangedEventResponse> withdrawalAddressChangedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("WithdrawalAddressChanged", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, WithdrawalAddressChangedEventResponse>() {
            @Override
            public WithdrawalAddressChangedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                WithdrawalAddressChangedEventResponse typedResponse = new WithdrawalAddressChangedEventResponse();
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.changedBy = (String) eventValues.getIndexedValues().get(2).getValue();
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
            typedResponse.withdrawalAddressFixedAs = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.fixedBy = (String) eventValues.getNonIndexedValues().get(1).getValue();
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
                typedResponse.withdrawalAddressFixedAs = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.fixedBy = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<TransactionReceipt> fixWithdrawalAddress(String _withdrawalAddress) {
        Function function = new Function(
                "fixWithdrawalAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_withdrawalAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> uploadSignedString(String _fingerprint, String _signedString, BigInteger weiValue) {
        Function function = new Function(
                "uploadSignedString", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_fingerprint), 
                new org.web3j.abi.datatypes.Utf8String(_signedString)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> priceForVerificationInWei() {
        Function function = new Function("priceForVerificationInWei", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> unverifiedFingerprint(String param0) {
        Function function = new Function("unverifiedFingerprint", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setWithdrawalAddress(String _withdrawalAddress) {
        Function function = new Function(
                "setWithdrawalAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_withdrawalAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> signedString(String param0) {
        Function function = new Function("signedString", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> addManager(String _acc) {
        Function function = new Function(
                "addManager", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_acc)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> verificationAddedOn(String param0) {
        Function function = new Function("verificationAddedOn", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> signedStringUploadedOnUnixTime(String param0) {
        Function function = new Function("signedStringUploadedOnUnixTime", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> birthDate(String param0) {
        Function function = new Function("birthDate", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> changeOwnerStart(String _newOwner) {
        Function function = new Function(
                "changeOwnerStart", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> keyCertificateValidUntil(String param0) {
        Function function = new Function("keyCertificateValidUntil", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<byte[]> fingerprint(String param0) {
        Function function = new Function("fingerprint", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes20>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<byte[]> lastName(String param0) {
        Function function = new Function("lastName", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> addVerificationData(String _acc, String _fingerprint, byte[] _fingerprintBytes20, BigInteger _keyCertificateValidUntil, String _firstName, String _lastName, BigInteger _birthDate, String _nationality) {
        Function function = new Function(
                "addVerificationData", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_acc), 
                new org.web3j.abi.datatypes.Utf8String(_fingerprint), 
                new org.web3j.abi.datatypes.generated.Bytes20(_fingerprintBytes20), 
                new org.web3j.abi.datatypes.generated.Uint256(_keyCertificateValidUntil), 
                new org.web3j.abi.datatypes.Utf8String(_firstName), 
                new org.web3j.abi.datatypes.Utf8String(_lastName), 
                new org.web3j.abi.datatypes.generated.Uint256(_birthDate), 
                new org.web3j.abi.datatypes.Utf8String(_nationality)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> withdrawalAddressFixed() {
        Function function = new Function("withdrawalAddressFixed", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> revokedOn(String param0) {
        Function function = new Function("revokedOn", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> bytes32ToString(byte[] _bytes32) {
        Function function = new Function("bytes32ToString", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_bytes32)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple9<String, BigInteger, String, String, BigInteger, String, BigInteger, BigInteger, String>> verification(String param0) {
        final Function function = new Function("verification", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple9<String, BigInteger, String, String, BigInteger, String, BigInteger, BigInteger, String>>(
                new Callable<Tuple9<String, BigInteger, String, String, BigInteger, String, BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple9<String, BigInteger, String, String, BigInteger, String, BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple9<String, BigInteger, String, String, BigInteger, String, BigInteger, BigInteger, String>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (String) results.get(8).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> changeOwnerAccept() {
        Function function = new Function(
                "changeOwnerAccept", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<byte[]> firstName(String param0) {
        Function function = new Function("firstName", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> removeManager(String manager) {
        Function function = new Function(
                "removeManager", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(manager)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdrawAllToWithdrawalAddress() {
        Function function = new Function(
                "withdrawAllToWithdrawalAddress", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> stringToSignExample() {
        Function function = new Function("stringToSignExample", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<byte[]> stringToBytes32(String source) {
        Function function = new Function("stringToBytes32", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(source)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<String> addressAttached(byte[] param0) {
        Function function = new Function("addressAttached", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes20(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> revokeVerification(String _acc) {
        Function function = new Function(
                "revokeVerification", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_acc)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<byte[]> nationality(String param0) {
        Function function = new Function("nationality", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<String> withdrawalAddress() {
        Function function = new Function("withdrawalAddress", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> isManager(String param0) {
        Function function = new Function("isManager", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> setPriceForVerification(BigInteger priceInWei) {
        Function function = new Function(
                "setPriceForVerification", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(priceInWei)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<CryptonomicaVerification> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CryptonomicaVerification.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CryptonomicaVerification> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CryptonomicaVerification.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static CryptonomicaVerification load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CryptonomicaVerification(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static CryptonomicaVerification load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CryptonomicaVerification(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class SignedStringUploadedEventResponse {
        public String fromAccount;

        public String fingerprint;

        public String uploadedString;
    }

    public static class VerificationAddedEventResponse {
        public String verifiedAccount;

        public String forFingerprint;

        public String verificationAddedByAccount;
    }

    public static class VerificationRevokedEventResponse {
        public String revocedforAccount;

        public String revokedBy;

        public String withFingerprint;

        public BigInteger revokedOnUnixTime;
    }

    public static class ChangeOwnerStartedEventResponse {
        public String startedBy;

        public String newOwner;
    }

    public static class OwnerChangedEventResponse {
        public String from;

        public String to;
    }

    public static class ManagerAddedEventResponse {
        public String added;

        public String addedBy;
    }

    public static class ManagerRemovedEventResponse {
        public String removed;

        public String removedBy;
    }

    public static class PriceChangedEventResponse {
        public String changedBy;

        public BigInteger from;

        public BigInteger to;
    }

    public static class WithdrawalEventResponse {
        public String to;

        public String by;

        public BigInteger sumInWei;

        public Boolean success;
    }

    public static class WithdrawalAddressChangedEventResponse {
        public String from;

        public String to;

        public String changedBy;
    }

    public static class WithdrawalAddressFixedEventResponse {
        public String withdrawalAddressFixedAs;

        public String fixedBy;
    }
}
