package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.StrengthOverTurnsPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import downfall.util.CardIgnore;

import static awakenedOne.util.Wiz.applyToSelf;

public class MysticOrder extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(MysticOrder.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    boolean chant = false;

    public MysticOrder() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 5;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        this.addToBot(new VFXAction(p, new InflameEffect(p), 1.0F));
        applyToSelf(new RitualPower(p, magicNumber, true));
    }


    @Override
    public void upp() {
        upgradeBlock(3);
    }
}