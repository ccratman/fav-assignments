<?php
include '../classes/Db_conn.php';

header('Content-Type: application/json');

try {
    $db = new DatabaseConn();
    $conn = $db->dbOpen();

    $sql = "SELECT first_name, last_name FROM names ORDER BY last_name, first_name";
    $stmt = $conn->prepare($sql);
    $stmt->execute();

    $names = $stmt->fetchAll(PDO::FETCH_ASSOC);
    $nameList = '';

    if (count($names) > 0) {
        foreach ($names as $name) {
            $nameList .= '<p>' . htmlspecialchars($name['last_name']) . ', ' . htmlspecialchars($name['first_name']) . '</p>';
        }
    } else {
        $nameList = '<p>No names to display</p>';
    }

    echo json_encode([
        'masterstatus' => 'success',
        'names' => $nameList
    ]);
} catch (PDOException $e) {
    echo json_encode([
        'masterstatus' => 'error',
        'msg' => 'Error fetching names: ' . $e->getMessage()
    ]);
}
?>
