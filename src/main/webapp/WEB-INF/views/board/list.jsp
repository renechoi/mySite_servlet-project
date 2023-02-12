<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <link href="/assets/css/board.css" rel="stylesheet" type="text/css">
    <script defer src='/assets/js/board.js'></script>
    <title>Mysite</title>
</head>
<body>
<div id="container">

    <c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
    <c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>

    <div id="content">
        <div id="board">
            <form id="search_form" action="/board?a=search" method="post">
                <div class="search-type">
                    <label for="search-type" hidden>검색 유형</label>
                    <select class="form-control" id="search-type" name="searchType">
                        <option value="name">작성자</option>
                        <option value="reg_date">작성일시</option>
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                        <option value="file_name">파일이름</option>
                    </select>
                </div>
                <div class="search-value">

                <label for="search-value" hidden>검색어</label>
                <input placeholder="검색어..." type="text" id="kwd" name="searchValue" value="">
                <input type="submit" value="찾기">
                </div>




            </form>
            <table class="tbl-ex">
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>글쓴이</th>
                    <th>조회수</th>
                    <th>작성일</th>
                    <th>&nbsp;</th>
                </tr>
                <c:forEach items="${list }" var="vo">
                    <tr>
                        <td>${vo.no }</td>
                        <td><a href="/board?a=read&no=${vo.no }">${vo.title }</a></td>
                        <td>${vo.userName }</td>
                        <td>${vo.hit }</td>
                        <td>${vo.regDate }</td>
                        <td>
                            <c:if test="${authUser.no == vo.userNo }">
                                <a href="/board?a=delete&no=${vo.no }" class="del">삭제</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <div class="pager">
                <ul>
                    <li style="cursor:hand"><a onclick="pageMove(${pagination.pageCount},${pagination.currentPage-1})" >◀</a></li>
                    <c:forEach var="i" begin="1" end="${pagination.pageCount}">
                        <c:choose>
                            <c:when test="${pagination.currentPage == i}">
                                <li class="selected"><a href="/board?a=list&page=${i}">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="/board?a=list&page=${i}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <li style="cursor:hand"><a onclick="pageMove(${pagination.pageCount},${pagination.currentPage+1})" >▶</a></li>
                </ul>
            </div>
            <c:if test="${authUser != null }">
                <div class="bottom">
                    <a href="/board?a=writeform" id="new-book">글쓰기</a>
                </div>
            </c:if>
        </div>
    </div>

    <c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>

</div><!-- /container -->
</body>
</html>		
		
