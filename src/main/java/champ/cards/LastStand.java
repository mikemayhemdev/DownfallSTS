package champ.cards;

import champ.powers.LastStandPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class LastStand extends AbstractChampCard {
    public final static String ID = makeID("LastStand");

    public LastStand() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LastStandPower( 6));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth / 2 ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public String getLimitBreak() {
        ArrayList<String> derpy = new ArrayList<>();
        derpy.add(EXTENDED_DESCRIPTION[0]);
        derpy.add(EXTENDED_DESCRIPTION[1]);
        return derpy.get(AbstractDungeon.cardRandomRng.random(derpy.size() - 1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}