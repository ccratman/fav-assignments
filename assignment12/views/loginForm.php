<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="container mt-5">
  <h2>Login</h2>

  <?php
  if (isset($_GET['msg'])) {
    echo "<p style='color:red'>" . htmlspecialchars($_GET['msg']) . "</p>";
  }
  ?>

  <form method="post" action="/~ccratcliff/cps_276/assignments/assignment12/controllers/loginProc.php">
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" class="form-control" name="email" id="email"
        value="ccratcliff@admin.com" required />
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" class="form-control" name="password" id="password"
        value="password" required />
    </div>
    <button type="submit" class="btn btn-primary">Login</button>
  </form>
</body>
</html>
