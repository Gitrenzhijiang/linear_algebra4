<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>回答问题</title>
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
    <!-- //chart -->
    <!-- js-->
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/modernizr.custom.js"></script>
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
    <link href="css/demo-page.css" rel="stylesheet" media="all">
    <link href="css/hover.css" rel="stylesheet" media="all">
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <!--left-fixed -navigation-->
    <div class="sidebar" role="navigation">
        <div class="navbar-collapse">
            <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right dev-page-sidebar mCustomScrollbar _mCS_1 mCS-autoHide mCS_no_scrollbar" id="cbp-spmenu-s1">
                <div class="scrollbar scrollbar1">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="<c:url value='/user.action!toindex'/>"><i class="fa fa-home nav_icon"></i>主页</a>
                        </li>
                        <c:if test="${sessionScope.user.role.pk_id eq 1 }">
                            <!--管理员start-->
                            <li>
                                <a href="<c:url value='/user.action!tosignupcheck'/>"><i class="fa fa-check-square-o nav_icon"></i>注册审批</a>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-key nav_icon"></i>权限管理<span class="fa arrow"></span></a>
                                <ul class="nav nav-second-level collapse">
                                    <li>
                                        <a href="<c:url value='/resource.action!toadd'/>">添加权限</a>
                                    </li>
                                    <li>
                                        <a href="<c:url value='/resource.action!toresource'/>">删除权限</a>
                                    </li>
                                </ul>
                            </li>
                            <!--管理员end-->
                        </c:if>
                        <!--问答-->
                        <li>
                            <a href="#"><i class="fa fa-comments-o nav_icon"></i>有问有答<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse">

                                <li>
                                    <a href="<c:url value='/question.action!toask'/>">提问</a>
                                </li>
                                <li>
                                    <a href="<c:url value='/user.action!toindex?user_id=${sessionScope.user.pk_id }'/>">我的提问</a>
                                </li>
                                
                            </ul>
                        </li>
                        <!--通知-->
                        <li>
                            <a href="#"><i class="fa fa-bell-o nav_icon"></i>所有通知<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse">
                                <li>
                                    <a href="<c:url value='/notice.action!toallnotice'/>">通知列表</a>
                                </li>
                                <c:if test="${sessionScope.user.role.pk_id eq 1 or sessionScope.user.role.pk_id eq 2}">
                                    <!--如果是教师权限，可以发布通知，管理自己发布的通知-->
                                    <li>
                                        <a href="<c:url value='/notice.action!toaddnotice'/>">发布通知</a>
                                    </li>
                                    <li>
                                        <a href="<c:url value='/notice.action!tomynotice'/>">我的通知</a>
                                    </li>
                                    <!--教师权限end-->
                                </c:if>
                            </ul>
                        </li>
                        <c:if test="${sessionScope.user.role.pk_id eq 3  or sessionScope.user.role.pk_id eq 2}">
                        <!--(作业)非管理员start-->
                        <li>
                            <a href="#"><i class="fa fa-tasks nav_icon"></i>作业详情<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse">
                                <c:if test="${sessionScope.user.role.pk_id eq 3 }">
                                <!--如果是学生权限-->
                                <li>
                                    <a href="<c:url value='/task.action!tomytask'/>">我的作业</a>
                                </li>
                                <!--学生权限结束-->
                                </c:if>
                                <c:if test="${sessionScope.user.role.pk_id eq 2 }">
                                <!--如果是教师权限-->
                                <li>
                                    <a href="<c:url value='/task.action!toadd'/>">发布作业</a>
                                </li>
                                <li>
                                    <a href="<c:url value='/task.action!tolist2finish'/>">提交进度</a>
                                </li>
                                <!--教师权限结束-->
                                </c:if>
                            </ul>
                        </li>
                        <!--非管理员end-->
                        </c:if>
                        <!--算法-->
                        <li>
                            <a href="#"><i class="fa fa-coffee nav_icon"></i>我的算法<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse">
                                <li>
                                    <a href="<c:url value='/agorithm.action!toadd'/>">新增算法</a>
                                </li>
                                <li>
                                    <a href="<c:url value='agorithm.action!tomyago'/>">算法集</a>
                                </li>
                            </ul>
                        </li>
                        <!--邮件-->
                        <li>
                            <a href="#"><i class="fa fa-envelope  nav_icon"></i>我的邮件<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse">
                                <li>
                                    <a href="<c:url value='/mail.action!toadd'/>">写邮件</a>
                                </li>
                                <li>
                                    <a href="<c:url value='/mail.action!tomail'/>">收件箱</a>
                                </li>
                            </ul>
                        </li>
                        <!--教学资料-->
                        <li>
                            <a href="#"><i class="fa fa-download nav_icon"></i>教学资料<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse">

                                <li>
                                    <a href="<c:url value='/tres.action!toteachresource'/>">下载资料</a>
                                </li>
                                <c:if test="${sessionScope.user.role.pk_id eq 1 or sessionScope.user.role.pk_id eq 2}">
                                <!--如果是教师权限-->
                                <li>
                                    <a href="<c:url value='/tres.action!toupload'/>">上传资料</a>
                                </li>
                                <li>
                                    <a href="<c:url value='/tres.action!todelete'/>">资料管理</a>
                                </li>
                                <!--教师权限结束-->
                                </c:if>
                            </ul>
                        </li>
                        <!--个人信息-->
                        <li>
                            <a href="<c:url value='/user.action!touserinfo'/>"><i class="fa fa-cogs nav_icon"></i>个人信息</a>
                        </li>
                        <!--线性代数运算-->
                        <li>
                            <a href="#"><i class="fa fa-forward nav_icon"></i>在线运算<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse">
                                <li>
                                    <a href="<c:url value='/ojs.action!tohlsqz'/>">行列式求值</a>
                                </li>
                                <li>
                                    <a href="<c:url value='/ojs.action!tojuzjiachen'/>">矩阵加乘</a>
                                </li>
                                <li>
                                    <a href="<c:url value='/ojs.action!tonizzz'/>">逆矩阵和秩</a>
                                </li>
                                <li>
                                    <a href="<c:url value='/ojs.action!tojzxxtzz'/>">特征值与线性相关</a>
                                </li>
                                <li>
                                    <a href="<c:url value='/ojs.action!tojzxs2'/>">矩阵相似</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
                <!-- //sidebar-collapse -->
            </nav>
        </div>
    </div>
    <!--left-fixed -navigation-->
    <!-- header-starts -->
    <div class="sticky-header header-section ">
        <div class="header-left">
            <!--logo -->
            <div class="logo">
                <a href="<c:url value='/user.action!toindex'/>">
                    <ul>
                        <li><img src="images/logo1.png" alt="" /></li>
                        <li><h1>线性代数</h1></li>
                        <div class="clearfix"> </div>
                    </ul>
                </a>
            </div>
            <!--//logo-->
            <div class="header-right header-right-grid">
                <div class="profile_details_left"><!--notifications of menu start -->
                    <ul class="nofitications-dropdown">
                        <li class="dropdown head-dpdn">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i><span class="badge">${requestScope.totalunreadinboxnum }</span></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <div class="notification_header">
                                        <h3>您有${requestScope.totalunreadinboxnum }封未读邮件</h3>
                                    </div>
                                </li>
                                <c:forEach items="${requestScope.threeInboxs }" var="inbox">
                                    <li><a href="<c:url value='/mail.action!tomail?isread=0'/>">
                                        <div class="user_img"><img src="${inbox.userBySendId.headpic}" alt=""></div>
                                        <div class="notification_desc">
                                            <p>${inbox.subject }</p>
                                            <p><span><fmt:formatDate value="${inbox.sendtime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
                                        </div>
                                        <div class="clearfix"></div>
                                    </a></li>
                                </c:forEach>
                                
                                <li>
                                    <div class="notification_bottom">
                                        <a href="<c:url value='/mail.action!tomail'/>">查看所有</a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown head-dpdn">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-bell"></i><span class="badge blue">${requestScope.totalunreadnoticenum }</span></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <div class="notification_header">
                                        <h3>您有${requestScope.totalunreadnoticenum }未读通知</h3>
                                    </div>
                                </li>
                                <c:forEach items="${requestScope.threeNotices }" var="notice">
                                <li><a href="<c:url value='/notice.action!toallnotice'/>">
                                    <div class="user_img"><img src="${notice.user.headpic}" alt=""></div>
                                    <div class="notification_desc">
                                        <p>${notice.ntitle }</p>
                                        <p><span><fmt:formatDate value="${notice.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
                                    </div>
                                    <div class="clearfix"></div>
                                </a></li>
                                </c:forEach>
                                <li>
                                    <div class="notification_bottom">
                                        <a href="<c:url value='/notice.action!toallnotice'/>">查看所有</a>
                                    </div>
                                </li>
                            </ul>
                        </li> 
                        <c:if test="${sessionScope.user.role.pk_id eq 3 }">
                        <!--只有学生才有任务-->
                        <li class="dropdown head-dpdn">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-tasks"></i><span class="badge blue1">${requestScope.totalunfinishtasknum }</span></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <div class="notification_header">
                                        <h3>您当前有${requestScope.totalunfinishtasknum }个正在进行的任务</h3>
                                    </div>
                                </li>
                                <c:forEach items="${requestScope.threeTasks }" var="task">
                                <li><a href="<c:url value='/task.action!taskdesc?task_id=${task.pk_id }'/>">
                                    <div class="task-info">
                                        <span class="task-desc">${task.name }</span><span class="percentage">0%</span>
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="progress progress-striped active">
                                        <div class="bar yellow" style="width:0%;"></div>
                                    </div>
                                </a></li>
                                </c:forEach>

                                <li>
                                    <div class="notification_bottom">
                                        <a href="<c:url value='/task.action!tomytask'/>">查看所有任务</a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <!--学生-->
                        </c:if>
                    </ul>
                    <div class="clearfix"> </div>
                </div>
            </div>


            <div class="clearfix"> </div>
        </div>
        <!--search-box-->
        <div class="search-box">
            <form class="input">
                <input class="sb-search-input input__field--madoka" placeholder="查询..." type="search" id="input-31" />
            </form>
        </div>
        <!--//end-search-box-->
        <div class="header-right">

            <!--notification menu end -->
            <div class="profile_details">
                <ul>
                    <li class="dropdown profile_details_drop">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            <div class="profile_img">
                                <span class="prfil-img"><img src="${sessionScope.user.headpic }" alt=""> </span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                        <ul class="dropdown-menu drp-mnu">
                            <li> <a href="<c:url value='/user.action!touserinfo'/>"><i class="fa fa-cog"></i> 设置</a> </li>
                            <li> <a href="<c:url value='agorithm.action!tomyago'/>"><i class="fa fa-user"></i> 算法</a> </li>
                            <li> <a href="<c:url value='/user.action!logout'/>"><i class="fa fa-sign-out"></i> 登出</a> </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!--toggle button start-->
            <button id="showLeftPush"><i class="fa fa-bars"></i></button>
            <!--toggle button end-->
            <div class="clearfix"> </div>
        </div>
        <div class="clearfix"> </div>
    </div>
    <!-- //header-ends -->
    <!-- main content start-->
    <div id="page-wrapper">
        <div class="main-page">
            <div class="grids">

                <!--问题标题-->
                <div class="page-header">
                    <div class="text-center"><h2>title</h2></div>
                    <div class="text-center text-muted">提问：${requestScope.problem.user.name } &nbsp;时间：<fmt:formatDate value="${requestScope.problem.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp; 
                    		<c:choose>
                    			<c:when test="${requestScope.problem.issolved eq 0}">未解决</c:when>
                    			<c:otherwise>已解决</c:otherwise>
                    		</c:choose> &nbsp;&nbsp;
                    	<c:choose>
                    			<c:when test="${requestScope.problem.level eq 0 }">简单</c:when>
                    			<c:when test="${requestScope.problem.level eq 1 }">中等</c:when>
                    			<c:when test="${requestScope.problem.level eq 2 }">难</c:when>
                    			<c:when test="${requestScope.problem.level eq 3 }">困难</c:when>
                    		</c:choose>
                    </div>
                </div>
                 <!--问题描述 + 图片-->
                <div class="jumbotron">
                    <div class="row">
                        <div class="text-center col-md-offset-1 col-md-10">
                           ${requestScope.problem.descript }
                        </div>
                    </div>
                    <div class="row">
                        <div class="text-center col-md-offset-1 col-md-10">
                        	<c:if test="${not empty requestScope.problem.qpicpath }">
	                            <hr/>
	                                <div class="row">
	                                	 <div class="col-sm-9 col-md-12">
									        <a href="<c:url value='${requestScope.problem.qpicpath }'/>" class="thumbnail" target="_blank">
									            <img src="${requestScope.problem.qpicpath }"
									                 alt="无图片">
									        </a>
									    </div>
									</div>
	                            <hr/>
                            </c:if>
                        </div>
                    </div>
                </div>

                <!--输入表单-->
                <div class="panel panel-widget">
                    <div class="compose-right widget-shadow">
                        <div class="panel-default">
                            <div class="panel-heading">
                                我的回答
                            </div>
                            <div class="panel-body">
                                <div class="alert alert-info">
                                   <c:choose>
                                   	<c:when test="${not empty requestScope.form_err }">${requestScope.form_err }</c:when>
                                   	<c:otherwise>请认真填写答案</c:otherwise>
                                   </c:choose>
                                </div>
                                <form class="com-mail" action="<c:url value='/answer.action!answer'/>" method="post" enctype="multipart/form-data">
									<input type="hidden" name="problem_id" value="${requestScope.problem.pk_id }"/>
                                    <textarea rows="6" name="answertext" value="${requestScope.answer.answertext }" class="form-control1 control2" placeholder="answer text..." required></textarea>
                                    <div class="form-group">
                                        <div class="btn btn-default btn-file">
                                            <i class="fa fa-paperclip"></i> 附加图片(可选)
                                            <input type="file" name="apicpath" accept=".jpg,.png,.bmp,.jpeg,.gif">
                                        </div>
                                        <p class="help-block">Max. 1MB</p>
                                    </div>
                                    <input type="submit" value="回答">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!--输入表单结束-->

            </div>
        </div>
    </div>
    <!--footer-->
    <div class="dev-page">

        <!-- page footer -->
        <!-- dev-page-footer-closed dev-page-footer-fixed -->
        <div class="dev-page-footer dev-page-footer-fixed ">
            <div class="container">
                <div class="copyright">
                    <p>Copyright &copy; 2018 &copy; 更多链接: <a href="http://www.jxau.edu.cn">江西农业大学官网</a> <a href="http://www.baidu.com">百度一下</a> FLYFISH
                    </p>
                </div>
            </div>
        </div>
        <!-- /page footer -->
    </div>
    <!--//footer-->
