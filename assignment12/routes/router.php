<?php
// Enable error reporting for debugging
ini_set('display_errors', 1);
error_reporting(E_ALL);

$allowed_pages = [
    'loginForm', 'welcome', 'addContact', 'deleteContacts', 'addAdmin', 'deleteAdmins', 'logout'
];

$page = $_GET['page'] ?? 'loginForm';

// Redirect to login if page is not allowed
if (!in_array($page, $allowed_pages)) {
    header('Location: index.php?page=loginForm');
    exit();
}

// Only allow access if logged in, except for loginForm
if (!isset($_SESSION['access']) && $page !== 'loginForm') {
    header('Location: index.php?page=loginForm');
    exit();
}

// Staff cannot access admin pages
if (
    isset($_SESSION['status']) && $_SESSION['status'] === 'staff' &&
    in_array($page, ['addAdmin', 'deleteAdmins'])
) {
    header('Location: index.php?page=loginForm');
    exit();
}

// Route to the correct view
switch ($page) {
    case 'loginForm':
        require 'views/loginForm.php';
        break;
    case 'welcome':
        require 'views/welcome.php';
        break;
    case 'addContact':
        require 'views/addContactForm.php';
        break;
    case 'deleteContacts':
        require 'views/deleteContactsTable.php';
        break;
    case 'addAdmin':
        require 'views/addAdminForm.php';
        break;
    case 'deleteAdmins':
        require 'views/deleteAdminsTable.php';
        break;
    case 'logout':
        require 'views/logout.php';
        break;
    default:
        header('Location: index.php?page=loginForm');
        exit();
}
?>
