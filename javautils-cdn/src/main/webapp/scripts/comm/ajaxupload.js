var uploadUrl = 'fileUploadServlet?func=ajaxUploadAvatar';



/**
 * 通用的异步文件上传功能
 * 
 * @param params
 * @param defaultUploadAnimation
 * @param successFunc
 * @param errorFunc
 * @return
 */
function uploadFile(params, defaultUploadAnimation, successFunc, errorFunc) {
	defaultUploadAnimation();
	$.ajaxFileUpload({
		url : uploadUrl,
		secureuri : false,
		fileElementId : params.iptId,
		dataType : 'json',
		data : params,
		success : function(data, status) {
			if (typeof (data.err) != 'undefined') {
				if (data.err != '') {
					errorFunc(data);
				} else {
					successFunc(data);
				}
			}
		},
		error : function(data, status, e) {
			alert(e);
		}
	});
	return false;
};
/* 默认的上传动画 */
function defaultUploadAnimation() {
	$("#loading").ajaxStart(function() {
		$(this).show();
	}).ajaxComplete(function() {
		$(this).hide();
	});
};
