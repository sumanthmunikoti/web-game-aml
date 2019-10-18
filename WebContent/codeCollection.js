var sessionUser = '';


function sessionCode(){

	$.ajax({
		url : "TestServlet2",
		type : 'POST',
		dataType : 'text',
		success : function(response) {
			var type = typeof (response);
			console.log(type);
			console.log("response:");
			console.log(response);
			document.getElementById("welcome").innerHTML = "Welcome, "+ response;
			sessionUser = response.trim();
		},
		error : function(xhr, ajaxOptions, thrownError) {
			if (xhr.status == 200) {
				console.log("error");
				console.log(xhr);
			} 
		}
	});
}// sessionCode ends here

//---------------------------------------------------------------------------

function loadTransactions(){
	
	var theArray = [];
	
	$.ajax({
	url : "ViewTransactions",
	type : 'POST',
	contentType : 'application/json',
	//data : sessionUser,
	dataType : 'text',
	success : function(response) {
		var type = typeof (response);
		console.log(type);
		console.log("response:");
		console.log(response);
		response = JSON.parse(response);

		for (i = 0; i < response.length; i++) {

			var jsoni = response[i];
			console.log(jsoni);

			var transactionid = jsoni.transactionid;
			var sender = jsoni.sender;
			var valuableQty = jsoni.valuableQty;
			var commodity = jsoni.commodity;
			var receiver = jsoni.receiver;
			var reported = jsoni.reported;
			
			var preArray = [];
			
			preArray.push(transactionid);
			preArray.push(sender);
			preArray.push(valuableQty);
			preArray.push(commodity);
			preArray.push(receiver);
			preArray.push(reported);
			
			theArray.push(preArray);
			
		}// end for
		loadTable(theArray);
	},

	error : function(xhr, ajaxOptions, thrownError) {
		if (xhr.status == 200) {
			console.log("error");
			console.log(xhr);
			//alert(ajaxOptions);
		} else {
			//alert(xhr.status);
			//alert(thrownError);
		}
	}
});

	function loadTable(dataSet){
		//console.log("In loadTable function");
		$(document).ready(function() {
			$('#reportTable').DataTable({
				data : theArray,
				columns : [ 
				            {title : "Transaction ID"}, 
				            {title : "Sender"}, 
			                {title : "Amount"},
			                {title : "Commodity"},
			                {title : "Receiver"},
			                {title : "Red Flagged?"},
			           ]
			});
		});
	}

}

//-------------------------------------------------------------------------------

function viewTransactions(){
	
	//-----------------------------userlogdetails---------------------------
	
	var today = new Date();
	var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
	var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
	var dateTime = date+' '+time;
	console.log(dateTime);
	
	var eventName = "View Transactions Button clicked";
	var value = "";
	var userLog = "{'clientTimeStamp':'"+dateTime+"','eventName':'"+eventName+"','value':'"+value+"'}";
	
	$.ajax({
		url : "UserLogServlet",
		type : 'POST',
		contentType : 'application/json',
		data : userLog,

		success : function(data) {
			console.log("Communication successful");
			console.log(data);
			var type = typeof (data);
			console.log(type);
		},

		error : function(xhr, ajaxOptions, thrownError) {
			if (xhr.status == 200) {
				console.log("Failed");
			} 
		}
	});

	//-----------------------------userlogdetails---------------------------

	window.location.href = "ViewTransactions.html";
}

//--------------------updateAlerted----------------------------------------------

function updateAlerted(transactionid){
	
	//change alerted to yes
	$
	.ajax({
		url : "UpdateAlerted",
		type : 'POST',
		contentType : 'application/json',
		data : transactionid,
		dataType : 'text',
		
		success : function(response) {
			console.log("refreshing.....");
			window.location.reload();
			}
	});

}

//-----------------------------------------------------------------------------

function fillAssetsTable(){
	$.ajax({
		url : "RetrieveAssetValues",
		type : 'POST',
		contentType : 'application/json',
		dataType : 'text',
		success : function(response) {

			var type = typeof (response);
			console.log(type);
			console.log("response:");
			console.log(response);
			response = JSON.parse(response);

			var cleanmoney = response.cleanmoney;
			var dirtymoney = response.dirtymoney;
			var preciousMetals = response.preciousMetals;
			var property = response.property;
			var artWorks = response.artWorks;

			document.getElementById("cm").innerHTML = cleanmoney;
			document.getElementById("dm").innerHTML = dirtymoney;
			document.getElementById("pm").innerHTML = preciousMetals;
			document.getElementById("ppty").innerHTML = property;
			document.getElementById("ar").innerHTML = artWorks;
		},

		error : function(xhr, ajaxOptions, thrownError) {
			//On error do this
			if (xhr.status == 200) {
				console.log("error");
				console.log(xhr);
				//alert(ajaxOptions);
			} else {
				//alert(xhr.status);
				//alert(thrownError);
			}
		}
	});
	
}

//-----------------------------------------------------------------------------
//----------The below is from chat.html----------------------------------------

