package org.example.command;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationSetting implements Externalizable {

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(notificationMap);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        notificationMap = (Map<Long, Notification>) objectInput.readObject();
    }

    public enum Notification {
        NINE(9),
        TEN(10),
        ELEVEN(11),
        TWELVE(12),
        THIRTEEN(13),
        FOURTEEN(14),
        FIFTEEN(15),
        SIXTEEN(16),
        SEVENTEEN(17),
        EIGHTEEN(18),
        OFF_NOTIFY(-1);

        private int time;

        Notification(int time) {this.time = time;
        }

        public int getTime() {
            return time;
        }

    }

    private static Map<Long, Notification> notificationMap = new HashMap<>();

    public static Map<Long, Notification> getNotificationMap() {
        return notificationMap;
    }

    public static void setNotification(long chatId, Notification notification) {
        notificationMap.put(chatId, notification);
    }

    public Notification getNotification(long chatId) {
        return notificationMap.getOrDefault(chatId, Notification.OFF_NOTIFY);
    }

    String setButton9Name(Long chatId) {
        return  (getNotification(chatId).equals(Notification.NINE)) ? "9" + " ✅" : "9" ;
    }

    String setButton10Name(Long chatId) {
        return  (getNotification(chatId).equals(Notification.TEN)) ? "10" + " ✅" : "10" ;
    }

    String setButton11Name(Long chatId) {
        return  (getNotification(chatId).equals(Notification.ELEVEN)) ? "11" + " ✅" : "11" ;
    }

    String setButton12Name(Long chatId) {
        return  (getNotification(chatId).equals(Notification.TWELVE)) ? "12" + " ✅" : "12" ;
    }

    String setButton13Name(Long chatId) {
        return  (getNotification(chatId).equals(Notification.THIRTEEN)) ? "13" + " ✅" : "13" ;
    }

    String setButton14Name(Long chatId) {
        return  (getNotification(chatId).equals(Notification.FOURTEEN)) ? "14" + " ✅" : "14" ;
    }

    String setButton15Name(Long chatId) {
        return  (getNotification(chatId).equals(Notification.FIFTEEN)) ? "15" + " ✅" : "15" ;
    }

    String setButton16Name(Long chatId) {
        return  (getNotification(chatId).equals(Notification.SIXTEEN)) ? "16" + " ✅" : "16" ;
    }

    String setButton17Name(Long chatId) {
        return  (getNotification(chatId).equals(Notification.SEVENTEEN)) ? "17" + " ✅" : "17" ;
    }

    String setButton18Name(Long chatId) {
        return  (getNotification(chatId).equals(Notification.EIGHTEEN)) ? "18" + " ✅" : "18" ;
    }

    String setButtonNoNotificationName(Long chatId) {
        return  (getNotification(chatId).equals(Notification.OFF_NOTIFY)) ? "Вимкнути сповіщення" + " ✅" : "Вимкнути сповіщення" ;
    }
      public static ReplyKeyboardMarkup getNotificationButtons(long chatId) {
        ReplyKeyboardMarkup notificationMarkup = new ReplyKeyboardMarkup();
        notificationMarkup.setResizeKeyboard(true);
        notificationMarkup.setOneTimeKeyboard(true);


        KeyboardButton button9 = new KeyboardButton();
        button9.setText(new NotificationSetting().setButton9Name(chatId));

        KeyboardButton button10 = new KeyboardButton();
        button10.setText(new NotificationSetting().setButton10Name(chatId));

        KeyboardButton button11 = new KeyboardButton();
        button11.setText(new NotificationSetting().setButton11Name(chatId));

        KeyboardButton button12 = new KeyboardButton();
        button12.setText(new NotificationSetting().setButton12Name(chatId));

        KeyboardButton button13 = new KeyboardButton();
        button13.setText(new NotificationSetting().setButton13Name(chatId));

        KeyboardButton button14 = new KeyboardButton();
        button14.setText(new NotificationSetting().setButton14Name(chatId));

        KeyboardButton button15 = new KeyboardButton();
        button15.setText(new NotificationSetting().setButton15Name(chatId));

        KeyboardButton button16 = new KeyboardButton();
        button16.setText(new NotificationSetting().setButton16Name(chatId));

        KeyboardButton button17 = new KeyboardButton();
        button17.setText(new NotificationSetting().setButton17Name(chatId));

        KeyboardButton button18 = new KeyboardButton();
        button18.setText(new NotificationSetting().setButton18Name(chatId));

        KeyboardButton buttonNoNotification = new KeyboardButton();
        buttonNoNotification.setText(new NotificationSetting().setButtonNoNotificationName(chatId));
        KeyboardButton back = new KeyboardButton();
        back.setText("🏠"+ "Додому");

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(button9);
        keyboardFirstRow.add(button10);
        keyboardFirstRow.add(button11);

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(button12);
        keyboardSecondRow.add(button13);
        keyboardSecondRow.add(button14);

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(button15);
        keyboardThirdRow.add(button16);
        keyboardThirdRow.add(button17);

        KeyboardRow keyboardFourthRow = new KeyboardRow();
        keyboardFourthRow.add(button18);
        keyboardFourthRow.add(buttonNoNotification);
        keyboardFourthRow.add(back);

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourthRow);

        notificationMarkup.setKeyboard(keyboard);

        return notificationMarkup;

   }
}