</div>
<!--论坛-->
<script type="text/javascript">
    //讨论列表的控制
    var kzps  = $('.kzpllist');

    kzps.click(function(){
        //找到它之前的所有兄弟 li
        var ul = $(this).parents('ul');
        var lis = $('li', ul);
        var kztext = $(this).text();
        lis.each(function (){
            if(this != lis.first().get(0) && this != lis.last().get(0)){
                if(kztext == '打开全部'){
                    $(this).show('fast');
                }else{
                    $(this).hide('fast');
                }
            }
        });
        if(kztext == '打开全部'){
            $(this).text('点击收起');
        }else{
            $(this).text('打开全部');
        }
    });
    //发言div显示与隐藏
    $('.iyfy').click(function () {
        var senddiv  = $(this).parents('.panel').next(".senddiv");
        var iyfytext = $(this).text();
        if(iyfytext == '我要发言'){
            senddiv.show('fast');
            $(this).text('收起发言');
        }else{
            senddiv.hide('fast');
            $(this).text('我要发言');
        }
    });
    //发言按钮
    $('.hvr-reveal').click(function () {
        var senddiv = $(this).parents('.senddiv');
        var text = $('input', senddiv).val();

        var indexli = $('li', senddiv.prev()).last();

        if(text != '' && text != undefined){
            $('input', senddiv).val('');
            //在<ul>内添加一个li
            var cloneli = $('li',$('#clonenodeparent'));
            var newNode = cloneli.get(0).cloneNode(true);
            var kzpllist = $('.kzpllist', indexli);//打开全部/xxx
            var currsize = $('li', senddiv.prev()).size();
            if(currsize <= 1 || kzpllist.html()!='打开全部'){
                $(newNode).css('display','block');
            }
            var divs = $('div',$(newNode));
            $(divs[0]).html('ren');
            $(divs[1]).html('2016-12-12 23:23:23');
            $(divs[2]).html(text);
            $(newNode).insertBefore(indexli);
            //发送到服务端
            //...
        }

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