function chatCode() {

	var Chat = {};
	var message;

	Chat.socket = null;

	Chat.connect = (function(host) {
		if ('WebSocket' in window) {
			Chat.socket = new WebSocket(host);
		} else if ('MozWebSocket' in window) {
			Chat.socket = new MozWebSocket(host);
		} else {
			Console.log('Error: WebSocket is not supported by this browser.');
			return;
		}

		Chat.socket.onopen = function() {
			//Console.log('Info: WebSocket connection opened.');
			document.getElementById('chat').onkeydown = function(event) {
				if (event.keyCode == 13) {
					Chat.sendMessage();
				}
			};
		};

		Chat.socket.onclose = function() {
			document.getElementById('chat').onkeydown = null;
			//Console.log('Info: WebSocket closed.');
		};

		Chat.socket.onmessage = function(message) {
			Console.log(message.data);

			//------------userlogdetails------

			var today = new Date();
			var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-'
					+ today.getDate();
			var time = today.getHours() + ":" + today.getMinutes() + ":"
					+ today.getSeconds();
			var dateTime = date + ' ' + time;
			console.log(dateTime);

			var eventName = "Chat";
			var value = message.data;

			var userLog = "{'clientTimeStamp':'" + dateTime + "','eventName':'"
					+ eventName + "','value':'" + value + "'}";

			$.ajax({
				url : "UserLogServlet",
				type : 'POST',
				contentType : 'application/json',
				data : userLog,

				success : function(data) {
				},

				error : function(xhr, ajaxOptions, thrownError) {
					if (xhr.status == 200) {
						console.log("Failed");
					}
				}
			});
			//------userlogdetails------------
		};
	});

	Chat.initialize = function() {
		if (window.location.protocol == 'http:') {
			Chat.connect('ws://' + window.location.host + '/aml/aml/chat');
		} else {
			Chat.connect('wss://' + window.location.host + '/aml/aml/chat');
		}
	};

	Chat.sendMessage = (function() {
		message = document.getElementById('chat').value;
		if (message != '') {
			//Chat.socket.send(message);
			document.getElementById('chat').value = '';

			var date = new Date();
			var strdate = date.getTime();
			console.log(strdate);

			var str = "{'time':'" + strdate + "','message':'" + message
							+ "','sessionId':'" + sessionUser + "'}";
			console.log("str: " + str);
			Chat.socket.send(str);

		}
	});

	var Console = {};

	Console.log = (function(message) {
		var console = document.getElementById('console');
		var p = document.createElement('p');
		p.style.wordWrap = 'break-word';
		p.innerHTML = message;
		console.appendChild(p);
		while (console.childNodes.length > 25) {
			console.removeChild(console.firstChild);
		}
		console.scrollTop = console.scrollHeight;
	});

	Chat.initialize();

	document.addEventListener("DOMContentLoaded", function() {
		// Remove elements with "noscript" class - <noscript> is not allowed in XHTML
		var noscripts = document.getElementsByClassName("noscript");
		for (var i = 0; i < noscripts.length; i++) {
			noscripts[i].parentNode.removeChild(noscripts[i]);
		}
	}, false);
}

//----------------------------------------The above is from chat.html-----------

function transactionDetails(){
	
	var valuableQty = document.getElementsByName('valuableQty')[0].value;
	var commodity = document.getElementById('valuableName').value;
	var userid = document.getElementById('usersList').value;
	var alerted = "no";
	
	if((valuableQty < 0) || (valuableQty % 1 !=0)){
		alert("Please enter a valid number");
	}
	
	else if (confirm("Are you sure you want to send "+valuableQty+" units of "+commodity+" to "+userid)) {
		$.ajax({
			url : "RetrieveUserCommQty",
			type : 'POST',
			contentType : 'application/json',
			data: commodity,
			dataType : 'text',
			
			success : function(response) {
				
				response = JSON.parse(response);
				console.log("response after parsing: "+commQty);
				
				var jsoni = response[0];
				var commQty = jsoni.commQty;
				commQty = parseInt(commQty);
				var type = typeof (commQty);
				
				if(valuableQty <= commQty)
				{
					changeQty(valuableQty, userid, commodity);
					
					dataStr = '{"sender":"'+sessionUser+'","valuableQty":"'+valuableQty+'","commodity":"'+commodity+'","receiver":"'+userid+'","alerted":"no","reported":"no"}';

					//-----------------------------userlogdetails---------------------------
					
					var today = new Date();
					var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
					var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
					var dateTime = date+' '+time;
					console.log(dateTime);
					
					var eventName = "Send "+valuableQty +" of "+commodity+ " to "+ userid;
					var value = "";
					
					var userLog = "{'clientTimeStamp':'"+dateTime+"','eventName':'"+eventName+"','value':'"+value+"'}";
					
					$.ajax({
						url : "UserLogServlet",
						type : 'POST',
						contentType : 'application/json',
						data : userLog,

						success : function(data) {
							console.log("Communication successful");
							console.log(data);
							var type = typeof (data);
							console.log(type);
						},

						error : function(xhr, ajaxOptions, thrownError) {
							if (xhr.status == 200) {
								console.log("Failed");
							} 
						}
					});

					//-----------------------------userlogdetails end---------------------------
					
					$.ajax({

						url : "SimpleTransaction",
						type : 'POST',
						contentType : 'application/json',
						data: dataStr,
						dataType : 'text',
						
						success : function(response) {
							window.location.reload();
						},

						error : function(xhr, ajaxOptions, thrownError) {
							if (xhr.status == 200) {
								console.log("error junk if");
								console.log(xhr);
							} else {
								console.log("error junk else");
								console.log(xhr);
							}
						}
					});
				
				}
				else{
					alert("You aren't that rich yet!")
					
					//-----------------------------userlogdetails start---------------------------
					
					var today = new Date();
					var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
					var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
					var dateTime = date+' '+time;
					console.log(dateTime);
					
					var eventName = "Send "+valuableQty +" of "+commodity+ " to "+ receiver;
					var value = "Transaction failed; user poor";
					
					var userLog = "{'clientTimeStamp':'"+dateTime+"','eventName':'"+eventName+"','value':'"+value+"'}";
					
					$.ajax({
						url : "UserLogServlet",
						type : 'POST',
						contentType : 'application/json',
						data : userLog,

						success : function(data) {
							console.log("Communication successful");
							console.log(data);
							var type = typeof (data);
							console.log(type);
						},

						error : function(xhr, ajaxOptions, thrownError) {
							if (xhr.status == 200) {
								console.log("Failed");
							} 
						}
					});

					//-----------------------------userlogdetails end---------------------------
				
				}
			},

			error : function(xhr, ajaxOptions, thrownError) {
				if (xhr.status == 200) {
					console.log("error junk if");
					console.log(xhr);
				} else {
					console.log("error junk else");
					console.log(xhr);
				}
			}
		});
		}
	}
	

