<%@page autoFlush="false" buffer="8kb" trimDirectiveWhitespaces="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rcfmedia.net/taglib/excel.tld" prefix="xls"%>

<%
	pageContext.setAttribute("borderDefs", new String[] { "top", "bottom", "right", "left", "all" });
	pageContext.setAttribute("borderTypes", new String[] { "default", "thin", "medium", "thick", "dash", "hair", "none" });

	pageContext.setAttribute("alignDefs", new String[] { "center", "left", "right" });
	pageContext.setAttribute("valignDefs", new String[] { "center", "top", "bottom" });
%>

<xls:workbook filename="ExcelTest.xls">
	<xls:sheet name="Row border">
		<xls:row/>
		<c:forEach items="${borderDefs}" var="borderDef">
			<c:forEach items="${borderTypes}" var="borderType">
				<xls:row border="${borderDef}:${borderType}">
					<xls:cell width="20" border="all:default"/>
					<xls:cell width="auto">xls:row border="${borderDef}:${borderType}"</xls:cell>
				</xls:row>
				<xls:row/>
			</c:forEach>
			<xls:row border="all:default"/>
		</c:forEach>
	</xls:sheet>

	<xls:sheet name="Row Height">
		<xls:row/>
			<c:forEach begin="0" end="210" step="10" var="height">
				<xls:row height="${height}">
					<xls:cell width="15"/>
					<xls:cell width="auto">height=${height}</xls:cell>
				</xls:row>
			</c:forEach>
	</xls:sheet>

	<xls:sheet name="Cell border">
		<xls:row/>
		<c:forEach items="${borderDefs}" var="borderDef">
			<xls:row>
				<c:forEach items="${borderTypes}" var="borderType">
					<xls:cell width="15"/>
					<xls:cell width="auto" border="${borderDef}:${borderType}">xls:row border="${borderDef}:${borderType}"</xls:cell>
				</c:forEach>
			</xls:row>
			<xls:row/>
		</c:forEach>
	</xls:sheet>

	<xls:sheet name="Cell Width">
		<xls:row/>
		<xls:row>
			<xls:cell width="15"/>
			<c:forEach begin="10" end="660" step="10" var="width">
				<xls:cell width="${width}">width=${width}</xls:cell>
			</c:forEach>
		</xls:row>
	</xls:sheet>

	<xls:sheet name="Cell Alignement">
		<xls:row/>
		<c:forEach items="${alignDefs}" var="align">
			<xls:row height="70">
				<xls:cell width="15"/>
				<c:forEach items="${valignDefs}" var="valign">
					<xls:cell width="300" align="${align}" valign="${valign}">
						[align=${align}][valign=${valign}]
					</xls:cell>
				</c:forEach>
			</xls:row>
		</c:forEach>
	</xls:sheet>

</xls:workbook>