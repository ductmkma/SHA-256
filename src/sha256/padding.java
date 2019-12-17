/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sha256;

/**
 *
 * @author anhkma
 */
public class padding {

    public static String to_Bin(String message) {
        String kq = "";
        int len = message.length();
        for (int i = 0; i < len; i++) {
            char key = message.charAt(i);
            int k = key;
            String text = Integer.toBinaryString(k);
            while (text.length() < 8) {
                text = "0" + text;
            }
            kq = kq + text;
        }
        return kq;

    }

    public static String Message_pad(String message) {
        int len_message = message.length();
        String convert = to_Bin(message);
        convert = convert + "1";
        int len = (convert.length() % 512);
        if (len <= (448)) {
            while (len < 448) {
                convert = convert + "0";
                len = len + 1;
            }
        } else {
            for (int i = 1; i <= (512 - len + 448); i++) {
                convert = convert + "0";
            }
        }
        String leng = Integer.toBinaryString(len_message * 8);
        if (leng.length() < 64) {
            while (leng.length() < 64) {
                leng = "0" + leng;
            }
        }
        convert = convert + leng;
        return convert;
    }

}
