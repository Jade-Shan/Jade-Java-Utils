/**
 * 
 */
package jadeutils.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author morgan
 * 
 */
public class PageSpliter {

	/**
	 * 计算机当前页的偏移量
	 * 
	 * @param currentPageNo
	 *            当前页号
	 * @param pageSize
	 *            每页大小
	 * @return 当前页记录的偏移量
	 */
	public static int getOffSet(int currentPageNo, int pageSize) {
		if (currentPageNo < 2 || pageSize < 1) {
			return 0;
		}
		return (currentPageNo - 1) * pageSize;
	}

	/**
	 * 计算记录要分成几页
	 * 
	 * @param recNumber
	 *            总记录数
	 * @param pageSize
	 *            每页大小
	 * @return 共要多少页
	 */
	public static int countPages(int recNumber, int pageSize) {
		if (recNumber < 1 || pageSize < 1) {
			return 1;
		}
		return (recNumber + pageSize - 1) / pageSize;
	}

	/**
	 * 列出当前页面号之前的页号列表
	 * 
	 * @param currentPageNo
	 *            当前页面号
	 * @return 当前页面号之前的页号列表
	 */
	private static List<String> prePageNumbers(int start, int end) {
		if (start < 1) {
			start = 1;
		}
		List<String> l = new ArrayList<String>();
		for (int i = start; i < end; i++) {
			l.add("" + i);
		}
		return l;
	}

	/**
	 * 列出当前页面号之后的页号列表
	 * 
	 * @param currentPageNo
	 *            当前页面号
	 * @return 当前页面号之前的页号列表
	 */
	private static List<String> posPageNumbers(int start, int end) {
		if (end < start) {
			end = start;
		}
		List<String> l = new ArrayList<String>();
		for (int i = start + 1; i <= end; i++) {
			l.add("" + i);
		}
		return l;
	}

	/**
	 * 根据页码（dto.getCurrPageNo()）、
	 * <p>
	 * 总记录数（dto .getCountRecNumber()）、
	 * <p>
	 * 每页记录数（dto.getPageSize()）来完成分页工作
	 * 
	 * @param dto
	 * @throws JadeDaoException
	 */
	public static final void splitPage(PageSplitDto dto) throws JadeDaoException {
		int spNo = 4;
		try {
			/*
			 * 取得总页数
			 */
			int countPageNumber = 0;
			try {
				countPageNumber = PageSpliter.countPages(
						dto.getCountRecNumber(), dto.getPageSize());
			} catch (Exception e) {
				countPageNumber = 1;
			}
			dto.setCountPageNumber(countPageNumber);
			// 当前页号
			int currentPageNo = dto.getCurrPageNo();

			// 是否有下一页
			int nextPageNo = currentPageNo + 1;
			/*
			 * 计算后一页的页码
			 */
			if (nextPageNo > countPageNumber) {
				nextPageNo = countPageNumber;
			}
			dto.setNextPageNo(nextPageNo);
			// 是否省略之后多余的页数
			if (currentPageNo + spNo < countPageNumber) {
				dto.setAppPos("YES");
			}

			/*
			 * 计算前一页的页号(面前有4页)
			 */
			int prePageNo = currentPageNo - 1;
			if (prePageNo < 1) {
				prePageNo = 1;
			}
			dto.setPrePageNo(prePageNo);
			// 是否省略之前的多余页数
			if (1 + spNo < currentPageNo) {
				dto.setAppPre("YES");
			}

			/*
			 * 取得之前的页数列表(后面有5页)
			 */
			int startPage = currentPageNo - spNo;
			int exp = spNo + 1 - (countPageNumber - currentPageNo);
			if (exp > 0) {
				startPage -= exp;
			}
			dto.setPrePageNoList(PageSpliter.prePageNumbers(startPage,
					currentPageNo));

			/*
			 * 取得之后的页数列表
			 */
			int endPage = currentPageNo + spNo + 1;
			int exn = spNo - currentPageNo + 1;
			if (exn > 0) {
				endPage += exn;
			}
			if (endPage > countPageNumber) {
				endPage = countPageNumber;
			}
			dto.setPosPageNoList(PageSpliter.posPageNumbers(currentPageNo,
					endPage));
		} catch (Exception e) {
			e.printStackTrace();
			// 查询结果时出错
			throw new JadeDaoException("err.groupon.001");
		}
	}

}
