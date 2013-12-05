package amidst.json;

import java.io.File;
import java.util.ArrayList;

import amidst.Log;
import amidst.Options;
import amidst.Util;

public class JarLibrary {
	public String name;
	public ArrayList<JarRule> rules;
	
	private File file;
	
	public JarLibrary() {
		rules = new ArrayList<JarRule>();
	}
	
	public boolean isActive() {
		// lwjgl/jinput would require natives and is not needed to load AMIDST.
		if (name.contains("lwjgl") || name.contains("jinput") || name.contains("twitch")) return false;
		
		// Currently this just accepts all libraries regardless of the rules.
		return true;
	}
	
	public File getFile() {
		if (file == null) {
			String searchPath = Util.minecraftDirectory + "/libraries/";
			String[] pathSplit = name.split(":");
			pathSplit[0] = pathSplit[0].replace('.', '/');
			for (int i = 0; i < pathSplit.length; i++)
				searchPath += pathSplit[i] + "/";
			File searchPathFile = new File(searchPath);
			if (!searchPathFile.exists()) {
				Log.w("Failed attempt to load library at: " + searchPathFile);
				return null;
			}
			File[] libraryFiles = searchPathFile.listFiles();
			if (libraryFiles.length > 0)
				file = libraryFiles[0];
			else
				Log.w("Attempted to search for file at path: " + searchPath + " but found nothing. Skipping.");
		}
		return file;
	}
}
