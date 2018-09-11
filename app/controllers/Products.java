package controllers;

import models.Ingredient;
import models.Product;
import models.ProductIngredientRelation;
import models.User;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by unurbayar on 6/27/2018.
 */
@With(Secure.class)
public class Products extends CRUD {
    public static void list(int page, String search, String searchFields, String orderBy, String order) {
        User user = Users.getUser();
        if (!(user.getUserPermissionType(Consts.permissionProduct) > 0)) forbidden();

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
        if (!(user.getUserPermissionType(Consts.permissionProduct) > 0)) forbidden();

        CRUD.ObjectType type = CRUD.ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Product object = Product.findById(id);
//        List<User> users = FunctionController.getTeamUsers(id);
        List<Ingredient> ingredients = Ingredient.findAll();
        notFoundIfNull(object);
        try {
            render(type, object, ingredients);
        } catch (TemplateNotFoundException e) {
            render("CRUD/show.html", type, object, ingredients);
        }
    }

    public static void save(Long id, List<Long> ingredient_name, List<Integer> ingredient_portion) throws Exception {
        User user = Users.getUser();
        if (!(user.getUserPermissionType(Consts.permissionProduct) > 0)) forbidden();
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Product object = Product.findById(id);
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

        for (ProductIngredientRelation rel : object.productIngredientRelations) rel._delete();
        if (ingredient_name != null) {
            for (int i = 0; i < ingredient_name.size(); i++) {
                ProductIngredientRelation productIngredientRelation = new ProductIngredientRelation();
                productIngredientRelation.ingredient = Ingredient.findById(ingredient_name.get(i));
                productIngredientRelation.product = object;
                productIngredientRelation.portion = ingredient_portion.get(i);
                productIngredientRelation.create();
            }
        }

        flash.success(play.i18n.Messages.get("crud.saved", ""));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        redirect(request.controller + ".show", object._key());
    }

    @Check(Consts.permissionProduct)
    public static void blank() throws Exception {
        CRUD.ObjectType type = CRUD.ObjectType.get(getControllerClass());
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

    public static void create(List<Long> ingredient_name, List<Integer> ingredient_portion) throws Exception {
        CRUD.ObjectType type = CRUD.ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Product object = (Product) constructor.newInstance();
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

        if (ingredient_name != null) {
            for (int i = 0; i < ingredient_name.size(); i++) {
                ProductIngredientRelation productIngredientRelation = new ProductIngredientRelation();
                productIngredientRelation.ingredient = Ingredient.findById(ingredient_name.get(i));
                productIngredientRelation.product = object;
                productIngredientRelation.portion = ingredient_portion.get(i);
                productIngredientRelation.create();
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

    @Check(Consts.permissionProduct)
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

////    //@Check(Consts.permissionProduct)
//    public static void delete(Long id) {
////        CRUD.ObjectType type = CRUD.ObjectType.get(getControllerClass());
////        notFoundIfNull(type);
//        System.out.println("hhhhh " + id);
//        Product object = Product.findById(id);
////        notFoundIfNull(object);
//        for (ProductIngredientRelation rel : object.productIngredientRelations) rel._delete();
//
//        object._delete();
////        try {
////
////        } catch (Exception e) {
////            flash.error(play.i18n.Messages.get("crud.delete.error", type.modelName));
////            redirect(request.controller + ".show", object._key());
////        }
////        flash.success(play.i18n.Messages.get("crud.deleted", type.modelName));
//        flash.success(" Устгагдлаа");
//        redirect(request.controller + ".list");
//    }
}
