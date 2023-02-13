package champ.cards;

import champ.ChampMod;
import champ.powers.FocusedBerPower;
import champ.vfx.StanceDanceEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FancyFootwork extends AbstractChampCard {
    public final static String ID = makeID("FancyFootwork");

    public FancyFootwork() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 10;
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
        upgradeMagicNumber(5);
    }
}