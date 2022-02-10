package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import hermit.characters.hermit;

import java.util.Iterator;

@SpirePatch(
        clz= HandCardSelectScreen.class,
        method="prep"
)

public class SelectScreenPatch2 {


    @SpirePrefixPatch
    public static SpireReturn SelectionPrePatch(HandCardSelectScreen reg)
    {
        if (AbstractDungeon.player.chosenClass == hermit.Enums.HERMIT) {
            SelectScreenPatch.handClone = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            Iterator var1 = AbstractDungeon.player.hand.group.iterator();

            while (var1.hasNext()) {
                AbstractCard c = (AbstractCard) var1.next();
                SelectScreenPatch.handClone.addToBottom(c);
            }

        }
        return SpireReturn.Continue();
    }


}
