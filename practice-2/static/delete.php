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

    <button type="submit">Удалить студента</button>
</form>
<?php
$mysqli = new mysqli("db", "user", "password", "appDB");

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id = $_POST["id"];
    $deleteQuery = "DELETE FROM students WHERE ID='$id'";
    if ($mysqli->query($deleteQuery)) {
        exit();
    } else {
    }
}



?>
</table>
</body>
</html>