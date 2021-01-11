package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import champ.powers.FalseCounterPower;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class FalseCounter extends AbstractChampCard {

    public final static String ID = makeID("FalseCounter");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 4;

    public FalseCounter() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.FINISHER);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!(AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) ||AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID))) {
            cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) applyToSelf(new CounterPower(magicNumber));
        applyToSelf(new FalseCounterPower(1));
        finisher();
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}