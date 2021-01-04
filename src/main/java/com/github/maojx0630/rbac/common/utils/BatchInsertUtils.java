package com.github.maojx0630.rbac.common.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Consumer;

/**
 * 批量插入
 * 使用时需要严格校验数据是否合法
 * <br/>
 * @author MaoJiaXing
 * @date 2020-07-13 15:57 
 */
public class BatchInsertUtils {

	/**
	 * 批量插入，直接返回
	 * <br/>
	 * @param threshold 阀值
	 * @param list 插入的集合
	 * @param consumer 插入操作
	 * @author MaoJiaXing
	 * @date 2020-07-14 09:16
	 */
	public static <T> void asyncInsert(int threshold, List<T> list, Consumer<List<T>> consumer) {
		new ForkJoinTask<>(0, list.size(), threshold, list, consumer, false).compute();
	}

	/**
	 * 批量插入，同步结果
	 * <br/>
	 * @param threshold 阀值
	 * @param list 插入的集合
	 * @param consumer 插入操作
	 * @return java.lang.Integer
	 * @author MaoJiaXing
	 * @date 2020-07-14 09:16
	 */
	public static <T> Integer syncResults(int threshold, List<T> list, Consumer<List<T>> consumer) {
		ForkJoinTask<T> joinTask = new ForkJoinTask<>(0, list.size(), threshold, list, consumer, true);
		return joinTask.compute();
	}

	static class ForkJoinTask<T> extends RecursiveTask<Integer> {

		private int start;
		private int end;
		private int threshold;
		private List<T> list;
		private Consumer<List<T>> consumer;
		private boolean flag;

		@Override
		protected Integer compute() {
			int length = end - start;
			if (length <= threshold) {
				// 在临界值范围内，实现业务代码
				List<T> temList = new ArrayList<>();
				for (int i = start; i < end; i++) {
					temList.add(list.get(i));
				}
				// 入库操作
				consumer.accept(temList);
				if (flag) {
					return temList.size();
				} else {
					return null;
				}
			} else {
				//不在临界值范围，继续拆分
				int middle = (start + end) / 2;
				ForkJoinTask<T> left = new ForkJoinTask<>(start, middle, threshold, list, consumer, flag);
				left.fork();
				ForkJoinTask<T> right = new ForkJoinTask<>(middle, end, threshold, list, consumer, flag);
				right.fork();
				if (flag) {
					return left.join() + right.join();
				} else {
					return null;
				}

			}

		}

		ForkJoinTask(int start, int end, int threshold, List<T> list, Consumer<List<T>> consumer, boolean flag) {
			this.start = start;
			this.end = end;
			this.threshold = threshold;
			this.list = list;
			this.consumer = consumer;
			this.flag = flag;
		}
	}
}
