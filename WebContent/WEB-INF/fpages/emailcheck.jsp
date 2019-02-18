<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<title>激活账号</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Baxster Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
SmartPhone Compatible web template, free WebDesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- Custom CSS -->
<link href="css/style.css" rel='stylesheet' type='text/css' />
<!-- font CSS -->
<link rel="icon" href="favicon.ico" type="image/x-icon" >
<!-- font-awesome icons -->
<link href="css/font-awesome.css" rel="stylesheet"> 
<!-- //font-awesome icons -->
<!-- chart -->
<script src="js/Chart.js"></script>
<!-- //chart -->
 <!-- js-->
<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/modernizr.custom.js"></script>
<script src="js/ajaxutils.js"></script>

<!--webfonts-->
<link href='https://fonts.googleapis.com/css?family=Roboto+Condensed:400,300,300italic,400italic,700,700italic' rel='stylesheet' type='text/css'>
<!--//webfonts--> 
<!--animate-->
<link href="css/animate.css" rel="stylesheet" type="text/css" media="all">
<script src="js/wow.min.js"></script>
	<script>
		 new WOW().init();
	</script>
<!--//end-animate-->
<!-- Metis Menu -->
<script src="js/metisMenu.min.js"></script>
<script src="js/custom.js"></script>
<link href="css/custom.css" rel="stylesheet">
<!--//Metis Menu -->
</head> 
<body class="cbp-spmenu-push">
	<div class="main-content">
		
		<!-- main content start-->
		<div id="page-wrapper">
			<div class="main-page">
				<!--grids-->
				<div class="grids">
					<div class="panel panel-widget forms-panel">
						<div class="forms">
							<div class=" form-grids form-grids-right">
								<div class="widget-shadow " data-example-id="basic-forms"> 
									<div class="form-title">
										<h4>激活账户</h4>
									</div>
									<div class="form-body">
										<form class="form-horizontal" action="<c:url value='/user.action!mailcheck'/>" method="post">
										<input id="truechecknum" type="hidden" name="truechecknum" value="${requestScope.truechecknum }"/>
										<input type="hidden" name="checkuserno" value="${requestScope.checkuserno }"/>
										<input id='email' type="hidden" name="email" value="${requestScope.email }"/>
										<div class="form-group"> 
										<label for="inputEmail3" class="col-sm-2 control-label">验证码</label>
										 <div class="col-sm-9"> 
										 	<input type="text" name="checknum" class="form-control" id="inputEmail3" placeholder="请您前往查看您的邮件，并填写验证码">
										  </div> 
										 </div> 
										 <div class="form-group">
										 	<div class='col-sm-4 col-sm-offset-2' style='color:red;'>${requestScope.checknum_err }</div>
										 </div>
										   <span class="col-sm-offset-2"> <button type="submit" class="btn btn-default">确认</button> </span>
											<span class="col-sm-offset-2"> <button id='resendmail' type="button" class="btn btn-default ">重新发送邮件</button> </span>
										    </form> 
									</div>
								</div>
							</div>
						</div>	
					</div>
				</div>
				<!--//grids-->
				
			</div>
		</div>
		
	</div>
	<!--重新发送邮件的js-->
	
	<script type="text/javascript">
	//获得按钮
	var resendmail = $('#resendmail');
	resendmail.click(function(){
		//当你点击按钮时，必然是没有disabled类的
		//发送AJAX请求,
		//按钮加上disabled类，并在60s后移除
		if(resendmail.hasClass('disabled'))
			return;
		//ajax?
		var emailtext = $('#email').val();
		
		ajax(
				{
					url:"<c:url value='/user.action!resendMail'/>",
					type:"text",
					method:"post",
					params:"email=" + emailtext,
					callback:function(data){
						var temp = eval("(" + data + ")");
						$('#truechecknum').val(temp);
					}
				}
			);
		//ajax end
		var count = 10;

		resendmail.text('等待' + count +'s...');
		$(this).addClass('disabled');
		var timer = setInterval(function(){
			count--;
			resendmail.text('等待' + count +'s...');
			if(count == 0){
				count = 10;
				resendmail.removeClass('disabled');
				resendmail.text('重新发送邮件');
				timer = clearInterval(timer);
			}

			
		}, 1000);

	});
	</script>
	<!-- Classie -->
		<script src="js/classie.js"></script>
		<script>
			var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
				showLeftPush = document.getElementById( 'showLeftPush' ),
				body = document.body;
				
			showLeftPush.onclick = function() {
				classie.toggle( this, 'active' );
				classie.toggle( body, 'cbp-spmenu-push-toright' );
				classie.toggle( menuLeft, 'cbp-spmenu-open' );
				disableOther( 'showLeftPush' );
			};
			

			function disableOther( button ) {
				if( button !== 'showLeftPush' ) {
					classie.toggle( showLeftPush, 'disabled' );
				}
			}
		</script>
	<!-- Bootstrap Core JavaScript --> 
		
        <script type="text/javascript" src="js/bootstrap.min.js"></script>

        <script type="text/javascript" src="js/dev-loaders.js"></script>
        <script type="text/javascript" src="js/dev-layout-default.js"></script>
		<script type="text/javascript" src="js/jquery.marquee.js"></script>
		<link href="css/bootstrap.min.css" rel="stylesheet">

		<script type="text/javascript" src="js/jquery.jqcandlestick.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/jqcandlestick.css" />
		
		<!--max-plugin-->
		<script type="text/javascript" src="js/plugins.js"></script>
		<!--//max-plugin-->
		
		<!--scrolling js-->
		<script src="js/jquery.nicescroll.js"></script>
		<script src="js/scripts.js"></script>
		<!--//scrolling js-->
</body>
</html>