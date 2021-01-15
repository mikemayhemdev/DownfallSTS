package champ.cards;

import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import champ.vfx.StanceDanceEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FancyFootwork extends AbstractChampCard {

    public final static String ID = makeID("FancyFootwork");

    // intellij stuff skill, self, uncommon

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public FancyFootwork() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)) {
            return super.canUse(p, m);
        }
        cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        if (AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID)) {
            berserkOpen();
        } else if (AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID)) {
            defenseOpen();
        }
        atb(new DrawCardAction(magicNumber));
        p.useHopAnimation();
        atb(new VFXAction(new StanceDanceEffect(p, false, false, true), 0.7F));

    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}