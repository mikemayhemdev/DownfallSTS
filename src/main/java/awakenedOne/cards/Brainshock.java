package awakenedOne.cards;

import awakenedOne.cards.tokens.PlumeJab;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.NewRipAndTearAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;

import static awakenedOne.AwakenedOneMod.*;

public class Brainshock extends AbstractAwakenedCard {
    public final static String ID = makeID(Brainshock.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1
    public Brainshock() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 8;
        loadJokeCardImage(this, makeBetaCardPath(Brainshock.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < 2; ++i) {
            this.addToBot(new NewRipAndTearAction(this));
        }
        Wiz.atb(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true, false));

    }

    public void upp() {
        upgradeDamage(2);
    }
}