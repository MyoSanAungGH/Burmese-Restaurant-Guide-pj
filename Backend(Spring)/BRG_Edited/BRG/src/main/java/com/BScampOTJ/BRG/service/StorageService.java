package com.BScampOTJ.BRG.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	//file come from api
		public String create(MultipartFile file, String fileType);

		//return byte array
		public byte[] load(String filePath);

		public boolean delete(String filePath);

		public String update(MultipartFile file, String fileType, String filePath);

		public void clearAll();

		public boolean check(String filePath);
}
