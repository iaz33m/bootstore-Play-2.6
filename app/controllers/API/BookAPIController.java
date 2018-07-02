package controllers.API;

import models.Book;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class BookAPIController extends Controller {

    public Result index() {
        List<Book> books = Book.find.all();
        return ok(Json.toJson(books));
    }

}
