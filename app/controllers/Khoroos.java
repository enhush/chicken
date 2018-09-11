package controllers;

import models.Khoroo;
import play.mvc.With;

import java.util.List;

/**
 * Created by unurbayar on 6/29/2018.
 */
@With(Secure.class)
public class Khoroos extends CRUD{

    public static void find_khoroos(Long district_id) {
        List<Khoroo> khoroos = Khoroo.find("district.id=?1", district_id).fetch();
        render(khoroos);
    }
}
