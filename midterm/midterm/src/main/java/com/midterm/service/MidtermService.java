package com.midterm.service;

import java.util.Map;

public interface MidtermService {

    /**
     * 範例程式
     * @param map
     * @return
     */
    Map<String, Object> demoCode(Map<String, String> map);
    
    /**
     * 第一題：發撲克牌
     * @param map
     * @return
     */
    Map<String, Object> deal(Map<String, String> map);
    
    /**
     * 第二題：證件號碼驗證
     * @param map
     * @return
     */
    Map<String, Object> checkId(Map<String, String> map);
    
    /**
     * 第二題：證件號碼驗證_加分題
     * @param map
     * @return
     */
    Map<String, Object> getRandomId(Map<String, String> map);
    
    /**
     * 第三題：Wordle
     * @param map
     * @return
     */
    Map<String, Object> wordle(Map<String, String> map);
    
    /**
     * 第四題：對對碰
     * @param map
     * @return
     */
    Map<String, Object> matchingGame(Map<String, String> map);
    
    /**
     * 第五題：捷運車資計算
     * @param map
     * @return
     */
    Map<String, Object> metro(Map<String, String> map);

}
