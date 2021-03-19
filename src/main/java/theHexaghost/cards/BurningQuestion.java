package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.red.Rampage;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BurningQuestion extends AbstractHexaCard {
    public final static String ID = makeID("BurningQuestion");
    // intellij stuff skill, self, rare, 10, 2, 10, 2, 8, 2
    //ART:

    public BurningQuestion() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseDamage = 10;
        baseBlock = 10;
        baseMagicNumber = magicNumber = 8;
        isMultiDamage = true;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new ModifyDamageAction(uuid, magicNumber));
    }

    @Override
    public void triggerOnExhaust() {
        flash();
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
        upgradeDamage(2);
        upgradeBlock(2);
        upgradeMagicNumber(2);
    }
}