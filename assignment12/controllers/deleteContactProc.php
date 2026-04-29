<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

echo "Script started<br>";

require_once(__DIR__ . '/../classes/Pdo_methods.php');
echo "Pdo_methods loaded<br>";

$pdo = new PdoMethods();

$msg = "<p>&nbsp;</p>";
$output = "";
$deleted = false;

if ($_SERVER['REQUEST_METHOD'] === 'POST' && !empty($_POST['delete'])) {
    echo "POST request<br>";
    $ids = $_POST['delete'];

    if (is_array($ids) && count($ids) > 0) {
        $placeholders = implode(',', array_fill(0, count($ids), '?'));
        $sql = "DELETE FROM contacts WHERE id IN ($placeholders)";
        $result = $pdo->otherBinded($sql, $ids);

        if ($result === 'error') {
            $msg = "<p style='color:red;'>Could not delete the contacts.</p>";
        } else {
            $msg = "<p style='color:green;'>Contact(s) deleted.</p>";
        }
    } else {
        $msg = "No contacts selected.";
    }

    if (session_status() === PHP_SESSION_NONE) {
        session_start();
    }
    $_SESSION['delete_message'] = $msg;
    header("Location: ../index.php?page=deleteContacts");
    exit();
} else {
    echo "Not a POST request.<br>";
    header("Location: ../index.php?page=deleteContacts");
    exit();
}

// Fetch records for table display
$sql = "SELECT * FROM contacts";
$records = $pdo->selectNotBinded($sql);
?>
