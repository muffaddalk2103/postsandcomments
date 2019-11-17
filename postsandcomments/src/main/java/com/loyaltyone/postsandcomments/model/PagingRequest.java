/**
 *
 */
package com.loyaltyone.postsandcomments.model;

import java.util.List;
import java.util.Map;

/**
 * @author muffa
 *
 */
public class PagingRequest {

	private int draw;
	private List<Column> columns;
	private int start;
	private int length;
	private List<Order> order;
	private Map<String, String> search;

	/**
	 * @return the columns
	 */
	public List<Column> getColumns() {
		return columns;
	}

	/**
	 * @return the draw
	 */
	public int getDraw() {
		return draw;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the order
	 */
	public List<Order> getOrder() {
		return order;
	}

	/**
	 * @return the search
	 */
	public Map<String, String> getSearch() {
		return search;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	/**
	 * @param draw the draw to set
	 */
	public void setDraw(int draw) {
		this.draw = draw;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(List<Order> order) {
		this.order = order;
	}

	/**
	 * @param search the search to set
	 */
	public void setSearch(Map<String, String> search) {
		this.search = search;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}
}
