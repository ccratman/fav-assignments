<?php
session_name('ccratcliff_session');
session_set_cookie_params([
    'path' => '/~ccratcliff/',
]);
session_start();

require_once(__DIR__ . '/../classes/Db_conn.php');
require_once(__DIR__ . '/../classes/Pdo_methods.php');

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $pdo = new PdoMethods();
    $sql = "SELECT * FROM admins WHERE email = :email";
    $bindings = [
        [':email', $_POST['email'], 'str']
    ];
    
    $records = $pdo->selectBinded($sql, $bindings);

    if ($records === 'error') {
        header("Location: ../index.php?page=loginForm&msg=There was an error logging in.");
        exit();
    } elseif (count($records) != 1) {
        header("Location: ../index.php?page=loginForm&msg=Invalid login.");
        exit();
    } else {
        $user = $records[0];
        if (password_verify($_POST['password'], $user['password'])) {
            $_SESSION['access'] = 'granted';
            $_SESSION['name'] = $user['name'];
            $_SESSION['status'] = $user['status'];
            header("Location: ../index.php?page=welcome");
            exit();
        } else {
            header("Location: ../index.php?page=loginForm&msg=Invalid login.");
            exit();
        }
    }
} else {
    header("Location: ../index.php?page=loginForm&msg=No POST data received.");
    exit();
}
?>
