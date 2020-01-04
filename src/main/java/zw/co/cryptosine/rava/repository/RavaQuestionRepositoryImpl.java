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

    private static final Logger LOGGER = LoggerFactory.getLogger(RavaQuestionRepositoryImpl.class);

    private static final String PUBLIC_KEY = "302a300506032b657003210033c43dc2180936a2a9138a05f06c892d2fb1cfda4562cbc35373bf13cd8ed373";
    private static final String PRIVATE_KEY = "302e020100300506032b6570042204206f6b0cd095f1e83fc5f08bffb79c7c8a30e77a3ab65f4bc659026b76394fcea8";


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
                .baseUrl(environment.getProperty("bigchainurl"))
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
                LOGGER.info("transaction created \n{}",createTransaction);
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
                    RavaQuestion ravaQuestion = stringToObject(tojson(asset.getData()),RavaQuestion.class);
                    ravaQuestionList.add(ravaQuestion);
                }catch (Exception e){
                    if(LOGGER.isDebugEnabled()){
                        LOGGER.error("not instance of rava {}",e.getMessage());
                    }
                }
            });
        } catch (IOException e) {
            if(LOGGER.isDebugEnabled()){
                LOGGER.error("{}",e);
            }
        }
        if(LOGGER.isDebugEnabled()){
            LOGGER.info("result list size {}",ravaQuestionList.size());
        }
        ravaQuestionList.forEach(ravaQuestion -> LOGGER.info(ravaQuestion.toString()));
        return ravaQuestionList;
    }

    private void buildKeys(){
        try{
            X509EncodedKeySpec encoded = new X509EncodedKeySpec(Utils.hexToBytes(PUBLIC_KEY));
            EdDSAPublicKey keyIn = new EdDSAPublicKey(encoded);
            PKCS8EncodedKeySpec encodedP = new PKCS8EncodedKeySpec(Utils.hexToBytes(PRIVATE_KEY));
            EdDSAPrivateKey keyPr = new EdDSAPrivateKey(encodedP);
            setKeyPair(new KeyPair(keyIn,keyPr));
        }
        catch (Exception e){
            if(LOGGER.isDebugEnabled()){
                LOGGER.error("{}",e);
            }
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
        return new Gson().fromJson(s, clazz);
    }

}
