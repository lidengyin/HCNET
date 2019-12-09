package cn.hctech2006.hcnet.util;

import org.pegdown.PegDownProcessor;

/**
 * Markdown转接工具类
 */
public class MarkdownToHtml {
    public static String markDownToHtml(String makeDownStr){
        PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
        return pdp.markdownToHtml(makeDownStr);
    }
}
