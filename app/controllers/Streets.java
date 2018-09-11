package controllers;

import models.Street;
import play.mvc.With;

import java.util.List;

/**
 * Created by unurbayar on 6/29/2018.
 */
@With(Secure.class)
public class Streets extends CRUD{
//    public static void find_khoroos(Long district_id) {
//        List<Street> streets = Street.find("s.id=?1", district_id).fetch();
//        render(streets);
//    }
}
