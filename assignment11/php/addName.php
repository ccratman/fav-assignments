<?php
include '../classes/Db_conn.php';

header('Content-Type: application/json');

// Get the raw POST data
$data = json_decode(file_get_contents("php://input"));

if (!isset($data->name) || empty(trim($data->name))) {
    echo json_encode([
        'masterstatus' => 'error',
        'msg' => 'Name is required'
    ]);
    exit;
}

// Clean and split the name input
$fullName = trim($data->name);
$nameParts = explode(' ', $fullName, 2); // limit to 2 parts

if (count($nameParts) < 2) {
    echo json_encode([
        'masterstatus' => 'error',
        'msg' => 'Please enter both first and last name'
    ]);
    exit;
}

$firstName = trim($nameParts[0]);
$lastName = trim($nameParts[1]);

try {
    $db = new DatabaseConn();
    $conn = $db->dbOpen();

    $sql = "INSERT INTO names (first_name, last_name) VALUES (:first_name, :last_name)";
    $stmt = $conn->prepare($sql);
    $stmt->bindParam(':first_name', $firstName);
    $stmt->bindParam(':last_name', $lastName);
    $stmt->execute();

    echo json_encode([
        'masterstatus' => 'success',
        'msg' => 'Name added: ' . htmlspecialchars($lastName . ', ' . $firstName)
    ]);
} catch (PDOException $e) {
    echo json_encode([
        'masterstatus' => 'error',
        'msg' => 'Error adding name: ' . $e->getMessage()
    ]);
}
?>
