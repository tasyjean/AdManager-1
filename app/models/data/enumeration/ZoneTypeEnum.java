package models.data.enumeration;

import com.avaje.ebean.annotation.EnumValue;

public enum ZoneTypeEnum {

	@EnumValue("text")
	TEXT{
	public String toString() {
		return "Tipe Teks";
	}},
	@EnumValue("banner")
	BANNER{
	public String toString() {
		return "Tipe Banner";
	}}
}
