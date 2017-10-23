package cn.tedu.ttms.product.service;
import java.util.Map;
public interface TeamService {
	/**
	 * 分页查询显示数据
	 * @param name
	 * @param pageCurrent
	 * @return 包含当前页数据List<Map<String,Object>>
	 * 和分页信息PageObject
	 */
	Map<String,Object> findPageObjects(
		String name,Integer pageCurrent);
	
}
