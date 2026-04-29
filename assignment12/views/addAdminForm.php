<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
require_once('includes/navigation.php');
$msg = $_GET['msg'] ?? '';
echo "addAdminForm loaded<br>";
if (isset($_SESSION['add_admin_message'])) {
    echo '<div class="alert alert-info">' . htmlspecialchars($_SESSION['add_admin_message']) . '</div>';
    unset($_SESSION['add_admin_message']);
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Add Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
  <h1 class="mb-4">Add Admin</h1>
  <?php if ($msg): ?>
    <div class="alert alert-info"><?= htmlspecialchars($msg) ?></div>
  <?php endif; ?>
  <form method="post" action="/~ccratcliff/cps_276/assignments/assignment12/controllers/addAdminProc.php">
    <div class="row mb-3">
      <div class="col-md-6">
        <label for="fname" class="form-label">First Name</label>
        <input type="text" class="form-control" id="fname" name="fname" required>
      </div>
      <div class="col-md-6">
        <label for="lname" class="form-label">Last Name</label>
        <input type="text" class="form-control" id="lname" name="lname" required>
      </div>
    </div>
    <div class="row mb-3">
      <div class="col-md-4">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email" name="email" required>
      </div>
      <div class="col-md-4">
        <label for="password" class="form-label">Password</label>
        <input type="text" class="form-control" id="password" name="password" placeholder="Password" required>
      </div>
      <div class="col-md-4">
        <label for="status" class="form-label">Status</label>
        <select class="form-select" id="status" name="status" required>
          <option value="">Please Select a Status</option>
          <option value="admin">Admin</option>
          <option value="staff">Staff</option>
        </select>
      </div>
    </div>
    <button type="submit" class="btn btn-primary">Add Admin</button>
  </form>
</body>
</html>
