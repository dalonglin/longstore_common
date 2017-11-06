package com.longstore.common.util;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.longstore.common.domain.secret.RSAKey;

/**
 * RSA数据加密解密
 */
public class RSAUtils {
	private final static Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);

	public static final String KEY_ALGORITHM = "RSA";
	private static final int KEY_LENG = 1024;
	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_DECRYPT_BLOCK = 128;
	
	/** 取得公钥私钥 */
	public static RSAKey generateRSAKey() {
		try {
			// 获得对象 KeyPairGenerator 参数 RSA
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(KEY_LENG);
			// 通过对象 KeyPairGenerator 获取对象KeyPair
			KeyPair keyPair = keyPairGen.generateKeyPair();

			// 通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			return new RSAKey(publicKey, privateKey);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 公钥加密过程
	 */
	public static byte[] encryptByPublicKey(String publicKey, byte[] content) {
		if (StringUtils.isBlank(publicKey) || content == null || content.length == 0) {
			return null;
		}
		RSAPublicKey _publicKey = loadPublicKey(publicKey);
		return encryptByPublicKey(_publicKey, content);
	}
	/**
	 * 公钥加密过程
	 */
	public static byte[] encryptByPublicKey(RSAPublicKey publicKey, byte[] content) {
		if (publicKey == null || content == null || content.length == 0) {
			return null;
		}
		if (content.length <= MAX_ENCRYPT_BLOCK) {
			try {
				Cipher cipher = getCipher();
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				byte[] output = cipher.doFinal(content);
				return output;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return null;
			}
		}else{
			return encryptByPublicKey_subsection(publicKey, content);
		}
	}
	//分段公钥加密过程
	private static byte[] encryptByPublicKey_subsection(RSAPublicKey publicKey, byte[] content) {
		try {
			Cipher cipher = getCipher();
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			int inputLen = content.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        int maxLeng = MAX_ENCRYPT_BLOCK - 1;
	        for(int i = 0; inputLen - offSet > 0; offSet = i * maxLeng) {
	            byte[] cache;
	            if(inputLen - offSet > maxLeng) {
	                cache = cipher.doFinal(content, offSet, maxLeng);
	            } else {
	                cache = cipher.doFinal(content, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            ++i;
	        }
	        byte[] output = out.toByteArray();
	        out.close();
	        return output;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 私钥加密过程
	 */
	public static byte[] encryptByPrivateKey(String privateKey, byte[] content) {
		if (StringUtils.isBlank(privateKey) || content == null || content.length == 0) {
			return null;
		}
		RSAPrivateKey _pPrivateKey = loadPrivateKey(privateKey);
		return encryptByPrivateKey(_pPrivateKey, content);
	}
	/**
	 * 私钥加密过程
	 */
	public static byte[] encryptByPrivateKey(RSAPrivateKey privateKey, byte[] content) {
		if (privateKey == null || content == null || content.length == 0) {
			return null;
		}
		if (content.length <= MAX_ENCRYPT_BLOCK) {
			try {
				Cipher cipher = getCipher();
				cipher.init(Cipher.ENCRYPT_MODE, privateKey);
				byte[] output = cipher.doFinal(content);
				return output;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return null;
			}
		}else{
			return encryptByPrivateKey_subsection(privateKey, content);
		}
	}
	//分段私钥加密过程
	private static byte[] encryptByPrivateKey_subsection(RSAPrivateKey privateKey, byte[] content) {
		try {
			Cipher cipher = getCipher();
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			int inputLen = content.length;
	        int maxLeng = MAX_ENCRYPT_BLOCK - 1;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        for(int i = 0; inputLen - offSet > 0; offSet = i * maxLeng) {
	            byte[] cache;
	            if(inputLen - offSet > maxLeng) {
	                cache = cipher.doFinal(content, offSet, maxLeng);
	            } else {
	                cache = cipher.doFinal(content, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            ++i;
	        }
	        byte[] output = out.toByteArray();
	        out.close();
	        return output;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 公钥解密过程
	 */
	public static byte[] decryptByPublicKey(String publicKey, byte[] content) {
		if (StringUtils.isBlank(publicKey) || content == null || content.length == 0) {
			return null;
		}
		RSAPublicKey _publicKey = loadPublicKey(publicKey);
		return decryptByPublicKey(_publicKey, content);
	}
	/**
	 * 公钥解密过程
	 */
	public static byte[] decryptByPublicKey(RSAPublicKey publicKey, byte[] content) {
		if (publicKey == null || content == null || content.length == 0) {
			return null;
		}
		if (content.length <= MAX_DECRYPT_BLOCK) {
			try {
				Cipher cipher = getCipher();
				cipher.init(Cipher.DECRYPT_MODE, publicKey);
				byte[] output = cipher.doFinal(content);
				return output;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return null;
			}
		}else{
			return decryptByPublicKey_subsection(publicKey, content);
		}
	}
	//分段公钥解密过程
	private static byte[] decryptByPublicKey_subsection(RSAPublicKey publicKey, byte[] content) {
		try {
			Cipher cipher = getCipher();
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			int inputLen = content.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        for(int i = 0; inputLen - offSet > 0; offSet = i * MAX_DECRYPT_BLOCK) {
	            byte[] cache;
	            if(inputLen - offSet > MAX_DECRYPT_BLOCK) {
	                cache = cipher.doFinal(content, offSet, MAX_DECRYPT_BLOCK);
	            } else {
	                cache = cipher.doFinal(content, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            ++i;
	        }
	        byte[] output = out.toByteArray();
	        out.close();
	        return output;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
    }

	/**
	 * 私钥解密过程
	 */
	public static byte[] decryptByPrivateKey(String privateKey, byte[] content) {
		if (StringUtils.isBlank(privateKey) || content == null || content.length == 0) {
			return null;
		}
		RSAPrivateKey _pPrivateKey = loadPrivateKey(privateKey);
		return decryptByPrivateKey(_pPrivateKey, content);
	}
	/**
	 * 私钥解密过程
	 */
	public static byte[] decryptByPrivateKey(RSAPrivateKey privateKey, byte[] content) {
		if (privateKey == null || content == null || content.length == 0) {
			return null;
		}
		if (content.length <= MAX_DECRYPT_BLOCK) {
			try {
				Cipher cipher = getCipher();
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				byte[] output = cipher.doFinal(content);
				return output;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return null;
			}
		}else{
			return decryptByPrivateKey_subsection(privateKey, content);
		}
	}
	//分段私钥解密过程
	private static byte[] decryptByPrivateKey_subsection(RSAPrivateKey privateKey, byte[] content) {
		try {
			Cipher cipher = getCipher();
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			int inputLen = content.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        for(int i = 0; inputLen - offSet > 0; offSet = i * MAX_DECRYPT_BLOCK) {
	            byte[] cache;
	            if(inputLen - offSet > MAX_DECRYPT_BLOCK) {
	                cache = cipher.doFinal(content, offSet, MAX_DECRYPT_BLOCK);
	            } else {
	                cache = cipher.doFinal(content, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            ++i;
	        }
	        byte[] output = out.toByteArray();
	        out.close();
	        return output;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
    }
	/**
	 * 从字符串中加载公钥
	 */
	public static RSAPublicKey loadPublicKey(String publicKeyStr) {
		if (StringUtils.isBlank(publicKeyStr)) {
			return null;
		}
		try {
			byte[] buffer = Base64Util.decode(publicKeyStr);
			KeyFactory keyFactory = getKeyFactory();
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 从字符串中加载私钥
	 */
	public static RSAPrivateKey loadPrivateKey(String privateKeyStr) {
		if (StringUtils.isBlank(privateKeyStr)) {
			return null;
		}
		try {
			byte[] buffer = Base64Util.decode(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = getKeyFactory();
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	private final static ThreadLocal<KeyFactory> keyFactoryLocal = new ThreadLocal<KeyFactory>();
	private static KeyFactory getKeyFactory() {
		KeyFactory keyFactory = keyFactoryLocal.get();
		if (keyFactory == null) {
			synchronized (KEY_ALGORITHM) {
				keyFactory = keyFactoryLocal.get();
				if (keyFactory == null) {
					try {
						keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
						keyFactoryLocal.set(keyFactory);
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}
		}
		return keyFactory;
	}
	private final static ThreadLocal<Cipher> cipherLocal = new ThreadLocal<Cipher>();
	private static Cipher getCipher() {
		Cipher cipher = cipherLocal.get();
		if (cipher == null) {
			synchronized (KEY_ALGORITHM) {
				cipher = cipherLocal.get();
				if (cipher == null) {
					try {
						cipher = Cipher.getInstance(KEY_ALGORITHM);
						cipherLocal.set(cipher);
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}
		}
		return cipher;
	}

}
