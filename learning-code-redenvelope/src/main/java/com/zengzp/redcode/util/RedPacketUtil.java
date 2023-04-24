package com.zengzp.redcode.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author zengzp
 * @Date 2023/4/22 22:20
 * @Version 1.0
 **/
public class RedPacketUtil {

    /**
     * 发送红包算法
     * @param totalAmount 总金额
     * @param totalPeopleNum 总人数
     * @return
     */
    public static List<Integer> divideRedPacket(Integer totalAmount,Integer totalPeopleNum){
        List<Integer> amountList = new ArrayList<>();
        if(totalAmount>0 && totalPeopleNum>0){
            Integer restAmount=totalAmount;
            Integer restPeopleNum=totalPeopleNum;
            Random random=new Random();
            for (int i=0;i<totalPeopleNum-1;i++ ){
                int amount=random.nextInt(restAmount/restPeopleNum*2-1)+1;
                restAmount-=amount;
                restPeopleNum--;
                amountList.add(amount);
            }
            amountList.add(restAmount);
        }
        return amountList;

    }
}
