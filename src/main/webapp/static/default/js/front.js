Shuogesha = {};
Shuogesha.viewCount = function(base, id, viewId, commentId, downloadId, upId,
		downId) {
	viewId = viewId || "views";
	commentId = commentId || "comments";
	downloadId = downloadId || "downloads";
	upId = upId || "ups";
	downId = downId || "downs";
	$.getJSON(base + "/count_view.jhtml", {
		id : id
	}, function(data) {
		if (data.length > 0) {
			$("#" + viewId).text(data[0]);
			$("#" + commentId).text(data[1]);
			$("#" + downloadId).text(data[2]);
			$("#" + upId).text(data[3]);
			$("#" + downId).text(data[4]);
		}
	});
}

var browser={ 
	isPC:function(){  
       var userAgentInfo = navigator.userAgent;  
       var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");  
       var flag = true;  
       for (var v = 0; v < Agents.length; v++) {  
           if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = false; break; }  
       }  
       return flag;  
	},	
    versions:function(){   
     var u = navigator.userAgent, app = navigator.appVersion;   
     return {//移动终端浏览器版本信息   
       trident: u.indexOf('Trident') > -1, //IE内核  
       presto: u.indexOf('Presto') > -1, //opera内核  
       webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核  
       gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核  
       mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端  
       ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端  
       android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器  
       iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器  
       iPad: u.indexOf('iPad') > -1, //是否iPad    
       webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部  
      };  
      }(),  
      language:(navigator.browserLanguage || navigator.language).toLowerCase()  
}