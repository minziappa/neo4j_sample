<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>

</head>
<body>

<h1>Neo4J Locat Test</h1>
<br>
	<font color="#ff0000">${errorMessage?if_exists}</font>
	<div>
		<dl>
			<dt><label for="nodeKey">Key Name</label></dt>
			<dt><label for="nodeValue">Value Nmae</label></dt>
		</dl>
	<#if model.nodeList?has_content>
		<#list model.nodeList as node>
		<dl>
			<dt><label for="nodeKey">${node.nodeKey?if_exists}</label></dt>
			<dt><label for="nodeValue">${node.nodeValue?if_exists}</label></dt>
		</dl>
		</#list>
	</#if>
	</div>

</body>
</html>