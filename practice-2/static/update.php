

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
        <input name="id" type="number" placeholder="id">
    </label>
    <label>
        <input name="name" type="text" placeholder="name">
    </label>
    <label>
        <input name="surname" type="text" placeholder="surname">
    </label>
    <label>
        <input name="age" type="number" placeholder="age">
    </label>
    <label>
        <input name="grade" type="number" placeholder="grade">
    </label>
    <button type="submit">Изменить студента</button>
</form>
<?php
$mysqli = new mysqli("db", "user", "password", "appDB");

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id = $_POST["id"];
    $name = $_POST["name"];
    $surname = $_POST["surname"];
    $age = $_POST["age"];
    $grade = $_POST["grade"];

    $updateQuery = "UPDATE students SET name='$name', surname='$surname', age='$age', grade='$grade' WHERE ID='$id'";
    if ($mysqli->query($updateQuery)) {
        exit();
    } else {
    }
}



?>
</table>
</body>
</html>