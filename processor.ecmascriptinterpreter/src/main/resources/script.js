function scriptclass() { // An object literal
	// the script is provided with the vars:
	// output:   an array of size 8 representing 8 IRuntimeOutputPorts
	// eventout: an array of size 8 representing 8 IRuntimeEventTriggererPorts
	
	
	
	
	
	
	
	this.dataInput = function(in_nb, in_data) {
		in_data = in_data.concat(" changed");
		output[in_nb].sendData(in_data);
	};
	
	this.eventInput = function(ev_nb) {
		eventout[ev_nb].raiseEvent();
	};
};
