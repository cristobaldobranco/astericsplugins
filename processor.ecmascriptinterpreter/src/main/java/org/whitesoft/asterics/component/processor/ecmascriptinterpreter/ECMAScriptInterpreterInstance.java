

/*
 *    AsTeRICS - Assistive Technology Rapid Integration and Construction Set
 * 
 * 
 *        d8888      88888888888       8888888b.  8888888 .d8888b.   .d8888b. 
 *       d88888          888           888   Y88b   888  d88P  Y88b d88P  Y88b
 *      d88P888          888           888    888   888  888    888 Y88b.     
 *     d88P 888 .d8888b  888   .d88b.  888   d88P   888  888         "Y888b.  
 *    d88P  888 88K      888  d8P  Y8b 8888888P"    888  888            "Y88b.
 *   d88P   888 "Y8888b. 888  88888888 888 T88b     888  888    888       "888
 *  d8888888888      X88 888  Y8b.     888  T88b    888  Y88b  d88P Y88b  d88P
 * d88P     888  88888P' 888   "Y8888  888   T88b 8888888 "Y8888P"   "Y8888P" 
 *
 *
 *                    homepage: http://www.asterics.org 
 *
 *       This project has been partly funded by the European Commission, 
 *                      Grant Agreement Number 247730
 *  
 *  
 *    License: GPL v3.0 (GNU General Public License Version 3.0)
 *                 http://www.gnu.org/licenses/gpl.html
 * 
 */

package org.whitesoft.asterics.component.processor.ecmascriptinterpreter;


import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import eu.asterics.mw.model.runtime.AbstractRuntimeComponentInstance;
import eu.asterics.mw.model.runtime.IRuntimeEventListenerPort;
import eu.asterics.mw.model.runtime.IRuntimeEventTriggererPort;
import eu.asterics.mw.model.runtime.IRuntimeInputPort;
import eu.asterics.mw.model.runtime.IRuntimeOutputPort;
import eu.asterics.mw.model.runtime.impl.DefaultRuntimeEventTriggererPort;
import eu.asterics.mw.model.runtime.impl.DefaultRuntimeOutputPort;

/**
 * 
 * <Describe purpose of this module>
 * 
 * 
 *  
 * @author Christoph Weiss [christoph.weiss@gmail.com]
 *         Date: 
 *         Time: 
 */
public class ECMAScriptInterpreterInstance extends AbstractRuntimeComponentInstance
{
	final IRuntimeInputPort [] ipInputPorts  = new IRuntimeInputPort[NUMBER_OF_INPUTS];
	final IRuntimeOutputPort [] opOutputPorts = new IRuntimeOutputPort[NUMBER_OF_OUTPUTS];
	// Usage of an output port e.g.: opMyOutPort.sendData(ConversionUtils.intToBytes(10)); 

	final IRuntimeEventListenerPort  [] elpEventListenerPorts = new IRuntimeEventListenerPort[NUMBER_OF_EVENT_INPUTS]; 
	final IRuntimeEventTriggererPort [] etpEventTriggerPorts = new IRuntimeEventTriggererPort[NUMBER_OF_EVENT_OUTPUTS];
	// Usage of an event trigger port e.g.: etpMyEtPort.raiseEvent();

	String propScriptname = "";

	// declare member variables here

	ScriptEngine engine;
	Object scriptclass = null;	
    
    static final int NUMBER_OF_INPUTS = 8;
    static final int NUMBER_OF_OUTPUTS = 8;
    static final int NUMBER_OF_EVENT_INPUTS = 8;
    static final int NUMBER_OF_EVENT_OUTPUTS = 8;
    
   /**
    * The class constructor.
    */
    public ECMAScriptInterpreterInstance()
    {

    	for (int i = 0; i < ipInputPorts.length; i++)
    	{
    		ipInputPorts[i] = new InputPort(i);
    	}

    	for (int i = 0; i < opOutputPorts.length; i++)
    	{
    		opOutputPorts[i] = new DefaultRuntimeOutputPort();
    	}
    	

    	for (int i = 0; i < elpEventListenerPorts.length; i++)
    	{
    		elpEventListenerPorts[i] = new EventListenerPort(i);
    	}
    	
    	for (int i = 0; i < etpEventTriggerPorts.length; i++)
    	{
    		etpEventTriggerPorts[i] = new DefaultRuntimeEventTriggererPort();
    	}
    	
    }

   /**
    * returns an Input Port.
    * @param portID   the name of the port
    * @return         the input port or null if not found
    */
    public IRuntimeInputPort getInputPort(String portID)
    {
		if (portID.startsWith("inputPort"))
		{
			String strstr = portID.replace("inputPort", "");
			int idx = Integer.parseInt(strstr);
			return ipInputPorts[idx - 1];
		}

		return null;
	}

