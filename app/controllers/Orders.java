package controllers;

import models.*;
import org.joda.time.DateTime;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by unurbayar on 7/2/2018.
 */
@With(Secure.class)
public class Orders extends CRUD {
    public static void list(int page, String search, String searchFields, String orderBy, String order) {
        User user = Users.getUser();
        if (!(user.getUserPermissionType(Consts.permissionOrder) > 0)) forbidden();

        CRUD.ObjectType type = CRUD.ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        if (orderBy == null) {
            orderBy = "name";
            order = "ASC";
        }
        String where = (String) request.args.get("where");

        List<Model> objects = type.findPage(page, search, searchFields, orderBy, order, where);
        Long count = type.count(search, searchFields, where);
        Long totalCount = type.count(null, null, where);
        render(type, objects, count, totalCount, page, orderBy, order, search);
    }

    public static void show(Long id) throws Exception {
        User user = Users.getUser();
        if (!(user.getUserPermissionType(Consts.permissionOrder) > 0)) forbidden();

        CRUD.ObjectType type = CRUD.ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Order object = Order.findById(id);
        notFoundIfNull(object);
        List<Product> products = Product.findAll();
        List<User> users = User.findAll();
        String qr = "phoneNumber='" + object.phoneNumber + "'";
        List<Address> addresses = Address.find(qr).fetch();
        List<District> districts = District.findAll();
        List<Khoroo> khoroos = Khoroo.findAll();
        List<Apartment> apartments = Apartment.findAll();
        List<Street> streets = Street.findAll();
        try {
            render(type, object, products, users, addresses, districts, khoroos, apartments, streets);
        } catch (TemplateNotFoundException e) {
            render("CRUD/show.html", type, object, products, users, addresses, districts, khoroos, apartments, streets);
        }
    }

    public static void save(Long id, String object_takeDate, String object_endDate, Long object_address, Long add_district, Long add_khoroo, String add_street,
                            String add_apartment, String add_orts, String add_floor, String add_door, String add_code, String add_detail,
                            List<Long> product_name, List<Long> product_price, List<Integer> product_count, List<Long> total_price) throws Exception {
        User user = Users.getUser();
        if (!(user.getUserPermissionType(Consts.permissionOrder) > 0)) forbidden();
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Order object = Order.findById(id);
        notFoundIfNull(object);
        Binder.bindBean(params.getRootParamNode(), "object", object);
        validation.valid(object);
        if (validation.hasErrors()) {
//            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
//            try {
//                render(request.controller.replace(".", "/") + "/show.html", type, object);
//            } catch (TemplateNotFoundException e) {
//                render("CRUD/show.html", type, object);
//            }
        }
        object._save();
        if(object_takeDate != null && !object_takeDate.isEmpty()) object.takeDate = Consts.myDateFormat.parse(object_takeDate);
        else object.takeDate = null;
        if(object_endDate != null && !object_endDate.isEmpty()) object.endDate = Consts.myDateFormat.parse(object_endDate);
        else object.endDate = null;
        object.status = 1;
        object._save();

        if (object_address != 0) {
            object.address = Address.findById(object_address);
            object._save();
        } else {
            Address address = new Address();
            if (add_district != 0) address.district = District.findById(add_district);
            if (add_khoroo != 0) address.khoroo = Khoroo.findById(add_khoroo);
            String qr = "name='" + add_street + "'";
            Street street = Street.find(qr).first();
            if (street == null) {
                street = new Street();
                street.name = add_street;
                street.create();
            }
            address.street = street;
            String qr2 = "khoroo_id=" + add_khoroo + " AND name='" + add_apartment + "'";
            Apartment apartment = Apartment.find(qr2).first();
            if (apartment == null) {
                apartment = new Apartment();
                apartment.name = add_apartment;
                apartment.khoroo = Khoroo.findById(add_khoroo);
                apartment.create();
            }
            address.apartment = apartment;
            address.orts = add_orts;
            address.floor = add_floor;
            address.door = add_door;
            address.doorCode = add_code;
            address.detail = add_detail;
            address.phoneNumber = object.phoneNumber;
            address.create();
            object.address = address;
            object._save();

        }

        for (OrderProductRelation rel : object.orderProductRelations) rel._delete();
        if (product_name != null) {
            for (int i = 0; i < product_name.size(); i++) {
                OrderProductRelation orderProductRelation = new OrderProductRelation();
                orderProductRelation.product = Product.findById(Long.valueOf(product_name.get(i)));
                orderProductRelation.order = object;
                orderProductRelation.count = product_count.get(i);
                orderProductRelation.price = total_price.get(i);
                orderProductRelation.create();
            }
        }

        flash.success(play.i18n.Messages.get("crud.saved", ""));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        redirect(request.controller + ".show", object._key());
    }

