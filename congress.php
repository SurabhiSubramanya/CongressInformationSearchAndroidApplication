<html>
	<head>
		<title>
			HW6
		</title>

		<!--CSS STYLING STARTS HERE-->
		<style type="text/css">

			body {
					text-align: center;
					font-family: "Times New Roman", Times, serif;
			}

			.where {
					text-align: center;
			}

			.form_table {
					border: 1px solid black;
			}

			.result_table {
					/*border: 1px solid black;*/
          border-collapse: collapse;
			}

      .repAlign {
          padding-left: 2cm;
      }

      .otherAlign {
          text-align: center;
      }

      .imgAlign {
          display: block;
          margin: auto;
          width: 30%;
          padding-top: 30px; 
          padding-bottom: 30px;
      }

      .noCellBorder {
        border: none;
        margin-left: 100px;
      }

      .paddingCell {
        padding-left: 80px;
      }

      .paddingBottom {
        padding-bottom: 20px;
      }

      .paddingTop {
        padding-top: 20px;
      }

/*      .genTable {
          border: 1px solid black;
      }
*/
			/*.link {
    				target-name: new;
    				target-new: tab;
			}*/

		/* CSS STYLING ENDS HERE */
		</style> 
	</head>

	<body>
		
		<!--HEADING FOR THE PAGE ACCORDING TO EXPECTED OUTPUT-->
		<h2>Congress Information Search</h2>

		<!--FORM TO ACCEPT THE REQUIRED INFORMATION-->
		<form name="myform" action="congress.php" onsubmit="return validateForm(this)" method="POST">
  			<table class="form_table" align="center">
    				<tr>
    					<td class="where">Congress Database</td>
    					<td>
    						<select name="congressdatabase" id="databasename" onchange="changeKeyword()">
  								<option value="default" <?php if (isset($_POST['congressdatabase']) && $_POST['congressdatabase']=="default") echo ' selected';?>>Select your option</option>
  								<option value="legislators" <?php if (isset($_POST['congressdatabase']) && $_POST['congressdatabase']=="legislators") echo ' selected';?>>Legislators</option>
  								<option value="committees" <?php if (isset($_POST['congressdatabase']) && $_POST['congressdatabase']=="committees") echo ' selected';?>>Committees</option>
  								<option value="bills" <?php if (isset($_POST['congressdatabase']) && $_POST['congressdatabase']=="bills") echo ' selected';?>>Bills</option>
  								<option value="amendments" <?php if (isset($_POST['congressdatabase']) && $_POST['congressdatabase']=="amendments") echo ' selected';?>>Amendments</option>
							</select>
						</td>
    				</tr>
    				<tr>
    					<td class="where">Chamber</td>
    					<td><input id="radioSenate" type="radio" name="chamber" checked value="senate" <?php if (isset($_POST['chamber']) && $_POST['chamber'] == 'senate')  echo ' checked="checked"';?> />Senate
    					<input type="radio" name="chamber" value="house" <?php if (isset($_POST['chamber']) && $_POST['chamber'] == 'house')  echo ' checked="checked"';?> />House </td>
    				</tr>
    				<tr>
    					<td class="where" id="replaceKeyword">
                <?php echo isset($_POST["congressdatabase"])? printKeyword($_POST["congressdatabase"]) : 'Keyword*';?>
                <?php
                  function printKeyword($k)
                  {
                    switch($k)
                    {
                      case "default" : echo "Keyword*"; break;
                      case "legislators" : echo "State/Representative*"; break;
                      case "committees" : echo "Committee ID*"; break;
                      case "bills" : echo "Bill ID*"; break;
                      case "amendments" : echo "Amendment ID*"; break;
                    }
                  }
                ?>
              </td>
    					<td><input type="text" name="keyword" value="<?php echo isset($_POST['keyword']) ? $_POST['keyword'] : '' ?>" ></td>
    				</tr>
    				<tr>
    					<td></td>
    					<td>
                <input type="submit" name="search" value="Search">
    				    <input type="button" name="clear" value="Clear" onclick="clearForm(this.form)"></td>
    				</tr>
    				<tr>
    					<td colspan="2" class="where"><a href="http://sunlightfoundation.com/" target="_blank">Powered by Sunlight Foundation</a></td>
    				</tr>
			</table>
		</form>

