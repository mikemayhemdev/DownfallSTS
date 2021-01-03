package champ.cards;

import champ.ChampChar;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class LastStand extends AbstractChampCard {

    public final static String ID = makeID("LastStand");

    //stupid intellij stuff skill, self, rare

    public LastStand() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RemoveDebuffsAction(p));
        applyToSelf(new StrengthPower(p, 6));
        finisher();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.currentHealth < p.maxHealth / 2) {
            return super.canUse(p, m);
        }
        cantUseMessage = ChampChar.characterStrings.TEXT[28];
        return false;
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}