package champ.cards;

import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import champ.powers.UltimateFormNextTurnPower;
import champ.powers.UltimateFormPower;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.util.TechniqueMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;

public class UltimateStance extends AbstractChampCard {

    public final static String ID = makeID("UltimateStance");

    // intellij stuff power, self, rare

    private static final int MAGIC = 1;

    public UltimateStance() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    /*
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.stance.ID.equals(champ.stances.UltimateStance.STANCE_ID) ||AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID)) {
            return super.canUse(p, m);
        }
        cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

     */

    public void use(AbstractPlayer p, AbstractMonster m) {
       // techique();
     //   triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
        //ultimateStance();
        applyToSelf(new UltimateFormNextTurnPower(1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}