<%@page autoFlush="false" buffer="8kb" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://rcfmedia.net/taglib/excel.tld" prefix="xls"%>

<xls:workbook filename="simpleExcel.xls">
	<xls:sheet name="First Sheet">
		<xls:row>
			<xls:cell>Hello, World !</xls:cell>
		</xls:row>
	</xls:sheet>
</xls:workbook>