    @Check(Consts.permissionOrder)
    public static void blank() throws Exception {
        CRUD.ObjectType type = CRUD.ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Model object = (Model) constructor.newInstance();
        List<Product> products = Product.findAll();
        List<User> users = User.findAll();
        List<Address> addresses = Address.findAll();
        List<District> districts = District.findAll();
        List<Khoroo> khoroos = Khoroo.findAll();
        List<Apartment> apartments = Apartment.findAll();
        List<Street> streets = Street.findAll();
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String number1 = "" + cal.get(Calendar.YEAR);
        String number = number1.substring(2,4);
        if(cal.get(Calendar.MONTH)<9) number = number + "0" + (cal.get(Calendar.MONTH)+1);
        else number = number + (cal.get(Calendar.MONTH)+1);
        if(cal.get(Calendar.DAY_OF_MONTH)<10) number = number + "0" + cal.get(Calendar.DAY_OF_MONTH);
        else number = number + cal.get(Calendar.DAY_OF_MONTH);
        if(cal.get(Calendar.HOUR_OF_DAY)<10) number = number + "0" + cal.get(Calendar.HOUR_OF_DAY);
        else number = number + cal.get(Calendar.HOUR_OF_DAY);
        if(cal.get(Calendar.MINUTE)<10) number = number + "0" + cal.get(Calendar.MINUTE);
        else number = number + cal.get(Calendar.MINUTE);

        try {
            render(type, object, products, users, addresses, districts, khoroos, apartments, streets, now, number);
        } catch (TemplateNotFoundException e) {
            render("CRUD/blank.html", type, object, products, users, addresses, districts, khoroos, apartments, streets, now, number);
        }
    }

    public static void create(String object_takeDate, String object_endDate, Long object_address, Long add_district, Long add_khoroo, String add_street,
                              String add_apartment, String add_orts, String add_floor, String add_door, String add_code, String add_detail,
                              List<Long> product_name, List<Long> product_price, List<Integer> product_count, List<Long> total_price) throws Exception {
        CRUD.ObjectType type = CRUD.ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Order object = (Order) constructor.newInstance();
        Binder.bindBean(params.getRootParamNode(), "object", object);
        validation.valid(object);
        if (validation.hasErrors()) {
//            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
//            try {
//                render(request.controller.replace(".", "/") + "/blank.html", type, object);
//            } catch (TemplateNotFoundException e) {
//                render("CRUD/blank.html", type, object);
//            }
        }

        object._save();
        if(object_takeDate != null && !object_takeDate.isEmpty()) object.takeDate = Consts.myDateFormat.parse(object_takeDate);
        else object.takeDate = null;
        if(object_endDate != null && !object_endDate.isEmpty()) object.endDate = Consts.myDateFormat.parse(object_endDate);
        else object.endDate = null;
        object.status = 1;
        object._save();

        if (object_address != 0) {
            object.address = Address.findById(object_address);
            object._save();
        } else {
            Address address = new Address();
            if (add_district != 0) address.district = District.findById(add_district);
            if (add_khoroo != 0) address.khoroo = Khoroo.findById(add_khoroo);
            String qr = "name='" + add_street + "'";
            Street street = Street.find(qr).first();
            if (street == null) {
                street = new Street();
                street.name = add_street;
                street.create();
            }
            address.street = street;
            String qr2 = "khoroo_id=" + add_khoroo + " AND name='" + add_apartment + "'";
            Apartment apartment = Apartment.find(qr2).first();
            if (apartment == null) {
                apartment = new Apartment();
                apartment.name = add_apartment;
                apartment.khoroo = Khoroo.findById(add_khoroo);
                apartment.create();
            }
            address.apartment = apartment;
            address.orts = add_orts;
            address.floor = add_floor;
            address.door = add_door;
            address.doorCode = add_code;
            address.detail = add_detail;
            address.phoneNumber = object.phoneNumber;
            address.create();
            object.address = address;
            object._save();

        }

        if (product_name != null) {
            for (int i = 0; i < product_name.size(); i++) {
                OrderProductRelation orderProductRelation = new OrderProductRelation();
                orderProductRelation.product = Product.findById(Long.valueOf(product_name.get(i)));
                orderProductRelation.order = object;
                orderProductRelation.count = product_count.get(i);
                orderProductRelation.price = total_price.get(i);
                orderProductRelation.create();
            }
        }

        flash.success(play.i18n.Messages.get("crud.created", type.modelName));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        if (params.get("_saveAndAddAnother") != null) {
            redirect(request.controller + ".blank");
        }
        redirect(request.controller + ".show", object._key());
    }

    @Check(Consts.permissionOrder)
    public static void delete(String id) throws Exception {
        System.out.println("dsada");
        Product product = Product.findById(Long.valueOf(id));
        for (ProductIngredientRelation rel : product.productIngredientRelations) {
            System.out.println("TTTT " + rel.id);
            rel.delete();
        }
        product.productIngredientRelations = null;
        product._save();
        try {
            product._delete();
        } catch (Exception e) {
            flash.error("Алдаа гарлаа");
            System.out.println(e);
            redirect(request.controller + ".show", product._key());
        }
        flash.success("Устгагдлаа");
        redirect(request.controller + ".list");


    }


}
