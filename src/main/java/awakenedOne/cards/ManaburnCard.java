package awakenedOne.cards;

import awakenedOne.powers.ManaburnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.TriggerMarksAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

import static awakenedOne.AwakenedOneMod.*;

public class ManaburnCard extends AbstractAwakenedCard {
    public final static String ID = makeID(ManaburnCard.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    //Manaburn

    //rework to pressure points

    public ManaburnCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 5;
        loadJokeCardImage(this, makeBetaCardPath(ManaburnCard.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        this.addToBot(new ApplyPowerAction(m, p, new ManaburnPower(m, this.magicNumber), this.magicNumber));
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(2);
    }
}