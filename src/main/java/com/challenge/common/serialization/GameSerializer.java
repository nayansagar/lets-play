package com.challenge.common.serialization;

import com.challenge.common.domain.Theme;

import java.io.*;

public class GameSerializer {
    private static final String STATE_FILE_LOCATION = System.getProperty("user.home");
    private static final String STATE_FILE_NAME = "gog.ser";
    private static final String STATE_FILE = STATE_FILE_LOCATION + File.separator + STATE_FILE_NAME;

    public void save(Theme theme) throws IOException {
        FileOutputStream fos = new FileOutputStream(STATE_FILE, false);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(theme);
        oos.close();
    }

    public Theme resume() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(STATE_FILE);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Theme result = (Theme) ois.readObject();
        ois.close();
        return result;
    }

    public void remove() {
        File file = new File(STATE_FILE);
        if(file.exists()){
            file.delete();
        }
    }
}
