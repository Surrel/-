package cn.tedu.ttms.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.vo.Node;
import cn.tedu.ttms.product.dao.ProductTypeDao;
import cn.tedu.ttms.product.entity.ProductType;
import cn.tedu.ttms.product.service.ProductTypeService;
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
	@Autowired
	private ProductTypeDao productTypeDao;
	
	@Override
	public List<Node> findZtreeNodes() {
		return productTypeDao.findZtreeNodes();
	}
	
	@Override
	public List<Map<String, Object>> findObjects() {
		return productTypeDao.findObjects();
	}
	@Override
	public void saveObject(ProductType entity) {
		System.out.println("insert.before.entity="+entity);
		if(entity==null)
		throw new ServiceException("保存对象不能为空");
		int rows=productTypeDao.insertObject(entity);
		if(rows<1)
		throw new ServiceException("数据保存失败");
		System.out.println("insert.after.entity="+entity);
	}
	@Override
	public void deleteObject(Integer id) {
		//1.判定id的有效性
		if(id==null||id<=0)
		throw new ServiceException("id的值无效,id="+id);
		//2.判定此id对应的分类是否有子分类
		int count=productTypeDao.hasChilds(id);
		if(count>0)
		throw new ServiceException("有子元素不元素删除");
		//3.执行删除动作
		int rows=productTypeDao.deleteObject(id);
		if(rows<1)
		throw new ServiceException("删除失败");
	}

	
}
