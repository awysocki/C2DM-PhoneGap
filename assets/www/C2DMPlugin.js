
/**
 *  
 * @return Instance of C2DM
 */
var C2DM = function() { 

}

/**
 * 
 * 
 * 
 */
C2DM.prototype.register = function(senderEmail, eventCallback, successCallback, failureCallback) {

	if ( typeof eventCallback != "string")		// The eventCallback has to be a STRING name not the actual routine like success/fail routines
	{
		var e = new Array();
		e.msg = 'eventCallback must be a STRING name of the routine';
		e.rc = -1;
		failureCallback( e );
		return;
	}
	
    return PhoneGap.exec(successCallback,    	//Callback which will be called when directory listing is successful
    					failureCallback,     	//Callback which will be called when directory listing encounters an error
    					'C2DMPlugin',  			//Telling PhoneGap that we want to run "DirectoryListing" Plugin
    					'register',             //Telling the plugin, which action we want to perform
    					[{ email: senderEmail, ecb : eventCallback }]);        	//Passing a list of arguments to the plugin, 
    											// The ecb variable is the STRING name of your javascript routine to be used for callbacks
    											// You can add more to validate that eventCallback is a string and not an object
};


C2DM.prototype.unregister = function( successCallback, failureCallback ) {

	
    return PhoneGap.exec(successCallback,    	//Callback which will be called when directory listing is successful
    					failureCallback,     	//Callback which will be called when directory listing encounters an error
    					'C2DMPlugin',  			//Telling PhoneGap that we want to run "DirectoryListing" Plugin
    					'unregister',             //Telling the plugin, which action we want to perform
    					[{ }]);        	//Passing a list of arguments to the plugin, 
};



/**
 */


PhoneGap.addConstructor(function() {
	//Register the javascript plugin with PhoneGap
	PhoneGap.addPlugin('C2DM', new C2DM());
	
	//Register the native class of plugin with PhoneGap
	PluginManager.addService("C2DMPlugin","com.plugin.C2DM.C2DMPlugin");
	
	//alert( "added Service C2DMPlugin");
});