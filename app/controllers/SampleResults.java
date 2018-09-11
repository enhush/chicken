package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.QueryWhere;
import models.SampleResult;
import models.User;
import play.libs.WS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Munguntsetseg on 2017-07-18.
 */
public class SampleResults extends CRUD {

    public static void list() {
        User user = Users.getUser();

        render(user);
    }

    public static void listData(int CurrentPageNumber) {
        User user = Users.getUser();
        int permission = user.getUserPermissionType(Consts.permissionAccount);

//        Doctor doctor = null;
//        if(user.userType_id == 1)
//            doctor = Doctor.find("user.id = ?1", user.id).first();

        QueryWhere queryWhere = listLoadQrOrg(user);
        String qr = queryWhere.qr, where = queryWhere.where;

        int pageLimit = 20, totalSize, MaxPageNumber;
        List<SampleResult> sampleResults = null;

        qr = "SELECT DISTINCT s FROM tb_sample_result s" + qr + where + "";
        totalSize = SampleResult.find(qr).fetch().size();
        MaxPageNumber = totalSize / pageLimit;
//        sampleResults = Farmer.find(qr).fetch(CurrentPageNumber, pageLimit);
        if (totalSize % pageLimit != 0) MaxPageNumber++;
        render( MaxPageNumber, CurrentPageNumber, permission, totalSize);
    }

    public static QueryWhere listLoadQrOrg(User user) {
        QueryWhere queryWhere = new QueryWhere();
        String ss, qr = "", where="";
        where = " 1=0";

//        if(user.userType_id == 0){
//            where = " 1=1 AND deleteFlag=0";
//        }
//        if(user.userType_id == 1) {
//            Doctor d = Doctor.find("user.id = ?1", user.id).first();
//            where = " s.vet_sum_id = " + d.sumName.vet_sum_id + " AND deleteFlag=0";
//        }
        if (where.length() > 0) where = " WHERE" + where ;
        queryWhere.where = where;
        queryWhere.qr = qr;
        return queryWhere;
    }

    public static void getSampleResult(Long sumNameId) {

        List<SampleResult> oldResults = new ArrayList<SampleResult>();
        if(sumNameId == 0){
            oldResults = SampleResult.findAll();
        }else {
            oldResults = SampleResult.find("vet_sum_id = ?1",sumNameId).fetch();
        }
        JsonArray resultMessage = new JsonArray();
        try {
            WS.HttpResponse response = WS.url(CompanyConf.url + "appGetSumSamples?api_key=26127b94&sumNameId=" + sumNameId).post();
            resultMessage = response.getJson().getAsJsonArray();

            List<SampleResult> newResults = new ArrayList<SampleResult>();
            if (resultMessage != null) {
                for (JsonElement element : resultMessage) {
                    JsonObject jo = element.getAsJsonObject();
                    SampleResult sam = new SampleResult();
                    sam.sample_id = jo.get("sample_id").getAsLong();
                    sam.number = jo.get("number").getAsString();
                    sam.purpose = jo.get("purpose").getAsString();
                    sam.recieve_date = appDate(jo.get("recieve_date").getAsString());
                    sam.result_date = appDate1(jo.get("result_date").getAsString());
                    sam.vet_sum_id = jo.get("vet_sum_id").getAsLong();
//                    sam.sumName = SumName.find("vet_sum_id = ?1",jo.get("vet_sum_id").getAsLong()).first();
                    sam.pdfUrl = jo.get("pdfUrl").getAsString();
                    if(jo.get("pdfUrl").getAsString().length()==0){
                        sam.path = "";
                    }else{
                        sam.path = "http://vet.shinjilgee.mn/FileCenter/SamplePdf/"+jo.get("pdfUrl").getAsString()+".pdf";
                    }
                    newResults.add(sam);
                }
            }
            //delete
            for(SampleResult res: oldResults){
                int delete = 0;
                for (SampleResult res1 : newResults) {
                    if (res.sample_id.equals(res1.sample_id) &&  res.vet_sum_id.equals(res1.vet_sum_id)){
                        delete++;
                    }
                }
                if(delete ==0){
                    res.delete();
                }
            }
            //save
            for (SampleResult newResult : newResults) {
                int check = 0;
                for (SampleResult oldResult : oldResults) {
                    if (oldResult.sample_id.equals(newResult.sample_id) &&  oldResult.vet_sum_id.equals(newResult.vet_sum_id)){
                        check++;
                    }
                }
                SampleResult res = new SampleResult();
                if (check == 0) {
                    res.sample_id = newResult.sample_id;
                    res.number = newResult.number;
                    res.purpose = newResult.purpose;
                    res.recieve_date = newResult.recieve_date;
                    res.result_date = newResult.result_date;
                    res.vet_sum_id = newResult.vet_sum_id;;
//                    res.sumName = newResult.sumName;
                    res.pdfUrl = newResult.pdfUrl;
                    res.path = newResult.path;
                    res.create();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJSON(resultMessage);
    }

    public static Date appDate(String date) {
        try {
            SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return myDateFormat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date appDate1(String date) {
        try {
            SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return myDateFormat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

}
