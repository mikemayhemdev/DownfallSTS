/*
package downfall.relics;

import automaton.AutomatonChar;
import automaton.actions.RepeatCardAction;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import champ.ChampChar;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import downfall.downfallMod;
import expansioncontent.patches.CardColorEnumPatch;
import guardian.patches.AbstractCardEnum;
import sneckomod.TheSnecko;
import theHexaghost.TheHexaghost;

import java.util.ArrayList;

public class TestRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = downfallMod.makeID("TestRelic");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/WingStatue.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/WingStatue.png"));

    public TestRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
        counter = 0;
    }

    private ArrayList<AbstractCard> allCardsSortedAlphabet() {
        ArrayList<AbstractCard> newCardsList = new ArrayList<>(CardLibrary.getAllCards());
        newCardsList.removeIf(c -> c.rarity == AbstractCard.CardRarity.SPECIAL || c.color != AbstractDungeon.player.getCardColor());
        newCardsList.sort((o1, o2) -> o1.name.compareToIgnoreCase(o2.name));
        return newCardsList;
    }

    private void setNextCard() {
        if (counter < allCardsSortedAlphabet().size() - 1) {
            counter++;
            tips.clear();
            tips.add(new PowerTip(name, description));
            tips.add(new CardPowerTip(allCardsSortedAlphabet().get(counter)));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.getCurrRoom().monsters.monsters.get(0).maxHealth < 1000) {
            AbstractDungeon.getCurrRoom().monsters.monsters.get(0).increaseMaxHp(12345, true);
        }
        for (AbstractCard c: AbstractDungeon.player.hand.group){
            addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
        }
        flash();
        AbstractDungeon.player.gainEnergy(allCardsSortedAlphabet().get(counter).cost + 2);
        addToTop(new MakeTempCardInHandAction(allCardsSortedAlphabet().get(counter)));
        setNextCard();
    }
}
*/