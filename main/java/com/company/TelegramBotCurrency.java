package com.company;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


import java.util.ArrayList;
import java.util.List;

public class TelegramBotCurrency extends TelegramLongPollingBot {
    private String currentCurrency;
    private String currentExchange;
    private Double amount;

    @Override
    public String getBotUsername() {
        return "@currency_PDP_B15_bot";
    }

    @Override
    public String getBotToken() {
        return "5289336116:AAFcvd1sH69TkNtlZUJCZt6UZwyzo5qDbBs";
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        if (update.hasCallbackQuery()) {
            handleCallBack(update.getCallbackQuery());
        }
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }

        execute(sendMessage);

    }

    @SneakyThrows
    public void handleCallBack(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        String data = callbackQuery.getData();
        if (data.equals("menu")) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(getCurrencyButton());
            SendMessage sendMessage = SendMessage.builder()
                    .text("Konvertatsiya qilmoqchi bo'lgan valyutangizni tanlang!!!")
                    .chatId(message.getChatId().toString())
                    .replyMarkup(inlineKeyboardMarkup)
                    .build();
            execute(sendMessage);
        }
        if (data.equals("usd") || data.equals("eur") || data.equals("cny")) {
            SendMessage sendMessage = SendMessage.builder()
                    .text("1 " + data.toUpperCase() + " = " + Currency.getCurrency(data) + " UZS")
                    .chatId(message.getChatId().toString())
                    .replyMarkup(getCurrencyExchange(data))
                    .build();
            currentCurrency = data;
            execute(sendMessage);
        }
        if (data.equals("touzs")) {
            SendMessage sendMessage = SendMessage.builder()
                    .text("Enter amount in " + currentCurrency.toUpperCase())
                    .chatId(message.getChatId().toString())
                    .build();
            currentExchange = data;
            execute(sendMessage);


        }
        if (data.equals("fromuzs")) {
            SendMessage sendMessage = SendMessage.builder()
                    .text("Enter amount in UZS")
                    .chatId(message.getChatId().toString())
                    .build();
            currentExchange = data;
            execute(sendMessage);

        }

    }

    @SneakyThrows
    public void handleMessage(Message message) {
        String s = message.getText();
        boolean isNumeric = true;
        try {
            amount = Double.valueOf(s);
        } catch (NumberFormatException num) {
            isNumeric = false;
        }

        if (message.getText().equals("/start")) {

            SendMessage sendMessage = SendMessage.builder()
                    .text("Welcome to our convertation bot.")
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(menuButton()).build())
                    .chatId(message.getChatId().toString())
                    .build();

            execute(sendMessage);

        }
        if (isNumeric) {
            SendMessage sendMessage = SendMessage.builder()
                    .text(exchange())
                    .chatId(message.getChatId().toString())
                    .build();
            execute(sendMessage);
        }

    }

    public List<List<InlineKeyboardButton>> menuButton() {
        List<List<InlineKeyboardButton>> buttonList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = InlineKeyboardButton.builder().text("MENU").callbackData("menu").build();
        row.add(button);

        buttonList.add(row);
        return buttonList;
    }


    public String exchange() {
        String str = currentExchange.toLowerCase();
        Double currencyRate = Currency.getCurrency(currentCurrency);
        if (str.equals("fromuzs")) {
            return amount / currencyRate + " " + currentCurrency.toUpperCase();
        }
        return amount * currencyRate + " " + "UZS";
    }


    public List<List<InlineKeyboardButton>> getCurrencyButton() {


        List<List<InlineKeyboardButton>> cButtonList = new ArrayList<>();

        List<InlineKeyboardButton> cButton1 = new ArrayList<>();
        List<InlineKeyboardButton> cButton2 = new ArrayList<>();

        InlineKeyboardButton b1 = new InlineKeyboardButton();
        b1.setText("USD");
        b1.setCallbackData("usd");
        InlineKeyboardButton b2 = new InlineKeyboardButton();
        b2.setText("EUR");
        b2.setCallbackData("eur");
        InlineKeyboardButton b3 = new InlineKeyboardButton();
        b3.setText("CNY");
        b3.setCallbackData("cny");
        InlineKeyboardButton b4 = new InlineKeyboardButton();
        b4.setText("MENU");
        b4.setCallbackData("menu");

        cButton1.add(b1);
        cButton1.add(b2);
        cButton1.add(b3);
        cButton2.add(b4);

        cButtonList.add(cButton1);
        cButtonList.add(cButton2);

        return cButtonList;
    }

    public InlineKeyboardMarkup getCurrencyExchange(String currency) {
        InlineKeyboardMarkup currencyKey = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> cButtonList = new ArrayList<>();

        List<InlineKeyboardButton> cButton1 = new ArrayList<>();
        List<InlineKeyboardButton> cButton2 = new ArrayList<>();

        InlineKeyboardButton b1 = new InlineKeyboardButton();
        b1.setText(currency.toUpperCase() + "->UZS");
        b1.setCallbackData("touzs");
        InlineKeyboardButton b2 = new InlineKeyboardButton();
        b2.setText("UZS->" + currency.toUpperCase());
        b2.setCallbackData("fromuzs");
        InlineKeyboardButton b3 = new InlineKeyboardButton();
        b3.setText("MENU");
        b3.setCallbackData("menu");

        cButton1.add(b1);
        cButton1.add(b2);
        cButton2.add(b3);

        cButtonList.add(cButton1);
        cButtonList.add(cButton2);

        currencyKey.setKeyboard(cButtonList);
        return currencyKey;

    }
}
