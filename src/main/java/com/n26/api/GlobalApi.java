package com.n26.api;

import com.n26.entities.Statistic;
import com.n26.entities.Transaction;
import com.n26.service.StatisticsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
 * Created by ahmed on 4.10.17.
 */

@RestController

public class GlobalApi {



    private StatisticsServices statisticsServices;


    @Autowired
    public GlobalApi(StatisticsServices statisticsServices){
        this.statisticsServices= statisticsServices;
    }

    /*
    * Methods are synchronized to deal with the thread safe requirements
    *
    *
    * */

    @RequestMapping(value = "/transactions",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public synchronized  void addTransaction(@RequestBody Transaction transaction , HttpServletResponse response) {


        if((transaction.getTimestamp())/1000>60){

            response.setStatus(204);
        }
        else
        {
        statisticsServices.saveTansaction(transaction);
            response.setStatus(201);
        }

    }



    @RequestMapping(value = "/statistics",method = RequestMethod.GET)
    @GetMapping
    public synchronized Statistic getStatistics() {

        return statisticsServices.getStatistics();


    }

}
