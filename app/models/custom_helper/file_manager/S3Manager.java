package models.custom_helper.file_manager;

import java.io.File;
import java.io.IOException;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import models.custom_helper.S3Plugin;
import models.data.FileUpload;
import net.coobird.thumbnailator.Thumbnails;
import play.Logger;
import play.Play;
import play.mvc.Http.MultipartFormData.FilePart;

public class S3Manager {

	private  String BASE_PATH; //path sebenernya
	private  String BASE_URL_PATH; //path buat request
	private  String PROFILE;
	private  String ADS;;
	private  String TRANSFER;;
	private  String OTHER;;
	
	private  String THUMBNAIL;
	private  String THUMBNAIL_PREFIX;
	private  String bucket;
	private final  String APP_PATH=S3Plugin.AWS_ENDPOINT+S3Plugin.s3Bucket;
	
	//inisialisasi
	public S3Manager(String bASE_PATH, String bASE_URL_PATH, String pROFILE,
			String aDS, String tRANSFER, String oTHER, String tHUMBNAIL,
			String tHUMBNAIL_PREFIX) {
		BASE_PATH = bASE_PATH;
		BASE_URL_PATH = bASE_URL_PATH;
		PROFILE = pROFILE;
		ADS = aDS;
		TRANSFER = tRANSFER;
		OTHER = oTHER;
		THUMBNAIL = tHUMBNAIL;
		THUMBNAIL_PREFIX = tHUMBNAIL_PREFIX;
	}
	public void saveNew(File input, String savePath){
        if (S3Plugin.amazonS3 == null) {
            Logger.error("Tidak bisa menyimpan karena storage tidak siap");
            throw new RuntimeException("Could not save");
        }else{
        	this.bucket=S3Plugin.s3Bucket;
        	PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, savePath, input);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
            S3Plugin.amazonS3.putObject(putObjectRequest); // upload file
            GetObjectRequest request=new GetObjectRequest(bucket, savePath);
            S3Object object =S3Plugin.amazonS3.getObject(request);
            if(object==null) throw new RuntimeException(); 
        }
	}
	public boolean delete(FileUpload upload) {
		try {
            DeleteObjectRequest request=new DeleteObjectRequest(bucket, 
            											  upload.getPath()+
            											  upload.getId()+
            											  upload.getName());
            S3Plugin.amazonS3.deleteObject(request);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public String getThumbnailURL(FileUpload file){
		return APP_PATH+file.getPath()+THUMBNAIL+THUMBNAIL_PREFIX+file.getId()+file.getName();
			   	
	}	
	//save thumbnail untuk kontent tipe gambar
	public boolean saveThumbnail(FileUpload upload){
		
		File imageFile = new File(this.getFileUrl(upload));
		try {
			File outFile = null; 
			Thumbnails.of(imageFile).width(60).height(60).toFile(outFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;	
		}
	
		return true;
	}	
	public String getFileUrl(FileUpload upload){
		return APP_PATH+upload.getPath()+upload.getId()+upload.getName();
	}
}
