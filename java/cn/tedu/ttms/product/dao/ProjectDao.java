package cn.tedu.ttms.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.ttms.product.entity.Project;
/**项目模块持久层对象*/
public interface ProjectDao {
	/**
	 * 根据id查找项目对象
	 * @param id
	 * @return
	 */
	 Project findObjectById(Integer id);
	 /**
	  * 写入项目信息
	  * @param entity
	  * @return
	  */
	 int insertObject(
			@Param("entity")Project entity);
	 /**
	  * 修改项目信息
	  * @param entity
	  * @return
	  */
	 int updateObject(Project entity);
     /**获取所有项目信息*/
	 List<Project> findObjects();
	 /**
	  * @param name 查询时用户输入的项目名
	  * @param valid 查询时用户输入的状态
	  * @param startIndex 分页查询时的起始位置
	  * @param pageSize 每页最多显示多少条记录
	  * @return 当前页数据
	  */
	 List<Project> findPageObjects(
	   @Param("name")String name,
	   @Param("valid")Integer valid,
	   @Param("startIndex") int startIndex,
	   @Param("pageSize") int pageSize);
	 
	 /**
	  * @return 返回记录总数
	  */
	 int getRowCount(
		 @Param("name")String name,
		 @Param("valid")Integer valid);
	 /**
	  * 禁用或启用项目信息
	  * */
	 int validById(
			Integer valid,String[] ids);
}








