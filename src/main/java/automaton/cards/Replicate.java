package automaton.cards;

import automaton.AutomatonMod;
import automaton.cardmods.EncodeAndShuffleMod;
import automaton.cardmods.EncodeMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Replicate extends AbstractBronzeCard {

    public final static String ID = makeID("Replicate");

    //stupid intellij stuff attack, enemy, basic

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    public Replicate() {
        super(ID, 0, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("Replicate.png"));
        cardsToPreview = new Strike();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        cardsToPreview.upgrade();

    }
}