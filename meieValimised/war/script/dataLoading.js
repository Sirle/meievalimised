  //N‰itab kelle poolt inimene h‰‰letanud on juba
  function showDiv(){
	 $("Loading").show();
	 $.ajax({
		    type : "GET",
		    url : "/getVotedData",
		    data : {
		     "id": kasutajaID
		    },
		    dataType : "json",
		    success : function(call) {
		    	console.log(JSON.String(call)); 
		    },
		    error : function(e, t, n) {
		     alert(n)
		    }
		   })
	 $("Id_voted").show();
	 $("Loading").hide();
  }