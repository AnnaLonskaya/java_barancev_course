package ua.annalonskaya.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Взаимодействие с сервером при помощи Http клиента. Создаем помощник HttpSession (он не требует доступа к браузеру). Как работает HttpSession:
// в момент конструирования, т.е. когда вызывается метод newSession() из ApplicationManager создается новый экземпляр помощника и внутри создается
// новый клиент (новая сессия для работы по протоколу Http - объект, к-ый будет отправлять запросы на сервер)(строка 30)
public class HttpSession {

  private CloseableHttpClient httpclient;
  private ApplicationManager app;

  public HttpSession(ApplicationManager app) { // конструктор HttpSession принимает на вход ApplicationManager для удобства. Чтобы не думать, какие именно
    // данные ApplicationManager должен передать помощнику, он передает ссылку на самого себя. Помощник знает, кто явл-ся его менеджером и для необходимой
    // инф-ции он обращается к менеджеру.
    this.app = app;
    httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build(); // в созданном объекте HttpClients.custom() уст-ся стратегия
    // перенаправлений, чтобы Http клиент, к-ый создается автоматически выполнял все перенаправления. В итоге вызвали конструктор, создан новый объект и
    // помещен в поле httpclient.
  }

  public boolean login(String username, String password) throws IOException { // метод для выполнения логина
    HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php"); // для выполнения логина отправляем запрос. Для этого создаем будущий Post запрос (пока пустой). У него будет тело и какие-то параметры
    List<NameValuePair> params = new ArrayList<>();                                  // формируем набор параметров
    params.add(new BasicNameValuePair("username", username));
    params.add(new BasicNameValuePair("password", password));
    params.add(new BasicNameValuePair("secure_session", "on"));
    params.add(new BasicNameValuePair("return", "index.php"));
    post.setEntity(new UrlEncodedFormEntity(params));                                // после этого параметры упаковываются и помещаются в запрос post.setEntity
    CloseableHttpResponse response = httpclient.execute(post); // httpclient.execute(post)-происходит отправка запроса. response - ответ, к-ый получен от сервера
    String body = geTextFrom(response); // получаем текст ответа от сервера (на языке HTML)
    return body.contains(String.format("<span class=\"italic\">%s</span>", username)); // проверяем, действитеьно ли польз-ль залогинился,
  } // содержит ли код страницы эту строку, где написано мя польз-ля, к-ый вошел  в систему

  private String geTextFrom(CloseableHttpResponse response) throws IOException{
    try {
      return EntityUtils.toString(response.getEntity());
    } finally {
      response.close();
    }
  }

  public boolean isLoggedInAs(String username) throws IOException {  // метод для определения какой польз-ль сейчас залогинен
    HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php");
    CloseableHttpResponse response = httpclient.execute(get);
    String body = geTextFrom(response);
    return body.contains(String.format("<span class=\"italic\">%s</span>", username));
  }

}
