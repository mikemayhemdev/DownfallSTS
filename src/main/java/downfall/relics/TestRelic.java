package downfall.relics;

import automaton.AutomatonChar;
import automaton.actions.RepeatCardAction;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import champ.ChampChar;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import downfall.downfallMod;
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
        newCardsList.removeIf(c -> c.color == TheSnecko.Enums.SNECKO_CYAN || c.color == AbstractCardEnum.GUARDIAN || c.color == slimebound.patches.AbstractCardEnum.SLIMEBOUND || c.color == TheHexaghost.Enums.GHOST_GREEN || c.color == ChampChar.Enums.CHAMP_GRAY || c.color == AutomatonChar.Enums.BRONZE_AUTOMATON);
        newCardsList.sort((o1, o2) -> o1.name.compareToIgnoreCase(o2.name));
        return newCardsList;
    }

    private void setNextCard() {
        counter++;
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new CardPowerTip(allCardsSortedAlphabet().get(counter)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onRightClick() {
        if (counter == 0) {
            AbstractDungeon.getCurrRoom().monsters.monsters.get(0).increaseMaxHp(12345, true);
        }
        flash();
        addToTop(new RepeatCardAction(allCardsSortedAlphabet().get(counter)));
        setNextCard();
    }
}