    /**
     * returns an Output Port.
     * @param portID   the name of the port
     * @return         the output port or null if not found
     */
    public IRuntimeOutputPort getOutputPort(String portID)
	{
		if (portID.startsWith("outputPort"))
		{
			String strstr = portID.replace("outputPort", "");
			int idx = Integer.parseInt(strstr);
			return opOutputPorts[idx - 1];
		}

		return null;
	}

    /**
     * returns an Event Listener Port.
     * @param eventPortID   the name of the port
     * @return         the EventListener port or null if not found
     */
    public IRuntimeEventListenerPort getEventListenerPort(String eventPortID)
    {
		if (eventPortID.startsWith("elpPort"))
		{
			String strstr = eventPortID.replace("elpPort", "");
			int idx = Integer.parseInt(strstr);
			return elpEventListenerPorts[idx - 1];
		}
        return null;
    }

    /**
     * returns an Event Triggerer Port.
     * @param eventPortID   the name of the port
     * @return         the EventTriggerer port or null if not found
     */
    public IRuntimeEventTriggererPort getEventTriggererPort(String eventPortID)
    {
		if (eventPortID.startsWith("etpPort"))
		{
			String strstr = eventPortID.replace("etpPort", "");
			int idx = Integer.parseInt(strstr);
			return etpEventTriggerPorts[idx - 1];
		}
        return null;
    }
		
    /**
     * returns the value of the given property.
     * @param propertyName   the name of the property
     * @return               the property value or null if not found
     */
    public Object getRuntimePropertyValue(String propertyName)
    {
		if ("scriptname".equalsIgnoreCase(propertyName))
		{
			return propScriptname;
		}

        return null;
    }

    /**
     * sets a new value for the given property.
     * @param propertyName   the name of the property
     * @param newValue       the desired property value or null if not found
     */
    public Object setRuntimePropertyValue(String propertyName, Object newValue)
    {
		if ("scriptname".equalsIgnoreCase(propertyName))
		{
			final Object oldValue = propScriptname;
			propScriptname = (String) newValue;
			return oldValue;
		}
 
        return null;
    }

     /**
      * Input Ports for receiving values.
      */
    class InputPort implements IRuntimeInputPort
    {
    	int index; 
    	
    	public InputPort(int index)
    	{
    		this.index = index;
    	}

		@Override
		public void receiveData(byte[] data) 
		{
			if (scriptclass != null)
			{
				Invocable inv = (Invocable) engine;
				try {
					inv.invokeMethod(scriptclass, "dataInput", index, new String(data));
				} catch (NoSuchMethodException | ScriptException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void startBuffering(AbstractRuntimeComponentInstance c,
				String portID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void stopBuffering(AbstractRuntimeComponentInstance c,
				String portID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isBuffered() {
			return false;
		}
    };
 
     /**
      * Event Listerner Ports.
      */
	class EventListenerPort implements IRuntimeEventListenerPort
	{
		int index; 
		
		public EventListenerPort(int idx)
		{
			this.index = idx;
		}
		
		public void receiveEvent(final String data)
		{
			if (scriptclass != null)
			{
				Invocable inv = (Invocable) engine;
				try {
					inv.invokeMethod(scriptclass, "eventInput", index);
				} catch (NoSuchMethodException | ScriptException e) {
					e.printStackTrace();
				}
			}
		}
	};


	// the script is provided with the vars:
	// output:   an array of size 8 representing 8 IRuntimeOutputPorts
	// eventout: an array of size 8 representing 8 IRuntimeEventTriggererPorts
	


     /**
      * called when model is started.
      */
      @Override
      public void start()
      {
          ScriptEngineManager factory = new ScriptEngineManager();
          // create JavaScript engine
          engine = factory.getEngineByName("JavaScript");
          engine.put("output", opOutputPorts);
          engine.put("eventout", etpEventTriggerPorts);

          try {
        	  System.out.println("Opening " + propScriptname + " ...");
        	
			engine.eval(new FileReader(propScriptname));
			
			scriptclass = engine.get("scriptclass");
			
          } catch (FileNotFoundException | ScriptException e) {
        	  e.printStackTrace();
          }          
          super.start();
      }

     /**
      * called when model is paused.
      */
      @Override
      public void pause()
      {
          super.pause();
      }

     /**
      * called when model is resumed.
      */
      @Override
      public void resume()
      {
          super.resume();
      }

     /**
      * called when model is stopped.
      */
      @Override
      public void stop()
      {
          super.stop();
  		  engine = null;
      }
}