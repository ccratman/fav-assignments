<?php
// controllers/addAdminProc.php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

require_once(__DIR__ . '/../classes/Pdo_methods.php');

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $fname = trim($_POST['fname'] ?? '');
    $lname = trim($_POST['lname'] ?? '');
    $email = trim($_POST['email'] ?? '');
    $password = $_POST['password'] ?? '';
    $status = $_POST['status'] ?? '';

    if ($fname && $lname && $email && $password && $status) {
        $pdo = new PdoMethods();

        // Check for duplicate email
        $sql = "SELECT id FROM admins WHERE email = ?";
        $result = $pdo->selectBinded($sql, [$email]);
        if ($result !== 'error' && is_array($result) && count($result) > 0) {
            $msg = "An admin with that email already exists.";
        } else {
            try {
                $hash = password_hash($password, PASSWORD_DEFAULT);
                $name = $fname . ' ' . $lname;
                $sql = "INSERT INTO admins (name, email, password, status) VALUES (:name, :email, :password, :status)";
                $bindings = [
                    [':name', $name, 'str'],
                    [':email', $email, 'str'],
                    [':password', $hash, 'str'],
                    [':status', $status, 'str']
                ];
                $result = $pdo->otherBinded($sql, $bindings);
                if ($result === 'noerror') {
                    $msg = "Admin added successfully!";
                } else {
                    $msg = "There was an error adding the admin.";
                }
            } catch (Exception $e) {
                $msg = "Exception: " . $e->getMessage();
            }
        }
    } else {
        $msg = "All fields are required.";
    }

    // Redirect back to the form with a message
    header("Location: ../index.php?page=addAdmin&msg=" . urlencode($msg));
    exit();
} else {
    header("Location: ../index.php?page=addAdmin");
    exit();
}
?>
