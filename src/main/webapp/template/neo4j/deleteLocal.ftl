<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>

</head>
<body>

<h1>Neo4J GetGraphLocal</h1>
<br>

	<font color="#ff0000">${errorMessage?if_exists}</font>
	<form method="post" action="deleteGraphLocal.neo">
	<div>
		<dl>
			<dt><label for="nodeKey">Key Name</label></dt>
			<dd><input type="text" name="nodeKey" maxlength="12" value=""/></dd>
			<dt><label for="nodeValue">Value Nmae</label></dt>
			<dd><input type="text" name="nodeValue" maxlength="24" value=""/></dd>
		</dl>
	</div>
	<button type="submit">Click Me!</button>
	</form>

</body>
</html>