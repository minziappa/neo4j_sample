<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>

</head>
<body>

<h1>Neo4J Test</h1>
<br>
	<font color="#ff0000">${errorMessage?if_exists}</font>
	<div>
		<dl>
			<dt><label for="nodeValue">Value Nmae</label></dt>
		</dl>
		<dl>
			<dt><label for="nodeValue">${model.value?if_exists}</label></dt>
		</dl>
	</div>

	Get data from the Graph Db.
	</br>
	<a href="index.neo" > Return to index </a>

</body>
</html>