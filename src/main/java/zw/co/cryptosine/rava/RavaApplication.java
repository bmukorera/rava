package zw.co.cryptosine.rava;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.EdDSASecurityProvider;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@SpringBootApplication
public class RavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RavaApplication.class,args);/**/
      /*  net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
        KeyPair keyPair = edDsaKpg.generateKeyPair();*/


/*



try{
    KeyPairGenerator factory = KeyPairGenerator.getInstance("EdDSA", new EdDSASecurityProvider());
    KeyPair keyPair = factory.generateKeyPair();
    byte[] encodedPublicKey = keyPair.getPublic().getEncoded();
    byte[] encodedPrivateKey=keyPair.getPrivate().getEncoded();

    String pubKey= Base64.getEncoder().encodeToString(encodedPublicKey);
    String privateKey=Base64.getEncoder().encodeToString(encodedPrivateKey);

    System.out.println("public key \n\n"+pubKey+"\n\nend public key");

    System.out.println("private key \n\n"+privateKey+"\n\nend private key");
    String outFile = "/data/rava/keys/ravaapp";
    PrintStream out = new PrintStream( new FileOutputStream(outFile + ".key"));
    out.print(pubKey);
    out.close();
    out = new PrintStream(new FileOutputStream(outFile + ".pub"));
    out.println(privateKey);
    out.close();

    try{
      //  Path pathPvt = Paths.get("/data/rava/keys/ravaapp.key");
       // Path pathPub = Paths.get("/data/rava/keys/ravaapp.pub");

        //byte[] publicKeyBytes = Files.readAllBytes(pubKey);
      //  byte[] privateKeyBytes=Files.readAllBytes(pathPvt);


        // decode the base64 encoded string
        byte[] decodedKey = Base64.getDecoder().decode(pubKey);
        // rebuild key using SecretKeySpec
       // SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "EdDSA");

        byte[] decodedPKey=Base64.getDecoder().decode(privateKey);

        X509EncodedKeySpec encoded = new X509EncodedKeySpec(decodedKey);
        EdDSAPublicKey ed25519PublicKey = new EdDSAPublicKey(encoded);


        EdDSAPrivateKeySpec encodedP = new EdDSAPrivateKeySpec(decodedPKey,EdDSANamedCurveTable.getByName("Ed25519"));
        EdDSAPrivateKey edDSAPrivateKey = new EdDSAPrivateKey(encodedP);



        System.out.println(ed25519PublicKey.getAlgorithm());
    }
    catch (Exception e){
        e.printStackTrace();
    }



 //   System.out.println("**********\nalgo --- "+keyPair.getPublic().getAlgorithm()+"\n format --- "+keyPair.getPublic().getFormat());


        }catch (Exception e){
        e.printStackTrace();
    }
*/

    }
}
