package models.custom_helper;

public class EmailSenderThread extends Thread{
	SendMail mail;
	int trial;
	public EmailSenderThread(SendMail mail) {
		this.mail=mail;
	}
	
	public void run(){
		trial++;
		try {
			mail.sendHTML();
			System.out.println("Berhasil mengirim email dalam "+trial+" kali coba");
		} catch (Exception e) {
			System.out.println("Gagal mengirim, mencoba lagi untuk ke "+trial+" kali");
			try {
				this.sleep(20000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			this.run();
		}
	}
}


