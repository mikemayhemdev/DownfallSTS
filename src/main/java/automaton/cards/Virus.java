
package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.AddToFuncAction;
import automaton.cards.encodedcards.EncodedPoisonStab;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;
import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.LIGHTNING;

public class Virus extends AbstractBronzeCard {

    public final static String ID = makeID("Virus");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 6;
    private static final int MAGIC = 3;

    public Virus() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 3;
        cardsToPreview = new EncodedPoisonStab();
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("Virus.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        this.addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber));

        AbstractCard q = new EncodedPoisonStab();
        if (upgraded) q.upgrade();
        atb(new SelectCardsInHandAction(2, masterUI.TEXT[6], true, true, c -> c.type == CardType.STATUS, (cards) -> {
            att(new AddToFuncAction(q.makeStatEquivalentCopy(), null));
            att(new ExhaustSpecificCardAction(cards.get(0), p.hand, true));
        }));
    }

    public void upp() {
        cardsToPreview.upgrade();
        upgradeDamage(2);
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}

