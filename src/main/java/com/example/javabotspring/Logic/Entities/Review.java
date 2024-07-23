package com.example.javabotspring.Logic.Entities;

public class Review {
String id;
String userName;
String text;
String productValuation;
Object answer;
String productName;

String productArticle;

String filters = "Благодарим вас за оставленный отзыв. С Уважением, команда Vommy ☀️ " +
        "Рекомендуем посмотреть средство против зелени и водорослей в бассейне арт.156227682. " +
        "Альгицид используется для первичной или постоянной дезинфекции. " +
        "Введите номер в поисковой строке на главной странице Wildberries.";

    public Review(String id, String userName, String text, String productValuation, Object answer, String productName, String productArticle) {
        this.id = id;
        this.userName = userName;
        this.text = text;
        this.productValuation = productValuation;
        this.answer = answer;
        this.productName = productName;
        this.productArticle = productArticle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getProductValuation() {
        return productValuation;
    }

    public void setProductValuation(String productValuation) {
        this.productValuation = productValuation;
    }

    public Object getAnswer() {
        return answer;
    }

    public void setAnswer(Object answer) {
        this.answer = answer;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductArticle() {
        return productArticle;
    }

    public void setProductArticle(String productArticle) {
        this.productArticle = productArticle;
    }

    public static String getHimiya() {
        return "Здравствуйте, благодарим вас за высокую оценку и отзыв, " +
                "мы признательны вам за обратную связь, мы стремимся предоставить нашим " +
                "покупателям качественные продукты высокого качества. Рекомендуем посмотреть " +
                "донную щетку для чистки бассейна арт.168493458. Введите номер в поисковой строке " +
                "на главной странице Wildberries.";
    }

    public static String getShalognya() {
        return "Здравствуйте, благодарим вас за высокую оценку и отзыв, " +
                "мы благодарим вас за обратную связь, мы стремимся предоставить нашим " +
                "покупателям качественные продукты высокого качества. Рекомендуем посмотреть " +
                "песочный фильтр для бассейна, без которого невозможно наслаждаться купанием в полном " +
                "объёме, арт. 74604227. Введите номер в поисковой строке на главной странице Wildberries.";
    }

    public static String getFilters() {
        return "Благодарим вас за оставленный отзыв. С Уважением, команда Vommy ☀️ " +
                "Рекомендуем посмотреть средство против зелени и водорослей в бассейне арт.156227682. " +
                "Альгицид используется для первичной или постоянной дезинфекции. " +
                "Введите номер в поисковой строке на главной странице Wildberries.";
    }

    public static String getOsushitel() {
        return "Благодарим вас за оставленный отзыв. С Уважением, команда Step Mark! ☀️ ";
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", text='" + text + '\'' +
                ", productValuation='" + productValuation + '\'' +
                ", answer=" + answer +
                ", productName='" + productName + '\'' +
                ", productArticle='" + productArticle + '\'' +
                '}';
    }
}
