package champ.cards.stancecards;

import champ.ChampMod;
import champ.cards.AbstractChampCard;
import champ.powers.EnergizedDurationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

public class RopeADope extends AbstractChampCard {

    public final static String ID = makeID("RopeADope");

    public RopeADope() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseMagicNumber = magicNumber = 2;
        tags.add(ChampMod.FINISHER);
        baseBlock = block = 10;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, 2), 2));

    }

    public void upp() {
        upgradeBlock(3);
    }
}