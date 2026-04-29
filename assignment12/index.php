<?php
// Show all errors (for development)
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// Set session name and cookie path for russet-v8 subdirectory hosting
session_name('ccratcliff_session');
session_set_cookie_params([
    'path' => '/~ccratcliff/',
]);
session_start(); // Only call this ONCE, here!

// TEMPORARY DEBUG: Set a test session variable
if (!isset($_SESSION['test'])) {
    $_SESSION['test'] = 'hello';
}

// Include the router to handle different pages
require_once('routes/router.php');
?>
