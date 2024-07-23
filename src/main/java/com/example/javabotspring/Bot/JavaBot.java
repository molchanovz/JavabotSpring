package com.example.javabotspring.Bot;

import com.example.javabotspring.Logic.WildberriesHandler.Feedbacks.Feedback.ReviewToMessage;
import com.example.javabotspring.Logic.googleSheets.OzonToSheet;
import com.example.javabotspring.Logic.googleSheets.WildberriesToSheet;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class JavaBot extends TelegramLongPollingBot {
    final BotConfig config;
    String cashMsg = "";

    public JavaBot(BotConfig config) {
        super(config.token);
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.botName;
    }


    @Override
    public void onUpdateReceived(@NotNull Update update) {
        String key2 = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjIwMjMxMjI1djEiLCJ0eXAiOiJKV1QifQ.eyJlbnQiOj" +
                "EsImV4cCI6MTcyMTQzMDkxMywiaWQiOiI2ODRlY2ViMS0zMTA3LTQxNDAtYTc5MC0xZTgzODRmYTM1M" +
                "WMiLCJpaWQiOjQ2MTk4NjUsIm9pZCI6MTcyMjkwLCJzIjo1MTAsInNpZCI6IjhjMjgzNzM4LWZjZmUt" +
                "NGUzMy04YzM3LTVhNGFlMWQ3N2Q2ZiIsInQiOmZhbHNlLCJ1aWQiOjQ2MTk4NjV9.EgPsQ4RtXo982A" +
                "0x_5lGy-kcpth-2eFv8bTOjWSnUUrW7ptqhs05PwsFTEIa4nDgWyvp_p5C2Au2RWmtPvx3MQ";

        String key = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjIwMjMxMDI1" +
                "djEiLCJ0eXAiOiJKV1QifQ.eyJlbnQiOjEsImV4cCI6MTcxODE0MTE0NCwiaWQiOiJmYWQwNTFjMC1m" +
                "MTc1LTRjMzktOWQ4Yy0zNzc2NDgzMGY5OGEiLCJpaWQiOjQ2MTk4NjUsIm9pZCI6Mjc1NjYsInMiOjQ" +
                "0Niwic2FuZGJveCI6ZmFsc2UsInNpZCI6IjYzYTk2NDNlLWM3NTYtNTQ0Yy1iZTkzLTRhM2ExMzI2Nj" +
                "g5MyIsInVpZCI6NDYxOTg2NX0.ncQDNGh1vg-10tfwfDP7CoDsEa-XDfkIIqaGNSgRPW32rpl92UDWX" +
                "0dgN_SEM1fuZkD6Ce3ZszJ0sQWYv2OzsA";

        OzonToSheet ozonTrade = new OzonToSheet("259267","451952b2-a29b-4f7e-819e-3fd96f580fbc","");
        WildberriesToSheet wbTrade = new WildberriesToSheet(key,"");

        OzonToSheet ozonEco = new OzonToSheet("1441373","34b6db44-b259-4fe9-8d22-55463873c0f1"," ЭТ");
        WildberriesToSheet wbEco = new WildberriesToSheet(key2," ЭТ");


        String name = update.getMessage().getFrom().getFirstName();
        String surName = String.valueOf(update.getMessage().getFrom().getLastName());
        String chatId = String.valueOf(update.getMessage().getChatId());
        log.info(chatId);
        String userName = update.getMessage().getFrom().getUserName();
        String text = update.getMessage().getText();

        if(text.equals("/start")){
            sendMsg(chatId,"Привет, "+name);
        }

        if(text.equals("Ответить на отзывы ВБ")){
            sendMsg(chatId,"Отвечаю на отзывы");
            sendMsg(chatId,ReviewToMessage.setAnswer(key));
        }

        if(text.equals("Вчерашние заказы ВБ")){
            sendMsg(chatId,"Отправляю вчерашние заказы ВБ");
            sendMsg(chatId, wbTrade.getLastDayOrders());
        }

        if(text.equals("Вчерашние заказы ВБ Экотехника")){
            sendMsg(chatId,"Отправляю вчерашние заказы ВБ Экотехника");
            sendMsg(chatId, wbEco.getLastDayOrders());
        }

        if(text.equals("Вчерашние заказы ОЗОН")){
            sendMsg(chatId,"Отправляю вчерашние заказы ОЗОН");
            sendMsg(chatId, ozonTrade.getLastOrders());
        }

        if(text.equals("Вчерашние заказы ОЗОН Экотехника")){
            sendMsg(chatId,"Отправляю вчерашние заказы ОЗОН Экотехника");
            sendMsg(chatId, ozonEco.getLastOrders());
        }


    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(s);
        setButtons_1(message);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void setButtons_1(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Ответить на отзывы ВБ");
        keyboardFirstRow.add(keyboardButton);

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Вчерашние заказы ВБ"));
        keyboardSecondRow.add(new KeyboardButton("Вчерашние заказы ВБ Экотехника"));

        KeyboardRow keyboardThreeRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardThreeRow.add(new KeyboardButton("Вчерашние заказы ОЗОН"));
        keyboardThreeRow.add(new KeyboardButton("Вчерашние заказы ОЗОН Экотехника"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThreeRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public synchronized void setButtons_2(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("пошел нахуй");
        keyboardFirstRow.add(keyboardButton);

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("пизда"));

        KeyboardRow keyboardThreeRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardThreeRow.add(new KeyboardButton("дрозда"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThreeRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}
