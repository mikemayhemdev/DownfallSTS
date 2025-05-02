package awakenedOne.cards;

import awakenedOne.relics.KTRibbon;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.atb;

public class Debilitate extends AbstractAwakenedCard {
    public final static String ID = makeID(Debilitate.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    boolean chant = false;

    public Debilitate() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 10;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
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
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying) && !monster.halfDead) {
                    atb(new ApplyPowerAction(monster, AbstractDungeon.player, new WeakPower(monster, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    atb(new ApplyPowerAction(monster, AbstractDungeon.player, new VulnerablePower(monster, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }
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
        upgradeDamage(3);
        upgradeMagicNumber(1);
    }
}