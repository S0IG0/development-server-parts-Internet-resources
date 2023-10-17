<html lang="en">
<head>
<title>Hello world page</title>
    <link rel="stylesheet" href="style.css" type="text/css"/>
</head>
<body>
<form
        method="post"
>
    <label>
        <input name="name" type="text" >
    </label>
    <label>
        <input name="surname" type="text" >
    </label>
    <label>
        <input name="age" type="number" >
    </label>
    <label>
        <input name="grade" type="number" >
    </label>
    <button type="submit">Добавить студента</button>
</form>

<h1>Таблица пользователей данного продукта</h1>
<table>
    <tr><th>Id</th><th>Name</th><th>Surname</th><th>Age</th><th>Grade</th></tr>
<?php
$mysqli = new mysqli("db", "user", "password", "appDB");
$result = $mysqli->query("SELECT * FROM students");
foreach ($result as $row){
    echo "<tr><td>{$row['ID']}</td><td>{$row['name']}</td><td>{$row['surname']}</td><td>{$row['age']}</td><td>{$row['grade']}</td></tr>";
}

?>
</table>
</body>
</html>