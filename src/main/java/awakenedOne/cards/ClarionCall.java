package awakenedOne.cards;

import automaton.cards.goodstatus.IntoTheVoid;
import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.IntensifyPower;
import awakenedOne.powers.VoidRefundPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static awakenedOne.util.Wiz.*;

public class ClarionCall extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(ClarionCall.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public ClarionCall() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        applyToSelf(new VoidRefundPower(p, 1));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}