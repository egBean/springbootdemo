package bootstrap.util;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class RSAUtil {


    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    //获得公钥字符串
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encodeBASE64(key.getEncoded());
    }

    //获得私钥字符串
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encodeBASE64(key.getEncoded());
    }

    //按base64字符集将字符转为byte[]
    private static byte[] decodeBASE64(String key) throws Exception {
        return new BASE64Decoder().decodeBuffer(key);
    }

    //按base64字符集将byte[]转为字符串
    private static String encodeBASE64(byte[] key) throws Exception {
        return new BASE64Encoder().encodeBuffer(key);
    }

    //map对象中存放公私钥
    public static Map<String, Object> initKey() throws Exception {

        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom secureRandom = new SecureRandom();
        //获得对象 KeyPairGenerator 参数 RSA 512个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(512, secureRandom);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


    //获取公钥对象
    private static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] publicBytes = decodeBASE64(publicKey);
        // 构造PKCS8EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        return KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(keySpec);
    }

    //获取私钥对象
    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] privateBytes = decodeBASE64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateBytes);
        return KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(pkcs8KeySpec);
    }

    /**
     * 私钥生成签名
     *
     * @param privateKey
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String sign(String privateKey, byte[] bytes) throws Exception {
        PrivateKey privateKeyObj = getPrivateKey(privateKey);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKeyObj);
        signature.update(bytes);
        return encodeBASE64(signature.sign());
    }

    /**
     * 公钥验签
     *
     * @param publicKey
     * @param bytes
     * @return
     * @throws Exception
     */
    public static boolean verify(String publicKey, String sign, byte[] bytes) throws Exception {
        PublicKey publicKeyObj = getPublicKey(publicKey);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKeyObj);
        signature.update(bytes);
        return signature.verify(decodeBASE64(sign));
    }

    /**
     * 公钥加密
     *
     * @param bytes
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(byte[] bytes, String publicKey) throws Exception {
        PublicKey publicKeyObj = getPublicKey(publicKey);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKeyObj);
        byte[] output = cipher.doFinal(bytes);
        return encodeBASE64(output);
    }

    public static byte[] decryptByPirvateKey(String msg, String privateKey) throws Exception {

        PrivateKey privateKeyObj = getPrivateKey(privateKey);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKeyObj);

        return cipher.doFinal(decodeBASE64(msg));
    }

    public static void main(String[] args) {
        Map<String, Object> keyMap;
        try {
            keyMap = initKey();
            String publicKey = getPublicKey(keyMap);
            String privateKey = getPrivateKey(keyMap);
            //验签测试
            String temp = "老王";
            String sign = sign(privateKey, temp.getBytes("utf-8"));
            System.out.println(verify(publicKey, sign, temp.getBytes("utf-8")));

            //加密测试
            String temp2 = "hello worldabc";
            String msg = encryptByPublicKey(temp2.getBytes("utf-8"), publicKey);
            byte[] bytes = decryptByPirvateKey(msg, privateKey);
            System.out.println(new String(bytes,"utf-8"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
