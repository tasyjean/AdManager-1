package models.data;

import play.db.ebean.Model;


import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import play.Logger;
import play.Play;
import models.custom_helper.S3Plugin;
import models.custom_helper.file_manager.FileManager;
import models.custom_helper.file_manager.FileManagerFactory;
import models.custom_helper.file_manager.FileManagerInterface;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Entity
@Table(name="file_upload")
public class FileUpload extends Model {

    @Id
    private int id;

    private String path;
    private String url_path;
    private String name;
	public static Model.Finder<Integer,FileUpload> find = new Model.Finder(Integer.class, FileUpload.class);
	static FileManagerInterface manager=new FileManagerFactory().getManager();

    
	public String getFileURL(){
		return manager.getFileUrl(this.id);
	}
	
	public String getThumbnailURL(){
		return manager.getThumbnailURL(this.id);
	}
	@Override
	public void delete() {
//		Fungsi delete digantikan sama yang ada di file manager
		manager.delete(this.id);
		super.delete();
	};
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl_path() {
		return url_path;
	}

	public void setUrl_path(String url_path) {
		this.url_path = url_path;
	}

}