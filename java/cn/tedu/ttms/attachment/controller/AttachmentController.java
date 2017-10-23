package cn.tedu.ttms.attachment.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.ttms.attachment.entity.Attachment;
import cn.tedu.ttms.attachment.service.AttachmentService;
import cn.tedu.ttms.common.web.JsonResult;

@RequestMapping("/attachment/")
@Controller
public class AttachmentController {
	
	  @Autowired
	  private AttachmentService attachmentService;	 
	
	  @RequestMapping("attachmentUI")
	  public String listUI(){
		return "attachment/attachment";  
	  }
	  @RequestMapping("doUpload")
	  @ResponseBody
	  public JsonResult doUpload(String title,
			  MultipartFile mFile)throws IOException{
		  /*String name=mFile.getOriginalFilename();
		  System.out.println("name="+name);
		  File dest=new File("d:\\"+name);
		  //将文件内容赋值到dest对象
		  mFile.transferTo(dest);*/
		  
		  attachmentService.saveObjects(title, mFile);
		  return new JsonResult("upload ok");
	  }
	  
	  @RequestMapping("doFindObjects")
	  @ResponseBody
	  public JsonResult doFindObjects(){
		  List<Attachment> list = attachmentService.findObjects();
		  return new JsonResult(list);
	  }

	  @RequestMapping("doDownload")
	  @ResponseBody
	  public byte[] doDownload(Integer id,HttpServletResponse response) throws IOException{
		  //根据id获得对象
		  Attachment a  = attachmentService.findObjectById(id);  
		  //设置协议头
			//2.设置下载内容类型以及响应头(固定格式)
	    	response.setContentType("appliction/octet-stream");
	    	//中文文件名可能有乱码,通过如下语句对文件名编码
	    	String fileName=URLEncoder.encode(a.getFileName(),"utf-8");
			response.setHeader("Content-disposition",
					"attachment;filename="+fileName);
		 
		  //根据文件路径构建一个Path
		  Path path = Paths.get(a.getFilePath());
		  //读取指定路径下的文件字节
		  return Files.readAllBytes(path);
	  
	  }
}




