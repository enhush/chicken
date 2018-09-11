package controllers;


import org.apache.commons.io.IOUtils;
import play.Play;
import play.mvc.Controller;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Calendar;
import java.util.Date;


public class FileUploader extends Controller {

    public static void uploadFile(String uploadPath, String filename) throws Exception {
        String extension = "null";
        String FileNameOrig = "";
        boolean success = true;
        String filesize = "0";
        String errorMessege = "Хуулах явцад алдаа гарлаа";
        if (request.isNew) {
            FileOutputStream moveTo = null;
            try {
                String[] length = filename.split("\\.");
                for (int fi = 0; fi < length.length - 1; fi++) FileNameOrig += length[fi];
                extension = length[length.length - 1];
                Date currentDate = new Date();
                filename = currentDate.getTime() + "";
                // huuchin ni body, shine ni qqfile-aar upload hiij bga
                InputStream data = request.body;

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                uploadPath = uploadPath + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE) + "/";
                String absolutePath = Functions.cleanUrl(Play.applicationPath.getAbsolutePath() + "/" + uploadPath);

                File f = new File(absolutePath);
                if (!f.exists()) {
                    if (!f.mkdirs()) throw new Exception("Unable to create upload dir");
                }
                absolutePath = Functions.cleanUrl(absolutePath + "/" + filename + "." + extension);
                File copy = new File(absolutePath);
                moveTo = new FileOutputStream(copy);

                IOUtils.copy(data, moveTo);
                moveTo.close();
                filesize = Functions.getFloatFormat((float) copy.length() / 1024 / 1024, 2);
                if (isImage(extension)) {        //to small
                    ConvertToImage convertToImage = new ConvertToImage();
                    if (!uploadPath.startsWith("/FolderShare/"))
                        if (!convertToImage.convert(uploadPath + "/" + filename, extension, 400, 400, "none", true))
                            success = false;
                }
                uploadPath += filename;
            } catch (Exception ex) {
                ex.printStackTrace();
                if (moveTo != null)
                    moveTo.close();
                success = false;
                errorMessege = "Хуулах явцад алдаа гарлаа";
            }
        }
        renderText("{\"success\":" + success + ",\"filedir\":\"" + uploadPath + "\",\"filename\":\"" + FileNameOrig + "\",\"extension\":\"" + extension + "\",\"filesize\":\"" + filesize + "\""
                + (!success ? ",\"error\":\"" + errorMessege + "\"" : "") + "}");
    }

    public static void uploadFileNew(String uploadPath, String qqfilename, File qqfile) throws Exception {
        String extension = "null";
        String FileNameOrig = "";
        boolean success = true;
        String filesize = "0";
        String errorMessege = "Хуулах явцад алдаа гарлаа";
        if (request.isNew) {
            FileOutputStream moveTo = null;
            try {
                String[] length = qqfilename.split("\\.");
                for (int fi = 0; fi < length.length - 1; fi++) FileNameOrig += length[fi];
                extension = length[length.length - 1];
                Date currentDate = new Date();
                qqfilename = currentDate.getTime() + "";
                // huuchin ni body, shine ni qqfile-aar upload hiij bga
                InputStream data = new FileInputStream(qqfile);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                uploadPath = uploadPath + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE) + "/";
                String absolutePath = Functions.cleanUrl(Play.applicationPath.getAbsolutePath() + "/" + uploadPath);

                File f = new File(absolutePath);
                if (!f.exists()) {
                    if (!f.mkdirs()) throw new Exception("Unable to create upload dir");
                }
                absolutePath = Functions.cleanUrl(absolutePath + "/" + qqfilename + "." + extension);
                File copy = new File(absolutePath);
                moveTo = new FileOutputStream(copy);

                IOUtils.copy(data, moveTo);
                moveTo.close();
                filesize = Functions.getFloatFormat((float) copy.length() / 1024 / 1024, 2);
                if (isImage(extension)) {        //to small
                    ConvertToImage convertToImage = new ConvertToImage();
                    if (!uploadPath.startsWith("/FolderShare/"))
                        if (!convertToImage.convert(uploadPath + "/" + qqfilename, extension, 400, 400, "none", true))
                            success = false;
                }
                uploadPath += qqfilename;
            } catch (Exception ex) {
                ex.printStackTrace();
                if (moveTo != null)
                    moveTo.close();
                success = false;
                errorMessege = "Хуулах явцад алдаа гарлаа";
            }
        }
        renderText("{\"success\":" + success + ",\"filedir\":\"" + uploadPath + "\",\"filename\":\"" + FileNameOrig + "\",\"extension\":\"" + extension + "\",\"filesize\":\"" + filesize + "\""
                + (!success ? ",\"error\":\"" + errorMessege + "\"" : "") + "}");
    }

    public static void uploadFileCustom(String uploadPath, String qqfilename, File qqfile, String ratio) throws Exception {
        String uploadedFileName;
        int rw, rh;
        rw = Integer.parseInt(ratio.split("x")[0]);
        rh = Integer.parseInt(ratio.split("x")[1]);
        boolean success = true;
        String extension = "null";
        String errorMessege = "Хуулах явцад алдаа гарлаа";
        if (request.isNew) {
            FileOutputStream moveTo = null;
            try {
                InputStream data = new FileInputStream(qqfile);
                String[] length = qqfilename.split("\\.");
                extension = length[length.length - 1];
                Date currentDate = new Date();
                qqfilename = currentDate.getTime() + "";
                uploadPath += qqfilename;
                uploadedFileName = Play.applicationPath.getAbsolutePath() + uploadPath + "." + extension;
                moveTo = new FileOutputStream(new File(uploadedFileName));
                IOUtils.copy(data, moveTo);
                moveTo.close();
                ConvertToImage convertToImage = new ConvertToImage();
                if (!convertToImage.convert(uploadPath, extension, rw, rh, "none", false)) {
                    new File(Play.applicationPath.getAbsolutePath() + uploadPath + "." + extension).delete();
                    success = false;
                }
                if (!extension.endsWith("jpg")) {
                    new File(Play.applicationPath.getAbsolutePath() + uploadPath + "." + extension).delete();
                    extension = "jpg";
                }
            } catch (Exception ex) {
                success = false;
                ex.printStackTrace();
                if (moveTo != null)
                    moveTo.close();
            }
        }
        renderText("{\"success\":" + success + ",\"filedir\":\"" + uploadPath + "\",\"filename\":\"" + qqfilename + "\",\"extension\":\"" + extension + "\""
                + (!success ? ",\"error\":\"" + errorMessege + "\"" : "") + "}");
    }

    public static boolean isImage(String ext) {
        return (Consts.imageFileExtensions.contains(ext));
    }

    public static void deleteUploadFile(String fileDir, String extension) {
        System.out.println(request.headers);
        System.out.println(request.params);
        try {
            File file = new File(Play.applicationPath.getAbsolutePath() + fileDir + "." + extension);
            if (file.exists()) file.delete();
            if (!fileDir.startsWith("/FolderShare/") && isImage(extension)) {
                file = new File(Play.applicationPath.getAbsolutePath() + fileDir + "t.jpg");
                if (file.exists()) file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encodeToString(BufferedImage image) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static String encodeToBase64(String path) {
        ConvertToImage convertToImage = new ConvertToImage();
        return "data:image/png;base64," + encodeToString(convertToImage.convertFileToBufferedImage(path));
    }

    public static String decodeToImage(String path, String imageString) {
        BufferedImage image;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            path = path + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE) + "/";
            File f = new File(Play.applicationPath.getAbsolutePath() + "/" + path);
            if (!f.exists()) {
                if (!f.mkdirs()) throw new Exception("Unable to create upload dir");
            }
            path += calendar.getTimeInMillis() + ".png";
            ImageIO.write(image, "png", new File(Play.applicationPath.getAbsolutePath() + "/" + path));
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
