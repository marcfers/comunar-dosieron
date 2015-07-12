package br.edu.furb.filesharing;

final class FilesharingPluginVersion {

	private static final String REVISION = "@custom@";
	private static final short MAJOR = 1;
	private static final short MINOR = 2;

	public static String getVersion() {
		return (FilesharingPluginVersion.MAJOR + "." + FilesharingPluginVersion.MINOR);
	}

	public static long getRealVersion() {
		return FilesharingPluginVersion.MAJOR * 10000 + FilesharingPluginVersion.MINOR;
	}

	public static String getSvnRevision() {
		return FilesharingPluginVersion.REVISION;
	}
}
