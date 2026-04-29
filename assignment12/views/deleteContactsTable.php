<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<?php
require_once(__DIR__ . '/../includes/navigation.php');
require_once(__DIR__ . '/../classes/Pdo_methods.php');
$pdo = new PdoMethods();
$sql = "SELECT * FROM contacts ORDER BY lname, fname";
$contacts = $pdo->selectNotBinded($sql);
$msg = $_GET['msg'] ?? '';
if (isset($_SESSION['delete_message'])) {
    echo '<div class="alert alert-info">' . htmlspecialchars($_SESSION['delete_message']) . '</div>';
    unset($_SESSION['delete_message']);
}
?>

<div class="container mt-4">
    <h1 class="mb-4">Delete Contact(s)</h1>

    <?php if ($msg): ?>
        <div class="alert alert-info"><?= htmlentities($msg) ?></div>
    <?php endif; ?>

    <?php if (is_array($contacts) && count($contacts) > 0): ?>
        <form method="post" action="controllers/deleteContactProc.php">
            <button type="submit" class="btn btn-danger mb-3">Delete</button>
            <table class="table table-striped">
                <thead class="table-light">
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Address</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th>DOB</th>
                        <th>Contact</th>
                        <th>Age</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($contacts as $contact): ?>
                        <tr>
                            <td><?= htmlspecialchars($contact['fname']) ?></td>
                            <td><?= htmlspecialchars($contact['lname']) ?></td>
                            <td><?= htmlspecialchars($contact['address']) ?></td>
                            <td><?= htmlspecialchars($contact['city']) ?></td>
                            <td><?= htmlspecialchars($contact['state']) ?></td>
                            <td><?= htmlspecialchars($contact['phone']) ?></td>
                            <td><?= htmlspecialchars($contact['email']) ?></td>
                            <td><?= htmlspecialchars($contact['dob']) ?></td>
                            <td><?= htmlspecialchars($contact['contacts']) ?></td>
                            <td><?= htmlspecialchars($contact['age']) ?></td>
                            <td><input type="checkbox" name="delete[]" value="<?= $contact['id'] ?>"></td>
                        </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
        </form>
    <?php else: ?>
        <div class="alert alert-info">No contacts available to delete.</div>
    <?php endif; ?>
</div>
