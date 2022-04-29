package com.company;



import lombok.SneakyThrows;

import org.telegram.telegrambots.meta.TelegramBotsApi;

import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TelegramBotCurrency());
    }

}
/*Markaziy bank api ga bog'langan holda 3 valyuta (dollar, euro va yuan)
bo'yicha valyutani so'mga yoki so'mni valyutaga ayriboshlash imkonini beruvchi telegram bot yarating*/
