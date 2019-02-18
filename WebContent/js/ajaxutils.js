//生成异步对象
function createXMLHttpRequest(){
	try {	
			return new XMLHttpRequest();
		}catch(e)
		{
			try
			{
				return new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch(e)
			{
				try{
					return new ActiveXObject("Microsoft.XMLHTTP");
				
				}catch(e){
					alert("哥们，您用的是什么浏览器？");
					throw e;
					}
			}
		}

};



/*
 * option是一个对象
 * 请求方式， url, 是否同步, 请求体(post), 回掉函数fun ,响应数据转换成什么类型type
 * 
 * */
function ajax(option){
	//1.得到xmlHttp
	var xmlHttp = createXMLHttpRequest();
	//2.打开连接
	if(option.method == undefined){//默认GET请求
		option.method = "GET";
	}
	if(option.asyn == undefined){//默认异步请求
		option.asyn = true;
	}
	xmlHttp.open(option.method, option.url, option.asyn);
	if(option.method=="POST" || option.method=='post'){
		
		xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	}
	//3.发送
	xmlHttp.send(option.params);
	//4.监听
	xmlHttp.onreadystatechange=function()
	{
		if(4==xmlHttp.readyState)
		{
			//http状态码 200代表成功
			if(xmlHttp.status == 200)
			{	//获取服务器返回的数据，转换
				var data;
				//这里感觉永远都是text,
				if(!option.type){
					data = xmlHttp.responseText;
				}	
				else if(option.type == 'xml'){
					data = xmlHttp.responseXML;
				}else if(option.type== "text"){
					data = xmlHttp.responseText;
				}else if(option.type == "json"){
					var text = xmlHttp.responseText;
					data = eval("("+ text +")");
				}
				//回掉方法
				option.callback(data);
			}
		}
	};
};



