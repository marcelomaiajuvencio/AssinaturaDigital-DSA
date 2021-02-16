import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RemetenteAssiDig {
	
	private PublicKey pubKey;
	private PublicKey publicKey;
	private byte[] publicKeyBytes;

    public PublicKey getPubKey() {
          return pubKey;
    }
    
    public PublicKey getPubKey2() {
        return publicKey;
  }

    public void setPubKey(PublicKey pubKey) {
          this.pubKey = pubKey;
    }
    
    public PublicKey getPubKeyByte() {
        return pubKey;
  }


    public byte[] geraAssinatura(String mensagem) throws NoSuchAlgorithmException,
    InvalidKeyException, SignatureException, InvalidKeySpecException {
        Signature sig = Signature.getInstance("DSA");
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        // Geração das chaves púlicas e privadas
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        SecureRandom secRan = new SecureRandom();
        kpg.initialize(512, secRan);
        KeyPair keyP = kpg.generateKeyPair();
        // Gera chave pública
        this.pubKey = keyP.getPublic();
        // Converte chave pública em bytes (para gravar na base, por exemplo)
        this.publicKeyBytes = this.pubKey.getEncoded();
        // Recupera chave pública (da base, por exemplo)
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        this.publicKey = keyFactory.generatePublic(publicKeySpec);
        // Gera chave privada
        PrivateKey priKey = keyP.getPrivate();
        // Converte chave privada em bytes (para gravar na base, por exemplo)
        byte[] privateKeyBytes = priKey.getEncoded();
        // Recupera chave privada (da base, por exemplo)
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        //Inicializando Obj Signature com a Chave Privada
        //sig.initSign(priKey);
        sig.initSign(privateKey);

        //Gerar assinatura
        sig.update(mensagem.getBytes());
        byte[] assinatura = sig.sign();

        return assinatura;
    }

}
