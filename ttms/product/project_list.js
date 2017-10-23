$(document).ready(function(){
	 console.log("===========ready==========");
	//在查询按钮上注册点击事件
	 $("#queryFormId")
	 .on("click",".btn-search",doQueryObjects)
	 .on("click",".btn-valid,.btn-invalid",doValidById)
	 .on("click",".btn-add,.btn-update",doLoadEditPage)
	 //页面加载完成执行数据的获取操作
	  doGetObjects();
});
/*加载编辑页面*/
function doLoadEditPage(){
   //定义访问编辑页面的url地址
   var url="project/editUI.do";
   //$(".content").load(url)
   var title;
   //var cls=$(this).attr("class");//btn btn-primary btn-add
   //if(cls=="btn btn-primary btn-add");
   if($(this).hasClass("btn-add")){
	   title="添加项目信息"
   }
   if($(this).hasClass("btn-update")){
	   //将获得的要修改记录的id值绑定到模态框对象上
	   //为什么要绑定这个id值?
	   //目的是根据模态框的这个id值判定是要执行添加还是修改
	   var idValue=$(this).parent().parent().data("id")
	   $("#modal-dialog").data("idKey",idValue);
	   title="修改项目信息,id="+idValue;
   }
   //在模态框中异步加载显示编辑页面.
   //本项目中模态框的定义在index.jsp中,而且默认是隐藏的.
   $("#modal-dialog .modal-body")
   .load(url,function(){//callback method
	  $(".modal-title").html(title);
	  //页面加载完成显示模态框(其中show表示显示,hide表示隐藏)
	  $("#modal-dialog").modal("show");
   });
};



/*禁用或启用项目信息*/
function doValidById(){//valid,ids
  //1.设置valid的值
  var valid;
  //1.1获得点击的button对象,根据点击对象的不同设置不同的valid
  /*var clazz=$(this).attr("class");
  if(clazz=="btn btn-primary btn-valid"){
	valid=1;  
  }*/
  if($(this).hasClass("btn-valid")){
	  valid=5;//启用
  }
  if($(this).hasClass("btn-invalid")){
	  valid=0;//禁用
  }
  //2.获得选中的checkbox的id值
  var ids="";//1,2,3,4
  $("#tbodyId input[name='checkId']")//迭代input对象
  .each(function(){//each函数用于迭代一个数组
	  if($(this).prop("checked")){//判定此input对象是否是选中的
		  if(ids==""){
			  ids+=$(this).val();
		  }else{
			  ids+=","+$(this).val();
		  }
		  
	  }
  });
  //console.log("valid="+valid);
  //console.log("ids="+ids);
  if(ids==""){
	  alert("请至少选择一个");
	  return;
  }
  //3.发起异步请求,更新数据
  var url="project/doValidById.do";
  var params={
		  "valid":valid,
		  "ids":ids
  }
  console.log(params);
  $.post(url,params,function(result){//controller return new JsonResult("..");
	  if(result.state==1){//{state:1,message:"",data:null}
	    alert(result.message);
	    //重新执行查询操作
	    doGetObjects();
	  }else if(result.state==0){//{state:0,message:"异常信息",data:null}
		alert(result.message); 
	  }
  });

}
/*点击查询按钮时执行此方法*/
function doQueryObjects(){
	//1.初始化当前页码数据
	$("#pageId").data("pageCurrent",1);
	//2.根据条件查询数据
	doGetObjects();
}
/*获取项目信息*/
function doGetObjects(){
	//var url="project/doGetObjects.do";
	/*$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		success:function(result){
		}
	});*/
	var url="project/doGetPageObjects.do"
	var pageCurrent=$("#pageId").data("pageCurrent");
	if(!pageCurrent)pageCurrent=1;//默认取第一页的数据
	var params={"pageCurrent":pageCurrent};
	//获得表单数据,并将其添加到params对象
	params.name=$("#searchNameId").val();
	params.valid=$("#searchValidId").val();
	console.log(params);//检测一下向服务端传递的数据
	//发起异步请求获取服务端数据
	$.getJSON(url,params,function(result){//callback method
		//console.log(result);//json对象
		if(result.state==1){
		//将数据显示在table的tbody位置
		setTableBodyRows(result.data.list);//map中的key对应的值
		//设置分页信息(函数定义在了page.js文件中)
		setPagination(result.data.pageObject);
		}else if(result.state==0){
		 alert(result.message);
		}
	});
}
/*将数据填充在table对象的body中*/
function setTableBodyRows(data){//扩展作业
	 //1.获得tbody对象
	 var tBody=$("#tbodyId");
	 tBody.empty();
	 //2.迭代数据集result
	 //for(var i=0;i<result.length;i++){}
	 for(var i in data){
	 //2.1构建一个tr对象
	 var tr=$("<tr></tr>");
	 tr.data("id",data[i].id);
	 //2.2构建每行td对象(一行有多个)
	 //var td0=$("<td></td>");
	 //....
	 //2.3在td对象内容填充具体数据
	 //td0.append(result[id].id);
	 //....
	 var tds="<td><input type='checkbox' name='checkId' value='"+data[i].id+"'></td>"+
	         "<td>"+data[i].code+"</td>"+
	         "<td>"+data[i].name+"</td>"+
	         "<td>"+data[i].beginDate+"</td>"+
	         "<td>"+data[i].endDate+"</td>"+
	         "<td>"+(data[i].valid?"有效":"无效")+"</td>"+
	         "<td><input type='button' class='btn btn-default btn-update' value='修改'/></td>";
	 //2.4将td添加到tr对象中(一行要放多个)
	 tr.append(tds);
	 //2.5将tr追加到tbody中
	 tBody.append(tr);
	 }
}




