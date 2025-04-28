package collector.cards.collectibles;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import guardian.powers.LoseThornsPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class SpikeSlimeCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SpikeSlimeCard.class.getSimpleName());
    // intellij stuff attack, enemy, common, 15, 5, , , , 

    public SpikeSlimeCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        baseBlock = 9;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new ThornsPower(p, magicNumber));
        if (TempHPField.tempHp.get(p) == 0){
            applyToSelf(new LoseThornsPower(p, magicNumber));
        }

    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}