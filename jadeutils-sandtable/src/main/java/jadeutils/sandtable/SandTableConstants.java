package jadeutils.sandtable;

import jadeutils.sandtable.model.LandForm;

import java.util.HashMap;
import java.util.Map;

public class SandTableConstants {
	public static Map<String, LandForm> LAND_FORMS;
	static {
		LAND_FORMS = new HashMap<String, LandForm>();
		LandForm f = new LandForm("GRASS", "*", "def.png", 1);
		LAND_FORMS.put(f.getLandformId(), f);
		f = new LandForm("SAND", "x", "def.png", 1);
		LAND_FORMS.put(f.getLandformId(), f);
		f = new LandForm("ROCK", "^", "def.png", 1);
		LAND_FORMS.put(f.getLandformId(), f);
		f = new LandForm("CLIFF", "\\", "def.png", 1);
		LAND_FORMS.put(f.getLandformId(), f);
		f = new LandForm("SWAMP", "#", "def.png", 1);
		LAND_FORMS.put(f.getLandformId(), f);
		f = new LandForm("STREAM", "~", "def.png", 1);
		LAND_FORMS.put(f.getLandformId(), f);
		f = new LandForm("RIVER", "~", "def.png", 1);
		LAND_FORMS.put(f.getLandformId(), f);
		f = new LandForm("SEA", "~", "def.png", 1);
		LAND_FORMS.put(f.getLandformId(), f);
	}

}
