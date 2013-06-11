package models.data.enumeration;

import com.avaje.ebean.annotation.EnumValue;

public enum ContactTypeEnum {
	
	@EnumValue("address")
	ADDRESS{
		@Override
		public String toString() {
			return "Alamat";
		}
	},
	@EnumValue("email")
	EMAIL{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Email";
		}
	},
	@EnumValue("home_phone")
	HOME_PHONE{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Telepon Rumah";
		}
	},
	@EnumValue("private_phone")
	PRIVATE_PHONE{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Telepon Pribadi";
		}
	},
	@EnumValue("alternative_phone")
	ALTERNATIVE_PHONE{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Telepon Alternatif";
		}
	},
	@EnumValue("social_profile")	
	SOCIAL_PROFILE{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Profil Jejaring Sosial";
		}
	},
	@EnumValue("company_website")
	COMPANY_WEBSITE{
		@Override
		public String toString() {
			return "Website Perusahaan";
		}
	},
	@EnumValue("personal_website")
	PERSONAL_WEBSITE{
		@Override
		public String toString() {
			return "Website Personal";
		}
	},
	@EnumValue("bank_account")
	BANK_ACCOUNT{
		@Override
		public String toString() {
			return "Rekening Bank";
		}
	},
	@EnumValue("other")
	OTHER{
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Data Lain";
		}
	}
	
}
