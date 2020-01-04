package zw.co.cryptosine.rava.repository;

import com.bigchaindb.api.AssetsApi;
import com.bigchaindb.builders.BigchainDbConfigBuilder;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.model.Asset;
import com.bigchaindb.model.Assets;
import com.bigchaindb.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import zw.co.cryptosine.rava.model.RavaQuestion;

import java.io.IOException;
import java.security.KeyPair;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RavaQuestionRepositoryImpl implements RavaQuestionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RavaQuestionRepository.class);

    private static final String publicKey = "302a300506032b657003210033c43dc2180936a2a9138a05f06c892d2fb1cfda4562cbc35373bf13cd8ed373";
    private static final String privateKey = "302e020100300506032b6570042204206f6b0cd095f1e83fc5f08bffb79c7c8a30e77a3ab65f4bc659026b76394fcea8";


    private KeyPair keyPair;

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    private Environment environment;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public RavaQuestionRepositoryImpl() {
        buildKeys();
        BigchainDbConfigBuilder
                .baseUrl("http://localhost:9984/")
                .setup();
    }

    @Override
    public RavaQuestion save(RavaQuestion ravaQuestion) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> assetData = mapper.convertValue(ravaQuestion, Map.class);
        try {
            Transaction createTransaction = BigchainDbTransactionBuilder
                    .init()
                    .addAssets(assetData, TreeMap.class)
                    .operation(Operations.CREATE)
                    .buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
                    .sendTransaction();
            if(LOGGER.isDebugEnabled()){
                LOGGER.info("transaction created {} \n{}",createTransaction.toString());
            }
        } catch (IOException e) {
            if(LOGGER.isDebugEnabled()){
                LOGGER.error("{}",e);
            }
        }
        return ravaQuestion;
    }

    @Override
    public List<RavaQuestion> getWithSearchKey(String searchKey) {
        List<RavaQuestion> ravaQuestionList = new ArrayList<>();
        try {
            Assets assets = AssetsApi.getAssets(searchKey);
            List<Asset> assetsList = assets.getAssets();
            assetsList.forEach(asset -> {
                try{
                    if(LOGGER.isDebugEnabled()){
                        LOGGER.info(" ASSET =>\n {} ",tojson(asset.getData()));
                    }
                    RavaQuestion ravaQuestion = stringToObject(tojson(asset.getData()),RavaQuestion.class);//(RavaQuestion)asset.getData();
                    ravaQuestionList.add(ravaQuestion);
                }catch (Exception e){
                    if(LOGGER.isDebugEnabled()){
                        LOGGER.error("not instance of rava {}",e.getMessage());
                    }
                }
            });
        } catch (IOException e) {
            if(LOGGER.isDebugEnabled()){
                e.printStackTrace();
            }
        }
        if(LOGGER.isDebugEnabled()){
            LOGGER.info("result list size {}",ravaQuestionList.size());
        }
        ravaQuestionList.forEach(ravaQuestion -> {
            LOGGER.info(ravaQuestion.toString());
        });
        return ravaQuestionList;
    }

    private void buildKeys(){
        try{
           /* Path pathPvt = Paths.get("/data/rava/keys/ravaapp.key");
            Path pathPub = Paths.get("/data/rava/keys/ravaapp.pub");
            byte[] publicKeyBytes = Files.readAllBytes(pathPub);
            byte[] privateKeyBytes=Files.readAllBytes(pathPvt);
            EdDSAPublicKeySpec ed25519spec = new EdDSAPublicKeySpec(publicKeyBytes, EdDSANamedCurveTable.getByName("Ed25519"));
            EdDSAPublicKey ed25519PublicKey = new EdDSAPublicKey(ed25519spec);*/
            byte[] TEST_PUBKEY = Utils.hexToBytes(publicKey);
            byte[] TEST_PRIVKEY=Utils.hexToBytes(privateKey);
            X509EncodedKeySpec encoded = new X509EncodedKeySpec(TEST_PUBKEY);
            EdDSAPublicKey keyIn = new EdDSAPublicKey(encoded);
            PKCS8EncodedKeySpec encodedP = new PKCS8EncodedKeySpec(TEST_PRIVKEY);
            EdDSAPrivateKey keyPr = new EdDSAPrivateKey(encodedP);
            setKeyPair(new KeyPair(keyIn,keyPr));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private String tojson(Object obj){
        ObjectMapper mapper=new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
        return "";

        }
    }

    public static <T> T stringToObject(String s, Class<T> clazz) {
        T arr = new Gson().fromJson(s, clazz);
        return arr; //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

}
