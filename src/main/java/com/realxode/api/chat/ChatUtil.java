package com.realxode.api.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ChatUtil {
    private static final int CENTER_PX = 154;

    private ChatUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static List<String> translate(List<String> in) {
        return in.stream().map(ChatUtil::translate).collect(Collectors.toList());
    }

    public static String strip(String in) {
        return ChatColor.stripColor(in);
    }

    public static void message(CommandSender sender, String in) {
        sender.sendMessage(translate(in));
    }

    public static void message(Player player, String in) {
        player.sendMessage(translate(in));
    }

    public static void broadcast(String in) {
        Bukkit.broadcastMessage(translate(in));
    }

    public static void log(String in) {
        Bukkit.getConsoleSender().sendMessage(translate(in));
    }

    public enum Chat {
        A('A', 5),
        a('a', 5),
        B('B', 5),
        b('b', 5),
        C('C', 5),
        c('c', 5),
        D('D', 5),
        d('d', 5),
        E('E', 5),
        e('e', 5),
        F('F', 5),
        f('f', 4),
        G('G', 5),
        g('g', 5),
        H('H', 5),
        h('h', 5),
        I('I', 3),
        i('i', 1),
        J('J', 5),
        j('j', 5),
        K('K', 5),
        k('k', 4),
        L('L', 5),
        l('l', 1),
        M('M', 5),
        m('m', 5),
        N('N', 5),
        n('n', 5),
        O('O', 5),
        o('o', 5),
        P('P', 5),
        p('p', 5),
        Q('Q', 5),
        q('q', 5),
        R('R', 5),
        r('r', 5),
        S('S', 5),
        s('s', 5),
        T('T', 5),
        t('t', 4),
        U('U', 5),
        u('u', 5),
        V('V', 5),
        v('v', 5),
        W('W', 5),
        w('w', 5),
        X('X', 5),
        x('x', 5),
        Y('Y', 5),
        y('y', 5),
        Z('Z', 5),
        z('z', 5),
        NUM_1('1', 5),
        NUM_2('2', 5),
        NUM_3('3', 5),
        NUM_4('4', 5),
        NUM_5('5', 5),
        NUM_6('6', 5),
        NUM_7('7', 5),
        NUM_8('8', 5),
        NUM_9('9', 5),
        NUM_0('0', 5),
        EXCLAMATION_POINT('!', 1),
        AT_SYMBOL('@', 6),
        NUM_SIGN('#', 5),
        DOLLAR_SIGN('$', 5),
        PERCENT('%', 5),
        UP_ARROW('^', 5),
        AMPERSAND('&', 5),
        ASTERISK('*', 5),
        LEFT_PARENTHESIS('(', 4),
        RIGHT_PERENTHESIS(')', 4),
        MINUS('-', 5),
        UNDERSCORE('_', 5),
        PLUS_SIGN('+', 5),
        EQUALS_SIGN('=', 5),
        LEFT_CURL_BRACE('{', 4),
        RIGHT_CURL_BRACE('}', 4),
        LEFT_BRACKET('[', 3),
        RIGHT_BRACKET(']', 3),
        COLON(':', 1),
        SEMI_COLON(';', 1),
        DOUBLE_QUOTE('"', 3),
        SINGLE_QUOTE('\'', 1),
        LEFT_ARROW('<', 4),
        RIGHT_ARROW('>', 4),
        QUESTION_MARK('?', 5),
        SLASH('/', 5),
        BACK_SLASH('\\', 5),
        LINE('|', 1),
        TILDE('~', 5),
        TICK('`', 2),
        PERIOD('.', 1),
        COMMA(',', 1),
        SPACE(' ', 3),
        DEFAULT('a', 4);

        private final char character;
        private final int length;
        private static final int CENTER_PX = 127;
        private static final int MAX_PX = 240;
        private static final int CENTER_CHAT_PX = 154;
        private static final int MAX_CHAT_PX = 250;

        Chat(char character, int length) {
            this.character = character;
            this.length = length;
        }

        public char getCharacter() {
            return this.character;
        }

        public int getLength() {
            return this.length;
        }

        public int getBoldLength() {
            return this == SPACE ? this.getLength() : this.length + 1;
        }

        public static Chat getDefaultFontInfo(char c) {
            Chat[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                Chat dFI = var1[var3];
                if (dFI.getCharacter() == c) {
                    return dFI;
                }
            }

            return DEFAULT;
        }

        public static String centerMotD(String message) {
            message = ChatColor.translateAlternateColorCodes('&', message.replace("<#center>", ""));
            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;
            int charIndex = 0;
            int lastSpaceIndex = 0;
            String toSendAfter = null;
            String recentColorCode = "";
            char[] var8 = message.toCharArray();
            int toCompensate = var8.length;

            int spaceLength;
            int c;
            for (spaceLength = 0; spaceLength < toCompensate; ++spaceLength) {
                c = var8[spaceLength];
                if (c == 167) {
                    previousCode = true;
                } else {
                    if (previousCode) {
                        previousCode = false;
                        recentColorCode = "§" + c;
                        if (c == 108 || c == 76) {
                            isBold = true;
                            continue;
                        }

                        isBold = false;
                    } else if (c == 32) {
                        lastSpaceIndex = charIndex;
                    } else {
                        Chat dFI = getDefaultFontInfo((char) c);
                        messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                        ++messagePxSize;
                    }

                    if (messagePxSize >= 240) {
                        toSendAfter = recentColorCode + message.substring(lastSpaceIndex + 1);
                        message = message.substring(0, lastSpaceIndex + 1);
                        break;
                    }

                    ++charIndex;
                }
            }

            int halvedMessageSize = messagePxSize / 2;
            toCompensate = 127 - halvedMessageSize;
            spaceLength = SPACE.getLength() + 1;
            c = 0;

            StringBuilder sb;
            for (sb = new StringBuilder(); c < toCompensate; c += spaceLength) {
                sb.append(" ");
            }

            if (toSendAfter != null) {
                centerMotD(toSendAfter);
            }

            return sb + message;
        }

        public static void sendCenteredMessageV1(Player player, String message) {
            if (message == null || message.equals("")) {
                player.sendMessage("");
            }

            message = ChatColor.translateAlternateColorCodes('&', message.replace("<#center>", ""));
            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;
            char[] var5 = message.toCharArray();
            int toCompensate = var5.length;

            int spaceLength;
            int c;
            for (spaceLength = 0; spaceLength < toCompensate; ++spaceLength) {
                c = var5[spaceLength];
                if (c == 167) {
                    previousCode = true;
                } else if (previousCode) {
                    previousCode = false;
                    isBold = c == 108 || c == 76;
                } else {
                    Chat dFI = getDefaultFontInfo((char) c);
                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                    ++messagePxSize;
                }
            }

            int halvedMessageSize = messagePxSize / 2;
            toCompensate = 127 - halvedMessageSize;
            spaceLength = SPACE.getLength() + 1;
            c = 0;

            StringBuilder sb;
            for (sb = new StringBuilder(); c < toCompensate; c += spaceLength) {
                sb.append(" ");
            }

            player.sendMessage(sb + message);
        }

        public static void sendCenteredMessageV2(Player player, String message) {
            message = ChatColor.translateAlternateColorCodes('&', message.replace("<#center>", ""));
            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;
            int charIndex = 0;
            int lastSpaceIndex = 0;
            String toSendAfter = null;
            String recentColorCode = "";
            char[] var9 = message.toCharArray();
            int toCompensate = var9.length;

            int spaceLength;
            int c;
            for (spaceLength = 0; spaceLength < toCompensate; ++spaceLength) {
                c = var9[spaceLength];
                if (c == 167) {
                    previousCode = true;
                } else {
                    if (previousCode) {
                        previousCode = false;
                        recentColorCode = "§" + c;
                        if (c == 108 || c == 76) {
                            isBold = true;
                            continue;
                        }

                        isBold = false;
                    } else if (c == 32) {
                        lastSpaceIndex = charIndex;
                    } else {
                        Chat dFI = getDefaultFontInfo((char) c);
                        messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                        ++messagePxSize;
                    }

                    if (messagePxSize >= 250) {
                        toSendAfter = recentColorCode + message.substring(lastSpaceIndex + 1);
                        message = message.substring(0, lastSpaceIndex + 1);
                        break;
                    }

                    ++charIndex;
                }
            }

            int halvedMessageSize = messagePxSize / 2;
            toCompensate = 154 - halvedMessageSize;
            spaceLength = SPACE.getLength() + 1;
            c = 0;

            StringBuilder sb;
            for (sb = new StringBuilder(); c < toCompensate; c += spaceLength) {
                sb.append(" ");
            }

            player.sendMessage(sb + message);
            if (toSendAfter != null) {
                sendCenteredMessageV2(player, toSendAfter);
            }

        }

        public static void sendCenteredMessageV2(CommandSender player, String message) {
            message = ChatColor.translateAlternateColorCodes('&', message.replace("<#center>", ""));
            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;
            int charIndex = 0;
            int lastSpaceIndex = 0;
            String toSendAfter = null;
            String recentColorCode = "";
            char[] var9 = message.toCharArray();
            int toCompensate = var9.length;

            int spaceLength;
            int c;
            for (spaceLength = 0; spaceLength < toCompensate; ++spaceLength) {
                c = var9[spaceLength];
                if (c == 167) {
                    previousCode = true;
                } else {
                    if (previousCode) {
                        previousCode = false;
                        recentColorCode = "§" + c;
                        if (c == 108 || c == 76) {
                            isBold = true;
                            continue;
                        }

                        isBold = false;
                    } else if (c == 32) {
                        lastSpaceIndex = charIndex;
                    } else {
                        Chat dFI = getDefaultFontInfo((char) c);
                        messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                        ++messagePxSize;
                    }

                    if (messagePxSize >= 250) {
                        toSendAfter = recentColorCode + message.substring(lastSpaceIndex + 1);
                        message = message.substring(0, lastSpaceIndex + 1);
                        break;
                    }

                    ++charIndex;
                }
            }

            int halvedMessageSize = messagePxSize / 2;
            toCompensate = 154 - halvedMessageSize;
            spaceLength = SPACE.getLength() + 1;
            c = 0;

            StringBuilder sb;
            for (sb = new StringBuilder(); c < toCompensate; c += spaceLength) {
                sb.append(" ");
            }

            player.sendMessage(sb + message);
            if (toSendAfter != null) {
                sendCenteredMessageV2(player, toSendAfter);
            }

        }

        public static String centerMessage(String message) {
            message = ChatColor.translateAlternateColorCodes('&', message.replace("<#center>", ""));
            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;
            int charIndex = 0;
            int lastSpaceIndex = 0;
            String toSendAfter = null;
            String recentColorCode = "";
            char[] var8 = message.toCharArray();
            int toCompensate = var8.length;

            int spaceLength;
            int c;
            for (spaceLength = 0; spaceLength < toCompensate; ++spaceLength) {
                c = var8[spaceLength];
                if (c == 167) {
                    previousCode = true;
                } else {
                    if (previousCode) {
                        previousCode = false;
                        recentColorCode = "§" + c;
                        if (c == 108 || c == 76) {
                            isBold = true;
                            continue;
                        }

                        isBold = false;
                    } else if (c == 32) {
                        lastSpaceIndex = charIndex;
                    } else {
                        Chat dFI = getDefaultFontInfo((char) c);
                        messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                        ++messagePxSize;
                    }

                    if (messagePxSize >= 250) {
                        toSendAfter = recentColorCode + message.substring(lastSpaceIndex + 1);
                        message = message.substring(0, lastSpaceIndex + 1);
                        break;
                    }

                    ++charIndex;
                }
            }

            int halvedMessageSize = messagePxSize / 2;
            toCompensate = 154 - halvedMessageSize;
            spaceLength = SPACE.getLength() + 1;
            c = 0;

            StringBuilder sb;
            for (sb = new StringBuilder(); c < toCompensate; c += spaceLength) {
                sb.append(" ");
            }

            return sb + message;
        }
    }
}
