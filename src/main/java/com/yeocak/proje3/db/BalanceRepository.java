package com.yeocak.proje3.db;

import com.yeocak.proje3.model.*;
import com.yeocak.proje3.utils.BankInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.yeocak.proje3.db.AuthRepository.statement;

public class BalanceRepository {
    public static Boolean setBalance(int accId, int amount) {
        try {
            statement.execute("UPDATE hesap SET miktar = " + amount + " WHERE hesap_id = " + accId);
            return true;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return false;
    }

    public static Boolean addTakeCredit(int accId, int amount, Months month, int year) {
        try {
            statement.execute("INSERT INTO kredi (hesap_id,miktar,tarih_ay,tarih_yil) " +
                    "VALUES(" + accId + "," + amount + "," + month.index + ", " + year + ")");
            return true;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getErrorCode());
        }
        return false;
    }

    public static boolean sendMoneyBetweenAccounts(Account source, Account target, int amount) {
        Boolean first = setBalance(source.getAccId(), source.getBalance() - amount);

        Double firstRatio = getExchangeRatio(source.getExchange());
        Double secondRatio = getExchangeRatio(target.getExchange());

        double newRatio = secondRatio / firstRatio;
        double newAmount = (double) amount / newRatio;

        Boolean second = setBalance(target.getAccId(), target.getBalance() + (int) newAmount);

        return first && second;
    }

    public static double getExchangeRatio(String name) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM para_birimi WHERE isim = '" + name + "'");
            if (resultSet.next()) {
                return resultSet.getDouble(2);
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return -1;
    }

