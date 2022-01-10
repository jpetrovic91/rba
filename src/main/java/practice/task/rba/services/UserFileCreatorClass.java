package practice.task.rba.services;

import org.springframework.stereotype.Service;

import practice.task.rba.entities.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class UserFileCreatorClass {

    private static final String USER_FILES = "userFiles";

    private UserFileCreatorClass(){}

    private static String getFileName(String OIB){
        return USER_FILES + File.separator + OIB + "_" + System.currentTimeMillis() + ".txt";
    }

    private static boolean userFilesDirectoryExists(){
        File directory = new File(USER_FILES);
        if(!directory.exists())
            return directory.mkdir();
        else
            return true;
    }

    private static boolean userFileExists(String OIB){
        File[] listFiles = new File(USER_FILES).listFiles();
        for (File listFile : listFiles) {
            if (listFile.isFile()) {
                String fileName = listFile.getName();
                if (fileName.startsWith(String.valueOf(OIB)) && fileName.endsWith(".txt"))
                    return true;
            }
        }
        return false;
    }

    public static void createFileFromUserData(User user){

        if(userFilesDirectoryExists()) {
            String satusForFile = "Original";
            if(userFileExists(user.getOIB()))
                satusForFile = "Duplikat";

            try (FileWriter file = new FileWriter(getFileName(user.getOIB()))) {
                file.write(user.fileRepresentationString(satusForFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteFileWithUserDataIfExists(String OIB){
        boolean fileDeleted = false;
        File[] listFiles = new File(USER_FILES).listFiles();
        for (File listFile : listFiles) {
            if (listFile.isFile()) {
                String fileName = listFile.getName();
                if (fileName.startsWith(String.valueOf(OIB)) && fileName.endsWith(".txt"))
                    fileDeleted = listFile.delete();
            }
        }
        return fileDeleted;
    }
}
