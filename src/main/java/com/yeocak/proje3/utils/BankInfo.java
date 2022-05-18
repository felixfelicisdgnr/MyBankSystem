package com.yeocak.proje3.utils;

import com.yeocak.proje3.db.BalanceRepository;
import com.yeocak.proje3.model.Months;

public class BankInfo {
    public static int income;
    public static int expense;
    public static int gain;
    public static int total;
    public static int workerPay;
    public static Months currentMonth;
    public static int currentYear;
    public static double interest;
    public static double addIntereset;

    public static void updateBankInfo(){
        BalanceRepository.getBankInfo();
    }

    public static void runTheDateOneMonth(){
        if(currentMonth == Months.ARALIK){
            BalanceRepository.changeBankDate(Months.OCAK, currentYear + 1);
        } else{
            BalanceRepository.changeBankDate(currentMonth.getNextMonthWithMod(), currentYear);
        }
    }
}
