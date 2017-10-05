package com.n26.service;

import com.n26.entities.Statistic;
import com.n26.entities.Transaction;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Service;


import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ahmed on 5.10.17.
 */

@Service
public class StatisticsServices {

    /*use of Concurrent HashMap Data structure to get close into o(1) as a required complexity*/
    private ConcurrentHashMap<Long,Statistic> statistics = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long,Transaction> transactions = new ConcurrentHashMap<>();

     /*
     * save any transaction received in less than 60s and update the statistics
     *
     * remove all recorded transaction in more than 60 s
     *
     * */
    public void saveTansaction(Transaction transaction){



        if ((System.currentTimeMillis() - transaction.getTimestamp()) / 1000 < 60) {

            transactions.put(transaction.getTimestamp(),transaction);

            Statistic stat = statistics.entrySet().stream().max((entry1, entry2) ->
                    entry1.getValue().getCount() > entry2.getValue().getCount() ? 1 : -1)
                    .get()
                    .getValue();

            if(stat==null || (System.currentTimeMillis() - stat.getTimestamp()) / 1000 > 60){
                stat = new Statistic() ;
                stat.setSum(transaction.getAmount());
                stat.setCount(1l);
                stat.setMax(transaction.getAmount());
                stat.setMin(transaction.getAmount());

                statistics.put(stat.getTimestamp(),stat);

            }
            else{
                Statistic  statistic = new Statistic() ;
                statistic.setCount(stat.getCount()+1);
                statistic.setSum(stat.getSum()+transaction.getAmount());
                if (Double.compare(transaction.getAmount(), stat.getMax()) > 0)
                    statistic.setMax(transaction.getAmount());
                if (Double.compare(transaction.getAmount(), stat.getMin()) < 0)
                    statistic.setMin(transaction.getAmount());
                statistics.put(statistic.getTimestamp(),statistic);
                statistics.entrySet()
                        .removeIf(entry->System.currentTimeMillis() - entry.getValue().getTimestamp()>60);

            }



        }


        transactions.entrySet().removeIf(entry->System.currentTimeMillis() - entry.getValue().getTimestamp()>60);

    }
    /*
    * get only the recent statistic about transactions in less than 60s
    *
    * */
    public Statistic getStatistics(){


        Statistic stat = statistics.entrySet().stream().max((entry1, entry2) ->
                entry1.getValue().getCount() > entry2.getValue().getCount() ? 1 : -1)
                .get()
                .getValue();
        stat.setAvg(stat.getCount() > 0l ? stat.getSum() / stat.getCount() : 0.0);
        return stat;

    }


}
