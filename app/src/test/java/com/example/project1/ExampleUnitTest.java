package com.example.project1;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;
import com.example.project1.model.FitnessLog;
import com.example.project1.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

//    @Test
//    public void deleteLogTest() {
//        FitnessLogDao fitnessLogDao;
//        fitnessLogDao = AppDatabase.getDatabaseInstance(this).getFitnessLogDao();
//        List<FitnessLog> logs;
//        logs = fitnessLogDao.getFitnessLogsByUserId('user1');
//        assertEquals(1, MainActivity.deleteFitnessLog(logs.get(0)));
//    }
}