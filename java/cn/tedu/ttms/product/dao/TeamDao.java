package cn.tedu.ttms.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.tedu.ttms.product.entity.Team;

public interface TeamDao {
	   /**
	    * 实现团目信息的分页查询
	    * @param name  项目名称
	    * @param startIndex
	    * @param endIndex
	    * @return
	    * 分析:此处list集合中为什么会放一个map
	    * 而不是List<Team>;
	    */
	   List<Map<String,Object>>
	   findPageObjects(
			@Param("name") String name,
			@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);
	   /**
	    * 按条件统计记录总数
	    * @param name
	    * @return
	    */
	   int getRowCount(@Param("name")String name);
	   //int insertObject(Team entity);
	   
}
