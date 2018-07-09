package controllers.Auth;

import controllers.routes;
import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect(routes.AuthController.login());
    }


    public static String GET_USER(){
        Http.Context ctx = Http.Context.current();
        return ctx.session().get("email");
    }

    public static boolean CHECK(){
        return (GET_USER() != null);
    }

    public static User USER(){
        return CHECK() ? User.find.byId(GET_USER()):null;
    }


}