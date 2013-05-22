package controllers;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

import play.*;
import play.mvc.*;
import views.html.helper.form;


public class CompressController extends Controller{
	
	public static Results.Status ok(Content content) {
        return Results.ok(compress(content)).as("text/html; charset=utf-8");        
    }

    public static Results.Status badRequest(Content content) {
        return Results.badRequest(compress(content)).as("text/html; charset=utf-8");        
    }

    public static Results.Status notFound(Content content) {
        return Results.notFound(compress(content)).as("text/html; charset=utf-8");      
    }

    public static Results.Status forbidden(Content content) {
        return Results.forbidden(compress(content)).as("text/html; charset=utf-8");     
    }

    public static Results.Status internalServerError(Content content) {
        return Results.internalServerError(compress(content)).as("text/html; charset=utf-8");       
    }

    public static Results.Status unauthorized(Content content) {
        return Results.unauthorized(compress(content)).as("text/html; charset=utf-8");      
    }

    private static String compress(Content content) {
        HtmlCompressor compressor = new HtmlCompressor();
        String output = content.body().trim();

        if (Play.isDev()) {
            compressor.setPreserveLineBreaks(true);
        } 

        output = compressor.compress(output);
        return output;
    }
    
}
