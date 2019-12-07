package pl.softlink.spellbinder.service;

import javafx.scene.control.TextArea;
import pl.softlink.spellbinder.lib.diff_match_patch;

import java.util.LinkedList;

public class TextDiff {

    private static diff_match_patch dmp = new diff_match_patch();

    public static String generate(String textOld, String textNew) {
        LinkedList<diff_match_patch.Diff> diffList =  dmp.diff_main(textOld, textNew, true);
        String delta = dmp.diff_toDelta(diffList);
        if (delta.contains("+") || delta.contains("-")) {
            return delta;
        }

        return null;
    }

    public static String apply(String text, String diff) {
        if (diff == null) {
            return text;
        }
        LinkedList<diff_match_patch.Diff> diffList = dmp.diff_fromDelta(text, diff);
        LinkedList<diff_match_patch.Patch> patchList = dmp.patch_make(text, diffList);
        Object[] result = dmp.patch_apply(patchList, text);
        return (String) result[0];
    }
}
