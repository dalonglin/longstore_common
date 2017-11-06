package com.longstore.common.domain.secret;

import java.io.Serializable;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.longstore.common.util.Base64Util;

/**
 * RSA数据加密解密
 */
public class RSAKey implements Serializable{
	private static final long serialVersionUID = -1846304892292884045L;
	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;
	
	public RSAKey(RSAPublicKey publicKey, RSAPrivateKey privateKey){
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public String toString(){
		return "publicKey=" + Base64Util.encode(publicKey.getEncoded()) + 
				", privateKey=" + Base64Util.encode(privateKey.getEncoded());
	}

	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public String getPublicKeyStr() {
		return Base64Util.encode(publicKey.getEncoded());
	}

	public String getPrivateKeyStr() {
		return Base64Util.encode(privateKey.getEncoded());
	}
	
}
