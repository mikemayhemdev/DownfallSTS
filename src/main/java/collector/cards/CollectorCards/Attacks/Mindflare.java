package collector.cards.CollectorCards.Attacks;

import collector.CollectorMod;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static collector.CollectorCollection.combatCollection;

public class Mindflare extends AbstractCollectorCard {
    public final static String ID = makeID("Mindflare");

    public Mindflare() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 16;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        for (AbstractPower po : m.powers){
            if (CollectorMod.AfflictionMatch(po.ID)){
                addToBot(new VFXAction(new InflameEffect(p)));
                atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractCard cardtodraw = combatCollection.getTopCard();
                        System.out.println(cardtodraw.name);
                        combatCollection.removeCard(cardtodraw);
                        p.hand.addToBottom(cardtodraw);
                        isDone = true;
                    }
                });
            }
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}
