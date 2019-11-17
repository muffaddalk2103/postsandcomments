/**
 *
 */
package com.loyaltyone.postsandcomments.model;

/**
 * @author muffa
 *
 */
public class PagingResponse {

	private long draw;
	private long recordsTotal;
	private long recordsFiltered;
	private Object data;
	private String error;

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @return the draw
	 */
	public long getDraw() {
		return draw;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @return the recordsFiltered
	 */
	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	/**
	 * @return the recordsTotal
	 */
	public long getRecordsTotal() {
		return recordsTotal;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @param draw the draw to set
	 */
	public void setDraw(long draw) {
		this.draw = draw;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @param recordsFiltered the recordsFiltered to set
	 */
	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	/**
	 * @param recordsTotal the recordsTotal to set
	 */
	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
}
