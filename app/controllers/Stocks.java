package controllers;

import models.Stock;
import models.StockFile;
import models.User;
import models.UserTeam;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by User on 18.09.01.
 */
@With(Secure.class)
public class Stocks extends CRUD {
    public static void list(int page, String search, String searchFields, String orderBy, String order) {
        User user = Users.getUser();
        if (!(user.getUserPermissionType(Consts.permissionStock) > 0)) forbidden();

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
        if (!(user.getUserPermissionType(Consts.permissionStock) > 0)) forbidden();

        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Stock object = Stock.findById(id);
        notFoundIfNull(object);
        List<UserTeam> userTeams = UserTeam.findAll();
        List<User> users = User.findAll();
        try {
            render(type, object, users, userTeams);
        } catch (TemplateNotFoundException e) {
            render("CRUD/show.html", type, object, users, userTeams);
        }
    }

    public static void save(Long id, List<String> file_name, List<String> file_path, List<String> file_extension) throws Exception {
        User user = Users.getUser();
        if (!(user.getUserPermissionType(Consts.permissionStock) > 1)) forbidden();
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Stock object = Stock.findById(id);
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

        for (StockFile rel : object.stockFiles) rel._delete();
        if (file_name != null) {
            for (int i = 0; i < file_name.size(); i++) {
                StockFile stockFile = new StockFile();
                stockFile.stock = object;
                stockFile.name = file_name.get(i);
                stockFile.path = file_path.get(i);
                stockFile.extension = file_extension.get(i);
                stockFile.create();
            }
        }

        flash.success(play.i18n.Messages.get("crud.saved", ""));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        redirect(request.controller + ".show", object._key());
    }

    @Check(Consts.permissionStock)
    public static void blank() throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Model object = (Model) constructor.newInstance();
        List<UserTeam> userTeams = UserTeam.findAll();
        List<User> users = User.findAll();

        try {
            render(type, object, users, userTeams);
        } catch (TemplateNotFoundException e) {
            render("CRUD/blank.html", type, object, users, userTeams);
        }
    }

    public static void create(List<String> file_name, List<String> file_path, List<String> file_extension) throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Stock object = (Stock) constructor.newInstance();
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

        if (file_name != null) {
            for (int i = 0; i < file_name.size(); i++) {
                StockFile stockFile = new StockFile();
                stockFile.stock = object;
                stockFile.name = file_name.get(i);
                stockFile.path = file_path.get(i);
                stockFile.extension = file_extension.get(i);
                stockFile.create();
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

    @Check(Consts.permissionStock)
    public static void delete(String id) throws Exception {
        Stock stock = Stock.findById(Long.valueOf(id));
        for (StockFile rel : stock.stockFiles) {
            //System.out.println("TTTT " + rel.id);
            rel.delete();
        }
        stock.stockFiles = null;
        stock._save();
        try {
            stock._delete();
        } catch (Exception e) {
            flash.error("????? ??????");
            System.out.println(e);
            redirect(request.controller + ".show", stock._key());
        }
        flash.success("??????????");
        redirect(request.controller + ".list");


    }
}
