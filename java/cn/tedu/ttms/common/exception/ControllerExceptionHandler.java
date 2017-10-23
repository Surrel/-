package cn.tedu.ttms.common.exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.ttms.common.web.JsonResult;
/**全局异常处理对象:
 * 希望通过此类实现所有Controller中的异常处理
 * @ControllerAdvice 声明的类可以作为统一异常处理对象
 */
@ControllerAdvice
public class ControllerExceptionHandler {
	  @ExceptionHandler(ServiceException.class)
	  @ResponseBody
	  public JsonResult handleServiceException(
			  ServiceException e){
		     e.printStackTrace();
		     return new JsonResult(e);//state=0,message=e.getMessage();
	  }
	  
	 /*@ExceptionHandler(Exception.class)
	  public ModelAndView handleException(
			  ServiceException e){
		     e.printStackTrace();
		     ModelAndView mv=new ModelAndView();
		     mv.setViewName("error");
		     mv.addObject("message", e.getMessage());
		     return mv;
	  }*/
}
