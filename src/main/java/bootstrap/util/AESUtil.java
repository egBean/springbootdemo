package bootstrap.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


/**
 * AES工具类
 * time: 2018/1/31 18:21.
 *
 * @author zhangzhen
 */
public class AESUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtil.class);

    private static String AES = "AES";

    private static Integer keyLength = 16;

    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        if (key.length != keyLength.intValue()) {
            throw new RuntimeException("key必须为16位长度");
        } else {
            SecretKeySpec e = new SecretKeySpec(key, AES);
            byte[] enCodeFormat = e.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, seckey);
            return cipher.doFinal(data);
        }
    }

    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        if (key.length != keyLength.intValue()) {
            throw new RuntimeException("key必须为16位长度");
        } else {
            SecretKeySpec e = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = e.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, seckey);
            return cipher.doFinal(data);
        }
    }

    /**
     * 按照 AES/ECB/PKCS5Padding 方式加密，使用128位密钥，加密数据
     *
     * @param data 要加密的明文字符串
     * @param key  加密key，必须为16位
     * @return
     */
    public static String encrypt128ToBase64(String data, String key) {
        try {
            byte[] e = encrypt(data.getBytes("UTF-8"), key.getBytes("UTF-8"));
            return new String(Base64.getEncoder().encode(e));
        } catch (Exception ex) {
            LOGGER.error("AES加密失败:", ex);
            return null;
        }
    }

    /**
     * 按照 AES/ECB/PKCS5Padding 方式解密，使用128位密钥，加密数据
     *
     * @param data 要解密的密文
     * @param key  解密key，必须为16位
     * @return
     */
    public static String decrypt128FromBase64(String data, String key) {
        try {
            byte[] e = Base64.getDecoder().decode(data.getBytes());
            byte[] valueByte = decrypt(e, key.getBytes("UTF-8"));
            return new String(valueByte, "UTF-8");
        } catch (Exception ex) {
            LOGGER.error("AES解密失败:", ex);
            return null;
        }
    }

    public static void main(String[] args) {
        String key = "fdsafafdsafafdsb";
        String s = encrypt128ToBase64("helloworld", key);
        System.out.println(s);
        System.out.println(decrypt128FromBase64(s,key));
    }
}
