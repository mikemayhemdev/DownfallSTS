package collector.cards.CollectorCards.Attacks;

import collector.cards.CollectorCards.AbstractCollectorCard;
import collector.powers.SoulMark;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class SoulHarvest extends AbstractCollectorCard {
    public final static String ID = makeID("SoulHarvest");

    public SoulHarvest() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ALL_ENEMY,CollectorCardSource.BACK);
        RearBaseDamage = douDamage = baseDamage = 6;
        isMultiDamage = true;
        selfRetain = true;
        exhaust = true;
        baseMagicNumber = magicNumber = 3;
        rearDealsDmg = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new CleaveEffect()));
        allDmg(AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.FIRE);
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            applyToEnemy(mo, new SoulMark(magicNumber,mo));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}