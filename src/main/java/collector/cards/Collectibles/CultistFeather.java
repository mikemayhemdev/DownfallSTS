package collector.cards.Collectibles;

import collector.CollectorChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CultistFeather extends AbstractCollectibleCard {
    public final static String ID = makeID("CultistFeather");

    public CultistFeather() {
        super(ID, 0, CardType.POWER, CardRarity.COMMON, CardTarget.NONE, AbstractCollectorCard.DTCardTarget.BOTH);
        baseDamage = 13;
        baseBlock = 1;
        magicNumber = baseMagicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CollectorChar.isFrontDragon()) {
            atb(new ApplyPowerAction(CollectorChar.torch,CollectorChar.torch,new StrengthPower(CollectorChar.torch,magicNumber)));
            if (upgraded){
                atb(new ApplyPowerAction(CollectorChar.torch,CollectorChar.torch,new DexterityPower(CollectorChar.torch,magicNumber)));
            }
        } else {
            atb(new ApplyPowerAction(p,p,new StrengthPower(p,magicNumber)));
            if (upgraded){
                atb(new ApplyPowerAction(p,p,new DexterityPower(CollectorChar.torch,magicNumber)));
            }
        }
    }

    @Override
    public void upp() {
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}