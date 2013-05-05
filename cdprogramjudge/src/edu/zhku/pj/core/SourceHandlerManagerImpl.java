package edu.zhku.pj.core;

import java.io.File;

public class SourceHandlerManagerImpl implements SourceHandlerManager {
	private Language[] languages;
	private String workFolder;

	@Override
	public void setLanguages(Language[] languages) {
		this.languages = languages;
		for (Language language : languages) {
			if(language.getHandler().getWorkFolder() == null) {
				language.getHandler().setWorkFolder(this.getWorkFolder());
			}
		}
	}

	@Override
	public SourceHandler getSourceHandler(String language) {
		for (Language lan : languages) {
			if(lan.getName().equals(language)) {
				SourceHandler handler = lan.getHandler();
				handler.setLanguageKey(language);
				return handler;
			}
		}
		return null;
	}

	@Override
	public Language[] getLanguages() {
		return this.languages;
	}


	@Override
	public void setWorkFolder(String workFolder) {
	    if(workFolder == null || workFolder.length() == 0 || ".".equals(workFolder)) {
            workFolder = System.getProperty("user.dir") + File.separator + "pj" + File.separator + "work";
        }
	    if(workFolder.length() > 1 || workFolder.startsWith(".")) {
            workFolder = System.getProperty("user.dir") + workFolder.substring(2);
        }
		File file = new File(workFolder);
		if(!file.exists()) {
			file.mkdirs();
		}
		this.workFolder = workFolder;
	}

	@Override
	public String getWorkFolder() {
		return this.workFolder;
	}

	@Override
	public Language getLanguage(String language) {
		for(Language lan : this.languages) {
			if(lan.getName().equals(language)) {
				return lan;
			}
		}
		return null;
	}
	

}
