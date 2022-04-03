package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class StasisStrike extends AbstractTimeEaterCard {
    public final static String ID = makeID("StasisStrike");
    // intellij stuff attack, enemy, uncommon, 16, 4, , , 4, 1

    public StasisStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 16;
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        atb(new ModifyDamageAction(this.uuid, this.magicNumber));
        selfStasis();
    }

    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(1);
    }
}