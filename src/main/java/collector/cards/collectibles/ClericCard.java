package collector.cards.collectibles;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class ClericCard extends AbstractCollectibleCard {
    public final static String ID = makeID(ClericCard.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , 10, 4

    public ClericCard() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 8;
        isPyre();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded){
            this.exhaust = TempHPField.tempHp.get(p) > 0;
        }
        atb(new AddTemporaryHPAction(p, p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(4);
    }
}