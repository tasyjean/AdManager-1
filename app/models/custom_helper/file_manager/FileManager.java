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

public class FileManager implements FileManagerInterface {

	private final String BASE_PATH=Play.application().configuration().getString("upload.base_path"); //path sebenernya
	private final String BASE_URL_PATH=Play.application().configuration().getString("upload.base_url_path"); //path buat request
	private final String PROFILE=Play.application().configuration().getString("upload.profile");
	private final String ADS=Play.application().configuration().getString("upload.ads");
	private final String TRANSFER=Play.application().configuration().getString("upload.transfer");
	private final String OTHER =Play.application().configuration().getString("upload.other");
	
	private final String THUMBNAIL=Play.application().configuration().getString("upload.thumbnail");
	private final String THUMBNAIL_PREFIX="thumb";
	private final String APP_PATH=Play.application().path().toString();
	
	private S3Manager s3;
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
		
		String fullPath=APP_PATH+path;
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
			File thumbnail=new File(getThumbnailFullPath(upload));

			file.delete();
			thumbnail.delete();
			
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
		return file.getUrl_path()+THUMBNAIL+THUMBNAIL_PREFIX+file.getId()+file.getName();
			   	
	}
	public String getThumbnailURL(int id){
		FileUpload file=FileUpload.find.byId(id);		
		return file.getUrl_path()+THUMBNAIL+THUMBNAIL_PREFIX+file.getId()+file.getName();
			   	
	}	
	public String getThumbnailFullPath(FileUpload file){
		return APP_PATH+
			   file.getPath()+
			   THUMBNAIL+
			   THUMBNAIL_PREFIX+
			   file.getId()+
			   file.getName();		   	
	}	
	public String getThumbnailFullPath(int id){
		FileUpload file=FileUpload.find.byId(id);		
		
		return APP_PATH+
			   file.getPath()+
			   THUMBNAIL+
			   THUMBNAIL_PREFIX+
			   file.getId()+
			   file.getName();
			   	
	}		
	//save thumbnail untuk kontent tipe gambar
	public boolean saveThumbnail2(File imageFile, String id_name){
		File output = new File(APP_PATH+
							   BASE_PATH+THUMBNAIL+THUMBNAIL_PREFIX+id_name);
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
	//save thumbnail untuk kontent tipe gambar
	public boolean saveThumbnail(int id){
		FileUpload file=FileUpload.find.byId(id);
		
		File imageFile = new File(this.getFilePath(file));
		File output = new File(APP_PATH+
							   file.getPath()+THUMBNAIL+THUMBNAIL_PREFIX+file.getId()+file.getName());
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
	public boolean resize(int input, int width, int height){
		FileUpload file=FileUpload.find.byId(input);
		
		File imageFile = new File(this.getFilePath(file));
		File output = new File("file.jpg");
		if(!output.exists()){
			try {
				Thumbnails.of(imageFile).width(width).height(height).toFile(output);
				imageFile.delete();
				System.out.println("FIle path output"+this.getFilePath(file)+" setelah "+output.getPath());
				output.renameTo(new File(this.getFilePath(file)));
			} catch (IOException e) {
				e.printStackTrace();
				return false;
				
			}
		}
		
		return true;		
	}
	public File getFile(FileUpload file){
		
		return new File(APP_PATH
    					+file.getPath()
    					+file.getId()
    					+file.getName());
	}
	public File getFile(int id){
		FileUpload file=FileUpload.find.byId(id);
		return new File(APP_PATH
    					+file.getPath()
    					+file.getId()
    					+file.getName());
	}
	public String getFilePath(FileUpload file){
		
		return APP_PATH
				+file.getPath()
				+file.getId()
				+file.getName();
	}
	public String getFilePath(int id){
		FileUpload file=FileUpload.find.byId(id);
		
		return APP_PATH
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
