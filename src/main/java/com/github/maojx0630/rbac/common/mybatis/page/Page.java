package com.github.maojx0630.rbac.common.mybatis.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.github.maojx0630.rbac.common.config.global.GlobalStatic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @see IPage 对象返回时的二次封装
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-26 12:09 
 */
@ApiModel("分页对象")
public class Page<T> implements IPage<T> {

	@ApiModelProperty(value = "当前页码", position = 1)
	private Long current;

	@ApiModelProperty(value = "页长", position = 2)
	private Long size;

	@ApiModelProperty(value = "总条数", position = 3)
	private Long total;

	@ApiModelProperty(value = "总页数", position = 4)
	private Long pages;

	@ApiModelProperty(value = "是否最后一页", position = 5)
	private boolean last;

	@ApiModelProperty(value = "是否是第一页", position = 6)
	private boolean first;

	@ApiModelProperty(value = "记录", position = 7)
	private List<T> records;
	//仅用于忽略返回值无实际作用
	@ApiModelProperty(hidden = true)
	private transient T param;

	//仅用于忽略返回值无实际作用
	@ApiModelProperty(hidden = true)
	private transient boolean hitCount;

	//仅用于忽略返回值无实际作用
	@ApiModelProperty(hidden = true)
	private transient boolean searchCount;

	public Page() {

	}

	public Page(Long current, Long size, T t) {
		this.current = current;
		this.size = size;
		this.param = t;
	}

	@Override
	public List<OrderItem> orders() {
		return null;
	}

	@Override
	public List<T> getRecords() {
		return records;
	}

	@Override
	public IPage<T> setRecords(List<T> records) {
		this.records = records;
		return this;
	}

	@Override
	public long getTotal() {
		return total;
	}

	@Override
	public IPage<T> setTotal(long total) {
		this.total = total;
		return this;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public IPage<T> setSize(long size) {
		this.size = size;
		return this;
	}

	@Override
	public long getCurrent() {
		return current;
	}

	@Override
	public IPage<T> setCurrent(long current) {
		this.current = current;
		return this;
	}

	@Override
	public long getPages() {
		if (getSize() == 0) {
			return 0L;
		}
		long pages = getTotal() / getSize();
		if (getTotal() % getSize() != 0) {
			pages++;
		}
		return pages;
	}

	public boolean isLast() {
		return current == getPages();
	}


	public boolean isFirst() {
		return current == GlobalStatic.DEFAULT_CURRENT;
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}

}