    public static ArrayList<String> getAllExchangeNames() {
        ArrayList<String> newList = new ArrayList();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT isim FROM para_birimi");
            while (resultSet.next()) {
                newList.add(resultSet.getString(1));
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return newList;
    }

    public static boolean addNewAccount(String tc, int miktar, String exchange) {
        try {
            statement.execute("INSERT INTO hesap (tc,miktar,kur) VALUES(" + tc + "," + miktar + ",'" + exchange + "')");
            return true;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return false;
    }

    public static Account getFirstAccount(String tc) {
        Account newAcc;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM hesap WHERE tc = '" + tc + "'");
            if (resultSet.next()) {
                newAcc = new Account(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4)
                );
                return newAcc;
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return null;
    }

    public static Account getAccount(int accId) {
        Account newAcc;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM hesap WHERE hesap_id = " + accId + "");
            if (resultSet.next()) {
                newAcc = new Account(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4)
                );
                return newAcc;
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return null;
    }

    public static ArrayList<Account> getAccounts(String tc) {
        ArrayList<Account> newList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM hesap WHERE tc = '" + tc + "'");
            while (resultSet.next()) {
                Account newAcc = new Account(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4)
                );
                newList.add(newAcc);
            }
            return newList;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return null;
    }

    public static ArrayList<Log> getLogs(String tc) {
        ArrayList<Log> newList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM log WHERE kaynak_tc = '" + tc + "' OR hedef_tc = '" + tc + "'");
            while (resultSet.next()) {
                Log log = new Log(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        Operation.valueOf(resultSet.getString(4)),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getString(8)
                );
                newList.add(log);
            }
            return newList;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return null;
    }

    public static ArrayList<Log> getAllLogs(int howMany) {
        ArrayList<Log> newList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM log");
            int counter = 1;
            while (resultSet.next() && counter++ <= howMany) {
                Log log = new Log(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        Operation.valueOf(resultSet.getString(4)),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getString(8)
                );
                newList.add(log);
            }
            return newList;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return null;
    }

    public static Months getCurrentMonth() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM banka");
            if (resultSet.next()) {
                int nowMonth = resultSet.getInt(7);
                return Months.get(nowMonth);
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getErrorCode());
        }
        return null;
    }

    public static int getCurrentYear() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM banka");
            if (resultSet.next()) {
                return resultSet.getInt(8);
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return 2020;
    }

    public static boolean hasCreditLog(int accId) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM kredi WHERE hesap_id = " + accId);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getErrorCode());
        }
        return false;
    }

    public static double getCreditAmount(int accId, Months month, int year) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT miktar FROM kredi WHERE hesap_id = " + accId + "AND tarih_ay = " + month.index + " AND tarih_yil " + year);
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getErrorCode());
        }
        return -1;
    }

    public static double getLastCreditAmount(int accId) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT miktar FROM kredi WHERE hesap_id = " + accId);
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getErrorCode());
        }
        return -1;
    }

    public static double getTotalCreditAmount(int accId) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT miktar FROM kredi WHERE hesap_id = " + accId);
            double total = -1;
            while (resultSet.next()) {
                total += resultSet.getDouble(1);
            }
            return total;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getErrorCode());
        }
        return -1;
    }

    public static Boolean setAmountCredit(int accId, double amount, Months month, int year) {
        try {
            statement.execute("UPDATE kredi SET miktar = " + amount + " WHERE hesap_id = " + accId + " AND tarih_ay = " + month.index + " AND tarih_yil = " + year);
            return true;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
        return false;
    }

    public static void updateUser(User oldUser, User newUser) {
        try {
            statement.execute("UPDATE kisi SET isim = '" + newUser.getName() + "', telefon = '" + newUser.getPhone() + "', adres = '" + newUser.getAddress() + "', eposta = '" + newUser.getEmail() + "'  WHERE tc = '" + oldUser.getTc() + "'");
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
    }

    public static ArrayList<User> getUserViaRepTc(String tc) {
        ArrayList<User> newList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM kisi WHERE temsilcisi_tc = '" + tc + "'");
            while (resultSet.next()) {
                User newUser = new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        Role.valueOf(resultSet.getString(7)),
                        resultSet.getString(8)
                );
                newList.add(newUser);
            }
            return newList;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
        return null;
    }

    public static void getBankInfo() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM banka");
            if (resultSet.next()) {
                BankInfo.income = resultSet.getInt(2);
                BankInfo.expense = resultSet.getInt(3);
                BankInfo.gain = resultSet.getInt(4);
                BankInfo.total = resultSet.getInt(5);
                BankInfo.workerPay = resultSet.getInt(6);
                BankInfo.currentMonth = Months.get(resultSet.getInt(7));
                BankInfo.currentYear = resultSet.getInt(8);
                BankInfo.interest = resultSet.getDouble(9);
                BankInfo.addIntereset = resultSet.getInt(10);
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getErrorCode());
        }
    }

    public static void updateBankInfo(double interest, double addInterest, int workerPay) {
        try {
            statement.execute("UPDATE banka SET faiz = " + interest + ", ek_faiz = " + addInterest + ", calisan_maas = " + workerPay);
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
    }

    public static ArrayList<Exchange> getAllExchanges() {
        ArrayList<Exchange> newList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM para_birimi");
            while (resultSet.next()) {
                Exchange newExchange = new Exchange(
                        resultSet.getString(1),
                        resultSet.getDouble(2)
                );
                newList.add(newExchange);
            }
            return newList;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
        return null;
    }

    public static void updateExchange(Exchange exchange) {
        try {
            statement.execute("UPDATE para_birimi SET how_tl = " + exchange.getRatio() + "WHERE isim = '" + exchange.getName() + "'");
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
    }

    public static boolean addNewExchange(Exchange exchange) {
        try {
            statement.execute("INSERT INTO para_birimi (isim,how_tl) VALUES('" + exchange.getName() + "','" + exchange.getRatio() + "')");
            return true;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return false;
    }

    public static void deleteExchange(Exchange exchange) {
        try {
            statement.execute("DELETE FROM para_birimi WHERE isim = '" + exchange.getName() + "'");
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
    }

    public static void deleteAllCredits(int accId) {
        try {
            statement.execute("DELETE FROM kredi WHERE hesap_id = " + accId);
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
    }

    public static void deleteCredit(int accId, Months month, int year) {
        try {
            statement.execute("DELETE FROM kredi WHERE hesap_id = " + accId + " AND tarih_ay = " + month.index + " AND tarih_yil = " + year);
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
    }

    public static Months getLastCreditMonth(int accId) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT tarih_ay FROM kredi WHERE hesap_id = " + accId);
            if (resultSet.next()) {
                return Months.get(resultSet.getInt(1));
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getErrorCode());
        }
        return null;
    }

    public static int getLastCreditYear(int accId) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT tarih_yil FROM kredi WHERE hesap_id = " + accId);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getErrorCode());
        }
        return -1;
    }

    public static void deleteAccount(int accId) {
        try {
            statement.execute("DELETE FROM hesap WHERE hesap_id = " + accId);
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
    }

    public static void createLog(Log log) {
        try {
            statement.execute("INSERT INTO log (kaynak_tc,hedef_tc,islem,tutar,kaynak_bakiye,hedef_bakiye,tarih) VALUES('" + log.getSourceTc() + "','" + log.getTargetTc() + "','" + log.getOperation() + "'," + log.getAmount() + "," + log.getSourceBalance() + "," + log.getTargetBalance() + ",'" + log.getDate() + "')");
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
    }

    public static void getBankInterest() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT faiz,ek_faiz FROM banka");
            if (resultSet.next()) {
                BankInfo.interest = resultSet.getDouble(1);
                BankInfo.addIntereset = resultSet.getDouble(2);
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getErrorCode());
        }
    }

    public static void addBankBalance(int amount) {
        int newTotal = (BankInfo.total + amount);

        if (amount > 0) {
            try {
                statement.execute("UPDATE banka SET toplam = " + newTotal + ", gelir = " + (BankInfo.expense + amount) + ", kar = " + (BankInfo.gain + amount));
            } catch (SQLException sqlEx) {
                System.out.println(sqlEx.getLocalizedMessage());
            }
            BankInfo.updateBankInfo();
            return;
        }
        if (amount < 0) {
            try {
                statement.execute("UPDATE banka SET toplam = " + newTotal + ", gider = " + (BankInfo.expense - amount) + ", kar = " + (BankInfo.gain + amount));
            } catch (SQLException sqlEx) {
                System.out.println(sqlEx.getLocalizedMessage());
            }
        }

        BankInfo.updateBankInfo();
    }

    public static void changeBankDate(Months month, int year) {
        try {
            statement.execute("UPDATE banka SET tarih_ay = " + month.index + ", tarih_yil = " + year);
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }

        BankInfo.updateBankInfo();
    }
}
