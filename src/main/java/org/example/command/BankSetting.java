package org.example.command;

import org.example.bank.Bank;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;
public class BankSetting implements Externalizable {
        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeObject(savedBank);
        }

        @Override
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            savedBank = (Map<Long, Bank>) objectInput.readObject();
        }
        private static Map<Long, Bank> savedBank = new HashMap<>();

        public static Bank getSavedBank(Long chatId) {
            return savedBank.getOrDefault(chatId, Bank.ПриватБанк);
        }

}
