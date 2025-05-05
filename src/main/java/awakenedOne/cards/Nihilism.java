package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.atb;

public class Nihilism extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Nihilism.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Nihilism() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        cardsToPreview = new VoidCard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (checkVoid());
        applyToSelf(new StrengthPower(p, magicNumber));
    }

    public static boolean checkVoid() {
        boolean hasVoid = false;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof VoidCard) {
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
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}