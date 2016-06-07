package platypus.util.general;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A collection of methods for manipulating files.
 *
 * @author Jingchen Xu
 */
public class PFileUtils {

	private PFileUtils() {
	}

	/**
	 * Recursively lists all children of a specified directory. If the specified
	 * directory is empty, an empty list will be returned. If the argument is
	 * not a directory, a list containing only that file will be returned.
	 *
	 * @param file
	 *            the directory to be searched
	 * @return a list of all files in the directory, including sub-directories
	 */
	public static ArrayList<File> deepListFiles(File file) {

		ArrayList<File> fileList = new ArrayList<File>();

		if (file.isDirectory()) {
			File[] children = file.listFiles();
			for (File child : children) {
				fileList.addAll(deepListFiles(child));
			}
		} else {
			fileList.add(file);
		}

		return fileList;
	}

	/**
	 * Recursively lists all children of a specified directory matching the
	 * specified filter. If the specified directory contains no such files, an
	 * empty list will be returned. If the argument is not a directory but
	 * matches the filter, a list containing only that file will be returned.
	 *
	 * @param file
	 *            the directory to be searched
	 * @param filter
	 *            the filter to apply on the search
	 * @return a list of all files matching the filter in the directory,
	 *         including sub-directories
	 */
	public static ArrayList<File> deepListFiles(File file, FileFilter filter) {

		ArrayList<File> fileList = new ArrayList<File>();

		if (file.isDirectory()) {
			File[] children = file.listFiles();
			for (File child : children) {
				fileList.addAll(deepListFiles(child));
			}
		} else if (filter.accept(file)) {
			fileList.add(file);
		}

		return fileList;
	}

	/**
	 * Sets the value of a user defined file attribute for a specified file. If
	 * a previous value exists, it is overwritten and that value is returned.
	 *
	 * @param file
	 *            the file to add the attribute to
	 * @param attribute
	 *            the name of the attribute
	 * @param value
	 *            the value of the attribute. If null, the attribute will be
	 *            deleted instead.
	 * @return the previous value of the attribute. If no such value existed,
	 *         null is returned
	 */
	public static String setUserDefinedFileAttribute(File file, String attribute, String value) {

		if (!file.exists()) {
			throw new IllegalArgumentException("Specified file does not exist");
		}

		Path path = Paths.get(file.getAbsolutePath());
		UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);

		// read previous value
		try {
			String previousValue = null;
			if (view.size(attribute) > 0) {
				ByteBuffer buf = ByteBuffer.allocate(view.size(attribute));
				view.read(attribute, buf);
				buf.flip();
				previousValue = Charset.defaultCharset().decode(buf).toString();

				System.out.printf("Replaced previous value: %s\n", previousValue);
			}

			return previousValue;
		} catch (NoSuchFileException e) {
			// no previous value
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			// write new value
			try {
				if (value == null) {
					view.delete(attribute);
				} else {
					view.write(attribute, Charset.defaultCharset().encode(value));
				}

			} catch (NoSuchFileException e) {
				System.err.println("Attribute not deleted; " + attribute + " not found");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Batch-sets multiple user defined file attribute on a specified file.
	 * Behaves in the same way as setUserDefinedFileAttribute().
	 *
	 * @param file
	 *            the file to add attributes to
	 * @param attributes
	 *            a map containing all the attribute-value pairs to be written
	 * @return
	 */
	public static Map<String, String> setUserDefinedFileAttributes(File file, Map<String, String> attributes) {

		Map<String, String> previousVals = new HashMap<String, String>();

		for (String key : attributes.keySet()) {
			String previousVal = setUserDefinedFileAttribute(file, key, attributes.get(key));
			previousVals.put(key, previousVal);
		}

		return previousVals;
	}

	/**
	 * Reads the value of a specified user defined file attribute from a file.
	 *
	 * @param file
	 *            the file to read the attribute from
	 * @param attribute
	 *            the attribute to read
	 * @return the value of that attribute
	 */
	public static String readUserDefinedFileAttribute(File file, String attribute) {

		if (!file.exists()) {
			return null;
		}

		Path path = Paths.get(file.getAbsolutePath());
		UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);

		try {
			ByteBuffer buf = ByteBuffer.allocate(view.size(attribute));
			view.read(attribute, buf);
			buf.flip();
			String value = Charset.defaultCharset().decode(buf).toString();

			return value;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Reads all user defined file attributes attached to a file.
	 *
	 * @param file
	 *            the file to read attributes from
	 * @return a map containing all the attribute-value pairs attached to the
	 *         file
	 */
	public static Map<String, String> readUserDefinedFileAttributes(File file) {

		if (!file.exists()) {
			return null;
		}

		Path path = Paths.get(file.getAbsolutePath());
		UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);

		Map<String, String> output = new HashMap<String, String>();
		try {
			for (String attribute : view.list()) {
				// read each attribute
				ByteBuffer buf = ByteBuffer.allocate(view.size(attribute));
				view.read(attribute, buf);
				buf.flip();
				String value = Charset.defaultCharset().decode(buf).toString();

				// add to the map
				output.put(attribute, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}

	/**
	 * Deletes a specified user defined file attribute from a file.
	 *
	 * @param file
	 *            the file to delete the attribute from
	 * @param attribute
	 *            the attribute to delete
	 */
	public static void clearUserDefinedFileAttribute(File file, String attribute) {
		if (!file.exists()) {
			return;
		}

		Path path = Paths.get(file.getAbsolutePath());
		UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
		try {
			view.delete(attribute);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Deletes all user defined file attributes attached to a file.
	 *
	 * @param file
	 *            the file to delete attributes from
	 */
	public static void clearUserDefinedFileAttributes(File file) {
		if (!file.exists()) {
			return;
		}

		Path path = Paths.get(file.getAbsolutePath());
		UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
		try {
			for (String attribute : view.list()) {
				view.delete(attribute);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
