$(document).ready(function(){
   $("#modal-dialog")
   //模态框的.ok按钮上注册点击事件
   .on("click",".ok",doSaveOrUpdate);
   //在模态框隐藏后移除ok上注册的事件,防止数据多次提交.
   $("#modal-dialog")
   .on("hidden.bs.modal",function(){
	   console.log("==hidden.bs.modal==");
	   $("#modal-dialog").off("click",".ok");
	   //模态框隐藏时移除绑定的idKey(为什么?)
	   $("#modal-dialog").removeData("idKey");
	   //$(this).off("click",".ok");
	   //"hidden.bs.modal"表示模态框隐藏以后要执行的动作
   });
   //获取模态框上绑定的值
   var id=$("#modal-dialog").data("idKey");
   if(id)doFindObjectById(id);
});
/*根据id执行查询操作*/
function doFindObjectById(id){
	var url="project/doFindObjectById.do";
	var params={"id":id};
	$.getJSON(url,params,function(result){
		if(result.state==1){
		  doInitFormData(result.data);//{"name":"","code":"",...}
		}else if(result.state==0){
		  alert(result.message);
		}
	});
}
/*修改初始化表单数据*/
function doInitFormData(data){
	$("#nameId").val(data.name);
	$("#codeId").val(data.code);
	$("#beginDateId").val(data.beginDate);
	$("#endDateId").val(data.endDate);
	$("#noteId").html(data.note);
	$("#editFormId input[name='valid']")
	.each(function(){
		if($(this).val()==data.valid){
			$(this).prop("checked",true);
		}
	});
}
/*添加或修改数据*/
function doSaveOrUpdate(){
	//0.验证表单数据是否为空
	if(!$("#editFormId").valid())return;
	//1.获得表单数据
	var params=getEditFormData();
	console.log(params);
	//2.异步提交表单数据
	var insertUrl="project/doSaveObject.do";
	var updateUrl="project/doUpdateObject.do";
	//获取模态框上绑定的id值.
	var id=$("#modal-dialog").data("idKey");
	//根据id判定是insert还是update
	var url=id?updateUrl:insertUrl;
	//在修改时需要id的值,所以假如是修改需要动态添加id
	if(id)params.id=id;
	
	$.post(url,params,function(result){
		if(result.state==1){
			//隐藏模态框
			$("#modal-dialog").modal("hide");
			//显示相关信息
			alert(result.message);
			//重新查询数据
			doGetObjects();
		}else{
			alert(result.message);
		}
	});
}
/*获取表单数据*/
function getEditFormData(){
	var params={
	  name:$("#nameId").val(),
	  code:$("#codeId").val(),
	  beginDate:$("#beginDateId").val(),
	  endDate:$("#endDateId").val(),
	  valid:$("input[type='radio']:checked").val(),
	  note:$("#noteId").val()
	}//JSON 对象
	return params;
}