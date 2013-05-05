package edu.zhku.pj.core.handler;

import java.io.File;

import edu.zhku.pj.core.HandleStatus;
import edu.zhku.pj.core.SourceHandler;
import edu.zhku.pj.core.ecd.ECDetectorManager;

public abstract class AbstractSourceHandler implements SourceHandler {
	private String workFolder;
	private String languageKey;
	
	@Override
    public String getLanguageKey() {
        return languageKey;
    }

    @Override
    public void setLanguageKey(String languageKey) {
        this.languageKey = languageKey;
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
    public HandleStatus handle(String sourceStr, String input, String output) {
        String desc = ECDetectorManager.detect(sourceStr, getLanguageKey());
        if(desc == null) {
            HandleStatus hs = HandleStatus.Evil_Code;
            hs.appendInfo(desc);
            return hs;
        }
        return executeHandle(sourceStr, input, output);
    }
    
    /**
     * 真正的
     * @return
     */
    public abstract HandleStatus executeHandle(String sourceStr, String input, String output) ;

}
