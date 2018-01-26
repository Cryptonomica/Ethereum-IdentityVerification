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
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple8;
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
 * <p>Generated with web3j version 3.1.1.
 */
public final class CryptonomicaVerification extends Contract {
    private static final String BINARY = "60606040526011805460a060020a60ff0219169055341561001f57600080fd5b600e8054600160a060020a033316600160a060020a031991821681179092556000828152600f60205260409020805460ff19166001179055601180549091169091179055612525806100726000396000f3006060604052600436106101d75763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166302c2934981146101dc57806309905bdb1461020f5780630a29f5081461023457806321b8092e1461027a57806322652e05146102995780632724c5161461032f5780632d06177a1461036157806334a00b271461038057806339b51e7c1461039f57806345d6f02e146103be57806347889c42146103dd5780634978ac3f146103fc578063546e19591461048f5780635569ca2d146104ae5780635b3d0bc1146105a05780635e57966d146105bf57806361b027b0146105de57806365455fdc146105fd57806376e57d4b1461061c578063836afead1461062f5780638da5cb5b1461064e5780639201de551461066157806392b4e132146106775780639538833c146108af5780639a159bf6146108c25780639b5522a6146108e1578063ac18de43146108f4578063b17a98b614610913578063be89d38514610926578063cc29f81114610977578063cfb5192814610a4c578063d61a86bf14610a9d578063ec94526914610abc578063ee3e469d14610adb578063f2bcd02214610b2c578063f3ae241514610b3f578063f883e47814610b5e578063fb20dc8014610bbd575b600080fd5b34156101e757600080fd5b6101fb600160a060020a0360043516610bd3565b604051901515815260200160405180910390f35b341561021a57600080fd5b610222610ca0565b60405190815260200160405180910390f35b6101fb60046024813581810190830135806020601f82018190048102016040519081016040528181529291906020840183838082843750949650610ca695505050505050565b341561028557600080fd5b6101fb600160a060020a0360043516610dfe565b34156102a457600080fd5b6102b8600160a060020a0360043516610ea5565b60405160208082528190810183818151815260200191508051906020019080838360005b838110156102f45780820151838201526020016102dc565b50505050905090810190601f1680156103215780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561033a57600080fd5b610345600435610f55565b604051600160a060020a03909116815260200160405180910390f35b341561036c57600080fd5b6101fb600160a060020a0360043516610f70565b341561038b57600080fd5b610222600160a060020a0360043516610fec565b34156103aa57600080fd5b610222600160a060020a0360043516610ffe565b34156103c957600080fd5b610222600160a060020a0360043516611010565b34156103e857600080fd5b610222600160a060020a0360043516611022565b341561040757600080fd5b6102b860046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052818152929190602084018383808284375094965061103495505050505050565b341561049a57600080fd5b6101fb600160a060020a036004351661105d565b34156104b957600080fd5b6101fb60048035600160a060020a03169060248035919060649060443590810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052818152929190602084018383808284378201915050505050509190803590602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405281815292919060208401838380828437509496506110ad95505050505050565b34156105ab57600080fd5b610222600160a060020a03600435166114d6565b34156105ca57600080fd5b6102b8600160a060020a03600435166114e8565b34156105e957600080fd5b610222600160a060020a036004351661153b565b341561060857600080fd5b610222600160a060020a036004351661154d565b341561062757600080fd5b6101fb61155f565b341561063a57600080fd5b610222600160a060020a036004351661156f565b341561065957600080fd5b610345611581565b341561066c57600080fd5b6102b8600435611590565b341561068257600080fd5b610696600160a060020a0360043516611618565b604051602081018890526080810185905260c0810183905260e081018290526101008082528954600260001960018316158402019091160490820181905281906040820190606083019060a08401906101208501908e90801561073a5780601f1061070f5761010080835404028352916020019161073a565b820191906000526020600020905b81548152906001019060200180831161071d57829003601f168201915b505085810384528b54600260001961010060018416150201909116048082526020909101908c9080156107ae5780601f10610783576101008083540402835291602001916107ae565b820191906000526020600020905b81548152906001019060200180831161079157829003601f168201915b505085810383528a54600260001961010060018416150201909116048082526020909101908b9080156108225780601f106107f757610100808354040283529160200191610822565b820191906000526020600020905b81548152906001019060200180831161080557829003601f168201915b50508581038252885460026000196101006001841615020190911604808252602090910190899080156108965780601f1061086b57610100808354040283529160200191610896565b820191906000526020600020905b81548152906001019060200180831161087957829003601f168201915b50509c5050505050505050505050505060405180910390f35b34156108ba57600080fd5b6101fb61164f565b34156108cd57600080fd5b610222600160a060020a03600435166116e2565b34156108ec57600080fd5b6102b86116f4565b34156108ff57600080fd5b6101fb600160a060020a036004351661170a565b341561091e57600080fd5b6101fb611783565b341561093157600080fd5b6102b860046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094965061186a95505050505050565b341561098257600080fd5b6102b860046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528181529291906020840183838082843750949650611bfa95505050505050565b3415610a5757600080fd5b61022260046024813581810190830135806020601f82018190048102016040519081016040528181529291906020840183838082843750949650611d1595505050505050565b3415610aa857600080fd5b6102b8600160a060020a0360043516611d22565b3415610ac757600080fd5b610222600160a060020a0360043516611d9f565b3415610ae657600080fd5b61034560046024813581810190830135806020601f82018190048102016040519081016040528181529291906020840183838082843750949650611db195505050505050565b3415610b3757600080fd5b610345611ddc565b3415610b4a57600080fd5b6101fb600160a060020a0360043516611deb565b3415610b6957600080fd5b6101fb60048035600160a060020a03169060446024803590810190830135806020601f82018190048102016040519081016040528181529291906020840183838082843750949650611e0095505050505050565b3415610bc857600080fd5b6101fb600435612010565b600e5460009033600160a060020a03908116911614610bf157600080fd5b601154600160a060020a03838116911614610c0b57600080fd5b60115460a060020a900460ff1615610c2257600080fd5b6011805474ff0000000000000000000000000000000000000000191660a060020a17908190557fff51cf04e7fbddffc521b8673e9282b10a91c659c12eea9ef99182bd9a95ff7190600160a060020a031633604051600160a060020a039283168152911660208201526040908101905180910390a15060015b919050565b60105481565b6000601054341015610cb757600080fd5b600160a060020a0333166000908152600a60205260409020541515610cdb57600080fd5b600160a060020a0333166000908152600b602052604090205415610cfe57600080fd5b600160a060020a0333166000908152600c60205260409020828051610d2792916020019061243c565b50600160a060020a0333166000818152600b60209081526040808320429055600c909152908190207fc8a9806458025c6b39a3365b2711f67211bc9c98672493f2f2119b0b6ace90f7915160208082528254600260001961010060018416150201909116049082018190528190604082019084908015610de85780601f10610dbd57610100808354040283529160200191610de8565b820191906000526020600020905b815481529060010190602001808311610dcb57829003601f168201915b50509250505060405180910390a2506001919050565b600e5460009033600160a060020a03908116911614610e1c57600080fd5b60115460a060020a900460ff1615610e3357600080fd5b601154600160a060020a033381169184821691167f15fc95cfd5d20b9661cf80c2719d8e3180bee6d89379a03b7b14ca6ac6adea9860405160405180910390a45060118054600160a060020a03831673ffffffffffffffffffffffffffffffffffffffff199091161790556001919050565b600c6020528060005260406000206000915090508054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610f4d5780601f10610f2257610100808354040283529160200191610f4d565b820191906000526020600020905b815481529060010190602001808311610f3057829003601f168201915b505050505081565b600160205260009081526040902054600160a060020a031681565b600e5460009033600160a060020a03908116911614610f8e57600080fd5b600160a060020a038083166000818152600f602052604090819020805460ff1916600117905533909216917f05a4006f300442cf8b7fdb885f5ee958812020bffb5c5a8e655fde64e5f987ed905160405180910390a3506001919050565b600a6020526000908152604090205481565b60076020526000908152604090205481565b600b6020526000908152604090205481565b60056020526000908152604090205481565b61103c6124b6565b6110566110488461208f565b6110518461208f565b6120b7565b9392505050565b600e5460009033600160a060020a0390811691161461107b57600080fd5b5060128054600160a060020a03831673ffffffffffffffffffffffffffffffffffffffff199091161790556001919050565b600160a060020a0333166000908152600f602052604081205460ff1615156110d457600080fd5b6020855111156110e357600080fd5b6020845111156110f257600080fd5b815160021461110057600080fd5b600160a060020a0387166000908152600b6020526040902054151561112457600080fd5b600160a060020a0387166000908152600760205260409020541561114757600080fd5b600160a060020a03871660009081526002602081815260408084208a9055600d909152909120600181018890550185805161118692916020019061243c565b5061119085611d15565b600160a060020a038816600090815260036020818152604080842094909455600d9052919020018480516111c892916020019061243c565b506111d284611d15565b600160a060020a0388166000908152600460208181526040808420949094556005808252848420889055600d909152929091209081018590550182805161121d92916020019061243c565b5061122782611d15565b600160a060020a038816600081815260066020818152604080842095909555600781528483204290819055600d909152918490209081019190915590915180828054600181600116156101000203166002900480156112bd5780601f1061129b5761010080835404028352918201916112bd565b820191906000526020600020905b8154815290600101906020018083116112a9575b5050915050604051908190039020600160a060020a038916600090815260026020818152604080842054600d80845282862060058086529684902054919094527f2d409d50cee634403fd5b6ab17fdecb0f3cff81c7b05d1b7dc24c04e1515387495919484019360038101939192019033905186815260608101849052600160a060020a03821660a082015260c060208201818152875460026000196101006001841615020190911604918301829052906040830190608084019060e08501908a9080156113cc5780601f106113a1576101008083540402835291602001916113cc565b820191906000526020600020905b8154815290600101906020018083116113af57829003601f168201915b50508481038352885460026000196101006001841615020190911604808252602090910190899080156114405780601f1061141557610100808354040283529160200191611440565b820191906000526020600020905b81548152906001019060200180831161142357829003601f168201915b50508481038252865460026000196101006001841615020190911604808252602090910190879080156114b45780601f10611489576101008083540402835291602001916114b4565b820191906000526020600020905b81548152906001019060200180831161149757829003601f168201915b5050995050505050505050505060405180910390a35060019695505050505050565b60026020526000908152604090205481565b6114f06124b6565b61153560408051908101604052600281527f3078000000000000000000000000000000000000000000000000000000000000602082015261153084612123565b611034565b92915050565b60006020819052908152604090205481565b60046020526000908152604090205481565b60115460a060020a900460ff1681565b60086020526000908152604090205481565b600e54600160a060020a031681565b6115986124b6565b6115a06124b6565b600060206040518059106115b15750595b818152601f19601f8301168101602001604052905091505b6020811015611611578381602081106115de57fe5b1a60f860020a028282815181106115f157fe5b906020010190600160f860020a031916908160001a9053506001016115c9565b5092915050565b600d602052600090815260409020600181015460048201546006830154600784015460028501926003860192909160058701919088565b60125460009033600160a060020a0390811691161461166d57600080fd5b601254600e54600160a060020a0391821691167fb532073b38c83145e3e5135377a08bf9aab55bc0fd7c1179cd4fb995d2a5159c60405160405180910390a350601254600e805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0390921691909117905560015b90565b60036020526000908152604090205481565b6116fc6124b6565b611705336114e8565b905090565b600e5460009033600160a060020a0390811691161461172857600080fd5b600160a060020a038083166000818152600f602052604090819020805460ff1916905533909216917f3e902a6ee93dd5b2d48bd1009c7701a481be512b1ef73dbed2f95ea44c59ea88905160405180910390a3506001919050565b601154600090600160a060020a03308116803192909116903180156108fc0290604051600060405180830381858888f19350505050151561181457601154600160a060020a0333811691167f78746de4b42c369479b14075849ee3378535cb810d96e74712e26a7924f7b021836000604051918252151560208201526040908101905180910390a360009150611866565b601154600160a060020a0333811691167f78746de4b42c369479b14075849ee3378535cb810d96e74712e26a7924f7b021836001604051918252151560208201526040908101905180910390a3600191505b5090565b6118726124b6565b61187a6124b6565b6118826124b6565b61188a6124b6565b600160a060020a0333166000908152600a6020526040812054156118ad57600080fd5b600160a060020a0333166000908152600a6020526040902042905585516028146118d657600080fd5b606060405190810160405280602181526020017f492068657265627920636f6e6669726d2074686174207468652061646472657381526020017f7300000000000000000000000000000000000000000000000000000000000000815250935061193d6116f4565b925060408051908101604052601681527f6973206d7920457468657265756d20616464726573730000000000000000000060208201529150611980848484611bfa565b600160a060020a03331660009081526009602052604090209080516119a992916020019061243c565b506119b386611d15565b600160a060020a03331660008181526020818152604080832085905584835260018252808320805473ffffffffffffffffffffffffffffffffffffffff191685179055928252600d905220909150868051611a1292916020019061243c565b50856040518082805190602001908083835b60208310611a435780518252601f199092019160209182019101611a24565b6001836020036101000a0380198251168184511617909252505050919091019250604091505051908190039020600160a060020a033316600081815260096020526040908190207f3937ba338b899c5febd54744a75928d84dddd9017746f3fe421412cc51875528915160208082528254600260001961010060018416150201909116049082018190528190604082019084908015611b235780601f10611af857610100808354040283529160200191611b23565b820191906000526020600020905b815481529060010190602001808311611b0657829003601f168201915b50509250505060405180910390a36009600033600160a060020a0316600160a060020a031681526020019081526020016000208054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611bea5780601f10611bbf57610100808354040283529160200191611bea565b820191906000526020600020905b815481529060010190602001808311611bcd57829003601f168201915b5050505050945050505050919050565b611c026124b6565b611c0a6124c8565b611c126124b6565b611c1a6124b6565b611c5660408051908101604052600181527f2000000000000000000000000000000000000000000000000000000000000000602082015261208f565b92506003604051805910611c675750595b908082528060200260200182016040528015611c9d57816020015b611c8a6124c8565b815260200190600190039081611c825790505b509150611ca98761208f565b82600081518110611cb657fe5b60209081029091010152611cc98661208f565b82600181518110611cd657fe5b60209081029091010152611ce98561208f565b82600281518110611cf657fe5b60209081029091010152611d0a8383612247565b979650505050505050565b6000602082015192915050565b60096020528060005260406000206000915090508054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610f4d5780601f10610f2257610100808354040283529160200191610f4d565b60066020526000908152604090205481565b600080611dbd83611d15565b600090815260016020526040902054600160a060020a03169392505050565b601154600160a060020a031681565b600f6020526000908152604090205460ff1681565b600082600160a060020a031633600160a060020a03161480611e3a5750600160a060020a0333166000908152600f602052604090205460ff165b1515611e4557600080fd5b8151601414611e5357600080fd5b816040518082805190602001908083835b60208310611e835780518252601f199092019160209182019101611e64565b6001836020036101000a0380198251168184511617909252505050919091019250604091505051908190039020600160a060020a0384166000908152600d60205260409081902090518082805460018160011615610100020316600290048015611f245780601f10611f02576101008083540402835291820191611f24565b820191906000526020600020905b815481529060010190602001808311611f10575b505091505060405190819003902014611f3c57600080fd5b600160a060020a0380841660009081526008602090815260408083204290819055600d909252918290206007015533909116908390518082805190602001908083835b60208310611f9e5780518252601f199092019160209182019101611f7f565b6001836020036101000a0380198251168184511617909252505050919091019250604091505051809103902084600160a060020a03167fc6f2b8565550ea0e6941e2f0f6b7e65e5eb1fdccb33e0c7815af0f3ce5669cff4260405190815260200160405180910390a450600192915050565b600160a060020a0333166000908152600f6020526040812054819060ff16151561203957600080fd5b506010805490839055600160a060020a0333167f665d155f71ad96c4a04629d54ef9fb27ef57911253588f2ee93474cd02fa3f53828560405191825260208201526040908101905180910390a250600192915050565b6120976124c8565b602082016040805190810160405280845181526020019190915292915050565b6120bf6124b6565b6120c76124b6565b600083518551016040518059106120db5750595b818152601f19601f830116810160200160405290509150602082019050612108818660200151875161237b565b61211b855182018560200151865161237b565b509392505050565b61212b6124b6565b6121336124b6565b60008060008060286040518059106121485750595b818152601f19601f830116810160200160405290509450600093505b601484101561223c578360130360080260020a87600160a060020a031681151561218a57fe5b0460f860020a02925060108360f860020a900460ff168115156121a957fe5b0460f860020a0291508160f860020a90046010028360f860020a90040360f860020a0290506121d7826123c0565b8585600202815181106121e657fe5b906020010190600160f860020a031916908160001a905350612207816123c0565b85856002026001018151811061221957fe5b906020010190600160f860020a031916908160001a905350600190930192612164565b509295945050505050565b61224f6124b6565b60008061225a6124b6565b60008551151561227a576020604051908101604052600081529450612371565b60018651038751029350600092505b85518310156122b85785838151811061229e57fe5b906020019060200201515190930192600190920191612289565b836040518059106122c65750595b818152601f19601f8301168101602001604052905060009350915050602081015b855183101561236d5761232a8187858151811061230057fe5b906020019060200201516020015188868151811061231a57fe5b906020019060200201515161237b565b85838151811061233657fe5b90602001906020020151510160018651038310156123625761235e818860200151895161237b565b8651015b6001909201916122e7565b8194505b5050505092915050565b60005b602082106123a1578251845260208401935060208301925060208203915061237e565b6001826020036101000a03905080198351168185511617909352505050565b60007f0a000000000000000000000000000000000000000000000000000000000000007fff0000000000000000000000000000000000000000000000000000000000000083161015612424578160f860020a900460300160f860020a029050610c9b565b8160f860020a900460570160f860020a029050610c9b565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061247d57805160ff19168380011785556124aa565b828001600101855582156124aa579182015b828111156124aa57825182559160200191906001019061248f565b506118669291506124df565b60206040519081016040526000815290565b604080519081016040526000808252602082015290565b6116df91905b8082111561186657600081556001016124e55600a165627a7a72305820925176172556a96ec5521034ec15700f57bdee2e76597b2fd91505dab963b21f0029";

