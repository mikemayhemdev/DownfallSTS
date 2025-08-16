package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.BurnPower;
import theHexaghost.relics.CandleOfCauterizing;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Explode extends AbstractBronzeCard {
    public final static String ID = makeID("Explode");

    public Explode() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseMagicNumber = magicNumber = 15;
        baseAuto = auto = 2;
        thisEncodes();
        tags.add(AutomatonMod.BAD_COMPILE);
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Explode.png"));
    }

    @Override
    public void applyPowers() {
        if(AbstractDungeon.player.hasRelic(CandleOfCauterizing.ID)){
            this.magicNumber = this.baseMagicNumber + CandleOfCauterizing.SOULBURN_BONUS_AMOUNT;
        }
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(q, AbstractDungeon.player, new BurnPower(q, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }


    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            shuffleIn(new Burn(), auto);
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(5);
    }
}
