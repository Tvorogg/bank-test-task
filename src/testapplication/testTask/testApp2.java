package testapplication.testTask;

import java.util.HashMap;
import java.util.Map;

public class testApp2 {
    private static final String INPUT_STRING = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> \n"
                                               + "<DeliverySMS> \n"
                                               + " <PackNum>16692535</PackNum> \n"
                                               + " <ProvID>2</ProvID> \n"
                                               + " <SMS> \n"
                                               + " <DBSMSId>146920910</DBSMSId> \n"
                                               + " <CrmID>0</CrmID> \n"
                                               + " <ServiceType>6</ServiceType> \n"
                                               + " <PhoneTarget>375336110000</PhoneTarget> \n"
                                               + " <PhoneSource>1200</PhoneSource> \n"
                                               + " <Message>Priorbank 10/07 18:00. Code: ШИФР po karte DK0500. Spravka: 80172890000</Message> \n"
                                               + " <SendStart>0</SendStart> \n"
                                               + " <SendStop>1439</SendStop> \n"
                                               + " <Priority>0</Priority> \n"
                                               + " </SMS> \n"

                                               + " <SMS> \n"
                                               + " <DBSMSId>146920909</DBSMSId> \n"
                                               + " <CrmID>0</CrmID> \n"
                                               + " <ServiceType>4</ServiceType> \n"
                                               + " <PhoneTarget>375298810000</PhoneTarget> \n"
                                               + " <PhoneSource>1200</PhoneSource> \n"
                                               + " <Message>Priorbank 10/07 18:00. M-kod=H000, Platezh s DK8800, schet platezha 000000000. Summa 5.00 BYN. Spravka: 487 VhGfTg00000</Message> \n"
                                               + " <SendStart>0</SendStart> \n"
                                               + " <SendStop>1439</SendStop> \n"
                                               + " <Priority>0</Priority> \n"
                                               + " </SMS> \n"
                                               + "</DeliverySMS> ";

    private static final String MESSAGE_CVV_PREFIX = "Code:";
    private static final String MESSAGE_CVV_POSTFIX = " ";
    private static final String MESSAGE_START = "<Message>";
    private static final String MESSAGE_END = "</Message>";

    public static void main(String[] args) {
        System.out.println(maskCipherCVCInPack(INPUT_STRING));
    }

    private static String maskCipherCVCInPack(String xml) {
        Map<String, String> replaces = new HashMap<>();
        String buf = xml;
        int startPos = buf.indexOf(MESSAGE_START);
        while (startPos > -1) {
            buf = buf.substring(startPos, buf.length());
            int endPos = buf.indexOf(MESSAGE_END);
            int tailLength = MESSAGE_END.length();
            if (endPos <= -1) {
                endPos = buf.length();
                tailLength = 0;
            }
            String message = buf.substring(0, endPos + tailLength);
            String masked = maskCipherCVCInMsg(message);
            if (!message.equals(masked)) {
                replaces.put(message, masked);
            }
            startPos = buf.indexOf(MESSAGE_START, endPos + MESSAGE_END.length());
        }
        for (Map.Entry<String, String> rec : replaces.entrySet()) {
            xml = xml.replace(rec.getKey(), rec.getValue());
        }
        return xml;
    }

    private static String maskCipherCVCInMsg(String message) {
        int startFrom = 0;
        String mask = "---";
        int cipherLength;
        int startPos = message.toUpperCase().indexOf(MESSAGE_CVV_PREFIX.toUpperCase(), startFrom);
        startPos += MESSAGE_CVV_PREFIX.length() + 1;
        int endPos = message.indexOf(MESSAGE_CVV_POSTFIX, startPos);
        if (endPos > -1) {
            cipherLength = endPos - startPos;
        } else {
            cipherLength = message.length() - startPos - MESSAGE_END.length();
        }
        message = message.substring(0, startPos) + mask + message.substring(startPos + cipherLength, message.length());
        return message;
    }
}