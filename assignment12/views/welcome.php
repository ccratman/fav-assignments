<?php
require_once(__DIR__ . '/../includes/navigation.php');
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Welcome</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="container mt-5">
  <h2>Welcome Page</h2>
  <p>Welcome <?= htmlspecialchars($_SESSION['name'] ?? 'Guest') ?></p>
</body>
</html>
