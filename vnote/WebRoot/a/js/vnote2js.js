/**
 * 只写模式的js文件
 */

	var edit = 0;
	$(document).ready(function() {
		//读取文本
		get();

		//杂项
		others();
	});
	var timer = null;
	// 点击提交按钮提交数据
	function bindup() {

		edit = 1;

		//alert($("#txt2").val());
		var str = $("#txt2").val() + "\n" + "---------由" + $("#nicheng").val()
				+ " 添加于" + new Date().toLocaleString() + "-----------"
				+ "\n\n\n" + $("#txt").val();
		//绑定数据上传事件

		$.post("ajaxtxt", {
			id : $("#myid").val(),
			txt : str
		}, function(data, status) {
			edit = 0;
			get();
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
	function others() {
		$("#txt2").on("click", function() {
			if ($("#txt2").val() == "输入文本...") {
				$("#txt2").val("");
			}
		});
		$("#txt2").on("blur", function() {
			if ($("#txt2").val() == "") {
				$("#txt2").val("输入文本...");
			}
		});
	}
	function tobianji(){
	
		$.post("pwd", {
			id : $("#myid").val(),
			mod : 0,//请求切换为0模式（编辑模式）
			frm:0
		}, function(data, status) {
		$("#layer3").html(data);
		});			
	}
	function myhelp(){
		if($('#help').css('display')=='none'){
			$('#myhelpc').load("a/help.jsp", function(data) {
				$('#myhelpc').html(data);
			});
			$('#help').show();
		}
		else {
			$('#myhelpc').html("");
			$('#help').hide();
		}	
	}
	window.onunload=function(){
		//备份到数据库
		$.post("backup");
		} 