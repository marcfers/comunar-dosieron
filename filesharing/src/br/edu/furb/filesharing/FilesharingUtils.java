package br.edu.furb.filesharing;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.json.JSONObject;

import br.edu.furb.filesharing.definition.FilesharingStatus;
import freenet.support.io.Closer;

public final class FilesharingUtils {

	private FilesharingUtils() {
		// class of utilitaries functions.
	}

	public static byte[] getInputBuffer(final InputStream inputStream) throws IOException {
		if (inputStream == null) {
			return new byte[0];
		}
		final byte[] inputBuffer = new byte[inputStream.available()];
		inputStream.read(inputBuffer);
		return inputBuffer;
	}

	public static String loadFileContent(final String fileName) throws IOException {
		final URL resource = FilesharingUtils.class.getResource(fileName);
		if (resource == null) {
			throw new FileNotFoundException("File not found: " + fileName);
		}
		InputStream inputStream = null;
		try {
			inputStream = resource.openStream();
			return new String(FilesharingUtils.getInputBuffer(inputStream));
		} finally {
			Closer.close(inputStream);
		}
	}

	public static String beanToJson(final Object bean) {
		return new JSONObject(bean).toString();
	}

	public static void appendJsonStringProperty(final StringBuilder sb, final String property, final String value, final boolean isAspas, final boolean isSeparator) {
		sb.append("\"");
		sb.append(property);
		sb.append("\"");
		sb.append(":");
		if (isAspas) {
			sb.append("\"");
		}
		sb.append(value);
		if (isAspas) {
			sb.append("\"");
		}
		if (isSeparator) {
			sb.append(",");
		}
	}

	public static String exceptionToJson(final Exception exception) {
		exception.printStackTrace();
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		FilesharingUtils.appendJsonStringProperty(sb, "errorMessage", exception.getMessage(), true, true);
		FilesharingUtils.appendJsonStringProperty(sb, "status", new JSONObject(FilesharingStatus.WITH_ERROR).toString(), false, false);
		sb.append("}");
		return sb.toString();
	}

	public static void openSharedFile(final String filePath) throws IOException {
		Desktop.getDesktop().open(new File(filePath));
	}

	public static void showSharedFileInFolder(final String filePath) throws IOException {
		// TODO Dessa forma está "amarrado" ao Windows...
		new ProcessBuilder("explorer", "/select,\"" + new File(filePath).getPath() + "\"").start();
	}

}
