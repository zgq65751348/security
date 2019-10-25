package org.peak.myth.security.base64;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>
 * 加密算法测试
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/25 0025 下午 1:59
 */

public class Base64Test {

	public static void main(String[] args) {
		String stringKey = "peak.myth";
		byte[] encodedKey = Base64.decodeBase64(stringKey);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		System.out.println(key.toString());
	}
}
