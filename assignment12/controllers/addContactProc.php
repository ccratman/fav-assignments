<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

echo "Script started<br>";

require_once(__DIR__ . '/../classes/StickyForm.php');
require_once(__DIR__ . '/../classes/Pdo_methods.php');
echo "Pdo_methods loaded<br>";

$acknowledgment = "<p></p>";//I use $acknowledgment as a placeholder because sometimes it has data and sometimes it does not and if it does not I don't want the space to collapse. 

$formConfig = [
    'first_name' => [
        'type' => 'text',
        'regex' => 'name',
        'label' => '*First Name',
        'name' => 'first_name',
        'id' => 'first_name',
        'errorMsg' => 'You must enter a valid first name',
        'error' => '',
        'required' => true,
        'value' => ''
    ],
    'last_name' => [
        'type' => 'text',
        'regex' => 'name',
        'label' => '*Last Name',
        'name' => 'last_name',
        'id' => 'last_name',
        'errorMsg' => 'You must enter a valid last name.',
        'error' => '',
        'required' => true,
        'value' => ''
    ],
    'address' => [
        'type' => 'text',
        'regex' => 'address',
        'label' => '*Address',
        'name' => 'address',
        'id' => 'address',
        'errorMsg' => 'You must enter a valid address.',
        'error' => '',
        'required' => true,
        'value' => '123 Anyplace'
    ],
    'state' => [
        'type' => 'select',
        'label' => '*State',
        'name' => 'state',
        'id' => 'state',
        'errorMsg' => 'You must select a state.',
        'error' => '',
        'selected' => 'mi',
        'required' => true,
        'options' => [
            '0' => 'Please Select a State',
            'ca' => 'California',
            'tx' => 'Texas',
            'mi' => 'Michigan',
            'ny' => 'New York',
            'fl' => 'Florida'
        ]
    ],
    'zip_code' => [
        'type' => 'text',
        'regex' => 'zip',
        'label' => '*Zip Code',
        'name' => 'zip_code',
        'id' => 'zip_code',
        'errorMsg' => 'You must enter a valid zip code.',
        'error' => '',
        'required' => true,
        'value' => '12345'
    ],
    'phone' => [
        'type' => 'text',
        'regex' => 'phone',
        'label' => '*Phone',
        'name' => 'phone',
        'id' => 'phone',
        'errorMsg' => 'You must enter a valid phone number.',
        'error' => '',
        'required' => true,
        'value' => '999.999.9999'
    ],
    'email' => [
        'type' => 'text',
        'regex' => 'email',
        'label' => '*Email',
        'name' => 'email',
        'id' => 'email',
        'errorMsg' => 'You must enter a valid email address.',
        'error' => '',
        'required' => true,
        'value' => ''
    ],
       
    'masterStatus' => [
        'error' => false
    ]

];


// Initialize StickyForm instance
$stickyForm = new StickyForm();

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    echo "POST request<br>";
    $pdo = new PdoMethods();

    $contacts = isset($_POST['contacts']) ? implode(',', $_POST['contacts']) : '';

    $sql = "INSERT INTO contacts (fname, lname, address, city, state, phone, email, dob, contacts, age)
            VALUES (:fname, :lname, :address, :city, :state, :phone, :email, :dob, :contacts, :age)";

    $bindings = [
        [':fname', $_POST['first_name'], 'str'],
        [':lname', $_POST['last_name'], 'str'],
        [':address', $_POST['address'], 'str'],
        [':city', $_POST['city'], 'str'],
        [':state', $_POST['state'], 'str'],
        [':phone', $_POST['phone'], 'str'],
        [':email', $_POST['email'], 'str'],
        [':dob', $_POST['dob'], 'str'],
        [':contacts', $contacts, 'str'],
        [':age', $_POST['age'], 'str']
    ];

    $result = $pdo->otherBinded($sql, $bindings);

    if ($result === 'noerror') {
        header("Location: ../index.php?page=addContact&msg=Contact added successfully!");
        exit;
    } else {
        header("Location: ../index.php?page=addContact&msg=Error adding contact.");
        exit;
    }
} else {
    echo "Not a POST request.<br>";
    header("Location: ../index.php?page=addContact");
    exit();
}