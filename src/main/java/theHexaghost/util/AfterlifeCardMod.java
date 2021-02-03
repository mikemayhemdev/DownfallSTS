package theHexaghost.util;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theHexaghost.HexaMod;

public class AfterlifeCardMod extends AbstractCardModifier {
    private UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("afterlifeMod");

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(HexaMod.AFTERLIFE);
        card.isEthereal = true;
    }

    @Override
    public void onExhausted(AbstractCard card) {
        card.use(AbstractDungeon.player, AbstractDungeon.getRandomMonster()); //NOTE: Minor things may happen if we make single-target Afterlife cards.
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return uiStrings.TEXT[0] + rawDescription;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new AfterlifeCardMod();
    }
}
