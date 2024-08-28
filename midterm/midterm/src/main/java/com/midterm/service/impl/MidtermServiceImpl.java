package com.midterm.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.midterm.service.MidtermService;

@Service
public class MidtermServiceImpl implements MidtermService {

	@Autowired
	private DataSource dataSource;

	/**
	 * 範例程式
	 * 
	 * @param demoMap
	 * @return
	 */
	@Override
	public Map<String, Object> demoCode(Map<String, String> demoMap) {
		/* 1. 將前端傳入值取出：使用前端傳入物件的key值，從Map中取得對應value，例如： */
		String id = demoMap.get("id");
		String keyword = demoMap.get("keyword");
		System.err.println("id--->" + id);
		System.err.println("keyword--->" + keyword);

		/*
		 * 2. 業務邏輯：檢核、題目要求邏輯實作，如需使用DB連線，請參考下列程式碼 try (Connection conn =
		 * dataSource.getConnection(); PreparedStatement pstmt =
		 * conn.prepareStatement(DEMOCODE_QUERY_SQL_STRING);) {
		 * 
		 * 
		 * } catch (SQLException e) { e.printStackTrace(); }
		 */

		/* 3. 把要回傳給前端的值包裝成Map後return，例如： */
		Map<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("success", true);
		rtnMap.put("returnMessage", "驗證成功");
		rtnMap.put("metro_fee", 100);
		rtnMap.put("pokerA", new ArrayList<>());
		return rtnMap;
	}

	/**
	 * 第一題：發撲克牌
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, Object> deal(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 第二題：證件號碼驗證
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, Object> checkId(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;

	}

	/**
	 * 第二題：證件號碼驗證_加分題
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, Object> getRandomId(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;

	}

	/**
	 * 第三題：Wordle
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, Object> wordle(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 第四題：對對碰
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, Object> matchingGame(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 第五題：捷運車資計算
	 * 
	 * @param map
	 * @return
	 */

