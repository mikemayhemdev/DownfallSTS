package automaton.patches;

import automaton.cards.FunctionCard;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.EverythingFix;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class DontShowFunctionInCardLibraryPatch {
    @SpirePatch(
            clz = EverythingFix.Initialize.class,
            method = "Insert"
    )
    public static class CardLibraryDontDisplayPairCards {
        public static void Postfix(Object __obj_instance) {
            for (AbstractCard.CardColor color : EverythingFix.Fields.cardGroupMap.keySet()) {
                ArrayList<AbstractCard> remove = new ArrayList<>();
                for (AbstractCard card : EverythingFix.Fields.cardGroupMap.get(color).group) {
                    if (card.cardID.equals(FunctionCard.ID)) {
                        remove.add(card);
                    }
                }
                EverythingFix.Fields.cardGroupMap.get(color).group.removeAll(remove);
            }
        }
    }
}