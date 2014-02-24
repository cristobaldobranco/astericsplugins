function dataInput (in_nb, in_data) 
{
	in_data = in_data.concat(" changed");
};

function eventInput(ev_nb) {
	eventout[ev_nb].raiseEvent();
};


function clazz(dataout, evout) {
 
	this.dataInput = function(in_nb, in_data) {
		in_data = in_data.concat(" changed");
		output[in_nb].sendData(in_data);
	};
	
	this.eventInput = function(ev_nb) {
		eventout[ev_nb].raiseEvent();
	};
};

var scriptclass = new clazz();
