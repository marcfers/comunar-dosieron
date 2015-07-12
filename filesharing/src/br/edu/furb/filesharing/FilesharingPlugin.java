package br.edu.furb.filesharing;

import java.io.IOException;

import br.edu.furb.filesharing.ui.FilesharingWebInterface;
import freenet.pluginmanager.FredPlugin;
import freenet.pluginmanager.FredPluginHTTP;
import freenet.pluginmanager.FredPluginRealVersioned;
import freenet.pluginmanager.FredPluginThreadless;
import freenet.pluginmanager.FredPluginVersioned;
import freenet.pluginmanager.PluginHTTPException;
import freenet.pluginmanager.PluginRespirator;
import freenet.support.api.HTTPRequest;

public final class FilesharingPlugin implements FredPluginVersioned, FredPluginRealVersioned, FredPlugin, FredPluginThreadless, FredPluginHTTP {

	public static final String PLUGIN_URI = "/Filesharing/";

	private static final String REDIRECT_PAGE_HTML = "/br/edu/furb/filesharing/ui/redirectPage.html";

	@Override
	public String getVersion() {
		return FilesharingPluginVersion.getVersion() + " " + FilesharingPluginVersion.getSvnRevision();
	}

	@Override
	public long getRealVersion() {
		return FilesharingPluginVersion.getRealVersion();
	}

	private FilesharingWebInterface webInterface;

	@Override
	public void runPlugin(final PluginRespirator pluginRespirator) {
		this.webInterface = new FilesharingWebInterface(pluginRespirator);
		this.webInterface.initialize();
	}

	@Override
	public void terminate() {
		this.webInterface.terminate();
	}

	@Override
	public String handleHTTPGet(final HTTPRequest request) throws PluginHTTPException {
		this.webInterface.setFormPassword(request.getParam("formPassword"));
		try {
			return FilesharingUtils.loadFileContent(FilesharingPlugin.REDIRECT_PAGE_HTML);
		} catch (final IOException e) {
			throw new PluginHTTPException(e.getMessage(), request.getPath());
		}
	}

	@Override
	public String handleHTTPPost(final HTTPRequest request) throws PluginHTTPException {
		return null;
	}

}
