/**
 *
 */
package com.mk.postsandcomments.model.pagination;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * @author muffa
 *
 */
public class PagingRequest {
	@NotNull
	@Digits(integer = Integer.MAX_VALUE, fraction = 0)
	private Integer draw;
	private List<Column> columns;
	@NotNull
	@Digits(integer = Integer.MAX_VALUE, fraction = 0)
	private Integer start;
	@NotNull
	@Digits(integer = Integer.MAX_VALUE, fraction = 0)
	private Integer length;
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
	public Integer getDraw() {
		return draw;
	}

	/**
	 * @return the length
	 */
	public Integer getLength() {
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
	public Integer getStart() {
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
	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
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
	public void setStart(Integer start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "PagingRequest [draw=" + draw + ", columns=" + columns + ", start=" + start + ", length=" + length
				+ ", order=" + order + ", search=" + search + "]";
	}
}
