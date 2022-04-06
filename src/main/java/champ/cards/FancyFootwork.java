package champ.cards;

import champ.ChampMod;
import champ.powers.FocusedBerPower;
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

    private static final int MAGIC = 10;
    private static final int UPG_MAGIC = 5;

    public FancyFootwork() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        //exhaust = true;
        tags.add(ChampMod.OPENER);
        tags.add(ChampMod.OPENERNOTIN);
        postInit();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {


        applyToSelf(new FocusedBerPower(magicNumber));
        p.useHopAnimation();
        atb(new VFXAction(new StanceDanceEffect(p, false, false, true), 0.7F));

    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}