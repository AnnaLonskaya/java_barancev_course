package ua.annalonskaya.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

// нужно подключить зависимость в build.gradle (commons-net)
public class FtpHelper {

  private final ApplicationManager app;
  private FTPClient ftp;

  public FtpHelper(ApplicationManager app) {  // при вызове конструктора вып-ся иниц-ция, созд-ся FTPClient, к-ый будет создавать соединение и передавать файлы
    this.app = app;
    ftp = new FTPClient();
  }

  public void upload(File file, String target, String backup) throws IOException { // загружает новый файл, а старый переименовывает. Метод принимает 3 параметра
    // file - локальный файл, к-ый должен быть загружен на удаленную машину, target - имя удаленного файла, куда все это загружается, backup - имя резервной
    // копии, если удаленный файл уже сущ-ет.
    ftp.connect(app.getProperty("ftp.host"));            // уст-ем соединение с сервером
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(backup);                              // Т.е. сначала удаляем предыдущую резервную копию,
    ftp.rename(target, backup);                          // переименовываем удаленный файл, делаем резервную копию
    ftp.enterLocalPassiveMode();                         // включается пассивный режим передачи данных
    ftp.storeFile(target, new FileInputStream(file));    // файл передается
    ftp.disconnect();
  }

  public void restore(String backup, String target) throws IOException {  // восстанавливает старый файл
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(target);
    ftp.rename(backup, target);
    ftp.disconnect();
  }

}
// чтобы передавать файлы нужен Ftp сервер. В составе XAMPP он есть - FileZilla. Запускаем его. Нажимаем кнопку admin -> Ok, чтобы создать польз-ля, к-ый будет
// исп-ся в тестах. Нажимаем 4-ую кнопку слева(Users) -> add (создаем нового польз-ля, имя и пароль).