package net.jadedungeon.javautil.sandtable.tools;

public class RloRating {

	/**
	 * 计算期望值 // E_A = \frac{1}{1 + 10^{(R_B-R_A)/400}}
	 * 
	 * @param playerRank   玩家的积分
	 * @param opponentRank 对手的积分
	 * @return 玩家所得积分的期望值
	 */
	private static double caculateExpectationValue(double playerRank, double opponentRank) {
		double n1 = (opponentRank - playerRank) / 400;
		double n2 = 1.0 + Math.pow(10, n1);
		double expect = 1 / n2;
		return expect;
	}

	/**
	 * 计算新积分
	 * 
	 * {R_A}' = R_A + K(S_A - E_A) 
	 * 
	 * @param oldRank 玩家比赛前的积分
	 * @param score   比赛结果得分：（胜得1分，平局0.5分，负得0分）
	 * @param expect  根据玩家积分与对手积分算出的期望值
	 * @param k       积分增加权重，比如国际象棋比赛中用`30`，暴雪战网比赛中用`15`
	 * @return 玩家比赛后的新积分
	 */
	private static double caculateNewRank(double oldRank, double score, double expect, double k) {
		return oldRank + (k * (score - expect));
	}

	/**
	 * 
	 * @param playerRank   玩家的积分
	 * @param opponentRank 对手的积分
	 * @param score        比赛结果得分：（胜得1分，平局0.5分，负得0分）
	 * @param k            积分增加权重，比如国际象棋比赛中用`30`，暴雪战网比赛中用`15`
	 * @return 玩家比赛后的新积分
	 */
	public static double caculateRank(double playerRank, double opponentRank, double score, double k) {
		double expect = caculateExpectationValue(playerRank, opponentRank);
		return caculateNewRank(playerRank, score, expect, k);
	}

	public static void main(String[] args) {
		double rankA = 130;
		double rankB = 500;
		double k = 30;
		double scoreA = 0;
		double scoreb = 0;
		// 第1场比赛 A 胜
		scoreA = 1;
		scoreb = 0;
		rankA = caculateRank(rankA, rankB, scoreA, k);
		rankB = caculateRank(rankB, rankA, scoreb, k);
		System.out.println("第1场比赛 A 胜，积分： A（" + rankA + "）B（" + rankB + "）");
		// 第2场比赛 A 胜
		scoreA = 1;
		scoreb = 0;
		rankA = caculateRank(rankA, rankB, scoreA, k);
		rankB = caculateRank(rankB, rankA, scoreb, k);
		System.out.println("第2场比赛 A 胜，积分： A（" + rankA + "）B（" + rankB + "）");
		// 第3场比赛 b 胜
		scoreA = 0;
		scoreb = 1;
		rankA = caculateRank(rankA, rankB, scoreA, k);
		rankB = caculateRank(rankB, rankA, scoreb, k);
		System.out.println("第3场比赛 A 胜，积分： A（" + rankA + "）B（" + rankB + "）");

	}

}
