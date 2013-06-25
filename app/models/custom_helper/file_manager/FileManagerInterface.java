package models.custom_helper.file_manager;

import models.data.FileUpload;
import play.mvc.Http.MultipartFormData.FilePart;

public interface FileManagerInterface {
	
	
	public FileUpload saveNew(FilePart part, SaveToEnum saveTo);
	public boolean delete(int id);
	public String getThumbnailURL(int id);
	public boolean saveThumbnail(int id);
	public String getFileUrl(int id);
	public String getThumbnailFullPath(int id);
	public boolean resize(int file, int width, int height);
}