	@Override
	public Map<String, Object> metro(Map<String, String> map) {
		Map<String, Object> rtnMap = new HashMap<>();
		String startRoute = map.get("startRoute");
		String endRoute = map.get("endRoute");
		String startStation = map.get("startStation");
		String endStation = map.get("endStation");

		try (Connection conn = dataSource.getConnection()) {
			// 起點路線和終點路線相同
			if (startRoute.equals(endRoute)) {
				List<String> resultLst = calculateRoute(conn, startRoute, startStation, endStation);
				String resultString = String.join("、", resultLst);
				int stationNum = resultLst.size(); // 經過站數
				BigDecimal fare = new BigDecimal(stationNum).multiply(BigDecimal.TEN); // 計算車資

				rtnMap.put("sameRoute", true); // 判斷是否為相同路線
				rtnMap.put("stations", resultString); // 經過的站點
				rtnMap.put("stationCount", stationNum); // 站點數量
				rtnMap.put("fare", fare.toPlainString()); // 車資
				
			// 起點路線和終點路線不同
			} else {
				List<String> preTransferStations = new ArrayList<>();
				List<String> midTransferStations = new ArrayList<>();
				List<String> postTransferStations = new ArrayList<>();
				String firstTransferStation = null; // 第一次轉乘站
				String firstTransferRoute = null;
				String secondTransferStation = null;
				String secondTransferRoute = null;
				Boolean directTransferFound = false; // 判斷有無找到轉乘站

				List<Map<String, String>> startRouteStations = findTransferStations(conn, startRoute); // 找起點路線的轉乘站

				for (Map<String, String> transferInfo : startRouteStations) {
					String transferStation = transferInfo.get("station");
					String transferRoute = transferInfo.get("transferRoute");
					// 當轉乘路線跟終點路線相同
					if (transferRoute.equals(endRoute)) {
						firstTransferStation = transferStation;
						preTransferStations = calculateRoute(conn, startRoute, startStation, firstTransferStation);
						postTransferStations = calculateRoute(conn, endRoute, firstTransferStation, endStation);

						int stationNum = preTransferStations.size() + postTransferStations.size() - 1; // 都會包含到轉乘站
						BigDecimal totalFare = new BigDecimal(stationNum).multiply(BigDecimal.TEN);

						directTransferFound = true; // 找到轉乘站
						
						// 移除轉乘站在pre/post TransferStations 的資料
						if (!preTransferStations.isEmpty() && preTransferStations.contains(firstTransferStation)) {
							preTransferStations.remove(firstTransferStation);
						}
						if (!postTransferStations.isEmpty() && postTransferStations.contains(firstTransferStation)) {
							postTransferStations.remove(firstTransferStation);
						}

						rtnMap.put("sameRoute", false);
						rtnMap.put("preTransferStations", String.join("、", preTransferStations));
						rtnMap.put("firstTransferStation", firstTransferStation);
						rtnMap.put("postTransferStations", String.join("、", postTransferStations));
						rtnMap.put("stationCount", stationNum);
						rtnMap.put("fare", totalFare.toPlainString());
						break;
					}
				}

				// 需要轉兩次車
				if (!directTransferFound) {
					for (Map<String, String> transferInfo : startRouteStations) {
						firstTransferStation = transferInfo.get("station");
						firstTransferRoute = transferInfo.get("transferRoute");

						// 查找第一次轉乘後的轉乘站
						List<Map<String, String>> secondRouteStations = findTransferStations(conn, firstTransferRoute);

						for (Map<String, String> secondTransferInfo : secondRouteStations) {
							secondTransferStation = secondTransferInfo.get("station");
							secondTransferRoute = secondTransferInfo.get("transferRoute");

							if (secondTransferRoute.equals(endRoute)) {

								preTransferStations = calculateRoute(conn, startRoute, startStation, firstTransferStation);
								midTransferStations = calculateRoute(conn, firstTransferRoute, firstTransferStation, secondTransferStation);
								postTransferStations = calculateRoute(conn, secondTransferRoute, secondTransferStation, endStation);

								int stationNum = preTransferStations.size() + midTransferStations.size() + postTransferStations.size() - 2;
								BigDecimal totalFare = new BigDecimal(stationNum).multiply(BigDecimal.TEN);

								directTransferFound = true;

								if (!preTransferStations.isEmpty() && preTransferStations.contains(firstTransferStation)) {
									preTransferStations.remove(firstTransferStation);
								}
								if (!midTransferStations.isEmpty() && midTransferStations.contains(firstTransferStation)) {
									midTransferStations.remove(firstTransferStation);
								}
								if (!midTransferStations.isEmpty() && midTransferStations.contains(secondTransferStation)) {
									midTransferStations.remove(secondTransferStation);
								}
								if (!postTransferStations.isEmpty()	&& postTransferStations.contains(secondTransferStation)) {
									postTransferStations.remove(secondTransferStation);
								}

								rtnMap.put("sameRoute", false);
								rtnMap.put("preTransferStations", String.join("、", preTransferStations));
								rtnMap.put("firstTransferStation", firstTransferStation);
								rtnMap.put("midTransferStations", String.join("、", midTransferStations));
								rtnMap.put("secondTransferStation", secondTransferStation);
								rtnMap.put("postTransferStations", String.join("、", postTransferStations));
								rtnMap.put("stationCount", stationNum);
								rtnMap.put("fare", totalFare.toPlainString());
								break;

							}
							// 跳出外層迴圈
							if (directTransferFound) {
								break;
							}

						}
					}
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rtnMap;

	}
	
	// 計算經過的站點
	private List<String> calculateRoute(Connection conn, String route, String startStation, String endStation)
			throws SQLException {

		List<String> metroList = new ArrayList<>();

		final String SEL_SQL_SAME_ROUTE = "select STATION from STUDENT.EX_METRO where ROUTE= ? and STATION_ORDER between ? and ? order by STATION_ORDER";

		try (PreparedStatement pstmt = conn.prepareStatement(SEL_SQL_SAME_ROUTE)) {
			int startOrder = getStationOrder(conn, route, startStation);
			int endOrder = getStationOrder(conn, route, endStation);

			// 設定參數，確保 startOrder 總是小於 endOrder
			pstmt.setString(1, route);
			pstmt.setInt(2, Math.min(startOrder, endOrder)); // 找到比較小的order
			pstmt.setInt(3, Math.max(startOrder, endOrder)); // 找到比較大的order

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					metroList.add(rs.getString("STATION"));
				}
			}

			// 如果原來的 startOrder 比 endOrder 大，反轉結果列表
			if (startOrder > endOrder) {
				Collections.reverse(metroList);
			}
		}

		return metroList;
	}

	private int getStationOrder(Connection conn, String route, String station) throws SQLException {
		String orderSql = "select STATION_ORDER from STUDENT.EX_METRO where ROUTE = ? and STATION = ?";
		try (PreparedStatement orderStmt = conn.prepareStatement(orderSql)) {
			orderStmt.setString(1, route);
			orderStmt.setString(2, station);
			try (ResultSet rs = orderStmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("STATION_ORDER");
				} else {
					throw new SQLException("Station not found: " + station);
				}
			}
		}

	}
	
	// 查找轉站點的方法
	private List<Map<String, String>> findTransferStations(Connection conn, String route) throws SQLException {
		final String SEL_TRANSFER_SQL = "select STATION, TRANSFER from STUDENT.EX_METRO where ROUTE= ? and TRANSFER IS NOT NULL order by STATION_ORDER"; // 篩選transfer不為空
		List<Map<String, String>> transferStations = new ArrayList<>(); // 紀錄哪站可以轉乘 [{"station":轉乘站, "transferRoute":轉乘路線}]
		
		try (PreparedStatement pstmt = conn.prepareStatement(SEL_TRANSFER_SQL)) {
			pstmt.setString(1, route);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Map<String, String> stationInfo = new HashMap<>();
					stationInfo.put("station", rs.getString("STATION"));
					stationInfo.put("transferRoute", rs.getString("TRANSFER"));
					transferStations.add(stationInfo);
				}
			}
		}
		return transferStations;
	}
}
