package org.example;

import org.example.command.Buttons;
import org.example.command.NotificationSetting;
import org.glassfish.jersey.server.ManagedAsync;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.example.command.Buttons.NumberSymbolsAfterCommaSetting.getButtonsOfParse;
public class TelegramCurrencyBot extends TelegramLongPollingBot {
    BotLogic botLogic = new BotLogic();

    private Message lastMessageCom, lastMessageBank, lastMessageCUR;

    public TelegramCurrencyBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    private void sendMessageAndKeyboard(String text, Long chatId, InlineKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId);
        if (keyboard != null) {
            message.setReplyMarkup(keyboard);
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendMessageNotification(String text, Long chatId, ReplyKeyboardMarkup replyMarkup) {
        SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText(text);
                message.setReplyMarkup(replyMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void editMessageAndKeyboard(Message message, InlineKeyboardMarkup keyboard) throws TelegramApiException {

        EditMessageText newMessage = new EditMessageText();
        newMessage.setChatId(message.getChatId());
        newMessage.setMessageId(message.getMessageId());
        newMessage.setText(message.getText());
        if (keyboard != null) {
            newMessage.setReplyMarkup(keyboard);
        }
        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void commandStart(Update update) throws TelegramApiException {
        String infoAndSettings = "Ласкаво просимо! Цей бот допоможе відслідкувати актуальні курси валют.\n" +
                "Щоб отримати інформацію про курс USD/UAH у ПриватБанку натисніть кнопку 'Отримати інформацію'.\n" +
                "Для того щоб змінити налаштування, обрати інший банк, валюту та інше натисніть кнопку 'Налаштування'.";
        Long chatId1 = update.getMessage().getChatId();
        sendMessageAndKeyboard(infoAndSettings, chatId1, Buttons.getButtonsInfoAndSettings());
    }
    public void commandSettings(CallbackQuery callbackQuery) throws TelegramApiException {
        Long chatId1 = callbackQuery.getMessage().getChatId();
        String callbackQueryData = callbackQuery.getData();

        if (callbackQueryData.equals("Налаштування")) {

            sendMessageAndKeyboard("Налаштування", chatId1, Buttons.getButtonsOfSettings());
        }
        if (callbackQueryData.equals("Отримати інформацію")) {

            sendMessageAndKeyboard(botLogic.getFinalMessage(), chatId1, Buttons.getButtonsInfoAndSettings());

        }
        if (callbackQueryData.equals("Кількість знаків після коми")) {

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Кількість знаків після коми");
            sendMessage.setChatId(chatId1);
            sendMessage.setReplyMarkup(getButtonsOfParse(chatId1));
            lastMessageCom = execute(sendMessage);
        }

        if (Character.isDigit(callbackQueryData.charAt(0))) {

            botLogic.parseMenuButton(callbackQueryData);
            editMessageAndKeyboard(lastMessageCom,getButtonsOfParse(chatId1));
        }
        if (callbackQueryData.equals("Банк")) {

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("\uD83C\uDFE6"+"Банк");
            sendMessage.setChatId(chatId1);
            sendMessage.setReplyMarkup(Buttons.Num.getButtonsBank(chatId1));
            lastMessageBank= execute(sendMessage);
        }
        switch (callbackQueryData){
        case "ПриватБанк","МоноБанк","НБУ":
            botLogic.bankMenuButton(callbackQueryData);
            editMessageAndKeyboard(lastMessageBank, Buttons.Num.getButtonsBank(chatId1));
            break;
        }
        if(callbackQueryData.equals("Валюта")){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Валюта");
            sendMessage.setChatId(chatId1);
            sendMessage.setReplyMarkup(Buttons.getButtonsCurr(chatId1));
            lastMessageCUR= execute(sendMessage);

        }
        switch (callbackQueryData){
            case "EUR","USD":
                botLogic.Curracy(callbackQueryData);
                editMessageAndKeyboard(lastMessageCUR, Buttons.getButtonsCurr(chatId1));
                break;
        }
         if (callbackQueryData.equals("назад")){
             sendMessageAndKeyboard("Налаштування",chatId1,Buttons.getButtonsOfSettings());
        }
          if (callbackQueryData.equals("Час сповіщень")){
              SendMessage sendMessage = new SendMessage();
              sendMessage.setText("Виберіть час сповіщення");
              sendMessage.setChatId(chatId1);
              sendMessage.setReplyMarkup(NotificationSetting.getNotificationButtons(chatId1));
              execute(sendMessage);
          }
        if (callbackQueryData.equals("Головне меню")){

            sendMessageAndKeyboard("Головне меню",chatId1,Buttons.getButtonsInfoAndSettings());
        }
    }
    private void handleMessage(Message message) throws TelegramApiException {
        String text = message.getText();
        Long chatId = message.getChatId();
        String chatID = message.getChatId().toString();

        switch (text) {
            case "🏠"+ "Додому":
                sendMessageAndKeyboard("Ви повернулись на головне меню", chatId, Buttons.getButtonsInfoAndSettings());
                break;
            case "9":
                NotificationSetting.setNotification(message.getChatId(), NotificationSetting.Notification.NINE);
                sendMessageNotification("Надішлемо Вам сповіщення о  9 годині.", chatId, NotificationSetting.getNotificationButtons(chatId));
                break;

            case "10":
                NotificationSetting.setNotification(message.getChatId(), NotificationSetting.Notification.TEN);
                sendMessageNotification("Надішлемо Вам сповіщення о  10 годині.", chatId, NotificationSetting.getNotificationButtons(chatId));
                break;

            case "11":
                NotificationSetting.setNotification(message.getChatId(), NotificationSetting.Notification.ELEVEN);
                sendMessageNotification("Надішлемо Вам сповіщення о  11 годині.", chatId, NotificationSetting.getNotificationButtons(chatId));
                break;

            case "12":
                NotificationSetting.setNotification(message.getChatId(), NotificationSetting.Notification.TWELVE);
                sendMessageNotification("Надішлемо Вам сповіщення о  12 годині.", chatId, NotificationSetting.getNotificationButtons(chatId));
                break;

            case "13":
                NotificationSetting.setNotification(message.getChatId(), NotificationSetting.Notification.THIRTEEN);
                sendMessageNotification("Надішлемо Вам сповіщення о  13 годині.", chatId, NotificationSetting.getNotificationButtons(chatId));
                break;

            case "14":
                NotificationSetting.setNotification(message.getChatId(), NotificationSetting.Notification.FOURTEEN);
                sendMessageNotification("Надішлемо Вам сповіщення о  14 годині.", chatId, NotificationSetting.getNotificationButtons(chatId));
                break;

            case "15":
                NotificationSetting.setNotification(message.getChatId(), NotificationSetting.Notification.FIFTEEN);
                sendMessageNotification("Надішлемо Вам сповіщення о  15 годині.", chatId, NotificationSetting.getNotificationButtons(chatId));
                break;

            case "16":
                NotificationSetting.setNotification(message.getChatId(), NotificationSetting.Notification.SIXTEEN);
                sendMessageNotification("Надішлемо Вам сповіщення о  16 годині.", chatId, NotificationSetting.getNotificationButtons(chatId));
                break;

            case "17":
                NotificationSetting.setNotification(message.getChatId(), NotificationSetting.Notification.SEVENTEEN);
                sendMessageNotification("Надішлемо Вам сповіщення о  17 годині.", chatId, NotificationSetting.getNotificationButtons(chatId));
                break;

            case "18":
                NotificationSetting.setNotification(message.getChatId(), NotificationSetting.Notification.EIGHTEEN);
                sendMessageNotification("Надішлемо Вам сповіщення о  18 годині.", chatId, NotificationSetting.getNotificationButtons(chatId));
                break;

            case "Вимкнути сповіщення":
                NotificationSetting.setNotification(message.getChatId(), NotificationSetting.Notification.OFF_NOTIFY);
                sendMessageNotification("Ви вимкнули сповіщення.", chatId, NotificationSetting.getNotificationButtons(chatId));
                break;
        }
    }
    public void sendNotification(long chatId) throws TelegramApiException {

        execute(SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(BotLogic.getFinalMessage())
                .replyMarkup(Buttons.getButtonsInfoAndSettings())
                .build());
    }
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().isCommand()) {
            try {
                commandStart(update);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {

            try {
              commandSettings(update.getCallbackQuery());
           } catch (TelegramApiException e) {
               throw new RuntimeException(e);
          }
        } else if (update.hasMessage() && update.getMessage().hasText()) {

            try {
                handleMessage(update.getMessage());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}