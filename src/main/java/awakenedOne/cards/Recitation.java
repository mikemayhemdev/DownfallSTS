package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.relics.KTRibbon;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.applyToSelf;

public class Recitation extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(Recitation.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    boolean chant = false;

    public Recitation() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.chant) {
            chant();
        }

        if ((!this.chant) && AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            if (!AbstractDungeon.player.getRelic(KTRibbon.ID).grayscale) {
                chant();
                awaken(1);
            }
        }
    }

    @Override
    public void chant() {
        this.addToBot(new VFXAction(AbstractDungeon.player, new InflameEffect(AbstractDungeon.player), 1.0F));
        applyToSelf((new StrengthPower(AbstractDungeon.player, magicNumber)));
        checkOnChant();
    }

    public void triggerWhenDrawn() {
        this.chant = false;
    }

    public void triggerOnCardPlayed(AbstractCard card) {
        if (card.type == CardType.POWER && AbstractDungeon.player.hand.contains((AbstractCard)this))
            this.chant = true;
    }

    @Override
    public void onMoveToDiscard() {
        this.chant = false;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = this.chant ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}