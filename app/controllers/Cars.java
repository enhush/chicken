package controllers;

import models.CarSchedule;
import models.Ingredient;
import models.Car;
import models.User;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.List;

/**
 * Created by unurbayar on 8/6/2018.
 */
@With(Secure.class)
public class Cars extends CRUD {
    public static void list(int page, String search, String searchFields, String orderBy, String order) {
        User user = Users.getUser();
        if (!(user.getUserPermissionType(Consts.permissionCar) > 0)) forbidden();

        ObjectType type = ObjectType.get(getControllerClass());
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
        if (!(user.getUserPermissionType(Consts.permissionCar) > 0)) forbidden();

        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Car object = Car.findById(id);
//        List<User> users = FunctionController.getTeamUsers(id);
        List<Ingredient> ingredients = Ingredient.findAll();
        notFoundIfNull(object);
        try {
            render(type, object, ingredients);
        } catch (TemplateNotFoundException e) {
            render("CRUD/show.html", type, object, ingredients);
        }
    }

    public static void save(Long id, List<Date> s_date, List<String> driver_name, List<String> receive_km, List<String> went_km, List<String> give_km,
                            List<String> fine_info, List<String> fuel_info) throws Exception {
        User user = Users.getUser();
        if (!(user.getUserPermissionType(Consts.permissionCar) > 0)) forbidden();
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Car object = Car.findById(id);
        notFoundIfNull(object);
        Binder.bindBean(params.getRootParamNode(), "object", object);
        validation.valid(object);
        if (validation.hasErrors()) {
            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
            try {
                render(request.controller.replace(".", "/") + "/show.html", type, object);
            } catch (TemplateNotFoundException e) {
                render("CRUD/show.html", type, object);
            }
        }
        object._save();

        for (CarSchedule carSchedule : object.carSchedules) carSchedule._delete();
        if (s_date != null) {
            for (int i = 0; i < s_date.size(); i++) {
                CarSchedule schedule = new CarSchedule();
                schedule.car = object;
                schedule.driverName = driver_name.get(i);
                schedule.receiveKm = receive_km.get(i);
                schedule.wentKm = went_km.get(i);
                schedule.giveKm = give_km.get(i);
                schedule.date = s_date.get(i);
                schedule.fineInfo = fine_info.get(i);
                schedule.fuelInfo = fuel_info.get(i);
                schedule.create();
            }
        }

        flash.success(play.i18n.Messages.get("crud.saved", ""));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        redirect(request.controller + ".show", object._key());
    }

    @Check(Consts.permissionCar)
    public static void blank() throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Model object = (Model) constructor.newInstance();
        List<Ingredient> ingredients = Ingredient.findAll();
        try {
            render(type, object, ingredients);
        } catch (TemplateNotFoundException e) {
            render("CRUD/blank.html", type, object, ingredients);
        }
    }

    public static void create(List<Date> s_date, List<String> driver_name, List<String> receive_km, List<String> went_km, List<String> give_km,
                              List<String> fine_info, List<String> fuel_info) throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Car object = (Car) constructor.newInstance();
        Binder.bindBean(params.getRootParamNode(), "object", object);
        validation.valid(object);
        if (validation.hasErrors()) {
            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
            try {
                render(request.controller.replace(".", "/") + "/blank.html", type, object);
            } catch (TemplateNotFoundException e) {
                render("CRUD/blank.html", type, object);
            }
        }

        object._save();

        if (s_date != null) {
            for (int i = 0; i < s_date.size(); i++) {
                CarSchedule schedule = new CarSchedule();
                schedule.car = object;
                schedule.driverName = driver_name.get(i);
                schedule.receiveKm = receive_km.get(i);
                schedule.wentKm = went_km.get(i);
                schedule.giveKm = give_km.get(i);
                schedule.date = s_date.get(i);
                schedule.fineInfo = fine_info.get(i);
                schedule.fuelInfo = fuel_info.get(i);
                schedule.create();
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

    @Check(Consts.permissionCar)
    public static void delete(String id) throws Exception {
        Car car = Car.findById(Long.valueOf(id));
        for (CarSchedule rel : car.carSchedules) {
            System.out.println("TTTT " + rel.id);
            rel.delete();
        }
        car.carSchedules = null;
        car._save();
        try {
            car._delete();
        } catch (Exception e) {
            flash.error("Алдаа гарлаа");
            System.out.println(e);
            redirect(request.controller + ".show", car._key());
        }
        flash.success("Устгагдлаа");
        redirect(request.controller + ".list");


    }
}
