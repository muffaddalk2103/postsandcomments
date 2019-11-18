/**
 *
 */
package com.mk.postsandcomments.model.pagination;

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

	@Override
	public String toString() {
		return "Order [column=" + column + ", dir=" + dir + "]";
	}
}