<!--PHP CODE STARTS HERE-->
<?php

	//INITIALISE VARIABLE STATE
	$state = "nothing";
	$legislators = "";
  global $legislatorsDecoded;
  $committees = "";
  $committeesDecoded;
  $bills = "";
  global $billsDecoded;
  $amendments = "";
  $amendmentsDecoded;
  $errorFlag = false;
  $congressdatabase = "";

	//INITIALISE FORM VARIABLES FROM THE INPUT OBTAINED
	if(isset($_POST['search']) && ($_SERVER['REQUEST_METHOD'] == "POST"))
	{
		//DEBUG STATEMENT
		//echo $_POST["congressdatabase"], $_POST["chamber"], $_POST["keyword"];
		if (isset($_POST["congressdatabase"]) && isset($_POST["chamber"]) && isset($_POST["keyword"]))
		{
			$congressdatabase = $_POST["congressdatabase"];
			$chamber = $_POST["chamber"];
			$keyword = $_POST["keyword"];
			$keyword = trim($keyword);
  		$keyword = stripslashes($keyword);
  		$keyword = htmlspecialchars($keyword);
  			//print $keyword;

		}
	}
	
	/******CONSTRUCT HTTP REQUEST TO THE SUNLIGHT CONGRESS RESTFUL WEB SERVICE******/

	//IF ALL FORM ELEMENTS ARE SET
	if(($_SERVER["REQUEST_METHOD"] == "POST") && isset($_POST["search"]))
	{
		if (isset($congressdatabase) && isset($chamber) && isset($keyword)) // if the get parameter action is get_user and if the id is set, call the api to get the user information
		{
			//CASE: DATABASE CHOSEN IS LEGISLATORS
			if($congressdatabase  == "legislators")
			{
				//DEBUG STATEMENT
				//print($state);
			//START SWITCH CASE BLOCK FOR THE STATE TO TWO LETTER CODE MAPPING
				switch($keyword) 
				{
        			case "Alabama":
        			case "alabama":
            			$state = "AL";
            			break;

        			case "Alaska":
        			case "alaska":
            			$state = "AK";
            			break;

            		case "Arizona":
            		case "arizona":
            			$state = "AZ";
           				break;
        
        			case "Arkansas":
        			case "arkansas":
            			$state = "AR";
            			break;

        			case "California":
        			case "california":
            			$state = "CA";
            			break;

        			case "Colorado":
        			case "colorado":
            			$state = "CO";
            			break;

        			case "Connecticut":
        			case "connecticut":
            			$state = "CT"; 
            			break;

        			case "Delaware":
        			case "delaware":
            			$state = "DE";
            			break;

            		case "District Of Columbia":
            		case "District of Columbia":
            		case "district of columbia":
            		case "District of columbia":
            		case "district of Columbia": 
            		case "district Of Columbia":
            		case "district Of columbia":
            		case "District Of columbia":
            			$state = "DC";
            			break;

        			case "Florida":
        			case "florida":
            			$state = "FL";
            			break;

        			case "Georgia":
        			case "georgia":
            			$state = "GA";
            			break;

        			case "Hawaii":
        			case "hawaii":
            			$state = "HI";
            			break;

            		case "Idaho":
            		case "idaho":
            			$state = "ID";
            			break;

            		case "Illinois":
            		case "illinois":
            			$state = "IL";
            			break;

            		case "Indiana":
            		case "indiana":
            			$state = "IN";
            			break;

        			case "Iowa":
        			case "iowa":
            			$state = "IA";
            			break;

        			case "Kansas":
        			case "kansas":
            			$state = "KS";
            			break;

        			case "Kentucky":
        			case "kentucky":
            			$state = "KY";
            			break;

        			case "Louisiana":
        			case "louisiana":
            			$state = "LA";
            			break;

            		case "Maine":
            		case "maine":
            			$state = "ME";
            			break;

            		case "Maryland":
            		case "maryland":
            			$state = "MD";
            			break;

        			case "Massachusetts":
        			case "massachusetts":
            			$state = "MA";
            			break;
        		
        			case "Michigan":
        			case "michigan":
            			$state = "MI";
            			break;

        			case "Minnesota":
        			case "minnesota":
            			$state = "MN";
            			break;

            		case "Mississippi":
            		case "mississippi":
            			$state = "MS";
            			break;

        			case "Missouri":
        			case "missouri":
            			$state = "MO";
            			break;

        			case "Montana":
        			case "montana":
            		    $state = "MT";
            			break;

            		case "Nebraska":
            		case "nebraska":
            			$state = "NE";
            			break;

            		case "Nevada":
            		case "nevada":
            			$state = "NV";
            			break;

            		case "New Hampshire":
            		case "new hampshire":
            		case "New hampshire":
            		case "new Hampshire":
            			$state = "NH";
            			break;

            		case "New Jersey":
            		case "new jersey":
            		case "New jersey":
            		case "new Jersey":
            			$state = "NJ";
            			break;

            		case "New Mexico":
            		case "new mexico":
            		case "New mexico":
            		case "new Mexico":
            			$state = "NM";
            			break;

            		case "New York":
            		case "new york":
            		case "New york":
            		case "new York":
            			$state = "NY";
            			break;

        			case "North Carolina":
        			case "north carolina":
        			case "North carolina":
        			case "north Carolina":
            			$state = "NC";
            			break;

        			case "North Dakota":
        			case "north dakota":
        			case "North dakota":
        			case "north Dakota":
            			$state = "ND";
            			break;

        			case "Ohio":
        			case "ohio":
            			$state = "OH";
            			break;

        			case "Oklahoma":
        			case "oklahoma":
            			$state = "OK";
            			break;

        			case "Oregon":
        			case "oregon":
            			$state = "OR";
            			break;

        			case "Pennsylvania":
        			case "pennsylvania":
            			$state = "PA";
            			break;

        			case "Rhode Island":
        			case "rhode island":
        			case "Rhode island":
        			case "rhode Island":
            			$state = "RI";
            			break;

        			case "South Carolina":
        			case "south carolina":
        			case "South carolina":
        			case "south Carolina":
            			$state = "SC";
            			break;

        			case "South Dakota":
        			case "south dakota":
        			case "South dakota":
        			case "south Dakota":
            			$state = "SD";
            			break;

        			case "Tennessee":
        			case "tennessee":
            			$state = "TN";
            			break;

        			case "Texas":
        			case "texas":
            			$state = "TX";
            			break;

        			case "Utah":
        			case "utah":
            			$state = "UT";
            			break;

        			case "Vermont":
        			case "vermont":
            			$state = "VT";
            			break;

            		case "Virginia":
            		case "virginia":
            			$state = "VA";
            			break;

        			case "Washington":
        			case "washington":
            			$state = "WA";
            			break;

            		case "West Virginia":
            		case "west virginia":
            		case "West virginia":
            		case "west Virginia":
            			$state = "WV";
            			break;

        			case "Wisconsin":
        			case "wisconsin":
            			$state = "WI";
            			break;

        			case "Wyoming":
        			case "wyoming":
            			$state = "WY";
            			break;

            		default: 
            			$state = "nothing";
            			break;
    			//END SWITCH CASE BLOCK FOR STATE TO TWO LETTER CODE MAPPING        		
    			}

    			//DEBUG STATEMENT
    			//print($state);

    			//CASE CHAMBER IS SENATE AND REPRESENTATIVE IS ENTERED
    			if(($chamber === "senate") && ($state === "nothing"))
    			{
            $word_count = str_word_count($keyword);
            if ($word_count > 1)
            {
              $arrOfWords = explode(' ',$keyword,2);
              $legislators = file_get_contents('http://congress.api.sunlightfoundation.com/legislators?chamber=senate&=first_name=' . $arrOfWords[0] . '&last_name=' . urlencode($arrOfWords[1]) . '&apikey=97d5da407a40475aa556c622c0dbacd1');
              $legislatorsDecoded = json_decode($legislators, true);

            }
            else if($word_count == 1)
            {
    				  $legislators = file_get_contents('http://congress.api.sunlightfoundation.com/legislators?chamber=senate&query=' . $keyword . '&apikey=97d5da407a40475aa556c622c0dbacd1');
  					  $legislatorsDecoded = json_decode($legislators, true);
            }
  					//DEBUG STATEMENT
  					//var_dump($legislatorsDecoded);
    			}

    			//CASE CHAMBER IS SENATE AND STATE IS ENTERED 
				else if(($chamber === "senate") && ($state != "nothing"))
				{
					//$url = 'http://congress.api.sunlightfoundation.com/legislators?chamber=senate&state=' . $state . '&apikey=97d5da407a40475aa556c622c0dbacd1';
					//print $url;
					$legislators = file_get_contents('http://congress.api.sunlightfoundation.com/legislators?chamber=senate&state=' . $state . '&apikey=97d5da407a40475aa556c622c0dbacd1');
  					$legislatorsDecoded = json_decode($legislators, true);
  					//DEBUG STATEMENT
  					//var_dump($legislatorsDecoded);
				}

				//CASE CHAMBER IS HOUSE AND REPRESENTATIVE IS ENTERED 
				else if(($chamber === "house") && ($state === "nothing"))
    			{

            $word_count = str_word_count($keyword);
            if ($word_count > 1)
            {
              $arrOfWords = explode(' ',$keyword,2);
              $legislators = file_get_contents('http://congress.api.sunlightfoundation.com/legislators?chamber=house&=first_name=' . $arrOfWords[0] . '&last_name=' . urlencode($arrOfWords[1]) . '&apikey=97d5da407a40475aa556c622c0dbacd1');
              $legislatorsDecoded = json_decode($legislators, true);

            }
            else if($word_count == 1)
            {
              $legislators = file_get_contents('http://congress.api.sunlightfoundation.com/legislators?chamber=house&query=' . $keyword . '&apikey=97d5da407a40475aa556c622c0dbacd1');
              $legislatorsDecoded = json_decode($legislators, true);
            }
  					//DEBUG STATEMENT
  					//var_dump($legislatorsDecoded);
    			}

    			//CASE CHAMBER IS HOUSE AND STATE IS ENTERED 
				else if(($chamber === "house") && ($state != "nothing"))
				{
					$legislators = file_get_contents('http://congress.api.sunlightfoundation.com/legislators?chamber=house&state=' . $state . '&apikey=97d5da407a40475aa556c622c0dbacd1');
  					$legislatorsDecoded = json_decode($legislators, true);
  					//DEBUG STATEMENT
  					//var_dump($legislatorsDecoded);
				}

				//print $legislators;

				//ERROR HANDLING FOR EMPTY RESULTS
				if(count($legislatorsDecoded['results']) === 0)
  					{
  						$errorFlag = true;
  						echo "<br><br>";
  						echo "<div id='apiZero'>The API returned zero results for the request.</div>";
  					}

  				if($errorFlag === false)
  				{
    				//HTML CODE TO DISPLAY THE QUERIED LEGISLATORS RESULTS AS A TABLE-->
    				echo "<br>";
            echo "<div id=mainDiv>";
   					echo "<table id=infoTable class=result_table align=center border=1 width=60%>";
      					//TABLE HEADERS-->
       					echo "<tr><th>Name</th><th>State</th><th>Chamber</th><th>Details</th></tr>";
      					//<!--RESULT ROWS-->
      					for ($i=0;$i<count($legislatorsDecoded['results']);$i++)
      					{	
      						$x=$legislatorsDecoded['results'][$i]['first_name']. " " . $legislatorsDecoded['results'][$i]['last_name'];
      						$y=$legislatorsDecoded['results'][$i]['state_name'];
      						$z=$legislatorsDecoded['results'][$i]['chamber'];
                  $bioguide_id = $legislatorsDecoded['results'][$i]['bioguide_id'];
                  $title = $legislatorsDecoded['results'][$i]['title'];
                  $first_name = $legislatorsDecoded['results'][$i]['first_name'];
                  $last_name = $legislatorsDecoded['results'][$i]['last_name'];
                  $term_end = $legislatorsDecoded['results'][$i]['term_end'];
                  $website = json_encode($legislatorsDecoded['results'][$i]['website']);
                  $website = htmlspecialchars($website);
                  str_replace('"', '', $website);
                  $office = $legislatorsDecoded['results'][$i]['office'];
                  if(isset($legislatorsDecoded['results'][$i]['facebook_id']))
                  {
                    $facebook_id=$legislatorsDecoded['results'][$i]['facebook_id'];
                  }
                  else
                  {
                    $facebook_id = "";
                  }
                  if(isset($legislatorsDecoded['results'][$i]['twitter_id']))
                  {
                    $twitter_id=$legislatorsDecoded['results'][$i]['twitter_id'];
                  }
                  else
                  {
                    $twitter_id = "";
                  }
      						echo "<tr><td class=repAlign>$x</td><td class=otherAlign>$y</td><td class=otherAlign>$z</td><td class=otherAlign><a href=javascript:; onclick='viewLegDetails(\"". $bioguide_id ."\", \"". $title ."\",\"". $first_name ."\",\"". $last_name ."\",\"". $term_end ."\",". $website .",\"". $office ."\",\"". $facebook_id ."\",\"". $twitter_id ."\")'>View Details</a></td></tr>\n";
      					} 
    				echo "</table>";
            echo "</div>";
    			}//END BLOCK TO PRINT TABLE
		
  			}//END CASE LEGISLATORS

  			//CASE: DATABASE CHOSEN IS COMMITTEES
  			else if ($congressdatabase  == "committees")
  			{
  				if($chamber === "senate")
    			{
    				$committees = file_get_contents('http://congress.api.sunlightfoundation.com/committees?committee_id=' . $keyword . '&chamber=senate&apikey=97d5da407a40475aa556c622c0dbacd1');
  					$committeesDecoded = json_decode($committees, true);
  					//DEBUG STATEMENT
  					//var_dump($legislatorsDecoded);
    			}

    			//CASE CHAMBER IS SENATE AND STATE IS ENTERED 
				else if($chamber === "house")
				{
					$committees = file_get_contents('http://congress.api.sunlightfoundation.com/committees?committee_id=' . $keyword . '&chamber=house&apikey=97d5da407a40475aa556c622c0dbacd1');
  					$committeesDecoded = json_decode($committees, true);
  					//DEBUG STATEMENT
  					//var_dump($legislatorsDecoded);
				}

				//ERROR HANDLING FOR EMPTY RESULTS
				if(count($committeesDecoded['results']) === 0)
  					{
  						$errorFlag = true;
  						echo "<br><br>";
  						echo "<div id='apiZero'>The API returned zero results for the request.</div>";
  					}

  				if($errorFlag === false)
  				{
					//HTML CODE TO DISPLAY THE QUERIED COMMITTEES RESULTS AS A TABLE-->
    				echo "<br>";
   					echo "<table class=result_table align=center border=1 width=60%>";
      					//TABLE HEADERS-->
       					echo "<tr><th>Committee ID</th><th>Committee Name</th><th>Chamber</th></tr>";
      					//<!--RESULT ROWS-->
      					for ($i=0;$i<count($committeesDecoded['results']);$i++)
      					{	
      						$x=$committeesDecoded['results'][$i]['committee_id'];
      						$y=$committeesDecoded['results'][$i]['name'];
      						$z=$committeesDecoded['results'][$i]['chamber'];
      						echo "<tr><td class=otherAlign>$x</td><td class=otherAlign>$y</td><td class=otherAlign>$z</td></tr>";
      					} 
    				echo "</table>";
    			}//END BLOCK TO PRINT TABLE

  			}//END CASE COMMIITTEES

  			//CASE: DATABASE CHOSEN IS BILLS
  			else if ($congressdatabase  == "bills")
  			{
  				if($chamber === "senate")
    			{
    				$bills = file_get_contents('http://congress.api.sunlightfoundation.com/bills?bill_id=' . $keyword . '&chamber=senate&apikey=97d5da407a40475aa556c622c0dbacd1');
  					$billsDecoded = json_decode($bills, true);
  					//DEBUG STATEMENT
  					//var_dump($legislatorsDecoded);
    			}

    			//CASE CHAMBER IS SENATE AND STATE IS ENTERED 
				else if($chamber === "house")
				{
					$bills = file_get_contents('http://congress.api.sunlightfoundation.com/bills?bill_id=' . $keyword . '&chamber=house&apikey=97d5da407a40475aa556c622c0dbacd1');
  					$billsDecoded = json_decode($bills, true);
  					//DEBUG STATEMENT
  					//var_dump($legislatorsDecoded);
				}

				//ERROR HANDLING FOR EMPTY RESULTS
				if(count($billsDecoded['results']) === 0)
  					{
  						$errorFlag = true;
  						echo "<br><br>";
  						echo "<div id='apiZero'>The API returned zero results for the request.</div>";
  					}

  				if($errorFlag === false)
  				{
					//HTML CODE TO DISPLAY THE QUERIED COMMITTEES RESULTS AS A TABLE-->
    				echo "<br>";
            echo "<div id=mainDiv>";
   					echo "<table id=billTable class=result_table align=center border=1 width=60%>";
      					//TABLE HEADERS-->
       					echo "<tr><th>Bill ID</th><th>Short Title</th><th>Chamber</th><th>Details</th></tr>";
      					//<!--RESULT ROWS-->
      					for ($i=0;$i<count($billsDecoded['results']);$i++)
      					{	
      						$x=$billsDecoded['results'][$i]['bill_id'];
      						$y=$billsDecoded['results'][$i]['short_title'];
      						$z=$billsDecoded['results'][$i]['chamber'];
                  $bill_id=$billsDecoded['results'][$i]['bill_id'];
                  $bill_title=$billsDecoded['results'][$i]['short_title'];
                  $title=$billsDecoded['results'][$i]['sponsor']['title'];
                  $first_name=$billsDecoded['results'][$i]['sponsor']['first_name'];
                  $last_name=$billsDecoded['results'][$i]['sponsor']['last_name'];
                  $introduced_on=$billsDecoded['results'][$i]['introduced_on'];
                  $version_name=$billsDecoded['results'][$i]['last_version']['version_name'];
                  $last_action_at=$billsDecoded['results'][$i]['last_action_at'];
                  $url=$billsDecoded['results'][$i]['last_version']['urls'];
                  $bill_url=$url['pdf'];
      						echo "<tr><td class=otherAlign>$x</td><td class=otherAlign>$y</td><td class=otherAlign>$z</td><td class=otherAlign><a <a href=javascript:; onclick='viewBillDetails(\"". $bill_id ."\", \"". $bill_title ."\",\"". $title ."\",\"". $first_name ."\",\"". $last_name ."\",". $introduced_on .",\"". $version_name ."\",\"". $last_action_at ."\",\"". $bill_url ."\")'>View Details</a></td></tr>\n";
      					} 
    				echo "</table>";
            echo "</div>";

    			}//END BLOCK TO PRINT TABLE

  			}//END CASE BILLS

  			//CASE: DATABASE CHOSEN IS AMENDMENTS
  			else if ($congressdatabase  == "amendments")
  			{
  				if($chamber === "senate")
    			{
    				$amendments = file_get_contents('http://congress.api.sunlightfoundation.com/amendments?amendment_id=' . $keyword . '&chamber=senate&apikey=97d5da407a40475aa556c622c0dbacd1');
  					$amendmentsDecoded = json_decode($amendments, true);
  					//DEBUG STATEMENT
  					//var_dump($legislatorsDecoded);
    			}

    			//CASE CHAMBER IS SENATE AND STATE IS ENTERED 
				else if($chamber === "house")
				{
					$amendments = file_get_contents('http://congress.api.sunlightfoundation.com/amendments?amendment_id=' . $keyword . '&chamber=house&apikey=97d5da407a40475aa556c622c0dbacd1');
    				$amendmentsDecoded = json_decode($amendments, true);
  					//DEBUG STATEMENT
  					//var_dump($legislatorsDecoded);
				}

				//ERROR HANDLING FOR EMPTY RESULTS
				if(count($amendmentsDecoded['results']) === 0)
  					{
  						$errorFlag = true;
  						echo "<br><br>";
  						echo "<div id='apiZero'>The API returned zero results for the request.</div>";
  					}

				if($errorFlag === false)
				{
					//HTML CODE TO DISPLAY THE QUERIED COMMITTEES RESULTS AS A TABLE-->
    				echo "<br>";
   					echo "<table class=result_table align=center border=1 width=60%>";
      					//TABLE HEADERS-->
       					echo "<tr><th>Amendment ID</th><th>Amendment Type</th><th>Chamber</th><th>Introduced on</th></tr>";
      					//<!--RESULT ROWS-->
      					for ($i=0;$i<count($amendmentsDecoded['results']);$i++)
      					{	
      						$x=$amendmentsDecoded['results'][$i]['amendment_id'];
      						$y=$amendmentsDecoded['results'][$i]['amendment_type'];
      						$z=$amendmentsDecoded['results'][$i]['chamber'];
      						$a=$amendmentsDecoded['results'][$i]['introduced_on'];
      						echo "<tr><td class=otherAlign>$x</td><td class=otherAlign>$y</td><td class=otherAlign>$z</td><td class=otherAlign>$a</td></tr>";
      					} 
    				echo "</table>";
  				}//END BLOCK TO PRINT TABLE

  			}//END CASE AMENDMENTS

  		}//END BLOCK THAT CHECKS IF THE FORM ELEMENTS ARE SET OR NOT 

	}//CLOSE THIS LATER - OUTERMOST LOOP CHECKING IF THE SUBMIT BUTTON IS SET OR NOT - PERFORM PH PROCESSING ONLY AFTER THE BUTTON IS CLICKED

