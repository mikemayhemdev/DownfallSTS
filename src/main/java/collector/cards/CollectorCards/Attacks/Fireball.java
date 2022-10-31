package collector.cards.CollectorCards.Attacks;

import collector.cards.CollectorCards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;

public class Fireball extends AbstractCollectorCard {
    public final static String ID = makeID("Fireball");

    public Fireball() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 8;
        magicNumber = baseMagicNumber = 3;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new VFXAction(new FireballEffect(p.drawX,p.drawY,m.drawX,m.drawY)));
        atb(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        atb(new SelectCardsInHandAction(magicNumber, ExhaustAction.TEXT[0],true,true,card ->true, Cards->{
            if (Cards.size() > 0){
                atb(new ExhaustSpecificCardAction(Cards.get(0), AbstractDungeon.player.hand));
                AbstractCard copy = this.makeStatEquivalentCopy();
                copy.baseDamage += magicNumber;
                atb(new MakeTempCardInDiscardAction(copy,1));
            }
        }));

    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}