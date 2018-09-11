package controllers;

import java.text.SimpleDateFormat;

public class Consts {

    public static final String [] type = {"Байгууллага","Хувь хүн"};
    public static final String [] customerType = {"Үйлчлүүлэгч" ,"Ажилтан"};
    public static final String [] productType = {"Хоол" ,"Уух зүйл"};
    public static final String [] paymentType = {"Бэлэн мөнгө" ,"Картын машин", "Мобайл/интернет банк", "Бусад"};
    public static final String [] otherPayment = {"Ажилчид цалингаас зээлээр" ,"Маркетингийн зардалд", "Захирлын хэрэгцээнд"};
    public static final String [] status = {"Ноорог","Хүлээгдэж байгаа", "Хүргэгдсэн", "Төлбөр төлөгдсөн", "Бэлэн бусаар төлөгдсөн"};
    public static final String [] doctor_type = {"Нэгжийн эмч","Тасгийн эмч"};
    public static final String [] stockState = {"Шинэ","Хуучин"};

    public static final String detailedFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String detailedFormat2 = "yy-MM-dd HH:mm";
    public static final SimpleDateFormat myDateFormat = new SimpleDateFormat(detailedFormat);
    public static final SimpleDateFormat myDateFormat2 = new SimpleDateFormat(detailedFormat2);
    public static final SimpleDateFormat dateFormat2 = new SimpleDateFormat("YYYY/MM/dd");
    public static final SimpleDateFormat dateFormatAPI = new SimpleDateFormat("yyyy-MM-dd");

    public static final String dateFormat = "yyyy-MM-dd";
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

    public static final int maxDescriptionLength = 10000;

    public static final String uploadUserImagePath = "/public/userImages/";
    public static final String uploadCoverImagePath = "/FileCenter/Dashboard/cover/";
    public static final String uploadPostPath = "/FileCenter/PostPath/";
    public static final String uploadChatPath = "/FileCenter/ChatPath/";
    public static final String animalPicture = "/FileCenter/Animal/";
    public static final String uploadMegPdf = "/FileCenter/MegPfd/";

    public static final String permissionDashboard = "dashboard";
    public static final String permissionAccount = "account";
    public static final String permissionFileShare = "file";
    public static final String permissionReport = "report";
    public static final String permissionIngredient = "ingredient";
    public static final String permissionProduct = "product";
    public static final String permissionOrder = "order";
    public static final String permissionCar = "car";
    public static final String permissionStock = "stock";

    public static final String imageFileExtensions = "gif,jpg,jpeg,png,bmp,tiff,GIF,JPG,JPEG,PNG,BMP,";
    public static final String imageFileType = "image";
    public static final String imageFileIcon = "/public/images/fileIcon/picture-icon.png";
    public static final String pdfFileExtensions = "pdf,PDF,";
    public static final String pdfFileIcon = "/public/images/fileIcon/pdf-icon.png";
    public static final String wordFileExtensions = "docx,doc,DOCX,DOC,";
    public static final String wordFileIcon = "/public/images/fileIcon/word-icon.png";
    public static final String excelFileExtensions = "xls,xlsx,XLS,XLSX,";
    public static final String excelFileIcon = "/public/images/fileIcon/excel-icon.png";
    public static final String powerPointFileExtensions = "ppt,pptx,PPT,PPTX,";
    public static final String powerPointFileIcon = "/public/images/fileIcon/powerpoint-icon.png";
    public static final String autoCadFileExtensions = "dwg,dxf,dgn,DWG,DXF,DGN";
    public static final String autoCadFileIcon = "/public/images/fileIcon/autocad-icon.png";
    public static final String videoFileExtensions = "avi,wmv,rm,rmvb,mp4,mpeg,mkv,AVI,WMV,RM,RMVB,MP4,MPEG,MKV,";
    public static final String videoFileIcon = "/public/images/fileIcon/video-icon.png";
    public static final String viewableVideoExtensions = "flv,swf,FLV,SWF,";
    public static final String viewableVideoType = "viewableVideo";
    public static final String viewableVideoIcon = "/public/images/fileIcon/viewable-video-icon.png";
    public static final String otherFileIcon = "/public/images/fileIcon/other-icon.png";

    public static final String fileStock = "/FileCenter/Stock/";

}