package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import champ.powers.EnGardePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class EnGarde extends AbstractChampCard {

    public final static String ID = makeID("EnGarde");

    //stupid intellij stuff skill, self, common

    public EnGarde() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        blck();
        applyToSelf(new EnGardePower(block));
    }

    public void upp() {
        upgradeBlock(3);
    }
}