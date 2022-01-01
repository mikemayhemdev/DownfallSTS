package collector.cards.CollectorCards.Attacks;

import collector.CollectorChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TagTeam extends AbstractCollectorCard {
    public final static String ID = makeID("TagTeam");

    public TagTeam() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, CollectorCardSource.BOTH);
        baseDamage = 9;
        douBaseDamage = 9;
        baseBlock = 9;
        douBaseBlock = 9;
    }
    @Override
    public void applyPowers() {
        super.applyPowers();

        if (CollectorChar.isRearYou()) {
            rawDescription = DESCRIPTION;
        } else {
            rawDescription = EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CollectorChar.isFrontTorchHead()) {
            atb(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL)));
            if (CollectorChar.getLivingTorchHead() != null) {
                atb(new GainBlockAction(CollectorChar.torch, douBlock));
            }
        } else {
            if (CollectorChar.getLivingTorchHead() != null) {
                atb(new DamageAction(m, new DamageInfo(CollectorChar.torch, douDamage, DamageInfo.DamageType.NORMAL)));
            }
            atb(new GainBlockAction(p,block));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}