package controllers;

import models.Apartment;
import models.Khoroo;
import play.mvc.With;

import java.util.List;

/**
 * Created by unurbayar on 6/29/2018.
 */
@With(Secure.class)
public class Apartments extends CRUD{
    public static void find_apartments(Long khoroo_id) {
        List<Apartment> apartments = Apartment.find("khoroo.id=?1", khoroo_id).fetch();
        render(apartments);
    }

//    public static void find_apartments_by_district(Long khoroo_id) {
//        List<Apartment> apartments = Apartment.find("khoroo.id=?1", khoroo_id).fetch();
//        render(apartments);
//    }
}
