package controllers.API;

import com.fasterxml.jackson.databind.JsonNode;
import models.Tag;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.HashMap;
import java.util.List;

public class TagAPIController extends Controller {

    public Result index(){
        List<Tag> tags = Tag.find.all();
        return ok(Json.toJson(tags));
    }

    public Result save(){

        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();

        HashMap<String,Object> res = new HashMap<>();

        if(json == null){
            res.put("code","Bad Request");
            res.put("message","We Only Accept Json");
            return badRequest(Json.toJson(res));
        }

        Tag tag = Json.fromJson(json, Tag.class);

        tag.save();

        res.put("code","success");
        res.put("message","Tag Saved Successfully.");

        return ok(Json.toJson(res));
    }

    public Result update(Integer id){

        HashMap<String,Object> res = new HashMap<>();
        res.put("code","Bad Request");

        Tag tag = Tag.find.byId(id);

        if(tag == null){
            res.put("message","Tag does not exists");
            return badRequest(Json.toJson(res));
        }

        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();

        if(json == null){
            res.put("message","We Only Accept Json");
            return badRequest(Json.toJson(res));
        }

        Tag newTag = Json.fromJson(json, Tag.class);

        tag.name = newTag.name;
        tag.description = newTag.description;

        tag.update();

        res.put("code","success");
        res.put("message","Tag Updated Successfully.");

        return ok(Json.toJson(res));
    }

    public Result destroy(Integer id){

        HashMap<String,Object> res = new HashMap<>();

        Tag tag = Tag.find.byId(id);

        if(tag == null){
            res.put("code","Bad Request");
            res.put("message","Tag does not exists");
            return badRequest(Json.toJson(res));
        }

        if(tag.books != null){
            res.put("code","Bad Request");
            res.put("message","You can not delete this tag, some books are associated with it.");
            return badRequest(Json.toJson(res));
        }

        tag.delete();

        res.put("code","success");
        res.put("message","Tag deleted Successfully.");

        return ok(Json.toJson(res));
    }
}
