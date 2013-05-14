 // Additional JS functions here
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '504180456313968', // App ID
      channelUrl : 'http://meievalimised.appspot.com/channel.html', // Channel File
      status     : true, // check login status
      cookie     : true, // enable cookies to allow the server to access the session
      xfbml      : true  // parse XFBML
    });

    // Additional init code here
    FB.getLoginStatus(function(response) {
    	console.log(response);
    	console.log(response.status);
    	
    	  if (response.status === 'connected') {
    	    // connected
    	    window.kasutajaID = response.authResponse.userID;
    	    // location.href= "WebHome.html";
    	    // document.getElementByID("Id".onclick = function(){}
    	    login();
    	    document.getElementById("logi").onclick = function() {
    	    	logout()
    	    	
    	    };
    	    document.getElementById("logi").value = "Välju";
    	    $("#kuvah22leta").show();
    	    
    	  
    
    	  } else if (response.status === 'not_authorized') {
    	    // not_authorized
    		  login();
    	  } else {
    	    // not_logged_in
    		  login();
    		  
    	  }
    	  console.log(response);
    	  //window.kasutajaID = response.authResponse.userID;
    	 });
  };
  
  function login() {
	    FB.login(function(response) {
	    	console.log(response.authResponse)
	        if (response.authResponse) {
	        	
	            // connected
	        	 testAPI();
	   	      document.getElementById("logi").value = "Välju";
	   	      $("#kuvah22leta").show();
	   	      document.getElementById("logi").onclick = function() {
	   	       logout()
	   	      };
	   	     /**document.getElementById("kasutajainfo").innerHTML = "Olete sisse logitud.<br>Kasutaja ID on: "
	   	        + window.kasutajaID;
	   	      a = location.hash;
	   	      a = a.replace("#", "");
	   	      if (a === "Vote" || a === "Kandidaadid") {
	   	       $("Id_voted").show()
	   	       */
	   	      
	   	     } else  {
	   	      console.log("cancelled");
	   	     }
	   	    })
	   	 }
	   	 function logout() {
	   	  FB
	   	    .logout(function(e) {
	   	     console.log("User is now logged out");
	   	     document.getElementById("logi").value = "Sisene";
	   	     $("#kuvah22leta").hide();
	   	     document.getElementById("logi").onclick = function() {
	   	      login()
	   	      
	   	     };
	   	     /**
	   	     document.getElementById("kasutajainfo").innerHTML = "Palun logige sisse.";
	   	     a = location.hash;
	   	     a = a.replace("#", "");
	   	     if (a === "Vote" || a === "Kandidaadid") {
	   	      $("#sees").hide()
	   	      */
	   	     
	   	    })
	   	 }
	  	   
	   	 
  function testAPI() {
	    console.log('Welcome!  Fetching your information.... ');
	    FB.api('/me', function(response) {
	        console.log('Good to see you, ' + response.name + '.');
	    });
	}
  // Load the SDK Asynchronously
  (function(d){
     var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement('script'); js.id = id; js.async = true;
     js.src = "//connect.facebook.net/en_US/all.js";
     ref.parentNode.insertBefore(js, ref);
   }(document));
  
  
