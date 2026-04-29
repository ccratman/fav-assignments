<?php
include '../classes/Db_conn.php';

header('Content-Type: application/json');

try {
    // Connect to the database
    $db = new DatabaseConn();
    $conn = $db->dbOpen();

    // Delete all names from the table
    $sql = "DELETE FROM names";
    $stmt = $conn->prepare($sql);
    $stmt->execute();

    // Return success response
    echo json_encode([
        'masterstatus' => 'success',
        'msg' => 'All names cleared!'
    ]);
} catch (PDOException $e) {
    // Return error response
    echo json_encode([
        'masterstatus' => 'error',
        'msg' => 'Error clearing names: ' . $e->getMessage()
    ]);
}
?>
