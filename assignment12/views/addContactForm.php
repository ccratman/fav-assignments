<!-- views/addContactForm.php -->
<?php
require_once(__DIR__ . '/../includes/navigation.php');
$msg = $_GET['msg'] ?? '';
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Add Contact</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="container mt-5">
  <h2>Add Contact</h2>
  <?php if ($msg): ?>
    <div class="alert alert-info"><?= htmlspecialchars($msg) ?></div>
  <?php endif; ?>
  <form method="post" action="/~ccratcliff/cps_276/assignments/assignment12/controllers/addContactProc.php">
    <div class="form-row">
      <div class="form-group col-md-6">
        <label for="first_name">First Name</label>
        <input type="text" class="form-control" name="first_name" id="first_name" value="" required>
      </div>
      <div class="form-group col-md-6">
        <label for="last_name">Last Name</label>
        <input type="text" class="form-control" name="last_name" id="last_name" value="" required>
      </div>
    </div>
    <div class="form-group">
      <label for="address">Address</label>
      <input type="text" class="form-control" name="address" id="address" value="123 Anyplace" required>
    </div>
    <div class="form-row">
      <div class="form-group col-md-4">
        <label for="city">City</label>
        <input type="text" class="form-control" name="city" id="city" value="Somewhere" required>
      </div>
      <div class="form-group col-md-4">
        <label for="state">State</label>
        <input type="text" class="form-control" name="state" id="state" value="Michigan" required>
      </div>
      <div class="form-group col-md-4">
        <label for="zip">Zip Code</label>
        <input type="text" class="form-control" name="zip" id="zip" value="12345" required>
      </div>
    </div>
    <div class="form-row">
      <div class="form-group col-md-4">
        <label for="phone">Phone</label>
        <input type="text" class="form-control" name="phone" id="phone" value="999.999.9999" required>
      </div>
      <div class="form-group col-md-4">
        <label for="email">Email</label>
        <input type="email" class="form-control" name="email" id="email" value="" required>
      </div>
      <div class="form-group col-md-4">
        <label for="dob">Date of Birth</label>
        <input type="text" class="form-control" name="dob" id="dob" value="9/9/1999" required>
      </div>
    </div>
    <div class="form-group">
      <label>Choose an Age Range</label><br>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="radio" name="age" id="age1" value="0-17">
        <label class="form-check-label" for="age1">0-17</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="radio" name="age" id="age2" value="18-30">
        <label class="form-check-label" for="age2">18-30</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="radio" name="age" id="age3" value="30-50">
        <label class="form-check-label" for="age3">30-50</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="radio" name="age" id="age4" value="50+">
        <label class="form-check-label" for="age4">50+</label>
      </div>
    </div>
    <div class="form-group">
      <label>Select One or More Options</label><br>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" name="contacts[]" id="newsletter" value="newsletter">
        <label class="form-check-label" for="newsletter">newsletter</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" name="contacts[]" id="email_opt" value="email">
        <label class="form-check-label" for="email_opt">email</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" name="contacts[]" id="text" value="text">
        <label class="form-check-label" for="text">text</label>
      </div>
    </div>
    <button type="submit" class="btn btn-primary">Add Contact</button>
  </form>
</body>
</html>
