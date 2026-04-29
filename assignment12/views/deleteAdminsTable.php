<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<div class="container mt-4">
    <h1 class="mb-4">Delete Admin(s)</h1>

    <?php
    // Show message if set
    if (isset($_SESSION['delete_message'])) {
        echo '<div class="alert alert-info">' . htmlspecialchars($_SESSION['delete_message']) . '</div>';
        unset($_SESSION['delete_message']);
    }

    // Fetch admins from the database
    require_once(__DIR__ . '/../classes/Pdo_methods.php');
    $pdo = new PdoMethods();
    $sql = "SELECT * FROM admins";
    $admins = $pdo->selectNotBinded($sql);

    if ($admins === 'error' || empty($admins)) {
        echo '<div class="alert alert-info">No admins available to delete.</div>';
    } else {
        echo '<form method="post" action="controllers/deleteAdminProc.php">';
        echo '<button type="submit" class="btn btn-danger mb-3">Delete</button>';
        echo '<table class="table table-striped">';
        echo '<thead class="table-light">';
        echo '<tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Password</th>
            <th>Status</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>';
        foreach ($admins as $admin) {
            // Split name into first and last for display (if you store as one field, adjust accordingly)
            $names = explode(' ', $admin['name'], 2);
            $fname = $names[0];
            $lname = isset($names[1]) ? $names[1] : '';
            echo '<tr>';
            echo '<td>' . htmlspecialchars($fname) . '</td>';
            echo '<td>' . htmlspecialchars($lname) . '</td>';
            echo '<td>' . htmlspecialchars($admin['email']) . '</td>';
            echo '<td>' . htmlspecialchars($admin['password']) . '</td>';
            echo '<td>' . htmlspecialchars($admin['status']) . '</td>';
            echo '<td><input type="checkbox" name="delete[]" value="' . $admin['id'] . '"></td>';
            echo '</tr>';
        }
        echo '</tbody></table>';
        echo '</form>';
    }
    ?>
</div>
