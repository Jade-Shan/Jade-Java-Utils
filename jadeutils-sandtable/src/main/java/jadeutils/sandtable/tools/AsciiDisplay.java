package jadeutils.sandtable.tools;

import jadeutils.sandtable.SandTableConstants;
import jadeutils.sandtable.model.Area;
import jadeutils.sandtable.model.LandForm;
import jadeutils.sandtable.model.SandTable;

public class AsciiDisplay {

	public static void showSandTable(SandTable sandTable) {
		for (int y = 0; y < sandTable.getWidth(); y++) {
			for (int x = 0; x < sandTable.getWidth(); x++) {
				Area area = sandTable.getArea(x, y);
				String formId = area.getLandformId();
				LandForm lf = SandTableConstants.LAND_FORMS.get(formId);
				System.out.print(lf.getDisplayChar());
			}
			System.out.println();
		}
	}
}
