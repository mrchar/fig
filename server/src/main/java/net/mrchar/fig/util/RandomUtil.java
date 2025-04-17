package net.mrchar.fig.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import net.mrchar.fig.common.PreparationFailedException;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang3.RandomUtils;

public class RandomUtil {
  private static final Base32 base32 = new Base32();

  public static String generatePassword() {
    // 定义密码字符集
    String characters =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;':\",./<>?";
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < 8; i++) {
      // 生成随机索引
      int randomIndex = RandomUtils.secure().randomInt(0, characters.length());
      // 根据索引从字符集中选取字符
      stringBuilder.append(characters.charAt(randomIndex));
    }

    return stringBuilder.toString();
  }

  public static String generateCode(String seed) {
    MessageDigest sha1;
    try {
      sha1 = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
      throw new PreparationFailedException("创建失败，请稍候重试。");
    }

    sha1.update(seed.getBytes());
    sha1.update(Instant.now().toString().getBytes());

    byte[] hash = sha1.digest();

    return new String(base32.encode(hash));
  }
}
