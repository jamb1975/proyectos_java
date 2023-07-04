package isoftconta;

import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public abstract class ContabilidadFXServicios extends ServicioBase {

	private static final AreaInfo areaInfo = new AreaInfo();
	
	@Override
	public String getAreaNombre() {
		return "IsoftConta";
	}

	@Override
	public String getAreaVersion() {
		return areaInfo.getVersion();
	}
	
	
	private static class AreaInfo {
		
		private String version;
		
		
		public AreaInfo() {
			
			InputStream s = getClass().getClassLoader().getResourceAsStream(
					"META-INF/MANIFEST.MF");
			
			try {
				Manifest manifest = new Manifest(s);
				Attributes attr = manifest.getMainAttributes();
				version = attr.getValue("Implementation-Version");
			} catch (Throwable e) {
				System.out.println("Unable to load project version for ControlsFX "
				        + "samples project as the manifest file can't be read "
				        + "or the Implementation-Version attribute is unavailable.");
				version = "";
			}
		}
		
		public String getVersion() {
			return version;
		}
	}

}