//----------------------------------------------------------------------------------------------------------
//---changeQty is to change the amounts of commodities in both the session user's and the receiver's accounts---

function changeQty(valuableQty, receiver, commodity){
	
	var myStr = '{"valuableQty":"'+valuableQty+'","receiver":"'+receiver+'","commodity":"'+commodity+'"}';
//	console.log("myStr: "+myStr);
	
	
	$.ajax({
		url : "UpdateCommodityQuantity",
		type : 'POST',
		contentType : 'application/json',
		data : myStr,
		dataType : 'text',
		success : function(response) {
			console.log("response:");
			console.log(response);

		},

		error : function(xhr, ajaxOptions, thrownError) {
			if (xhr.status == 200) {
				console.log("error");
				console.log(xhr);
			}
		}
	});
}

//-----------------------------------------------------------------

function stringManufacture(){ 
	
	$.ajax({

		url : "GetAllUsernamesServlet",
		type : 'POST',
		contentType : 'application/json',
		dataType : 'text',
		success : function(response) {
			var type = typeof (response);
			response = JSON.parse(response);
			
			var j = 0;
			
			//The following declarations are to help complete the String format in "<option>...."
			var a = '\"';
			var c = "";
			var d = '\"';
			
			for (i = 0; i < response.length; i++) {

				var jsoni = response[i];
				var userid = jsoni.userid;
				//console.log("userid: "+userid);
				var b = "<option value=\""+userid+"\">"+userid+"</option>"; 
				c = c + b;
			}// end for
			
			var finalString = a + c + d;
			//console.log("finalString: "+ finalString);
			document.getElementById("usersList").innerHTML = finalString;
		},

		error : function(xhr, ajaxOptions, thrownError) {
			if (xhr.status == 200) {
				console.log("error junk if");
				console.log(xhr);
			} else {
				console.log("error junk else");
				console.log(xhr);
			}
		}
	});
  }

//------------------Code to notify users of the changes in their accounts----------

function transactionRequests(){
	
	$
		.ajax({
			url : "AlertNotification",
			type : 'POST',
			contentType : 'application/json',
			data : sessionUser,
			dataType : 'text',
			success : function(response) {
				var type = typeof (response);
				response = JSON.parse(response);
				
				for (i = 0; i < response.length; i++) {
					
					var jsoni = response[i];

					var sender = jsoni.sender;
					var valuableQty = jsoni.valuableQty;
					var commodity = jsoni.commodity;
					var transactionid = jsoni.transactionid;
					
					if (confirm(sender+" has sent you "+valuableQty+" units of "+commodity)) {
						//change alerted to "yes"
						updateAlerted(transactionid);
					}
				}
			}
		});
}

//-----------------------------------------

function killSession(){
	
	if (confirm("Are you sure you want to Log out?!")) {
		
		
	$.ajax({
		url : "LogOut",
		type : 'POST',
		contentType : 'text',
		data : 'yo',
		dataType : 'text',
		success : function(response) {

			var type = typeof (response);
			console.log(type);
			console.log("response: " + response);

		},

		error : function(xhr, ajaxOptions, thrownError) {
			//On error do this
			if (xhr.status == 200) {
				console.log("error");
				console.log(xhr);
				//alert(ajaxOptions);
			} else {
				//alert(xhr.status);
				//alert(thrownError);
			}
		}
	});// end of  AJAX!!!!!!
	
	window.location.href = "SimpleLoginForm.html";
	}
}
//------------------------------------------------
