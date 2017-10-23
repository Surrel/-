package cn.tedu.ttms.attachment.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.tedu.ttms.attachment.entity.Attachment;

public interface AttachmentService {
	void saveObjects(String title,MultipartFile mFile);
	
	public List<Attachment> findObjects();
	
	public Attachment findObjectById(Integer id);
}
