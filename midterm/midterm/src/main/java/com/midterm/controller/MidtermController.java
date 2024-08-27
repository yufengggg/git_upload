package com.midterm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.midterm.service.MidtermService;

@RestController
@RequestMapping("/midterm")
@CrossOrigin({ "*" })
public class MidtermController {

    @Autowired
    MidtermService midtermService;

    /**
     * 範例程式
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/demoCode")
    public ResponseEntity<Map<String, Object>> demoCode(@RequestBody
    Map<String, String> map) {
        return new ResponseEntity<>(midtermService.demoCode(map), HttpStatus.OK);
    }
    
    /**
     * 第一題：發撲克牌
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/deal")
    public ResponseEntity<Map<String, Object>> deal(@RequestBody
    Map<String, String> map) {
        return new ResponseEntity<>(midtermService.deal(map), HttpStatus.OK);
    }

    /**
     * 第二題：證件號碼驗證
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/checkId")
    public ResponseEntity<Map<String, Object>> checkId(@RequestBody
    Map<String, String> map) {
        return new ResponseEntity<>(midtermService.checkId(map), HttpStatus.OK);
    }
    
    /**
     * 第二題：證件號碼驗證_加分題
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/getRandomId")
    public ResponseEntity<Map<String, Object>> getRandomId(@RequestBody
    Map<String, String> map) {
        return new ResponseEntity<>(midtermService.getRandomId(map), HttpStatus.OK);
    }

    /**
     * 第三題：Wordle
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/wordle")
    public ResponseEntity<Map<String, Object>> wordle(@RequestBody
    Map<String, String> map) {
        return new ResponseEntity<>(midtermService.wordle(map), HttpStatus.OK);
    }

    /**
     * 第四題：對對碰
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/matchingGame")
    public ResponseEntity<Map<String, Object>> matchingGame(@RequestBody
    Map<String, String> map) {
        return new ResponseEntity<>(midtermService.matchingGame(map), HttpStatus.OK);
    }

    /**
     * 第五題：捷運車資計算
     * @param map
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/metro")
    public ResponseEntity<Map<String, Object>> metro(@RequestBody
    Map<String, String> map) {
        return new ResponseEntity<>(midtermService.metro(map), HttpStatus.OK);
    }

}