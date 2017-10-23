package cn.tedu.ttms.product.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.ttms.common.vo.Node;
import cn.tedu.ttms.common.web.JsonResult;
import cn.tedu.ttms.product.entity.ProductType;
import cn.tedu.ttms.product.service.ProductTypeService;
@Controller
@RequestMapping("/type/")
public class ProductTypeController {
	 //private java.util.logging.Logger log=java.util.logging.Logger.getLogger(ProductTypeController.class.getName());
	 private Logger log= LoggerFactory.getLogger(ProductTypeController.class);
	 @Autowired
	 private ProductTypeService productTypeService;
	 @RequestMapping("listUI")
	 public String listUI(){
		 return "product/type_list";
	 }
	 @RequestMapping("editUI")
	 public String editUI(){
		 return "product/type_edit";
	 }
	 @RequestMapping("doSaveObject")
	 @ResponseBody
	 public JsonResult doSaveObject(ProductType entity){
		 productTypeService.saveObject(entity);
		 return new JsonResult("insert ok");
	 }
	 @RequestMapping("doFindZtreeNodes")
	 @ResponseBody
	 public JsonResult doFindZtreeNodes(){
		 //System.out.println("==doFindZtreeNodes==");
		 List<Node> list=
		 productTypeService.findZtreeNodes();
		 //log.info("zTree.data="+list);
		 //log.error("list="+list);
		 //log.debug(arg0);
		 return new JsonResult(list);
	 }
	 @RequestMapping("doFindObjects")
	 @ResponseBody
	 public JsonResult doFindObjects(){
		 List<Map<String,Object>> map=
				productTypeService.findObjects();
		 System.out.println(map);
		 return new JsonResult(map);
	 }
	 @RequestMapping("doDeleteObject")
	 @ResponseBody
	 public JsonResult deleteObject(Integer id){
		 productTypeService.deleteObject(id);
		 return new JsonResult("删除OK");
	 }
}
