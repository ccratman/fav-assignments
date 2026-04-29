<?php
// controllers/deleteAdminProc.php

require_once(__DIR__ . '/../classes/Pdo_methods.php');

if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

if ($_SERVER['REQUEST_METHOD'] === 'POST' && !empty($_POST['delete'])) {
    $pdo = new PdoMethods();
    $ids = $_POST['delete'];

    if (is_array($ids) && count($ids) > 0) {
        // Build the placeholders and bindings
        $placeholders = [];
        $bindings = [];
        foreach ($ids as $i => $id) {
            $ph = ":id$i";
            $placeholders[] = $ph;
            $bindings[] = [$ph, $id, 'int'];
        }
        $sql = "DELETE FROM admins WHERE id IN (" . implode(',', $placeholders) . ")";
        $result = $pdo->otherBinded($sql, $bindings);

        if ($result === 'noerror') {
            $message = "Admin(s) deleted.";
        } else {
            $message = "Could not delete the admins.";
        }
    } else {
        $message = "No admins selected.";
    }

    $_SESSION['delete_message'] = $message;
    header("Location: ../index.php?page=deleteAdmins");
    exit();
} else {
    header("Location: ../index.php?page=deleteAdmins");
    exit();
}
?>
