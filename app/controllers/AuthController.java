package controllers;

import controllers.Auth.Secured;
import models.Address;
import models.User;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.Hash;
import views.html.auth.login;
import views.html.auth.register;

import javax.inject.Inject;
import java.util.Map;

public class AuthController extends Controller {

    @Inject
    FormFactory ff;

    @Inject
    MailerClient mailerClient;

    public Result sendEmail(){

        Email email = new Email()
                .setSubject("Simple email")
                .setFrom("Mister FROM <noreply@tutorism.pk>")
                .addTo("Miss TO <azeemtariq00@gmail.com>")
                .setBodyText("A text message");

        mailerClient.send(email);

        return ok("Done");
    }


    public Result register(){
        if(Secured.CHECK()){
            return redirect(routes.BooksController.index());
        }
        Form<User> form = ff.form(User.class);
        return ok(register.render(form));
    }

    public Result login(){
        if(Secured.CHECK()){
            return redirect(routes.BooksController.index());
        }
        Form<User> form = ff.form(User.class);
        return ok(login.render(form));
    }

    public Result save(){

        Form<User> form = ff.form(User.class).bindFromRequest();

        if(form.hasErrors()){
            flash("danger","Input validation failed.");
            return badRequest(register.render(form));
        }

        Map<String,String> input = form.rawData();

        User user = form.get();

        try {
            user.password = Hash.create(user.password);
        } catch (Exception e) {
            flash("danger",e.getMessage());
            return badRequest(register.render(form));
        }

        user.save();

        Address address = new Address(
                input.get("streetAddress"),
                input.get("city"),
                input.get("state"),
                input.get("country"),
                Integer.valueOf(input.get("zipCode"))
        );

        address.user = user;
        address.save();

        flash("success","Registered Successfully.");

        return redirect(routes.AuthController.login());
    }

    public Result auth(){

        Form<User> form = ff.form(User.class).bindFromRequest();

        String email = form.rawData().get("email");
        String password = form.rawData().get("password");

        if(email == null || password == null){
            flash("danger","Email or Password is invalid.");
            return redirect(routes.AuthController.login());
        }

        User user = User.find.byId(email);

        if((user == null) || (!Hash.check(password,user.password))){
            flash("danger","Email or Password is invalid..");
            return redirect(routes.AuthController.login());
        }

        session("email", email);

        return redirect(routes.AuthorController.index());
    }

    @Security.Authenticated(Secured.class)
    public Result logout(){
        session().clear();
        flash("success","You've been logged out");
        return redirect(routes.BooksController.index());
    }


}
