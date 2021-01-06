package collector.cards;

import collector.actions.FireballAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;

public class Fireball extends AbstractCollectorCard {
    public final static String ID = makeID("Fireball");

    public Fireball() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 10;
        isMultiDamage = true;
        CollectorSecondDamage = BaseCollectorSecondDamage = 16;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new FireballEffect(p.drawX,p.drawY,m.drawX,m.drawY)));
        atb(new FireballAction(m,new DamageInfo(p,CollectorSecondDamage, DamageInfo.DamageType.NORMAL),damage));
    }

    @Override
    public void upp() {
        upgradeCollectorSecondDamage(4);
        upgradeDamage(5);
    }
}