package controllers;

import com.google.gson.JsonObject;
import models.ChatMessage;
import org.apache.commons.codec.binary.Base64;
import play.Play;
import play.classloading.enhancers.LocalvariablesNamesEnhancer;
import play.db.jpa.Model;
import play.libs.WS;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Functions {
    public static String[] monthName = {"Нэгдүгээр сар", "Хоёрдугаар cар", "Гуравдугаар сар", "Дөрөвдүгээр сар", "Тавдугаар сар", "Зургаадугаар сар", "Долоодугаар сар", "Наймдугаар сар", "Есдүгээр сар", "Аравдугаар сар", "Арваннэгдүгээр сар", "Арванхоёрдугаар сар"};
    public static String[] monthNameShort = {"1сар", "2cар", "3сар", "4сар", "5сар", "6сар", "7сар", "8сар", "9сар", "10сар", "11сар", "12сар"};
    public static String[] dayNames = {"Ням", "Даваа", "Мягмар", "Лхагва", "Пүрэв", "Баасан", "Бямба"};
    public static String[] dayName2 = {"Даваа", "Мягмар", "Лхагва", "Пүрэв", "Баасан", "Бямба", "Ням"};
    public static String[] dayNamesMin = {"Ня", "Да", "Мя", "Лх", "Пү", "Ба", "Бя"};
    public static String[] dayNamesShort = {"Ням", "Дав", "Мяг", "Лха", "Пүр", "Баа", "Бям"};

    public static String[] fileShareExtensions = {"Бүгд", "Зураг", "MP4", "PDF", "Office"};
    public static String[] fileShareExtensionTypes = {"gif,jpg,jpeg,png,bmp,tiff,dwg,dxf,dgn,pdf,docx,doc,xls,xlsx,ppt,pptx,mpp,vsd"
            , "gif,jpg,jpeg,png,bmp,tiff,", "mp4,", "pdf", "docx,doc,xls,xlsx,ppt,pptx,mpp,vsd"};

    public static String getUsernameUniqueString(String username) {
        return Functions.getSha1String(Functions.getSha1String(username) + "Pm1s#Se55i0n%1");
    }

    public static String generateMegNumber(Long megId){
        return "Meg"+megId;
    }

    public static String generateMegQR(Long megId){
        return "LTS-QR-"+megId;
    }

    public static String getMonthName(Date date) {
        String strDateFormat = "MMMM";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }

    public static String getDateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return Functions.monthName[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DATE) + " " + Functions.dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + new SimpleDateFormat("HH").format(date) + ':' + new SimpleDateFormat("mm").format(date);
    }

    public static Calendar setCalDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTimeZone(TimeZone.getTimeZone("Asia/Ulaanbaatar"));
        return cal;
    }


    public static Date convertHourNull(Date date) {
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        date = new Date(calendar.get(Calendar.YEAR) - 1900,
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        return date;
    }

    public static Date convertMinutNull(Date date) {
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
    }

    public static Date addOrMinusDays(Date incomingDate, int dayCount, boolean add) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(incomingDate);
        if (add) {
            cal.add(Calendar.DATE, +dayCount); //minus number would decrement the days
        } else {
            cal.add(Calendar.DATE, -dayCount); //minus number would decrement the days
        }
        return cal.getTime();
    }

    public static ArrayList<Long> ids;

    public static List<?> subtractEntityLists(List<?> list1, List<?> list2) {
        for (Model model2 : (List<Model>) list2)
            for (Model model1 : (List<Model>) list1)
                if (model1.id.longValue() == model2.id.longValue()) {
                    list1.remove(model1);
                    break;
                }
        return list1;
    }

    public static String[] CalendarList(String dateVal, int action) {

        int mm = Integer.parseInt(dateVal.split("-")[1]) - 1;
        int yy = 0;
        String cals[] = new String[8];
        int day;
        if (action == 0) day = Integer.parseInt(dateVal.split("-")[2]);
        else {
            if (action == 1 && mm == 12) {
                yy = 1;
                mm = 0;
            }
            if (action == -1 && mm == 1) {
                yy = -1;
                mm = 13;
            }
            day = 1;
        }
        Date date = new Date(Integer.parseInt(dateVal.split("-")[0]) - 1900 + yy,
                mm + action,
                Integer.parseInt(dateVal.split("-")[2]));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cals[0] = cal.get(Calendar.DATE) + "";
        if (action == 1) cals[0] = "1";
        if (action == -1) cals[0] = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + "";
        cals[1] = (cal.get(Calendar.MONTH) + 1) + "";
        cals[2] = cal.get(Calendar.YEAR) + "";
        cals[3] = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + "";
        if ((cal.get(Calendar.MONTH) - 1) < 0) cals[4] = monthName[11];
        else cals[4] = monthName[cal.get(Calendar.MONTH) - 1];
        cals[5] = monthName[cal.get(Calendar.MONTH)];
        if ((cal.get(Calendar.MONTH) + 1) > 11) cals[6] = monthName[0];
        else cals[6] = monthName[cal.get(Calendar.MONTH) + 1];
        return cals;
    }

    public static Date PrevNextDay(Date date, int prevNext) {
        Calendar origDay = Calendar.getInstance();
        origDay.setTime(date);
        Calendar nextDay = (Calendar) origDay.clone();
        if (prevNext > 0) nextDay.add(Calendar.DAY_OF_YEAR, 1);
        else nextDay.add(Calendar.DAY_OF_YEAR, -1);
        return nextDay.getTime();
    }

    public static Date StringToDate(String text) {
        String sss = text.split("-")[2];
        String ss = sss.split(" ")[1];
        return new Date(2000 + Integer.parseInt(text.split("-")[0]) - 1900, Integer.parseInt(text.split("-")[1]) - 1,
                Integer.parseInt(sss.split(" ")[0]), Integer.parseInt(ss.split(":")[0]), Integer.parseInt(ss.split(":")[1]));
    }

    public static Date StringToDate2(String text) {
        String sss = text.split("-")[2];
        String ss = sss.split(" ")[1];
        return new Date(Integer.parseInt(text.split("-")[0]) - 1900, Integer.parseInt(text.split("-")[1]) - 1,
                Integer.parseInt(sss.split(" ")[0]), Integer.parseInt(ss.split(":")[0]), Integer.parseInt(ss.split(":")[1]));
    }

    public static Date StringToDay(String text) {
        int year;
        if (Integer.parseInt(text.split("-")[0]) > 2000) year = Integer.parseInt(text.split("-")[0]);
        else year = 2000 + Integer.parseInt(text.split("-")[0]);
        return new Date(year - 1900, Integer.parseInt(text.split("-")[1]) - 1,
                Integer.parseInt(text.split("-")[2]));
    }

    public static List<String[]> buildNavigation(String[][] navigationRaw) {

        List<String[]> navigations = new ArrayList<String[]>();
        for (String[] nav : navigationRaw)
            navigations.add(new String[]{nav[0], nav[1]});

        return navigations;

    }

    public static String getSha1String(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] sha1hash = new byte[40];
            md.update(pass.getBytes("iso-8859-1"), 0, pass.length());
            sha1hash = md.digest();
            return convertToHex(sha1hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }


    //Author: bb1987
    //Traverses and saves nodes of given dir
    private static void traverse(File dir, List<Object[]> dirs) {

        if (dir.isFile()) dirs.add(new Object[]{null, dir});

        else if (dir.isDirectory()) {
            List<Object[]> dirStruct = new ArrayList<Object[]>();
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                traverse(new File(dir, children[i]), dirStruct);
            }
            dirs.add(0, new Object[]{dir, dirStruct});
        }

    }

    private static final char[] PASSWORD = "sldkfjsldkfj434sdf_+-5678djfjf_-h".toCharArray();
    private static final byte[] SALT = {
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
            (byte) 0xdc, (byte) 0x83, (byte) 0x13, (byte) 0x02,
    };

    public static String encrypt(String property) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
            return base64Encode(pbeCipher.doFinal(property.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String base64Encode(byte[] bytes) {
        // NB: This class is internal, and you probably should use another impl
        return new String(Base64.encodeBase64URLSafe(bytes));
//        return new BASE64Encoder().encode(bytes);
    }

    public static String decrypt(String property) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
            return new String(pbeCipher.doFinal(base64Decode(property)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] base64Decode(String property) throws IOException {
        // NB: This class is internal, and you probably should use another impl
        return Base64.decodeBase64(property);
//        return new BASE64Decoder().decodeBuffer(property);
    }

    public static String encodeURIComponent(String s) {
        String result = null;

        try {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }
    public static void sendNotificationPhone(JsonObject object, String device) {
        if (device != null && !device.equalsIgnoreCase("")) {
            if (device.equals("web")) {
                //mn.ikhgur AIzaSyA1CFq4VPJkEIODP8-KFdd-Ji0F6fNh1Vw
                // mn.progex AIzaSyDolU8H5qJvkyKyTnSUaDZZOlxmZ8axxwA
                try {
                    WS.HttpResponse response =
                            WS.url("https://fcm.googleapis.com/fcm/send").setHeader("Content-Type", "application/json").setHeader("Authorization", "key=AIzaSyBs9nEVfO8aGqJAlLoFtOR6Y99Dq2Je16o").body(object).post();
                    System.out.println("FCM response status " + response.getStatusText() + " String" + response.getString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Map<String, Object> getTemplateVariables(Object... args) {
        Map<String, Object> templateBinding = new HashMap<String, Object>();
        for (Object o : args) {
            List<String> names = LocalvariablesNamesEnhancer.LocalVariablesNamesTracer.getAllLocalVariableNames(o);
            for (String name : names) {
                templateBinding.put(name, o);
            }
        }
        return templateBinding;
    }

    public static String cleanUrl(String url) {
        return url.replace("//", "/").replace("///", "/");
    }

    public static String cleanUrlSlash(String url) {
        String val = "";
        for (int i = 0; i < url.length(); i++) {
            if ((int) url.charAt(i) == 92) val += "/";
            else val += url.charAt(i);
        }
        return val;
    }

    public static int showRandomInteger(int aStart, int aEnd, Random aRandom) {
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long) aEnd - (long) aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * aRandom.nextDouble());
        return (int) (fraction + aStart);
    }

    public static String getDecimal(Long val) {
        int c = 1;
        String vv = "", value = val.toString();
        for (int i = value.length() - 1; i >= 0; i--) {
            if (i != 0 && c == 3) {
                vv += value.charAt(i) + "";
                c = 1;
            } else {
                vv += value.charAt(i);
                c++;
            }
        }
        value = "";
        for (int i = vv.length() - 1; i >= 0; i--) {
            value += vv.charAt(i);
        }
        return value;
    }


    public static String getFloatFormat(float floatVar, int dec) {
        DecimalFormat format;
        if (dec == 1) format = new DecimalFormat("#.#");
        else if (dec == 2) format = new DecimalFormat("#.##");
        else format = new DecimalFormat("#.###");
        if (floatVar == Math.floor(floatVar)) return Integer.toString((int) floatVar);
        else return format.format(floatVar);
    }

    public static float durationMinute(Date dateS, Date dateF) {
        long diff = dateF.getTime() - dateS.getTime();
        return (float) diff / (60 * 1000);
    }

    public static String durationToText(float minutes) {
        String txt = "";
        int vv = (int) (minutes / 60);
        if (vv > 0) txt += vv + " цаг";
        vv = (int) minutes - (vv * 60);
        if (vv > 0) {
            if (txt.length() > 0) txt += " ";
            txt += vv + " минут";
        }
        return txt;
    }

    public static Date getFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    public static String getDateOrTime(Timestamp date) {
        Date now = Functions.convertHourNull(new Date());
        if (now.before(date)) return (new SimpleDateFormat("HH:mm")).format(date);
        return (new SimpleDateFormat("MM/dd HH:mm")).format(date);
    }

    public static String getLateDate(Date date) {
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        long d = diff / (24 * 60 * 60 * 1000);
        if (d > 0) return (d + 1) + " өдрийн өмнө";
        else {
            d = diff / (60 * 60 * 1000) % 24;
            if (d > 0) return d + " цагийн өмнө";
            else {
                d = diff / (60 * 1000) % 60;
                if (d > 0) return d + " минутын өмнө";
                else {
                    d = diff / 1000 % 60;
                    if (d > 0) return d + " секундын өмнө";
                }
            }
        }
        return "Яг одоо";
    }

    public static void deleteFileSingle(String fileDir) {
        try {
            File file = new File(Play.applicationPath.getAbsolutePath() + fileDir);
            if (file.exists()) file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getDifferenceDays(Date d1, Date d2) {
        long diffDays = 0;
        int minus = 1;
        if (d1 != null && d2 != null) {
            if (d1.getTime() > d2.getTime()) {
                Date dd = d1;
                d1 = d2;
                d2 = dd;
                minus = -1;
            }
            long diff = d2.getTime() - d1.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000) + 1;
        }
        return (int) diffDays * minus;
    }

    public static boolean isBefor30Minut(Date prevDate, Date nextDate) {
        return (((nextDate.getTime() - prevDate.getTime()) / (60 * 1000)) > 30);
    }

    public static int randomNumber(int max) {
        Random generator = new Random();
        max--;
        int randomPic = generator.nextInt(max);
        randomPic++;
        return randomPic;
    }

    public static long getCountUnreadMessage(Long remoteId, Long userId) {
        return ChatMessage.count("msgSender.id=?1 AND msgReceiver.id=?2 AND readed=false", remoteId, userId);
    }

    public static boolean isImage(String ext) {
        return (Consts.imageFileExtensions.contains(ext));
    }

    public static void deleteUploadFile(String fileDir, String extension) {
        try {
            File file = new File(Play.applicationPath.getAbsolutePath() + fileDir + "." + extension);
            if (file.exists()) {
                file.delete();
                if (isImage(extension)) {
                    file = new File(Play.applicationPath.getAbsolutePath() + fileDir + "t.jpg");
                    if (file.exists()) file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String handleDocumentAttachment(String fileDir, String fileName, String extension, Float filesize) {
        String img = "<img src='#src' class='imgIcon' fileDir='#fileDir' type='#type' fileName='#fileName' extension='#extension' filesize='" + (filesize != null ? filesize : "") + "'>";
        if (Consts.imageFileExtensions.contains(extension)) {
            img = img.replace("#src", fileDir + "t.jpg")
                    .replace("#type", Consts.imageFileType);
            img = "<span class='fancybox-buttons' data-fancybox-group='button' href='" + fileDir + "." + extension + "'>" + img + "</span>";
        } else if (Consts.wordFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.wordFileIcon);
        } else if (Consts.excelFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.excelFileIcon);
        } else if (Consts.powerPointFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.powerPointFileIcon);
        } else if (Consts.autoCadFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.autoCadFileIcon);
        } else if (Consts.pdfFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.pdfFileIcon);
        } else if (Consts.videoFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.videoFileIcon);
        } else if (Consts.viewableVideoExtensions.contains(extension)) {
            img = img.replace("#src", Consts.viewableVideoIcon).replace("#type", Consts.viewableVideoType);
        } else {
            img = img.replace("#src", Consts.otherFileIcon);
        }
        img = img.replace("#fileDir", fileDir).replace("#fileName", fileName).replace("#extension", extension);

        return img;
    }

    public static String handleDocumentAttachmentImgGallery(String id) {
        return "<script>" +
                "$('#" + id + " span.fancybox-buttons').fancybox({" +
                "closeBtn: false," +
                "helpers: {" +
                "title: {type: 'inside'}," +
                "thumbs: {width: 50,height: 50}," +
                "buttons: {}}," +
                "tpl: {next:\"<a title='" + play.i18n.Messages.get("Next") + "' class='fancybox-nav fancybox-next'><span></span></a>\"" +
                ",prev:\"<a title='" + play.i18n.Messages.get("Previous") + "' class='fancybox-nav fancybox-prev'><span></span></a>\"}," +
                "afterLoad: function () {" +
                "this.title = 'Зураг ' + (this.index + 1) + ' / ' + this.group.length + (this.title ? ' - ' + this.title : '');}" +
                "})</script>";
    }

    public static String handleDocumentIcon(String fileDir, String fileName, String extension) {

        String img = "<img src='#src' class='imgIcon' fileDir='#fileDir' type='#type'" +
                " fileName='#fileName' extension='#extension' style='height: 18px;vertical-align: bottom;margin-left:2px'>";
        if (Consts.imageFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.imageFileIcon);
        } else if (Consts.wordFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.wordFileIcon);
        } else if (Consts.excelFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.excelFileIcon);
        } else if (Consts.powerPointFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.powerPointFileIcon);
        } else if (Consts.autoCadFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.autoCadFileIcon);
        } else if (Consts.pdfFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.pdfFileIcon);
        } else if (Consts.videoFileExtensions.contains(extension)) {
            img = img.replace("#src", Consts.videoFileIcon);
        } else if (Consts.viewableVideoExtensions.contains(extension)) {
            img = img.replace("#src", Consts.viewableVideoIcon).replace("#type", Consts.viewableVideoType);
        } else {
            img = img.replace("#src", Consts.otherFileIcon);
        }
        img = img.replace("#fileDir", fileDir).replace("#fileName", fileName).replace("#extension", extension);

        return img;
    }

    public static String handleDocumentAttachmentDel(String fileDir, String fileName, String extension) {
        return "<img height=12 width=12 src='/public/images/delete-image.png' class='deleteUploadedImage' fileDir='" + fileDir + "' extension='" + extension + "'>";

    }

    public static String handleDocumentAttachmentNonDel(String fileDir, String fileName, String extension) {
        return "<img height=12 width=12 src='/public/images/delete-image.png' class='deleteImage' fileDir='" + fileDir + "' extension='" + extension + "'>";
    }

    public static boolean checkString(String param[]) {
        return param != null && param.length > 0 && param[0] != null && param[0].length() > 0;
    }

    public static String getWhere(String where) {
        return where.length() > 0 ? " AND" : "";
    }
}