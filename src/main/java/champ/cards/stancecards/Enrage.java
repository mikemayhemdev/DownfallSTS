package champ.cards.stancecards;

import champ.ChampMod;
import champ.cards.AbstractChampCard;
import champ.powers.EnergizedDurationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class Enrage extends AbstractChampCard {

    public final static String ID = makeID("Enrage");

    public Enrage() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new VigorPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}