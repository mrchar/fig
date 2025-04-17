package net.mrchar.fig.authentication;

import org.apache.commons.lang3.RandomUtils;

public class PasswordUtil {

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
}
