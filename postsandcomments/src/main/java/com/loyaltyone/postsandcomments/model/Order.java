/**
 *
 */
package com.loyaltyone.postsandcomments.model;

/**
 * @author muffa
 *
 */
public class Order {
	private String column;
	private String dir;

	/**
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
}
