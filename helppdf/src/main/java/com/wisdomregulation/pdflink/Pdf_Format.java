package com.wisdomregulation.pdflink;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.SplitCharacter;
import com.itextpdf.text.pdf.PdfChunk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 本类 是 pdf打印的帮助类
 */
public class Pdf_Format {
    public static float getStringLength(String orgstring, Font fontChinese) {//方法废弃
        Chunk phrase1 = new Chunk(orgstring, fontChinese);
        float result = 0;
        List<Chunk> mArrayList1 = phrase1.getChunks();
        for (int i = 0; i < mArrayList1.size(); i++) {
            float everyblock = ((Chunk) (mArrayList1.get(i))).getWidthPoint();
            result = result + everyblock;
        }
        System.out.println(result);
        return result;
    }

    /**
     * 年月日时分秒0 1 2 3 4 5
     *
     * @param time
     * @param index
     * @return
     */
    public static String getTime(String time, int index) {
        String result = "";
        String orgfm = getTime(time);
        if (orgfm != null && !"".equals(orgfm)) {
            String[] times = orgfm.split("-");
            if (times.length > index) {
                result = times[index].trim();
            } else {
                result = " ";
            }
        }
        return result;
    }

    public static String getTime(String time) {

        String resulttime = "";
        if (time == null || time.equals("")) {
            resulttime = (new Date()).getTime() + "";
        } else {
            if (time.matches("(.+)-(.*)")) {
                String[] headresultarray = time.split(" ");
                String headresult = "";
                if (headresultarray.length < 1) {
                    headresult = time;
                } else {
                    if (time.matches("(.+) (.*)")) {
                        headresult = time.split(" ")[0];
                    } else {
                        time = time + " 00:00:00";
                        headresult = time.split(" ")[0];
                    }

                }

                String bodyresult = "";
                String body = time.split(" ")[1];
                String[] bodysplit = new String[3];
                String[] bodysplit2 = body.split(":");
                for (int i = 0; i < bodysplit2.length; i++) {
                    String tmp = body.split(":")[i].trim();
                    if (tmp == null || tmp.equals("") || tmp.equals("null")) {
                        bodysplit[i] = "00";
                    } else {
                        bodysplit[i] = tmp;
                    }

                }
                for (int i = 0; i < bodysplit.length; i++) {
                    bodyresult = bodyresult
                            + (bodysplit[i] != null ? bodysplit[i] : "00")
                            + "-";
                }
                if (bodyresult != null && bodyresult.length() > 0) {
                    bodyresult = bodyresult.substring(0,
                            bodyresult.length() - 1);
                }
                resulttime = headresult + "-" + bodyresult;

            } else if (time.matches("(.*?)年(.*?)月(.*?)日")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                try {
                    Date thistime = sdf.parse(time);
                    SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
                    String newtime = sdfs.format(thistime);
                    resulttime = getTime(newtime);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                resulttime = time;
            }
        }

        return resulttime;
    }


    /**
     * // 格式化成UTF8填写横线数据 目的去除换行符（其实后来也兼容了换行符 不过一直没允许换行符）
     *
     * @param orgin
     * @return
     */
    public static String stringFormatToUTF8(String orgin) {
        orgin = orgin.replaceAll("\n", ";").replace("（", "(").replace("）", ")");
        String endString = "";
        try {

            endString = orgin;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return endString;
    }

    /**
     * // 格式化成UTF8填写横线数据 目的去除换行符（其实后来也兼容了换行符 不过一直没允许换行符）
     *
     * @param orgin
     * @return
     */
    public static String stringFormatToUTF8NoReplace(String orgin) {
        orgin = orgin.replace("（", "(").replace("）", ")");
        String endString = "";
        try {

            endString = orgin;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return endString;
    }

    /**
     * // 用于表格中格式化固定长度的字段 不支持换行 目前项目没有要求 表格中带下划线且换行的 所以问题不大
     *
     * @param orgin
     * @param formatFixedlength
     * @return
     */
    public static String stringFormatFixedLength(String orgin, float formatFixedlength, double textsize, float firstLineIndent, float pagewidth) {
        if (pagewidth == -1) {
            pagewidth = 409.5f;
        }
        formatFixedlength = formatlength(formatFixedlength, (int) textsize);
        firstLineIndent = formatlength(firstLineIndent, (int) textsize);
        orgin = stringFormatToUTF8(orgin);
        String endString = "";
        String orginCharge = "";
        try {
            char[] orginChars = orgin.toCharArray();
            int orginLength = orginChars.length;
            int chineseLength = 0;
            for (int i = 0; i < orginChars.length; i++) {
                if ((orginChars[i] >= 0x4e00) && (orginChars[i] <= 0x9fbb)) {
                    chineseLength++;
                    orginCharge = orginCharge + orginChars[i];

                } else {


                }
            }
            float orginwidth = (float) (chineseLength * textsize * 2 + (orginLength - chineseLength) * textsize);
            float targetwidth = (float) (formatFixedlength);
            if (orginwidth < targetwidth) {
                int targetIndex = (int) ((targetwidth - orginwidth) / textsize);
                for (int i = 0; i < targetIndex - 1; i++) {
                    orgin = orgin + ' ';
                }
            } else {
                double sumwidth = 0;
                int charindex = 0;
                for (int i = 0; i < orginChars.length; i++) {
                    if ((orginChars[i] >= 0x4e00) && (orginChars[i] <= 0x9fbb)) {
                        sumwidth = sumwidth + textsize * 2;
                    } else {
                        sumwidth = sumwidth + textsize;
                    }
                    if (sumwidth > targetwidth) {
                        charindex = i;
                        break;
                    }
                    if (sumwidth == targetwidth) {
                        charindex = i + 1;
                        break;
                    }

                }

                orgin = orgin.substring(0, charindex);


            }
            if (formatFixedlength + firstLineIndent <= pagewidth) {
                //小于时不换行  加起来小于总页面宽度就不换行
            } else {
                orginChars = orgin.toCharArray();
                String tmpstring = "";
                double sumwidth2 = 0;
                int charindex2 = 0;
                for (int i = 0; i < orginChars.length; i++) {
                    if ((orginChars[i] >= 0x4e00) && (orginChars[i] <= 0x9fbb)) {
                        sumwidth2 = sumwidth2 + textsize * 2;
                    } else {
                        sumwidth2 = sumwidth2 + textsize;
                    }
                    if (sumwidth2 > pagewidth - firstLineIndent) {
                        charindex2 = i;
                        break;
                    }
                }
                tmpstring = tmpstring + orgin.substring(0, charindex2) + '\u00a0' + '\n';
                double sumwidth3 = 0;
                int charindex3 = charindex2;
                for (int i = charindex2; i < orginChars.length; i++) {
                    if ((orginChars[i] >= 0x4e00) && (orginChars[i] <= 0x9fbb)) {
                        sumwidth3 = sumwidth3 + textsize * 2;
                    } else {
                        sumwidth3 = sumwidth3 + textsize;
                    }
                    if (sumwidth3 >= pagewidth) {

                        tmpstring = tmpstring + orgin.substring(charindex3, i) + '\u00a0' + '\n';
                        charindex3 = i;
                        sumwidth3 = 0;
                    }
                    if (i + 1 == orginChars.length) {
                        tmpstring = tmpstring + orgin.substring(charindex3, i) + '\u00a0' + '\n';
                    }
                }
                if (tmpstring.length() > 1) {
                    boolean needloop = true;
                    while (needloop) {
                        if (tmpstring.substring(tmpstring.length() - 1, tmpstring.length()).equals("\n")) {

                            tmpstring = tmpstring.substring(0, tmpstring.length() - 1);//去除最后一个换行
                        }
                        if (tmpstring.substring(tmpstring.length() - 1, tmpstring.length()).equals("\u00a0")) {

                            tmpstring = tmpstring.substring(0, tmpstring.length() - 1);//去除最后一个换行
                        } else {
                            tmpstring = tmpstring + '\u00a0';
                            needloop = false;
                        }

                    }


                }
                orgin = tmpstring;
            }

            endString = orgin;
//            System.out.println("--------");
//            System.out.println(endString);
//            System.out.println("--------");
            return endString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * // 用于表格中格式化固定长度的字段 不支持换行 目前项目没有要求 表格中带下划线且换行的 所以问题不大
     *
     * @param orgin
     * @param formatFixedlength
     * @return
     */
    public static String stringFormatFixedTableLength(String orgin, float formatFixedlength, double textsize) {
        formatFixedlength = formatlength(formatFixedlength, (int) textsize);
        orgin = stringFormatToUTF8(orgin);
        String endString = "";
        String orginCharge = "";
        try {
            char[] orginChars = orgin.toCharArray();
            int orginLength = orginChars.length;
            int chineseLength = 0;
            for (int i = 0; i < orginChars.length; i++) {
                if ((orginChars[i] >= 0x4e00) && (orginChars[i] <= 0x9fbb)) {
                    chineseLength++;
                    orginCharge = orginCharge + orginChars[i];

                } else {

                }
            }
            float orginwidth = (float) (chineseLength * textsize * 2 + (orginLength - chineseLength) * textsize);
            float targetwidth = (float) (formatFixedlength);
            if (orginwidth < targetwidth) {
                int targetIndex = (int) ((targetwidth - orginwidth) / textsize);
                for (int i = 0; i < targetIndex - 1; i++) {
                    orgin = orgin + ' ';
                }
            } else {
                double sumwidth = 0;
                int charindex = 0;
                for (int i = 0; i < orginChars.length; i++) {
                    if ((orginChars[i] + "").getBytes().length > 1) {
                        sumwidth = sumwidth + textsize * 2;
                    } else {
                        sumwidth = sumwidth + textsize;
                    }
                    if (sumwidth > targetwidth) {
                        charindex = i;
                        break;
                    }
                    if (sumwidth == targetwidth) {
                        charindex = i + 1;
                        break;
                    }

                }
                orgin = orgin.substring(0, charindex);

            }
            endString = orgin;
            return endString + '\u00a0';
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * // 非表格中格式化字符串成固定大小
     *
     * @param orgin
     * @param formatFixedlength
     * @return
     */
    public static String stringFormatFixLength(String orgin, float formatFixedlength, double textsize) {
        formatFixedlength = formatlength(formatFixedlength, (int) textsize);

        orgin = stringFormatToUTF8(orgin);
        String endString = "";
        String orginCharge = "";
        try {
            char[] orginChars = orgin.toCharArray();
            int orginLength = orginChars.length;
            int chineseLength = 0;
            for (int i = 0; i < orginChars.length; i++) {
                if ((orginChars[i] + "").getBytes().length > 1) {
                    chineseLength++;
                    orginCharge = orginCharge + orginChars[i];

                } else {

                }
            }
            float orginwidth = (float) (chineseLength * textsize * 2 + (orginLength - chineseLength) * textsize);
            float targetwidth = (float) (formatFixedlength);
            if (orginwidth < targetwidth) {
                int targetIndex = (int) ((targetwidth - orginwidth) / textsize);
                for (int i = 0; i < targetIndex; i++) {
                    orgin = orgin + ' ';
                }
            } else {
                double sumwidth = 0;
                int charindex = 0;
                for (int i = 0; i < orginChars.length; i++) {
                    if ((orginChars[i] + "").getBytes().length > 1) {
                        sumwidth = sumwidth + textsize * 2;
                    } else {
                        sumwidth = sumwidth + textsize;
                    }
                    if (sumwidth > targetwidth) {
                        charindex = i;
                        break;
                    }
                    if (sumwidth == targetwidth) {
                        charindex = i + 1;
                        break;
                    }
                }
                orgin = orgin.substring(0, charindex);
            }
            endString = '\u00a0' + orgin;

            return endString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * //非表格中有下划线的空位 支持换行运算
     *
     * @param text
     * @param length
     * @param fontChinese
     * @param firstLineIndent 前面空余量
     * @param pagewidth       换行时的长度
     * @return
     */
    public static Chunk underLine(String text, String length, Font fontChinese, String firstLineIndent, float pagewidth) {
        Chunk targetChunk = null;
        try {
//            System.out.println(firstLineIndent+":"+pagewidth);
            targetChunk = new Chunk(stringFormatFixedLength(text, getStringLength(length, fontChinese), fontChinese.getSize() / 2, getStringLength(firstLineIndent, fontChinese), pagewidth), fontChinese).setUnderline(-1f, -1f)
                    .setSplitCharacter(new SplitCharacter() {

                        @Override
                        public boolean isSplitCharacter(int arg0, int arg1, int arg2, char[] arg3,
                                                        PdfChunk[] arg4) {
                            char c;
                            if (arg4 == null)
                                c = arg3[arg1];
                            else
                                c = (char) arg4[Math.min(arg1, arg4.length - 1)].getUnicodeEquivalent(arg3[arg1]);
                            if (c <= ' ' || c == '-') {
                                return false;
                            }
                            if (c < 0x2e80)
                                return false;
                            return false;
//				    return ((c >= 0x2e80 && c < 0xd7a0)
//				    || (c >= 0xf900 && c < 0xfb00)
//				    || (c >= 0xfe30 && c < 0xfe50)
//				    || (c >= 0xff61 && c < 0xffa0));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //非表格中有下划线的空位 支持换行运算
     *
     * @param text
     * @param length
     * @param fontChinese
     * @param firstLineIndent 前面空余量
     * @param pagewidth       换行时的长度
     * @return
     */
    public static Chunk nounderLine(String text, float length, Font fontChinese, String firstLineIndent, float pagewidth) {
        Chunk targetChunk = null;
        try {
//            System.out.println(firstLineIndent+":"+pagewidth);
            targetChunk = new Chunk(stringFormatFixedLength(text, length, fontChinese.getSize() / 2, getStringLength(firstLineIndent, fontChinese), pagewidth), fontChinese)
                    .setSplitCharacter(new SplitCharacter() {

                        @Override
                        public boolean isSplitCharacter(int arg0, int arg1, int arg2, char[] arg3,
                                                        PdfChunk[] arg4) {
                            char c;
                            if (arg4 == null)
                                c = arg3[arg1];
                            else
                                c = (char) arg4[Math.min(arg1, arg4.length - 1)].getUnicodeEquivalent(arg3[arg1]);
                            if (c <= ' ' || c == '-') {
                                return false;
                            }
                            if (c < 0x2e80)
                                return false;
                            return false;
//				    return ((c >= 0x2e80 && c < 0xd7a0)
//				    || (c >= 0xf900 && c < 0xfb00)
//				    || (c >= 0xfe30 && c < 0xfe50)
//				    || (c >= 0xff61 && c < 0xffa0));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //非表格中有下划线的空位 支持换行运算
     *
     * @param text
     * @param length
     * @param fontChinese
     * @param firstLineIndent 前面空余量
     * @param pagewidth       换行时的长度
     * @return
     */
    public static Chunk underLine(String text, float length, Font fontChinese, String firstLineIndent, float pagewidth) {
        Chunk targetChunk = null;
        try {
//            System.out.println(firstLineIndent+":"+pagewidth);
            targetChunk = new Chunk(stringFormatFixedLength(text, length, fontChinese.getSize() / 2, getStringLength(firstLineIndent, fontChinese), pagewidth), fontChinese).setUnderline(-1f, -1f)
                    .setSplitCharacter(new SplitCharacter() {

                        @Override
                        public boolean isSplitCharacter(int arg0, int arg1, int arg2, char[] arg3,
                                                        PdfChunk[] arg4) {
                            char c;
                            if (arg4 == null)
                                c = arg3[arg1];
                            else
                                c = (char) arg4[Math.min(arg1, arg4.length - 1)].getUnicodeEquivalent(arg3[arg1]);
                            if (c <= ' ' || c == '-') {
                                return false;
                            }
                            if (c < 0x2e80)
                                return false;
                            return false;
//				    return ((c >= 0x2e80 && c < 0xd7a0)
//				    || (c >= 0xf900 && c < 0xfb00)
//				    || (c >= 0xfe30 && c < 0xfe50)
//				    || (c >= 0xff61 && c < 0xffa0));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //非表格中有下划线的空位 支持换行运算
     *
     * @param text
     * @param length
     * @param fontChinese
     * @param firstLineIndent 前面空余量
     * @param pagewidth       换行时的长度
     * @return
     */
    public static Chunk nounderLine(String text, float length, Font fontChinese, float firstLineIndent, float pagewidth) {
        Chunk targetChunk = null;
        try {
//            System.out.println(firstLineIndent+":"+pagewidth);
            targetChunk = new Chunk(stringFormatFixedLength(text, length, fontChinese.getSize() / 2, firstLineIndent, pagewidth), fontChinese)
                    .setSplitCharacter(new SplitCharacter() {

                        @Override
                        public boolean isSplitCharacter(int arg0, int arg1, int arg2, char[] arg3,
                                                        PdfChunk[] arg4) {
                            char c;
                            if (arg4 == null)
                                c = arg3[arg1];
                            else
                                c = (char) arg4[Math.min(arg1, arg4.length - 1)].getUnicodeEquivalent(arg3[arg1]);
                            if (c <= ' ' || c == '-') {
                                return false;
                            }
                            if (c < 0x2e80)
                                return false;
                            return false;
//				    return ((c >= 0x2e80 && c < 0xd7a0)
//				    || (c >= 0xf900 && c < 0xfb00)
//				    || (c >= 0xfe30 && c < 0xfe50)
//				    || (c >= 0xff61 && c < 0xffa0));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetChunk;
    }


    /**
     * //非表格中有下划线的空位 支持换行运算
     *
     * @param text
     * @param length
     * @param fontChinese
     * @param firstLineIndent 前面空余量
     * @param pagewidth       换行时的长度
     * @return
     */
    public static Chunk underLine(String text, float length, Font fontChinese, float firstLineIndent, float pagewidth) {
        Chunk targetChunk = null;
        try {
//            System.out.println(firstLineIndent+":"+pagewidth);
            targetChunk = new Chunk(stringFormatFixedLength(text, length, fontChinese.getSize() / 2, firstLineIndent, pagewidth), fontChinese).setUnderline(-1f, -1f)
                    .setSplitCharacter(new SplitCharacter() {

                        @Override
                        public boolean isSplitCharacter(int arg0, int arg1, int arg2, char[] arg3,
                                                        PdfChunk[] arg4) {
                            char c;
                            if (arg4 == null)
                                c = arg3[arg1];
                            else
                                c = (char) arg4[Math.min(arg1, arg4.length - 1)].getUnicodeEquivalent(arg3[arg1]);
                            if (c <= ' ' || c == '-') {
                                return false;
                            }
                            if (c < 0x2e80)
                                return false;
                            return false;
//				    return ((c >= 0x2e80 && c < 0xd7a0)
//				    || (c >= 0xf900 && c < 0xfb00)
//				    || (c >= 0xfe30 && c < 0xfe50)
//				    || (c >= 0xff61 && c < 0xffa0));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //非表格中有下划线的空位
     *
     * @param text
     * @param length      需要画的长度
     * @param fontChinese
     * @return
     */
    public static Chunk underLine(String text, String length, Font fontChinese) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixLength(text, getStringLength(length, fontChinese), fontChinese.getSize() / 2), fontChinese).setUnderline(-1f, -1f)
                    .setSplitCharacter(new SplitCharacter() {

                        @Override
                        public boolean isSplitCharacter(int arg0, int arg1, int arg2, char[] arg3,
                                                        PdfChunk[] arg4) {
                            char c;
                            if (arg4 == null)
                                c = arg3[arg1];
                            else
                                c = (char) arg4[Math.min(arg1, arg4.length - 1)].getUnicodeEquivalent(arg3[arg1]);
                            if (c <= ' ' || c == '-') {
                                return false;
                            }
                            if (c < 0x2e80)
                                return false;
                            return false;
//				    return ((c >= 0x2e80 && c < 0xd7a0)
//				    || (c >= 0xf900 && c < 0xfb00)
//				    || (c >= 0xfe30 && c < 0xfe50)
//				    || (c >= 0xff61 && c < 0xffa0));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //非表格中有下划线的空位
     *
     * @param text
     * @param length      需要画的长度
     * @param fontChinese
     * @return
     */
    public static Chunk underLine(String text, float length, Font fontChinese) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixLength(text, length, fontChinese.getSize() / 2), fontChinese).setUnderline(-1f, -1f)
                    .setSplitCharacter(new SplitCharacter() {

                        @Override
                        public boolean isSplitCharacter(int arg0, int arg1, int arg2, char[] arg3,
                                                        PdfChunk[] arg4) {
                            char c;
                            if (arg4 == null)
                                c = arg3[arg1];
                            else
                                c = (char) arg4[Math.min(arg1, arg4.length - 1)].getUnicodeEquivalent(arg3[arg1]);
                            if (c <= ' ' || c == '-') {
                                return false;
                            }
                            if (c < 0x2e80)
                                return false;
                            return false;
//				    return ((c >= 0x2e80 && c < 0xd7a0)
//				    || (c >= 0xf900 && c < 0xfb00)
//				    || (c >= 0xfe30 && c < 0xfe50)
//				    || (c >= 0xff61 && c < 0xffa0));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //非表格中有下划线的空位
     *
     * @param text
     * @param length      需要画的长度
     * @param fontChinese
     * @return
     */
    public static Chunk underLine(String text, int timeindex, float length, Font fontChinese) {
        return underLine(getTime(text, timeindex), length, fontChinese);
    }

    /**
     * //非表格中无下划线的空位
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk nounderLine(String text, String length, Font fontChinese) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixLength(text, getStringLength(length, fontChinese), fontChinese.getSize() / 2), fontChinese)
                    .setSplitCharacter(new SplitCharacter() {

                        @Override
                        public boolean isSplitCharacter(int arg0, int arg1, int arg2, char[] arg3,
                                                        PdfChunk[] arg4) {
                            char c;
                            if (arg4 == null)
                                c = arg3[arg1];
                            else
                                c = (char) arg4[Math.min(arg1, arg4.length - 1)].getUnicodeEquivalent(arg3[arg1]);
                            if (c <= ' ' || c == '-') {
                                return false;
                            }
                            if (c < 0x2e80)
                                return false;
                            return false;
//				    return ((c >= 0x2e80 && c < 0xd7a0)
//				    || (c >= 0xf900 && c < 0xfb00)
//				    || (c >= 0xfe30 && c < 0xfe50)
//				    || (c >= 0xff61 && c < 0xffa0));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //非表格中无下划线的空位
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk nounderLine(String text, float length, Font fontChinese) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixLength(text, length, fontChinese.getSize() / 2), fontChinese)
                    .setSplitCharacter(new SplitCharacter() {

                        @Override
                        public boolean isSplitCharacter(int arg0, int arg1, int arg2, char[] arg3,
                                                        PdfChunk[] arg4) {
                            char c;
                            if (arg4 == null)
                                c = arg3[arg1];
                            else
                                c = (char) arg4[Math.min(arg1, arg4.length - 1)].getUnicodeEquivalent(arg3[arg1]);
                            if (c <= ' ' || c == '-') {
                                return false;
                            }
                            if (c < 0x2e80)
                                return false;
                            return false;
//				    return ((c >= 0x2e80 && c < 0xd7a0)
//				    || (c >= 0xf900 && c < 0xfb00)
//				    || (c >= 0xfe30 && c < 0xfe50)
//				    || (c >= 0xff61 && c < 0xffa0));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetChunk;
    }


    /**
     * //非表格中无下划线的空位
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk nounderLine(String text, int timeindex, float length, Font fontChinese) {

        return nounderLine(getTime(text, timeindex), length, fontChinese);
    }

    /**
     * //表格中没有下划线的空 未设定长度 一般正常人不会超 不删除换行符
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tablenounderLineNoReplace(String text, float length, Font fontChinese) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatToUTF8NoReplace(text), fontChinese).setSplitCharacter(new SplitCharacter() {

                @Override
                public boolean isSplitCharacter(int arg0, int arg1, int arg2, char[] arg3,
                                                PdfChunk[] arg4) {
                    char c;
                    if (arg4 == null)
                        c = arg3[arg1];
                    else
                        c = (char) arg4[Math.min(arg1, arg4.length - 1)].getUnicodeEquivalent(arg3[arg1]);
                    if (c <= ' ' || c == '-') {
                        return true;
                    }
                    if (c < 0x2e80)
                        return true;
                    return true;
//				    return ((c >= 0x2e80 && c < 0xd7a0)
//				    || (c >= 0xf900 && c < 0xfb00)
//				    || (c >= 0xfe30 && c < 0xfe50)
//				    || (c >= 0xff61 && c < 0xffa0));
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }

//    /**
//     * //表格中没有下划线的空 未设定长度 一般正常人不会超
//     *
//     * @param text
//     * @param length
//     * @param fontChinese
//     * @return
//     */
//    public static Chunk tablenounderLine(String text, float length, Font fontChinese) {
//        Chunk targetChunk = null;
//        try {
//            targetChunk = new Chunk(stringFormatToUTF8(text), fontChinese).setSplitCharacter(new SplitCharacter() {
//
//                @Override
//                public boolean isSplitCharacter(int arg0, int arg1, int arg2, char[] arg3,
//                                                PdfChunk[] arg4) {
//                    char c;
//                    if (arg4 == null)
//                        c = arg3[arg1];
//                    else
//                        c = (char) arg4[Math.min(arg1, arg4.length - 1)].getUnicodeEquivalent(arg3[arg1]);
//                    if (c <= ' ' || c == '-') {
//                        return true;
//                    }
//                    if (c < 0x2e80)
//                        return true;
//                    return true;
////				    return ((c >= 0x2e80 && c < 0xd7a0)
////				    || (c >= 0xf900 && c < 0xfb00)
////				    || (c >= 0xfe30 && c < 0xfe50)
////				    || (c >= 0xff61 && c < 0xffa0));
//                }
//            });
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return targetChunk;
//    }

    /**
     * //表格中无下划线的空位
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tablenounderLine(String text, String length, Font fontChinese) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixedTableLength(text, getStringLength(length, fontChinese), fontChinese.getSize() / 2), fontChinese);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //表格中无下划线的空位
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tablenounderLine(String text, int timeindex, String length, Font fontChinese) {
        return tablenounderLine(getTime(text, timeindex), length, fontChinese);
    }


    /**
     * //表格中无下划线的空位
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tablenounderLine(String text, float length, Font fontChinese) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixedTableLength(text, length, fontChinese.getSize() / 2), fontChinese);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }


    /**
     * //表格中无下划线的空位
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tablenounderLine(String text, int timeindex, float length, Font fontChinese) {

        return tablenounderLine(getTime(text, timeindex), length, fontChinese);
    }

    /**
     * //表格中有下划线的空位
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tableunderLine(String text, String length, Font fontChinese) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixedTableLength(text, getStringLength(length, fontChinese), fontChinese.getSize() / 2), fontChinese).setUnderline(-1f, -1f);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //表格中有下划线的空位
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tableunderLine(String text, float length, Font fontChinese) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixedTableLength(text, length, fontChinese.getSize() / 2), fontChinese).setUnderline(-1f, -1f);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //表格中无下划线的空位 支持换行
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tablenounderLine(String text, float length, Font fontChinese, String firstLineIndent, String pagewidth) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixedLength(text, length, fontChinese.getSize() / 2, getStringLength(firstLineIndent, fontChinese), getStringLength(pagewidth, fontChinese)), fontChinese);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //表格中无下划线的空位 支持换行
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tablenounderLine(String text, float length, Font fontChinese, String firstLineIndent, float pagewidth) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixedLength(text, length, fontChinese.getSize() / 2, getStringLength(firstLineIndent, fontChinese), pagewidth), fontChinese);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }


    /**
     * //表格中无下划线的空位 支持换行
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tablenounderLine(String text, String length, Font fontChinese, float firstLineIndent, float pagewidth) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixedLength(text, getStringLength(length, fontChinese), fontChinese.getSize() / 2, firstLineIndent, pagewidth), fontChinese);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //表格中无下划线的空位 支持换行
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tablenounderLine(String text, float length, Font fontChinese, float firstLineIndent, float pagewidth) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixedLength(text, length, fontChinese.getSize() / 2, firstLineIndent, pagewidth), fontChinese);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //表格中有下划线的空位 支持换行
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tableunderLine(String text, float length, Font fontChinese, String firstLineIndent, String pagewidth) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixedLength(text, length, fontChinese.getSize() / 2, getStringLength(firstLineIndent, fontChinese), getStringLength(pagewidth, fontChinese)), fontChinese).setUnderline(-1f, -1f);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //表格中有下划线的空位 支持换行
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tableunderLine(String text, float length, Font fontChinese, String firstLineIndent, float pagewidth) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixedLength(text, length, fontChinese.getSize() / 2, getStringLength(firstLineIndent, fontChinese), pagewidth), fontChinese).setUnderline(-1f, -1f);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //表格中有下划线的空位 支持换行
     *
     * @param text
     * @param length
     * @param fontChinese
     * @return
     */
    public static Chunk tableunderLine(String text, float length, Font fontChinese, float firstLineIndent, float pagewidth) {
        Chunk targetChunk = null;
        try {
            targetChunk = new Chunk(stringFormatFixedLength(text, length, fontChinese.getSize() / 2, firstLineIndent, pagewidth), fontChinese).setUnderline(-1f, -1f);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetChunk;
    }

    /**
     * //为画checkbos计算位置 按左下角坐标定义
     *
     * @param leftXPoint
     * @param leftYPoint
     * @param borderlength
     * @return
     */
    public static Rectangle setCheckBoxArea(float leftXPoint, float leftYPoint, float borderlength) {
        float leftx = leftXPoint;
        float lefty = leftYPoint + borderlength;
        float rightx = leftXPoint + borderlength;
        float righty = leftYPoint;
        Rectangle endRectangle = new Rectangle(leftx, lefty, rightx, righty);
        return endRectangle;
    }

    /**
     * 获得倍数级别的适配长度 使得识别输入的不精确的数字
     *
     * @param fixlength
     * @param textsize
     */
    public static float formatlength(float fixlength, float textsize) {
        float result = 0;
        if (fixlength % textsize == 0) {
            result = fixlength;
        } else {
            float num = fixlength / textsize;
            result = num * textsize;
        }
        return result;
    }

    /**
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }



}