    private CryptonomicaVerification(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private CryptonomicaVerification(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<StringToSignRequestedEventResponse> getStringToSignRequestedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("StringToSignRequested", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<StringToSignRequestedEventResponse> responses = new ArrayList<StringToSignRequestedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            StringToSignRequestedEventResponse typedResponse = new StringToSignRequestedEventResponse();
            typedResponse.forAccount = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.forKeyFingerpint = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.stringToSignProvided = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<StringToSignRequestedEventResponse> stringToSignRequestedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("StringToSignRequested", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, StringToSignRequestedEventResponse>() {
            @Override
            public StringToSignRequestedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                StringToSignRequestedEventResponse typedResponse = new StringToSignRequestedEventResponse();
                typedResponse.forAccount = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.forKeyFingerpint = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.stringToSignProvided = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<SignedStringUploadedEventResponse> getSignedStringUploadedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("SignedStringUploaded", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<SignedStringUploadedEventResponse> responses = new ArrayList<SignedStringUploadedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            SignedStringUploadedEventResponse typedResponse = new SignedStringUploadedEventResponse();
            typedResponse.fromAccount = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.uploadedString = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SignedStringUploadedEventResponse> signedStringUploadedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("SignedStringUploaded", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, SignedStringUploadedEventResponse>() {
            @Override
            public SignedStringUploadedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                SignedStringUploadedEventResponse typedResponse = new SignedStringUploadedEventResponse();
                typedResponse.fromAccount = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.uploadedString = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<VerificationAddedEventResponse> getVerificationAddedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("VerificationAdded", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<VerificationAddedEventResponse> responses = new ArrayList<VerificationAddedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            VerificationAddedEventResponse typedResponse = new VerificationAddedEventResponse();
            typedResponse.forFingerprint = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.verifiedAccount = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.keyCertificateValidUntilUnixTime = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.userFirstName = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.userLastName = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.userBirthDate = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.userNationality = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.verificationAddedByAccount = (String) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<VerificationAddedEventResponse> verificationAddedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("VerificationAdded", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, VerificationAddedEventResponse>() {
            @Override
            public VerificationAddedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                VerificationAddedEventResponse typedResponse = new VerificationAddedEventResponse();
                typedResponse.forFingerprint = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.verifiedAccount = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.keyCertificateValidUntilUnixTime = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.userFirstName = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.userLastName = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.userBirthDate = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.userNationality = (String) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.verificationAddedByAccount = (String) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public List<VerificationRevokedEventResponse> getVerificationRevokedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("VerificationRevoked", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<VerificationRevokedEventResponse> responses = new ArrayList<VerificationRevokedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            VerificationRevokedEventResponse typedResponse = new VerificationRevokedEventResponse();
            typedResponse.revocedforAccount = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.withFingerprint = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.revokedBy = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.revokedOnUnixTime = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<VerificationRevokedEventResponse> verificationRevokedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("VerificationRevoked", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, VerificationRevokedEventResponse>() {
            @Override
            public VerificationRevokedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                VerificationRevokedEventResponse typedResponse = new VerificationRevokedEventResponse();
                typedResponse.revocedforAccount = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.withFingerprint = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.revokedBy = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.revokedOnUnixTime = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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

    public RemoteCall<BigInteger> priceForVerificationInWei() {
        Function function = new Function("priceForVerificationInWei", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> uploadSignedString(String _signedString, BigInteger weiValue) {
        Function function = new Function(
                "uploadSignedString", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_signedString)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
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

    public RemoteCall<String> bytes32FingerprintToAddress(byte[] param0) {
        Function function = new Function("bytes32FingerprintToAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> addManager(String acc) {
        Function function = new Function(
                "addManager", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(acc)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> stringToSignRequestedOnUnixTime(String param0) {
        Function function = new Function("stringToSignRequestedOnUnixTime", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteCall<String> stringsConcatenation(String str1, String str2) {
        Function function = new Function("stringsConcatenation", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(str1), 
                new org.web3j.abi.datatypes.Utf8String(str2)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> changeOwnerStart(String _newOwner) {
        Function function = new Function(
                "changeOwnerStart", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addVerificationData(String acc, BigInteger _keyCertificateValidUntil, String _firstName, String _lastName, BigInteger _birthDate, String _nationality) {
        Function function = new Function(
                "addVerificationData", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(acc), 
                new org.web3j.abi.datatypes.generated.Uint256(_keyCertificateValidUntil), 
                new org.web3j.abi.datatypes.Utf8String(_firstName), 
                new org.web3j.abi.datatypes.Utf8String(_lastName), 
                new org.web3j.abi.datatypes.generated.Uint256(_birthDate), 
                new org.web3j.abi.datatypes.Utf8String(_nationality)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> keyCertificateValidUntil(String param0) {
        Function function = new Function("keyCertificateValidUntil", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> addressToString(String _address) {
        Function function = new Function("addressToString", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<byte[]> fingerprint(String param0) {
        Function function = new Function("fingerprint", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<byte[]> lastName(String param0) {
        Function function = new Function("lastName", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
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

    public RemoteCall<Tuple8<String, BigInteger, String, String, BigInteger, String, BigInteger, BigInteger>> verification(String param0) {
        final Function function = new Function("verification", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple8<String, BigInteger, String, String, BigInteger, String, BigInteger, BigInteger>>(
                new Callable<Tuple8<String, BigInteger, String, String, BigInteger, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple8<String, BigInteger, String, String, BigInteger, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple8<String, BigInteger, String, String, BigInteger, String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue());
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

    public RemoteCall<String> messageSenderAddressToString() {
        Function function = new Function("messageSenderAddressToString", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<TransactionReceipt> requestStringToSignWithKey(String _fingerprint) {
        Function function = new Function(
                "requestStringToSignWithKey", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_fingerprint)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> stringsJoin(String str1, String str2, String str3) {
        Function function = new Function("stringsJoin", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(str1), 
                new org.web3j.abi.datatypes.Utf8String(str2), 
                new org.web3j.abi.datatypes.Utf8String(str3)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<byte[]> stringToBytes32(String source) {
        Function function = new Function("stringToBytes32", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(source)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<String> stringToSign(String param0) {
        Function function = new Function("stringToSign", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<byte[]> nationality(String param0) {
        Function function = new Function("nationality", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<String> getAddressConnectedToFingerprint(String _fingerprint) {
        Function function = new Function("getAddressConnectedToFingerprint", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_fingerprint)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<TransactionReceipt> revokeVerification(String acc, String _fingerprint) {
        Function function = new Function(
                "revokeVerification", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(acc), 
                new org.web3j.abi.datatypes.Utf8String(_fingerprint)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public static class StringToSignRequestedEventResponse {
        public String forAccount;

        public byte[] forKeyFingerpint;

        public String stringToSignProvided;
    }

    public static class SignedStringUploadedEventResponse {
        public String fromAccount;

        public String uploadedString;
    }

    public static class VerificationAddedEventResponse {
        public byte[] forFingerprint;

        public String verifiedAccount;

        public BigInteger keyCertificateValidUntilUnixTime;

        public String userFirstName;

        public String userLastName;

        public BigInteger userBirthDate;

        public String userNationality;

        public String verificationAddedByAccount;
    }

    public static class VerificationRevokedEventResponse {
        public String revocedforAccount;

        public byte[] withFingerprint;

        public String revokedBy;

        public BigInteger revokedOnUnixTime;
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