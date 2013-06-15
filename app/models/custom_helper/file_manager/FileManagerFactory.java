package models.custom_helper.file_manager;

import play.Play;

public class FileManagerFactory {

	
	boolean aws_mode=Play.application().configuration().getBoolean("aws.mode");
			
	public FileManagerInterface getManager(){
		if(aws_mode){
			return new S3Manager();
		}else{
			return new FileManager();
		}
	}

}
