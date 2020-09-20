package champ.cards;

import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Backstep extends AbstractChampCard {

    public final static String ID = makeID("Backstep");

    //stupid intellij stuff skill, self, uncommon

    public Backstep() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        defenseOpen();
        if (bcombo()) {
            if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
                int x = AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount;
                atb(new GainBlockAction(p, x));
            }
        }
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}