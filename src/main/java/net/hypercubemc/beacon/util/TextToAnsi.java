package net.hypercubemc.beacon.util;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.*;
import java.util.regex.Pattern;
import static java.util.Map.entry;

/*
   Incomplete - Contributions welcome
   Trying to port fabric-console (kotlin) to java
 */
public class TextToAnsi {
    private static final Map<Formatting, String> formattingToAnsi = Map.ofEntries(
        entry(Formatting.BLACK, AnsiCodes.colorBlack),
        entry(Formatting.DARK_BLUE, AnsiCodes.colorBlue),
        entry(Formatting.DARK_GREEN, AnsiCodes.colorGreen),
        entry(Formatting.DARK_AQUA, AnsiCodes.colorCyan),
        entry(Formatting.DARK_RED, AnsiCodes.colorRed),
        entry(Formatting.DARK_PURPLE, AnsiCodes.colorMagenta),
        entry(Formatting.GOLD, AnsiCodes.colorYellow),
        entry(Formatting.GRAY, AnsiCodes.colorLightGrey),
        entry(Formatting.DARK_GRAY, AnsiCodes.colorDarkGrey),
        entry(Formatting.BLUE, AnsiCodes.colorBrightBlue),
        entry(Formatting.GREEN, AnsiCodes.colorBrightGreen),
        entry(Formatting.AQUA, AnsiCodes.colorBrightCyan),
        entry(Formatting.RED, AnsiCodes.colorBrightRed),
        entry(Formatting.LIGHT_PURPLE, AnsiCodes.colorPink),
        entry(Formatting.YELLOW, AnsiCodes.colorBrightYellow),
        entry(Formatting.WHITE, AnsiCodes.colorWhite),
        entry(Formatting.OBFUSCATED, ""),
        entry(Formatting.BOLD, AnsiCodes.formatBold),
        entry(Formatting.STRIKETHROUGH, AnsiCodes.formatStrike),
        entry(Formatting.UNDERLINE, AnsiCodes.formatUnderline),
        entry(Formatting.ITALIC, AnsiCodes.formatItalic),
        entry(Formatting.RESET, AnsiCodes.formatReset)
    );

    private final Pattern formattingCodePattern = Pattern.compile("§[0-9a-fk-or]", Pattern.CASE_INSENSITIVE);

    private Formatting formattingFromChar(Character Char) {
        return Formatting.RESET; // Dummy
    }

    public static Text textToAnsi(Text message) {
        return message; // Dummy
    }
}