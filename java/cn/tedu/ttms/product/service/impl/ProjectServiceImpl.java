package cn.tedu.ttms.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.web.PageObject;
import cn.tedu.ttms.product.dao.ProjectDao;
import cn.tedu.ttms.product.entity.Project;
import cn.tedu.ttms.product.service.ProjectService;
@Service
public class ProjectServiceImpl 
           implements ProjectService{
	@Autowired
	private ProjectDao projectDao;
	@Override
	public List<Project> findObjects() {
		List<Project> list=projectDao.findObjects();
		return list;
	}
	@Override
	public Project findObjectById(Integer id) {
		//1.判定参数的有效性
		if(id==null)
		throw new ServiceException("id 不能为空");
		//2.根据id查找对象
		Project project=projectDao.findObjectById(id);
		//3.验证查询结果
		if(project==null)
		throw new ServiceException("对象不存在");
		//4.返回查询结果
		return project;
	}
	@Override
	public void updateObject(Project entity) {
		if(entity==null)
		throw new ServiceException("更新对象不能为空");
		int rows=projectDao.updateObject(entity);
		if(rows<=0)
		throw new ServiceException("update error");
	}
	@Override
	public void saveObject(Project entity) {
		if(entity==null)
	    throw new ServiceException("保存对象不能为空");
		int rows=projectDao.insertObject(entity);
		if(rows<=0)
		throw new ServiceException("insert error");
	}
	@Override
	public Map<String, Object> findPageObjects(
			String name,Integer valid,int pageCurrent) {
		int pageSize=2;
		int startIndex=(pageCurrent-1)*pageSize;
		//获取当前页数据
		List<Project> list=
		projectDao.findPageObjects(
				name,valid,startIndex,pageSize);
		//获取总记录数并封装分页信息
		int rowCount=
				projectDao.getRowCount(name,valid);
		PageObject pageObject=new PageObject();
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setStartIndex(startIndex);
		//将当前页数据以及分页信息封装到map
		Map<String,Object>  map=
				new HashMap<String,Object>();
		map.put("list", list);
		map.put("pageObject", pageObject);
		return map;
	}
	@Override
	public void validById(Integer valid, String ids) {
		//1.对数据进行业务验证
		if(valid!=0&&valid!=1)
		throw new ServiceException("valid的值不合法:valid="+valid);
		if(StringUtils.isEmpty(ids))
		throw new ServiceException("ids 的值不能为空");
		//2.对参数数据进行处理
		String[] idArray=ids.split(",");
		//3.执行业务更新操作
		int rows=projectDao.validById(valid, idArray);
		//4.验证结果的有效性
		if(rows==0)
		throw new ServiceException("修改失败");
	}//静态代码检测工具
	


}




