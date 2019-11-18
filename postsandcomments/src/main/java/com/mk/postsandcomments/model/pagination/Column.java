/**
 *
 */
package com.mk.postsandcomments.model.pagination;

import java.util.Map;

/**
 * @author muffa
 *
 */
public class Column {
	private String data;
	private String name;
	private boolean searchable;
	private boolean orderable;
	private Map<String, String> search;

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the search
	 */
	public Map<String, String> getSearch() {
		return search;
	}

	/**
	 * @return the orderable
	 */
	public boolean isOrderable() {
		return orderable;
	}

	/**
	 * @return the searchable
	 */
	public boolean isSearchable() {
		return searchable;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param orderable the orderable to set
	 */
	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}

	/**
	 * @param search the search to set
	 */
	public void setSearch(Map<String, String> search) {
		this.search = search;
	}

	/**
	 * @param searchable the searchable to set
	 */
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	@Override
	public String toString() {
		return "Column [data=" + data + ", name=" + name + ", searchable=" + searchable + ", orderable=" + orderable
				+ ", search=" + search + "]";
	}
}
