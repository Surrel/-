package cn.tedu.ttms.product.service;

import java.util.List;
import java.util.Map;

import cn.tedu.ttms.common.vo.Node;
import cn.tedu.ttms.product.entity.ProductType;
public interface ProductTypeService {
	  List<Node> findZtreeNodes();
	  List<Map<String,Object>>
	        findObjects();
	  void deleteObject(Integer id);
	  void saveObject(ProductType entity);
}
