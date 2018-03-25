
function getFormatDate(value){
	var date = new Date(value);
	var M = date.getMonth()+1;
	var MM = M<10?("0"+M):M;
	var d = date.getDate();
	var dd = d<10?("0"+d):d;
	var H = date.getHours();
	var HH = H<10?("0"+H):H;
	var m = date.getMinutes();
	var mm = m<10?("0"+m):m;
	var s = date.getSeconds();
	var ss = s<10?("0"+s):s;
	return date.getFullYear()+"-"+MM+"-"+dd+" "+HH+":"+mm+":"+ss;
}

function getFormatDateNohms(value){
	var date = new Date(value);
	var M = date.getMonth()+1;
	var MM = M<10?("0"+M):M;
	var d = date.getDate();
	var dd = d<10?("0"+d):d;
	return date.getFullYear()+"-"+MM+"-"+dd;
}
