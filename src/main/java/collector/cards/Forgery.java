package collector.cards;

import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class Forgery extends AbstractCollectorCard {
    public final static String ID = makeID(Forgery.class.getSimpleName());
    // intellij stuff attack, enemy, common, 9, 3, , , , 

    public Forgery() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            AbstractCard tar = cards.get(0).makeStatEquivalentCopy();
            CardModifierManager.addModifier(tar, new EtherealMod());
            att(new MakeTempCardInHandAction(tar));
        }));
    }

    public void upp() {
        upgradeDamage(3);
    }
}