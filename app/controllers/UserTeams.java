package controllers;

import models.User;
import models.UserTeam;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

import java.lang.reflect.Constructor;
import java.util.List;

@With(Secure.class)
public class UserTeams extends CRUD {
    public static void list(int page, String search, String searchFields, String orderBy, String order) {
        User user = Users.getUser();
        if (!(user.getUserPermissionType(Consts.permissionAccount) > 0)) forbidden();

        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        if (orderBy == null) {
            orderBy = "queue,name";
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
        if (!(user.getUserPermissionType(Consts.permissionAccount) > 0)) forbidden();

        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        UserTeam object = UserTeam.findById(id);
        List<User> users = FunctionController.getTeamUsers(id);
        notFoundIfNull(object);
        try {
            render(type, object, users);
        } catch (TemplateNotFoundException e) {
            render("CRUD/show.html", type, object, users);
        }
    }

    public static void save(Long id) throws Exception {
        User user = Users.getUser();
        if (!(user.getUserPermissionType(Consts.permissionAccount) > 0)) forbidden();

        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        UserTeam object = UserTeam.findById(id);
        notFoundIfNull(object);
        String name = object.name;
        String nameMin = object.nameMin;
        Long queue = object.queue;
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
        flash.success(play.i18n.Messages.get("crud.saved", type.modelName));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        redirect(request.controller + ".show", object._key());
    }

    @Check(Consts.permissionAccount)
    public static void blank() throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Model object = (Model) constructor.newInstance();
        Long queue = UserTeam.count() + 1;
        try {
            render(type, object, queue);
        } catch (TemplateNotFoundException e) {
            render("CRUD/blank.html", type, object, queue);
        }
    }

    public static void create() throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        UserTeam object = (UserTeam) constructor.newInstance();
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
        flash.success(play.i18n.Messages.get("crud.created", type.modelName));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        if (params.get("_saveAndAddAnother") != null) {
            redirect(request.controller + ".blank");
        }
        redirect(request.controller + ".show", object._key());
    }

    @Check(Consts.permissionAccount)
    public static void delete(String id) throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = type.findById(id);
        notFoundIfNull(object);
        try {
            object._delete();
        } catch (Exception e) {
            flash.error(play.i18n.Messages.get("crud.delete.error", type.modelName));
            redirect(request.controller + ".show", object._key());
        }
        flash.success(play.i18n.Messages.get("crud.deleted", type.modelName));
        redirect(request.controller + ".list");
    }
}