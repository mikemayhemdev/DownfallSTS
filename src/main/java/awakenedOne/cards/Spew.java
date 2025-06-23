package awakenedOne.cards;

import automaton.cards.goodstatus.IntoTheVoid;
import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;

public class Spew extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Spew.class.getSimpleName());
    private static final int COST = 2;

    public Spew() {
        super(ID, COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 12;
        this.cardsToPreview = new VoidCard();
        loadJokeCardImage(this, ID+".png");
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dam = this.damage;
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, dam, damageTypeForTurn), AbstractGameAction.AttackEffect.POISON));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);

        if (checkVoid()) {
            int base_dam = this.damage;
            this.damage += base_dam;
        }

        isDamageModified = damage != baseDamage;
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