package de.tyranus.poseries.usecase;

import java.nio.file.*;
import java.util.List;
import java.util.Set;

/**
 * Business logic that is called by the presentation layer.
 * 
 * @author Tim
 * 
 */
public interface UseCaseService {

	/**
	 * Finds a source directory pattern by the selected source dir.
	 * 
	 * @param selectedSrcDir
	 * @return found source directory pattern.
	 */
	String findSrcDirPattern(String selectedSrcDir);

	/**
	 * Creates the final source dir from the selected source dir.
	 * The selected source dir is one of the downloaded series directories.
	 * 
	 * @param selectedSrcDir
	 *            one of the downloaded series directories.
	 * @return the final source directory which simply is one directory higher
	 *         than the selected source directory.
	 */
	Path createFinalSrcDir(String selectedSrcDir);

	/**
	 * Returns a list of subdirectories of finalSrcDir that matches the source
	 * directory pattern.
	 * 
	 * @param finalSrcDir
	 *            the finalized source dir.
	 * @param srcDirPattern
	 *            The source directory pattern.
	 * @return Set of matching files.
	 * @throws UseCaseServiceException
	 *             if an IOException while walking through the directories.
	 */
	Set<Path> findMatchingSrcDirs(Path finalSrcDir, String srcDirPattern) throws UseCaseServiceException;
	
	/**
	 * Formats the found file list.
	 * @param files the file list
	 * @return the formatted file list.
	 */
	String formatFileList(Set<Path> files);	

	/**
	 * Returns an overlieap of the found video extensions from the list of files
	 * and pre configured video extensions.
	 * 
	 * @param files
	 *            List of files found in directories that match the directory
	 *            pattern.
	 * @return List of file extensions that belong to found files and that are
	 *         configured as multimedia extensions.
	 */
	Set<String> getFoundVideoExtensions(Set<Path> files);

	/**
	 * Gets a string representation of the file extensions
	 * 
	 * @param extensions
	 *            the file extensions
	 * @return string representation.
	 */
	String explodeVideoExtensions(Set<String> extensions);
	
	/**
	 * Gets a set of file extensions from its string representation.
	 * @param extensions the string representation.
	 * @return the file extensions.
	 */
	Set<String> implodeVideoExtensions(String extensions);

	/**
	 * Post processes the subdirectories of src matching the pattern und
	 * copy/moves them to dst depending to the mode.
	 * 
	 * @param src
	 *            source directory to find subdirectories by the pattern
	 * @param dst
	 *            destination directory to insert files matching the pattern
	 * @param pattern
	 * @param mode
	 * @throws UseCaseServiceException
	 */
	void postProcessSeries(String src, String dst, String pattern, PostProcessMode mode) throws UseCaseServiceException;

}
