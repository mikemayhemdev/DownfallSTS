package awakenedOne.cards;

import automaton.cards.goodstatus.IntoTheVoid;
import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Spew extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Spew.class.getSimpleName());
    public int cost_revert = 0;
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    private static final int COST = 2;

    public Spew() {
        super(ID, COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 14;
        this.cardsToPreview = new VoidCard();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.POISON);
    }

    public void applyPowers() {
        super.applyPowers();
            if (checkVoid()) {
                this.freeToPlayOnce = true;
            }
            else {
                this.freeToPlayOnce = false;
        }
    }

    public static boolean checkVoid() {
        boolean hasVoid = false;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof VoidCard || c instanceof IntoTheVoid) {
                hasVoid = true;
            }
        }
        return hasVoid;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (checkVoid()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }
}