package com.BScampOTJ.BRG.service;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImp implements StorageService {

	
		//static  variable
		private final Path storagePath;
		
		//constructor(do the first time when call storageSeviceImp)
		@Autowired
		public StorageServiceImp() throws IOException{
			
			//put direction of the file path
			Path storagePath = Paths.get("").resolve("src").resolve("main")
					.resolve("resources").resolve("static").resolve("media");
			//check path exit or not 
			if (!Files.exists(storagePath)) {
				Files.createDirectories(storagePath);
			}
			this.storagePath = storagePath;
		}
		
		
		@Override
		public String create(MultipartFile file, String fileType) {

		
		String filePath = null;
		
		try {
			//create file name
			String fileName = Instant.now().getEpochSecond() + "_"
					+ StringUtils.cleanPath(file.getOriginalFilename());
			//save file
			Files.copy(
					file.getInputStream(), this.storagePath.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING
			);
			
			switch (fileType) {
				case "image/jpg" :
					filePath = "/media/jpg/" + fileName;
					break;
				case "image/jpeg" :
					filePath = "/media/jpg/" + fileName;
					break;
				case "image/png" :
				default :
					filePath = "/media/png/" + fileName;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return filePath;
	}

	@Override
	public byte[] load(String fileName) {
		
		byte[] retBytes = null;
		
		try {
			Path filePath = this.storagePath.resolve(fileName);
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists() && resource.isReadable()) {
				retBytes = StreamUtils
						.copyToByteArray(resource.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retBytes;

	}

	
	@Override
	public boolean delete(String filePath) {
	
		filePath = filePath.replace("/media/jpg/", "");
		filePath = filePath.replace("/media/png/", "");
		
		if (filePath != null && filePath != "") {
			try {
				Files.delete(this.storagePath.resolve(filePath));
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		return false;

	}

	
	
	@Override
	public String update(MultipartFile file, String fileType, String filePath) {
		
		String retfilePath = null;
		
		filePath = filePath.replace("/media/jpg/", "");
		filePath = filePath.replace("/media/png/", "");
		
		//If exit oldPath, delete it
		try {
			if (filePath != null && filePath != "") {
				try {
					Files.delete(this.storagePath.resolve(filePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			//Create fileName , Save it and  choose type
			String fileName = Instant.now().getEpochSecond() + "_"
					+ StringUtils.cleanPath(file.getOriginalFilename());
			Files.copy(
					file.getInputStream(), this.storagePath.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING
			);
			
			switch (fileType) {
				case "image/jpg" :
				case "image/jpeg" :
					retfilePath = "/media/jpg/" + fileName;
					break;
				case "image/png" :
				default :
					retfilePath = "/media/png/" + fileName;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retfilePath;

	}

	@Override
	public void clearAll() {
		
		try {
		Files.walk(this.storagePath).sorted().forEach(file->{
			try {
				if (!Files.isDirectory(file)) {
					Files.deleteIfExists(file);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	} catch (IOException e) {
		e.printStackTrace();
	}

	}

	@Override
	public boolean check(String filePath) {
		filePath = filePath.replace("/media/jpg/", "");
		filePath = filePath.replace("/media/png/", "");

		if (filePath != null && filePath != "") {
			Path checkFilePath = this.storagePath.resolve(filePath);
			return Files.exists(checkFilePath)
					&& !Files.isDirectory(checkFilePath)
					&& Files.isReadable(checkFilePath);
		}

		return false;

	}

}
