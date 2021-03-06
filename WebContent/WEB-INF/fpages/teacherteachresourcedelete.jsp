<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>教学资料删除</title>
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
                <div class="progressbar-heading grids-heading">
                    <h2>我上传的资料</h2>
                </div>
                <div class="panel panel-widget">
                    <div class="inbox-page">

                        <c:forEach items="${requestScope.trList }" var="tr">
                        <div class="inbox-row widget-shadow" id="accordion" role="tablist" aria-multiselectable="true">
                            <div class="mail"><img src="images/i1.png" alt=""></div>
                            <div class="mail mail-name"> <h6>${tr.user.name }</h6> </div>
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne${tr.pk_id }" aria-expanded="true" aria-controls="collapseOne${tr.pk_id }">
                                <div class="mail"><p>${tr.rname }</p></div>
                            </a>
                            <div class="mail-right">
                                <div class="dropdown">
                                    <a href="#" data-toggle="dropdown" aria-expanded="false">
                                        <p><i class="fa fa-ellipsis-v mail-icon"></i></p>
                                    </a>
                                    <ul class="dropdown-menu float-right">
                                        <li>
                                            <a role="button"  href="<c:url value='/tres.action!delete?tres_id=${tr.pk_id }'/>" >
                                                <i class="fa fa-cut nav_icon"></i>
                                                	删除
                                            </a>
                                        </li>

                                    </ul>
                                </div>
                            </div>
                            <div class="mail-right"><p><fmt:formatDate value="${tr.uploadtime }" pattern="yyyy-MM-dd HH:mm:ss"/></p></div>
                            <div class="clearfix"> </div>
                            <div id="collapseOne${tr.pk_id }" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                                <div class="mail-body">
                                    <p>${tr.descript }</p>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                        <c:choose>
	                        <c:when test="${requestScope.pagebean.totalpages eq 0}">
	                        	暂无资源
	                        </c:when>
	                        <c:otherwise>
	                        <div class="text-center">
	                            <nav>
	                                <ul class="pagination"> 
	                                    <li <c:if test="${requestScope.pagebean.isfirst}">class="disabled"</c:if> ><a href="<c:url value='/tres.action!toteachresource?pageindex=${requestScope.pagebean.preindex }'/>" aria-label="Previous"><i class="fa fa-angle-left"></i></a></li>
	                                    <c:forEach items="${requestScope.pagebean.pageList }" var="page"> 
	                                    	<li <c:if test="${requestScope.pagebean.pageindex eq page}">class="active"</c:if>><a href="<c:url value='/tres.action!toteachresource?pageindex=${page }'/>">${page }</a></li>
	                                    </c:forEach>
	                                    <li <c:if test="${requestScope.pagebean.islast}">class="disabled"</c:if>><a href="<c:url value='/tres.action!toteachresource?pageindex=${requestScope.pagebean.nextindex }'/>" aria-label="Next"><i class="fa fa-angle-right"></i></a></li>
	                                </ul>
	                            </nav>
	                        </div>
	                        </c:otherwise>
                        </c:choose>
                    </div>
                </div>

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