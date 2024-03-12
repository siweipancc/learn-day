package com.pancc.learn.jdks.web;

import java.net.FileNameMap;
import java.net.URLConnection;

/**
 * @author Siweipancc
 */
public class MineTy {
    public static void main(String[] args) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        System.out.println(STR."fileNameMap.getContentTypeFor(\"jj\") = \{fileNameMap.getContentTypeFor("jj")}");
        System.out.println(STR."fileNameMap.getContentTypeFor(\"jj.jpg\") = \{fileNameMap.getContentTypeFor("jj.jpg")}");
        System.out.println(STR."fileNameMap.getContentTypeFor(\"jj.JPG\") = \{fileNameMap.getContentTypeFor("jj.JPG")}");
        System.out.println(STR."fileNameMap.getContentTypeFor(\"jj.png\") = \{fileNameMap.getContentTypeFor("jj.png")}");
        System.out.println(STR."fileNameMap.getContentTypeFor(\"jj.xml\") = \{fileNameMap.getContentTypeFor("jj.xml")}");
        System.out.println(STR."fileNameMap.getContentTypeFor(\"jj.txt\") = \{fileNameMap.getContentTypeFor("jj.txt")}");
        System.out.println(STR."fileNameMap.getContentTypeFor(\"jj.avi\") = \{fileNameMap.getContentTypeFor("jj.avi")}");
        System.out.println(STR."fileNameMap.getContentTypeFor(\"jj.exe\") = \{fileNameMap.getContentTypeFor("jj.exe")}");
    }
}
