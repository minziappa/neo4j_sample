<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>

</head>
<body>

<h1>Neo4J setGraphLocal</h1>
<br>

	<font color="#ff0000">${errorMessage?if_exists}</font>
	<form method="post" action="setDataComplete.neo">
	<div>
		<dl>
			<dt><label for="nodeKey">First Key Name</label></dt>
			<dd><input type="text" name="firstNodeKey" maxlength="12" value=""/></dd>
			<dt><label for="nodeValue">First Value Name</label></dt>
			<dd><input type="text" name="firstNodeValue" maxlength="24" value=""/></dd>
		</dl>
	</div>
	<div>
		<dl>
			<dt><label for="nodeKey">Second Key Name</label></dt>
			<dd><input type="text" name="secondNodeKey" maxlength="12" value=""/></dd>
			<dt><label for="nodeValue">Second Vale Name</label></dt>
			<dd><input type="text" name="secondNodeValue" maxlength="24" value=""/></dd>
		</dl>
	</div>
	<div>
		<dl>
			<dt><label for="nodeKey">Relation Key Name</label></dt>
			<dd><input type="text" name="relationshopKey" maxlength="12" value=""/></dd>
			<dt><label for="nodeValue">Relation Vale Name</label></dt>
			<dd><input type="text" name="relationshopValue" maxlength="24" value=""/></dd>
		</dl>
	</div>
	<button type="submit">Click Me!</button>
	<input type="hidden" name="type" value="1"/>
	</form>

</body>
</html>