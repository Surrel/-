package cn.tedu.ttms.attachment.service.Impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;

import cn.tedu.ttms.attachment.dao.AttachmentDao;
import cn.tedu.ttms.attachment.entity.Attachment;
import cn.tedu.ttms.attachment.service.AttachmentService;
import cn.tedu.ttms.common.exception.ServiceException;

@Service
public class AttachementImpl implements AttachmentService{
	
	/**实现文件上传
	 * 1)将文件存储到服务器
	 * 2)将文件信息存储到数据库
	 * */
	
	@Autowired
	private AttachmentDao attachmentDao;
	public void saveObjects(String title,MultipartFile mFile){
		
		if(StringUtils.isEmpty(title)){
			throw new ServiceException("title不能为空");
		}
		if(mFile==null){
			throw new ServiceException("请选择文件");
		}
		if(mFile.isEmpty()){
			throw new ServiceException("文件不能为空");
		}

		//2.判定文件是否已上传(根据摘要信息)
		//2.1)根据mFile内容生成摘要信息(MD5)
		
		String digest = null;
		try{
			byte[] bytes = mFile.getBytes();

			digest = DigestUtils.md5DigestAsHex(bytes);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("文件上传失败");
		}
		///mFile.transferTo(dest);
		//2.2)根据摘要信息进行数据库查询
		int count = attachmentDao.getRowCountByDigest(digest);
		//2.3)根据查询的结果判定文件是否已上传
		if(count>0){
			throw new ServiceException("文件已经上传");
		}
		//3.假如文件不在则上传文件
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD");
		String dateDir = sdf.format(new Date());
		File fileDir = new File("e:/uploads/" + dateDir);
		//	判定目录是否存在,不在则创建
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		//构建文件对象(fileDir为目录,mFile.getOriginalFilename文件名)
		File dest = new File(fileDir,mFile.getOriginalFilename());
		
		try{
			//上传文件
			mFile.transferTo(dest);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("文件上传失败!");
		}
		
		//4.将文件信息保存到数据库
		Attachment a = new Attachment();
		a.setTitle(title);
		a.setFileName(mFile.getOriginalFilename());
		a.setFileDisgest(digest);
		a.setFilePath(dest.getPath());
		a.setContentType(mFile.getContentType());
		
		int rows = attachmentDao.insertObject(a);
		
		//5.验证保存结果
		if(rows<0){
			throw new ServiceException("保存文件失败");
		}
	}
	@Override
	public List<Attachment> findObjects() {
		
		return attachmentDao.findObjects();
	}
	@Override
	public Attachment findObjectById(Integer id) {
		if(id==null){
			throw new ServiceException("id不能为空");
		}
		Attachment a = attachmentDao.findObjectById(id);
		if(a==null){
			throw new ServiceException("对象已不存在");
		}
		return a;
	}
}
