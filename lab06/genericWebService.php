<?php

require_once("DatabaseConnection.php");

if (isset($_POST)) {
    $varFN = $_REQUEST['selectFn'];

    if ($varFN == "searchStudent") {
        $recordSetObj = array();

        if (!empty($_REQUEST['studName'])) {
            $strStudName = $_REQUEST["studName"];
            $strQry = "SELECT * FROM users WHERE firstname LIKE :firstname OR lastname LIKE :lastname";
            $stmt = $dbPDO->prepare($strQry);
            $stmt->execute(array('firstname' => "%{$strStudName}%", 'lastname' => "%{$strStudName}%"));
            $recordSetObj = $stmt->fetchAll(PDO::FETCH_OBJ);
        } elseif (!empty($_REQUEST['studId'])) {
            $strStudId = $_REQUEST["studId"];
            $strQry = "SELECT * FROM users WHERE login = :login";
            $stmt = $dbPDO->prepare($strQry);
            $stmt->execute(array('login' => $strStudId));
            $recordSetObj = $stmt->fetchAll(PDO::FETCH_OBJ);
        } else {
            // If both studId and studName are empty, return an error message
            $recordSetObj = array(
                (object) array(
                    'message' => 'Please enter either a student ID or a student name'
                )
            );
        }

        echo json_encode($recordSetObj);
    }
}

?>