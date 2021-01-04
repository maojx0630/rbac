package com.github.maojx0630.rbac.common.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.util.Date;

/**
 * 解析雪花id获取雪花id的信息
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-23 06:57
 */
@SuppressWarnings("all")
public class IdUtils {

	/**
	 * 2010-11-04 09:42:54
	 * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
	 */
	private static final long twepoch = 1288834974657L;
	/**
	 * 机器标识位数
	 */
	private static final long workerIdBits = 5L;
	private static final long datacenterIdBits = 5L;
	private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
	private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	/**
	 * 毫秒内自增位
	 */
	private static final long sequenceBits = 12L;
	private static final long workerIdShift = sequenceBits;
	private static final long datacenterIdShift = sequenceBits + workerIdBits;
	/**
	 * 时间戳左移动位
	 */
	private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;


	/**
	 * 根据Snowflake的ID，获取机器id
	 *
	 * @param id snowflake算法生成的id
	 * @return 所属机器的id
	 */
	public static long getWorkerId(long id) {
		return id >> workerIdShift & ~(-1L << workerIdBits);
	}

	/**
	 * 根据Snowflake的ID，获取数据中心id
	 *
	 * @param id snowflake算法生成的id
	 * @return 所属数据中心
	 */
	public static long getDataCenterId(long id) {
		return id >> datacenterIdShift & ~(-1L << datacenterIdBits);
	}

	/**
	 *根据Snowflake的ID，获取生成时间
	 *
	 * @param id snowflake算法生成的id
	 * @return 生成的时间
	 */
	public static long getTime(long id) {
		return (id >> timestampLeftShift & ~(-1L << 41L)) + twepoch;
	}

	/**
	 *根据Snowflake的ID，获取生成时间
	 *
	 * @param id snowflake算法生成的id
	 * @return 生成的时间
	 */
	public static Date getDate(long id) {
		return new Date(getTime(id));
	}

	public static Long next() {
		return IdWorker.getId();
	}

	public static String nextStr() {
		return IdWorker.getIdStr();
	}
}
