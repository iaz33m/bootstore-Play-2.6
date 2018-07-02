package controllers;

import controllers.Auth.Secured;
import models.User;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.author.index;

import javax.inject.Inject;
import java.util.List;

@Security.Authenticated(Secured.class)
public class AuthorController  extends Controller {

    @Inject
    FormFactory ff;

    public Result index(){
        List<User> authors = User.find.all();
        return ok(index.render(authors));
    }

    public Result destroy(String email){

        User author = User.find.byId(email);

        if(author == null) {
            flash("danger", "Author Not Found");
            return notFound();
        }

        author.delete();

        flash("success","Author Deleted Successfully");

        return ok();
    }

}
