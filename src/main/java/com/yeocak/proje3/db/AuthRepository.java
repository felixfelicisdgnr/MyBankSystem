package com.yeocak.proje3.db;

import com.yeocak.proje3.model.Role;
import com.yeocak.proje3.model.User;
import com.yeocak.proje3.utils.UserInfo;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class AuthRepository {

    public static Connection connection;
    public static Statement statement;

    public static Boolean setupSQL() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybank", "root", "asdasd");
            statement = connection.createStatement();
            return true;
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return false;
    }

    public static boolean checkIfUserExists(String tc, String password) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM kisi WHERE tc ='" + tc + "' and sifre='" + password + "'");
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return false;
    }

    public static User getUser(String tc) {
        User newUser = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM kisi WHERE tc ='" + tc + "'");
            if (resultSet.next()) {
                newUser = new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        Role.valueOf(resultSet.getString(7)),
                        resultSet.getString(8)
                );
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return newUser;
    }

    public static void addUser(User user) {
        try {
            statement.execute("INSERT INTO kisi (isim,telefon,tc,adres,eposta,sifre,rol,temsilcisi_tc) " +
                    "VALUES('" + user.getName() + "', '" + user.getPhone() + "', '" + user.getTc() + "', '" + user.getAddress() + "', '" + user.getEmail() + "', '" + user.getPassword() + "', '" + user.getRole() + "', '" + user.getRep_tc() + "')");
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
    }

    public static ArrayList<String> getAllReps(){
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT tc FROM kisi WHERE rol ='TEMSILCI'");
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }
        return list;
    }

    public static int getNumberOfCustomer(String tc){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(tc) FROM kisi WHERE temsilcisi_tc = '"+tc+"'");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.getLocalizedMessage());
        }
        return -1;
    }

    private static class TemporaryRep {
        public String repTc;
        public int repCounter;
    }

    public static String getTcOfLeastRep() {
        ArrayList<String> reps = getAllReps();
        ArrayList<TemporaryRep> repClasses = new ArrayList<>();

        for (String rep : reps) {
            TemporaryRep adding = new TemporaryRep();
            adding.repTc = rep;
            adding.repCounter = 0;

            repClasses.add(adding);
        }

        for (int a = 0; a < reps.size(); a++) {
            repClasses.get(a).repCounter = getNumberOfCustomer(reps.get(a));
        }

        repClasses.sort(Comparator.comparingInt(left -> left.repCounter));

        return repClasses.get(0).repTc;
    }
}
