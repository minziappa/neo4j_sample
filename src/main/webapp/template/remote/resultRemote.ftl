<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>

</head>
<body>

<h1>Neo4J Remote Test</h1>
<br>

	<font color="#ff0000">${errorMessage?if_exists}</font>
<br>
	<div>
		<dl>
			<dt><label for="nodeKey1">生成されたのどを「node -> 20」変更してアクセスが出来ます。</label></dt>
			<dt><label for="nodeKey2">http://172.28.237.26:7474/db/data/node/[node]/relationships/all</label></dt>
			<dt><label for="nodekey3">http://172.28.237.26:7474/db/data/node/[node]/relationships/out</label></dt>
		</dl>
	</div>
</body>
</html>