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
    <button type="submit">Добавить студента</button>
</form>
<?php
$mysqli = new mysqli("db", "user", "password", "appDB");

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name = $_POST["name"];
    $surname = $_POST["surname"];
    $age = $_POST["age"];
    $grade = $_POST["grade"];

    $insertQuery = "INSERT INTO students (name, surname, age, grade) VALUES ('$name', '$surname', '$age', '$grade')";
    if ($mysqli->query($insertQuery)) {
        exit();
    } else {
    }
}


?>
</table>
</body>
</html>