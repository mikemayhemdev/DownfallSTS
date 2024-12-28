package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

import static sneckomod.SneckoMod.getRandomStatus;

public class Break extends AbstractBronzeCard {

    public final static String ID = makeID("Break");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 5;

    public Break() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 3;
        thisEncodes();
        tags.add(AutomatonMod.BAD_COMPILE);
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Break.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            atb(new MakeTempCardInHandAction(new Burn(), 1, true));
            atb(new MakeTempCardInHandAction(new Wound(), 1, true));
            atb(new MakeTempCardInHandAction(new Slimed(), 1, true));
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}