package awakenedOne.cards;

import automaton.cards.goodstatus.IntoTheVoid;
import awakenedOne.AwakenedOneMod;
import awakenedOne.patches.OnCreateCardSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;

public class Spew extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Spew.class.getSimpleName());
    private static final int COST = 2;

    public Spew() {
        super(ID, COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 11;
        loadJokeCardImage(this, makeBetaCardPath(Spew.class.getSimpleName() + ".png"));
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dam = this.damage;
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, dam, damageTypeForTurn), AbstractGameAction.AttackEffect.POISON));
    }



    public void applyPowers() {
        super.applyPowers();
        if (checkVoid()) {
            this.costForTurn = 0;
        }
    }


    public static boolean checkVoid() {
        return OnCreateCardSubscriber.CardsCreatedThisTurn > 0;
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