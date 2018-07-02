package controllers;

import controllers.Auth.Secured;
import models.Tag;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.errors._404;
import views.html.tags.index;

import javax.inject.Inject;
import java.util.List;

@Security.Authenticated(Secured.class)
public class TagsController extends Controller {

    @Inject
    FormFactory ff;

    public Result index(){
        Form<Tag> form = ff.form(Tag.class);
        List<Tag> tags = Tag.find.all();
        return ok(index.render(form,tags));
    }

    public Result save(){

        Form<Tag> form = ff.form(Tag.class).bindFromRequest();

        if(form.hasErrors()){
            flash("danger","Validation Failed.");

            List<Tag> tags = Tag.find.all();

            return badRequest(index.render(form,tags));
        }

        Tag tag = form.get();
        tag.save();

        flash("success","Tag created successfully.");

        return redirect(routes.TagsController.index());
    }

    public Result show(Integer id){

        Tag tag = Tag.find.byId(id);

        if(tag==null){
            return notFound(_404.render());
        }

        return ok(views.html.books.index.render(tag.books,tag.name + " Books"));
    }
}
