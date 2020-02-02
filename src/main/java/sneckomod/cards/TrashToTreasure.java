package sneckomod.cards;

import com.megacrit.cardcrawl.actions.defect.RecycleAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class TrashToTreasure extends AbstractSneckoCard {

    public final static String ID = makeID("TrashToTreasure");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public TrashToTreasure() {
        super(ID,  1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RecycleAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}