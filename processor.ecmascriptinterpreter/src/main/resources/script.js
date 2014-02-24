function clazz(dataout, evout) {
 
	this.dataInput = function(in_nb, in_data) {
		in_data = in_data.concat(" changed");
		str = new java.lang.String(in_data);
		output[in_nb].sendData(str.getBytes());
	};
	
	this.eventInput = function(ev_nb) {
		eventout[ev_nb].raiseEvent();
	};
};

var scriptclass = new clazz();
