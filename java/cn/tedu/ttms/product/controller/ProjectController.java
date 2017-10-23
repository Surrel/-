package cn.tedu.ttms.product.controller;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.web.JsonResult;
import cn.tedu.ttms.product.dao.ProjectDao;
import cn.tedu.ttms.product.entity.Project;
import cn.tedu.ttms.product.service.ProjectService;

@Controller
@RequestMapping("/project/")
public class ProjectController{
	  private  Logger log=
			   Logger.getLogger(
			   ProjectController.class.getName());
	  @Autowired
	  @Qualifier("projectServiceImpl")
	  private ProjectService projectService;
	  
      /**返回项目列表页面*/
	  @RequestMapping("listUI")
	  public String listUI(){
		  return "product/project_list";
	  }
	  
	  /**返回项目编辑页面*/
	  @RequestMapping("editUI")
	  public String eidtUI(){
		  return "product/project_edit";
	  }
	  @RequestMapping("doFindObjectById")
	  @ResponseBody
	  public JsonResult doFindObjectById(Integer id){
	        Project project=
	        projectService.findObjectById(id);
	        return new JsonResult(project);
	  }
	  @RequestMapping("doUpdateObject")
	  @ResponseBody
	  public JsonResult doUpdateObject(
			  Project entity){
	       projectService.updateObject(entity);
	       return new JsonResult("更新OK");
	  }
	  /**
	   * name="东欧游"&code="..."&beginDate="..."
	   * spring 获得参数数据以后会对数据进行解析然后
	   * 调用project对象的setXXX方法将数据存储到project
	   * 对象.
	   */
	  @RequestMapping("doSaveObject")
	  @ResponseBody
	  public JsonResult doSaveObject(Project entity){
	     projectService.saveObject(entity);
	     return new JsonResult("insert ok");
	  }
	  /**禁用或启用项目信息
	   * {
	   *  state:1,
	   *  message:"ok"
	   *  data:{}
	   * }
	   * */
	  @RequestMapping("doValidById")
	  @ResponseBody
	  public JsonResult doValidById(Integer valid,
			  String ids){
		  log.info("valid:"+valid);
		  log.info("ids:"+ids);
		  projectService.validById(valid, ids);
		  //return new JsonResult(); message="OK"
		  return new JsonResult(valid==1?"启用OK":"禁用OK");
	  }
	  
	 /* 
	     刷新整个project_list.jsp的一种写法(不推荐)
	   @RequestMapping("doValidById")
	   public String doValidById(Integer valid,
			  String ids){
		  projectService.validById(valid, ids);
	      return "redirect:listUI.do?t="+System.currentTimeMillis();
	   }
	  */
	  @RequestMapping("doGetPageObjects")
	  @ResponseBody
	  public JsonResult doGetPageObjects(
	    		  String name,
	    		  Integer valid,
	    		  Integer pageCurrent){
		  log.info("name="+name);//乱码
		  log.info("valid="+valid);
		  log.info("pageCurrent="+pageCurrent);
		  Map<String,Object> map=
		  projectService.findPageObjects(
				  name,valid,pageCurrent);
		  log.info("map="+map);
		  return new JsonResult(map);//map对象转换为json串的表示形式如下
		  /*{
		   * state:1,
		   * message:"ok"
		   * data:
		   * {
		     "list":[{id:1,name:"环球游",...},{...}] 
		     "pageObject":{pageCurrent:1,pageCount:2,...}
		     }
		     }
		   */
	  }
	/*  @ExceptionHandler(ServiceException.class)
	  @ResponseBody
	  public JsonResult handleServiceException(
			  ServiceException e){
		     e.printStackTrace();
		     return new JsonResult(e);//state=0,message=e.getMessage();
	  }*/
	  
	  @RequestMapping("doGetObjects")
	  @ResponseBody
	  public List<Project> doGetObjects(){
		  //System.out.println(projectService);
		  log.info("projectService="+projectService);
		  List<Project> list=
		  projectService.findObjects();
		  return list;
	  }
	  /**spring 发现控制层方法上有ResponseBody注解,
	   * 此时就会启用第三方API(例如jackson,gson),
	   * 将返回的对象转换为JSON字符串
	   * [
	   *  {"id":1,"name":"环球游",...},
	   *  {"id":1,"name":"月球游",...},
	   * ]
	   */	
	  
}
