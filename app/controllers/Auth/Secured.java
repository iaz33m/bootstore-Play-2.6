package controllers.Auth;

import controllers.routes;
import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

    public static User USER;

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect(routes.AuthController.login());
    }

    public static boolean CHECK(){
        return (USER != null);
    }

    public static boolean GUEST(){
        return (USER == null);
    }
}