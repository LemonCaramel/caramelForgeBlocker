package moe.caramel.forgeblocker.utils;

import org.bukkit.ChatColor;

import java.nio.charset.StandardCharsets;

public class Util {

    /**
     * config 색 코드를 플러그인에서 사용할 수 있도록 변경해 줍니다.
     *
     * @param message 변환할 메시지를 작성합니다.
     */
    public static String colorTranslate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }


    /**
     * Byte 배열의 내용을 String UFT-8 형식으로 변환합니다.
     *
     * @param message byte 문자를 작성합니다.
     */
    public static String getStringMessage(byte[] message) {
        return new String(message, StandardCharsets.UTF_8);
    }


    /**
     * caramelLibrary 에서 사용하는 버전 형식에 맞추어 서버 버전을 변경해줍니다.
     *
     * @param version 마인크래프트 서버 버전을 작성합니다.
     */
    public static int getMCVersion(String version) {
        StringBuilder temp = new StringBuilder();
        try {
            for (String a : version.split("-")[0].split("\\."))
                temp.append("00".substring(a.length())).append(a);
            return Integer.parseInt(temp.toString() + "000000".substring(temp.length()));
        } catch (Exception error) {
            error.printStackTrace();
            return -1;
        }
    }

}
