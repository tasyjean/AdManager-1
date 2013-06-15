package models.custom_helper.file_manager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.coobird.thumbnailator.Thumbnails;
import play.Logger;
import play.Play;
import play.mvc.Http.MultipartFormData.FilePart;
import scala.collection.parallel.ParIterableLike.Find;
import models.data.FileUpload;

public class FileManager {

	private final String BASE_PATH=Play.application().configuration().getString("upload.base_path"); //path sebenernya
	private final String BASE_URL_PATH=Play.application().configuration().getString("upload.base_url_path"); //path buat request
	private final String PROFILE=Play.application().configuration().getString("upload.profile");
	private final String ADS=Play.application().configuration().getString("upload.ads");
	private final String TRANSFER=Play.application().configuration().getString("upload.transfer");
	private final String OTHER =Play.application().configuration().getString("upload.other");
	
	private final String THUMBNAIL=Play.application().configuration().getString("upload.thumbnail");
	private final String THUMBNAIL_PREFIX="thumb";
	private String app_path;
	
	/*
		private final String BASE_URL_PATH="/assets/upload/"; //path buat request
		private final String PROFILE="profile/";
		private final String ADS="ads/";
		private final String TRANSFER="transfer/";
		private final String OTHER ="other/";
		
		private final String THUMBNAIL="profile/thumbnail/";
		private final String THUMBNAIL_PREFIX="thumb";
		
		
		
	 * Informasi penting
	 * Nama file disimpan dengan format id+nama asli,
	 * Nama yang disimpan didatabase adalah nama asli
	 * jadi ketika akses file asli melalui database, maka ditambahkan id didepannya...
	 * 
	 * Untuk thumbnail, ditambahkan thumb didepan dan ditaruh di folder
	 * 
	 * XENOVON
	 */
	
	/*Untuk mengupload file baru dengan inputan bertipe FilePart dan lokasi penyimpanannya
	 * 
	 * @param part saveto
	 */
	public FileManager() {
		if(Play.application().configuration().getBoolean("heroku.mode")){
			app_path=System.getenv("PWD");
		}else{
			app_path=Play.application().path().toString();			
		}
	}
	public FileUpload saveNew(FilePart part, SaveToEnum saveTo){
		String path = getSavePath(saveTo);
		String fileName = part.getFilename()
							  .replace(" ", "")
							  .replace("[", "")
							  .replace("]", "")
							  .replace("{", "")
							  .replace("}", "");		
		
		FileUpload upload=new FileUpload();
		upload.setName(fileName);
		upload.setPath(path);
		upload.setUrl_path(getURLPath(saveTo));
		upload.save();
		
		String contentType = part.getContentType(); 
		File file = part.getFile();
		
		String fullPath=app_path+path;
		String save_name=upload.getId()+fileName; //nama yang disave adalah id+nama file asli
		
		file.renameTo(new File(fullPath+save_name));
		
		if(new File(fullPath+save_name).exists()){
			return upload;		
		}else{
			return null; //kode untuk salah upload
		}	
	}
	public boolean delete(int id){
		try {
			FileUpload upload=FileUpload.find.byId(id);
			File file= getFile(upload);
			
			upload.delete();
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public FileUpload getData(int id){
		return FileUpload.find.byId(id);
	}
	public String getThumbnailURL(FileUpload file){
		return BASE_URL_PATH+THUMBNAIL+THUMBNAIL_PREFIX+file.getId()+file.getName();
			   	
	}
	public String getThumbnailURL(int id){
		FileUpload file=FileUpload.find.byId(id);		
		return BASE_URL_PATH+THUMBNAIL+THUMBNAIL_PREFIX+file.getId()+file.getName();
			   	
	}	
	public String getThumbnailFullPath(FileUpload file){
		return app_path+
			   BASE_PATH+
			   THUMBNAIL+
			   THUMBNAIL_PREFIX+
			   file.getId()+
			   file.getName();		   	
	}	
	public String getThumbnailFullPath(int id){
		FileUpload file=FileUpload.find.byId(id);		
		
		return app_path+
			   BASE_PATH+
			   THUMBNAIL+
			   THUMBNAIL_PREFIX+
			   file.getId()+
			   file.getName();
			   	
	}		
	//save thumbnail untuk kontent tipe gambar
	public boolean saveThumbnail(int id){
		FileUpload file=FileUpload.find.byId(id);
		
		File imageFile = new File(this.getFilePath(file));
		File output = new File(app_path+
							   BASE_PATH+THUMBNAIL+THUMBNAIL_PREFIX+file.getId()+file.getName());
		if(!output.exists()){
			try {
				Thumbnails.of(imageFile).width(60).height(60).toFile(output);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
				
			}
		}
		
		return true;
	}	
	public File getFile(FileUpload file){
		
		return new File(app_path
    					+file.getPath()
    					+file.getId()
    					+file.getName());
	}
	public File getFile(int id){
		FileUpload file=FileUpload.find.byId(id);
		return new File(app_path
    					+file.getPath()
    					+file.getId()
    					+file.getName());
	}
	public String getFilePath(FileUpload file){
		
		return app_path
				+file.getPath()
				+file.getId()
				+file.getName();
	}
	public String getFilePath(int id){
		FileUpload file=FileUpload.find.byId(id);
		
		return app_path
				+file.getPath()
				+file.getId()
				+file.getName();
	}
	
	public String getFileUrl(FileUpload file){
		
		return file.getUrl_path()
				+file.getId()
				+file.getName();
	}
	public String getFileUrl(int id){
		FileUpload file=FileUpload.find.byId(id);
		
		return file.getUrl_path()
				+file.getId()
				+file.getName();
	}	
	private String getSavePath(SaveToEnum enume){
		switch (enume) {
		case ADS_FILE		   : return BASE_PATH+ADS;
		case PROFILE_PICTURE   : return BASE_PATH+PROFILE;
		case OTHER			   : return BASE_PATH+OTHER;
		case TRANSFER_EVIDENCE : return BASE_PATH+TRANSFER;
		default:
			return BASE_PATH+"MISC";
		}
	}
	private String getURLPath(SaveToEnum enume){
		switch (enume) {
		case ADS_FILE		   : return BASE_URL_PATH+ADS;
		case PROFILE_PICTURE   : return BASE_URL_PATH+PROFILE;
		case OTHER			   : return BASE_URL_PATH+OTHER;
		case TRANSFER_EVIDENCE : return BASE_URL_PATH+TRANSFER;
		default:
			return BASE_URL_PATH+"MISC";
		}
	}

	


}

/*
  public URL getUrl() throws MalformedURLException {
        String url;
    	return new URL(url + path + "/" + getActualFileName());
    }

    private String getActualFileName() {
        return id + "/" + name;
    }

    @Override
    public void save() {
        if (S3Manager.amazonS3 == null) {
            Logger.error("Could not save because amazonS3 was null");
            throw new RuntimeException("Could not save");
        }
        else {
            this.path = S3Manager.s3Bucket;
            
            super.save(); // assigns an id

            PutObjectRequest putObjectRequest = new PutObjectRequest(path, getActualFileName(), file);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
            S3Manager.amazonS3.putObject(putObjectRequest); // upload file
        }
    }

    @Override
    public void delete() {
        if (S3Manager.amazonS3 == null) {
            Logger.error("Could not delete because amazonS3 was null");
            throw new RuntimeException("Could not delete");
        }
        else {
            S3Manager.amazonS3.deleteObject(path, getActualFileName());
            super.delete();
        }
*/