<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>		 		
</head>
<body>
	<table border="1">
	     <tr>
        		<th>流水号</th>
        		<th>车次</th>
        		<th>始发时间</th>
        		<th>座位等级</th>
        		<th>数量</th>
        		<th>查询时间</th>
        	</tr> 
		<s:iterator id="ticket" value="tickets">
        	<tr>
        		<td><s:property value="ticketId"/></td>
        		<td><s:property value="trainNo"/></td>        		
        		<td><s:property value="departureDate"/></td>
        		<td><s:property value="grade"/></td>
        		<td><s:property value="count"/></td>
        		<td><s:date name="insertTime" format="yyyy-MM-dd hh:mm:ss"/></td>
        	</tr>   
        </s:iterator>
     </table>
</body>
</html>