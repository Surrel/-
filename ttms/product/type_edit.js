var zTree;
var setting = {//zTree中显示数据结构的一个基本配制
		data : {   
			simpleData : {
				enable : true,
				idKey : "id",  //节点数据中保存唯一标识的属性名称
				pIdKey : "parentId",//节点数据中保存其父节点唯一标识的属性名称
				rootPId : null  //根节点id
			}
		}
}
$(document).ready(function(){	
	$("#editTypeForm")
	.on("click",".load-product-type",loadZtreeNodes);
	
	$("#typeLayer")
	.on("click",".btn-cancle",doHideZtree)
	.on("click",".btn-confirm",doSetSelectedType)
	
	$("#btn-return").click(function(){
		doBack();
	});
	$("#btn-save").click(function(){
		doSaveOrUpdate();
	})
});
function doSaveOrUpdate(){
	//debugger
	console.log("==doSaveOrUpdate===");
	//1.获取表单数据
	if(!$("#editTypeForm").valid())return;
	var params=getEditFormData();
	//2.保存数据
	var url="type/doSaveObject.do";
	$.post(url,params,function(result){
		 if(result.state==1){
			 alert(result.message);
			 doBack();
		 }else{
			 alert(result.message);
		 }
	});
}
function getEditFormData(){
	var params={
	   name:$("#typeNameId").val(),		
	   parentId:$("#parentNameId").data("parentId"),		
	   sort:$("#typeSortId").val(),	
	   note:$("#typeNoteId").val(),		
	}
	return params;
}
/*设置上级分类信息*/
function doSetSelectedType(){
   //1.获得选中的数据信息
   //FAQ:当此处报getSelectedNodes方法为定义(undefine)
   //首先检测方法名,其次检测zTree是否已赋值.
   var nodes=zTree.getSelectedNodes();
   //2.将数据信息填充在form表单中
   //console.log(nodes[0].id);
   $("#parentNameId").val(nodes[0].name);
   $("#parentNameId").data("parentId",nodes[0].id);
   //3.隐藏zTree对象.
   doHideZtree();
}
/*隐藏zTree*/
function doHideZtree(){
	$("#typeLayer").css("display","none");
}
/*编辑编辑页面的上级分类表单元素时执行此函数*/
function loadZtreeNodes(){
	//显示ztree窗口
	$("#typeLayer").css("display","block");
	//异步加载数据
	var url="type/doFindZtreeNodes.do";
	$.getJSON(url,function(result){
		console.log(result.data)
		if(result.state==1){
			console.log("===init===")
			//jquery.zTree.js
			//init函数为zTree中的一个用于初始化数据的函数
			zTree=$.fn.zTree.init(
			$("#typeTree"),setting,result.data);
		}else{
			alert(result.message);
		}
	});
}
function doBack(){
	var url="type/listUI.do";
    $(".content").load(url);	
}









