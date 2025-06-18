package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RagePower;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static awakenedOne.util.Wiz.applyToSelf;

public class MysticOrder extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(MysticOrder.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public MysticOrder() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new RageExhaustPower(p, this.magicNumber), this.magicNumber));
    }


    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}