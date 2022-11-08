<%@page import="vo.QBoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
QBoardBean article = (QBoardBean)request.getAttribute("article");
    String loginID = (String)session.getAttribute("mem_id"); //세션에 있는 ID불러오기
%>

<%-- JSTL 사용을 위한 선언 부분 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.js"></script> 
<meta charset="UTF-8"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="style/initStyle.css">
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="style/header.css">
<link rel="stylesheet" type="text/css" href="style/body.css">
<link rel="stylesheet" type="text/css" href="style/footer.css">
<title>게시글 수정</title>
	<script type="text/javascript">
	function modifyboard(){
				
		modifyform.submit();
	}
	</script>
	<style type="text/css">
   #registForm{
      width: 500px;
      height: 600px;
      margin:auto; 
   }   
   h4{
      text-align: center; padding-top:20px ;
   }
   table{
      margin:auto;
      width: 600px;
      }
   #td_left{
      text-align: center;
   }
    
   #commandCell{
      text-align: center;
      
   }
   #table-first{ margin:0 auto; width: 60%; border-color: black;  font-size: 12px; border-color: #e1e4e1;}
</style>
</head>
<body>
<jsp:include page="../header.jsp" />
	 
	 <div id = "contentWrap">
	<!-- 게시판 등록 -->

<section id = "writeForm">

<form action="qboardModifyPro.qna" method="post" name = "modifyform">
<input type = "hidden" name = "QBOARD_NUM" value = "${article.QBOARD_NUM}"/>

<h4>게시판글수정</h4>
<table border="1" id="table-first" class="table table-bordered">
	<tr>
		<td id="td_left">
			<label for = "QBOARD_NAME">작성자 ID</label>
		</td>
		<td>
			 ${article.MEM_ID}
		</td>
	</tr>

	<tr>
		<td id="td_left">
			<label for = "QBOARD_SUBJECT" >제 목</label>
		</td>
		<td>
			<input name="QBOARD_SUBJECT" type="text" id = "QBOARD_SUBJECT" value = "${article.QBOARD_SUBJECT}"  class="form-control"/>
		</td>
	</tr>	
	<tr>
		<td id="td_left">
			<label for = "QBOARD_CONTENT">내 용</label>
		</td>
		<td>
			<textarea id = "QBOARD_CONTENT" name="QBOARD_CONTENT" cols="60" rows="12"  class="form-control" style="font-size:12px;">${article.QBOARD_CONTENT}</textarea>
		</td>
	</tr>

</table>
	<section id = "commandCell">
	         <%--로그인 한 사람의 본인의 작성글만 수정할 수 있는 조건문 --%>
	         <c:choose>
	         	<c:when test="${userID == article.MEM_ID }">
	         		<button type = "button" onclick = "modifyboard()" class="btn btn-outline-dark" style="font-size:12px;">수정</button>&nbsp;&nbsp;
	         	</c:when>
	         	<c:otherwise>
	         		<button type = "button" onclick="" class="btn btn-outline-dark" style="font-size:12px;" disabled>수정</button>&nbsp;&nbsp;
	         	</c:otherwise>
	         </c:choose>
			
			<a href="javascript:history.go(-1)" class="btn btn-outline-dark" style="font-size:12px;">뒤로</a>
	</section> 
</form>

</section>
</div>
<jsp:include page="../footer.jsp" />
</body>
</html>
