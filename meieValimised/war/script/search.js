function otsi() {
		var firstname = $("#name").val();
		var partyCode = $("#party").val();
		var areaCode = $("#area").val();
		var partyName = $("#party option:selected").text();
		var regionName = $("#area option:selected").text();
		$('*').css('cursor','wait');

		$.ajax({
			type : 'GET',
			url: '/CandidateSearchServlet',
			 data: { 
    		 'firstname': firstname, 
    		 'lastname': '',
    		 'party': partyCode,
    		 'area': areaCode
				},
			dataType : 'json',
			success : function(data) {
				var candidates = data;
				createTable(candidates, partyName, regionName);
				$('*').css('cursor','default');
				console.log(JSON.stringify(data));
			},
			error : function(xhr, ajaxOptions, thrownError) {
				$('*').css('cursor','default');
				alert(thrownError);
			}
		});
}

function createTable(candidates, givenParty, givenRegion) {
	//clear previous table
	$('#candidate-list tbody tr').remove();

	var name, region, party;

	for (i in candidates) {
		//get values from json data
		name = candidates[i]['firstName'] + ' ' + candidates[i]['lastName'];
		party = candidates[i]['party'];
		region = candidates[i]['location'];

		//create new row with data
		var cols = new Array();
		
		//kandidaadiInfo="klikinime('"+name+"','"+region+"','"+party+"')";
		
		//asi="klikinime('"+name+"')";
		kandidaadiInfo="tere";

		cols[0] = $("<td></td>").text(name).attr("onClick",kandidaadiInfo);
		cols[1] = $("<td></td>").text(region).attr("onClick",kandidaadiInfo);
		cols[2] = $("<td></td>").text(party).attr("onClick",kandidaadiInfo);
		var row = $("<tr></tr>");//.attr("onClick",kandidaadiInfo);
		//$("td").click(function(){klikinime(kandidaadinimi,kandidaadilinn,kandidaadipartei);});


		for (j in cols)
			row.append(cols[j]);
		//append data to table
		row.addClass("candidateClickable");
		$("#candidate-list tbody").append(row);
	}
	//display table and apply tablesorter
	if ($("#candidate-list tbody tr").size() > 0) {
		$('#candidate-list').show();
		$("#candidate-list").trigger("clearCache");
		// let the plugin know that we made a update 
		$("#candidate-list").trigger("update");
	} else {
		$('#candidate-list').hide();
	}
}

function getCandidateInfo(candidate) {
	$('#partyInfo').text(candidate.party.name);
	$('#regionInfo').text(candidate.region.name);
	$('#personInfo').text(candidate.person.name);
}

function otsiStat() {
	var firstname = $("#name").val();
	var partyCode = $("#party").val();
	var areaCode = $("#area").val();
	var partyName = $("#party option:selected").text();
	var regionName = $("#area option:selected").text();
	$('*').css('cursor','wait');

	$.ajax({
		type : 'GET',
		url: '/CandidateSearchServlet',
		 data: { 
		 'firstname': firstname, 
		 'lastname': '',
		 'party': partyCode,
		 'area': areaCode
			},
		dataType : 'json',
		success : function(data) {
			var candidates = data;
			createTable(candidates, partyName, regionName);
			$('*').css('cursor','default');
			console.log(JSON.stringify(data));
		},
		error : function(xhr, ajaxOptions, thrownError) {
			$('*').css('cursor','default');
			alert(thrownError);
		}
	});
}