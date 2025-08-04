package awakenedOne.cards.tokens;

import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.cards.FeatherWhirl;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static awakenedOne.AwakenedOneMod.*;

public class PlumeJab extends AbstractAwakenedCard {
    public final static String ID = makeID(PlumeJab.class.getSimpleName());
    // intellij stuff attack, enemy, special, 3, 2, , , ,

    public PlumeJab() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY, CardColor.COLORLESS);
        baseDamage = 2;
        loadJokeCardImage(this, makeBetaCardPath(PlumeJab.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < 2; ++i) {
            this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }
}