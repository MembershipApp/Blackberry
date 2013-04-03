//login header template

window.loginHeaderTmpl = '<div id="LoginHeader">'+
			'<label id="Controltxt">Control</label><label id="Riskstxt">Risks</label>'+
			'<label id="internationaltxt">International<br/><span id="SOStxt">SOS</span></label>'+
			'</div>';


window.videoTmpl =  '<div id="videotour">'+
			'<img src="thumbnail.png" onclick="playVideo()">  </img>'+
			'<!--<video src="sample.3gp" controls="true" type="video/mov">-->'+
			'<br/>'+
			'<label>Play video to learn more about International SOS Membership App</label>'+
			'</div>';

window.loginFormTmpl = '<div id="LoginContent">'+
			'<div>'+
			'<label>Please enter your International SOS Membership Number provided by your organizatioin </label>'+
			'<input type="text" placeholder="Enter Member No." value="bbtest1" id="membership_id" required />'+
			'</div>'+
			'<div id="rememberme">'+
			'<input type="checkbox" id="remembermecheckbox"/>'+
			'<label>Remember Me</label>'+
			'<br/>'+
			'</div>'+
			'<div id="btnloginhelp">'+
			'<button id="loginBtn" onclick="loginBtnEvent()">Login</button>'+
			'<button id="helpBtn" onclick="helpBtnEvent()">Help</button>'+
			'</div>'+
			'</div>';

window.dashBoardTmpl =  '<div id="LoginHeader">'+
			'<label id="Controltxt">Control</label><label id="Riskstxt">Risks</label>'+
			'<label id="internationaltxt">International<br/><span id="SOStxt">SOS</span></label>'+
			'</div>'+
			'<table id="changelocation">'+
			'<tr>'+
			'<td ><img src="" /></td>'+
			'<td> <label id="smalltext">We have detected You\'re in</label> <br/> <label id="countryName"> </label></td>'+
			'<td onclick="comingFeature()">CHANGE<br/>LOCATION</td>'+
			'<td><img src="img/changelocationarrow.png" /></td>'+
			'</tr>'+
			'</table>'+
			'</div>'+
			'<div id="dashboarditems">'+
			'<ul id="AlertsGuides">'+
			'<li id="dashboard-a"><a onClick="onAlertClick()" class="ui-link"><img src="img/alert.png" />'+
			'<br/>'+
			'<label>ALERTS</label></a>'+
			'</li>'+
			'<li id="dashboard-b">'+
			'<a onclick="onCheckin()" class="ui-link"><img src="img/checkin.png" />'+
			'<br/>'+
			'<label>CHECK IN</label></a>'+
			'</li>'+
			'</ul>'+
			'<ul id="CheckinClinic">'+
			'<li id="dashboard-c">'+
			'<a onclick="getExternalLink()" class="ui-link"> <img src="img/guide.png" />'+
			'<br/>'+
			'<label>GUIDES</label>'+
			'</a>'+
			'</li>'+
			'<li id="dashboard-d">'+
			'<a onclick="getExternalLink()" class="ui-link">'+
			'<img src="img/clinic.png" />'+
			'<br/>'+
			'<label>CLINICS</label>'+
			'</a>'+
			'</li>'+
			'</ul>'+
			'</div>'+
			'<div id="bottommenu" >'+
			'<ul>'+
			'<li>'+
			'<a onclick="comingFeature()"><img src="img/location.png" />'+
			'<br/>'+
			'Location</a>'+
			'</li>'+
			'<li>'+
			'<a onclick="comingFeature()"><img src="img/globalpulse.png" />'+
			'<br/>'+
			'Global Pulse</a>'+
			'</li>'+
			'<li>'+
			'<a onclick="comingFeature()"><img src="img/call.png" />'+
			'<br/>'+
			'Call Us</a>'+
			'</li>'+
			'<li>'+
			'<a onclick="comingFeature()"><img src="img/settings.png" />'+
			'<br/>'+
			'Settings</a>'+
			'</li>'+
			'<li>'+
			'<a onclick="comingFeature()"><img src="img/more.png" />'+
			'<br/>'+
			'More</a>'+
			'</li>'+
			'</ul>'+
			'</div><!-- /footer -->'+
			'</div>';



window.alertInTmpl = '<section data-role="header" data-position="inline" >'+
			'<div id="CheckinHeader">'+
			'<a onclick="goBack()">'+
			'<div class="backbutton" >'+
			'</div>'+
			'</a>'+
			'<span>Alerts</span>'+
			'</div>'+
			'</section>'+
			'<!-- End Header -->'+
			'<!-- Begin Main Content -->'+
			
			'<div class="content-primary" id="alertWrapper">'+
			'<ul data-role="listview" id="AlertsList" style="margin-left:0px;">'+
			'</ul>'+
			'</div>';

window.checkInTmpl = '<section data-role="header" data-position="inline" >'+
			'<div id="CheckinHeader">'+
			'<a onclick="goBack()">'+
			'<div class="backbutton" >'+
			'</div>'+
			'</a>'+
			'<span>Check In</span>'+
			'</div>'+
			'</section>'+
			'<!-- End Header -->'+
			'<!-- Begin Main Content -->'+
			'<!-- Map Content -->'+
			'<div>'+
			'<div id="map_canvas" style="height:200px">'+
				'<img id="image"/> '+
			'</div>'+
			'</div>'+
			'<div id="CheckinLocationDetails">'+
			'<!--'+
			'<div id="CheckinLocationName">'+
			'<h1>The Netherlands</h1>'+
			'<h2>Veenendaal</h2>'+
			'</div>'+
			'<img src="images/netherland_checkin_flag.gif" />'+
			'-->'+
			'<div id="locationHeader">'+
				'<strong>Location: </strong>'+

				'<label id="country"> </label>'+
				'<br/>'+
				'Latitude: <label id="latitudeValue"> </label>'+
				'<br/>'+
				'Longitude: <label id="longitudeValue"></label>'+
			'</div>'+
			'<div id="btnCheckIn" >'+
				'<a onclick="onCheckInClick()">Check In Here</a>'+
			'</div>'+
			'<!-- End Main Content -->';

window.checkinSuccessfulTmpl='<div  id="CheckinSuccessful" data-position="fixed" >'+
			'<img src="img/check.png" />'+
			'<p>You\'ve successfully checked in.</p>'+
			'<a onclick="successCheckIn()">Done</a>'+
			'</div>';




