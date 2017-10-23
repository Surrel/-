package cn.tedu.ttms.product.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.ttms.common.vo.Node;
import cn.tedu.ttms.product.entity.ProductType;

public interface ProductTypeDao {
	/**
	 * 保存数据到数据库
	 * @param entity
	 * @return
	 */
	int insertObject(ProductType entity);
	 /**
	  * 查询分类信息中的id,parentId,name
	  * @return
	  */
	 List<Node> findZtreeNodes();
	
	 List<Map<String,Object>> 
	        findObjects();
	 /**
	  * 判定此id下是否有子元素
	  * @param id
	  * @return 返回值为0表示没子字元素
	  */
	 int hasChilds(Integer id);
	 /**
	  * 根据id删除分类信息
	  * @param id
	  * @return
	  */
	 int deleteObject(Integer id);
}
