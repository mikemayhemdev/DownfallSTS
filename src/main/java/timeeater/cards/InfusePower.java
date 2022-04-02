package timeeater.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cardmods.IncreasedDamageMod;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class InfusePower extends AbstractTimeEaterCard {
    public final static String ID = makeID("InfusePower");
    // intellij stuff attack, enemy, common, 6, 2, , , 2, 1

    public InfusePower() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : SuspendHelper.suspendGroup.group) {
                    CardModifierManager.addModifier(q, new IncreasedDamageMod(magicNumber));
                    q.superFlash();
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}