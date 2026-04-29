<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

function checkLogin() {
    if (!isset($_SESSION['access']) || $_SESSION['access'] !== 'granted') {
        header("Location: index.php?page=loginForm");
        exit();
    }
}
?>
