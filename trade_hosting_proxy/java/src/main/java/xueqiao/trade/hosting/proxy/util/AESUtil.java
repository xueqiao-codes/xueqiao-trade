package xueqiao.trade.hosting.proxy.util;

import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Base64;
import java.util.Properties;

public class AESUtil {
    /**
     * 16字节长度的密钥，也就是128位
     * 注：密钥长度受限，不能使用256位的，所以使用128位，但可以通过修改系统配置解决
     * https://stackoverflow.com/questions/6481627/java-security-illegal-key-size-or-default-parameters
     * 注意：这个KEY值不要随意改，因为会多个地方都需要同一个KEY
     * */
    static final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("8937507684356724"), "AES");
    /**
     * 固定16字节长度
     * 注意：这个KEY值不要随意改，因为会多个地方都需要同一个KEY
     * */
    static final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("8937507684358673"));
    /**
     * 加密方法/加密模式/填充方式，CBC是安全性好于ECB,适合传输长度长的报文,是SSL、IPSec的标准
     * */
    static final String transform = "AES/CBC/PKCS5Padding";

    /**
     * 加密, 若加密失败，则返回原文
     * @param text 需要加密的明文
     * @return 经过base64加密后的密文
     * */
    public static String encryptSafe(String text) {
        if (StringUtils.isBlank(text)) {
            return "";
        }
        try {
            return encrypt(text);
        } catch (Throwable t) {
            AppLog.e("AESUtil ---- encryptSafe", t);
        }
        return text;
    }

    public static String decryptSafe(String encodedString) {
        if (StringUtils.isBlank(encodedString)) {
            return null;
        }
        try {
            return dencrypt(encodedString);
        } catch (Throwable t) {
            AppLog.e("AESUtil ---- decryptSafe", t);
        }
        return encodedString;
    }

    /**
     * 加密
     * @param text 需要加密的明文
     * @return 经过base64加密后的密文
     * @throws IOException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws ShortBufferException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encrypt(String text) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, BadPaddingException, IllegalBlockSizeException {
        Properties properties = new Properties();
        final ByteBuffer outBuffer;
        final int bufferSize = 1024;
        final int updateBytes;
        final int finalBytes;
        try (CryptoCipher encipher = Utils.getCipherInstance(transform, properties)) {
            ByteBuffer inBuffer = ByteBuffer.allocateDirect(bufferSize);
            outBuffer = ByteBuffer.allocateDirect(bufferSize);
            inBuffer.put(getUTF8Bytes(text));
            inBuffer.flip(); // ready for the cipher to read it
            encipher.init(Cipher.ENCRYPT_MODE, key, iv);
            updateBytes = encipher.update(inBuffer, outBuffer);
            finalBytes = encipher.doFinal(inBuffer, outBuffer);
        }
        outBuffer.flip(); // ready for use as decrypt
        byte[] encoded = new byte[updateBytes + finalBytes];
        outBuffer.duplicate().get(encoded);
        String encodedString = new String(Base64.getEncoder().encode(encoded), StandardCharsets.UTF_8);
        return encodedString;
    }

    /**
     * 解密
     * @param encodedString 经过base64加密后的密文
     * @return 明文
     * @throws IOException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws ShortBufferException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String dencrypt(String encodedString) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, BadPaddingException, IllegalBlockSizeException {
        Properties properties = new Properties();
        final ByteBuffer outBuffer;
        final int bufferSize = 1024;
        ByteBuffer decoded = ByteBuffer.allocateDirect(bufferSize);
        try (CryptoCipher decipher = Utils.getCipherInstance(transform, properties)) {
            decipher.init(Cipher.DECRYPT_MODE, key, iv);
            outBuffer = ByteBuffer.allocateDirect(bufferSize);
            outBuffer.put(Base64.getDecoder().decode(getUTF8Bytes(encodedString)));
            outBuffer.flip();
            decipher.update(outBuffer, decoded);
            decipher.doFinal(outBuffer, decoded);
            decoded.flip(); // ready for use
        }

        return asString(decoded);
    }

    /**
     * 转换 String 为 UTF8 bytes
     */
    private static byte[] getUTF8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 转换 ByteBuffer 为 String
     */
    private static String asString(ByteBuffer buffer) {
        final ByteBuffer copy = buffer.duplicate();
        final byte[] bytes = new byte[copy.remaining()];
        copy.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
