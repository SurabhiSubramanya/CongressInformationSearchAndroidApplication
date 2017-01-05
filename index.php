<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: PUT, GET, POST");
header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");

if(isset($_GET['legislators'])) {    
    $legislators = file_get_contents('http://104.198.0.197:8080/legislators?order=state_name__asc,last_name__asc&per_page=all');
              
    echo $legislators;
}

if(isset($_GET['viewdetailslegbills'])) {  
    $viewDetailsLegBills = file_get_contents('http://104.198.0.197:8080/bills?&per_page=5&sponsor_id='.$_GET['viewdetailslegbills']);
              
    echo $viewDetailsLegBills;
}

if(isset($_GET['viewdetailslegcomm'])) {    
    $viewDetailsLegComm = file_get_contents('http://104.198.0.197:8080/committees?&per_page=5&member_ids='.$_GET['viewdetailslegcomm']);
              
    echo $viewDetailsLegComm;
}

if(isset($_GET['activebills'])) {    
    $activebills = file_get_contents('http://104.198.0.197:8080/bills?history.active=true&last_version.urls.pdf__exists=true&order=introduced_on__desc&per_page=50');
              
    echo $activebills;
}

if(isset($_GET['newbills'])) {    
    $newbills = file_get_contents('http://104.198.0.197:8080/bills?history.active=false&last_version.urls.pdf__exists=true&order=introduced_on__desc&per_page=50');
              
    echo $newbills;
}

if(isset($_GET['committees'])) {    
    $committees = file_get_contents('http://104.198.0.197:8080/committees?per_page=all');
              
    echo $committees;
}

?>