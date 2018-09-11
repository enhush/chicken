package controllers;

import com.google.gson.JsonObject;
import models.Address;
import models.Apartment;
import play.mvc.With;

import java.util.List;

/**
 * Created by unurbayar on 6/29/2018.
 */
@With(Secure.class)
public class Addresses extends CRUD {

    public static void find_addresses(String phone) {
        String qr = "phoneNumber='" + phone + "'";
        List<Address> addresses = Address.find(qr).fetch();
        render(addresses);
    }

    public static void set_addresses(Long id) {
        Address address = Address.findById(id);
        render(address);
    }

}
