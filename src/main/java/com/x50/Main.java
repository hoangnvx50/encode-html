package com.x50;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        //TODO: replace your path at here
        final File folder = new File("C:/Users/hoangnv5.os/Desktop/Biz_Projects/corp/gateway/template/smsEmail");
        listFilesForFolder(folder);

    }

    public static void listFilesForFolder(final File folder) throws IOException {
        int counter = 0;
        //get list file in path
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                counter++;
                Path path = Paths.get("C:/Users/hoangnv5.os/Desktop/Biz_Projects/corp/gateway/template/smsEmail/" + fileEntry.getName());
                Charset charset = StandardCharsets.UTF_8;
                //Read file content
                String content = Files.readString(path, charset);
                // Decode file
                String contentAfterDecode = StringEscapeUtils.unescapeHtml4(content);
                //System.err.println("SAU KHI DECODE: " + contentAfterDecode);
                // Replace if need
                /*contentAfterDecode = contentAfterDecode.replaceAll("MB", "LP");
                contentAfterDecode = contentAfterDecode.replaceAll("MBBank", "LPBank");
                contentAfterDecode = contentAfterDecode.replaceAll("mbbank", "lpbank");
                contentAfterDecode = contentAfterDecode.replaceAll("19009045", "1800 577758");
                contentAfterDecode = contentAfterDecode.replaceAll("1900545426", "1800 577758");
                contentAfterDecode = contentAfterDecode.replaceAll("Ngân hàng TMCP Quân đội", "Ngân hàng Thương mại Cổ phần Bưu điện Liên Việt");*/

                String contentAfterEncode = "";
                int indexOfEndHeader = contentAfterDecode.indexOf("###");
                if (indexOfEndHeader == -1) {
                    int indexOfStartBody = contentAfterDecode.indexOf("<");
                    if (indexOfStartBody < 0) {
                        //Not encode and use now
                        contentAfterEncode = contentAfterDecode;
                    } else {
                        //Encode
                        String header = indexOfStartBody == 0 ? "" : contentAfterDecode.substring(0, indexOfStartBody - 1);
                        String body = contentAfterDecode.substring(indexOfStartBody);
                        contentAfterEncode = header + tfEncoder(body);
                    }
                } else {
                    String header = contentAfterDecode.substring(0, indexOfEndHeader + 3);
                    String body = contentAfterDecode.substring(indexOfEndHeader + 3);
                    contentAfterEncode = header + tfEncoder(body);
                }

                //Encode lại sau khi thay đổi nhận diện thương hiệu
                //System.err.println("SAU KHI ENCODE VÀ THAY THẾ LP: " + contentAfterEncode);
                Files.write(path, contentAfterEncode.getBytes(charset));
            }
        }
        System.out.println("COUNTER: " + counter);
    }

    private static String tfEncoder(String needEncode) {
        var tf1 = new String[]{"&#38;", "&#169;", "&#174;", "&#178;", "&#179;", "&#8211;", "&#8212;", "&#8216;", "&#8217;", "&#8220;", "&#8221;", "&#8226;", "&#8224;", "&#8225;", "&#8242;", "&#8243;", "&#8249;", "&#8250;", "&#8364;", "&#8482;", "&#732;", "&#710;", "&#9824;", "&#9827;", "&#9829;", "&#9830;", "&#9674;", "&#8592;", "&#8594;", "&#8593;", "&#8595;", "&#8596;", "&#172;", "&#161;", "&#162;", "&#163;", "&#164;", "&#165;", "&#166;", "&#167;", "&#168;", "&#170;", "&#171;", "&#172;", "&#173;", "&#175;", "&#176;", "&#177;", "&#180;", "&#181;", "&#182;", "&#183;", "&#184;", "&#185;", "&#186;", "&#187;", "&#188;", "&#189;", "&#190;", "&#191;", "&#192;", "&#193;", "&#194;", "&#195;", "&#196;", "&#197;", "&#198;", "&#199;", "&#200;", "&#201;", "&#202;", "&#203;", "&#204;", "&#205;", "&#206;", "&#207;", "&#208;", "&#209;", "&#210;", "&#211;", "&#212;", "&#213;", "&#214;", "&#215;", "&#216;", "&#217;", "&#218;", "&#219;", "&#220;", "&#221;", "&#222;", "&#223;", "&#224;", "&#225;", "&#226;", "&#227;", "&#228;", "&#229;", "&#230;", "&#231;", "&#232;", "&#233;", "&#234;", "&#235;", "&#236;", "&#237;", "&#238;", "&#239;", "&#240;", "&#241;", "&#242;", "&#243;", "&#244;", "&#245;", "&#246;", "&#247;", "&#248;", "&#249;", "&#250;", "&#251;", "&#252;", "&#253;", "&#254;", "&#255;", "&#60;", "&#62;"};
        var tf2 = new String[]{"&amp;", "&copy;", "&reg;", "&sup2;", "&sup3;", "&ndash;", "&mdash;", "&lsquo;", "&rsquo;", "&ldquo;", "&rdquo;", "&bull;", "&dagger;", "&Dagger;", "&prime;", "&Prime;", "&lsaquo;", "&rsaquo;", "&euro;", "&trade;", "&tilde;", "&circ;", "&spades;", "&clubs;", "&hearts;", "&diams;", "&loz;", "&larr;", "&rarr;", "&uarr;", "&darr;", "&harr;", "&not;", "&iexcl;", "&cent;", "&pound;", "&curren;", "&yen;", "&brvbar;", "&sect;", "&uml;", "&ordf;", "&laquo;", "&not;", "&shy;", "&macr;", "&deg;", "&plusmn;", "&acute;", "&micro;", "&para;", "&middot;", "&cedil;", "&sup1;", "&ordm;", "&raquo;", "&frac14;", "&frac12;", "&frac34;", "&iquest;", "&Agrave;", "&Aacute;", "&Acirc;", "&Atilde;", "&Auml;", "&Aring;", "&AElig;", "&Ccedil;", "&Egrave;", "&Eacute;", "&Ecirc;", "&Euml;", "&Igrave;", "&Iacute;", "&Icirc;", "&Iuml;", "&ETH;", "&Ntilde;", "&Ograve;", "&Oacute;", "&Ocirc;", "&Otilde;", "&Ouml;", "&times;", "&Oslash;", "&Ugrave;", "&Uacute;", "&Ucirc;", "&Uuml;", "&Yacute;", "&THORN;", "&szlig;", "&agrave;", "&aacute;", "&acirc;", "&atilde;", "&auml;", "&aring;", "&aelig;", "&ccedil;", "&egrave;", "&eacute;", "&ecirc;", "&euml;", "&igrave;", "&iacute;", "&icirc;", "&iuml;", "&eth;", "&ntilde;", "&ograve;", "&oacute;", "&ocirc;", "&otilde;", "&ouml;", "&divide;", "&oslash;", "&ugrave;", "&uacute;", "&ucirc;", "&uuml;", "&yacute;", "&thorn;", "&yuml;", "&lt;", "&gt;"};
        var aRet = new ArrayList<String>();
        for (int i = 0; i < needEncode.length(); i++) {
            int iC = needEncode.charAt(i);
            if (iC == 38 || (iC == 96) || iC > 127) {
                aRet.add(i, "&#" + iC + ";");
            } else {
                aRet.add(i, String.valueOf(needEncode.charAt(i)));
            }
        }
        needEncode = String.join("", aRet);
        for (var ii = 0; ii < tf1.length; ii++) {
            needEncode = needEncode.replaceAll(tf1[ii] + "g", tf2[ii]);
        }

        return needEncode;
    }
}




