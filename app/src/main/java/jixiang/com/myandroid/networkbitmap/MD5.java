package jixiang.com.myandroid.networkbitmap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *  md5 tools 
 *
 */
class MD5 {

	/**
	 * Create MD5 Hash
	 * @param s input String
	 * @return md5 hash
	 */
	public static String md5(String s) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuilder hexString = new StringBuilder();
			for (byte aMessageDigest : messageDigest) {
				hexString.append(Integer.toHexString(0xFF & aMessageDigest));
			}
			return hexString.toString();
	
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
