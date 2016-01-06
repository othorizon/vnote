/**
 * 编辑模式的js文件
 */

	var edit = 0;
	$(document).ready(function() {
		//读取文本
		get();
		$("#txt").blur(function() {
			if(edit==1){
			$.post("ajaxloseedit");
			$("#editstatus").html("<font color=gray>查看中...</font>");
		}
		});
		$("#txt").on("click", function() {
			$("#editstatus").html("<font color=green>编辑中...</font>");
			if (edit == 0) {
				$.post("ajaxgetedit", {
					id : $("#myid").val()
				}, function(data, status) {
					if (data == "true") {
						bindup();
						edit = 1;
					} else {
						$("#tip").html("<font color=red>正在被编辑，不能修改</font>");
						setTimeout(function() {
							$("#tip").html("");
						}, 2000);
						$("#txt").blur();
					}
				});
			}
		});	
	});

	var timer = null;
	function bindup() {

		//alert($("#myid").val());
		//绑定数据上传事件
		$("#txt").on("keyup", function() {
			$.post("ajaxtxt", {
				id : $("#myid").val(),
				txt : $("#txt").val()
			}, function(data, status) {
				// alert("Data: " + data + "\nStatus: " + status);
				//松开按键后放弃编辑权限
				window.clearInterval(timer);
				timer = setTimeout(function() {
					$("#txt").blur();
					edit = 0;
					get();
					$.post("ajaxloseedit");
				}, 6000)
			});
		});
	}
	function get() {
		$.post("ajaxget", {
			id : $("#myid").val()
		}, function(data, status) {
			$("#txt").val(data);
			if (edit == 0)
				setTimeout(get(), 3000)
		});
	}

	function AddFavorite(sURL, sTitle) {
		url = window.location.href;
		id = $("#myid").val();
		title = "vnote-" + id;
		try {
			window.external.addFavorite(url, title);
		} catch (e) {
			try {
				window.sidebar.addPanel(url, title, "");
			} catch (e) {
				alert("请按 Ctrl+D 加入收藏夹");
			}
		}
	}
	function tobianji() {

		$.post("pwd", {
			id : $("#myid").val(),
			mod : 1,
			frm:0
		//请求切换为1模式（只写模式）
		}, function(data, status) {
			$("#layer3").html(data);
		});
	}
	function mdfpwd(){

		$.post("pwd", {
			id : $("#myid").val(),
			mod : 0,
			frm:1 		
		}, function(data, status) {
			$("#layer3").html(data);
		});
	}

window.onunload=function(){
//备份到数据库
$.post("backup");
}   
