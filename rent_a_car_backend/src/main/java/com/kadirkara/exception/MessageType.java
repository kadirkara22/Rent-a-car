package com.kadirkara.exception;

import lombok.Getter;

@Getter
public enum MessageType {

	NO_RECORD_EXIST("1004" , "kayıt bulunamadı"),
	TOKEN_IS_EXPIRED("1005" , "tokenın süresi bitmiştir"),
	USERNAME_NOT_FOUND("1006" , "username bulunamadı"),
	USERNAME_OR_PASSWORD_INVALID("1007" , "kullanıcı adı veya şifre hatalı"),
	REFRESH_TOKEN_NOT_FOUND("1008" , "refresh token bulunamadı"),
	REFRESH_TOKEN_IS_EXPIRED("1009" , "refresh tokenın süresi bitmiştir"),
	CURRENY_RATES_IS_OCCURED("1010" , "döviz kuru alınamadı"),
	CUSTOMER_AMOUNT_IS_NOT_ENOUGH("1011" , "müşterinin parası yeterli değildir"),
	CAR_STATUS_IS_ALREADY_SALED("1012" , "araba satılmış göründüğü için satılamaz"),
	USERNAME_ALREADY_EXISTS("1013","username daha önce kullanılmıs"),
	USER_NOT_HAVE_PERMISSIONS("1014","Admin yetkiniz bulunmamaktadır"),
	GENERAL_EXCEPTION("9999" , "genel bir hata oluştu");
	
	private String code;
	private String message;
	
	 MessageType(String code , String message) {
		this.code =code;
		this.message = message;
	}
}
