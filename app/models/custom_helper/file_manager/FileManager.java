package models.custom_helper.file_manager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import play.Logger;
import play.Play;
import play.mvc.Http.MultipartFormData.FilePart;
import models.data.FileUpload;

public class FileManager {

	private final String BASE_PATH="/public/upload/";
	private final String BASE_URL_PATH="/assets/upload/";
	private final String PROFILE="profile/";
	private final String ADS="ads/";
	private final String TRANSFER="transfer/";
	private final String OTHER ="other/";
	
	public int saveNew(FilePart part, SaveToEnum saveTo){
		String path = getSavePath(saveTo);
		String fileName = part.getFilename()
							  .replace(" ", "");		
		
		FileUpload upload=new FileUpload();
		upload.setName(fileName);
		upload.setPath(path);
		upload.setUrl_path(getURLPath(saveTo));
		upload.save();
		
		String contentType = part.getContentType(); 
		File file = part.getFile();
		
		String fullPath=Play.application().path()+path;
		String save_name=upload.getId()+fileName; //nama yang disave adalah id+nama file asli
		
		file.renameTo(new File(fullPath+save_name));
		
		if(new File(fullPath+save_name).exists()){
			return upload.getId();			
		}else{
			return 0; //kode untuk salah upload
		}
		
	}

	public boolean delete(int id){
		try {
			FileUpload upload=FileUpload.find.byId(id);
			File file= getFile(id);
			
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
	public File getFile(int id){
		FileUpload file=FileUpload.find.byId(id);
    	return new File(Play.application().path()
    					+file.getPath()
    					+file.getId()
    					+file.getName());
	}
	
	public String getFilePath(int id){
		FileUpload file=FileUpload.find.byId(id);		
		return Play.application().path()
				+file.getPath()
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