//PHP CODE ENDS HERE		
?>

<!--FUNCTION TO REPLACE THE KEYWORD BASED ON THE INPUT-->
<script type="text/javascript">

	function changeKeyword()
	{
		var e = document.getElementById("databasename");
		var name = e.options[e.selectedIndex].value;

		if(name === "default")
		{
			document.getElementById("replaceKeyword").innerHTML = "Keyword*";
		}

 
		else if(name === "legislators")
		{
			document.getElementById("replaceKeyword").innerHTML = "State/Representative*";
		}

		else if(name === "committees")
		{
			document.getElementById("replaceKeyword").innerHTML = "Committee ID*";
		}

		else if(name === "bills")
		{
			document.getElementById("replaceKeyword").innerHTML = "Bill ID*";
		}

		else if(name === "amendments")
		{
			document.getElementById("replaceKeyword").innerHTML = "Amendment ID*";
		}
	}

	function validateForm(myForm)
	{
		//alert("hello");
		var flag = 0;
		var errMsg = "Please enter the following missing information: ";
		var finalErrMsg = "";
		var x = myForm.congressdatabase.value;
    	if ((x == "default") || (x == null))
    	{
    		flag = 1;
    		finalErrMsg = errMsg + "Congress Database";
    	}
    	var y = myForm.keyword.value;
    	if ((y == "") || (y == null) || (y.match(/^\s*$/)))
    	{
    		if (flag == 0)
    		{
    			flag = 1;
    			finalErrMsg = errMsg + "Keyword";
    		}

    		else if (flag == 1)
    		{
    			finalErrMsg = finalErrMsg + ", Keyword";
    		}
    	}

      	if (flag == 1) 
      	{
      		window.alert(finalErrMsg);
      		return false;
      	}

      	else if (flag == 0)
      	{
      		return true;
      	}

    }

    function clearForm(myForm)
    {
    	var elements = myForm.elements; 

      if (document.getElementById('mainDiv') === null){} else { document.getElementById('mainDiv').innerHTML="";}
      if (document.getElementById('apiZero') === null){} else { document.getElementById('apiZero').innerHTML="";}
    
  		for(var i=0; i<elements.length; i++) 
  		{
      
  			var field_type = elements[i].type.toLowerCase();
  
  			switch(field_type) 
  			{
    			case "text":    
      				elements[i].value = "";
      				document.getElementById("replaceKeyword").innerHTML = "Keyword*";
					break;
        
    			case "radio":
        			if (elements[i].checked) 
        			{
                  elements[i].checked = false;
      				}
      				break;

    			case "select-one":
                	elements[i].selectedIndex = "default";
     				break;

    			default: 
      				break;
  			}
   		}
      document.getElementById('radioSenate').checked = true;
    }
    
    /*function test(abc) {
      console.log(abc);
    }*/

    function viewLegDetails(bioguide_id,title,first_name,last_name,term,website,office,facebook_id,twitter_id)
    {
      //COMPUTE ALL DETAILS BEFORE DISPLAYING IN TABLE 
      var image_url = "https://theunitedstates.io/images/congress/225x275/" + bioguide_id + ".jpg";
      var full_name = first_name + " " + last_name;
      var twitter_name;
      var facebook_name;

      var facebook_tr;
      var twitter_tr;
      if (facebook_id == "")
      {
        facebook_name = "NA";
        var facebook = "#";
        facebook_tr = "<tr><td class='noCellBorder paddingCell'>Facebook</td><td class=noCellBorder>"+facebook_name+"</td></tr>";

      }
      else 
      {
        var facebook = "https://www.facebook.com/" + facebook_id;
        facebook_name = full_name;
        facebook_tr = "<tr><td class='noCellBorder paddingCell'>Facebook</td><td class=noCellBorder><a href="+facebook+" target='_blank'>"+facebook_name+"</a></td></tr>";
      }
      if (twitter_id == "")
      {
        twitter_name = "NA";
        var twitter = "#";
        twitter_tr = "<tr><td class='noCellBorder paddingCell paddingBottom'>Twitter</td><td class='noCellBorder paddingBottom'>"+twitter_name+"</td></tr>";

      }
      else 
      {
        var twitter = "https://twitter.com/" + twitter_id;
        twitter_name = full_name;
        twitter_tr = "<tr><td class='noCellBorder paddingCell paddingBottom'>Twitter</td><td class='noCellBorder paddingBottom'><a href="+twitter+" target='_blank'>"+twitter_name+"</a></td></tr>";
      }
        document.getElementById("infoTable").innerHTML = "<table class='genTable'>"+
        "<tr><td class='noCellBorder paddingTop' colspan=2><img class=imgAlign src="+image_url+"></td></tr>"+
        "<tr><td width='50%' class='noCellBorder paddingCell'>Full Name</td><td width='50%' class=noCellBorder>"+title+" "+full_name+"</td></tr>"+
        "<tr><td class='noCellBorder paddingCell'>Term Ends on</td><td class=noCellBorder>"+term+"</td></tr>"+
        "<tr><td class='noCellBorder paddingCell'>Website</td><td class=noCellBorder><a href="+website+" target='_blank'>"+website+"</a></td></tr>"+
        "<tr><td class='noCellBorder paddingCell'>Office</td><td class='noCellBorder'>"+office+"</td></tr>"+
        facebook_tr+
        twitter_tr+
        "</table>";
    }

    function viewBillDetails(bill_id,bill_title,title,first_name,last_name,introduced_on,version_name,last_action_at,bill_url)
    {
      var sponsor = title + " " + first_name + " " + last_name;
      var last_action_with_date = version_name + ", " + last_action_at;
      document.getElementById("billTable").innerHTML = "<table style='border:1px solid black'>"+
        "<tr><td width='50%' class='noCellBorder paddingCell paddingTop'>Bill ID</td><td width='50%' class='noCellBorder paddingTop'>"+bill_id+"</td></tr>"+
        "<tr><td class='noCellBorder paddingCell'>Bill Title</td><td class=noCellBorder>"+bill_title+"</td></tr>"+
        "<tr><td class='noCellBorder paddingCell'>Sponsor</td><td class=noCellBorder>"+sponsor+"</td></tr>"+
        "<tr><td class='noCellBorder paddingCell'>Introduced On</td><td class=noCellBorder>"+introduced_on+"</td></tr>"+
        "<tr><td class='noCellBorder paddingCell'>Last action with date</td><td class=noCellBorder>"+last_action_with_date+"</td></tr>"+
        "<tr><td class='noCellBorder paddingCell paddingBottom'>Bill URL</td><td class='noCellBorder paddingBottom'><a href="+bill_url+" target='_blank'>"+bill_title+"</a></td></tr>"+
        "</table>";
    }

    /*
    function keywordUpdate()
    {
    	var e = <?php echo $_POST['congressdatabase'] ?>;

		//alert(e);
 
		if(e === "legislators")
		{
			document.getElementById("replaceKeyword").innerHTML = "State/Representative*";
		}

		else if(e === "committees")
		{
			document.getElementById("replaceKeyword").innerHTML = "Committee ID*";
		}

		else if(e === "bills")
		{
			document.getElementById("replaceKeyword").innerHTML = "Bill ID*";
		}

		else if(e === "amendments")
		{
			document.getElementById("replaceKeyword").innerHTML = "Amendment ID*";
		}
    }*/

    /*function keywordChange()
    {
    	var e = document.getElementById("databasename");
		var name = e.options[e.selectedIndex].value;
 
		if(name === "legislators")
		{
			document.getElementById("replaceKeyword").innerHTML = "State/Representative*";
		}

		else if(name === "committees")
		{
			document.getElementById("replaceKeyword").innerHTML = "Committee ID*";
		}

		else if(name === "bills")
		{
			document.getElementById("replaceKeyword").innerHTML = "Bill ID*";
		}

		else if(name === "amendments")
		{
			document.getElementById("replaceKeyword").innerHTML = "Amendment ID*";
		}
    */

</script>

<!--END THE BODY OF THE HTML CONTENT-->
	</body>
<!--END THE HTML PORTION-->
</html>
