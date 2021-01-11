package champ.cards;

import champ.ChampMod;
import champ.powers.CalledShotPower;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class CalledShot extends AbstractChampCard {

    public final static String ID = makeID("CalledShot");

    //stupid intellij stuff power, self, uncommon

    public CalledShot() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        tags.add(ChampMod.COMBOBERSERKER);
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID) ||AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID)) {
            return super.canUse(p, m);
        }
        cantUseMessage = EXTENDED_DESCRIPTION[3];
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (bcombo()){
            this.exhaust = true;
            if (upgraded) {
                atb(new GainEnergyAction(3));
            } else {
                atb(new GainEnergyAction(2));
            }
        }
        if (dcombo()){
            atb(new DrawCardAction(magicNumber));
        }
    }


    @Override
    public void triggerOnGlowCheck() {
        glowColor = (dcombo() || bcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        upgradeMagicNumber(1);
        initializeDescription();
        tags.add(SneckoMod.BANNEDFORSNECKO);

    }
}