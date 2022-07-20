package quaks.by.ntmcore.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import quaks.by.ntmcore.NTMcore;

import java.io.File;
import java.io.IOException;

public class RoleList {
    static String name = "rolelist"; // Имя, которое получит файл
    private static File file; // Переменная file типа File. Использование см. ниже
    private static FileConfiguration fileConfiguration; // Переменная fileConfiguration типа FileConfiguration. Использование см. ниже
    public static void setup(NTMcore t) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Ntmcore").getDataFolder(), name+".yml"); // Загружаем в память файл {name}.yml из папки плагина. Если файла не существует - Java загрузит пустой файл и запомнит что его не существует

        if(!file.exists()){
            try {
                file.createNewFile();
                t.saveResource(name + ".yml", true);
            } catch (IOException e) {
                e.printStackTrace();
            }// Пробуем создать файл
        }// Если файла не существует
        fileConfiguration = YamlConfiguration.loadConfiguration(file); // fileConfiguration загружаем из file
    }
    public static FileConfiguration get(){
        return fileConfiguration;
    } // Функция получения файла конфигурации (конкретно этого)
    public static void save(){
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// Функция сохранения файла
    public static void reload(){
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }// Функция перезагрузки файла (нужно если файл отредактировали извне)
}