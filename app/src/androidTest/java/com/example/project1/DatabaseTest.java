package com.example.project1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.project1.Utility.Utils;
import com.example.project1.activity.MainActivity;
import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;
import com.example.project1.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        FitnessLogDao fitnessLogDao = AppDatabase.getDatabaseInstance(appContext).getFitnessLogDao();

        // Username verification Test
        User user = new User("User1", "User123");

        fitnessLogDao.insert(user);

        boolean isValid = Utils.verifyUsername("User1", user.getUsername());

        assertTrue(isValid);

        user.setUsername("User2");

        fitnessLogDao.update(user);

        isValid = Utils.verifyUsername("User1", user.getUsername());

        assertFalse(isValid);

        // Password Verification Test
        isValid = Utils.verifyPassword("User123", user.getPassword());

        assertTrue(isValid);

        user.setPassword("User2");

        fitnessLogDao.update(user);

        isValid = Utils.verifyPassword("User123", user.getPassword());

        assertFalse(isValid);

    }

    @Test
    public void verifyIntent() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent intent = MainActivity.intentFactory(appContext, 10);
        intent.putExtra("Test", "intentWithData");

        assertEquals("intentWithData", intent.getStringExtra("Test"));

        intent = new Intent();
        intent.putExtra("Test1", "intentWithData2");

        assertEquals("intentWithData2", intent.getStringExtra("Test1"));
    }
}
